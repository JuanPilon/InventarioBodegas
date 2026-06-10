package com.MV_Equipos.Inventario.Exception;

import com.MV_Equipos.Inventario.Dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice//Intercepta excepciones lanzadas por los controladores REST
// y devuelve respuestas JSON estandarizadas
public class GlobalExceptionHandler  {
    @ExceptionHandler(RecursoNoEncontradoException.class)//va a interceptar cualquier peticion de este tipo de excepciones
    public ResponseEntity<ErrorResponse> manejarRecurso(RecursoNoEncontradoException ex){
        ErrorResponse error= ErrorResponse.builder()
                .fecha(LocalDateTime.now())
                .codigo(404)
                .error(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(
            ValidacionException ex){

        ErrorResponse error = ErrorResponse.builder()
                .fecha(LocalDateTime.now())
                .codigo(400)
                .error(ex.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> manejarConstraintViolation(
            ConstraintViolationException ex){

        List<String> errores = ex.getConstraintViolations()
                .stream()
                .map(error ->
                        error.getPropertyPath() + ": "
                                + error.getMessage())
                .toList();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .fecha(LocalDateTime.now())
                .codigo(400)
                .error("Error de validación")
                .errores(errores)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacionesDto(
            MethodArgumentNotValidException ex){

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": "
                                + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .fecha(LocalDateTime.now())
                .codigo(400)
                .error("Error de validación")
                .errores(errores)
                .build();

        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(Exception ex){



        ErrorResponse error =ErrorResponse.builder()
                .fecha(LocalDateTime.now())
                .codigo(500)
                .error(ex.getMessage())
                .build();


        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }


}
