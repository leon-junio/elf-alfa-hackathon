import { isCPF } from 'brazilian-values';
import * as z from 'zod';
import fileSchema from './file';

const reportsSchema = z.object({
    name: z.string().min(1),
    resource: z.string().min(1),
    role: z.string().min(1),
    employee: z.string().min(1).refine(isCPF, { message: 'CPF inválido' }).optional(),
    ocurrenceDescription: z.string().min(1),
    latitude: z.string().min(1).optional(),
    longitude: z.string().min(1).optional(),
    pictures: z.array(fileSchema).nonempty()
})

export default reportsSchema;