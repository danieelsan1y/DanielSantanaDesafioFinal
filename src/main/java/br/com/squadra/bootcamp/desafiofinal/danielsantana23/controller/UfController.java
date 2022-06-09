package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.UfDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.UfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


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
    @PutMapping
    ResponseEntity<List<UfDTO>> alterar(@Valid @RequestBody UfDTO ufDTO) {
        ufService.alterar(ufDTO);
        List<UfDTO> ufsDTORetorno = ufService.buscarTodos();
        return ResponseEntity.ok().body(ufsDTORetorno);
    }
    @GetMapping
    ResponseEntity<List<UfDTO>> buscarPorFiltro (@RequestParam Map<String,String> parametros) {
        List<UfDTO> ufsDTO = ufService.buscarPorFiltro(parametros);
        return ResponseEntity.ok().body(ufsDTO);
    }
}
