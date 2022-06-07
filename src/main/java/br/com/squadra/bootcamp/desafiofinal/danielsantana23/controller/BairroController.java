package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.BairroDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.BairroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

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
    ResponseEntity<List<BairroDTO>> buscarTodos() {
        List<BairroDTO> bairroDTOS = bairroService.buscarTodos();
        return ResponseEntity.ok().body(bairroDTOS);
    }

    @GetMapping(params = "codigoBairro")
    ResponseEntity<BairroDTO> buscarPorCodigoBairro(@RequestParam Integer codigoBairro) {
        BairroDTO bairroDTO = bairroService.buscarPorCodigoBairro(codigoBairro);
        return ResponseEntity.ok().body(bairroDTO);
    }

    @GetMapping(params = "codigoMunicipio")
    ResponseEntity<List<BairroDTO>> buscarPorCodigoMunicipio(@RequestParam Integer codigoMunicipio) {
        List<BairroDTO> bairroDTOS = bairroService.buscarPorCodigoMunicipio(codigoMunicipio);
        return ResponseEntity.ok().body(bairroDTOS);
    }

    @GetMapping(params = "status")
    ResponseEntity<List<BairroDTO>> buscarStatus(@RequestParam Integer status) {
        List<BairroDTO> bairroDTOS = bairroService.buscarStatus(status);
        return ResponseEntity.ok().body(bairroDTOS);
    }

    @PutMapping
    ResponseEntity<List<BairroDTO>> alterar(@Valid @RequestBody BairroDTO bairroDTO) {
        bairroService.alterar(bairroDTO);
        List<BairroDTO> bairroDTOS = bairroService.buscarTodos();
        return ResponseEntity.ok().body(bairroDTOS);
    }

}
