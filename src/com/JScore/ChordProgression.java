import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChordProgression implements NoteTransformation {

    private ArrayList<Chord> chords = new ArrayList<>();
    private final Key key;

    public ChordProgression(Key key) {
        this.key = key;
    }

    /**
     * This allows to modify a specific chord in the progression.
     * @param index The index the chord is at.
     * @param modification The modification to apply to the chord.
     * @throws ArrayIndexOutOfBoundsException If the index is out of bounds.
     */
    public void modifySpecificChord(int index, Function<Chord,Chord> modification) {
        Chord chord = chords.get(index);
        chords.remove(index);
        chords.add(index,modification.apply(chord));
    }

    /**
     * This method will transpose the chord progression by the number of semitones.
     * @param semitones The number of semitones to transpose the chord progression by.
     */
    @Override
    public void transpose(byte semitones) {
        this.key.transpose(semitones);
        chords = (ArrayList<Chord>) chords.stream()
                .peek(chord -> chord.transpose(semitones))
                .collect(Collectors.toList());
    }

    /**
     * This method will shift the chord progression by a certain amount of octaves (12 semitones).
     * @param nbOfOctaveToShift The number of octaves to shift the chord progression by.
     */
    public void octaveShift(byte nbOfOctaveToShift) {
        this.key.octaveShift(nbOfOctaveToShift);
        chords = (ArrayList<Chord>) chords.stream()
                .peek(chord -> chord.octaveShift(nbOfOctaveToShift))
                .collect(Collectors.toList());
    }

    /**
     * Add the chord of the specified degree in the chord progression scale.
     * @param degree The degree of the key. This will be the root note of the chord.
     * @throws RuntimeException If the key equals No_Key.
     * @throws IllegalArgumentException If the scale degree is not between 1 and 7
     * @return The chord progression object with the added chord or no new chord (if something weird happens)
     */
    public ChordProgression addChordBasedOnScaleDegree(int degree) { return this.addAChord(key.getChordByDegree(degree)); }

    /**
     * Adds a major chord to the end of the progression.
     * @param rootNote RootNote of the major chord
     * @return The chord progression object
     */
    public ChordProgression addMajorChord(Note rootNote) { return addAChord(new Chord(rootNote).appendMajorChord()); }

    /**
     * Adds a minor chord to the end of the progression.
     * @param rootNote RootNote of the minor chord
     * @return The chord progression object
     */
    public ChordProgression addMinorChord(Note rootNote) { return addAChord(new Chord(rootNote).appendMinorChord()); }

    /**
     * Adds a chord to the end of the chord progression.
     * @param chord The chord to add to the progression.
     * @return The chord progression object.
     */
    public ChordProgression addAChord(Chord chord) {
        chords.add(chord);
        return this;
    }

    public ArrayList<Chord> getChords() { return chords; }

    public Key getKey() { return key; }

    @Override
    public String toString() {
        var string = new StringBuilder("Chord progression: [").append("Key: ").append(key);
        chords.forEach(chord -> string.append(", ").append(chord));
        return string.append("]").toString();
    }
}
