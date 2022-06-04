package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller.exceptions;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.comrelacionamento.PessoaRelacionamentoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.PessoaRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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
    ResponseEntity<PessoaRelacionamentoDTO> buscarPorCodigoPessoa(@PathVariable Integer codigoPessoa) {
        PessoaRelacionamentoDTO pessoaComRelaiconamentoDTO = pessoaService.buscarPessoaPorCodigoPessoa(codigoPessoa);
        return ResponseEntity.ok().body(pessoaComRelaiconamentoDTO);
     }
}
