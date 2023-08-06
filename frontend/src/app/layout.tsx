import { ThemeProvider } from "@/components/theme-provider"
import Header from "@/components/ui/header"
import { Toaster } from "@/components/ui/toaster"
import { UserProvider } from "@/components/user-provider"
import { cn } from "@/lib/utils"
import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import { cookies, headers } from "next/headers"
import './globals.css'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Alfa',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  const token = cookies().get("token")
  const headersList = headers();
  const pathname = headersList.get("x-pathname")
  const isInternal = pathname?.startsWith("/internal")

  return (
    <html lang="en">
      <body className={cn(inter.className, "flex flex-col h-screen w-screen overflow-hidden")}>
        <UserProvider initialToken={isInternal ? token?.value : undefined}>
          <ThemeProvider attribute="class" defaultTheme="system" enableSystem>
            <Header />
            <main className="flex-col flex-1 overflow-auto">
              {children}
            </main>
            <Toaster />
          </ThemeProvider>
        </UserProvider>
      </body>
    </html>
  )
}
