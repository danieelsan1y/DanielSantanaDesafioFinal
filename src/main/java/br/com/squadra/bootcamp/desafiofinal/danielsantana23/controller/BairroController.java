package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.BairroDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.BairroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/bairro")
public class BairroController {

    @Autowired
    BairroService bairroService;

    @PostMapping
    ResponseEntity<Void> savar(@Valid @RequestBody BairroDTO bairroDTO) {
        BairroDTO novoBairroDTO = bairroService.salvar(bairroDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigoMunicipio}").buildAndExpand(novoBairroDTO.getCodigoBairro()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    ResponseEntity<List<BairroDTO>> buscarTodos() {
        List<BairroDTO> bairroDTOS = bairroService.buscarTodos();
        return ResponseEntity.ok().body(bairroDTOS);
    }

}
