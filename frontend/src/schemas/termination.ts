import * as z from 'zod';

export const terminationSchema = z.object({
    approved: z.boolean().nullable().optional(),
    employee: z.string().uuid().optional(),
    targetEmployee: z.any(),
    terminationDate: z.date(),
    terminationType: z.any()
})

export default terminationSchema;