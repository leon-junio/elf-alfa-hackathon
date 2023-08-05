package com.elf.app.models.utils;

public enum CivilStatus {
    SOLTEIRO("Solteiro(a)"),
    CASADO("Casado(a)"),
    DIVORCIADO("Divorciado(a)"),
    VIUVO("Viúvo(a)"),
    SEPARADO("Separado(a)"),
    UNIAO_ESTAVEL("União Estável"),
    CONCURBINATO("Concubinato"),
    OUTROS("Outros");

    private String civilStatus;
    private CivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getCivilStatus() {
        return this.civilStatus;
    }

    public CivilStatus getCivilStatus(int id){
        return CivilStatus.values()[id];
    }
}
