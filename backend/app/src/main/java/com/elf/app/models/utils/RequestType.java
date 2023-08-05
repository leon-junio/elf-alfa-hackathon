package com.elf.app.models.utils;

public enum RequestType {
    RECISAO,
    FERIAS;

    /**
     * Busca o valor do nome do tipo de solicitação
     */
    public static RequestType getRequestType(int id){
        return RequestType.values()[id];
    }
}
