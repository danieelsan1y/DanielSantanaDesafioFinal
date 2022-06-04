package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.UfDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.UfRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.exeption.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UfService {

    @Autowired
    private UfRepository ufRepository;

    public UfDTO salvar(UfDTO ufDTO) {
        Uf uf = converterParaUf(ufDTO);
        converterParaMaiusculo(uf);

        if(!verficarSeJaExisteNoBancoPorNome(uf.getNome())) {
            ufRepository.save(uf);
            UfDTO novoUfDTO = new UfDTO();
            return novoUfDTO;
        } else {
            throw new ServiceException("Uf já cadastrado no banco!");
        }
    }

    public List<UfDTO> buscarTodos() {
        List<Uf> ufs = ufRepository.findAll();
        List<UfDTO> ufsDTO = ufs.stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        return ufsDTO;
    }

    public void alterar(UfDTO ufDTO, Integer codigoUf) {
        if(verficarSeJaExisteNoBancoPorCodigoUf(codigoUf)) {
            Uf ufNovo = converterParaUf(ufDTO);
            Uf ufAntigo = ufRepository.findByCodigoUf(codigoUf);
            alterarCampos(ufNovo, ufAntigo);
            ufRepository.save(ufAntigo);
        } else {
            throw new ServiceException("Uf não existe no banco");
        }

    }

    public UfDTO buscarPorNome(String nome) {
        nome = nome.toUpperCase();

        if(verficarSeJaExisteNoBancoPorNome(nome)) {
            Uf uf = ufRepository.findByNome(nome);
            UfDTO ufDTO = new UfDTO(uf);
            return ufDTO;
        } else {
            throw new ServiceException("Uf não existe no banco");
        }

    }

    public UfDTO buscarPorSigla(String sigla) {
        sigla = sigla.toUpperCase();
        if(verificarSeJaExisteNoBancoPorSigla(sigla)) {
            Uf uf = ufRepository.findBySigla(sigla);
            UfDTO ufDTO = new UfDTO(uf);
            return ufDTO;
        } else {
            throw new ServiceException("Uf não existe no banco");
        }

    }

    public void alterarStatusUf(Integer codigoUf, Integer status) {

        Uf uf = ufRepository.findByCodigoUf(codigoUf);
        uf.setStatus(status);
        ufRepository.save(uf);
    }

    public List<UfDTO> buscarUmStatus(Integer status) {
        List<Uf> ufs = ufRepository.findAllAtivos(status);
        List<UfDTO> ufsDTO = ufs.stream().map(uf -> new UfDTO(uf)).collect(Collectors.toList());
        return ufsDTO;
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
