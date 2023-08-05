import { ThemeProvider } from "@/components/theme-provider"
import { cn } from "@/lib/utils"
import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
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

  return (
    <html lang="en">
      <body className={cn(inter.className, "flex-col h-screen w-screen overflow-hidden")}>
        <ThemeProvider attribute="class" defaultTheme="system" enableSystem>
            <main className="p-4 h-full flex-col flex-1 overflow-auto">
              {children}
            </main>
        </ThemeProvider>
      </body>
    </html>
  )
}
