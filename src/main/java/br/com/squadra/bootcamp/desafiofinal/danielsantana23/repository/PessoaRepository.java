package br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>, JpaSpecificationExecutor<Pessoa> {


    Pessoa findByCodigoPessoa(@Param("codigoPessoa") Integer codigoPessoa);
    Pessoa findByLogin(@Param("login") String login);


}
