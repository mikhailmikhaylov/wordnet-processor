package org.noname.proc;

import org.noname.proc.data.Input;

import java.util.*;

public class ConsoleEntry {

    public static void main(String[] args) {
        WordProcessor.preConfigureWordNet();

        long time = System.nanoTime();

        List<String> words = new WordProcessor(Input.VALUE).obtainUniqueWords();

        for (String word : words) {
            System.out.println(word);
        }

        time = System.nanoTime() - time;

        System.out.println(words.size() + " : " + (time / 1000000000));
    }


}
