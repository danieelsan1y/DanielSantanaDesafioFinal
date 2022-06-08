package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.BairroDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification.BairoSpecification;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification.MunicipioSpecification;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.BairroRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.MunicipioRepository;
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
public class BairroService {

    @Autowired
    BairroRepository bairroRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    public void salvar(BairroDTO bairroDTO) {
        bairroDTO.setNome(bairroDTO.getNome().toUpperCase());
        if (!verificarSeBairroJaExisteNoBancoPorNome(bairroDTO.getNome())) {
            Municipio municipio = municipioRepository.findByCodigoMunicipio(bairroDTO.getCodigoMunicipio());
            if (municipio != null) {
                Bairro bairro = new Bairro();
                if(bairroDTO.getStatus() == 1 || bairroDTO.getStatus() == 2) {
                    bairro.setStatus(bairroDTO.getStatus());
                    bairro.setMunicipio(municipio);
                    bairro.setNome(bairroDTO.getNome());
                    bairroRepository.save(bairro);
                } else{
                    throw new ServiceException("Valor para status não válido!");
                }

            } else {
                throw new ServiceException("Municipio com o codigoMunicipi0: " + bairroDTO.getCodigoMunicipio() + " não está cadastrado no banco!");
            }

        } else {
            throw new ServiceException("Bairro " + bairroDTO.getNome() + " já cadastrado no banco!");
        }
    }

    public List<BairroDTO> buscarTodos() {
        List<Bairro> bairros = bairroRepository.findAll();
        List<BairroDTO> bairrosDTO = bairros.stream().map(bairro -> new BairroDTO(bairro)).collect(Collectors.toList());
        return bairrosDTO;
    }

    public BairroDTO buscarPorCodigoBairro(Integer codigoBairro) {
        if (verificarSeBairroJaExisteNoBancoPorCodigoBairro(codigoBairro)) {
            Bairro bairro = bairroRepository.findByCodigoBairro(codigoBairro);
            BairroDTO bairroDTO = new BairroDTO(bairro);
            return bairroDTO;
        } else {
            throw new ServiceException("Bairro com o id: " + codigoBairro + " não existe no Banco");
        }

    }

    public List<BairroDTO> buscarPorCodigoMunicipio(Integer codigoMunicipio) {
        if (verficarSeJaExisteNoBancoPorCodigoMunicipio(codigoMunicipio)) {
            List<Bairro> bairros = bairroRepository.findAllByCodigoMunicipio(codigoMunicipio);
            List<BairroDTO> bairrosDTO = bairros.stream().map(bairro -> new BairroDTO(bairro)).collect(Collectors.toList());
            return bairrosDTO;
        } else {
            throw new ServiceException("Bairro com o codigo municipio: " + codigoMunicipio + " não existe no Banco");
        }
    }

    public List<BairroDTO> buscarStatus(Integer status) {
        List<Bairro> bairros = bairroRepository.findAllStatus(status);
        List<BairroDTO> bairrosDTO = bairros.stream().map(bairro -> new BairroDTO(bairro)).collect(Collectors.toList());
        return bairrosDTO;
    }

    public void alterar(BairroDTO bairroDTO) {
        if (verificarSeBairroJaExisteNoBancoPorCodigoBairro(bairroDTO.getCodigoBairro())) {
            Bairro bairroAntigo = bairroRepository.findByCodigoBairro(bairroDTO.getCodigoBairro());
            alterarCampos(bairroDTO, bairroAntigo);
            bairroRepository.save(bairroAntigo);
        } else {
            throw new ServiceException("Bairro com codigoBairro: " + bairroDTO.getCodigoBairro() + " não existe no banco");
        }
    }

    public List<BairroDTO> buscarPorFiltro(Map<String, String> parametros) {
        List<BairroDTO> bairroDTOS = new ArrayList<>();
        if (parametros == null || parametros.isEmpty()) {
            return bairroDTOS = bairroRepository.findAll().stream().map(bairro -> new BairroDTO(bairro)).collect(Collectors.toList());
        }
        Specification<Bairro> specification = getBairroSpecification(parametros);

        List<Bairro> bairros = bairroRepository.findAll(specification);
        bairroDTOS = bairros.stream().map(bairro -> new BairroDTO(bairro)).collect(Collectors.toList());
        return bairroDTOS;
    }

    private Specification<Bairro> getBairroSpecification(Map<String, String> parametros) {

        Specification<Bairro> specification = null;
        Integer status = null;
        Integer codigoBairro = null;
        Integer codigoMunicipio = null;
        if (parametros.get("status") != null) {
            status = Integer.parseInt(parametros.get("status"));
        }
        if (parametros.get("codigoBairro") != null) {
            codigoBairro = Integer.parseInt(parametros.get("codigoBairro"));
        }
        if (parametros.get("codigoMunicipio") != null) {
            codigoMunicipio = Integer.parseInt(parametros.get("codigoMunicipio"));
        }

        if (parametros.get("status") != null && !parametros.get("status").isEmpty()) {
            Integer finalCodigoBairro = codigoBairro;
            Integer finalCodigoMunicipio = codigoMunicipio;
            specification = Optional.ofNullable(where(BairoSpecification.buscarPorStatus(status)))
                    .map(spec -> spec.and(BairoSpecification.buscarPorCodigoMunicipio(finalCodigoMunicipio))).
                    map(spec -> spec.and(BairoSpecification.buscarPorCodigoBairro(finalCodigoBairro)))
                    .map(spec -> spec.and(BairoSpecification.buscarPorNome(parametros.get("nome"))))
                    .orElse(null);
        }
        if (parametros.get("nome") != null && !parametros.get("nome").isEmpty()) {
            Integer finalCodigoBairro = codigoBairro;
            Integer finalCodigoMunicipio = codigoMunicipio;
            Integer finalStatus = status;
            specification = Optional.ofNullable(where(BairoSpecification.buscarPorNome(parametros.get("nome"))))
                    .map(spec -> spec.and(BairoSpecification.buscarPorCodigoMunicipio(finalCodigoMunicipio))).
                    map(spec -> spec.and(BairoSpecification.buscarPorCodigoBairro(finalCodigoBairro)))
                    .map(spec -> spec.and(BairoSpecification.buscarPorStatus(finalStatus)))
                    .orElse(null);
        }

        if (parametros.get("codigoBairro") != null && !parametros.get("codigoBairro").isEmpty()) {
            Integer finalCodigoBairro = codigoBairro;
            Integer finalCodigoMunicipio = codigoMunicipio;
            Integer finalStatus = status;
            specification = Optional.ofNullable(where(BairoSpecification.buscarPorCodigoBairro(codigoBairro)))
                    .map(spec -> spec.and(BairoSpecification.buscarPorCodigoMunicipio(finalCodigoMunicipio))).
                    map(spec -> spec.and(BairoSpecification.buscarPorNome(parametros.get("nome"))))
                    .map(spec -> spec.and(BairoSpecification.buscarPorStatus(finalStatus)))
                    .orElse(null);
        }
        if (parametros.get("codigoMunicipio") != null && !parametros.get("codigoMunicipio").isEmpty()) {
            Integer finalCodigoBairro = codigoBairro;
            Integer finalCodigoMunicipio = codigoMunicipio;
            Integer finalStatus = status;
            specification = Optional.ofNullable(where(BairoSpecification.buscarPorCodigoMunicipio(finalCodigoMunicipio)))
                    .map(spec -> spec.and(BairoSpecification.buscarPorCodigoBairro(finalCodigoBairro))).
                    map(spec -> spec.and(BairoSpecification.buscarPorNome(parametros.get("nome"))))
                    .map(spec -> spec.and(BairoSpecification.buscarPorStatus(finalStatus)))
                    .orElse(null);
        }

        return specification;
    }

    private boolean verficarSeJaExisteNoBancoPorCodigoMunicipio(Integer codigoMunicipio) {
        List<Bairro> bairroExistenteCodigoUf = bairroRepository.findAllByCodigoMunicipio(codigoMunicipio);
        if (bairroExistenteCodigoUf != null) {
            return true;
        } else {
            return false;
        }
    }

    private void alterarCampos(BairroDTO bairroDTO, Bairro bairroAntigo) {

        Municipio municipio = municipioRepository.findByCodigoMunicipio(bairroDTO.getCodigoMunicipio());
        if (municipio != null) {
            bairroAntigo.setNome(bairroDTO.getNome().toUpperCase());
            if(bairroDTO.getStatus() == 1 || bairroDTO.getStatus() == 2) {
                bairroAntigo.setStatus(bairroDTO.getStatus());
                bairroAntigo.setMunicipio(municipio);
            } else{
                throw new ServiceException("Valor para status não válido!");
            }

        } else {
            throw new ServiceException("Municipio com codigoMunicipio: " + bairroDTO.getCodigoMunicipio() + " não existe no banco");
        }


    }

    private boolean verificarSeBairroJaExisteNoBancoPorCodigoBairro(Integer codigoBairro) {
        Bairro bairroExistenteCodigoBairro = bairroRepository.findByCodigoBairro(codigoBairro);
        if (bairroExistenteCodigoBairro != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean verificarSeBairroJaExisteNoBancoPorNome(String nome) {
        Bairro bairroExistenteNome = bairroRepository.findByNome(nome);
        if (bairroExistenteNome != null) {
            return true;
        } else {
            return false;
        }
    }

}
