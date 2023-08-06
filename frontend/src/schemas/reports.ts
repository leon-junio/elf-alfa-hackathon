import { isCEP, isCPF, isPhone } from 'brazilian-values';
import * as z from 'zod';
import fileSchema from './file';

const reportsSchema = z.object({
    name: z.string().min(1),
    resource: z.string().min(1),
    role: z.string().min(1),
    employee: z.string().min(1),
    ocurrenceDescription: z.string().min(1),
    latitude: z.string().min(1),
    longitude: z.string().min(1),
    pictures: z.array(fileSchema)
})

export default reportsSchema;