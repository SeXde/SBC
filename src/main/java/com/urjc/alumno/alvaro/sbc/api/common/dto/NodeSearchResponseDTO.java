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
public class NodeSearchResponseDTO {

    private NodeDTO originNode;
    private List<LinkDTO> links;

}
