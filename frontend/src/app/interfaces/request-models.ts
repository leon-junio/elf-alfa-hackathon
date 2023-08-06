import employeeSchema from '@/schemas/employee'
import permitsSchema from '@/schemas/permits'
import * as z from 'zod'

export type UserModel = z.infer<typeof employeeSchema> & { id: string }
export type PermitModel = z.infer<typeof permitsSchema> & { id: string }