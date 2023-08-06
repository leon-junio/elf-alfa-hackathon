"use client"
import { UserContext } from "@/components/user-provider"
import { columns } from "@/tables/candidates/columns"
import { DataTable } from "@/tables/candidates/data-table"
import { Loader2 } from "lucide-react"
import { useContext, useEffect, useState } from "react"

const Page = () => {
    const { token } = useContext(UserContext)
    const [candidatesData, setCandidatesData] = useState<any>(null)


    useEffect(() => {
        if (!token) return console.log("Not logged in")
        try {
            const fetchResources = async () => {
                const res = await fetch(`${process.env.API_HOST}/employee/filter/true`, {
                    headers: {
                        'Authorization': token,
                    }
                })
                const data = await res.json()
                setCandidatesData(data)
            }
            fetchResources()
        } catch (error) {
            console.log(error)
        }
    }, [])

    return (
        <div className="flex w-full space-x-4">
            <div className="flex-1">
                {candidatesData ? (
                    <DataTable columns={columns} data={candidatesData} />
                ) : (
                    <div className="flex flex-col items-center justify-center h-full">
                        <Loader2 className="w-4 h-4 animate-spin mr-2" /> Carregando...
                    </div>
                )}
            </div>
        </div>
    )
}

export default Page