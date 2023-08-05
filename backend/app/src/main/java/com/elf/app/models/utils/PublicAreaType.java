package com.elf.app.models.utils;

public enum PublicAreaType {
    AVENIDA, RUA, TRAVESSA, ESTRADA, RODOVIA, ALAMEDA,
    BECO, LARGO, LARGUETA, LOTEAMENTO, PARQUE, PRACA,
    QUADRA, OUTROS;

    public static PublicAreaType getPublicAreaType(int id){
        return PublicAreaType.values()[id];
    }
}
