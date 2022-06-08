package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.BairroDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.BairroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/bairro")
public class BairroController {

    @Autowired
    BairroService bairroService;

    @PostMapping
    ResponseEntity<List<BairroDTO>> savar(@Valid @RequestBody BairroDTO bairroDTO) {
        bairroService.salvar(bairroDTO);
        List<BairroDTO> bairroDTOS = bairroService.buscarTodos();
        return ResponseEntity.ok().body(bairroDTOS);
    }

    @GetMapping
    ResponseEntity<List<BairroDTO>> buscarPorFiltro (@RequestParam Map<String,String> parametros) {
        List<BairroDTO> bairroDTOS = bairroService.buscarPorFiltro(parametros);
        return ResponseEntity.ok().body(bairroDTOS);
    }

    @PutMapping
    ResponseEntity<List<BairroDTO>> alterar(@Valid @RequestBody BairroDTO bairroDTO) {
        bairroService.alterar(bairroDTO);
        List<BairroDTO> bairroDTOS = bairroService.buscarTodos();
        return ResponseEntity.ok().body(bairroDTOS);
    }

}
