package guru.springframework.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jt, Spring Framework Guru.
 */
@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        if (exception.getCause().getCause() instanceof ConstraintViolationException constraintViolationException) {
            List<Map<String, String>> errors = constraintViolationException.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                        Map<String, String> errorMap = new HashMap<>();
                        errorMap.put("error", constraintViolation.getMessage());
                        errorMap.put("field", constraintViolation.getPropertyPath().toString());
                        return errorMap;
                    }).toList();
            return responseEntity.body(errors);
        }
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){

        List<Map<String, String>> errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String > errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();

        return ResponseEntity.badRequest().body(errorList);
    }
}
