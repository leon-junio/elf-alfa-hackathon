"use client"

import reportsSchema from "@/schemas/reports"
import { ColumnDef } from "@tanstack/react-table"
import * as z from "zod"

export type Report = z.infer<typeof reportsSchema> & { id: string }

export const columns: ColumnDef<Report>[] = [
  {
    accessorKey: "name",
    header: "Nome",
  },
  {
    accessorKey: "resource",
    header: "Recurso",
    cell: ({ row, }) => {
      // @ts-ignore
      const value = row.original.resource.description

      return value
    }
  },
  {
    accessorKey: "latitude",
    header: "Latitude",
  },
  {
    accessorKey: "longitude",
    header: "Longitude",
  },
]
