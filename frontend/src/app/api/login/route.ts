import { NextResponse } from "next/server"

export async function POST(request: Request) {
  try {
    const res = await request.json()

    const { cpf, password } = res

    const data = await fetch(`${process.env.API_HOST}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ cpf, password })
    })

    const parsedData = await data.json()

    if (parsedData.error) {
      console.log(parsedData.error)
      return NextResponse.error()
    }

    const response = NextResponse.json({ success: true, token: `Bearer ${parsedData.token}` }, { status: 200 })

    response.cookies.set("token", `Bearer ${parsedData.token}`, {
      httpOnly: true,
      secure: false,
      sameSite: "strict",
      path: "/",
    })

    return response
  } catch (error) {
    console.log(error)
    return NextResponse.error()
  }
}