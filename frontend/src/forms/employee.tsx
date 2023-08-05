import { Button } from "@/components/ui/button";
import { Combobox } from "@/components/ui/combobox";
import { DatePicker } from "@/components/ui/datepicker";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2, Trash } from "lucide-react";
import { useState } from "react";
import { useFieldArray, useForm, type Control, type FieldArrayWithId, type UseFieldArrayRemove } from "react-hook-form";
import { useIMask } from "react-imask";
import * as z from "zod";
import formSchema from "../schemas/employee";

const DependantForm = (props: { field: FieldArrayWithId<z.infer<typeof formSchema>, "dependants", "id">, index: number, control: Control<z.infer<typeof formSchema>>, remove: UseFieldArrayRemove }) => {
    const { field, index, control, remove } = props
    const { ref: cpfRef } = useIMask(
        {
            mask: "000.000.000-00"
        },
    );

    return (
        <div key={field.id} className="flex-col space-y-1 border rounded-md p-2">
            <FormField
                control={control}
                name={`dependants.${index}.cpf`}
                render={({ field }) => (
                    <FormItem>
                        <FormLabel>CPF</FormLabel>
                        <FormControl>
                            <Input placeholder="Digite o CPF" {...field} ref={cpfRef as React.RefObject<HTMLInputElement>} />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />
            <FormField
                control={control}
                name={`dependants.${index}.name`}
                render={({ field }) => (
                    <FormItem>
                        <FormLabel>Nome</FormLabel>
                        <FormControl>
                            <Input placeholder="Digite o nome" {...field} />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />
            <FormField
                control={control}
                name={`dependants.${index}.gender`}
                render={({ field }) => (
                    <FormItem className="flex flex-col">
                        <FormLabel>Sexo</FormLabel>
                        <FormControl>
                            <Combobox {...field} options={[
                                { label: 'Masculino', value: '1' },
                                { label: 'Feminino', value: '0' },
                            ]}
                                onChange={(option) => {
                                    field.onChange(Boolean(Number(option?.value)))
                                }}
                            />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />
            <FormField
                control={control}
                name={`dependants.${index}.relationship`}
                render={({ field }) => (
                    <FormItem>
                        <FormLabel>Relacionamento</FormLabel>
                        <FormControl>
                            <Input placeholder="Digite a relação" {...field} />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                )}
            />

            <Button
                type="button"
                variant="destructive"
                size="sm"
                className="mt-2"
                onClick={() => remove(index)}
            >
                <Trash className="mr-2 h-4 w-4" /> Remover dependente
            </Button>
        </div>
    )
}

const EmployeeForm = () => {
    const [sending, setSending] = useState(false)

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
    })

    const { fields, append, remove } = useFieldArray({
        name: "dependants",
        control: form.control,
    })

    const { ref: cpfRef } = useIMask(
        {
            mask: "000.000.000-00"
        },
    );
    const { ref: phone1Ref } = useIMask(
        {
            mask: "(00) 00000-0000"
        },
    );
    const { ref: phone2Ref } = useIMask(
        {
            mask: "(00) 00000-0000"
        },
    );
    const { ref: cepRef } = useIMask(
        {
            mask: "00000-000"
        },
    );
    const { ref: rgRef } = useIMask(
        {
            mask: "00.000.000-0"
        },
    );

    const watchHasFriend = form.watch('hasFriend')

    const onSubmit = async (data: z.infer<typeof formSchema>) => {
        setSending(true)
        try {
            const formData = new FormData()

            Object.entries(data).forEach(([key, value]) => {
                console.log(key, value)
                if (key === 'dependants') {
                    // @ts-ignore
                    value.forEach((dependant, index) => {
                        Object.entries(dependant).forEach(([dependantKey, dependantValue]) => {
                            // @ts-ignore
                            formData.append(`dependants[${index}][${dependantKey}]`, dependantValue)
                        })
                    })
                    // @ts-ignore
                } else if (value.file) {
                    // @ts-ignore
                    formData.append(key, value.file)
                }
                else {
                    // @ts-ignore
                    formData.append(key, value)
                }
            })
            
            await fetch(`${process.env.API_HOST}/external/employee`, {
                method: "POST",
                body: formData,
            })
        } catch (error) {
            console.error(error)
        } finally {
            setSending(false)
        }
    }

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
                <FormField
                    control={form.control}
                    name="name"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Nome</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu nome" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="motherName"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Nome da mãe</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o nome da sua mãe" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="fatherName"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Nome do pai</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o nome do seu pai" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="gender"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Sexo</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={[
                                    { label: 'Masculino', value: '1' },
                                    { label: 'Feminino', value: '0' },
                                ]}
                                    onChange={(option) => {
                                        field.onChange(Boolean(Number(option?.value)))
                                    }}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="civilStatus"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Estado civil</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={[
                                    { label: 'Solteiro', value: 0 },
                                    { label: 'Casado', value: 1 },
                                    { label: 'Divorciado', value: 2 },
                                    { label: 'Viúvo', value: 3 },
                                ]} onChange={(option) => {
                                    field.onChange(option?.value)
                                }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="schoolingType"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Escolaridade</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={[
                                    'Fundamental Incompleto',
                                    'Fundamental Completo',
                                    'Médio Incompleto',
                                    'Médio Completo',
                                    'Superior Incompleto',
                                    'Superior Completo',
                                    'Pós-Graduação Incompleta',
                                    'Pós-Graduação Completa',
                                    'Mestrado Incompleto',
                                    'Mestrado Completo',
                                    'Doutorado Incompleto',
                                    'Doutorado Completo',
                                    'Pós-Doutorado Incompleto',
                                    'Pós-Doutorado Completo',
                                ].map((str, i) => ({ value: i, label: str }))}
                                    onChange={(option) => {
                                        field.onChange(option?.value)
                                    }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="birthday"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Data de nascimento</FormLabel>
                            <FormControl>
                                <DatePicker {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="nacionality"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Nacionalidade</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite sua nacionalidade" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="countryBirth"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>País de nascimento</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu país de nascimento" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="stateBirth"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Estado de nascimento</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu estado de nascimento" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="cityBirth"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Cidade de nascimento</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite sua cidade de nascimento" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="shoeSize"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Tamanho do calçado</FormLabel>
                            <FormControl>
                                <Input type="number" min={0} placeholder="Digite seu tamanho de calçado" {...field} onChange={(e) => {
                                    field.onChange(Number(e.target.value))
                                }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="pantsSize"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Tamanho da calça</FormLabel>
                            <FormControl>
                                <Input type="number" min={0} placeholder="Digite seu tamanho de calça" {...field} onChange={(e) => {
                                    field.onChange(Number(e.target.value))
                                }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="shirtSize"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Tamanho da camisa</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu tamanho de camisa" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="phoneNumber1"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Telefone 1</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu telefone 1" {...field} ref={phone1Ref as React.RefObject<HTMLInputElement>} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="phoneNumber2"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Telefone 2</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu telefone 2" {...field} ref={phone2Ref as React.RefObject<HTMLInputElement>} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="email"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>E-mail</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu e-mail" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="address"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Rua</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite sua rua" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="number"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Número</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o número" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="complement"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Complemento</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o complemento" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="neighbor"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Bairro</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o bairro" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="city"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Cidade</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite a cidade" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="state"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Estado</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o estado" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="cep"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>CEP</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o CEP" {...field} ref={cepRef as React.RefObject<HTMLInputElement>} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="country"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>País</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o país" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="publicAreaType"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Tipo de logradouro</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={[
                                    { label: "Avenida", value: 0 },
                                    { label: "Rua", value: 1 },
                                    { label: "Travessa", value: 2 },
                                    { label: "Estrada", value: 3 },
                                    { label: "Rodovia", value: 4 },
                                    { label: "Alameda", value: 5 },
                                    { label: "Beco", value: 6 },
                                    { label: "Largo", value: 7 },
                                    { label: "Largueta", value: 8 },
                                    { label: "Loteamento", value: 9 },
                                    { label: "Parque", value: 10 },
                                    { label: "Praça", value: 11 },
                                    { label: "Quadra", value: 12 },
                                    { label: "Outro", value: 13 },
                                ]} onChange={(option) => {
                                    field.onChange(option?.value)
                                }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="rg"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>RG</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu RG" {...field} ref={rgRef as React.RefObject<HTMLInputElement>} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="rgIssuer"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Órgão emissor</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o órgão emissor do RG" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="rgIssuerState"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Estado do órgão emissor</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite o estado do órgão emissor do RG" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="rgExpeditionDate"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Data de expedição</FormLabel>
                            <FormControl>
                                <DatePicker {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="cpf"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>CPF</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu CPF" {...field} ref={cpfRef as React.RefObject<HTMLInputElement>} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="pis"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>PIS</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu PIS" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="role"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Cargo</FormLabel>
                            <FormControl>
                                <Input placeholder="Digite seu cargo" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="pcd"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>PCD</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={[
                                    { label: 'Sim', value: '1' },
                                    { label: 'Não', value: '0' },
                                ]}
                                    onChange={(option) => {
                                        field.onChange(Boolean(Number(option?.value)))
                                    }}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="hosted"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Alojado</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={[
                                    { label: "Sim", value: '1' },
                                    { label: "Não", value: '0' },
                                ]} onChange={(option) => {
                                    field.onChange(Boolean(Number(option?.value)))
                                }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="fileRgPath"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>RG</FormLabel>
                            <FormControl>
                                <Input type="file" {...field}
                                    value={field.value?.name}
                                    onChange={(e) => {
                                        const value = {
                                            name: e.target.value,
                                            file: e.target.files?.[0]
                                        }
                                        field.onChange(value)
                                    }}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="fileCpfPath"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>CPF</FormLabel>
                            <FormControl>
                                <Input type="file" {...field}
                                    value={field.value?.name}
                                    onChange={(e) => {
                                        const value = {
                                            name: e.target.value,
                                            file: e.target.files?.[0]
                                        }
                                        field.onChange(value)
                                    }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="fileCvPath"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Curriculo</FormLabel>
                            <FormControl>
                                <Input type="file" {...field}
                                    value={field.value?.name}
                                    onChange={(e) => {
                                        const value = {
                                            name: e.target.value,
                                            file: e.target.files?.[0]
                                        }
                                        field.onChange(value)
                                    }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="fileCnhPath"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>CNH</FormLabel>
                            <FormControl>
                                <Input type="file" {...field}
                                    value={field.value?.name}
                                    onChange={(e) => {
                                        const value = {
                                            name: e.target.value,
                                            file: e.target.files?.[0]
                                        }
                                        field.onChange(value)
                                    }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="fileReservistPath"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Reservista</FormLabel>
                            <FormControl>
                                <Input type="file" {...field}
                                    value={field.value?.name}
                                    onChange={(e) => {
                                        const value = {
                                            name: e.target.value,
                                            file: e.target.files?.[0]
                                        }
                                        field.onChange(value)
                                    }} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="hasFriend"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel>Você tem algum amigo na empresa</FormLabel>
                            <FormControl>
                                <Combobox {...field} options={[
                                    { label: 'Sim', value: '1' },
                                    { label: 'Não', value: '0' },
                                ]}
                                    onChange={(option) => {
                                        field.onChange(Boolean(Number(option?.value)))
                                    }}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                {
                    watchHasFriend && (
                        <>
                            <FormField
                                control={form.control}
                                name="friendName"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Nome do amigo</FormLabel>
                                        <FormControl>
                                            <Input placeholder="Digite o nome do amigo" {...field} />
                                        </FormControl>
                                        <FormMessage />
                                    </FormItem>
                                )}
                            />
                            <FormField
                                control={form.control}
                                name="friendRole"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Cargo do amigo</FormLabel>
                                        <FormControl>
                                            <Input placeholder="Digite o cargo do amigo" {...field} />
                                        </FormControl>
                                        <FormMessage />
                                    </FormItem>
                                )}
                            />
                            <FormField
                                control={form.control}
                                name="friendCity"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Telefone do amigo</FormLabel>
                                        <FormControl>
                                            <Input placeholder="Digite a cidade do amigo" {...field} />
                                        </FormControl>
                                        <FormMessage />
                                    </FormItem>
                                )}
                            />
                        </>
                    )
                }
                <div className="flex-col space-y-2">
                    {
                        fields.map((field, index) => (
                            <DependantForm key={field.id} field={field} index={index} control={form.control} remove={remove} />
                        ))
                    }
                    <Button
                        type="button"
                        variant="outline"
                        size="sm"
                        className="mt-2"
                        onClick={() => append({ cpf: '', gender: false, birthday: new Date(), name: '', relationship: '' })}
                    >
                        Adicionar dependente
                    </Button>
                </div>
                <Button type="submit" disabled={sending}>{sending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />} Candidatar</Button>
            </form>
        </Form>
    )
}

export default EmployeeForm