package br.com.squadra.bootcamp.desafiofinal.danielsantana23.controller.exceptions;

import br.com.squadra.bootcamp.desafiofinal.danielsantana23.exceptions.SeviceErro;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.exeption.ServiceException;
import br.com.squadra.bootcamp.desafiofinal.danielsantana23.service.exeption.VazioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerAdviceApi {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<SeviceErro> resourceNotFound(ServiceException e, HttpServletRequest request) {
        String erro = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        SeviceErro err = new SeviceErro(String.valueOf(status), e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<SeviceErro> resourceNotFound(ConstraintViolationException e) {
        String erro = "Campo(s) obrigatório(s) em branco";
        HttpStatus status = HttpStatus.NOT_FOUND;
        SeviceErro err = new SeviceErro(String.valueOf(status), "Campos Obrigatórios em branco");
        return ResponseEntity.status(status).body(err);
    }
}
