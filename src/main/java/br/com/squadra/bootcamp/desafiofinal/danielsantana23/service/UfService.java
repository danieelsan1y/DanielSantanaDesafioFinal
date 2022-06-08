package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.UfDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification.UfSpecification;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
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
public class UfService {

    @Autowired
    private UfRepository ufRepository;

    public void salvar(UfDTO ufDTO) {
        Uf uf = new Uf();
        if(!(ufDTO.getSigla().length() > 3) ) {

            if(ufDTO.getStatus() == 1 || ufDTO.getStatus() == 2) {
                uf.setStatus(ufDTO.getStatus());
                uf.setSigla(ufDTO.getSigla());
                uf.setNome(ufDTO.getNome());
                converterParaMaiusculo(uf);
                if (!verficarSeJaExisteNoBancoPorNome(uf.getNome())) {
                    ufRepository.save(uf);
                    UfDTO novoUfDTO = new UfDTO();
                } else {
                    throw new ServiceException("Uf já cadastrado no banco!");
                }
            } else{
                throw new ServiceException("Valor para status não válido!");
            }

        } else {
            throw new ServiceException("Tamanho de sigla não permitido!");
        }

    }

    public List<UfDTO> buscarTodos() {
        List<Uf> ufs = ufRepository.findAll();
        List<UfDTO> ufsDTO = ufs.stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        return ufsDTO;
    }

    public void alterar(UfDTO ufDTO) {
        if (verficarSeJaExisteNoBancoPorCodigoUf(ufDTO.getCodigoUf())) {
            Uf ufNovo = converterParaUf(ufDTO);
            Uf ufAntigo = ufRepository.findByCodigoUf(ufDTO.getCodigoUf());
            alterarCampos(ufNovo, ufAntigo);
            ufRepository.save(ufAntigo);
        } else {
            throw new ServiceException("Uf com o codigoUf: " + ufDTO.getCodigoUf() + " não esta cadastrado no Banco!");
        }

    }

    public List<UfDTO> buscarPorFiltro(Map<String, String> parametros) {
        List<UfDTO> ufsDTO = new ArrayList<>();
        if (parametros == null || parametros.isEmpty()) {
            return ufsDTO = ufRepository.findAll().stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        }
        Specification<Uf> specification = getUfSpecification(parametros);

        List<Uf> ufs = ufRepository.findAll(specification);
        ufsDTO = ufs.stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        return ufsDTO;
    }

    private Specification<Uf> getUfSpecification(Map<String, String> parametros) {
        Specification<Uf> specification = null;

        Integer status = null;
        Integer codigoUf = null;
        if (parametros.get("status") != null) {
            status = Integer.parseInt(parametros.get("status"));
        }
        if (parametros.get("codigoUf") != null) {
            codigoUf = Integer.parseInt(parametros.get("codigoUf"));
        }

        if (parametros.get("status") != null && !parametros.get("status").isEmpty()) {
            Integer finalCodigoUf = codigoUf;
            specification = Optional.ofNullable(where(UfSpecification.buscarPorStatus(status)))
                    .map(spec -> spec.and(UfSpecification.buscarPorSigla(parametros.get("sigla"))))
                    .map(spec -> spec.and(UfSpecification.buscarPorNome(parametros.get("nome"))))
                    .map(spec -> spec.and(UfSpecification.buscarPorStatus(finalCodigoUf)))
                    .orElse(null);
        }
        if (parametros.get("nome") != null && !parametros.get("nome").isEmpty()) {
            Integer finalStatus = status;
            Integer finalCodigoUf = codigoUf;
            specification = Optional.ofNullable(where(UfSpecification.buscarPorNome(parametros.get("nome"))))
                    .map(spec -> spec.and(UfSpecification.buscarPorSigla(parametros.get("sigla"))))
                    .map(spec -> spec.and(UfSpecification.buscarPorStatus(finalStatus)))
                    .map(spec -> spec.and(UfSpecification.buscarPorStatus(finalCodigoUf)))
                    .orElse(null);
        }

        if (parametros.get("sigla") != null && !parametros.get("sigla").isEmpty()) {
            Integer finalStatus = status;
            Integer finalCodigoUf = codigoUf;
            specification = Optional.ofNullable(where(UfSpecification.buscarPorSigla(parametros.get("sigla"))))
                    .map(spec -> spec.and(UfSpecification.buscarPorNome(parametros.get("nome"))))
                    .map(spec -> spec.and(UfSpecification.buscarPorStatus(finalStatus)))
                    .map(spec -> spec.and(UfSpecification.buscarPorStatus(finalCodigoUf)))
                    .orElse(null);
        }
        if (parametros.get("codigoUf") != null && !parametros.get("codigoUf").isEmpty()) {
            Integer finalStatus = status;
            Integer finalCodigoUf = codigoUf;
            specification = Optional.ofNullable(where(UfSpecification.buscarPorCodigoUf(codigoUf)))
                    .map(spec -> spec.and(UfSpecification.buscarPorNome(parametros.get("nome"))))
                    .map(spec -> spec.and(UfSpecification.buscarPorStatus(finalStatus)))
                    .map(spec -> spec.and(UfSpecification.buscarPorSigla(parametros.get("sigla"))))
                    .orElse(null);
        }
        return specification;
    }

    private void alterarCampos(Uf ufNovo, Uf ufAntigo) {
        if(!(ufAntigo.getSigla().length()>3)) {
            ufAntigo.setSigla(ufNovo.getSigla());
            ufAntigo.setNome(ufNovo.getNome());
            if(ufAntigo.getStatus() == 1 || ufAntigo.getStatus() == 2) {
                ufAntigo.setStatus(ufNovo.getStatus());
                converterParaMaiusculo(ufAntigo);
            } else{
                throw new ServiceException("Valor para status não válido!");
            }
        }
        else {
            throw new ServiceException("Tamanho de sigla não permitido!");
        }
    }

    private void converterParaMaiusculo(Uf uf) {
        uf.setNome(uf.getNome().toUpperCase());
        uf.setSigla(uf.getSigla().toUpperCase());
    }

    private boolean verficarSeJaExisteNoBancoPorCodigoUf(Integer codigoUf) {
        Uf ufExistenteCodigo = ufRepository.findByCodigoUf(codigoUf);
        if (ufExistenteCodigo != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean verficarSeJaExisteNoBancoPorNome(String nome) {
        Uf ufExistenteNome = ufRepository.findByNome(nome);
        if (ufExistenteNome != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean verificarSeJaExisteNoBancoPorSigla(String sigla) {
        Uf ufExistenteSigla = ufRepository.findBySigla(sigla);
        if (ufExistenteSigla != null) {
            return true;
        } else {
            return false;
        }
    }

    private Uf converterParaUf(UfDTO ufDTO) {
        Uf uf = new Uf(ufDTO.getCodigoUf(), ufDTO.getSigla(), ufDTO.getNome(), ufDTO.getStatus());
        return uf;
    }
}
