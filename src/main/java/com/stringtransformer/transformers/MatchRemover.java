package com.stringtransformer.transformers;

import com.stringtransformer.annotations.TransformerComponent;
import com.stringtransformer.controller.exception.StringTransformerException;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.stringtransformer.constants.StringTransformerConstants.*;

@Component
@TransformerComponent(groupId = "regex", transformerId = "remove")
public class MatchRemover implements ParametrizedTransformer {
    @Override
    public String transform(String value, @NonNull Map<String, String> params) {

        if(params == null) {throw new StringTransformerException(PARAMS_NOT_NULL);}
        String regex = params.get(REGEX);
        if(regex == null) {throw new StringTransformerException(REGEX_NOT_NULL);}
        return value == null ? null : value.replaceAll(regex, EMPTY);
    }
}
