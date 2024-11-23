package com.stringtransformer.integration;

import com.stringtransformer.constants.StringTransformerConstants;
import com.stringtransformer.dto.TransformedStringDTOs;
import com.stringtransformer.model.InputValue;
import com.stringtransformer.model.TransformerInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stringtransformer.constants.StringTransformerConstants.*;
import static com.stringtransformer.controller.TransformerAPI.TRANSFORM_REQUEST_MAPPING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransformerIntegrationTest {

	private static final String transformRequestMapping = TRANSFORM_REQUEST_MAPPING;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void transformString() {
		String value = "This123 is test to check if Αθήνα and356 Чачкалица test is working.000000000";

		Map<String, String> removeParams = new HashMap<>();
		removeParams.put(REGEX, "\\d+");
		TransformerInfo removeRegexMatch = TransformerInfo.builder()
				.groupId(REGEX)
				.transformerId(REMOVE)
				.parameters(removeParams)
				.build();

		Map<String, String> replacementParams = new HashMap<>();
		replacementParams.put(REGEX, "test");
		replacementParams.put(REPLACEMENT, "exam");
		TransformerInfo replaceRegexMatch = TransformerInfo.builder()
				.groupId(REGEX)
				.transformerId(REPLACE)
				.parameters(replacementParams)
				.build();

		TransformerInfo latinConverter = TransformerInfo.builder()
				.groupId(LANGUAGE)
				.transformerId(LATIN)
				.build();

		List<TransformerInfo> transformerInfoList = List.of(removeRegexMatch, replaceRegexMatch, latinConverter);

		List<InputValue> inputValues = new ArrayList<>();
		InputValue inputValue = InputValue.builder()
				.value(value)
				.transformers(transformerInfoList)
				.build();
		inputValues.add(inputValue);

		TransformedStringDTOs result = sendSuccessfulRequest(inputValues);

		String expected = "This is exam to check if Athina and Čačkalica exam is working.";
		assertEquals(expected, result.getTransformedStringDTOList().get(0).getTransformedValue());
	}

	@Test
	public void transformStringInvalidParameters() {
		String value = "Invalid regex parameters input";

		TransformerInfo removeRegexMatch = TransformerInfo.builder()
				.groupId(REGEX)
				.transformerId(REMOVE)
				.parameters(null) //causes exception
				.build();

		List<TransformerInfo> transformerInfoList = List.of(removeRegexMatch);

		List<InputValue> inputValues = new ArrayList<>();
		InputValue inputValue = InputValue.builder()
				.value(value)
				.transformers(transformerInfoList)
				.build();
		inputValues.add(inputValue);

		ResponseEntity<String> response = sendFailedRequest(inputValues);
		assertEquals(PARAMS_NOT_NULL, response.getBody());
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
	}

	@Test
	public void transformStringNull() {
		List<TransformerInfo> transformerInfoList = List.of();

		List<InputValue> inputValues = new ArrayList<>();
		InputValue inputValue = InputValue.builder()
				.value(null) //causes exception
				.transformers(transformerInfoList)
				.build();
		inputValues.add(inputValue);

		ResponseEntity<String> response = sendFailedRequest(inputValues);
		assertTrue(response.getBody().contains(VALUE_NOT_NULL));
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void transformStringInvalidTransformer() {
		String value = "Transformer with no grout or transformer id";

		Map<String, String> removeParams = new HashMap<>();
		removeParams.put("regex", "\\d+");
		TransformerInfo removeRegexMatch = TransformerInfo.builder()
				.groupId("group2") //doesn't exist
				.transformerId("transformer2")
				.parameters(removeParams)
				.build();

		List<TransformerInfo> transformerInfoList = List.of(removeRegexMatch);

		List<InputValue> inputValues = new ArrayList<>();
		InputValue inputValue = InputValue.builder()
				.value(value)
				.transformers(transformerInfoList)
				.build();
		inputValues.add(inputValue);

		ResponseEntity<String> response = sendFailedRequest(inputValues);

		assertTrue(response.getBody().contains(StringTransformerConstants.getMessage(TRANSFORMER_DOESNT_EXIST,
				Map.of("{groupId}", "group2", "{transformerId}", "transformer2"))));
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
	}

	@Test
	public void transformStringNullTransformer() {
		String value = "Transformer with no grout or transformer id";

		Map<String, String> removeParams = new HashMap<>();
		removeParams.put("regex", "\\d+");
		TransformerInfo removeRegexMatch = TransformerInfo.builder()
				.groupId(null) //throws exception
				.transformerId(null)
				.parameters(removeParams)
				.build();

		List<TransformerInfo> transformerInfoList = List.of(removeRegexMatch);

		List<InputValue> inputValues = new ArrayList<>();
		InputValue inputValue = InputValue.builder()
				.value(value)
				.transformers(transformerInfoList)
				.build();
		inputValues.add(inputValue);

		ResponseEntity<String> response = sendFailedRequest(inputValues);

		assertTrue(response.getBody().contains(GROUP_ID_OR_TRANSFORMER_ID_NULL));
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
	}


	public TransformedStringDTOs sendSuccessfulRequest(List<InputValue> request) {
		ResponseEntity<TransformedStringDTOs> response = restTemplate.postForEntity(
				transformRequestMapping,
				new HttpEntity<>(request),
				TransformedStringDTOs.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		return response.getBody();
	}

	public ResponseEntity<String> sendFailedRequest(List<InputValue> request) {
		ResponseEntity<String> response = restTemplate.postForEntity(
				transformRequestMapping,
				new HttpEntity<>(request),
				String.class);

		return response;
	}
}
