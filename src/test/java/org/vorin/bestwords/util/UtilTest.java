package org.vorin.bestwords.util;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void stripTrailingDot() {
        assertThat(Util.trimAndStripTrailingDot("some sentence."), is("some sentence"));
        assertThat(Util.trimAndStripTrailingDot("some. sentence."), is("some. sentence"));
        assertThat(Util.trimAndStripTrailingDot("some. sentence"), is("some. sentence"));
    }

    @Test
    public void chooseShortestString() {
        assertThat(Util.chooseShortestString(List.of("", "22", "333")), is(""));
        assertThat(Util.chooseShortestString(List.of("22", "", "333")), is(""));
        assertThat(Util.chooseShortestString(List.of("22", "333", "")), is(""));

        assertThat(Util.chooseShortestString(List.of("1", "22", "333")), is("1"));
        assertThat(Util.chooseShortestString(List.of("22", "1", "333")), is("1"));
        assertThat(Util.chooseShortestString(List.of("22", "333", "1")), is("1"));

        assertThat(Util.chooseShortestString(List.of("1", "22", "333"), 2), is("22"));
        assertThat(Util.chooseShortestString(List.of("22", "1", "333"), 2), is("22"));
        assertThat(Util.chooseShortestString(List.of("22", "333", "1"), 2), is("22"));
    }

    @Test
    public void firstLetterOfSentenceToLowerCase() {
        assertThat(Util.firstLetterOfSentenceToLowerCase("He is"), is("he is"));
        assertThat(Util.firstLetterOfSentenceToLowerCase("he is"), is("he is"));
        assertThat(Util.firstLetterOfSentenceToLowerCase("I am"), is("I am"));
    }
}