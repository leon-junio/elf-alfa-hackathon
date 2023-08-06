"use client"

import { Button } from "@/components/ui/button"
import { UserContext } from "@/components/user-provider"
import terminationSchema from "@/schemas/termination"
import { ColumnDef } from "@tanstack/react-table"
import { Check, SquareSlash, X } from "lucide-react"
import { useContext } from "react"
import * as z from "zod"

export type Termination = z.infer<typeof terminationSchema> & { id: string }

export const columns: ColumnDef<Termination>[] = [
  {
    accessorKey: "targetEmployee",
    header: "Funcionário",
  },
  {
    accessorKey: "terminationDate",
    header: "Data",
  },
  {
    accessorKey: "status",
    header: "Aprovação",
    cell: ({ row, table }) => {
      const {toast} = useContext(UserContext)
      const value = row.original.approved


      return (
        <div className="flex justify-center">
          <Button variant="outline" onClick={() => toast({title:'Salvo com sucesso'})}>
            <X className="h-4 w-4" />
          </Button>
          <Button variant="outline" onClick={() => toast({title:'Salvo com sucesso'})} >
            <SquareSlash className="h-4 w-4" />
          </Button>
          <Button variant="outline" onClick={() => toast({title:'Salvo com sucesso'})} >
            <Check className="h-4 w-4" />
          </Button>
        </div>
      )
    }
  }
]
