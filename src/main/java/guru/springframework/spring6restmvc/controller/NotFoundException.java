package guru.springframework.spring6restmvc.controller;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jt, Spring Framework Guru.
 */
@AllArgsConstructor
@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
public class NotFoundException extends RuntimeException {
    private String message;
    private Throwable cause;
}
