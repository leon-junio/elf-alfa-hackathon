import { Button } from "@/components/ui/button";
import { DatePicker } from "@/components/ui/datepicker";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { useToast } from "@/components/ui/use-toast";
import { UserContext } from "@/components/user-provider";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { useContext, useState } from "react";
import { useForm } from "react-hook-form";
import * as z from "zod";
import formSchema from "../schemas/vacation";

interface VacationFormProps {
    onCreate?: (data: z.infer<typeof formSchema>) => void
}

const VacationForm = (props: VacationFormProps) => {
    const { onCreate } = props
    const { user, token } = useContext(UserContext)
    const [sending, setSending] = useState(false)

    const { toast } = useToast()

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
    })

    const onSubmit = async (data: z.infer<typeof formSchema>) => {
        if (!user || !token) return toast({
            title: "Você precisa estar logado para pedir férias",
            variant: "destructive",
        })

        setSending(true)
        try {
            const res = await fetch(`${process.env.API_HOST}/vacationRequest`, {
                method: "POST",
                body: JSON.stringify({
                    ...data,
                    vacationStart: data.vacationStart.toISOString().split('T')[0],
                    vacationEnd: data.vacationEnd.toISOString().split('T')[0],
                    employee: user.id
                }),
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json'
                },
                redirect: 'follow'
            })
            if (res.status !== 200) throw new Error("Erro ao cadastrar férias")
            const vacation = await res.json();
            form.reset()
            onCreate?.(vacation)
            toast({
                title: "Férias cadastrado com sucesso",
            })
        } catch (error) {
            console.log(error)
            toast({
                title: "Erro ao cadastrar férias",
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
                    name="vacationStart"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Inicio</FormLabel>
                            <FormControl>
                                <DatePicker {...field}/>
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="vacationEnd"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Fim</FormLabel>
                            <FormControl>
                                <DatePicker {...field}/>
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

export default VacationForm