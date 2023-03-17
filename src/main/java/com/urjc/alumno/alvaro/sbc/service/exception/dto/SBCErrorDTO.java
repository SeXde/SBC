package com.urjc.alumno.alvaro.sbc.service.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SBCErrorDTO {

    private String message;
    private int httpStatus;

}
