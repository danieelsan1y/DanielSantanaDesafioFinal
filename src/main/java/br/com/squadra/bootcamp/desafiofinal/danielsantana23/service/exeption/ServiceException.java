package br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.exeption;

public class ServiceException extends RuntimeException{

    public ServiceException (String mensagem) {
        super(mensagem);
    }
}
