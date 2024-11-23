package com.stringtransformer.registry;

import com.stringtransformer.annotations.TransformerComponent;
import com.stringtransformer.transformers.Transformer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransformerRegistry {

    private final Map<String, Transformer> transformerMap = new HashMap();

    public TransformerRegistry(List<Transformer> transformers) {
        for(Transformer transformer : transformers) {
            TransformerComponent component = transformer.getClass().getAnnotation(TransformerComponent.class);
            if(component != null) {
                String key = component.groupId()+ ":" + component.transformerId();
                transformerMap.put(key, transformer);
            }
        }
    }

    public Transformer getTransformer(String groupId, String transformerId) {
        return transformerMap.get(groupId+ ":" +transformerId);
    }
}
