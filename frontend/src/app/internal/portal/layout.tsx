"use client"

import SignoutButton from '@/components/signout-button';
import { Button } from '@/components/ui/button';
import { ClipboardList, Flag, Shapes, UserCheck2 } from 'lucide-react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';

const Layout = ({
    children,
  }: {
    children: React.ReactNode
  }) => {
    const pathname = usePathname();

    return (
        <div className="flex max-w-7xl mx-auto h-full w-full">
            <aside className="h-full flex flex-col border border-t-0 border-l-0 border-b-0 p-4 space-y-4">
                <Link href="/internal/portal/candidates">
                    <Button variant={pathname === "/internal/portal/candidates" ? "default" : "outline"} className="w-full">
                        <ClipboardList className="w-4 h-4 mr-2" /> Candidatos
                    </Button>
                </Link>
                <Link href="/internal/portal/solicitations">
                    <Button variant={pathname === "/internal/portal/solicitations/" ? "default" : "outline"} className="w-full">
                        <UserCheck2 className="w-4 h-4 mr-2" /> Solicitações
                    </Button>
                </Link>
                <Link href="/internal/portal/reports">
                    <Button variant={pathname === "/internal/portal/reports" ? "default" : "outline"} className="w-full">
                        <Flag className="w-4 h-4 mr-2" /> Relatórios
                    </Button>
                </Link>
                <Link href="/internal/portal/resources">
                    <Button variant={pathname === "/internal/portal/resources" ? "default" : "outline"} className="w-full">
                        <Shapes className="w-4 h-4 mr-2" /> Áreas/Equipamentos
                    </Button>
                </Link>
                <div className="flex-1"></div>
                <SignoutButton />
            </aside>
            <section className="flex-1 p-4">
                {children}
            </section>
        </div>
    )
}

export default Layout;