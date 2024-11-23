package com.stringtransformer.transformers;

import java.util.Map;

public interface Transformer {

    String transform(String value, Map<String, String> params);
}
