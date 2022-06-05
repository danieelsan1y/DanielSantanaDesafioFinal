package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller.exceptions;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaEnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaSalvarAtrerarDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping
    ResponseEntity<List<PessoaDTO>> buscarTodas() {
        List<PessoaDTO> pessoasDTO = pessoaService.buscarTodas();
        return ResponseEntity.ok().body(pessoasDTO);
    }

    @GetMapping(params = "codigoPessoa")
    ResponseEntity<PessoaEnderecoDTO> buscarPorCodigoPessoa(@RequestParam Integer codigoPessoa) {
        PessoaEnderecoDTO pessoaComRelaiconamentoDTO = pessoaService.buscarPessoaPorCodigoPessoa(codigoPessoa);
        return ResponseEntity.ok().body(pessoaComRelaiconamentoDTO);
    }

    @PutMapping(value = "/{codigoPessoa}")
    ResponseEntity<Void> alterar (@Valid @RequestBody PessoaSalvarAtrerarDTO pessoaSalvarDTO, @PathVariable Integer codigoPessoa  ) {
       pessoaService.atualizar(pessoaSalvarDTO, codigoPessoa);
        return ResponseEntity.ok().build();

    }


}
