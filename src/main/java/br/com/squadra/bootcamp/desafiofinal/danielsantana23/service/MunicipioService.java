package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.MunicipioRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunicipioService {

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    UfRepository ufRepository;

    public MunicipioDTO salvar (MunicipioDTO municipioDTO) {
        Integer codigoUf = municipioDTO.getUf();
        Uf uf = ufRepository.findByCodigoUf(codigoUf);
        Municipio municipio = new Municipio();
        municipio.setCodigoMunicipio(municipioDTO.getCodigoMunicipio());
        municipio.setNome(municipioDTO.getNome());
        municipio.setStatus(municipioDTO.getStatus());
        municipio.setUf(uf);
        municipioRepository.save(municipio);

        MunicipioDTO novoMunicipioDTO = new MunicipioDTO(municipio);
        return novoMunicipioDTO;
    }





    private Municipio converterParaMunicipio(MunicipioDTO municipioDTO) {
        Uf uf = ufRepository.findByCodigoUf(municipioDTO.getUf());
        Municipio municipio = new Municipio(municipioDTO.getCodigoMunicipio(),uf, municipioDTO.getNome(), municipioDTO.getStatus());
        return municipio;
    }
}
