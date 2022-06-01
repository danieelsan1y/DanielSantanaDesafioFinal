package br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

    public Municipio findByCodigoMunicipio(Integer codigoMunicipio);

    public Municipio findByNome(String nomeMunicipio);


   @Query(" SELECT m FROM Municipio m " +
           " INNER JOIN m.uf uf " +
           " WHERE uf.codigoUf = :codigoUf ")
   List<Municipio> findAllByCodigoUf(@Param("codigoUf") Integer codigoUf);

    @Query("select m from Municipio m where m.status = :status")
    public List<Municipio> findAllStatus(@Param("status") Integer status);


}
