package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Pessoa_;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf_;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

public class PessoaSpecification {


    public static Specification<Pessoa> buscarPorLogin(final String login) {
        return Strings.isBlank(login) ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Pessoa_.LOGIN), login);
    }

    public static Specification<Pessoa> buscarPorStatus(final Integer status) {
        return status == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Pessoa_.STATUS), status);
    }

    public static Specification<Pessoa> buscarPorCodigoPessoa(final Integer codigoPessoa) {
        return codigoPessoa == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Pessoa_.CODIGO_PESSOA), codigoPessoa);
    }
}
