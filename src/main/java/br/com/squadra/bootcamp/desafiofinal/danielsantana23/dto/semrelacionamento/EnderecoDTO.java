package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

public class EnderecoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer codigoEndereco;
    private Integer codigoBairro;
    private String nomeRua;
    private String numero;
    private String complemento;
    private String cep;
    private Integer status;


    public EnderecoDTO(Integer codigoEndereco, Integer codigoBairro, String nomeRua, String numero, String complemento, String cep, Integer status) {
        this.codigoEndereco = codigoEndereco;
        this.codigoBairro = codigoBairro;
        this.nomeRua = nomeRua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.status = status;
    }

    public EnderecoDTO(Endereco endereco) {
        this.codigoEndereco = endereco.getCodigoEndereco();
        this.codigoBairro = endereco.getBairro().getCodigoBairro();
        this.nomeRua = endereco.getNomeRua();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.cep = endereco.getCep();
        this.status = endereco.getStatus();
    }

    public Integer getCodigoEndereco() {
        return codigoEndereco;
    }

    public void setCodigoEndereco(Integer codigoEndereco) {
        this.codigoEndereco = codigoEndereco;
    }

    public Integer getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(Integer codigoBairro) {
        this.codigoBairro = codigoBairro;
    }

    public String getNomeRua() {
        return nomeRua;
    }

    public void setNomeRua(String nomeRua) {
        this.nomeRua = nomeRua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
