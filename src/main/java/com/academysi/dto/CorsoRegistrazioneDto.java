package com.academysi.dto;

public class CorsoRegistrazioneDto {

    private String nomeCorso;
    private String descrizioneBreve;
    private String descrizioneCompleta;
    private int durata;
    private int categoriaId;

    public String getNomeCorso() {
        return nomeCorso;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public String getDescrizioneBreve() {
        return descrizioneBreve;
    }

    public void setDescrizioneBreve(String descrizioneBreve) {
        this.descrizioneBreve = descrizioneBreve;
    }

    public String getDescrizioneCompleta() {
        return descrizioneCompleta;
    }

    public void setDescrizioneCompleta(String descrizioneCompleta) {
        this.descrizioneCompleta = descrizioneCompleta;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
}
