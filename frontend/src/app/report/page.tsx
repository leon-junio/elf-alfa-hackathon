"use client"

import { Separator } from "@/components/ui/separator";
import ReportsForm from "@/forms/reports";

export default function Page() {
  return (
    <div className="h-full w-full p-4 overflow-y-auto">
      <section className="flex max-w-[980px] flex-col items-start gap-2 px-4 pt-8 md:pt-12 page-header pb-8">
        <h1 className="text-3xl font-bold leading-tight tracking-tighter md:text-5xl lg:leading-[1.1] hidden md:block">
          Reportar
        </h1>
        <h1 className="text-3xl font-bold leading-tight tracking-tighter md:text-5xl lg:leading-[1.1] md:hidden">Reportar</h1>
        <p className="max-w-[750px] text-lg text-muted-foreground sm:text-xl">
          Preencha o formulário abaixo para reportar.
        </p>
      </section>
      <Separator className="my-2" />
      <ReportsForm/>
    </div>
  )
}
