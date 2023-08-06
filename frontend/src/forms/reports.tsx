import { Button } from "@/components/ui/button";
import { Combobox } from "@/components/ui/combobox";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { useToast } from "@/components/ui/use-toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useIMask } from "react-imask";
import * as z from "zod";
import formSchema from "../schemas/reports";

const ReportsForm = () => {
    const [sending, setSending] = useState(false)

    const [resourceData, setResourceData] = useState<any>(null)
    const [roleData, setRoleData] = useState<any>(null)

    const { toast } = useToast()

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
    })

    const { ref: cpfRef } = useIMask(
        {
            mask: "000.000.000-00"
        },
    )

    const successFunction = async (data: z.infer<typeof formSchema>, coords: any) => {
        if (!(coords)) {
            return toast({
                title: "Você precisa permitir a localização para enviar o relato",
                variant: "destructive",
            })
        }

        setSending(true)
        try {
            const formData = new FormData()

            Object.entries(data).forEach(([key, value]) => {
                if (!value) return;

                if (key === 'pictures') {
                    // @ts-ignore
                    value.forEach((picture: any) => {
                        // @ts-ignore
                        formData.append('pictures', picture.file)
                    })
                    // @ts-ignore
                } else if (value.file) {
                    // @ts-ignore
                    formData.append(key, value.file)
                }
                else if (value instanceof Date) {
                    // @ts-ignore
                    formData.append(key, value.toISOString().split('T')[0])
                } else {
                    // @ts-ignore
                    formData.append(key, value)
                }
            })

            formData.append("latitude", coords.latitude)
            formData.append("longitude", coords.longitude)

            const res = await fetch(`${process.env.API_HOST}/external/report`, {
                method: "POST",
                body: formData,
            })

            if (res.status !== 200) throw new Error("Erro ao cadastrar recurso")

            toast({
                title: "Relato enviado com sucesso!",
            })
        } catch (error) {
            console.error(error)
            toast({
                title: "Erro ao enviar relato",
                variant: "destructive",
            })
        } finally {
            setSending(false)
        }
    }


    const onSubmit = async (data: z.infer<typeof formSchema>) => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition((e) => {
                successFunction(data, e.coords)
            }, (error) => {
                toast({
                    title: "Você precisa permitir a localização para enviar o relato",
                    variant: "destructive",
                })
            });
        } else {
            return toast({
                title: "Geolocalização não suportada",
                variant: "destructive",
            })
        }
    }

    useEffect(() => {
        const fetchResource = async () => {
            const response = await fetch(`${process.env.API_HOST}/external/resource`, {
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            const data = await response.json()
            setResourceData(data)
        }
        fetchResource()
    }, [])

    useEffect(() => {
        const fetchRole = async () => {
            const response = await fetch(`${process.env.API_HOST}/external/role`, {
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            const data = await response.json()
            setRoleData(data)
        }
        fetchRole()
    }, [])

    return (
        <Form {...form}>
            <form onSubmit={(e) => {
                form.handleSubmit(onSubmit, (error) => {
                    console.error(error)
                })(e)
            }} className="space-y-8">
                <FormField
                    control={form.control}
                    name="name"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Nome</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu nome" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="resource"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Área e/ou Equipamentos</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={resourceData?.map((resource: any) => ({
                                    label: resource.description,
                                    value: resource.id,
                                }))} onChange={(option) => {
                                    field.onChange(option?.value)
                                }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="role"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Cargo</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={roleData?.map((role: any) => ({
                                    label: role.name,
                                    value: role.id,
                                }))} onChange={(option) => {
                                    field.onChange(option?.value)
                                }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="employee"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>CPF</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu CPF" {...field} ref={cpfRef as React.RefObject<HTMLInputElement>} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="ocurrenceDescription"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Relatório</FormLabel>
                            <FormControl>
                                <Textarea placeholder="Digite uma ocorrência, uma crítica ou uma ideia" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <div className="flex flex-wrap space-x-2">
                    <FormField
                        control={form.control}
                        name="pictures.0"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Foto 1</FormLabel>
                                <FormControl>
                                    <div className="relative overflow-hidden border border-dashed flex flex-col items-center justify-center w-80 h-80 rounded-md">
                                        <Input className="absolute top-0 left-0 opacity-0 min-h-full min-w-full" type="file" accept="image/*" capture {...field}
                                            value={field.value?.name}
                                            onChange={(e) => {
                                                if (!e.target.files?.[0]) return
                                                const newValue = {
                                                    name: e.target.value,
                                                    file: e.target.files?.[0],
                                                }
                                                field.onChange(newValue)
                                            }}>
                                        </Input>
                                        {field.value?.file ? (
                                            // eslint-disable-next-line @next/next/no-img-element
                                            <img src={URL.createObjectURL(field.value.file)} className="w-full h-full object-cover" alt="" />
                                        ) : (
                                            <p className="text-sm text-center">Arraste e solte ou clique aqui para adicionar uma foto</p>
                                        )}
                                    </div>
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="pictures.1"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Foto 2</FormLabel>
                                <FormControl>
                                    <div className="relative overflow-hidden border border-dashed flex flex-col items-center justify-center w-80 h-80 rounded-md">
                                        <Input className="absolute top-0 left-0 opacity-0 min-h-full min-w-full" type="file" accept="image/*" capture {...field}
                                            value={field.value?.name}
                                            onChange={(e) => {
                                                if (!e.target.files?.[0]) return
                                                const newValue = {
                                                    name: e.target.value,
                                                    file: e.target.files?.[0],
                                                }
                                                field.onChange(newValue)
                                            }}>
                                        </Input>
                                        {field.value?.file ? (
                                            // eslint-disable-next-line @next/next/no-img-element
                                            <img src={URL.createObjectURL(field.value.file)} className="w-full h-full object-cover" alt="" />
                                        ) : (
                                            <p className="text-sm text-center">Arraste e solte ou clique aqui para adicionar uma foto</p>
                                        )}
                                    </div>
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="pictures.2"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Foto 3</FormLabel>
                                <FormControl>
                                    <div className="relative overflow-hidden border border-dashed flex flex-col items-center justify-center w-80 h-80 rounded-md">
                                        <Input className="absolute top-0 left-0 opacity-0 min-h-full min-w-full" type="file" accept="image/*" capture {...field}
                                            value={field.value?.name}
                                            onChange={(e) => {
                                                if (!e.target.files?.[0]) return
                                                const newValue = {
                                                    name: e.target.value,
                                                    file: e.target.files?.[0],
                                                }
                                                field.onChange(newValue)
                                            }}>
                                        </Input>
                                        {field.value?.file ? (
                                            // eslint-disable-next-line @next/next/no-img-element
                                            <img src={URL.createObjectURL(field.value.file)} className="w-full h-full object-cover" alt="" />
                                        ) : (
                                            <p className="text-sm text-center">Arraste e solte ou clique aqui para adicionar uma foto</p>
                                        )}
                                    </div>
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                </div>

                <Button type="submit" disabled={sending}>{sending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />} Enviar relatório</Button>
            </form>
        </Form>
    )
}

export default ReportsForm