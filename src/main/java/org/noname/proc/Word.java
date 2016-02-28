package org.noname.proc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Word {

    private final String baseWord;
    private final ArrayList<String> forms = new ArrayList<>();

    Word(String value) {
        this.baseWord = value;
    }

    String getBaseWord() {
        return baseWord;
    }

    void addForm(String form) {
        this.forms.add(form);
    }

    void addForms(String[] forms) {
        this.forms.addAll(Arrays.asList(forms));
    }

    @SuppressWarnings("unchecked")
    private List<String> getForms() {
        return (List<String>) forms.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Word)) {
            return false;
        }
        if (baseWord == null || baseWord.isEmpty()) {
            return false;
        }
        final Word other = (Word) obj;
        if (other.baseWord == null) {
            return false;
        }
        final List<String> thisForms = new ArrayList<>();
        thisForms.add(baseWord);
        thisForms.addAll(getForms());
        final List<String> otherForms = new ArrayList<>();
        otherForms.add(other.getBaseWord());
        otherForms.addAll(other.getForms());
        for (String form : thisForms) {
            if (otherForms.contains(form)) {
                return true;
            }
        }
        return false;
    }
}
