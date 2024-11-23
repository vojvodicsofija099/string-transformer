package com.stringtransformer.unit.service;

import com.stringtransformer.model.InputValue;
import com.stringtransformer.model.TransformerInfo;
import com.stringtransformer.registry.TransformerRegistry;
import com.stringtransformer.service.TransformerService;
import com.stringtransformer.unit.common.MockTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.stringtransformer.unit.common.TestConstants.groupId1;
import static com.stringtransformer.unit.common.TestConstants.transformerId1;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransformerServiceTest {

    @Mock
    private TransformerRegistry transformerRegistry;

    @InjectMocks
    private TransformerService transformerService;


    @Test
    void applyTransformationsValid() {
        MockTransformer mockTransformer = new MockTransformer(groupId1, transformerId1);
        Mockito.when(transformerRegistry.getTransformer(groupId1, transformerId1)).thenReturn(mockTransformer);

        String actual = transformerService.applyTransformations(InputValue.builder()
                .value("make me upper case")
                .transformers(List.of(TransformerInfo.builder()
                                .groupId(groupId1)
                                .transformerId(transformerId1)
                                .build()))
                .build());

        assertEquals("MAKE ME UPPER CASE", actual);
    }

    @ParameterizedTest
    @MethodSource({"cornerTestCaseProvider"})
    void applyTransformationsNoTransformers(List<TransformerInfo> transformerInfos, String value, String expected) {

        String actual = transformerService.applyTransformations(InputValue.builder()
                .value(value)
                .transformers(transformerInfos)
                .build());

        assertEquals(expected, actual);
    }

    private static Object[][] cornerTestCaseProvider() {
        return new Object[][]{
                {null, "test", "test"},
                {List.of(), "test", "test"}
        };
    }
}
