package com.stringtransformer.unit.common;

import com.stringtransformer.annotations.TransformerComponent;
import com.stringtransformer.transformers.Transformer;

import java.util.Map;

@TransformerComponent(groupId = "group1", transformerId = "transformer1")
public class MockTransformer implements Transformer {

    private final String groupId;
    private final String transformerId;

    public MockTransformer(String groupId, String transformerId) {
        this.groupId = groupId;
        this.transformerId = transformerId;
    }


    @Override
    public String transform(String value, Map<String, String> params) {
        return value == null ? null :value.toUpperCase();
    }

    public String getKey() {
        return groupId + ":" + transformerId;
    }
}
