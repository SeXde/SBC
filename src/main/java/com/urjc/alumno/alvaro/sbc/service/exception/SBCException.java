package com.urjc.alumno.alvaro.sbc.service.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SBCException extends RuntimeException {

    private String errorMessage;
    private HttpStatusCode status;

}
