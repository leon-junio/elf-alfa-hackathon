"use client"

import { Button } from "@/components/ui/button"
import { UserContext } from "@/components/user-provider"
import employeeSchema from "@/schemas/employee"
import { ColumnDef, RowData } from "@tanstack/react-table"
import { Check, SquareSlash, X } from "lucide-react"
import { useContext } from "react"
import * as z from "zod"
export type Employee = z.infer<typeof employeeSchema> & { id: string; status: 0 | 1 | 2 }

declare module '@tanstack/react-table' {
  interface TableMeta<TData extends RowData> {
    updateData: (rowIndex: number, columnId: string, value: unknown) => void
  }
}

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
  }, {
    accessorKey: "status",
    header: "Aprovação",
    cell: ({ row, table }) => {
      const { toast } = useContext(UserContext)
      const value = row.original.status

      const changeStatus = (status: 0 | 1 | 2) => {
        toast({
          title: "Status alterado com sucesso",
        })
       table.options.meta?.updateData(row.index, "status", status)
      }


      return (
        <div className="flex justify-center">
          <Button variant="outline" onClick={() => changeStatus(1)}>
            <X className="h-4 w-4" />
          </Button>
          <Button variant="outline" onClick={() => changeStatus(0)} >
            <SquareSlash className="h-4 w-4" />
          </Button>
          <Button variant="outline" onClick={() => changeStatus(2)}>
            <Check className="h-4 w-4" />
          </Button>
        </div>
      )
    },
  }
]
