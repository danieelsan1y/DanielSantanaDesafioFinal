package br.com.squadra.bootcamp.desafiofinal.danielsantana23.test;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Bairro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.BairroRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.MunicipioRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.UfRepository;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.BairroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration


public class TestConfig implements CommandLineRunner {
    @Autowired
    private UfRepository ufRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @Override
    public void run(String... args) throws Exception {

        Uf uf = new Uf(null, "GO", "GOIÁS", 1);
        Uf uf2 = new Uf(null, "SP", "SÃO PAULO", 1);
        Uf uf3 = new Uf(null, "MG", "MINAS GERAIS", 1);
        Uf uf4 = new Uf(null, "MT", "MATO GROSSO", 1);

        ufRepository.saveAll(Arrays.asList(uf, uf2, uf3, uf4));
        Municipio mun = new Municipio(null, uf, "ANÁPOLIS", 1);
        Municipio mun2 = new Municipio(null, uf2, "JUNDIAÍ", 1);
        Municipio mun3= new Municipio(null, uf3, "PARACATU", 1);
        Municipio mun4 = new Municipio(null, uf4, "CUIABÁ", 1);
        municipioRepository.saveAll(Arrays.asList(mun, mun2, mun3, mun4));

        Bairro bai = new Bairro(null,mun,"CENTRO",1);
        Bairro bai2 = new Bairro(null,mun2,"SETOR BUENO",1);
        Bairro bai3 = new Bairro(null,mun3,"BENEDITO LABOSSIERE",1);
        Bairro bai4 = new Bairro(null,mun4   ,"LAPA",1);

        bairroRepository.saveAll(Arrays.asList(bai, bai2, bai3, bai4));

    }


}
