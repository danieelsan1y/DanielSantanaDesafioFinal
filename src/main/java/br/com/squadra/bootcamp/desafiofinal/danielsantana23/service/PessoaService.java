package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.dto.*;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Endereco;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification.PessoaSpecification;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.BairroRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.EnderecoRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.PessoaRepository;
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
            if (pessoaSalvarDTO.getStatus() == 1 || pessoaSalvarDTO.getStatus() == 2) {
                pessoa.setStatus(pessoaSalvarDTO.getStatus());
                pessoa.setLogin(pessoaSalvarDTO.getLogin());
                pessoa.setSenha(pessoaSalvarDTO.getSenha());
                pessoa.setSobrenome(pessoaSalvarDTO.getSobrenome());
                salvarEnderecos(pessoa, pessoaSalvarDTO.getEnderecosDTO());
            } else {
                throw new ServiceException("Valor para status não válido!");
            }

        } else {
            throw new ServiceException("Login " + pessoaSalvarDTO.getLogin() + " já cadastrado no banco!");
        }

    }

    public void atualizar(PessoaSalvarAtrerarDTO pessoaSalvarDTO) {
        Pessoa pessoaAntiga = pessoaRepository.findByCodigoPessoa(pessoaSalvarDTO.getCodigoPessoa());
        if (verficarSeJaExisteNoBancoPorCodigoPessoa(pessoaSalvarDTO.getCodigoPessoa())) {
            pessoaAntiga.setSobrenome(pessoaSalvarDTO.getSobrenome().toUpperCase());
            pessoaAntiga.setNome(pessoaSalvarDTO.getNome().toUpperCase());
            pessoaAntiga.setSenha(pessoaSalvarDTO.getSenha());

            if(pessoaSalvarDTO.getStatus() == 1 || pessoaSalvarDTO.getStatus() == 2) {
                pessoaAntiga.setStatus(pessoaSalvarDTO.getStatus());
                pessoaAntiga.setLogin(pessoaSalvarDTO.getLogin().toLowerCase());
                atualizarEndereco(pessoaAntiga, pessoaSalvarDTO.getEnderecosDTO());
                pessoaRepository.save(pessoaAntiga);
            } else{
                throw new ServiceException("Valor para status não válido!");
            }

        } else {
            throw new ServiceException("Pessoa com codigoPessoa: " + pessoaSalvarDTO.getCodigoPessoa() + ", não esta cadastrado no Banco!");
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
        List<Endereco> todosEnderecos = enderecoRepository.findAllByCodigoPessoa(pessoaAntiga.getCodigoPessoa());
        List<Endereco> enderecosAtualizados = new ArrayList<>();
        List<Endereco> enderecosNovos = new ArrayList<>();
        enderecos.stream().map(endereco -> {

            Endereco enderecoAntigo = enderecoRepository.buscarEnderecoPorPessoaECodigo(pessoaAntiga.getCodigoPessoa(), endereco.getCodigoEndereco());
            if (enderecoAntigo != null) {
                enderecosAtualizados.add(enderecoAntigo);
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

                if (endereco.getCodigoEndereco().equals(0) || endereco.getCodigoEndereco() == null) {
                    Endereco enderecoNovo = new Endereco();
                    Bairro bairro = bairroRepository.findByCodigoBairro(endereco.getCodigoBairro());
                    if (bairro != null) {
                        enderecoNovo.setPessoa(pessoaAntiga);
                        enderecoNovo.setBairro(bairro);
                        enderecoNovo.setStatus(1);
                        enderecoNovo.setComplemento(endereco.getComplemento());
                        enderecoNovo.setNomeRua(endereco.getNomeRua());
                        enderecoNovo.setCep(endereco.getCep());
                        enderecoNovo.setNumero(endereco.getNumero());
                        enderecosNovos.add(enderecoNovo);
                    } else {
                        throw new ServiceException("Bairro com codigoBairro: " + endereco.getCodigoBairro() + ", não esta cadastrado no Banco!");
                    }

                } else {
                    throw new ServiceException("Pessoa com codigoEndereco: " + endereco.getCodigoEndereco() + ", não esta cadastrado no Banco!");
                }
            }
            return null;
        }).collect(Collectors.toList());

        todosEnderecos.removeAll(enderecosAtualizados);
        if (!todosEnderecos.isEmpty()) {
            enderecoRepository.deleteAll(todosEnderecos);
        }
        enderecoRepository.saveAll(enderecosNovos);
        System.out.println(todosEnderecos.size());


    }

    private void salvarEnderecos(Pessoa pessoa, List<EnderecoDTO> enderecos) {
        enderecos.stream().map(endereco -> {
            Endereco novosEnderecos = new Endereco();
            Bairro bairro = bairroRepository.findByCodigoBairro(endereco.getCodigoBairro());
            if (bairro != null) {
                pessoaRepository.save(pessoa);
                novosEnderecos.setBairro(bairro);
                novosEnderecos.setPessoa(pessoa);
                novosEnderecos.setCep(endereco.getCep());
                novosEnderecos.setNomeRua(endereco.getNomeRua());
                novosEnderecos.setNumero(endereco.getNumero());
                novosEnderecos.setComplemento(endereco.getComplemento());
                novosEnderecos.setStatus(1);
                enderecoRepository.save(novosEnderecos);
                return novosEnderecos;
            } else {
                throw new ServiceException("Bairro com codigoBairro: " + endereco.getCodigoBairro() + ", não esta cadastrado no Banco!");
            }

        }).collect(Collectors.toList());
    }

    public List<PessoaDTO> buscarPorFiltro(Map<String, String> parametros) {
        List<PessoaDTO> pessoaDTOS = new ArrayList<>();
        if (parametros == null || parametros.isEmpty()) {
            return pessoaDTOS = pessoaRepository.findAll().stream().map(pessoa -> new PessoaDTO(pessoa)).collect(Collectors.toList());
        }
        Specification<Pessoa> specification = getMunicipioSpecification(parametros);

        List<Pessoa> pessoas = pessoaRepository.findAll(specification);
        pessoaDTOS = pessoas.stream().map(pessoa -> new PessoaDTO(pessoa)).collect(Collectors.toList());
        return pessoaDTOS;
    }

    private Specification<Pessoa> getMunicipioSpecification(Map<String, String> parametros) {

        Specification<Pessoa> specification = null;
        Integer status = null;
        Integer codigoPessoa = null;
        if (parametros.get("status") != null) {
            status = Integer.parseInt(parametros.get("status"));
        }
        if (parametros.get("codigoPessoa") != null) {
            codigoPessoa = Integer.parseInt(parametros.get("codigoPessoa"));
        }

        if (parametros.get("status") != null && !parametros.get("status").isEmpty()) {
            Integer finalCodigoPessoa = codigoPessoa;
            specification = Optional.ofNullable(where(PessoaSpecification.buscarPorStatus(status)))
                    .map(spec -> spec.and(PessoaSpecification.buscarPorLogin(parametros.get("login")))).
                    map(spec -> spec.and(PessoaSpecification.buscarPorCodigoPessoa(finalCodigoPessoa)))
                    .orElse(null);
        }

        if (parametros.get("login") != null && !parametros.get("login").isEmpty()) {
            Integer finalCodigoPessoa = codigoPessoa;
            Integer finalStatus = status;
            specification = Optional.ofNullable(where(PessoaSpecification.buscarPorLogin(parametros.get("login"))))
                    .map(spec -> spec.and(PessoaSpecification.buscarPorStatus(finalStatus))).
                    map(spec -> spec.and(PessoaSpecification.buscarPorCodigoPessoa(finalCodigoPessoa)))
                    .orElse(null);
        }

        return specification;
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
