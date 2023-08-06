import { isCPF } from 'brazilian-values';
import * as z from 'zod';

const loginSchema = z.object({
    cpf: z.string().min(14).max(14).refine((arg) => isCPF(arg) || arg === "000.000.000-00", {
        message: 'CPF inválido',
    }),
    password: z.string().min(1)
});

export default loginSchema;