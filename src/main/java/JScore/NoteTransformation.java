package JScore;

public interface NoteTransformation {

    void transpose(byte semitones);

    void octaveShift(byte nbOfOctaveToShift);
}
