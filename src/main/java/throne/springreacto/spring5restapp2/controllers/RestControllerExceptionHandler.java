package throne.springreacto.spring5restapp2.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import throne.springreacto.spring5restapp2.services.ResourceNotFoundException;

/**
 * This class shall be consulted in case of problems within the controllers i.e Exceptions etc
 */
@ControllerAdvice
public class RestControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleControllerException(){

        return new ResponseEntity<>("Resource Not Found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
