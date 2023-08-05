"use client"
import LoginForm from "@/forms/login"

const Page = () => {
    return (
        <div className="h-full w-full flex flex-col space-y-2 items-center justify-center">
            <h1 className="text-3xl font-bold leading-tight tracking-tighter md:text-5xl lg:leading-[1.1]">
                Login
            </h1>
            <LoginForm />
        </div>
    )
}

export default Page