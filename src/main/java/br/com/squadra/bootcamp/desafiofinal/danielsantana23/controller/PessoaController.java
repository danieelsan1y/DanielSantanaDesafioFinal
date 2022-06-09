package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaEnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaSalvarAlterarDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/pessoa")
@RestController
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @PostMapping
    ResponseEntity<List<PessoaDTO>> savar(@Valid @RequestBody PessoaSalvarAlterarDTO pessoaSalvarDTO) {
        pessoaService.salvar(pessoaSalvarDTO);
        List<PessoaDTO> pessoas = pessoaService.buscarTodas();
        return ResponseEntity.ok().body(pessoas);
    }

    @PutMapping
    ResponseEntity<List<PessoaDTO>> alterar(@Valid @RequestBody PessoaSalvarAlterarDTO pessoaSalvarDTO) {
        pessoaService.atualizar(pessoaSalvarDTO);
        List<PessoaDTO> pessoas = pessoaService.buscarTodas();
        return ResponseEntity.ok().body(pessoas);
    }
    @GetMapping
    ResponseEntity<Object> buscarPorFiltro (@RequestParam Map<String,String> parametros) {
        Object pessoaDTOS = pessoaService.buscarPorFiltro(parametros);
        return ResponseEntity.ok().body(pessoaDTOS);
    }
}
