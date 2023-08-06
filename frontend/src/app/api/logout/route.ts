import { NextResponse } from "next/server";

export async function GET() {
    const res = NextResponse.json({ success: true }, { status: 200 })

    res.cookies.delete("token")

    return res
}