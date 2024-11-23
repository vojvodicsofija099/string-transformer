package com.stringtransformer.unit.register;

import com.stringtransformer.registry.TransformerRegistry;
import com.stringtransformer.transformers.Transformer;

import static com.stringtransformer.unit.common.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import com.stringtransformer.unit.common.MockTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TransformerRegistryTest {

    private TransformerRegistry transformerRegistry;



    @BeforeEach
    void setUp() {
        Transformer mockTransformer = new MockTransformer(groupId1, transformerId1);

        List<Transformer> transformerList = List.of(mockTransformer);
        transformerRegistry = new TransformerRegistry(transformerList);
    }

    @Test
    void registryInitialized() {
        assertNotNull(transformerRegistry);
    }

    @Test
    void testGetTransformerByValidId() {
        MockTransformer transformer = (MockTransformer) transformerRegistry.getTransformer(groupId1, transformerId1);
        assertNotNull(transformer);
        assertEquals(groupId1+":"+transformerId1, transformer.getKey());
    }

    @Test
    void testGetTransformerByInvalidId() {
        MockTransformer transformer = (MockTransformer) transformerRegistry.getTransformer(groupId2, transformerId2);
        assertNull(transformer);
    }

    @Test
    void testGetTransformerFromEmptyRegistry() {
        transformerRegistry = new TransformerRegistry(Arrays.asList());
        Transformer transformer = transformerRegistry.getTransformer(groupId1, transformerId1);
        assertNull(transformer);
    }

}
