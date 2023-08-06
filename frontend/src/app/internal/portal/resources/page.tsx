"use client"
import { useToast } from "@/components/ui/use-toast"
import { UserContext } from "@/components/user-provider"
import ResourceForm from "@/forms/resource"
import { columns } from "@/tables/resources/columns"
import { DataTable } from "@/tables/resources/data-table"
import { Loader2 } from "lucide-react"
import { useContext, useEffect, useState } from "react"

const Page = () => {
    const { token } = useContext(UserContext)
    const [resourcesData, setResourcesData] = useState<any>(null)

    const { toast } = useToast()

    const onCreate = (data: any) => {
        const newResource = {
            ...data,
            isAvailable: Boolean(data.isAvailable),
            filePath: data.filePath ? data.filePath.name : null,
        }
        setResourcesData((prev: any) => [...prev, data])
    }

    useEffect(() => {
        if (!token) return console.log("Not logged in")
        try {
            const fetchResources = async () => {
                const res = await fetch(`${process.env.API_HOST}/resource`, {
                    headers: {
                        'Authorization': token,
                    }
                })
                const data = await res.json()
                setResourcesData(data)
            }
            fetchResources()
        } catch (error) {
            console.log(error)
        }
    }, [])

    return (
        <div className="flex w-full space-x-4">
            <div className="flex-1 border rounded-md p-3 h-min">
                <ResourceForm
                    onCreate={onCreate}
                />
            </div>
            <div className="flex-1">
                {resourcesData ? (
                    <DataTable columns={columns} data={resourcesData} />
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