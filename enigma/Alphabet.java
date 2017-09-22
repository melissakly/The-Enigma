package enigma;

import java.util.HashMap;

import static enigma.EnigmaException.*;

/* Extra Credit Only */

/**
 * An alphabet of encodable characters.  Provides a mapping from characters
 * to and from indices into the alphabet.
 *
 * @author Melissa Ly
 */
class Alphabet {

    /**
     * alphabet of encodable characters.
     */
    private Alphabet _alphabet;
    /**
     * Hashmap to store characters and their corresponding indices.
     */
    private HashMap<Character, Integer> _index;
    /**
     * Hashmap to store indexes and their corresponding characters.
     */
    private HashMap<Integer, Character> character;

    /** String for characters. */
    private String _chars;

    /**
     * A new alphabet containing CHARS.  Character number #k has index
     * K (numbering from 0). No character may be duplicated.
     */
    Alphabet(String chars) {
        _chars = chars;
        if (_chars.length() == 0) {
            throw new EnigmaException("No alphabet inputs");
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length(); i++) {
            char c = chars.charAt(i);
            if ((c != '*') && (c != '(')
                    && (c != ')')
                    && (c != ' ') && (!map.containsKey(c))) {
                if (Character.isUpperCase(c)) {
                    map.put(c, i);
                } else {
                    c = Character.toUpperCase(c);
                    map.put(c, i);
                }
            } else {
                throw new EnigmaException("alphabet has illegal characters");
            }
        }
        _index = map;
        character = new HashMap<>();
        for (char c : _index.keySet()) {
            character.put(_index.get(c), c);
        }
    }


    /**
     * Returns the size of the alphabet.
     */
    int size() {
        return character.size();
    }

    /**
     * Returns true if C is in this alphabet.
     */
    boolean contains(char c) {
        return character.containsValue(c);
    }

    /**
     * Returns character number INDEX in the alphabet, where
     * 0 <= INDEX < size().
     */
    char toChar(int index) {
        if (0 <= index && index < size()) {
            return character.get(index);
        } else {
            throw new EnigmaException("Index out of bounds");
        }
    }

    /**
     * Returns the index of character C, which must be in the alphabet.
     */
    int toInt(char c) {
        if (contains(c)) {
            return _index.get(c);
        } else {
            throw new EnigmaException("Character s not in alphabet");
        }
    }
}
