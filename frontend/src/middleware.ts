// If not logged in (token cookie), redirect to login page
// Only applies to pages /internal/*

import { NextRequest, NextResponse } from "next/server";

export async function middleware(request: NextRequest) {
    const { pathname } = new URL(request.url);
    const headers = new Headers(request.headers)
    headers.set("x-pathname", request.nextUrl.pathname)

    if (pathname.startsWith("/internal") && !pathname.startsWith("/internal/login") && !request.cookies.get('token')) {
        return NextResponse.redirect(new URL("/internal/login", request.url), {
            headers: headers
        })
    } else if (pathname.startsWith("/internal") && pathname.startsWith("/internal/login") && request.cookies.get('token')) {
        return NextResponse.redirect(new URL("/internal/portal/candidates", request.url), {
            headers: headers
        })
    }


    return NextResponse.next({
        headers: headers
    });
}
