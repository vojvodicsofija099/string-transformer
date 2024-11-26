package com.stringtransformer.transformers;

import com.stringtransformer.annotations.TransformerComponent;
import com.stringtransformer.exception.StringTransformerException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.stringtransformer.constants.StringTransformerConstants.*;

@AllArgsConstructor
@Component
@TransformerComponent(groupId = "regex", transformerId = "replace")
public class MatchReplacer implements ParametrizedTransformer {
    @Override
    public String transform(String value, @NonNull Map<String, String> params) {

        if(params == null) {throw new StringTransformerException(PARAMS_NOT_NULL);}
        String regex = params.get(REGEX);
        if(regex == null) {throw new StringTransformerException(REGEX_NOT_NULL);}
        String replacement = params.get(REPLACEMENT);
        return value == null ? null : value.replaceAll(regex, replacement);
    }
}
