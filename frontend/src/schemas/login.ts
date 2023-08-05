import { isCPF } from 'brazilian-values';
import * as z from 'zod';

const loginSchema = z.object({
    cpf: z.string().min(14).max(14).refine(isCPF, {
        message: 'CPF inválido',
    }),
    password: z.string().min(6)
});

export default loginSchema;