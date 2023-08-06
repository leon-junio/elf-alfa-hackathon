import Image from "next/image"
import { ThemeToggle } from "./theme-toggle"

const Header = () => {

    return (
        <header className="relative border border-l-0 border-r-0 w-100 p-3 flex items-center justify-center">
            <div className="flex flex-1 justify-center items-center">
                <Image
                    className="hidden md:block"
                    src="/logo.png" alt="Logo" width={150} height={150} />
                <Image
                    className="md:hidden"
                    src="/favicon.png" alt="Logo" width={50} height={50} />
            </div>
            <ThemeToggle />
        </header>
    )
}

export default Header