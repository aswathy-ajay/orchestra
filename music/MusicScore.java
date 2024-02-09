package music;

public class MusicScore {
    String name;                                                                                    //Creating a variable which takes the instrument name
    int[] musicNotes;                                                                               //Creating a variable which takes the Integer list of notes
    boolean boolSoft;                                                                               //Creating a variable which takes the boolean value
    int instrumentID;                                                                               //Creating a variable which takes the instrument ID
    boolean value;                                                                                  //Creating a variable which stores a boolean value

    public MusicScore(String instrumentName, int[] notes, boolean soft) {                           //Creating a constructor
        name = instrumentName;
        musicNotes = notes;
        boolSoft = soft;
    }

    public int getInstrumentID() {                                                                  //Creating a getInstrumentID() method which returns the instrument ID based on the instrument name
        if (name.equals("Violin")) {
            instrumentID = 41;
        } else if (name.equals("Cello")) {
            instrumentID = 43;
        } else if (name.equals("Piano")) {
            instrumentID = 1;
        }
        return instrumentID;
    }

    public int[] getNotes() {                                                                       //Creating a getNotes() method which returns the Integer list of notes
        return musicNotes;
    }

    public boolean isSoft() {                                                                       //Creating a isSoft() method which returns a boolean value based on whether the music must be played softly or not
        if (boolSoft) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    public static void main(String[] args) {                                                        //Creating a main() method
        MusicScore musicscore = new MusicScore("Violin", new int[]{60, 60, 62, 0, 60, 0, 65, 0, 64, 0, 0, 0, 60, 60, 62, 0, 60, 0, 67, 0, 65, 0, 0, 0, 60, 60, 72, 0, 69, 0, 65, 0, 64, 0, 62, 0, 70, 70, 69, 0, 65, 0, 67, 0, 65, 0, 0, 0}, true);
        System.out.println(musicscore.getInstrumentID());
        System.out.println(musicscore.getNotes());
        System.out.println(musicscore.isSoft());

    }
}