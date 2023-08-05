package com.elf.app.models.utils;

public enum GenderType {
    HOMEM, MULHER, OUTRO;

    public static GenderType getGenderType(int id){
        return GenderType.values()[id];
    }
}
