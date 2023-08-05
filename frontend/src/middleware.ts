// If not logged in (token cookie), redirect to login page
// Only applies to pages /internal/*

import { NextRequest, NextResponse } from "next/server";

export async function middleware(request: NextRequest) {
    const { pathname } = new URL(request.url);

    console.log(pathname)
    if (pathname.startsWith("/internal") && !pathname.startsWith("/internal/login") && !request.cookies.get('token')) {
        return NextResponse.redirect(new URL("/internal/login", request.url))
    } else if (pathname.startsWith("/internal") && pathname.startsWith("/internal/login") && request.cookies.get('token')) {
        return NextResponse.redirect(new URL("/internal/dashboard", request.url))
    }

    return NextResponse.next();
}
