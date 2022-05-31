package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tb_municipio")
@Entity
public class Municipio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_municipio", length = 9)
    private Integer codigoMunicipio;
    @ManyToOne
    @JoinColumn(name = "codigo_uf")
    private Uf uf;
    @Column(name = "nome", length = 256)
    private String nome;
    @Column(name = "status", length = 3)
    private Integer status;

    public Municipio() {

    }

    public Municipio(Integer codigoMunicipio, Uf uf, String nome, Integer status) {
        this.codigoMunicipio = codigoMunicipio;
        this.uf = uf;
        this.nome = nome;
        this.status = status;
    }

    public Integer getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Integer codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
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
