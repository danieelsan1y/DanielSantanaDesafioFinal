package br.com.squadra.bootcamp.desafiofinal.danielsantana23.exceptions;


public class SeviceErro {
    private String status;
    private String Mensagem;

    public SeviceErro(String status, String mensagem) {
        this.status = status;
        Mensagem = mensagem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

}
