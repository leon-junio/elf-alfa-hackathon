import * as z from 'zod'
import fileSchema from './file'

const resourceSchema = z.object({
    description: z.string(),
    isAvailable: z.boolean().optional(),
    filePath: fileSchema
})

export default resourceSchema