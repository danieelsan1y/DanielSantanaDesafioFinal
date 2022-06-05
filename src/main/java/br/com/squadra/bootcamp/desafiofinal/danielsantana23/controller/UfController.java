package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.UfDTO;
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
    ResponseEntity<List<UfDTO>> savar(@Valid @RequestBody UfDTO ufDTO) {
       ufService.salvar(ufDTO);
        List<UfDTO> ufsDTORetorno = ufService.buscarTodos();
        return ResponseEntity.ok().body(ufsDTORetorno);
    }

    @GetMapping
    ResponseEntity<List<UfDTO>> buscarTodos () {
        List<UfDTO> ufsDTO = ufService.buscarTodos();
        return ResponseEntity.ok().body(ufsDTO);
    }

    @PutMapping(value = "/{codigouf}")
    ResponseEntity<List<UfDTO>> alterar(@Valid @RequestBody UfDTO ufDTO,@PathVariable Integer codigouf) {
        ufService.alterar(ufDTO,codigouf);
        List<UfDTO> ufsDTORetorno = ufService.buscarTodos();
        return ResponseEntity.ok().body(ufsDTORetorno);
    }

    @GetMapping(params = "nome")
    ResponseEntity<UfDTO> buscarUfPorNome(@RequestParam String nome) {
        UfDTO ufDTO = ufService.buscarPorNome(nome);
        return ResponseEntity.ok().body(ufDTO);
    }

    @GetMapping(params = "sigla")
    ResponseEntity<UfDTO> buscarUfPorSigla(@RequestParam String sigla) {
        UfDTO ufDTO = ufService.buscarPorSigla(sigla);
        return ResponseEntity.ok().body(ufDTO);
    }

    @GetMapping(params = "status")
    ResponseEntity<List<UfDTO>> buscarUmStatus (@RequestParam Integer status) {
        List<UfDTO> ufsDTO = ufService.buscarUmStatus(status);
        return ResponseEntity.ok().body(ufsDTO);
    }
}
