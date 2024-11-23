package com.stringtransformer.controller;


import com.stringtransformer.dto.TransformedStringDTO;
import com.stringtransformer.dto.TransformedStringDTOs;
import com.stringtransformer.model.InputValue;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TransformerAPI {

    String TRANSFORM_REQUEST_MAPPING = "/v1/transform";

    @PostMapping(TRANSFORM_REQUEST_MAPPING)
    TransformedStringDTOs applyTransformations(@RequestBody List<@Valid InputValue> input);
}
