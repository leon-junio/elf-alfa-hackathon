"use client";

import type { PermitModel, UserModel } from "@/app/interfaces/request-models";
import { useRouter } from "next/navigation";
import { createContext, useEffect, useState } from "react";
import { useToast } from "./ui/use-toast";


export const UserContext = createContext<{
    user: UserModel | undefined;
    token: string | undefined;
    getUser: (token?: string) => Promise<boolean> | void;
    logout: () => Promise<boolean> | void;
    permissions: PermitModel | undefined;
    toast: any;
}>({ user: undefined, getUser: () => { }, logout: () => { }, token: undefined, permissions: undefined, toast: undefined });

export const UserProvider = ({
    children,
    initialToken,
}: {
    children: React.ReactNode;
    initialToken?: string;
}) => {
    const router = useRouter()
    const [token, setToken] = useState<string | undefined>(initialToken)
    const [user, setUser] = useState<UserModel | undefined>(undefined);
    const [permissions, setPermissions] = useState<PermitModel | undefined>(undefined);

    const {toast} = useToast()

    const getUser = async (argToken?: string) => {
        if (argToken === token && user) return true
        if (!argToken) return false
        setToken(argToken)
        try {
            const res = await fetch(`${process.env.API_HOST}/user`, {
                headers: {
                    'Authorization': argToken,
                    'Content-Type': 'application/json',
                    credentials: 'same-origin'
                }
            })
            if (res.status !== 200) throw new Error("Erro ao obter usuário")
            const user = await res.json()
            setUser(user)
            const permissions = await getPermissions(user, argToken)
            if (!permissions) throw new Error("Erro ao obter permissões")
            return true
        } catch (error) {
            removeUser()
            await logout()
            return false
        }
    }

    const getPermissions = async (user: UserModel | undefined = undefined, token: string | undefined = undefined) => {
        if (!user || !token) return false
        const res = await fetch(`${process.env.API_HOST}/permits/employee/${user.id}`, {
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json',
                credentials: 'same-origin'
            }
        })
        if (res.status !== 200) throw new Error("Erro ao obter permissões")
        const permissions = await res.json()
        setPermissions(permissions[0])
        return true
    }

    const removeUser = () => {
        setUser(undefined)
        setToken(undefined)
    }

    const logout = async () => {
        try {
            const res = await fetch("/api/logout")
            if (res.status !== 200) throw new Error("Erro ao efetuar logout")
            removeUser()
            await router.push("/internal/login")
            return true
        } catch (error) {
            console.log(error)
            return false
        }
    }

    useEffect(() => {
        if (initialToken) getUser(initialToken)
    }, [initialToken, token])

    return (
        <UserContext.Provider value={{ user, token, getUser, logout, permissions, toast }}>
            {children}
        </UserContext.Provider>
    );
};
