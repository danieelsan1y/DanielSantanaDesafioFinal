package br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Integer>, JpaSpecificationExecutor<Bairro> {

    public Bairro findByNome(String nome);

    public Bairro findByCodigoBairro(Integer codigoBairro);

    @Query(" SELECT b FROM Bairro b " +
            " INNER JOIN b.municipio municipio " +
            " WHERE municipio.codigoMunicipio = :codigoMunicipio ")
    List<Bairro> findAllByCodigoMunicipio(@Param("codigoMunicipio") Integer codigoMunicipio);


    @Query("SELECT b FROM Bairro b where b.status = :status")
    public List<Bairro> findAllStatus(@Param("status") Integer status);
}
