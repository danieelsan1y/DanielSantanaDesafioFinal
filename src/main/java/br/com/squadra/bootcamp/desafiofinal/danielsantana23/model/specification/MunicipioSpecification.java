package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio_;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf_;
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
}
