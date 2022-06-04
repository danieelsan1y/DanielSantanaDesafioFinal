package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.UfDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.UfService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/uf")
public class UfController {

    @Autowired
    UfService ufService;

    @PostMapping
    ResponseEntity<Void> savar(@Valid @RequestBody UfDTO ufDTO) {
        UfDTO novoUfDTO = ufService.salvar(ufDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigoUf}").buildAndExpand(novoUfDTO.getCodigoUf()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    ResponseEntity<List<UfDTO>> buscarTodos () {
        List<UfDTO> ufsDTO = ufService.buscarTodos();
        return ResponseEntity.ok().body(ufsDTO);
    }

    @PutMapping(value = "/{codigouf}")
    ResponseEntity<Void> alterar(@Valid @RequestBody UfDTO ufDTO,@PathVariable Integer codigouf) {
        ufService.alterar(ufDTO,codigouf);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "nome/{nome}")
    ResponseEntity<UfDTO> buscarUfPorNome(@PathVariable String nome) {
        UfDTO ufDTO = ufService.buscarPorNome(nome);
        return ResponseEntity.ok().body(ufDTO);
    }

    @GetMapping(value = "sigla/{sigla}")
    ResponseEntity<UfDTO> buscarUfPorSigla(@PathVariable String sigla) {
        UfDTO ufDTO = ufService.buscarPorSigla(sigla);
        return ResponseEntity.ok().body(ufDTO);
    }

    @PutMapping(value = "status/{codigoUf}")
    ResponseEntity<Void> alterarStatus(@Valid @PathVariable Integer codigoUf, @RequestBody UfDTO ufDTO) {
        ufService.alterarStatusUf(codigoUf, ufDTO.getStatus());
        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "status/{status}")
    ResponseEntity<List<UfDTO>> buscarUmStatus (@PathVariable Integer status) {
        List<UfDTO> ufsDTO = ufService.buscarUmStatus(status);
        return ResponseEntity.ok().body(ufsDTO);
    }
}
