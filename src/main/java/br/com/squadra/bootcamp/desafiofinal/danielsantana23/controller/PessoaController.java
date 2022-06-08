package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaEnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaSalvarAtrerarDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.UfDTO;
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
    ResponseEntity<Void> savar(@Valid @RequestBody PessoaSalvarAtrerarDTO pessoaSalvarDTO) {
        pessoaService.salvar(pessoaSalvarDTO);
        return ResponseEntity.ok().build();
    }


    @GetMapping(params = "codigoPessoa")
    ResponseEntity<PessoaEnderecoDTO> buscarPorCodigoPessoa(@RequestParam Integer codigoPessoa) {
        PessoaEnderecoDTO pessoaComRelaiconamentoDTO = pessoaService.buscarPessoaPorCodigoPessoa(codigoPessoa);
        return ResponseEntity.ok().body(pessoaComRelaiconamentoDTO);
    }

    @PutMapping
    ResponseEntity<Void> alterar(@Valid @RequestBody PessoaSalvarAtrerarDTO pessoaSalvarDTO) {
        pessoaService.atualizar(pessoaSalvarDTO);
        return ResponseEntity.ok().build();

    }
    @GetMapping
    ResponseEntity<List<PessoaDTO>> buscarPorFiltro (@RequestParam Map<String,String> parametros) {
        List<PessoaDTO> pessoaDTOS = pessoaService.buscarPorFiltro(parametros);
        return ResponseEntity.ok().body(pessoaDTOS);
    }


}
