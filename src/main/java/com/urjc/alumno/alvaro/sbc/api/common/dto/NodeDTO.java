package com.urjc.alumno.alvaro.sbc.api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NodeDTO {

    private String iri;
    private String name;

}
