package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller.exceptions;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.PessoaEnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/pessoa")
@RestController
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @GetMapping
    ResponseEntity<List<PessoaDTO>> buscarTodas() {
        List<PessoaDTO> pessoasDTO =pessoaService.buscarTodas();
        return ResponseEntity.ok().body(pessoasDTO);
    }
    @GetMapping(value = "{codigoPessoa}")
    ResponseEntity<PessoaEnderecoDTO> buscarPorCodigoPessoa(@PathVariable Integer codigoPessoa) {
        PessoaEnderecoDTO pessoaComRelaiconamentoDTO = pessoaService.buscarPessoaPorCodigoPessoa(codigoPessoa);
        return ResponseEntity.ok().body(pessoaComRelaiconamentoDTO);
     }
}
