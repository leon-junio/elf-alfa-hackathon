package com.elf.app.models.utils;

public enum TerminationType {
    REDUCAO_EFETIVO, DEMISSAO_DESEMPENHO, JUSTA_CAUSA, PEDIDO_DEMISSAO, ACORDO_LEGAL;

    public static TerminationType getTerminationType(int id){
        return TerminationType.values()[id];
    }
}
