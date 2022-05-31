package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller.exceptions;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.UfDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/m")
public class MunicipioController {

    @Autowired
    MunicipioService municipioService;

    @PostMapping
    ResponseEntity<Void> savar(@RequestBody MunicipioDTO municipioDTO) {
        MunicipioDTO novoMunicipioDTO = municipioService.salvar(municipioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigoMunicipio}").buildAndExpand(novoMunicipioDTO.getCodigoMunicipio()).toUri();
        return null;
    }

}
