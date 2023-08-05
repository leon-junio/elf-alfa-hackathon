package com.elf.app.models.utils;

public enum RequestStatusType {
    REPROVADO, AGUARDANDO, APROVADO;

    public static RequestStatusType getRequestStatusType(int id){
        return RequestStatusType.values()[id];
    }
}
