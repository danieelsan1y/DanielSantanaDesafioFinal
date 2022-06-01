package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;

import javax.persistence.*;
import java.io.Serializable;

public class MunicipioDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer codigoMunicipio;
    private Integer codigoUf;
    private String nome;
    private Integer status;

    public MunicipioDTO(Integer codigoMunicipio, Integer codigoUf, String nome, Integer status) {
        this.codigoMunicipio = codigoMunicipio;
        this.codigoUf = codigoUf;
        this.nome = nome;
        this.status = status;
    }

    public MunicipioDTO(Municipio municipio) {
        this.codigoMunicipio = municipio.getCodigoMunicipio();
        this.codigoUf = municipio.getUf().getCodigoUf();
        this.nome = municipio.getNome();
        this.status = municipio.getStatus();
    }

    public Integer getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Integer codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public Integer getCodigoUf() {
        return codigoUf;
    }

    public void setCodigoUf(Integer codigoUf) {
        this.codigoUf = codigoUf;
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
