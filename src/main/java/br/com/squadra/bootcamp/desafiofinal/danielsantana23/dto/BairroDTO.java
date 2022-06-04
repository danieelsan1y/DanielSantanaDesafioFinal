package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;


public class BairroDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer codigoBairro;
    private Integer codigoMunicipio;
    private String nome;
    private Integer status;




    public BairroDTO(Bairro bairro) {
        this.codigoBairro = bairro.getCodigoBairro();
        this.codigoMunicipio = bairro.getMunicipio().getCodigoMunicipio();
        this.nome = bairro.getNome();
        status = bairro.getStatus();
    }

    public BairroDTO(Integer codigoBairro, Integer codigoMunicipio, String nome, Integer status) {
        this.codigoBairro = codigoBairro;
        this.codigoMunicipio = codigoMunicipio;
        this.nome = nome;
        this.status = status;
    }

    public Integer getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(Integer codigoBairro) {
        this.codigoBairro = codigoBairro;
    }

    public Integer getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Integer codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
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
