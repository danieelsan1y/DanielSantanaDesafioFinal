package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tb_municipio")
@Entity
public class Municipio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "municipio_gerator")
    @SequenceGenerator(name = "municipio_gerator", initialValue = 1, allocationSize = 1, sequenceName = "SEQUENCE_MUNICIPIO")
    @Column(name = "codigo_municipio", length = 9)
    private Integer codigoMunicipio;

    @ManyToOne
    @JoinColumn(name = "codigo_uf")
    private Uf uf;
    @NotEmpty(message = "O campo nome não pode ser vazio, insira novamente!")
    @Column(name = "nome", length = 256)
    private String nome;
    @Column(name = "status", length = 3)
    private Integer status;
    @OneToMany(mappedBy = "municipio")
    @JsonIgnore
    List<Bairro> bairros = new ArrayList<>();

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
