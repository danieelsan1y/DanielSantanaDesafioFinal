package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.comrelacionamento;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;

import java.io.Serializable;

public class UfRelacionamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer codigoUf;
    private String sigla;
    private String nome;
    private Integer status;


    public UfRelacionamentoDTO() {
    }

    public UfRelacionamentoDTO(Uf uf) {
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
