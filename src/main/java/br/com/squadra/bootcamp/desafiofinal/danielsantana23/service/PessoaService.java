package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.MunicipioDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Endereco;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.EnderecoRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.PessoaRepository;
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
        List<PessoaDTO> pessoaDTOS = pessoas.stream().map(pessoa -> new PessoaDTO(pessoa)).collect(Collectors.toList());
        return pessoaDTOS;
    }

}
