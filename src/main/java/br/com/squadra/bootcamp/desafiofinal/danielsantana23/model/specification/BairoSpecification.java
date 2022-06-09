package br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.specification;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.*;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.Bairro;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

public class BairoSpecification {



    public static Specification<Bairro> buscarPorCodigoMunicipio(final Integer codigoMunicipio) {
        return codigoMunicipio == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Bairro_.MUNICIPIO), codigoMunicipio);
    }

    public static Specification<Bairro> buscarPorNome(final String nome) {
        return Strings.isBlank(nome) ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Bairro_.NOME), nome);
    }
    public static Specification<Bairro> buscarPorStatus(final Integer status) {
        return status == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Bairro_.STATUS), status);
    }
    public static Specification<Bairro> buscarPorCodigoBairro(final Integer codigoBairro) {
        return codigoBairro == null ? null
                : (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Bairro_.CODIGO_BAIRRO), codigoBairro);
    }

}
