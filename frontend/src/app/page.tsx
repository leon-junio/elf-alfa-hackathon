"use client"

import { Separator } from "@/components/ui/separator";
import EmployeeForm from "@/forms/employee";
import { AlertTriangle } from 'lucide-react';

export default function Page() {
  return (
    <>
      <section className="flex max-w-[980px] flex-col items-start gap-2 px-4 pt-8 md:pt-12 page-header pb-8">
        <h1 className="text-3xl font-bold leading-tight tracking-tighter md:text-5xl lg:leading-[1.1] hidden md:block">
          Cadastro de candidatos
        </h1>
        <h1 className="text-3xl font-bold leading-tight tracking-tighter md:text-5xl lg:leading-[1.1] md:hidden">Candidatos</h1>
        <p className="max-w-[750px] text-lg text-muted-foreground sm:text-xl">
          Preencha o formulário abaixo para se registrar como candidato.
        </p>
        <p className="max-w-[750px] text-lg text-muted-foreground sm:text-xl">
          <AlertTriangle className="inline-block mr-2" />
          O preenchimento dessas informações é de suma importância para o seu prosseguimento no processo seletivo. Todos os campos são
          obrigatórios, então se atente às informações preenchidas
        </p>
      </section>
      <Separator className="my-2" />
      <EmployeeForm/>
    </>
  )
}
