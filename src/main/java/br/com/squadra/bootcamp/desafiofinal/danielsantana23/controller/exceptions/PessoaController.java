package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller.exceptions;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.BairroDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.PessoaEnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.PessoaSalvarDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping(value = "/pessoa")
@RestController
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @PostMapping
    ResponseEntity<Void> savar(@Valid @RequestBody PessoaSalvarDTO pessoaSalvarDTO) {
        pessoaService.salvar(pessoaSalvarDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<PessoaDTO>> buscarTodas() {
        List<PessoaDTO> pessoasDTO = pessoaService.buscarTodas();
        return ResponseEntity.ok().body(pessoasDTO);
    }

    @GetMapping(value = "{codigoPessoa}")
    ResponseEntity<PessoaEnderecoDTO> buscarPorCodigoPessoa(@PathVariable Integer codigoPessoa) {
        PessoaEnderecoDTO pessoaComRelaiconamentoDTO = pessoaService.buscarPessoaPorCodigoPessoa(codigoPessoa);
        return ResponseEntity.ok().body(pessoaComRelaiconamentoDTO);
    }


}
