package com.urjc.alumno.alvaro.sbc.api.common.dto;

import com.urjc.alumno.alvaro.sbc.api.common.enums.FlowEnum;
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
    private FlowEnum flow;
    private List<NodeDTO> nodes;

}
