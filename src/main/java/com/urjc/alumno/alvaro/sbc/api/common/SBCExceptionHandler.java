package com.urjc.alumno.alvaro.sbc.api.common;

import com.urjc.alumno.alvaro.sbc.service.exception.SBCException;
import com.urjc.alumno.alvaro.sbc.service.exception.dto.SBCErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SBCExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ SBCException.class })
    public ResponseEntity<SBCErrorDTO> handleSBCException(final SBCException sbcException, final WebRequest request) {
        return ResponseEntity
                .status(sbcException.getStatus())
                .body(
                        SBCErrorDTO
                                .builder()
                                .message(sbcException.getErrorMessage())
                                .httpStatus(sbcException.getStatus().value())
                                .build()
                );
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<SBCErrorDTO> defaultHandlerException(final Exception exception, final WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        SBCErrorDTO
                                .builder()
                                .message(exception.getMessage())
                                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .build()
                );
    }

}
