package br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.Uf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UfRepository extends JpaRepository<Uf, Integer>, JpaSpecificationExecutor<Uf> {

    public Uf findByNome(String nome);


    public Uf findByCodigoUf(Integer codigoUf);

    public Uf findByStatus(Integer status);

    @Query("select u from Uf u where u.status = :status")
    public List<Uf> findAllAtivos(@Param("status") Integer status);

    public Uf findBySigla(String sigla);



}
