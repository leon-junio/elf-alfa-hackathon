"use client"

import { Loader2, LogOut } from "lucide-react"
import { useContext, useState } from "react"
import { Button } from "./ui/button"
import { useToast } from "./ui/use-toast"
import { UserContext } from "./user-provider"

const SignoutButton = () => {
    const { logout } = useContext(UserContext)
    const [loading, setLoading] = useState(false)

    const { toast } = useToast()

    const onClick = async () => {
        setLoading(true)
        try {
            const success = await logout()
            if (!success) throw new Error("Erro ao efetuar logout")
            toast({
                title: "Logout efetuado com sucesso",
            })
        } catch (error) {
            console.log(error)
            toast({
                title: "Erro ao efetuar logout",
                variant: "destructive",
            })
        } finally {
            setLoading(false)
        }
    }

    return (
        <Button variant="destructive" disabled={loading} onClick={onClick}>
            {loading ? <Loader2 className="w-4 h-4 mr-2 animate-spin" /> : <LogOut className="w-4 h-4 mr-2" />} Sair
        </Button>
    )
}

export default SignoutButton