package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.PessoaEnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.EnderecoRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.PessoaRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.exeption.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    EnderecoRepository enderecoRepository;


    public List<PessoaDTO> buscarTodas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        List<PessoaDTO> pesoasDTO = pessoas.stream().map(pessoa -> new PessoaDTO(pessoa)).collect(Collectors.toList());
        return pesoasDTO;
    }

    public PessoaEnderecoDTO buscarPessoaPorCodigoPessoa(Integer codigoPessoa) {
        if(verficarSeJaExisteNoBancoPorCodigoPessoa(codigoPessoa)) {
            Pessoa pessoa = pessoaRepository.findByCodigoPessoa(codigoPessoa);
            PessoaEnderecoDTO pessoaRelacionamentoDTO = new PessoaEnderecoDTO(pessoa);
            return pessoaRelacionamentoDTO;
        } else {
            throw new ServiceException("Pessoa com codigoPessoa: " + codigoPessoa + ", não esta cadastrado no Banco!");
        }

    }

    private boolean verficarSeJaExisteNoBancoPorCodigoPessoa(Integer codigoPessoa) {
        Pessoa PessoaExistenteCodigo = pessoaRepository.findByCodigoPessoa(codigoPessoa);
        if (PessoaExistenteCodigo != null) {
            return true;
        } else {
            return false;
        }
    }

}
