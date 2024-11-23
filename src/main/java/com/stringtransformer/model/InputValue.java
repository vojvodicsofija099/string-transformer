package com.stringtransformer.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.stringtransformer.constants.StringTransformerConstants.VALUE_NOT_NULL;

@Getter
@Builder
public class InputValue {

    @NotNull (message = VALUE_NOT_NULL)
    private String value;
    private List<TransformerInfo> transformers;

}
