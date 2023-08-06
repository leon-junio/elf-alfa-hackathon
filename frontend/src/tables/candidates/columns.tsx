"use client"

import employeeSchema from "@/schemas/employee"
import { ColumnDef } from "@tanstack/react-table"
import * as z from "zod"

export type Employee = z.infer<typeof employeeSchema> & { id: string }

export const columns: ColumnDef<Employee>[] = [
  {
    accessorKey: "name",
    header: "Nome",
  },
  {
    accessorKey: "gender",
    header: "Sexo",
    cell: ({ row, }) => {
      const value = row.original.gender

      return value ? "Masculino" : "Feminino"
    }
  },
  {
    accessorKey: "email",
    header: "Email",
  },
  {
    accessorKey: "phoneNumber1",
    header: "Telefone 1",
  },
  {
    accessorKey: "role",
    header: "Cargo",
    cell: ({ row, }) => {
      // @ts-ignore
      const value = row.original.role.name

      return value
    }
  }
]
