package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PessoaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer codigoPessoa;
    private String nome;
    private String sobrenome;
    private String login;
    private String senha;
    private Integer status;

    public PessoaDTO(Pessoa pessoa) {
        this.codigoPessoa = pessoa.getCodigoPessoa();
        this.nome = pessoa.getNome();
        this.sobrenome = pessoa.getSobrenome();
        this.login = pessoa.getLogin();
        this.senha = pessoa.getSenha();
        this.status = pessoa.getStatus();
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}