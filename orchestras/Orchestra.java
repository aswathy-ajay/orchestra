package orchestra;

import people.musicians.Musician;
import people.musicians.Violinist;
import utils.SoundSystem;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Orchestra implements Musician {                                                        //Creating a class which implements the Musician interface
    Integer seatNumber;                                                                             //Creating a variable to store seat number
    HashMap<Integer, Musician> seating;                                                             //Creating a variable to store a HashMap with the seat number and musician
    HashMap<Musician, Integer> seat_record;                                                         //Creating a variable to store a HashMap in the reverse order of key-value pair
    Integer[] seatList = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};       //Creating a variable which stores all the seat numbers
    int value;                                                                                      //Creating a variable which stores int value
    boolean isSeated;                                                                               //Creating a variable of boolean data type to check whether the musician is seated
    SoundSystem soundSystem;                                                                        //Creating a variable of SoundSystem data type
    int loudness;                                                                                   //Creating a data type to store the value of loudness
    List<Integer> newNotes;                                                                         //Creating a variable to store the notes
    Iterator<Integer> nextNote;                                                                     //Creating an iterator which takes up the notes
    int instrumentID;                                                                               //Creating a variable which stores the instrument ID


    public Orchestra() {                                                                            //Creating a constructor
        this.seating = new HashMap<>();
        newNotes = new ArrayList<>();
        this.seat_record = new HashMap<>();
    }

    public int sitDown(Musician musician) {                                                         //Creating the sitDown() method which returns a value based on whether or not the musician is seated
        if (seating.containsValue(musician)) {
            value = 2;                                                                              //if seated returns 2
        } else if (seating.keySet().size() == 16) {
            value = 1;                                                                              //if there are no free seats in the orchestra returns 1

        } else {
            for (int n : seatList) {
                if (seating.containsKey(n)) {
                    continue;
                } else {
                    musician.setSeat(n);
                    seatNumber = n;
                    seating.put(seatNumber, musician);
                    seat_record.put(musician, seatNumber);
                    value = 0;                                                                      //if the musician needs to be seated returns 0
                    break;
                }
            }

        }

        return value;
    }

    public void setSeat(int Seat) {                                                                 //Implementing the setSeat() method in Musician
        soundSystem.setInstrument(seatNumber, instrumentID);                                        //Setting instrument to the seat using SoundSystem
        this.seatNumber = seatNumber;
    }

    public void readScore(int[] notes, boolean soft) {                                              //Implementing the readScore() method
        for (int n : notes) {
            newNotes.add(n);                                                                        //Adding notes to List<Integer>
        }
        nextNote = newNotes.iterator();                                                             //Creating an iterator
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

    public void playNextNote() {                                                                    //Implementing the playNextNote() method which plays the next note
        for (Musician m : seating.values()) {
            m.playNextNote();
        }
    }

    public int getInstrumentID() {                                                                  //Implementing the getInstrumentID() method which returns the instrument ID
        return instrumentID;
    }

    public static void main(String[] args) throws MidiUnavailableException {                        //Creating a main() method
        SoundSystem soundSystem = new SoundSystem();
        Musician musician = new Violinist("Alice", soundSystem);
        Orchestra orchestra = new Orchestra();
        orchestra.sitDown(musician);
        System.out.println(orchestra.isSeated(musician));
        orchestra.standUp(musician);
        System.out.println(orchestra.isSeated(musician));
    }

}


