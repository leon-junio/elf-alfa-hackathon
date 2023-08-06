"use client"
import { useToast } from "@/components/ui/use-toast"
import { UserContext } from "@/components/user-provider"
import VacationForm from "@/forms/termination"
import { columns } from "@/tables/termination/columns"
import { DataTable } from "@/tables/termination/data-table"
import { Loader2 } from "lucide-react"
import { useContext, useEffect, useState } from "react"

const Page = () => {
    const { token } = useContext(UserContext)
    const [terminationData, setTerminationData] = useState<any>(null)

    const { toast } = useToast()

    const onCreate = (data: any) => {
        setTerminationData((prev: any) => [...prev, data])
    }

    useEffect(() => {
        if (!token) return console.log("Not logged in")
        try {
            const fetchResources = async () => {
                const res = await fetch(`${process.env.API_HOST}/terminationRequest`, {
                    headers: {
                        'Authorization': token,
                    }
                })
                const data = await res.json()
                setTerminationData(data)
            }
            fetchResources()
        } catch (error) {
            console.log(error)
        }
    }, [])

    return (
        <div className="flex w-full space-x-4">
            <div className="w-1/3 border rounded-md p-3 h-min">
                <VacationForm
                    onCreate={onCreate}
                />
            </div>
            <div className="flex-1">
                {terminationData ? (
                    <DataTable columns={columns} data={terminationData} />
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