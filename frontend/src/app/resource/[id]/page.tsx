import { Button } from "@/components/ui/button"
import { Separator } from "@/components/ui/separator"
import { ExternalLink } from "lucide-react"
import Link from "next/link"

const Page = async ({ params }: { params: { id: string } }) => {
    const data = await fetch(`${process.env.API_HOST}/external/resource/${params.id}`)

    const resource = await data.json()

    const link = `${process.env.API_HOST}/resource/documents/${resource.id}`

    return (
        <div className="h-full w-full p-4 overflow-y-auto">
            <section className="flex max-w-[980px] flex-col items-start gap-2 px-4 pt-8 md:pt-12 page-header pb-8">
                <h1 className="text-3xl font-bold leading-tight tracking-tighter md:text-5xl lg:leading-[1.1] hidden md:block">
                    Recurso: {resource.description}
                </h1>
                <h1 className="text-3xl font-bold leading-tight tracking-tighter md:text-5xl lg:leading-[1.1] md:hidden">Candidatos</h1>
                <p className="max-w-[750px] text-lg text-muted-foreground sm:text-xl">
                    Está disponivel? <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${resource.isAvailable ? "bg-green-100 text-green-800" : "bg-red-100 text-red-800"}`}>
                        {resource.isAvailable ? "Sim" : "Não"}
                    </span>
                </p>
            </section>
            <Separator className="my-2" />
            <div className="flex flex-1 items-center justify-center">
                <Link href={link} target="_blank" className="w-full">
                    <Button variant="outline" className="w-full">
                        <ExternalLink className="w-4 h-4 mr-2" /> Abrir documento
                    </Button>
                </Link>
            </div>
        </div>
    )
}

export default Page