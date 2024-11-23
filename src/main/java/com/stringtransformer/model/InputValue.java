package com.stringtransformer.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InputValue {

    @NotNull
    private String value;
    private List<TransformerInfo> transformers;

}
