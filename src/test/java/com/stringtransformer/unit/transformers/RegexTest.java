package com.stringtransformer.unit.transformers;

import com.stringtransformer.controller.exception.StringTransformerException;
import com.stringtransformer.transformers.MatchRemover;
import com.stringtransformer.transformers.MatchReplacer;
import com.stringtransformer.transformers.Transformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.regex.PatternSyntaxException;

import static com.stringtransformer.constants.StringTransformerConstants.*;

public class RegexTest {

    private static final MatchRemover matchRemover = new MatchRemover();
    private static final MatchReplacer matchReplacer = new MatchReplacer();

    @ParameterizedTest
    @MethodSource({"cornerTestCaseRegexProvider"})
    public void cornerCaseRegexTest(Transformer transformer, String inputString, Map<String, String> regexParams, String expected) {

        String actual = transformer.transform(inputString, regexParams);
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] cornerTestCaseRegexProvider() {



        return new Object[][]{
                {matchRemover, "Hello ", Map.of(REGEX, "^$"), "Hello "}, //empty string regex, does nothing
                {matchRemover, "  ", Map.of(REGEX, "^$"), "  "}, //empty string regex, does nothing
                {matchRemover, "a long string from a to b", Map.of(REGEX, "a.*b"), ""}, //greedy regex, removes everything between a and b
                {matchRemover, "XYZ", Map.of(REGEX, "^xyz$"), "XYZ"}, //case insensitive
                {matchRemover, "abc", Map.of(REGEX, "^abc$"), ""}, //exactly the same string 'abc'
                {matchRemover, "abc\ndef", Map.of(REGEX, "^abc$"), "abc\ndef"}, //exactly the same string 'abc' won't be found because of the text in new line
                {matchRemover, "abc\ndef", Map.of(REGEX, "(?m)^abc$"), "\ndef"}, //because of the ?m (multiline) looks into each line separately

                {matchReplacer, "Hello ", Map.of(REGEX, "^$", REPLACEMENT, "123"), "Hello "}, //empty string regex, does nothing
                {matchReplacer, "  ", Map.of(REGEX, "^$", REPLACEMENT, "123"), "  "}, //empty string regex, does nothing
                {matchReplacer, "a long string from a to b", Map.of(REGEX, "a.*b", REPLACEMENT, "123"), "123"}, //greedy regex, removes everything between a and b
                {matchReplacer, "XYZ", Map.of(REGEX, "^xyz$", REPLACEMENT, "123"), "XYZ"}, //case insensitive - does nothing
                {matchReplacer, "abc", Map.of(REGEX, "^abc$", REPLACEMENT, "123"), "123"}, //replace exactly the same string 'abc'
                {matchReplacer, "abc\ndef", Map.of(REGEX, "^abc$", REPLACEMENT, "123"), "abc\ndef"}, //exactly the same string 'abc' won't be found because of the text in new line - does nothing
                {matchReplacer, "abc\ndef", Map.of(REGEX, "(?m)^abc$", REPLACEMENT, "123"), "123\ndef"} //because of the ?m (multiline) looks into each line separately, replaces first line
        };
    }

    @ParameterizedTest
    @MethodSource({"invalidRegexTestCaseProvider"})
    public void invalidRegexTest(Transformer transformer, String inputString, Map<String, String> regexParams) {
        Assertions.assertThrows(PatternSyntaxException.class, () -> transformer.transform(inputString, regexParams));
    }

    private static Object[][] invalidRegexTestCaseProvider() {

        return new Object[][]{
                {matchRemover, "aaab(c)", Map.of(REGEX, "a+b(c")}, //no closing )
                {matchRemover, "(", Map.of(REGEX, "(")},
                {matchRemover, "xyz", Map.of(REGEX, "[a-z")},
                {matchRemover, "abc", Map.of(REGEX, "a+*")}, //invalid syntax - a.* or a+
                {matchRemover, "ends with a", Map.of(REGEX, "*a")}, //invalid syntax - .*a
                {matchRemover, "word123", Map.of(REGEX, "(?<=word")},

                {matchReplacer, "aaab(c)", Map.of(REGEX, "a+b(c")}, //no closing )
                {matchReplacer, "(", Map.of(REGEX, "(")},
                {matchReplacer, "xyz", Map.of(REGEX, "[a-z")},
                {matchReplacer, "abc", Map.of(REGEX, "a+*")}, //invalid syntax - a.* or a+
                {matchReplacer, "ends with a", Map.of(REGEX, "*a")}, //invalid syntax - .*a
                {matchReplacer, "word123", Map.of(REGEX, "(?<=word")}
        };
    }

    @ParameterizedTest
    @MethodSource({"invalidRegexInputTestCaseProvider"})
    public void invalidRegexInputTest(Transformer transformer, String inputString, Map<String, String> regexParams, String expectedMessage) {

        StringTransformerException thrown = Assertions.assertThrows(StringTransformerException.class, () -> transformer.transform(inputString, regexParams));
        Assertions.assertEquals(expectedMessage, thrown.getMessage());
    }

    private static Object[][] invalidRegexInputTestCaseProvider() {

        return new Object[][]{
                {matchRemover, "Hello123", Map.of(), REGEX_NOT_NULL},
                {matchRemover, "Hello123", null, PARAMS_NOT_NULL},

                {matchReplacer, "Hello123", Map.of(), REGEX_NOT_NULL},
                {matchReplacer, "Hello123", null, PARAMS_NOT_NULL}
        };
    }
}
