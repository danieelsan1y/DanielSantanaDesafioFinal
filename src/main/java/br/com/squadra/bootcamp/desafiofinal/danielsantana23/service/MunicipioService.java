package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
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

    public void salvar(MunicipioDTO municipioDTO) {
        municipioDTO.setNome(municipioDTO.getNome().toUpperCase());
        if (!verificarSeMunicipioJaExisteNoBancoPorNome(municipioDTO.getNome())) {
            Uf uf = ufRepository.findByCodigoUf(municipioDTO.getCodigoUf());
            Municipio municipio = new Municipio();
            municipio.setNome(municipioDTO.getNome());
            municipio.setStatus(municipioDTO.getStatus());
            if (uf != null) {
                municipio.setUf(uf);
                municipioRepository.save(municipio);
            } else {
                throw new ServiceException("UF com o codigoUf: " + municipioDTO.getCodigoUf() + " não está cadastrado no banco!");
            }
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
            throw new ServiceException("Municipio com codigoMunicipio: " + codigoMunicipio + " não esta cadastrado no Banco!");
        }

    }

    public List<MunicipioDTO> buscarPorCodigoUf(Integer codigoUf) {
        if (verficarSeJaExisteNoBancoPorCodigoUf(codigoUf)) {
            List<Municipio> municipios = municipioRepository.findAllByCodigoUf(codigoUf);

            List<MunicipioDTO> municipioDTO = municipios.stream().map(municipio -> new MunicipioDTO(municipio)).collect(Collectors.toList());
            return municipioDTO;

        } else {
            throw new ServiceException("Uf com o codigoUf: " + codigoUf + " não esta cadastrado no Banco!");
        }

    }

    public void alterar(MunicipioDTO municipioDTO) {
        if (verficarSeJaExisteNoBancoPorCodigoMunicipio(municipioDTO.getCodigoMunicipio())) {
            Municipio municipioAntigo = municipioRepository.findByCodigoMunicipio((municipioDTO.getCodigoMunicipio()));
            alterarCampos(municipioDTO, municipioAntigo);
            municipioRepository.save(municipioAntigo);
        } else {
            throw new ServiceException("Município com codigoMunicipio: " + municipioDTO.getCodigoMunicipio() + " não existe no banco");
        }
    }

    public List<MunicipioDTO> buscarStatus(Integer status) {
        List<Municipio> municipios = municipioRepository.findAllStatus(status);
        List<MunicipioDTO> municipioDTO = municipios.stream().map(municipio -> new MunicipioDTO(municipio)).collect(Collectors.toList());
        return municipioDTO;
    }

    private void alterarCampos(MunicipioDTO municipioDTO, Municipio municipioAntigo) {

        Uf uf = ufRepository.findByCodigoUf(municipioDTO.getCodigoUf());
        if (uf != null) {
            municipioAntigo.setNome(municipioDTO.getNome().toUpperCase());
            municipioAntigo.setStatus(municipioDTO.getStatus());
            municipioAntigo.setUf(uf);
        } else {
            throw new ServiceException("Uf com codigoUf: " + municipioDTO.getCodigoUf() + " não existe no banco");
        }

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
}
