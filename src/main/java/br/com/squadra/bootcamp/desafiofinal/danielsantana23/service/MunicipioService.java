package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.UfDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.MunicipioRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.UfRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.exeption.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunicipioService {

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    UfRepository ufRepository;

    public MunicipioDTO salvar(MunicipioDTO municipioDTO) {
        municipioDTO.setNome(municipioDTO.getNome().toUpperCase());
        if (!verificarSeMunicipioJaExisteNoBancoPorNome(municipioDTO.getNome())) {
            Integer codigoUf = municipioDTO.getCodigoUf();
            Uf uf = ufRepository.findByCodigoUf(codigoUf);
            Municipio municipio = new Municipio();
            municipio.setCodigoMunicipio(municipioDTO.getCodigoMunicipio());
            municipio.setNome(municipioDTO.getNome());
            municipio.setStatus(municipioDTO.getStatus());
            municipio.setUf(uf);
            municipioRepository.save(municipio);

            MunicipioDTO novoMunicipioDTO = new MunicipioDTO(municipio);
            return novoMunicipioDTO;
        } else {
            throw new ServiceException("Município " + municipioDTO.getNome() + " já cadastrado no banco!");
        }

    }

    public List<MunicipioDTO> buscarTodos() {
        List<Municipio> municipios = municipioRepository.findAll();
        List<MunicipioDTO> municipioDTO = municipios.stream().map(municipio -> new MunicipioDTO(municipio)).collect(Collectors.toList());
        return municipioDTO;
    }

    public MunicipioDTO buscarPorCodigoMunicipio(Integer codigoMunicipio) {

        if (verficarSeJaExisteNoBancoPorCodigoMunicipio(codigoMunicipio)) {
            Municipio municipio = municipioRepository.findByCodigoMunicipio(codigoMunicipio);
            return new MunicipioDTO(municipio);
        } else {
            throw new ServiceException("Municipio com id: " + codigoMunicipio + " não esta cadastrado no Banco!");
        }

    }

    public List<MunicipioDTO> buscarPorCodigoUf(Integer codigoUf) {
        if (verficarSeJaExisteNoBancoPorCodigoUf(codigoUf)) {
            List<Municipio> municipios = municipioRepository.findAllByCodigoUf(codigoUf);

            List<MunicipioDTO> municipioDTO = municipios.stream().map(municipio -> new MunicipioDTO(municipio)).collect(Collectors.toList());
            return municipioDTO;

        } else {
            throw new ServiceException("Uf com o id: " + codigoUf + " não esta cadastrado no Banco!");
        }

    }
    public void alterar(MunicipioDTO municipioDTO, Integer codigoMunicipio) {
        if (verficarSeJaExisteNoBancoPorCodigoMunicipio(codigoMunicipio)){
            Municipio municipioAntigo = municipioRepository.findByCodigoMunicipio(codigoMunicipio);
            alterarCampos(municipioDTO, municipioAntigo);
            municipioRepository.save(municipioAntigo);
        } else {
            throw new ServiceException("Município com id: "+codigoMunicipio+ " não existe no banco");
        }
    }
    public List<MunicipioDTO> buscarStatus (Integer status) {
       List <Municipio> municipios = municipioRepository.findAllStatus(status);
        List<MunicipioDTO> municipioDTO = municipios.stream().map(municipio -> new MunicipioDTO(municipio)).collect(Collectors.toList());
        return municipioDTO;
    }

    private void alterarCampos(MunicipioDTO municipioDTO, Municipio municipioAntigo) {

        Uf uf = ufRepository.findByCodigoUf(municipioDTO.getCodigoUf());
        municipioAntigo.setNome(municipioDTO.getNome().toUpperCase());
        municipioAntigo.setStatus(municipioDTO.getStatus());
        municipioAntigo.setUf(uf);

    }

    private boolean verficarSeJaExisteNoBancoPorCodigoUf(Integer codigoUf) {
        List<Municipio> municipioExistenteCodigoUf = municipioRepository.findAllByCodigoUf(codigoUf);
        if (municipioExistenteCodigoUf != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean verficarSeJaExisteNoBancoPorCodigoMunicipio(Integer codigoMunicipio) {
        Municipio municipioExistenteCodigo = municipioRepository.findByCodigoMunicipio(codigoMunicipio);
        if (municipioExistenteCodigo != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean verificarSeMunicipioJaExisteNoBancoPorNome(String nome) {
        Municipio municipioExistenteNome = municipioRepository.findByNome(nome);
        if (municipioExistenteNome != null) {
            return true;
        } else {
            return false;
        }
    }

    private Municipio converterParaMunicipio(MunicipioDTO municipioDTO) {
        Uf uf = ufRepository.findByCodigoUf(municipioDTO.getCodigoUf());
        Municipio municipio = new Municipio(municipioDTO.getCodigoMunicipio(), uf, municipioDTO.getNome(), municipioDTO.getStatus());
        return municipio;
    }
}
