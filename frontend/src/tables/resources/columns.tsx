"use client"

import resourceSchema from "@/schemas/resource"
import { ColumnDef } from "@tanstack/react-table"
import * as z from "zod"

export type Resource = z.infer<typeof resourceSchema>

export const columns: ColumnDef<Resource>[] = [
  {
    accessorKey: "description",
    header: "Descrição",
  },
  {
    accessorKey: "isAvailable",
    header: "Autorizado",
    
  },
  {
    accessorKey: "filePath",
    header: "Documento",
  },
]
