package com.stringtransformer.controller.exception;

import lombok.Getter;

@Getter
public class StringTransformerException extends RuntimeException {

    private final String message;

    public StringTransformerException(String message) {
        this.message = message;
    }
}
