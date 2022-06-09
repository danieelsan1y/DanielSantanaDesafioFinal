package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.UfDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification.UfSpecification;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.Uf;
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
        if (!(ufDTO.getSigla().length() > 3)) {

            if (ufDTO.getStatus() == 1 || ufDTO.getStatus() == 2) {
                uf.setStatus(ufDTO.getStatus());
                uf.setSigla(ufDTO.getSigla());
                uf.setNome(ufDTO.getNome());
                converterParaMaiusculo(uf);
                if (!verficarSeJaExisteNoBancoPorNome(uf.getNome())) {
                    if (!verificarSeJaExisteNoBancoPorSigla(uf.getSigla())) {
                        ufRepository.save(uf);
                    } else {
                        throw new ServiceException("UF com essa sigla já cadastrado no banco!");
                    }

                } else {
                    throw new ServiceException("UF com esse nome já cadastrado no banco!");
                }
            } else {
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
        if (verficarSeJaExisteNoBancoPorCodigoUf(ufDTO.getCodigoUF())) {
            Uf ufNovo = converterParaUf(ufDTO);
            Uf ufAntigo = ufRepository.findByCodigoUf(ufDTO.getCodigoUF());
            alterarCampos(ufNovo, ufAntigo);
            ufRepository.save(ufAntigo);
        } else {
            throw new ServiceException("Uf com o codigoUF: " + ufDTO.getCodigoUF() + " não esta cadastrado no Banco!");
        }

    }

    public Object buscarPorFiltro(Map<String, String> parametros) {
        List<UfDTO> ufsDTO = new ArrayList<>();
        if (parametros == null || parametros.isEmpty()) {
            return ufsDTO = ufRepository.findAll().stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        }
        Specification<Uf> specification = getUfSpecification(parametros);

        List<Uf> ufs = ufRepository.findAll(specification);
        ufsDTO = ufs.stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());

        if (parametros.size() == 1 && parametros.get("status") != null) {
            return ufsDTO;
        } else {
            if (ufsDTO.size() != 0) {
                return ufsDTO.stream().findFirst().get();
            }
            return new ArrayList<>();
        }


    }

    private Specification<Uf> getUfSpecification(Map<String, String> parametros) {
        Specification<Uf> specification = null;

        Integer status = null;
        Integer codigoUf = null;
        if (parametros.get("status") != null) {
            status = Integer.parseInt(parametros.get("status"));
        }
        if (parametros.get("codigoUF") != null) {
            codigoUf = Integer.parseInt(parametros.get("codigoUF"));
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
        if (parametros.get("codigoUF") != null && !parametros.get("codigoUF").isEmpty()) {
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
        if (!verficarSeJaExisteNoBancoPorNome(ufNovo.getNome().toUpperCase()) || ufNovo.getNome().toUpperCase().equals(ufAntigo.getNome())) {
            if (!(ufAntigo.getSigla().length() > 3)) {
                if (!verificarSeJaExisteNoBancoPorSigla(ufNovo.getSigla().toUpperCase()) || ufNovo.getSigla().toUpperCase().equals(ufAntigo.getSigla())) {
                    ufAntigo.setSigla(ufNovo.getSigla());
                    ufAntigo.setNome(ufNovo.getNome());
                    if (ufNovo.getStatus() == 1 || ufNovo.getStatus() == 2) {
                        ufAntigo.setStatus(ufNovo.getStatus());
                        converterParaMaiusculo(ufAntigo);
                    } else {
                        throw new ServiceException("Valor para status não válido!");
                    }
                } else {
                    throw new ServiceException("Sigla já existe no banco");
                }

            } else {
                throw new ServiceException("Tamanho de sigla não permitido!");
            }
        } else {
            throw new ServiceException("UF com esse nome já cadastrado no banco!");
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
        Uf uf = new Uf(ufDTO.getCodigoUF(), ufDTO.getSigla(), ufDTO.getNome(), ufDTO.getStatus());
        return uf;
    }
}
