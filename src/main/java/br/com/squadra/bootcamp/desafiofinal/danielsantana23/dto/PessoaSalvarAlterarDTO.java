package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto;

import java.io.Serializable;
import java.util.List;

public class PessoaSalvarAlterarDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer codigoPessoa;
    private String nome;
    private String sobrenome;
    private String login;
    private String senha;
    private Integer status;
    List<EnderecoDTO> enderecos;

    public PessoaSalvarAlterarDTO(String nome, String sobrenome, String login, String senha, Integer status, List<EnderecoDTO> enderecos ) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.login = login;
        this.senha = senha;
        this.status = status;
        this.enderecos = enderecos;
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

    public List<EnderecoDTO> getEnderecosDTO() {
        return enderecos;
    }

    public void setEnderecosDTO(List<EnderecoDTO> enderecosDTO) {
        this.enderecos = enderecosDTO;
    }
}
