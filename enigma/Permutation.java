package enigma;

import java.util.ArrayList;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Melissa Ly
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters not
     *  included in any cycle map to themselves. Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        sortedList = new ArrayList<>();
        for (int i = 0; i < _alphabet.size(); i++) {
            sortedList.add(i);
        }
        String[] arrayOfStrings;
        cycles = cycles.replaceAll("[)(]", "");
        arrayOfStrings = cycles.split(" ");
        for (String c: arrayOfStrings) {
            addCycle(c);
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        for (int i = 0; i < cycle.length(); i++) {
            char c = cycle.charAt(i);
            if (cycle.length() < 1) {
                sortedList.set(_alphabet.toInt(c),
                        _alphabet.toInt(cycle.charAt(i)));
            } else if (i != (cycle.length() - 1)) {
                sortedList.set(_alphabet.toInt(c),
                        alphabet().toInt(cycle.charAt(i + 1)));
            } else {
                sortedList.set(_alphabet.toInt(c),
                        alphabet().toInt(cycle.charAt(0)));
            }
        }
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. If p is greater than alphabet
     *  size then you will get the remainder */
    int permute(int p) {
        return sortedList.get(wrap(p));
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        return sortedList.indexOf(wrap(c));
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int i = _alphabet.toInt(p);
        return _alphabet.toChar(permute(i));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    int invert(char c) {
        int i = _alphabet.toInt(c);
        return _alphabet.toChar(invert(i));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (int i : sortedList) {
            if (i == sortedList.indexOf(i)) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    /** Size of alphabet. */
    private int size;
    /** ArrayList to store the permuted list. */
    private ArrayList<Integer> sortedList;
}

