package br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query(" SELECT p from Pessoa p LEFT JOIN FETCH p.enderecos ")
    List<Pessoa> buscarPessoas();

}
