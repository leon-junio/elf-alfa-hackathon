import * as z from 'zod';

const permitsSchema = z.object({
    employee: z.string().min(1),
    assignPermits: z.boolean(),
    registerUser: z.boolean(),
    viewApplicant: z.boolean(),
    viewReports: z.boolean(),
    requestVacation: z.boolean(),
    requestTermination: z.boolean(),
    requestEmployeeTermination: z.boolean(),
    viewRequests: z.boolean(),
    registerResources: z.boolean(),

})

export default permitsSchema;