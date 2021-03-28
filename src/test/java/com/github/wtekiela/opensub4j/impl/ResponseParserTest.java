package com.github.wtekiela.opensub4j.impl;

import com.github.wtekiela.opensub4j.response.ListResponse;
import com.github.wtekiela.opensub4j.response.SubtitleFile;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResponseParserTest {

    private static final String TEST_SUBTITLE_FILE_DATA =
        "H4sIAAAAAAAAA5W92ZIkx5El+g4R/EMAuCL1EqgJX8yXfriQAtBEVRMLhwUSAoHwwTPTMzNYEeHZsVQy+S/zgfN0f+HqOaqmrp5VYHfL9ICAp6uGmbmama5Hi08/2Wz+Df/XrDebzerLL//flT4oyvWmrT/95KeH8fD2cnXennfjaXUcr6f9fjzcnFaX0/Zwt/pxOt6s/vqnHz/95PY47VfVy7pf/eXtt/9rPx3O98IODKfTzdXL3fbw7n+9fzh8+smnn5T6q9W/Fc2675P/avVvZbFOqf30k9fjbje9XP3wtDoM+/HF6dNP/jAdj+Pp/PLly5X96+q7y/7hJfhVmV/ZrtNmwa9f11X/6SffTqun6bJ6HA7n1bC6vp+up91wHr8CdZ2pK5lysQnUVbOuaxnNm9X1dNndrMZBqK+mC3jst7vddpLpDIcb+c/7YXe7mm5X5/vxNHJQydk261TUkW0nbLtPP5HZ7Yf9sBp2j8PTaXUatjcgbJywE8ImENblusR4dtvbUeZyWu2270YZwepq+gd+3Od14gjazKiu1l2Ky1JjYrIsv8qaHMb343H17jA9rh7vh/Onn8hCvTiOq7sJ3/c8re7GM9l1mV0Sdk1kl5p1t9l8+snP99NpXO0v8m2uZFwiKrfT8Txc7cbV6X6yUfXORqiaztnUInXrskpY7qvxzO+ly/447N7JIu1WN8PTp59sD8qMk5f1xpA//aTYKFtyqYIMyANZNcgAPtRhOq9ux3EnX+x8L/PjiIrCaet13TaRtl33RYkhPW5P96s38p1vnv+88iidh5C0feBRbNZ1UfFzy0+fV/eXo9FUmQavdGWkKYVGBOGHD+RDBOwoG2I1HFbD4+1lt9pNugC1MxParo7M5GuXG37Y1bXQnWVzqRxDdh7G42k6rK6eILvbY/hSRXKWwqGLn6oQES5lyr9gNCB8cpFZk7RxUnmz30TSft1jiz/OpO9H+eLjQX+0zZQlDof4o7KZUxIpfAOCx";

    public static final int TEST_SUBTITE_FILE_ID = 1952039423;

    private ResponseParser objectUnderTest = new ResponseParser();

    @Test
    void testBindListIfDataIsString() {
        // given
        Map<String, Object> response = new HashMap<>();
        response.put("seconds", 0.005d);
        response.put("data", "");
        response.put("status", "206 Partial content; missing idsubtitlefile(s): -1");

        // when
        ListResponse<SubtitleFile> result =
            objectUnderTest.bind(new ListResponse<>(), () -> new SubtitleFile(), response);

        // then
        Assertions.assertEquals(206, result.getStatus().getCode());
        Assertions.assertFalse(result.getData().isPresent());
    }

    @Test
    void testBindListIfDataIsBoolean() {
        // given
        Map<String, Object> response = new HashMap<>();
        response.put("seconds", 0.005d);
        response.put("data", false);
        response.put("status", "407 Download limit reached");

        // when
        ListResponse<SubtitleFile> result =
            objectUnderTest.bind(new ListResponse<>(), () -> new SubtitleFile(), response);

        // then
        Assertions.assertEquals(407, result.getStatus().getCode());
        Assertions.assertFalse(result.getData().isPresent());
    }

    @Test
    void testBindListIfDataIsArray() {
        // given
        Map<String, Object> entry = new HashMap<>();
        entry.put("data", TEST_SUBTITLE_FILE_DATA);
        entry.put("idsubtitlefile", TEST_SUBTITE_FILE_ID);

        Object[] values = new Object[1];
        values[0] = entry;

        Map<String, Object> response = new HashMap<>();
        response.put("seconds", 0.005d);
        response.put("data", values);
        response.put("status", "200 OK");

        // when
        ListResponse<SubtitleFile> result =
            objectUnderTest.bind(new ListResponse<>(), () -> new SubtitleFile(), response);

        // then
        Assertions.assertEquals(200, result.getStatus().getCode());
        Assertions.assertTrue(result.getData().isPresent());
        SubtitleFile subtitleFile = result.getData().get().get(0);
        Assertions.assertEquals(TEST_SUBTITE_FILE_ID, subtitleFile.getId());
    }
}