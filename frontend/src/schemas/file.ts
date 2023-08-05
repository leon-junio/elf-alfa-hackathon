import * as z from 'zod';

const fileSchema = z.object({
    name: z.string().min(1),
    file: z.instanceof(File)
})

export default fileSchema;