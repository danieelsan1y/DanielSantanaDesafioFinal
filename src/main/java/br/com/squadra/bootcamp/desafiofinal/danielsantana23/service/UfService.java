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
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class UfService {

    @Autowired
    private UfRepository ufRepository;

    public void salvar(UfDTO ufDTO) {
        Uf uf = new Uf();
        uf.setStatus(ufDTO.getStatus());
        uf.setSigla(ufDTO.getSigla());
        uf.setNome(ufDTO.getNome());
        converterParaMaiusculo(uf);

        if(!verficarSeJaExisteNoBancoPorNome(uf.getNome())) {
            ufRepository.save(uf);
            UfDTO novoUfDTO = new UfDTO();
        } else {
            throw new ServiceException("Uf já cadastrado no banco!");
        }
    }

    public List<UfDTO> buscarTodos() {
        List<Uf> ufs = ufRepository.findAll();
        List<UfDTO> ufsDTO = ufs.stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        return ufsDTO;
    }

    public void alterar(UfDTO ufDTO) {
        if(verficarSeJaExisteNoBancoPorCodigoUf(ufDTO.getCodigoUf())) {
            Uf ufNovo = converterParaUf(ufDTO);
            Uf ufAntigo = ufRepository.findByCodigoUf(ufDTO.getCodigoUf());
            alterarCampos(ufNovo, ufAntigo);
            ufRepository.save(ufAntigo);
        } else {
            throw new ServiceException("Uf com o codigoUf: " + ufDTO.getCodigoUf() + " não esta cadastrado no Banco!");
        }

    }

    public UfDTO buscarPorNome(String nome) {
        nome = nome.toUpperCase();

        if(verficarSeJaExisteNoBancoPorNome(nome)) {
            Uf uf = ufRepository.findByNome(nome);
            UfDTO ufDTO = new UfDTO(uf);
            return ufDTO;
        } else {
            throw new ServiceException("Uf com o nome: " +nome+ ", não existe no banco");
        }

    }

    public UfDTO buscarPorSigla(String sigla) {
        sigla = sigla.toUpperCase();
        if(verificarSeJaExisteNoBancoPorSigla(sigla)) {
            Uf uf = ufRepository.findBySigla(sigla);
            UfDTO ufDTO = new UfDTO(uf);
            return ufDTO;
        } else {
            throw new ServiceException("Uf com a sigla: "+sigla+",não existe no banco");
        }

    }

    public List<UfDTO> buscarUmStatus(Integer status) {
        List<Uf> ufs = ufRepository.findAllAtivos(status);
        List<UfDTO> ufsDTO = ufs.stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        return ufsDTO;
    }

    public List<UfDTO> buscarPorFiltro(Map<String,String> parametros) {
        List<UfDTO> ufsDTO = new ArrayList<>();
        if(parametros == null || parametros.isEmpty()) {
            return  ufsDTO = ufRepository.findAll().stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        }

        Specification<Uf> specification = where(UfSpecification.buscarPorNome(parametros.get("nome"))).
                and(UfSpecification.buscarPorSigla(parametros.get("sigla")).and(UfSpecification.buscarPorStatus(parametros.get("status"))));
        List<Uf> ufs = ufRepository.findAll(specification);
        ufsDTO = ufs.stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        return  ufsDTO;
    }
    private void alterarCampos(Uf ufNovo, Uf ufAntigo) {
        ufAntigo.setSigla(ufNovo.getSigla());
        ufAntigo.setNome(ufNovo.getNome());
        ufAntigo.setStatus(ufNovo.getStatus());
        converterParaMaiusculo(ufAntigo);
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

    private boolean verificarSeJaExisteNoBancoPorSigla (String sigla) {
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
