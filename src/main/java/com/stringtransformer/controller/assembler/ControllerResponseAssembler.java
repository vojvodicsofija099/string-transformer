package com.stringtransformer.controller.assembler;

import com.stringtransformer.dto.TransformedStringDTO;
import com.stringtransformer.dto.TransformedStringDTOs;

import java.util.List;

public class ControllerResponseAssembler {

    public static TransformedStringDTO assembleTransformedStringDTO(String originalValue, String transformedValue) {
        return TransformedStringDTO.builder()
                .originalValue(originalValue)
                .transformedValue(transformedValue)
                .build();
    }

    public static TransformedStringDTOs assembleTransformedStringDTOs(List<TransformedStringDTO> transformedStringDTOList) {
        return TransformedStringDTOs.builder()
                .transformedStringDTOList(transformedStringDTOList)
                .build();
    }
}
