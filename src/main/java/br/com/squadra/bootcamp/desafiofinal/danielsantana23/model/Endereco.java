package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_sequence")
    @Column(name = "codigo_endereco", length = 9)
    private Integer codigoEndereco;

    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    private Pessoa pessoa;
    @ManyToOne
    @JoinColumn(name = "codigo_bairro")
    private Bairro bairro;
    @Column(name = "nome_rua", length = 256)
    private String nomeRua;
    @Column(name = "numero", length = 256)
    private String numero;
    @Column(name = "complemento", length = 20)
    private String complemento;
    @Column(name = "cep", length = 10)
    private String cep;

    private Integer status;

    public Endereco() {
    }

    public Endereco(Integer codigoEndereco, Bairro bairro, Pessoa pessoa, String nomeRua, String numero, String complemento, String cep, Integer status) {
        this.codigoEndereco = codigoEndereco;
        this.bairro = bairro;
        this.nomeRua = nomeRua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.status = status;
        this.pessoa =pessoa;
    }

    public Integer getCodigoEndereco() {
        return codigoEndereco;
    }

    public void setCodigoEndereco(Integer codigoEndereco) {
        this.codigoEndereco = codigoEndereco;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
