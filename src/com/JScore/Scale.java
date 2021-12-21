import java.util.ArrayList;

public class Scale {

    private final ArrayList<Note> scaleNotes = new ArrayList<>();
    private ChordProgression scaleChords;

    public Scale(Key key) {
        generateScaleNotes(key);
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

    public ArrayList<Note> getScaleNotes() { return scaleNotes; }

    public ChordProgression getScaleChords() { return scaleChords; }

    @Override
    public String toString() {
        var string = new StringBuilder("Scale: [");
        scaleNotes.forEach(note -> string.append(note.getPitch()).append(", "));
        string.setLength(string.length() - 2);
        return string.append("]").toString();
    }
}
