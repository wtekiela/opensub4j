package com.github.wtekiela.opensub4j.impl;

import com.github.wtekiela.opensub4j.response.ListResponse;
import com.github.wtekiela.opensub4j.response.SubtitleFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ResponseParserTest {

    private ResponseParser objectUnderTest = new ResponseParser();

    @Test
    void testBindListIfDataIsNotAnArray() {
        // given
        Map<String, Object> response = new HashMap<>();
        response.put("seconds", 0.005d);
        response.put("data", "");
        response.put("status", "206 Partial content; missing idsubtitlefile(s): -1");

        // when
        ListResponse<SubtitleFile> result = objectUnderTest.bind(new ListResponse<>(), () -> new SubtitleFile(), response);

        // then
        Assertions.assertEquals(206, result.getStatus().getCode());
        Assertions.assertTrue(result.getData().isEmpty());
    }
}