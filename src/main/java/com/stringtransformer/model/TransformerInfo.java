package com.stringtransformer.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class TransformerInfo {
    private String groupId;
    private String transformerId;
    private Map<String, String> parameters;
}
