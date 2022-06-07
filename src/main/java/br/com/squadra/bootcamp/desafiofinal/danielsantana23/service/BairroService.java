package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.BairroDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
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

    public void salvar(BairroDTO bairroDTO) {
        bairroDTO.setNome(bairroDTO.getNome().toUpperCase());
        if (!verificarSeBairroJaExisteNoBancoPorNome(bairroDTO.getNome())) {
            Municipio municipio = municipioRepository.findByCodigoMunicipio(bairroDTO.getCodigoMunicipio());
            if(municipio != null) {
                Bairro bairro = new Bairro();
                bairro.setStatus(bairroDTO.getStatus());
                bairro.setMunicipio(municipio);
                bairro.setNome(bairroDTO.getNome());
                bairroRepository.save(bairro);
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

    public List<BairroDTO> buscarPorCodigoMunicipio (Integer codigoMunicipio) {
        if(verficarSeJaExisteNoBancoPorCodigoMunicipio(codigoMunicipio)) {
            List<Bairro> bairros = bairroRepository.findAllByCodigoMunicipio(codigoMunicipio);
            List<BairroDTO> bairrosDTO = bairros.stream().map(bairro -> new BairroDTO(bairro)).collect(Collectors.toList());
            return  bairrosDTO;
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
        if (verificarSeBairroJaExisteNoBancoPorCodigoBairro(bairroDTO.getCodigoBairro())){
            Bairro bairroAntigo = bairroRepository.findByCodigoBairro(bairroDTO.getCodigoBairro());
            alterarCampos(bairroDTO, bairroAntigo);
            bairroRepository.save(bairroAntigo);
        } else {
            throw new ServiceException("Bairro com codigoBairro: "+bairroDTO.getCodigoBairro()+ " não existe no banco");
        }
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
        if(municipio != null) {
            bairroAntigo.setNome(bairroDTO.getNome().toUpperCase());
            bairroAntigo.setStatus(bairroDTO.getStatus());
            bairroAntigo.setMunicipio(municipio);
        } else {
            throw new ServiceException("Municipio com codigoMunicipio: "+bairroDTO.getCodigoMunicipio()+ " não existe no banco");
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
