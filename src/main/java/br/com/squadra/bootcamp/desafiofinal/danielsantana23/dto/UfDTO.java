package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UfDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer codigoUf;
    private String sigla;
    private String nome;
    private Integer status;

    List<MunicipioDTO> municipioDTOS = new ArrayList<>();

    public UfDTO() {
    }

    public UfDTO(Uf uf) {
        this.codigoUf = uf.getCodigoUf();
        this.sigla = uf.getSigla();
        this.nome = uf.getNome();
        this.status = uf.getStatus();
    }

    public Integer getCodigoUf() {
        return codigoUf;
    }

    public void setCodigoUf(Integer codigoUf) {
        this.codigoUf = codigoUf;
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
