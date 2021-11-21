package com.JScore;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Chord implements NoteTransformation {

    private ArrayList<Note> notes = new ArrayList<>();

    public Chord (Note firstNote) { notes.add(firstNote); }

    public ArrayList<Note> getNotes() { return notes; }

    /**
     * This allows to modify a specific note in the chord.
     * @param index The index the note is at.
     * @param modification The modification to apply to the chord.
     * @throws ArrayIndexOutOfBoundsException If the index is out of bounds.
     */
    public void modifySpecificNote(int index, Function<Note,Note> modification) {
        Note note = notes.get(index);
        notes.remove(index);
        notes.add(index,modification.apply(note));
    }

    /**
     * This method will add a minor second after the last note of the chord.
     * @return The chord object
     */
    public Chord addMinor2() { return appendANoteSemitonesApartFromLastOne((byte) 1); }

    /**
     * This method will add a major second after the last note of the chord.
     * @return The chord object
     */
    public Chord addMajor2() { return appendANoteSemitonesApartFromLastOne((byte) 2); }

    /**
     * This method will add a minor third after the last note of the chord.
     * @return The chord object
     */
    public Chord addMinor3() { return appendANoteSemitonesApartFromLastOne((byte) 3); }

    /**
     * This method will add a major third after the last note of the chord.
     * @return The chord object
     */
    public Chord addMajor3() { return appendANoteSemitonesApartFromLastOne((byte) 4); }

    /**
     * This method will add a fourth after the last note of the chord.
     * @return The chord object
     */
    public Chord addPerfect4() { return appendANoteSemitonesApartFromLastOne((byte) 5); }

    /**
     * This method will add a tritone (augmented fourth) after the last note of the chord.
     * @return The chord object
     */
    public Chord addTritone() { return appendANoteSemitonesApartFromLastOne((byte) 6); }

    /**
     * This method will add a fifth after the last note of the chord.
     * @return The chord object
     */
    public Chord addPerfect5() { return appendANoteSemitonesApartFromLastOne((byte) 7); }

    /**
     * This is will append a note to the chord a certain amount of semitones away from the last note of the chord.
     * @param semitonesApart The amount of semitones away from the last note of the chord
     * @return The chord object
     */
    private Chord appendANoteSemitonesApartFromLastOne(byte semitonesApart) { return appendANewNote(new Note((byte) (notes.get(notes.size() - 1).getMidiKey() + semitonesApart))); }

    /**
     * This method will add a new note after the last note of
     * the chord.
     * @param noteToAdd The note to add
     * @return The chord object
     */
    public Chord appendANewNote(Note noteToAdd) {
        notes.add(noteToAdd);
        return this;
    }

    /**
     * Create a major chord with the last note of the chord.
     * @return The chord object
     */
    public Chord appendMajorChord() { return this.addMajor3().addMinor3(); }

    /**
     * Create a minor chord with the last note of the chord.
     * @return The chord object
     */
    public Chord appendMinorChord() { return this.addMinor3().addMajor3(); }

    /**
     * This method will transpose the chord by a certain amount of semitones.
     * @param semitones The number of semitones to transpose the chord by.
     */
    @Override
    public void transpose(byte semitones) {
        notes = (ArrayList<Note>) notes.stream()
                .peek(note -> note.transpose(semitones))
                .collect(Collectors.toList());
    }

    /**
     * This method will shift the chord by a certain amount of octaves (12 semitones).
     * @param nbOfOctaveToShift The number of octaves to shift the chord by.
     */
    @Override
    public void octaveShift(byte nbOfOctaveToShift) {
        notes = (ArrayList<Note>) notes.stream()
                .peek(note -> note.octaveShift(nbOfOctaveToShift))
                .collect(Collectors.toList());
    }

    /**
     * This method will do an octave shift on certain notes depending on the rootNoteIndex number.
     * @param rootNoteIndex The number of notes starting from the first note to shift by one octave.
     * @throws IllegalArgumentException If the rootNoteIndex number is not between 0 and chord length.
     * @return The chord object
     */
    public Chord invertChord(int rootNoteIndex) throws IllegalArgumentException {
        if (rootNoteIndex > notes.size() || rootNoteIndex < 0)
            throw new IllegalArgumentException("The number of inversion this chord has is " + notes.size());
        for (int i = 0; i < rootNoteIndex; i++) {
            modifySpecificNote(i, note -> {
                note.octaveShift((byte) 1);
                return note;
            });
        }
        return this;
    }

    /**
     * This method will stack two chords on top of one another.
     * @param bottomChord The chord at the bottom
     * @param topChord The chord at the top
     * @return The bottom chord object with the top chord on top
     */
    public static Chord stackChords(Chord bottomChord, Chord topChord) {
        topChord.getNotes().forEach(bottomChord::appendANewNote);
        return bottomChord;
    }

    @Override
    public String toString() {
        notes.sort(Note::compareTo);
        var string = new StringBuilder("Chord: [");
        notes.forEach(note -> string.append(note.getPitch()).append(", "));
        string.setLength(string.length() - 2);
        return string.append("]").toString();
    }
}
