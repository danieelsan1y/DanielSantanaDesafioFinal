package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

public class MunicipioSpecification {

    public static Specification<Municipio> buscarPorCodigoUf(final Integer codigoUf) {
        return codigoUf == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Municipio_.UF), codigoUf);
    }

    public static Specification<Municipio> buscarPorStatus(final Integer status) {
        return status == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Municipio_.STATUS), status);
    }
    public static Specification<Municipio> buscarPorCodigoMunicipio(final Integer codigoMunicipio) {
        return codigoMunicipio == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Municipio_.CODIGO_MUNICIPIO), codigoMunicipio);
    }
    public static Specification<Municipio> buscarPorNome(final String nome) {
        return Strings.isBlank(nome) ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Municipio_.NOME), nome);
    }
}
