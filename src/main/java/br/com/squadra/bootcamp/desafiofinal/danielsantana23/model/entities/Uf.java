package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_uf")
public class Uf implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uf_gerator")
    @SequenceGenerator(name = "uf_gerator", initialValue = 1, allocationSize = 1, sequenceName = "SEQUENCE_UF")
    @Column(name = "codigo_uf", length = 9)
    private Integer codigoUf;
    @NotEmpty(message = "O campo sigla não pode ser vazio, insira novamente!")
    @Column(name = "sigla", length = 3)
    private String sigla;
    @NotEmpty(message = "O campo nome não pode ser vazio, insira novamente!")
    @Column(name = "nome", length = 60)
    private String nome;
    @Column(name = "status", length = 3)
    private Integer status;

    @OneToMany(mappedBy = "uf")
    private List<Municipio> municipios = new ArrayList<>();

    public Uf() {
    }

    public Uf(Integer codigoUf, String sigla, String nome, Integer status) {
        this.codigoUf = codigoUf;
        this.sigla = sigla;
        this.nome = nome;
        this.status = status;
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

    public List<Municipio> getMunicipios() {
        return municipios;
    }

}
