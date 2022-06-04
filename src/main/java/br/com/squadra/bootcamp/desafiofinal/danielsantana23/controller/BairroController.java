package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.BairroDTO;
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

    @GetMapping(value = "{codigoBairro}")
    ResponseEntity<BairroDTO> buscarPorCodigoBairro(@PathVariable Integer codigoBairro) {
        BairroDTO bairroDTO = bairroService.buscarPorCodigoBairro(codigoBairro);
        return ResponseEntity.ok().body(bairroDTO);
    }

    @GetMapping(value = "municipio/{codigoMunicipio}")
    ResponseEntity<List<BairroDTO>> buscarPorCodigoMunicipio(@PathVariable Integer codigoMunicipio) {
        List<BairroDTO> bairroDTOS = bairroService.buscarPorCodigoMunicipio(codigoMunicipio);
        return ResponseEntity.ok().body(bairroDTOS);
    }
    @GetMapping(value = "status/{status}")
    ResponseEntity<List<BairroDTO>> buscarStatus(@PathVariable Integer status) {
        List<BairroDTO> bairroDTOS = bairroService.buscarStatus(status);
        return ResponseEntity.ok().body(bairroDTOS);
    }
    @PutMapping(value = "status/{codigoBairro}")
    ResponseEntity<List<BairroDTO>> alterarStatus(@RequestBody BairroDTO bairroDTO,@PathVariable Integer codigoBairro) {
     bairroService.alterarStatus(bairroDTO,codigoBairro);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{codigoBairro}")
    ResponseEntity<Void> alterar(@Valid @RequestBody BairroDTO bairroDTO, @PathVariable Integer codigoBairro) {
        bairroService.alterar(bairroDTO,codigoBairro);
        return ResponseEntity.ok().build();
    }

}
