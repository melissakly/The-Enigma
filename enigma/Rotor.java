package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author Melissa Ly
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        this._name = name;
        _permutation = perm;
    }

    /** Return my name. */
    String name() {
        return this._name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        return _setting;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        _setting = posn;
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        alphabet().toInt(cposn);
        _setting = cposn;
    }

    /** Return the conversion    of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {
        int conversion = permutation().
                permute(permutation().wrap(p + _setting));
        return permutation().wrap(conversion - _setting);
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int conversion = permutation().invert(permutation().wrap(e + _setting));
        return permutation().wrap(conversion - _setting);
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {

    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    /** Current setting. */
    private int _setting;

}
