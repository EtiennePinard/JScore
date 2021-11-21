package JScore;

public class Key implements NoteTransformation{

    private final Mode mode;
    private Note tonic;
    private Scale scale;

    public Key(Mode mode, Note tonic) {
        this.mode = mode;
        this.tonic = tonic;
        this.scale = new Scale(this);
    }

    /**
     * This method will transpose the key by a certain amount of semitones.
     * @param semitones The number of semitones to transpose the key by.
     */
    @Override
    public void transpose(byte semitones) {
        this.tonic = new Note((byte) (this.tonic.getMidiKey() + semitones));
        this.scale = new Scale(this);
    }

     /**
     * This method will shift the key by a certain amount of octaves (12 semitones).
     * @param nbOfOctaveToShift The number of octaves to shift the key by.
     */
     @Override
    public void octaveShift(byte nbOfOctaveToShift) {
        this.tonic = new Note((byte) (this.tonic.getMidiKey() + nbOfOctaveToShift * 12));
        this.scale = new Scale(this);
    }

    public Chord getChordByDegree(int degree) throws IllegalArgumentException {
        if (mode == Mode.Chromatic)
            throw new IllegalArgumentException("You cannot harmonize the chromatic scale! At least I do not know how.");
        if (degree < 1 || degree > scale.getScaleNotes().size())
            throw new IllegalArgumentException("There are " + (scale.getScaleNotes().size() - 1) +  " degrees in the " + tonic.getPitch()  + " " + mode + ", from 1 to " + (scale.getScaleNotes().size() - 1) + "!");
        return scale.getScaleChords().getChords().get(degree - 1);
    }

    public Scale getScale() { return scale; }

    public Mode getMode() { return mode; }

    public Note getTonic() { return tonic; }

    @Override
    public String toString() { return "Scale: [mode: " + mode + ", tonic: " + tonic + ", " + scale + ']'; }
}
