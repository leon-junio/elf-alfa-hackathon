import { isCEP, isCPF, isPhone } from 'brazilian-values';
import * as z from 'zod';
import fileSchema from './file';

enum CivilStatus {
    Solteiro,
    Casado,
    Divorciado,
    Viuvo,
    Separado,
    UniãoEstável,
    Concubinato,
    Outro,
}

enum SchoolingType {
    FundamentalIncompleto,
    FundamentalCompleto,
    MédioIncompleto,
    MédioCompleto,
    SuperiorIncompleto,
    SuperiorCompleto,
    PósGraduaçãoIncompleta,
    PósGraduaçãoCompleta,
    MestradoIncompleto,
    MestradoCompleto,
    DoutoradoIncompleto,
    DoutoradoCompleto,
    PósDoutoradoIncompleto,
    PósDoutoradoCompleto,
}

enum PublicAreaType {
    Avenida,
    Rua,
    Travessa,
    Estrada,
    Rodovia,
    Alameda,
    Beco,
    Largo,
    Largueta,
    Loteamento,
    Parque,
    Praça,
    Quadra,
    Outro
}

const dependantSchema = z.object({
    cpf: z.string().min(1).refine(isCPF, {
        message: 'CPF inválido',
    }),
    gender: z.boolean(),
    name: z.string().min(1),
    birthday: z.date(),
    relationship: z.string().min(1),
})

const employeeSchema = z.object({
    name: z.string().min(1),
    motherName: z.string().min(1),
    fatherName: z.string().min(1),
    gender: z.boolean(),
    civilStatus: z.nativeEnum(CivilStatus),
    schoolingType: z.nativeEnum(SchoolingType),
    birthday: z.date(),
    nationality: z.string().min(1),
    countryBirth: z.string().min(1),
    stateBirth: z.string().min(1),
    cityBirth: z.string().min(1),
    shoeSize: z.number().min(1),
    pantsSize: z.number().min(1),
    shirtSize: z.string().min(1),
    phoneNumber1: z.string().min(1).refine(isPhone, {
        message: 'Telefone inválido',
    }),
    phoneNumber2: z.string().min(1).refine(isPhone, {
        message: 'Telefone inválido',
    }).optional(),
    email: z.string().email(),
    address: z.string().min(1),
    number: z.string().min(1),
    complement: z.string().min(1),
    neighbor: z.string().min(1),
    city: z.string().min(1),
    state: z.string().min(1),
    cep: z.string().min(1).refine(isCEP, {
        message: 'CEP inválido',
    }),
    country: z.string().min(1),
    publicAreaType: z.nativeEnum(PublicAreaType),
    rg: z.string().min(1),
    rgIssuer: z.string().min(1),
    rgIssuerState: z.string().min(1),
    rgIssuerCity: z.string().min(1),
    rgExpeditionDate: z.date(),
    cpf: z.string().min(1).refine(isCPF, {
        message: 'CPF inválido',
    }),
    pis: z.string().min(1),
    role: z.string().uuid(),
    pcd: z.boolean(),
    hosted: z.boolean(),
    fileRgPath: fileSchema,
    fileCpfPath: fileSchema,
    fileCvPath: fileSchema,
    fileCnhPath: fileSchema.optional(),
    fileReservistPath: fileSchema.optional(),
    hasFriend: z.boolean(),
    friendName: z.string().min(1).optional(),
    friendRole: z.string().min(1).optional(),
    friendCity: z.string().min(1).optional(),
    candidate: z.boolean().optional(),
    employeeStatus: z.enum([
        'Contratado',
        'Demitido',
        'Afastado',
        'Férias',
        'Aposentado',
        'Suspenso',
        'Candidato'
    ]).optional(),
    dependants: z.array(dependantSchema),
}).refine(data => {
    if (data.hasFriend) {
        return data.friendName && data.friendRole && data.friendCity;
    }
    return true;
}, "Se o funcionário tem amigo, é necessário preencher os campos de nome, cargo e cidade do amigo.")

export default employeeSchema;