package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller.exceptions;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.PessoaRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/pessoa")
@RestController
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @Autowired
    PessoaRepository repository;

    @GetMapping
    ResponseEntity<List<PessoaDTO>> buscarTodas() {
        List<PessoaDTO> pessoas = pessoaService.buscarTodas();
        return ResponseEntity.ok().body(pessoas);
     }
}
