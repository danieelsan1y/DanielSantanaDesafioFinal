package br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Endereco;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco,Integer> {

    public Endereco findByCodigoEndereco(Integer codigoEndereco);

    @Query(" SELECT e FROM Endereco e " +
            " INNER JOIN e.pessoa pessoa " +
            " WHERE pessoa.codigoPessoa = :codigoPessoa ")
    List<Endereco> findAllByCodigoPessoa(@Param("codigoPessoa") Integer codigoPessoa);

}
