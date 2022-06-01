package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.BairroDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.BairroRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.MunicipioRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.exeption.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BairroService {

    @Autowired
    BairroRepository bairroRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    public BairroDTO salvar(BairroDTO bairroDTO) {
        bairroDTO.setNome(bairroDTO.getNome().toUpperCase());
        if (!verificarSeBairrpJaExisteNoBancoPorNome(bairroDTO.getNome())) {
            Integer codigoMunicipio = bairroDTO.getCodigoMunicipio();
            Municipio municipio = municipioRepository.findByCodigoMunicipio(codigoMunicipio);
            Bairro bairro = new Bairro();
            bairro.setStatus(bairroDTO.getStatus());
            bairro.setMunicipio(municipio);
            bairro.setNome(bairroDTO.getNome());
            bairroRepository.save(bairro);
            BairroDTO novoBairroDTO = new BairroDTO(bairro);
            return novoBairroDTO;
        } else {
            throw new ServiceException("Bairro " + bairroDTO.getNome() + " já cadastrado no banco!");
        }
    }
    public List<BairroDTO> buscarTodos() {
        List<Bairro> bairros = bairroRepository.findAll();
        List<BairroDTO> bairrosDTO = bairros.stream().map(bairro -> new BairroDTO(bairro)).collect(Collectors.toList());
        return bairrosDTO;
    }



    private boolean verificarSeBairrpJaExisteNoBancoPorNome(String nome) {
        Bairro bairroExistenteNome = bairroRepository.findByNome(nome);
        if (bairroExistenteNome != null) {
            return true;
        } else {
            return false;
        }
    }

}
