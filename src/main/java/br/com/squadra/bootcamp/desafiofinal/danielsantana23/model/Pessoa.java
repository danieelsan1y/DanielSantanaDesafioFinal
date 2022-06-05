package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_pessoa")
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_sequence")
    @Column(name = "codigo_pessoa", length = 9)
    private Integer codigoPessoa;
    //@NotEmpty(message = "O campo nome não pode ser vazio, insira novamente!")

    @OneToMany(mappedBy = "pessoa")
    private Set<Endereco> enderecos;
    @Column(name = "nome", length = 256)
    private String nome;
    @NotEmpty(message = "O campo sobrenome não pode ser vazio, insira novamente!")
    @Column(name = "sobrenome", length = 256)
    private String sobrenome;
     @NotEmpty(message = "O campo login não pode ser vazio, insira novamente!")
    @Column(name = "login", length = 50)
    private String login;
    @NotEmpty(message = "O campo senha não pode ser vazio, insira novamente!")
    @Column(name = "senha", length = 50)
    private String senha;
    @Column(name = "status", length = 3)
    private Integer status;

    public Pessoa() {
    }

    public Pessoa(Integer codigoPessoa, String nome, String sobrenome, String login, String senha, Integer status) {
        this.codigoPessoa = codigoPessoa;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.login = login;
        this.senha = senha;
        this.status = status;
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

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}