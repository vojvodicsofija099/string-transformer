package com.stringtransformer.controller;

import com.stringtransformer.dto.TransformedStringDTO;
import com.stringtransformer.dto.TransformedStringDTOs;
import com.stringtransformer.model.InputValue;
import com.stringtransformer.service.TransformerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import static com.stringtransformer.controller.assembler.ControllerResponseAssembler.assembleTransformedStringDTO;
import static com.stringtransformer.controller.assembler.ControllerResponseAssembler.assembleTransformedStringDTOs;

@RestController
public class TransformerController implements TransformerAPI {
    private final TransformerService transformerService;

    @Autowired
    public TransformerController(TransformerService transformerService) {
        this.transformerService = transformerService;
    }

    @Override
    public TransformedStringDTOs applyTransformations(@RequestBody List<@Valid InputValue> inputs) {

        List<TransformedStringDTO> outputs = new LinkedList<>();
        for(InputValue input : inputs) {
            String originalValue = input.getValue();
            String resultValue = transformerService.applyTransformations(input);
            TransformedStringDTO output = assembleTransformedStringDTO(originalValue, resultValue);
            outputs.add(output);
        }

        return assembleTransformedStringDTOs(outputs);
    }
}