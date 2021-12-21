import java.util.ArrayList;

/**
 * This represents a scale in music.
 * The scale object will have 2 fields: a list of notes of the scale and a list of chords of this scale.
 * Note: The chords field can be null if the mode of the key is the chromatic mode.
 */
public class Scale {

    private final ArrayList<Note> scaleNotes = new ArrayList<>();
    private ChordProgression scaleChords;

    /**
     * Creates a scale object with the desired key
     * @param key The key of this scale
     */
    public Scale(Key key) {
        generateScaleNotes(key);
        if (!key.getMode().equals(Mode.Chromatic))
            generateScaleChords(key);
    }

    private void generateScaleNotes(Key key) {
        scaleNotes.clear();
        scaleNotes.add(key.getTonic());
        for(int i = 0; i < key.getMode().getSteps().size(); i++)
            scaleNotes.add(new Note((byte) (scaleNotes.get(i).getMidiKey() + key.getMode().getSteps().get(i))));
    }

    private void generateScaleChords(Key key) {
        scaleChords = new ChordProgression(key);
        for (int i = 1; i <= scaleNotes.size(); i++)
            switch (i) {
                case 1,4,5 -> scaleChords.addAChord(new Chord(scaleNotes.get(i - 1)).appendMajorChord());
                case 2,3,6 -> scaleChords.addAChord(new Chord(scaleNotes.get(i - 1)).appendMinorChord());
                case 7 -> scaleChords.addAChord(new Chord(scaleNotes.get(i - 1)).addMinor3().addMinor3());
            }
    }

    /**
     * Gets the notes of this scale, starting from the tonic to the leading tone / subtonic
     * @return The notes of this scale
     */
    public ArrayList<Note> getScaleNotes() { return scaleNotes; }

    /**
     * Gets the chords of this scale, in a chord progression object, starting from the tonic chord to the leading tone / subtonic chord
     * This will return null if the mode of this scale key is the chromatic mode
     * @return The chords of this scale
     */
    public ChordProgression getScaleChords() { return scaleChords; }

    /**
     * Gets the string representation of this scale
     * @return The string representation of this scale
     */
    @Override
    public String toString() {
        var string = new StringBuilder("Scale: [");
        scaleNotes.forEach(note -> string.append(note.getPitch()).append(", "));
        string.setLength(string.length() - 2);
        return string.append("]").toString();
    }
}
