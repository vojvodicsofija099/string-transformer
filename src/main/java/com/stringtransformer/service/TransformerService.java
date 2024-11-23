package com.stringtransformer.service;

import com.stringtransformer.model.InputValue;
import com.stringtransformer.model.TransformerInfo;
import com.stringtransformer.registry.TransformerRegistry;
import com.stringtransformer.transformers.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            Transformer transformer = transformerRegistry.getTransformer(parameters.getGroupId(), parameters.getTransformerId());
            resultValue = transformer.transform(resultValue, parameters.getParameters());
        }

        return resultValue;
    }
}
