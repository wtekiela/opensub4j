package com.github.wtekiela.opensub4j.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.wtekiela.opensub4j.api.FileHashCalculator;
import java.io.File;
import org.junit.jupiter.api.Test;

class OpenSubtitlesFileHashCalculatorTest {

    @Test
    void testReferenceFie() throws Exception {
        // given:
        FileHashCalculator calc = new OpenSubtitlesFileHashCalculator();
        // when:
        String hash = calc.calculateHash(new File(this.getClass().getResource("/breakdance.avi").toURI()));
        // then:
        assertEquals("8e245d9679d31e12", hash);
    }

}