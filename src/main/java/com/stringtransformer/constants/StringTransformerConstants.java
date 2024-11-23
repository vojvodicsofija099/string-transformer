package com.stringtransformer.constants;

import java.util.Map;

public class StringTransformerConstants {

    public static final String REGEX = "regex";
    public static final String REPLACEMENT = "replacement";
    public static final String LANGUAGE = "language";
    public static final String LATIN = "latin";
    public static final String REPLACE = "replace";
    public static final String REMOVE = "remove";
    public static final String EMPTY = "";
    //errors
    public static final String PARAMS_NOT_NULL = "Transformer parameters must not be null";
    public static final String REGEX_NOT_NULL = "Regex parameter must not be null";
    public static final String INPUT_VALUE_LIST_NOT_NULL = "Input value list must not be null";
    public static final String VALUE_NOT_NULL = "Value must not be null";
    public static final String TRANSFORMER_DOESNT_EXIST = "Transformer with {groupId} and {transformerId} doesn't exist";
    public static final String GROUP_ID_OR_TRANSFORMER_ID_NULL = "Group id or transformer id is null";

    public static String getMessage(String message, Map<String, String> params) {

        for(Map.Entry<String, String> entry : params.entrySet()) {
            message = message.replace("{"+ entry.getKey() + "}", entry.getValue());
        }
        return message;
    }

}