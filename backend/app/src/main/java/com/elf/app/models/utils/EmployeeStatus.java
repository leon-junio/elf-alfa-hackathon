package com.elf.app.models.utils;

public enum EmployeeStatus {
    CONTRATADO,
    DEMITIDO,
    AFASTADO,
    FERIAS,
    LICENCA,
    APOSENTADO,
    SUSPENSO,
    CANDIDATO;

    public static EmployeeStatus getEmployeeStatus(int id){
        return EmployeeStatus.values()[id];
    }
}
