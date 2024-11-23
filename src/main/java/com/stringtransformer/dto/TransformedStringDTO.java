package com.stringtransformer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransformedStringDTO {

    private String originalValue;
    private String transformedValue;

}
