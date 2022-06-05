package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
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
    ResponseEntity<List<MunicipioDTO>> savar(@Valid @RequestBody MunicipioDTO municipioDTO) {
        municipioService.salvar(municipioDTO);
        List<MunicipioDTO> municipioDTOS = municipioService.buscarTodos();
        return ResponseEntity.ok().body(municipioDTOS);
    }

    @GetMapping
    ResponseEntity<List<MunicipioDTO>> bucarTodos() {
        List<MunicipioDTO> municipiosDTO = municipioService.buscarTodos();
        return ResponseEntity.ok().body(municipiosDTO);
    }

    @GetMapping(params = "codigoMunicipio")
    ResponseEntity<MunicipioDTO> buscarPorCodigoMunicipio(@RequestParam Integer codigoMunicipio) {
        MunicipioDTO municipioDTO = municipioService.buscarPorCodigoMunicipio(codigoMunicipio);
        return ResponseEntity.ok().body(municipioDTO);
    }

    @GetMapping(params = "codigoUf")
    ResponseEntity<List<MunicipioDTO>> buscarPorCodigoUf(@RequestParam Integer codigoUf) {
        List<MunicipioDTO> municipiosDTO = municipioService.buscarPorCodigoUf(codigoUf);
        return ResponseEntity.ok().body(municipiosDTO);
    }

    @GetMapping(params = "status")
    ResponseEntity<List<MunicipioDTO>> buscarStatus(@RequestParam Integer status) {
        List<MunicipioDTO> municipiosDTO = municipioService.buscarStatus(status);
        return ResponseEntity.ok().body(municipiosDTO);
    }

    @PutMapping(value = "/{codigoMunicipio}")
    ResponseEntity<List<MunicipioDTO>> alterar(@Valid @RequestBody MunicipioDTO municipioDTO, @PathVariable Integer codigoMunicipio) {
        municipioService.alterar(municipioDTO, codigoMunicipio);
        List<MunicipioDTO> municipioDTOS = municipioService.buscarTodos();
        return ResponseEntity.ok().body(municipioDTOS);
    }


}
