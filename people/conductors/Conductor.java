package people.conductors;

import music.Composition;
import music.MusicScore;
import orchestra.Orchestra;
import people.Person;
import people.musicians.Musician;
import utils.SoundSystem;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Conductor extends Person {                                                             //Creating a class which extends Person class
    String stringVariable;                                                                          //Creating a variable which stores the conductor name
    SoundSystem soundSystem;                                                                        //Creating a variable of data type SoundSystem
    Musician musician;                                                                              //Creating a variable of data type Musician
    MusicScore[] musicScore;                                                                        //Creating a variable of MusicScore[] data type
    HashSet<Musician> musicianBand;                                                                 //Creating variable to store a set of Musicians
    ArrayList<Conductor> conductorArray;
    HashMap<Musician, MusicScore[]> registerMap;                                                    //Creating a variable of HashMap which stores Musician- MusicScore[] data type
    int noteLength;                                                                                 //Creating a variable to store the note length
    Conductor conductor;
    Orchestra orchestra = new Orchestra();                                                          //Creating an Orchestra object
    int[] notes;                                                                                    //Creating a variable which stores notes
    boolean value;                                                                                  //Creating a variable to store boolean value
    int instrumentID;                                                                               //Creating a variable to store instrument ID
    String instrumentName;                                                                          //Creating a variable to store instrument name
    Integer[] seatList = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};       //Creating a variable which stores all the seat numbers
    int intValue;                                                                                   //Creating a variable which stores int value
    Integer seatNumber;                                                                             //Creating a variable to store seat number
    HashMap<Musician, Integer> seat_record;                                                         //Creating a variable to store a HashMap in the reverse order of key-value pair
    HashMap<Integer, Musician> seating;                                                             //Creating a variable to store a HashMap with the seat number and musician

    public Conductor(String stringVariable, SoundSystem newSoundSystem) {                           //Creating a constructor
        super(stringVariable);
        musicianBand = new HashSet<>();
        registerMap = new HashMap<>();
        soundSystem = newSoundSystem;
        this.seating = new HashMap<>();
        this.seat_record = new HashMap<>();

    }

    public void registerMusician(Musician musician) {                                               //Creating a registerMusician() method which adds the musician collection
        musicianBand.add(musician);
    }

    public void playComposition(Composition composition) {                                          //Creating a playComposition() method which plays the composition
        musicScore = composition.getScores();                                                       //Using composition.getScores() which will return the scores
        for (MusicScore n : musicScore) {
            notes = n.getNotes();
            if (n.isSoft()) {                                                                       //Checking if the MusicScore is soft ot not
                value = true;
            } else {
                value = false;
            }
            instrumentID = n.getInstrumentID();                                                     //Getting the instrument ID
            for (Musician m : musicianBand) {
                if (instrumentID == m.getInstrumentID()) {
                    orchestra.sitDown(m);                                                           //Making the Musician sit down
                    m.readScore(notes, value);                                                      //Reading the score
                }
                registerMap.put(m, musicScore);                                                     //Adding it to the HashMap
            }
            musicScore = composition.getScores();
        }
        noteLength = composition.getLength();                                                       //Returns length of the composition
        soundSystem.setSilentMode(false);                                                           //Setting whether it is silent or not
        soundSystem.setTextMode(true);                                                              //Setting to show texts
        for (int i = 0; i <= noteLength; i++) {                                                     //Looping as long as the length of the composition
            orchestra.playNextNote();                                                               //Orchestra will call playNextNote method
            try {
                //Thread.sleep(composition.getNoteLength());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                continue;
            }
        }
        soundSystem.init();                                                                         //Stops playing the music
    }
    public int sitDown(Musician musician) {                                                         //Creating the sitDown() method which returns a value based on whether or not the musician is seated
        if (seating.containsValue(musician)) {
            intValue = 2;                                                                           //if seated returns 2
        } else if (seating.keySet().size() == 16) {
            intValue = 1;                                                                           //if there are no free seats in the orchestra returns 1

        } else {
            for (int n : seatList) {
                if (seating.containsKey(n)) {
                    continue;
                } else {
                    musician.setSeat(n);
                    seatNumber = n;
                    seating.put(seatNumber, musician);
                    seat_record.put(musician, seatNumber);
                    intValue = 0;                                                                   //if the musician needs to be seated returns 0
                    break;
                }
            }

        }

        return intValue;
    }

    public boolean isSeated(Musician musician) {                                                    //Creating an isSeated() method which returns a boolean
        return seating.containsValue(musician);                                                     //Checking if the musician is seated
    }

    public void standUp(Musician musician) {                                                        //Creating a standUp() method which makes the musician stand from the orchestra
        if (isSeated(musician)) {
            seating.remove(seat_record.get(musician));
            seat_record.remove(musician);
            System.out.println("Musician is standing up");
        }
    }

    public static void main(String[] args) throws MidiUnavailableException {                        //Creating a main() method
        Conductor conductor = new Conductor("Aswathy", new SoundSystem());

    }
}
