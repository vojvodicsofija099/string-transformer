package com.stringtransformer.unit.transformers;

import com.stringtransformer.transformers.MatchRemover;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.stringtransformer.constants.StringTransformerConstants.REGEX;

@ExtendWith(MockitoExtension.class)
public class MatchRemoverTest {

    @InjectMocks
    MatchRemover transformer;

    @ParameterizedTest
    @MethodSource({"testCaseProvider"})
    public void removeMatchingStringTest(String inputString, Map<String, String> regexParams, String expected) {

        String actual = transformer.transform(inputString, regexParams);
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] testCaseProvider() {

        String inputValueWithDigits = "Hello123";
        String inputValueNoDigits = "Hello";
        String inputValueEmpty = "";
        String inputValueNull= null;

        Map<String, String> regexParamsDigits = Map.of(REGEX, "\\d+");

        return new Object[][]{
                {inputValueWithDigits, regexParamsDigits, "Hello"},
                {inputValueNoDigits, regexParamsDigits, "Hello"},
                {inputValueEmpty, regexParamsDigits, ""},
                {inputValueNull, regexParamsDigits, null}
        };
    }

}
