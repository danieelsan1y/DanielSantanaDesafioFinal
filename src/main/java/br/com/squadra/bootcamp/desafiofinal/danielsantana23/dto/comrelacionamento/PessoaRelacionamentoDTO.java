package br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.comrelacionamento;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class PessoaRelacionamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer codigoPessoa;
    private String nome;
    private String sobrenome;
    private String login;
    private String senha;
   private  Set<EnderecoRelacionamentoDTO> enderecos;
    private Integer status;
    public PessoaRelacionamentoDTO(Pessoa pessoa) {
        this.codigoPessoa = pessoa.getCodigoPessoa();
        this.nome = pessoa.getNome();
        this.sobrenome = pessoa.getSobrenome();
        this.login = pessoa.getLogin();
        this.senha = pessoa.getSenha();
        this.status = pessoa.getStatus();
        enderecos = pessoa.getEnderecos().stream().map(endereco -> new EnderecoRelacionamentoDTO(endereco)).collect(Collectors.toSet());
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

    public Set<EnderecoRelacionamentoDTO> getEnderecos() {
        return enderecos;
    }
}