package com.MV_Equipos.Inventario.Exception;

import com.MV_Equipos.Inventario.Dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice//Intercepta excepciones lanzadas por los controladores REST
// y devuelve respuestas JSON estandarizadas
public class GlobalExceptionHandler  {
    @ExceptionHandler(RecursoNoEncontradoException.class)//va a interceptar cualquier peticion de este tipo de excepciones
    public ResponseEntity<ErrorResponse> manejarValicaion(RecursoNoEncontradoException ex){
        ErrorResponse error= new ErrorResponse(LocalDateTime.now(),404,ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<ErrorResponse> manejarRecurso(ValidacionException ex){
        ErrorResponse error= new ErrorResponse(LocalDateTime.now(),400,ex.getMessage());
        //Se arma un objeto que va a tener los campos necesarios como la fecha el codigo del error y el mensaje que se extrajo
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(Exception ex){

        ErrorResponse error =
                new ErrorResponse(LocalDateTime.now(),
                        500,
                        "Error interno del servidor"
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

}
