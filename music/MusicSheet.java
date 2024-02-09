package music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicSheet implements Composition {                                                    //Creating a class MusicSheet which implements the Composition Interface
    String pieceName;                                                                               //Creating a variable to store the piece name
    String musicTempo;                                                                              //Creating a variable which stores the tempo of the music
    int noteLength;                                                                                 //Creating a variable which stores the length of the note based on the tempo
    HashMap<String, Integer> midiNotes = new HashMap<>();                                           //Creating a HashMap which stores the MIDI notes and corresponding Integer notes
    int intLength;
    String instrumentName;
    boolean soft;
    ArrayList<MusicScore> musicScoreList = new ArrayList<>();                                       //Creating an ArrayList which stores MusicScore
    List<String> notesList;                                                                         //Creating a List which stores String
    int givenLength;                                                                                //Creating a variable which stores the length of the composition

    public MusicSheet(String name, String tempo, int length) {                                      //Creating a constructor
        pieceName = name;
        musicTempo = tempo;
        givenLength = length;
        midiNotes.put("C3", 48);                                                                    //Adding key-value pairs to the HashMap
        midiNotes.put("C#3", 49);
        midiNotes.put("Db3", 49);
        midiNotes.put("D3", 50);
        midiNotes.put("D#3", 51);
        midiNotes.put("Eb3", 51);
        midiNotes.put("E3", 52);
        midiNotes.put("F3", 53);
        midiNotes.put("F#3", 54);
        midiNotes.put("Gb3", 54);
        midiNotes.put("G3", 55);
        midiNotes.put("G#3", 56);
        midiNotes.put("Ab3", 56);
        midiNotes.put("A3", 57);
        midiNotes.put("A#3", 58);
        midiNotes.put("Bb3", 58);
        midiNotes.put("B3", 59);
        midiNotes.put("C4", 60);
        midiNotes.put("C#4", 61);
        midiNotes.put("Db4", 61);
        midiNotes.put("D4", 62);
        midiNotes.put("D#4", 63);
        midiNotes.put("Eb4", 63);
        midiNotes.put("E4", 64);
        midiNotes.put("F4", 65);
        midiNotes.put("F#4", 66);
        midiNotes.put("Gb4", 66);
        midiNotes.put("G4", 67);
        midiNotes.put("G#4", 68);
        midiNotes.put("Ab4", 68);
        midiNotes.put("A4", 69);
        midiNotes.put("A#4", 70);
        midiNotes.put("Bb4", 70);
        midiNotes.put("B4", 71);
        midiNotes.put("C5", 72);
        midiNotes.put("C#5", 73);
        midiNotes.put("Db5", 73);
        midiNotes.put("D5", 74);
        midiNotes.put("D#5", 75);
        midiNotes.put("Eb5", 75);
        midiNotes.put("E5", 76);
        midiNotes.put("F5", 77);
        midiNotes.put("F#5", 78);
        midiNotes.put("Gb5", 78);
        midiNotes.put("G5", 79);
        midiNotes.put("G#5", 80);
        midiNotes.put("Ab5", 80);
        midiNotes.put("A5", 81);
        midiNotes.put("A#5", 82);
        midiNotes.put("Bb5", 82);
        midiNotes.put("B5", 83);
        midiNotes.put("C6", 84);
        midiNotes.put("C7", 96);
        midiNotes.put("C8", 108);
        midiNotes.put("C9", 120);
        midiNotes.put("none", 0);
    }

    public int getNoteLength() {                                                                    //Implementing the getNotelength() method
        if (musicTempo.equals("Larghissimo")) {
            noteLength = 1500;
        } else if (musicTempo.equals("Lento")) {
            noteLength = 1000;
        } else if (musicTempo.equals("Adante")) {
            noteLength = 500;
        } else if (musicTempo.equals("Moderato")) {
            noteLength = 300;
        } else if (musicTempo.equals("Allegro")) {
            noteLength = 175;
        } else if (musicTempo.equals("Presto")) {
            noteLength = 150;
        }
        return noteLength;
    }

    public void addScore(String instrumentName, List<String> notes, boolean soft) {                 //Implementing the addScore() method
        ArrayList<Integer> intNotes = new ArrayList<>();                                            //Creating an ArrayList which stores Integer
        int[] newIntNotes = new int[notes.size()];                                                  //Creating an int[] which stores the same as intNotes
        notesList = notes;                                                                          //Storing the parameter into a variable
        for (String n : notes) {
            if (midiNotes.containsKey(n)) {
                intNotes.add(midiNotes.get(n));                                                     //Adding the integer value of MIDI notes to ArrayList

            } else if (n.equals("none")) {
                intNotes.add(0);                                                                    //Adding zero when MIDI notes is none
            }
        }
        for (int m = 0; m < intNotes.size(); m++) {
            newIntNotes[m] = intNotes.get(m);                                                       //Converting into int[]
        }
        System.out.println(newIntNotes);
        MusicScore newMusicScore = new MusicScore(instrumentName, newIntNotes, soft);               //Creating a MusicScore object
        musicScoreList.add(newMusicScore);                                                          //Adding to the ArrayList<MusicScore>
    }


    public int getLength() {                                                                                            //Implementing the getLength() method
        return givenLength;
    }

    public MusicScore[] getScores() {                                                               //Implementing the getScores() method
        MusicScore[] musicScoreArray = new MusicScore[musicScoreList.size()];                       //Creating a MusicScore[] with the same size as the ArrayList
        for (int i = 0; i < musicScoreList.size(); i++) {
            musicScoreArray[i] = musicScoreList.get(i);                                             //Converting into MusicScore[]
        }
        return musicScoreArray;
    }

    public String getName() {                                                                       //Implementing the getName() method
        return pieceName;
    }

    public static void main(String[] args) {                                                        //Creating a main() method
        MusicSheet musicSheet = new MusicSheet("Ashy", "Lento", 17);
        String[] stringList = new String[]{"G4", "F4", "G4", "F4", "G4", "F4", "G4", "F4", "G4", " none ", " none ", "G4", "F4", "G4", "F4", "G4", "G4"};
        int count = 0;
        List<String> newStringList = new ArrayList<>();
        for (String i : stringList) {
            newStringList.add(i);
            count += 1;
        }
        musicSheet.addScore("Violin", newStringList, true);

    }
}