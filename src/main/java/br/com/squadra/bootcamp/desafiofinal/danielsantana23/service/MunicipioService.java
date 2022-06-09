package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification.MunicipioSpecification;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.MunicipioRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.UfRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.exeption.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

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
            if (municipioDTO.getStatus() == 1 || municipioDTO.getStatus() == 2) {
                municipio.setStatus(municipioDTO.getStatus());
                if (uf != null) {
                    municipio.setUf(uf);
                    municipioRepository.save(municipio);
                } else {
                    throw new ServiceException("UF com o codigoUf: " + municipioDTO.getCodigoUf() + " não está cadastrado no banco!");
                }
            } else {
                throw new ServiceException("Valor para status não válido!");
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

    public void alterar(MunicipioDTO municipioDTO) {
        if (verficarSeJaExisteNoBancoPorCodigoMunicipio(municipioDTO.getCodigoMunicipio())) {
            Municipio municipioAntigo = municipioRepository.findByCodigoMunicipio((municipioDTO.getCodigoMunicipio()));
            alterarCampos(municipioDTO, municipioAntigo);
            municipioRepository.save(municipioAntigo);
        } else {
            throw new ServiceException("Município com codigoMunicipio: " + municipioDTO.getCodigoMunicipio() + " não existe no banco");
        }
    }

    private void alterarCampos(MunicipioDTO municipioDTO, Municipio municipioAntigo) {
        if (!verificarSeMunicipioJaExisteNoBancoPorNome(municipioDTO.getNome().toUpperCase()) || municipioDTO.getNome().toUpperCase().equals(municipioDTO.getNome())) {
            Uf uf = ufRepository.findByCodigoUf(municipioDTO.getCodigoUf());
            if (uf != null) {
                municipioAntigo.setNome(municipioDTO.getNome().toUpperCase());

                if (municipioDTO.getStatus() == 1 || municipioDTO.getStatus() == 2) {
                    municipioAntigo.setStatus(municipioDTO.getStatus());
                    municipioAntigo.setUf(uf);
                } else {
                    throw new ServiceException("Valor para status não válido!");
                }

            } else {
                throw new ServiceException("Uf com codigoUf: " + municipioDTO.getCodigoUf() + " não existe no banco");
            }
        } else {
            throw new ServiceException("Municipio com esse nome já existe no banco");
        }
    }

    public Object buscarPorFiltro(Map<String, String> parametros) {
        List<MunicipioDTO> municipioDTOS = new ArrayList<>();
        if (parametros == null || parametros.isEmpty()) {
            return municipioDTOS = municipioRepository.findAll().stream().map(municipio -> new MunicipioDTO(municipio)).collect(Collectors.toList());
        }
        Specification<Municipio> specification = getMunicipioSpecification(parametros);

        List<Municipio> municipios = municipioRepository.findAll(specification);
        municipioDTOS = municipios.stream().map(municipio -> new MunicipioDTO(municipio)).collect(Collectors.toList());
        if (parametros.get("codigoMunicipio") != null && municipioDTOS.size() != 0) {
            return municipioDTOS.stream().findFirst().get();
        } else {
            if (municipioDTOS.size() != 0) {
                return municipioDTOS;
            }
            return new ArrayList<>();
        }
    }

    private Specification<Municipio> getMunicipioSpecification(Map<String, String> parametros) {

        Specification<Municipio> specification = null;
        Integer status = null;
        Integer codigoUf = null;
        Integer codigoMunicipio = null;
        if (parametros.get("status") != null) {
            status = Integer.parseInt(parametros.get("status"));
        }
        if (parametros.get("codigoUF") != null) {
            codigoUf = Integer.parseInt(parametros.get("codigoUF"));
        }
        if (parametros.get("codigoMunicipio") != null) {
            codigoMunicipio = Integer.parseInt(parametros.get("codigoMunicipio"));
        }

        if (parametros.get("status") != null && !parametros.get("status").isEmpty()) {
            Integer finalCodigoUf = codigoUf;
            Integer finalCodigoMunicipio = codigoMunicipio;
            specification = Optional.ofNullable(where(MunicipioSpecification.buscarPorStatus(status)))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorCodigoMunicipio(finalCodigoMunicipio))).
                    map(spec -> spec.and(MunicipioSpecification.buscarPorCodigoUf(finalCodigoUf)))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorNome(parametros.get("nome"))))
                    .orElse(null);
        }
        if (parametros.get("codigoMunicipio") != null && !parametros.get("codigoMunicipio").isEmpty()) {
            Integer finalCodigoUf = codigoUf;
            Integer finalCodigoMunicipio = codigoMunicipio;
            Integer finalStatus = status;
            specification = Optional.ofNullable(where(MunicipioSpecification.buscarPorCodigoMunicipio(codigoMunicipio)))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorStatus(finalStatus)))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorCodigoUf(finalCodigoUf)))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorNome(parametros.get("nome"))))
                    .orElse(null);
        }
        if (parametros.get("codigoUF") != null && !parametros.get("codigoUF").isEmpty()) {
            Integer finalCodigoUf = codigoUf;
            Integer finalCodigoMunicipio = codigoMunicipio;
            Integer finalStatus = status;
            specification = Optional.ofNullable(where(MunicipioSpecification.buscarPorCodigoUf(codigoUf)))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorStatus(finalStatus))).
                    map(spec -> spec.and(MunicipioSpecification.buscarPorCodigoMunicipio(finalCodigoMunicipio)))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorNome(parametros.get("nome"))))
                    .orElse(null);
        }

        if (parametros.get("nome") != null && !parametros.get("nome").isEmpty()) {
            Integer finalCodigoUf = codigoUf;
            Integer finalCodigoMunicipio = codigoMunicipio;
            Integer finalStatus = status;
            specification = Optional.ofNullable(where(MunicipioSpecification.buscarPorNome(parametros.get("nome"))))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorStatus(finalStatus))).
                    map(spec -> spec.and(MunicipioSpecification.buscarPorCodigoMunicipio(finalCodigoMunicipio)))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorNome(parametros.get("nome"))))
                    .map(spec -> spec.and(MunicipioSpecification.buscarPorCodigoUf(finalCodigoUf)))
                    .orElse(null);
        }
        return specification;
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
