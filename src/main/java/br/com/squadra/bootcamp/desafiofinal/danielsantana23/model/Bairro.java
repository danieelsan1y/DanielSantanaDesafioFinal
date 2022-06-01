package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name ="tb_bairro")
public class Bairro implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoBairro;
    @ManyToOne
    @JoinColumn(name = "codigo_municipio")
    private Municipio municipio;
    @NotEmpty(message = "O campo nome não pode ser vazio, insira novamente!")
    private String nome;
    private Integer Status;
    public Bairro() {
    }

    public Bairro(Integer codigoBairro, Municipio municipio, String nome, Integer status) {
        this.codigoBairro = codigoBairro;
        this.municipio = municipio;
        this.nome = nome;
        Status = status;
    }

    public Integer getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(Integer codigoBairro) {
        this.codigoBairro = codigoBairro;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}
