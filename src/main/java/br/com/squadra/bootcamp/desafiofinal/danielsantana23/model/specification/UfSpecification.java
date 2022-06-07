package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification;


import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf_;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

public class UfSpecification {

    public static Specification<Uf> buscarPorNome(final String nome) {
        return Strings.isBlank(nome) ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Uf_.NOME), nome);
    }
    public static Specification<Uf> buscarPorSigla(final String sigla) {
        return Strings.isBlank(sigla) ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Uf_.SIGLA), sigla);
    }

    public static Specification<Uf> buscarPorStatus(final Integer status) {
        return status == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Uf_.STATUS), status);
    }

    public static Specification<Uf> buscarPorCodigoUf(final Integer codigoUf) {
        return codigoUf == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Uf_.CODIGO_UF), codigoUf);
    }

}
