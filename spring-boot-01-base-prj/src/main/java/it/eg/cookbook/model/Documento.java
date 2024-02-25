package it.eg.cookbook.model;

import java.time.LocalDate;

public class Documento {

    private Long id;
    private String nome;
    private String descrizione;
    private LocalDate data;

    public Documento(Long id, String nome, String descrizione, LocalDate data) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}

