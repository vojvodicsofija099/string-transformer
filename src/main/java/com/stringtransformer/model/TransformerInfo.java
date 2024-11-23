package com.stringtransformer.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class TransformerInfo {

    @NotEmpty
    private String groupId;
    @NotEmpty
    private String transformerId;
    private Map<String, String> parameters;
}
