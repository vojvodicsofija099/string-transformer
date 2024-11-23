package com.stringtransformer.unit.transformers;

import com.stringtransformer.transformers.LatinConverter;
import com.stringtransformer.transformers.MatchReplacer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.stringtransformer.constants.StringTransformerConstants.REGEX;
import static com.stringtransformer.constants.StringTransformerConstants.REPLACEMENT;

@ExtendWith(MockitoExtension.class)
public class LatinConverterTest {

    @InjectMocks
    LatinConverter transformer;

    @ParameterizedTest
    @MethodSource({"testCaseProvider"})
    public void latinConverterStringTest(String inputString, String expected) {

        String actual = transformer.transform(inputString, Map.of());
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] testCaseProvider() {

        return new Object[][]{
                //original          //expected result
                {"Ћућорити", "Ćućoriti"},
                {"Шишмиш", "Šišmiš"},
                {"Чачкалица", "Čačkalica"},
                {"Њушка", "Njuška"},
                {"Џак", "Džak"},
                {"Јаје", "Jaje"},

                {"Αθήνα",         "Athina"},
                {"Ελλάδα",        "Ellada"},
                {"Καλημέρα",      "Kalimera"},
                {"Ουρανός",         "Ouranos"},
                {"Φίλος",           "Filos"},
                {"Χαρά",          "Chara"},
                {"Ψωμί",          "Psomi"},
                {"Ήλιος",         "Ilios"},
                {"Νερό",          "Nero"}
        };
    }
}
