import java.util.Comparator;
import java.util.Iterator;

import stdlib.StdRandom;

public class Sentence implements Comparable<Sentence>, Iterable<String> {
    private String s; // the sentence
    private String[] words; // words in the sentence

    public Sentence(String s) { // constructs a Sentence object from the sentence s
        this.s = s;

        this.words = s.split("\\s+");
    }

    public int charCount() { // return the numbers of characters in the sentence
        return this.s.length();
    }

    public int wordCount() { // return the number of words in the sentence
        return this.words.length;
    }

    public boolean equals(Object other) { // return true if the sentence is the same as other, and false otherwise
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return this.s.equals(((Sentence) other).s);
    }

    public String toString() { // return a string representation of this sentence in "<sentences>:<char count>
                               // format"
        return this.s + ":" + this.s.length();
    }

    public int compareTo(Sentence other) { // returns a comparison of this and other sentence
        return this.charCount() - other.charCount();
    }

    public static Comparator<Sentence> wordCountOrder() { // returns a comparator for comparing sentences based on their
                                                          // word counts
        return new wordCountOrder();
    }

    public Iterator<String> iterator() { // returns RandomwordIterator
        return new RandomWordIterator();
    }

    private static class wordCountOrder implements Comparator<Sentence> {
        public int compare(Sentence s1, Sentence s2) {
            return s1.wordCount() - s2.wordCount();
        }
    }

    private class RandomWordIterator implements Iterator<String> {
        private int i; // index of current letter
        private String[] shuffledWords;

        public RandomWordIterator() {
            shuffledWords = new String[words.length];
            for (int i = 0; i < words.length; i++) {
                shuffledWords[i] = words[i];
            }

            for (int i = shuffledWords.length - 1; i > 0; i--) {
                int j = StdRandom.uniform(i + 1);
                String temp = shuffledWords[i];
                shuffledWords[i] = shuffledWords[j];
                shuffledWords[j] = temp;
            }

        }

        public boolean hasNext() {

            return i < shuffledWords.length;
        }

        public String next() {
            return shuffledWords[i++];
        }
    }
}