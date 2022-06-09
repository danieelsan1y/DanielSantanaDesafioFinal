package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.Uf;

import java.io.Serializable;

public class UfDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer codigoUF;
    private String sigla;
    private String nome;
    private Integer status;

    public UfDTO() {
    }

    public UfDTO(Integer codigoUF, String sigla, String nome, Integer status) {
        this.codigoUF = codigoUF;
        this.sigla = sigla;
        this.nome = nome;
        this.status = status;
    }

    public UfDTO(Uf uf) {
        this.codigoUF = uf.getCodigoUf();
        this.sigla = uf.getSigla();
        this.nome = uf.getNome();
        this.status = uf.getStatus();
    }

    public Integer getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Integer codigoUF) {
        this.codigoUF = codigoUF;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
