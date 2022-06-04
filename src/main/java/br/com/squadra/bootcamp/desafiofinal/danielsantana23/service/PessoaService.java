package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.comrelacionamento.PessoaRelacionamentoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.semrelacionamento.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.EnderecoRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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

    public PessoaRelacionamentoDTO buscarPessoaPorCodigoPessoa(Integer codigoPessoa) {
        Pessoa pessoa = pessoaRepository.findAllByCodigoPessoa(codigoPessoa);
        PessoaRelacionamentoDTO pessoaRelacionamentoDTO = new PessoaRelacionamentoDTO(pessoa);
        return pessoaRelacionamentoDTO;
    }

}
