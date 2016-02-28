package org.noname.proc;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import org.noname.proc.data.Words;

import java.util.*;

/* package */ class WordProcessor {
    private final String input;

    private final WordNetDatabase database;
    private List<Word> knownWords;
    private List<Word> inputWords;
    private List<Word> uniqueInput;

    WordProcessor(String input) {
        this.input = input;
        database = WordNetDatabase.getFileInstance();
        prepareKnownWords();
        prepareInput();
        obtainUniqueInput();
    }

    static void preConfigureWordNet() {
        String home = System.getProperty("user.home");
        System.out.println(home);
        System.setProperty("wordnet.database.dir", home + "/Downloads/WordNet-3.0/dict");
//        System.setProperty("wordnet.database.dir", "/Users/mmikhaylov/Downloads/WordNet-3.0/dict");
    }

    List<String> obtainUniqueWords() {
        final List<String> uniqueWords = new ArrayList<>(uniqueInput.size());
        for (Word word : uniqueInput) {
            uniqueWords.add(word.getBaseWord());
        }
        return uniqueWords;
    }

    private void obtainUniqueInput() {
        final List<Word> uniqueInput = new ArrayList<>();

        for (Word word : inputWords) {
            boolean known = false;
            for (Word knownWord : knownWords) {
                if (word.equals(knownWord)) {
                    known = true;
                    break;
                }
            }
            if (!known) {
                uniqueInput.add(word);
            }
        }

        this.uniqueInput = uniqueInput;
    }

    private void prepareInput() {
        String input = this.input.toLowerCase().replace(".", "").replace(",", "").replace("!", "")
                .replace("-", "").replace("?", "").replace("\"", " ").replace("\n", " ");
        String[] splitInput = input.trim().split(" ");

        final Set<String> uniqueInput = new LinkedHashSet<>();
        Collections.addAll(uniqueInput, splitInput);

        final List<Word> inputWords = new ArrayList<>(uniqueInput.size());
        for (String word : uniqueInput) {
            Word builtWord = buildWord(word);
            final String tWord = truncateApostrophe(word);
            if (!word.equals(tWord)) {
                builtWord.addForm(tWord);
            }
            inputWords.add(builtWord);
        }

        this.inputWords = inputWords;
    }

    private String truncateApostrophe(String word) {
        if (!word.contains("'")) {
            return word;
        }
        int index = word.indexOf("'");
        return word.substring(0, index);
    }

    private void prepareKnownWords() {
        String[] splitKnownWords = Words.VALUE.split(" ");
        final List<Word> knownWords = new ArrayList<>(splitKnownWords.length);

        for (String word : splitKnownWords) {
            knownWords.add(buildWord(word));
        }

        this.knownWords = knownWords;
    }

    private Word buildWord(String word) {
        final Word knownWord = new Word(word);
        Synset[] synsets = database.getSynsets(word);
        if (synsets.length > 0) {
            String[] baseFormCandidates = database.getBaseFormCandidates(word, synsets[0].getType());
            knownWord.addForms(baseFormCandidates);
        }
        return knownWord;
    }
}
