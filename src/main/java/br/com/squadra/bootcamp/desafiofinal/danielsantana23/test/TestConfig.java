package br.com.squadra.bootcamp.desafiofinal.danielsantana23.test;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.model.entities.*;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration


public class TestConfig implements CommandLineRunner {
    @Autowired
    private UfRepository ufRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public void run(String... args) throws Exception {

        Uf uf = new Uf(null, "GO", "GOIÁS", 1);
        Uf uf2 = new Uf(null, "SP", "SÃO PAULO", 1);
        Uf uf3 = new Uf(null, "MG", "MINAS GERAIS", 1);
        Uf uf4 = new Uf(null, "MT", "MATO GROSSO", 1);

       // ufRepository.saveAll(Arrays.asList(uf, uf2, uf3, uf4));
        Municipio mun = new Municipio(null, uf, "ANÁPOLIS", 1);
        Municipio mun2 = new Municipio(null, uf2, "JUNDIAÍ", 1);
        Municipio mun3= new Municipio(null, uf3, "PARACATU", 1);
        Municipio mun4 = new Municipio(null, uf4, "CUIABÁ", 1);
       // municipioRepository.saveAll(Arrays.asList(mun, mun2, mun3, mun4));

        Bairro bai = new Bairro(null,mun,"CENTRO",1);
        Bairro bai2 = new Bairro(null,mun2,"SETOR BUENO",1);
        Bairro bai3 = new Bairro(null,mun3,"BENEDITO LABOSSIERE",1);
        Bairro bai4 = new Bairro(null,mun4   ,"LAPA",1);

       // bairroRepository.saveAll(Arrays.asList(bai, bai2, bai3, bai4));



        Pessoa pes1= new Pessoa(null,"DANIEL","SANTANA","daniel.santana","D@123",1);
        Pessoa pes2= new Pessoa(null,"DANILLO","SANTANA","danillo.santana","D@ddd",1);
        Pessoa pes3= new Pessoa(null,"DANIELLE","SANTANA","danillo.santana","99999",1);
        Pessoa pes4= new Pessoa(null,"JOSUÉ","SANTANA","josue.santana","222",1);

      //  pessoaRepository.saveAll(Arrays.asList(pes1,pes2,pes3,pes4));

        Endereco end1 = new Endereco(null,bai,pes1,"RUA 1","22","LOTE 03","75115-740",1);
        Endereco end2 = new Endereco(null,bai2,pes1,"RUA 2" ,"23","LOTE 04","75115-640",2);
        Endereco end3 = new Endereco(null,bai3,pes1,"RUA 3","24","LOTE 05","75115-690",3);
        Endereco end4 = new Endereco(null,bai4,pes4,"RUA 4","25","LOTE 06","75115-600",4);

       // enderecoRepository.saveAll(Arrays.asList(end1, end2, end3, end4));
    }

}
