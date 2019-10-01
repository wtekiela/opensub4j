package com.github.wtekiela.opensub4j.impl;

import com.github.wtekiela.opensub4j.response.SubtitleFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ResponseParserTest {

    private ResponseParser objectUnderTest = new ResponseParser();

    @Test
    void testBindListIfDataIsNotAnArray() throws ReflectiveOperationException {
        // given
        Map<String, Object> response = new HashMap<>();
        response.put("seconds", 0.005d);
        response.put("data", "");
        response.put("status", "206 Partial content; missing idsubtitlefile(s): -1");

        // when
        List<SubtitleFile> result = objectUnderTest.bindList(SubtitleFile.class, response);

        // then
        Assertions.assertTrue(result.isEmpty());
    }
}