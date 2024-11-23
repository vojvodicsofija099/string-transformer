package com.stringtransformer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransformedStringDTOs {

    private List<TransformedStringDTO> transformedStringDTOList;
}
