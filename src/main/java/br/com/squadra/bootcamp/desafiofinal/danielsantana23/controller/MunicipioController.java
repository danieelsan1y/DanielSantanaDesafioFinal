package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.UfDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/municipio")
public class MunicipioController {

    @Autowired
    MunicipioService municipioService;

    @PostMapping
    ResponseEntity<Void> savar(@Valid @RequestBody MunicipioDTO municipioDTO) {
        MunicipioDTO novoMunicipioDTO = municipioService.salvar(municipioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigoMunicipio}").buildAndExpand(novoMunicipioDTO.getCodigoMunicipio()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @GetMapping
    ResponseEntity<List<MunicipioDTO>> bucarTodos () {
        List<MunicipioDTO> municipiosDTO = municipioService.buscarTodos();
        return ResponseEntity.ok().body(municipiosDTO);
    }
    @GetMapping(value = "/{codigoMunicipio}")
    ResponseEntity<MunicipioDTO> buscarPorCodigoMunicipio(@PathVariable Integer codigoMunicipio) {
        MunicipioDTO municipioDTO = municipioService.buscarPorCodigoMunicipio(codigoMunicipio);
        return  ResponseEntity.ok().body(municipioDTO);
    }

    @GetMapping(value = "uf/{codigoUf}")
    ResponseEntity<List<MunicipioDTO>> buscarPorCodigoUf(@PathVariable Integer codigoUf) {
        List<MunicipioDTO> municipiosDTO = municipioService.buscarPorCodigoUf(codigoUf);
        return  ResponseEntity.ok().body(municipiosDTO);
    }
    @PutMapping(value = "/{codigoMunicipio}")
    ResponseEntity<Void> alterar(@Valid @RequestBody MunicipioDTO municipioDTO, @PathVariable Integer codigoMunicipio) {
        municipioService.alterar(municipioDTO,codigoMunicipio);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "status/{status}")
    ResponseEntity<List<MunicipioDTO>> buscarStatus (@PathVariable Integer status) {
        List<MunicipioDTO> municipiosDTO = municipioService.buscarStatus(status);
        return ResponseEntity.ok().body(municipiosDTO);

    }
}
