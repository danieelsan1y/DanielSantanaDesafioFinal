package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.Municipio;
import java.io.Serializable;

public class MunicipioDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer codigoMunicipio;
    private Integer codigoUF;
    private String nome;
    private Integer status;



    public MunicipioDTO(Integer codigoMunicipio, Integer codigoUF, String nome, Integer status) {
        this.codigoMunicipio = codigoMunicipio;
        this.codigoUF = codigoUF;
        this.nome = nome;
        this.status = status;

    }

    public MunicipioDTO(Municipio municipio) {
        this.codigoMunicipio = municipio.getCodigoMunicipio();
        this.codigoUF = municipio.getUf().getCodigoUf();
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
        return codigoUF;
    }

    public void setCodigoUf(Integer codigoUF) {
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

}
