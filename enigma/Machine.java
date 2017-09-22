package enigma;

import java.util.ArrayList;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Melissa Ly
 */
class Machine {
    /**
     * Common alphabet of my rotors.
     */
    private final Alphabet _alphabet;

    /**
     * Common alphabet of my rotors.
     */
    private int _numRotor;

    /**
     * Common rotor pawls.
     */
    private int _pawl;

    /**
     * Initial settings of the rotor.
     */

    private Permutation _plugboard;

    /** An arraylist of Rotors where I can get
     * named Rotors and insert Rotors. */
    private ArrayList<Rotor> _myrotor;

    /** Rotor Array of all rotors. */
    private Rotor[] _allRotors;


    /**
     * A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     * and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     * available rotors.
     */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotor = numRotors;
        _pawl = pawls;
        _allRotors = new Rotor[allRotors.size()];
        int i = 0;
        for (Rotor r: allRotors) {
            _allRotors[i] = r;
            i++;
        }

    }

    /**
     * Return the number of rotor slots I have.
     */
    int numRotors() {
        return _numRotor;
    }

    /**
     * Return the number pawls (and thus rotating rotors) I have.
     */
    int numPawls() {
        return _pawl;
    }

    /**
     * Set my rotor slots to the rotors named ROTORS from my set of
     * available rotors (ROTORS[0] names the reflector).
     * Initially, all rotors are set at their 0 setting.
     */
    void insertRotors(String[] rotors) {
        _myrotor = new ArrayList<>();
        ArrayList<String> rotorNames = new ArrayList<>();
        for (String s: rotors) {
            for (Rotor j : _allRotors) {
                if (s.equals(j.name())) {
                    j.set(0);
                    _myrotor.add(j);
                    if (rotorNames.contains(s)) {
                        throw new EnigmaException(
                                "Duplicate Rotor names passed.");
                    } else {
                        rotorNames.add(s);
                    }
                }
            }
        }
        if (_myrotor.size() != rotors.length) {
            throw new EnigmaException("bad rotor name");
        }
    }


    /**
     * Set my rotors according to SETTING, which must be a string of four
     * upper-case letters. The first letter refers to the leftmost
     * rotor setting (not counting the reflector).
     */
    void setRotors(String setting) {
        if (setting.length() != (numRotors() - 1)) {
            throw new EnigmaException("Wheel settings too short");
        }
        if (!_myrotor.get(0).reflecting()) {
            throw new EnigmaException("Reflector is missing.");
        } else {
            if (setting.length() == (numRotors() - 1)) {
                for (int i = 1; i < _myrotor.size(); i++) {
                    if (i >= 1 && i < numRotors() - numPawls()) {
                        if (!_myrotor.get(i).reflecting()
                                && !_myrotor.get(i).rotates()) {
                            _myrotor.get(i).set(_alphabet.toInt
                                    (setting.charAt(i - 1)));
                        } else {
                            throw new EnigmaException("Non-moving rotor"
                                    + " slots mismatched.");
                        }
                    } else if (i >= numRotors() - numPawls()) {
                        if (_myrotor.get(i).rotates()) {
                            _myrotor.get(i).set(_alphabet.toInt
                                    (setting.charAt(i - 1)));
                        } else {
                            throw new EnigmaException("Moving rotor"
                                    + " slots mismatched.");
                        }
                    }
                }
            } else {
                throw new EnigmaException("Wrong length of settings.");
            }
        }
    }

    /**
     * Set the plugboard to PLUGBOARD.
     */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /**
     * Returns the result of converting the input character C (as an
     * index in the range 0..alphabet size - 1), after first advancing
     * the machine.
     */
    int convert(int c) {
        advanceRotors();
        c = c % _alphabet.size();
        if (_plugboard != null) {
            c = _plugboard.permute(c);
        }
        for (int i = _myrotor.size() - 1; i >= 0; i--) {
            Rotor forwardRotor = _myrotor.get(i);
            c = forwardRotor.convertForward(c);
        }
        for (int m = 1; m < _myrotor.size(); m++) {
            Rotor backwardRotor = _myrotor.get(m);
            c = backwardRotor.convertBackward(c);
        }
        if (_plugboard != null) {
            c = _plugboard.permute(c);
        }
        return c;

    }

    /** Helper to allow me to advance my rotors and
     * double-stepping is checker here. */
    void advanceRotors() {
        ArrayList<Rotor> moving = new ArrayList<>();
        for (int i = numRotors() - numPawls(); i < numRotors(); i++) {
            Rotor currentRotor = _myrotor.get(i);
            if (i == (numRotors() - 1)) {
                moving.add(currentRotor);
            } else if (_myrotor.get(i + 1).atNotch()
                        || moving.contains(_myrotor.get(i - 1))) {
                if (!moving.contains(currentRotor)) {
                    moving.add(currentRotor);
                }
                if (_myrotor.get(i).atNotch()) {
                    if (!moving.contains(_myrotor.get(i - 1))) {
                        moving.add(_myrotor.get(i - 1));
                    }
                }
            }
        }
        for (Rotor r: moving) {
            r.advance();
        }
    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of
     * the rotors accordingly.
     */
    String convert(String msg) {
        String converting = "";
        for (int i = 0; i < msg.length(); i++) {
            converting +=
                    _alphabet.toChar(convert(_alphabet.toInt(msg.charAt(i))));
        }
        return converting;
    }
}
