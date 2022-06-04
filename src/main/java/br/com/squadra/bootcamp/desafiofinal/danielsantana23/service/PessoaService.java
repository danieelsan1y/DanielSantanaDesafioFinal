package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.EnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaEnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaSalvarDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Endereco;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.BairroRepository;
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

    @Autowired
    BairroRepository bairroRepository;

    public void salvar(PessoaSalvarDTO pessoaSalvarDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaSalvarDTO.getNome());
        pessoa.setStatus(pessoaSalvarDTO.getStatus());
        pessoa.setLogin(pessoaSalvarDTO.getLogin());
        pessoa.setSenha(pessoaSalvarDTO.getSenha());
        pessoa.setSobrenome(pessoaSalvarDTO.getSobrenome());
        pessoaRepository.save(pessoa);
        salvarEnderecos(pessoa,pessoaSalvarDTO.getEnderecosDTO());

    }

    private void salvarEnderecos(Pessoa pessoa, List<EnderecoDTO> enderecos) {
        enderecos.stream().map(endereco -> {
            Endereco novosEnderecos = new Endereco();
            Bairro bairro = bairroRepository.findByCodigoBairro(endereco.getCodigoBairro());
            novosEnderecos.setBairro(bairro);
            novosEnderecos.setPessoa(pessoa);
            novosEnderecos.setCep(endereco.getCep());
            novosEnderecos.setNomeRua(endereco.getNomeRua());
            novosEnderecos.setNumero(endereco.getNumero());
            novosEnderecos.setComplemento(endereco.getComplemento());
            novosEnderecos.setStatus(1);
            enderecoRepository.save(novosEnderecos);


            return novosEnderecos;
        }).collect(Collectors.toList());
    }

    public List<PessoaDTO> buscarTodas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        List<PessoaDTO> pesoasDTO = pessoas.stream().map(pessoa -> new PessoaDTO(pessoa)).collect(Collectors.toList());
        return pesoasDTO;
    }

    public PessoaEnderecoDTO buscarPessoaPorCodigoPessoa(Integer codigoPessoa) {
        if (verficarSeJaExisteNoBancoPorCodigoPessoa(codigoPessoa)) {
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
