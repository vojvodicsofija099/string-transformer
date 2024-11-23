package com.stringtransformer.transformers;

import java.util.Map;

public interface SimpleTransformer extends Transformer{

    @Override
    default String transform(String value, Map<String, String> params) {
        return transform(value);
    }
    String transform(String value);
}
