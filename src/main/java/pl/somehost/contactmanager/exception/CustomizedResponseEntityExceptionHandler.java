package pl.somehost.contactmanager.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice  //Apply to all controllers
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${role.joining.character}")
    private String roleJoiningCharacter;

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    ExceptionReponse exceptionReponse =
            new ExceptionReponse(new Date(),ex.getMessage(),request.getDescription(false));
    return new ResponseEntity<>(exceptionReponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   // Handling incorrect id value
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<Object> handleIllegalArgumentException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ExceptionReponse exceptionReponse =
                new ExceptionReponse(new Date(),"Incorrect Id value !",request.getDescription(false));
        return new ResponseEntity<>(exceptionReponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Overriding this method handle exception for "Json @RequestBody" all controllers errors
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionReponse exceptionReponse =
                new ExceptionReponse(new Date(),"JSON data error !",ex.getMessage());
        return new ResponseEntity<>(exceptionReponse, HttpStatus.BAD_REQUEST);
    }
    // Overriding this method handle exception for Validation constraints.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String error = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(roleJoiningCharacter));
        ExceptionReponse exceptionReponse =
                new ExceptionReponse(new Date(),"Validation Failed !",error);
        return new ResponseEntity<>(exceptionReponse, HttpStatus.BAD_REQUEST);
    }


}
