package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.Municipio;

import java.io.Serializable;

public class MunicipioUfDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer codigoMunicipio;
    private Integer codigoUF;
    private String nome;
    private Integer status;
    private UfDTO UF;


    public MunicipioUfDTO(Integer codigoMunicipio, Integer codigoUF, String nome, Integer status) {
        this.codigoMunicipio = codigoMunicipio;
        this.codigoUF = codigoUF;
        this.nome = nome;
        this.status = status;

    }

    public MunicipioUfDTO(Municipio municipio) {
        this.codigoMunicipio = municipio.getCodigoMunicipio();
        this.codigoUF = municipio.getUf().getCodigoUf();
        this.nome = municipio.getNome();
        this.status = municipio.getStatus();
        UF = new UfDTO(municipio.getUf());

    }

    public Integer getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Integer codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public Integer getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Integer codigoUF) {
        this.codigoUF = codigoUF;
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

    public UfDTO getUF() {
        return UF;
    }

    public void setUF(UfDTO UF) {
        this.UF = UF;
    }
}
