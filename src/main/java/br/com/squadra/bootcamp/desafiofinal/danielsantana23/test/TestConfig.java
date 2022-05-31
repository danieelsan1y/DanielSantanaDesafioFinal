package br.com.squadra.bootcamp.desafiofinal.danielsantana23.test;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Municipio;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.Uf;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration


public class TestConfig implements CommandLineRunner {
    @Autowired
    private UfRepository ufRepository;


    @Override
    public void run(String... args) throws Exception {

        Uf uf = new Uf(null,"GO","Goiás",1);
        ufRepository.save(uf);




    }


}
