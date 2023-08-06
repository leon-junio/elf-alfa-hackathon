import { Button } from "@/components/ui/button";
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Switch } from "@/components/ui/switch";
import { useToast } from "@/components/ui/use-toast";
import { UserContext } from "@/components/user-provider";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { useContext, useState } from "react";
import { useForm } from "react-hook-form";
import * as z from "zod";
import formSchema from "../schemas/resource";

interface ResourceFormProps {
    onCreate?: (data: z.infer<typeof formSchema>) => void
}

const ResourceForm = (props: ResourceFormProps) => {
    const { onCreate } = props
    const { user, token } = useContext(UserContext)
    const [sending, setSending] = useState(false)

    const { toast } = useToast()

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
    })

    const onSubmit = async (data: z.infer<typeof formSchema>) => {
        if (!user || !token) return toast({
            title: "Você precisa estar logado para cadastrar um recurso",
            variant: "destructive",
        })

        setSending(true)
        try {
            const formData = new FormData()

            Object.entries(data).forEach(([key, value]) => {
                if (!value) return;
                // @ts-ignore
                if (value.file) {
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

            const res = await fetch(`${process.env.API_HOST}/resource`, {
                method: "POST",
                body: formData,
                headers: {
                    'Authorization': token,
                },
                redirect: 'follow'
            })
            if (res.status !== 200) throw new Error("Erro ao cadastrar recurso")
            const resource = await res.json();
            form.reset()
            onCreate?.(resource)
            toast({
                title: "Recurso cadastrado com sucesso",
            })
        } catch (error) {
            console.log(error)
            toast({
                title: "Erro ao cadastrar recurso",
                variant: "destructive",
            })
        } finally {
            setSending(false)
        }
    }

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
                <FormField
                    control={form.control}
                    name="description"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Descrição do recurso (área ou equipamento)</FormLabel>
                            <FormControl>
                                <Input placeholder="Descreva o recurso" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="filePath"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Documento</FormLabel>
                            <FormControl>
                                <Input type="file" {...field}
                                    value={field.value?.name}
                                    onChange={(e) => {
                                        const value = {
                                            name: e.target.value,
                                            file: e.target.files?.[0]
                                        }
                                        field.onChange(value)
                                    }}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="isAvailable"
                    render={({ field }) => (
                        <FormItem className="flex flex-row items-center justify-between rounded-lg border p-4">
                            <div className="space-y-0.5">
                                <FormLabel className="text-base">
                                    Disponível
                                </FormLabel>
                                <FormDescription>
                                   Este recurso está disponível para uso
                                </FormDescription>
                            </div>
                            <FormControl>
                                <Switch
                                    checked={field.value}
                                    onCheckedChange={field.onChange}
                                />
                            </FormControl>
                        </FormItem>
                    )} />
                <Button type="submit" disabled={sending}>{sending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />} Cadastrar recurso</Button>
            </form>
        </Form>
    )
}

export default ResourceForm