import java.util.List;

/**
 * This is an enum containing the 7 modes of the major scale and 4 extra modes being major, harmonic minor, natural minor and chromatic.
 * Pick chromatic if your song/chordProgression has no key.
 * Also, there is no minor harmonic cause not clear when you actually go down.
 */
public enum Mode {

    Ionian(List.of(2,2,1,2,2,2,1)), // The major scale
    Dorian(List.of(2,1,2,2,2,1,2)), // Major scale with flat 3 and 7
    Phrygian(List.of(1,2,2,2,1,2,2)), // Major scale with flat 2, 3, 6 and 7
    Lydian(List.of(2,2,2,1,2,2,1)), // Major scale with sharp 4
    Mixolydian(List.of(2,2,1,2,2,1,2)), // Major scale with flat 7
    Aeolian(List.of(2,1,2,2,1,2,2)), // The natural minor scale
    Locrian(List.of(1,2,2,1,2,2,2)), // Major scale with flat 2, 3, 5, 6 and 7

    Major(List.of(2,2,1,2,2,2,1)), // The normal major scale. It is the same as the Ionian mode.
    Minor_Natural(List.of(2,1,2,2,1,2,2)), // The normal minor scale. It is the same as the Aeolian mode.
    Minor_Harmonic(List.of(2,1,2,2,1,3,1)), // The natural minor scale with a sharp 7
    Chromatic(List.of(1,1,1,1,1,1,1,1,1,1,1,1)); // The chromatic scale. Pick this one if you have no specific key on your progression.

    private final List<Integer> steps;

    Mode(List<Integer> steps) { this.steps = steps; }

    /**
     * Gets the interval between the notes of this mode
     * @return The interval between the notes of this mode
     */
    public List<Integer> getSteps() { return this.steps; }

}
