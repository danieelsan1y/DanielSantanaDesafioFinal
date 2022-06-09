package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @PutMapping
    ResponseEntity<List<MunicipioDTO>> alterar(@Valid @RequestBody MunicipioDTO municipioDTO) {
        municipioService.alterar(municipioDTO);
        List<MunicipioDTO> municipioDTOS = municipioService.buscarTodos();
        return ResponseEntity.ok().body(municipioDTOS);
    }

    @GetMapping
    ResponseEntity<Object> buscarPorFiltro(@RequestParam Map<String, String> parametros) {
        Object municipioDTOS = municipioService.buscarPorFiltro(parametros);
        return ResponseEntity.ok().body(municipioDTOS);
    }
}
