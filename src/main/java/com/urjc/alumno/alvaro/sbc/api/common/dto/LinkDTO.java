package com.urjc.alumno.alvaro.sbc.api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LinkDTO {

    private String edgeName;
    private List<NodeDTO> nodes;

}
