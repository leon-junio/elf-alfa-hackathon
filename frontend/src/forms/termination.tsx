import { Button } from "@/components/ui/button";
import { Combobox } from "@/components/ui/combobox";
import { DatePicker } from "@/components/ui/datepicker";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { useToast } from "@/components/ui/use-toast";
import { UserContext } from "@/components/user-provider";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { useContext, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import * as z from "zod";
import formSchema from "../schemas/termination";

interface TerminationFormProps {
    onCreate?: (data: z.infer<typeof formSchema>) => void
}

export const terminationsTypes = [
    'Redução de efetivo',
    'Demissão por desempenho',
    'Justa causa',
    'Pedido de demissão',
    'Acordo legal'
]

const TerminationForm = (props: TerminationFormProps) => {
    const { onCreate } = props
    const { user, token } = useContext(UserContext)
    const [sending, setSending] = useState(false)
    const [employeesData, setEmployeesData] = useState<any>(null)

    useEffect(() => {
        if (!token) return console.log("Not logged in")
        try {
            const fetchResources = async () => {
                const res = await fetch(`${process.env.API_HOST}/employee/filter/false`, {
                    headers: {
                        'Authorization': token,
                    }
                })
                const data = await res.json()
                setEmployeesData(data)
            }
            fetchResources()
        } catch (error) {
            console.log(error)
        }
    }, [])


    const { toast } = useToast()

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
    })

    const onSubmit = async (data: z.infer<typeof formSchema>) => {
        if (!user || !token) return toast({
            title: "Você precisa estar logado para pedir rescisão",
            variant: "destructive",
        })

        setSending(true)
        try {
            const res = await fetch(`${process.env.API_HOST}/terminationRequest`, {
                method: "POST",
                body: JSON.stringify({
                    ...data,
                    terminationDate: data.terminationDate.toISOString().split('T')[0],
                    employee: user.id
                }),
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json'
                },
                redirect: 'follow'
            })
            if (res.status !== 200) throw new Error("Erro ao cadastrar rescição")
            const termination = await res.json();
            form.reset()
            onCreate?.(termination)
            toast({
                title: "Recisão cadastrado com sucesso",
            })
        } catch (error) {
            console.log(error)
            toast({
                title: "Erro ao cadastrar recisão",
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
                    name="targetEmployee"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Funcionário</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={employeesData?.map((state: any) => ({
                                    label: state.name,
                                    value: state.id
                                }))}
                                    onChange={(option) => {
                                        field.onChange(option?.value)
                                    }}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                 <FormField
                    control={form.control}
                    name="terminationDate"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Data</FormLabel>
                            <FormControl>
                                <DatePicker {...field}/>
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="targetEmployee"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Tipo</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={terminationsTypes.map((state: any, i) => ({
                                    label: state,
                                    value: i
                                }))}
                                    onChange={(option) => {
                                        field.onChange(option?.value)
                                    }}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <Button type="submit" disabled={sending}>{sending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />} Pedir férias</Button>
            </form>
        </Form>
    )
}

export default TerminationForm