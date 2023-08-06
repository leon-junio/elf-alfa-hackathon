"use client"
import MapChart from "@/components/map"
import { UserContext } from "@/components/user-provider"
import { columns } from "@/tables/reports/columns"
import { DataTable } from "@/tables/reports/data-table"
import { Loader2 } from "lucide-react"
import { useContext, useEffect, useState } from "react"

const Page = () => {
    const { token } = useContext(UserContext)
    const [reportsData, setReportsData] = useState<any>(null)

    const marks = reportsData?.map((report: any) => ({
        latitude: report.latitude,
        longitude: report.longitude,
    }))


    useEffect(() => {
        if (!token) return console.log("Not logged in")
        try {
            const fetchResources = async () => {
                const res = await fetch(`${process.env.API_HOST}/report`, {
                    headers: {
                        'Authorization': token,
                    }
                })
                const data = await res.json()
                setReportsData(data)
            }
            fetchResources()
        } catch (error) {
            console.log(error)
        }
    }, [])

    return (
        <div className="flex flex-col w-full space-y-4">
            <div className="flex-1 ">
                {reportsData ? (
                    <DataTable columns={columns} data={reportsData} />
                ) : (
                    <div className="flex flex-col items-center justify-center h-full">
                        <Loader2 className="w-4 h-4 animate-spin mr-2" /> Carregando...
                    </div>
                )}
            </div>
            <div className="p-4 space-y-2">
                <h1 className="text-3xl font-bold leading-tight tracking-tighter md:text-5xl lg:leading-[1.1]">
                    Mapa de calor
                </h1>
                {marks && <MapChart marks={marks} />}
            </div>
        </div>
    )
}

export default Page