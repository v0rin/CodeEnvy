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
import static org.vorin.bestwords.util.Sources.WORD_REFERENCE_SOURCE;

public class WordReferenceTest {

    private static final WordInfo TEST_WORD_INFO = new WordInfo("can", null);
    private static final String TEST_CACHE_FILE_PATH = AppConfig.TEST_RES_DIR + "loaders/WordReference/" + TEST_WORD_INFO.getForeignWord();

    @Test
    public void parseAndPublish() throws IOException {
        // given
        var publisher = new XmlTranslationPublisher(null);
        var parser = new WordReferenceParser();

        var expectedWordList = new WordList();
        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "poder", WORD_REFERENCE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "poder", "I can carry those suitcases for you - Puedo llevarte esas maletas", WORD_REFERENCE_SOURCE);

        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "saber", WORD_REFERENCE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "saber", "She can play the piano - Ella sabe tocar el piano", WORD_REFERENCE_SOURCE);

        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "ser posible", WORD_REFERENCE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "ser posible", "Such things can happen if you're not careful - Es posible que pasen cosas asi si no llevas cuidado", WORD_REFERENCE_SOURCE);

        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "bote", WORD_REFERENCE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "bote", "We need three more cans of paint - Necesitamos tres botes mas de pintura", WORD_REFERENCE_SOURCE);

        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "lata", WORD_REFERENCE_SOURCE);
        expectedWordList.addExampleSentence(TEST_WORD_INFO.getForeignWord(), "lata", "Pass me that can of peas - Pasame esa lata de guisantes", WORD_REFERENCE_SOURCE);

        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "tacho", WORD_REFERENCE_SOURCE);
        expectedWordList.addMeaning(TEST_WORD_INFO.getForeignWord(), "cesto", WORD_REFERENCE_SOURCE);

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
        var downloader = new WordReferenceDownloader(Dictionary.EN_ES);

        try (var downloadedDataIS = downloader.download(TEST_WORD_INFO.getForeignWord())) {

            // creating a test file
            try(OutputStream fos = new FileOutputStream(TEST_CACHE_FILE_PATH)) {
                IOUtils.copy(downloadedDataIS, fos);
            }
//            System.out.println(IOUtils.toString(downloadedDataIS, StandardCharsets.UTF_8));
        }
    }
}