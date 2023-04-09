package ru.practicum.mainService.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Api error class
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiError {

    private List<String> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ApiError(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = Collections.emptyList();
        this.reason = message;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
