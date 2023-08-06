import { Button } from "@/components/ui/button";
import { Combobox } from "@/components/ui/combobox";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
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
    const [employeeData, setEmployeeData] = useState<any>(null)
    const [coordenatesData, setCountryData] = useState<any>(null)

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
    })

      const { ref: cpfRef } = useIMask(
        {
            mask: "000.000.000-00"
        },
    );


    const onSubmit = async (data: z.infer<typeof formSchema>) => {
        setSending(true)
        try {
            const formData = new FormData()

            Object.entries(data).forEach(([key, value]) => {
                console.log(key, value)
                if (key === 'dependants') {
                    // @ts-ignore
                    value.forEach((dependant, index) => {
                        Object.entries(dependant).forEach(([dependantKey, dependantValue]) => {
                            // @ts-ignore
                            //Alterar para um array de Objetos com os dados dos dependentes
                            formData.append(`dependants[${index}][${dependantKey}]`, dependantValue)
                        })
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

            await fetch(`${process.env.API_HOST}/external/employee`, {
                method: "POST",
                body: formData,
            })
        } catch (error) {
            console.error(error)
        } finally {
            setSending(false)
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

    useEffect(() => {
        const fetchEmployee = async () => {
            const response = await fetch(`${process.env.API_HOST}/external/employee`, {
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            const data = await response.json()
            setEmployeeData(data)
        }
        fetchEmployee()
    }, [])

    useEffect(() => {
        const fetchCoordenates = async () => {
            const response = await fetch('')
            const data = await response.json()
            setCountryData(data)
        }
        fetchCoordenates()
    }, [])

  return (
    
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
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
                                <Input placeholder="Digite seu CPF" {...field} ref={cpfRef as React.RefObject<HTMLInputElement>} />                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="name"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Reletório</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite uma ocorrência, uma crítica ou uma ideia" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                {/* location */}
                {/* <FormField
                    control={form.control}
                    name="pictures"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Fotos</FormLabel>
                            <FormControl>
                                <Input type="file" multiple {...field}
                                    value={field.value?.name}
                                    onChange={(e) => {
                                        const value = {
                                            name: e.target.value,
                                            file: e.target.files?.[0]
                                        }
                                        field.onChange(value)
                                    }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                /> */}
                <Button type="submit" disabled={sending}>{sending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />} Entrar</Button>
            </form>
        </Form>
        
    )
}

export default ReportsForm