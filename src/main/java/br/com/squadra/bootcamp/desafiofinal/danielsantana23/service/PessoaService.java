package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.EnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaEnderecoDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.PessoaSalvarAtrerarDTO;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Endereco;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.BairroRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.EnderecoRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.PessoaRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.exeption.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void salvar(PessoaSalvarAtrerarDTO pessoaSalvarDTO) {

        pessoaSalvarDTO.setLogin(pessoaSalvarDTO.getLogin().toLowerCase());
        if (!verficarSeJaExisteNoBancoPorLogin(pessoaSalvarDTO.getLogin())) {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(pessoaSalvarDTO.getNome());
            pessoa.setStatus(pessoaSalvarDTO.getStatus());
            pessoa.setLogin(pessoaSalvarDTO.getLogin());
            pessoa.setSenha(pessoaSalvarDTO.getSenha());
            pessoa.setSobrenome(pessoaSalvarDTO.getSobrenome());
            pessoaRepository.save(pessoa);
            salvarEnderecos(pessoa, pessoaSalvarDTO.getEnderecosDTO());
        } else {
            throw new ServiceException("Login " + pessoaSalvarDTO.getLogin() + " já cadastrado no banco!");
        }


    }

    public void atualizar(PessoaSalvarAtrerarDTO pessoaSalvarDTO, Integer codigoPessoa) {
        Pessoa pessoaAntiga = pessoaRepository.findByCodigoPessoa(codigoPessoa);
        if (verficarSeJaExisteNoBancoPorCodigoPessoa(codigoPessoa)) {
            pessoaAntiga.setSobrenome(pessoaSalvarDTO.getSobrenome().toUpperCase());
            pessoaAntiga.setNome(pessoaSalvarDTO.getNome().toUpperCase());
            pessoaAntiga.setSenha(pessoaSalvarDTO.getSenha());
            pessoaAntiga.setStatus(pessoaAntiga.getStatus());
            pessoaAntiga.setLogin(pessoaSalvarDTO.getLogin().toLowerCase());
            atualizarEndereco(pessoaAntiga, pessoaSalvarDTO.getEnderecosDTO());
            pessoaRepository.save(pessoaAntiga);
        } else {
            throw new ServiceException("Pessoa com codigoPessoa: " + codigoPessoa + ", não esta cadastrado no Banco!");
        }
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

    private void atualizarEndereco(Pessoa pessoaAntiga, List<EnderecoDTO> enderecos) {
        List<Endereco> enderecosCadastrados = new ArrayList<>();
        enderecos.stream().map(endereco -> {
            Endereco enderecoAntigo = enderecoRepository.findByCodigoEndereco(endereco.getCodigoEndereco());
            if (enderecoAntigo != null) {
                Bairro bairro = bairroRepository.findByCodigoBairro(endereco.getCodigoBairro());
                //enderecoAntigo.setStatus(endereco.getStatus());
                enderecoAntigo.setComplemento(endereco.getComplemento());
                enderecoAntigo.setNumero(endereco.getNumero());
                enderecoAntigo.setNomeRua(endereco.getNomeRua());
                enderecoAntigo.setCep(endereco.getCep());
                enderecoAntigo.setBairro(bairro);
                enderecoRepository.save(enderecoAntigo);
                return enderecoAntigo;
            } else {
                if (endereco.getCodigoEndereco().equals(0)) {
                    Endereco enderecoNovo = new Endereco();
                    Bairro bairro = bairroRepository.findByCodigoBairro(endereco.getCodigoBairro());
                    if(bairro != null) {
                        enderecoNovo.setPessoa(pessoaAntiga);
                        enderecoNovo.setBairro(bairro);
                        enderecoNovo.setStatus(1);
                        enderecoNovo.setComplemento(endereco.getComplemento());
                        enderecoNovo.setNomeRua(endereco.getNomeRua());
                        enderecoNovo.setCep(endereco.getCep());
                        enderecoNovo.setNumero(endereco.getNumero());
                        enderecoRepository.save(enderecoNovo);
                        enderecosCadastrados.add(enderecoNovo);
                    } else {
                        throw new ServiceException("Bairro com codigoBairro: " + endereco.getCodigoBairro() + ", não esta cadastrado no Banco!");
                    }

                } else {
                    throw new ServiceException("Pessoa com codigoEndereco: " + endereco.getCodigoEndereco() + ", não esta cadastrado no Banco!");
                }
            }
            return null;
        }).collect(Collectors.toList());
        List<Endereco> todosEnderecos = enderecoRepository.findAllByCodigoPessoa(pessoaAntiga.getCodigoPessoa());
        todosEnderecos.removeAll(enderecosCadastrados);

        if(!todosEnderecos.isEmpty()) {
            enderecoRepository.deleteAll(todosEnderecos);
        }


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


    private boolean verficarSeJaExisteNoBancoPorLogin(String login) {
        Pessoa PessoaExistenteLogin = pessoaRepository.findByLogin(login);
        if (PessoaExistenteLogin != null) {
            return true;
        } else {
            return false;
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
