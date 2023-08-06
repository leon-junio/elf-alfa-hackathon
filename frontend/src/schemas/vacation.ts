import * as z from 'zod';

export const vacationSchema = z.object({
    isApproved: z.boolean().nullable().optional(),
    employee: z.string().uuid().optional(),
    vacationStart: z.date(),
    vacationEnd: z.date(),
})

export default vacationSchema;