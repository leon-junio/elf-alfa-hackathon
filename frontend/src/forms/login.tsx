import { Button } from "@/components/ui/button";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useToast } from "@/components/ui/use-toast";
import { UserContext } from "@/components/user-provider";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { useRouter } from "next/navigation";
import { useContext, useState } from "react";
import { useForm } from "react-hook-form";
import { useIMask } from "react-imask";
import * as z from "zod";
import formSchema from "../schemas/login";

const LoginForm = () => {
  const { getUser } = useContext(UserContext)
  const router = useRouter()
  const [sending, setSending] = useState(false)

  const {toast} = useToast()

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
  })

  const { ref } = useIMask(
    {
      mask: "000.000.000-00"
    },
  );

  const onSubmit = async (data: z.infer<typeof formSchema>) => {
    setSending(true)
    try {
      const res = await fetch("/api/login", {
        method: "POST",
        body: JSON.stringify(data),
      })
      if(res.status !== 200) throw new Error("Erro ao efetuar login")

      const tokenData = await res.json()
      const success = await getUser(tokenData.token)
      if(!success) throw new Error("Erro ao efetuar login")
      toast({
        title: "Login efetuado com sucesso",
      })
      router.push("/internal/candidates")
    } catch (error) {
      console.log(error)
      toast({
        title: "Erro ao efetuar login",
        description: "Verifique suas credenciais e tente novamente",
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
          name="cpf"
          render={({ field }) => (
            <FormItem>
              <FormLabel>CPF</FormLabel>
              <FormControl>
                <Input placeholder="Digite seu CPF" {...field} ref={ref as React.RefObject<HTMLInputElement>} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Senha</FormLabel>
              <FormControl>
                <Input type="password" placeholder="Digite sua senha" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit" disabled={sending}>{sending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />} Entrar</Button>
      </form>
    </Form>
  )
}

export default LoginForm