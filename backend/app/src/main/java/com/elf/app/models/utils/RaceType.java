package com.elf.app.models.utils;

public enum RaceType {
    BRANCO, PRETO, PARDO, AMARELO, INDIGENA, OUTROS;

    public static RaceType getRaceType(int id){
        return RaceType.values()[id];
    }
}
