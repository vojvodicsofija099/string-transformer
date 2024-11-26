package com.stringtransformer.service;

import com.stringtransformer.constants.StringTransformerConstants;
import com.stringtransformer.exception.StringTransformerException;
import com.stringtransformer.model.InputValue;
import com.stringtransformer.model.TransformerInfo;
import com.stringtransformer.registry.TransformerRegistry;
import com.stringtransformer.transformers.Transformer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.stringtransformer.constants.StringTransformerConstants.GROUP_ID_OR_TRANSFORMER_ID_NULL;
import static com.stringtransformer.constants.StringTransformerConstants.TRANSFORMER_DOESNT_EXIST;

@Service
public class TransformerService {

    private final TransformerRegistry transformerRegistry;

    public TransformerService(TransformerRegistry transformerRegistry) {
        this.transformerRegistry = transformerRegistry;
    }

    public String applyTransformations(InputValue input) {

        List<TransformerInfo> transformers = input.getTransformers();
        String resultValue = input.getValue();
        if(transformers == null || transformers.isEmpty()) {return resultValue;}

        for(TransformerInfo parameters : transformers) {
            String groupId = parameters.getGroupId();
            String transformerId = parameters.getTransformerId();
            if(groupId == null || transformerId == null) {throw new StringTransformerException(GROUP_ID_OR_TRANSFORMER_ID_NULL);}

            Transformer transformer = transformerRegistry.getTransformer(groupId, transformerId);
            if(transformer == null) {
                throw new StringTransformerException(StringTransformerConstants.getMessage(TRANSFORMER_DOESNT_EXIST,
                        Map.of("{groupId}", groupId, "{transformerId}", transformerId)));
            }
            resultValue = transformer.transform(resultValue, parameters.getParameters());
        }

        return resultValue;
    }
}
