package enigma;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.HashMap;

/** Tests for the enigma machine.
 *  @author Melissa Ly
 */

public class MachineTest {

    @Test
    public void testM() {
        Alphabet alphabetz = new UpperCaseAlphabet();
        HashSet<Rotor> rotorStuff = new HashSet<Rotor>();
        HashMap map = TestUtils.NAVALA;
        String cycles1 = String.valueOf(map.get("I"));
        String cycles2 = String.valueOf(map.get("II"));
        String cycles3 = String.valueOf(map.get("III"));
        String cycles4 = String.valueOf(map.get("IV"));
        String cycles5 = String.valueOf(map.get("V"));
        String cycles6 = String.valueOf(map.get("VI"));
        String cycles7 = String.valueOf(map.get("VII"));
        String cycles8 = String.valueOf(map.get("VIII"));
        String cycles9 = String.valueOf(map.get("Beta"));
        String cycles10 = String.valueOf(map.get("Gamma"));
        String cycles11 = String.valueOf(map.get("B"));
        String cycles12 = String.valueOf(map.get("C"));
        rotorStuff.add(new MovingRotor("I",
                new Permutation(cycles1, alphabetz), "A"));
        rotorStuff.add(new MovingRotor("II",
                new Permutation(cycles2, alphabetz), "A"));
        rotorStuff.add(new MovingRotor("III",
                new Permutation(cycles3, alphabetz), "A"));
        rotorStuff.add(new MovingRotor("IV",
                new Permutation(cycles4, alphabetz), "A"));
        rotorStuff.add(new MovingRotor("V",
                new Permutation(cycles5, alphabetz), "A"));
        rotorStuff.add(new MovingRotor("VI",
                new Permutation(cycles6, alphabetz), "A"));
        rotorStuff.add(new MovingRotor("VII",
                new Permutation(cycles7, alphabetz), "A"));
        rotorStuff.add(new MovingRotor("VIII",
                new Permutation(cycles8, alphabetz), "A"));
        rotorStuff.add(new FixedRotor("Beta",
                new Permutation(cycles9, alphabetz)));
        rotorStuff.add(new FixedRotor("Gamma",
                new Permutation(cycles10, alphabetz)));
        rotorStuff.add(new Reflector("B",
                new Permutation(cycles11, alphabetz)));
        rotorStuff.add(new Reflector("C",
                new Permutation(cycles12, alphabetz)));
        Machine testM = new Machine(alphabetz, 5, 3, rotorStuff);
        String[] machineRotors = {"B", "Beta", "III", "II", "I"};
        testM.insertRotors(machineRotors);
        testM.setRotors("AAAA");
        testM.setPlugboard(new Permutation("", alphabetz));
        String testRun = testM.convert("IMDEADINSIDE");
        testM.setRotors("AAAA");
        String finalRun = testM.convert(testRun);
        assertEquals("IMDEADINSIDE", finalRun);
        testM.setRotors("BYEE");
        String firstTestRun = testM.convert(
                "THISWASTHEMOSTEXTRAPROJEVERBYE"
                        + "THISWASTHEMOSTEXTRAPROJEVERBYE");
        testM.setRotors("BYEE");
        String finalRun1 = testM.convert(firstTestRun);
        assertEquals(
                "THISWASTHEMOSTEXTRAPROJEVERBYE"
                        + "THISWASTHEMOSTEXTRAPROJEVERBYE",
                finalRun1);
    }
}
