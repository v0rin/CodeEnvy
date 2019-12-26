package org.vorin.bestwords.loaders;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.vorin.bestwords.AppConfig;
import org.vorin.bestwords.model.WordList;
import org.vorin.bestwords.util.Dictionary;

import java.io.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.vorin.bestwords.util.Sources.LINGUEE_SOURCE;

public class LingueeTest {

    private static final WordInfo TEST_WORD_INFO = new WordInfo("can", null);
    private static final String TEST_CACHE_FILE_PATH = AppConfig.TEST_RES_DIR + "loaders/Linguee/" + TEST_WORD_INFO.getForeignWord();

    @Test
    public void parseAndPublish() throws IOException {
        // given
        var publisher = new XmlTranslationPublisher(null);
        var parser = new LingueeParser();

        var expectedWordList = new WordList();
        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "poder", LINGUEE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "poder", "Cheetahs can run very fast - Los guepardos pueden correr muy rapido", LINGUEE_SOURCE);

        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "saber", LINGUEE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "saber", "My sister can speak four languages - Mi hermana sabe hablar cuatro idiomas", LINGUEE_SOURCE);

        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "enlatar", LINGUEE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "enlatar", "The workers can tuna with olive oil - Los operarios enlatan el atun con aceite de oliva", LINGUEE_SOURCE);

        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "lata", LINGUEE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "lata", "Many drinks are sold in plastic bottles or cans - Muchas bebidas se venden en botellas de plastico o en latas", LINGUEE_SOURCE);

        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "bote", LINGUEE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "bote", "I have a can of beer and a bottle of water - Tengo un bote de cerveza y una botella de agua", LINGUEE_SOURCE);

        // when
        try (var canCacheFileIS = new FileInputStream(new File(TEST_CACHE_FILE_PATH))) {
            parser.parseAndPublish(TEST_WORD_INFO, canCacheFileIS, publisher);
        }

        // then
        assertThat(publisher.getWordList(), is(expectedWordList));
    }

    @Ignore
    @Test
    public void downloadTest() throws IOException {
        var downloader = new LingueeDownloader(Dictionary.EN_ES);

        try (var downloadedDataIS = downloader.download(TEST_WORD_INFO.getForeignWord());) {

            // creating a test file
            try(OutputStream fos = new FileOutputStream(TEST_CACHE_FILE_PATH)) {
                IOUtils.copy(downloadedDataIS, fos);
            }
//            System.out.println(IOUtils.toString(downloadedDataIS, StandardCharsets.UTF_8));
        }
    }
}