"use client"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"
import resourceSchema from "@/schemas/resource"
import { ColumnDef } from "@tanstack/react-table"
import { ExternalLink, QrCode } from "lucide-react"
import Link from "next/link"
import QRCode from "react-qr-code"
import * as z from "zod"

export type Resource = z.infer<typeof resourceSchema> & { id: string }

export const columns: ColumnDef<Resource>[] = [
  {
    accessorKey: "description",
    header: "Descrição",
  },
  {
    accessorKey: "isAvailable",
    header: "Disponível",
    cell: ({ row, }) => {
      const isAvailable = row.original.isAvailable

      return (
        <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${isAvailable ? "bg-green-100 text-green-800" : "bg-red-100 text-red-800"}`}>
          {isAvailable ? "Sim" : "Não"}
        </span>
      )
    }
  },
  {
    header: "Compartilhar",
    cell: ({ row, }) => {
      const id = row.original.id
      const link = `${window.location.origin}/resource/${id}`

      return (
        <Popover>
          <PopoverTrigger>
            <Button variant="ghost">
              <QrCode className="w-4 h-4" />
            </Button>
          </PopoverTrigger>
          <PopoverContent className="w-80">
            <div className="grid gap-4">
              <div className="space-y-2">
                <h4 className="font-medium leading-none">Compartilhar recurso</h4>
                <p className="text-sm text-muted-foreground">
                  Copie o link ou o QR Code abaixo para compartilhar o recurso
                </p>
              </div>
              <div className="grid gap-2">
                <div className="grid grid-cols-3 items-center gap-4">
                  <Label htmlFor="width">Link</Label>
                  <Input
                    id="width"
                    defaultValue={link}
                    className="col-span-2 h-8"
                    readOnly
                  />
                </div>
              </div>
              <QRCode
                size={256}
                className="border-8 border-white"
                style={{ height: "auto", maxWidth: "100%", width: "100%" }}
                value={link}
                viewBox={`0 0 256 256`}
              />
            </div>
          </PopoverContent>
        </Popover>
      )
    }
  },
  {
    accessorKey: "filePath",
    header: "Documento",
    cell: ({ row, }) => {
      const id = row.original.id
      const link = `${process.env.API_HOST}/resource/documents/${id}`

      return (
        <Link href={link} target="_blank">
          <Button variant="outline">
            <ExternalLink className="w-4 h-4 mr-2" /> Ver documento
          </Button>
        </Link>
      )
    }
  },
]
