import java.lang.reflect.Array;
import music.Composition;
import music.MusicScore;
import music.MusicSheet;
import orchestra.Orchestra;
import people.conductors.Conductor;
import people.musicians.Cellist;
import people.musicians.Musician;
import people.musicians.Pianist;
import people.musicians.Violinist;
import utils.SoundSystem;

import javax.sound.midi.MidiUnavailableException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.*;

public class EcsBandAid {                                                                           //Creating a class EcsBandAid
    SoundSystem soundSystem;                                                                        //Creating a variable of data type SoundSystem
    ArrayList<Musician> musiciansCollection = new ArrayList<>();                                    //Creating a variable to store Musicians
    ArrayList<Composition> compositionsCollection = new ArrayList<>();                              //Creating a variable to store Compositions
    Random random = new Random();                                                                   //Creating a Random object
    int number;                                                                                     //Creating a variable to store an int
    Composition composition;                                                                        //Creating a Composition object
    Conductor conductor;                                                                            //Creating a conductor object
    String musicTempo;

    public EcsBandAid(SoundSystem newSoundSystem, ArrayList<Musician> MusiciansCollection, ArrayList<Composition> CompositionsCollection) { //Creating a constructor
        soundSystem = newSoundSystem;
        musiciansCollection = MusiciansCollection;
        compositionsCollection = CompositionsCollection;
        conductor = new Conductor("Name", soundSystem);

    }

    public void performForAYear() {                                                                 //Creating a performForAYear() method
        for (int i = 0; i < 3; i++) {                                                               //Creating a loop to choose three compositions
            if (compositionsCollection.size() != 0 && musiciansCollection.size()!=0) {              //Checking if the compositions collection is zero
                number = random.nextInt(compositionsCollection.size() - 1);                   //Choosing a random number using Random data type
                composition = compositionsCollection.get(number);                                   //Getting a random composition
                System.out.println("Name of composition is: " + composition.getName());             //Printing the name of the composition
                ArrayList<Musician> musiciansCollectionTemp= (ArrayList<Musician>)musiciansCollection.clone();  //Creating the copy of collection of musicians
                for (Musician m : musiciansCollection) {                                            //Using for enhanced loop through the collection of musicians
                    conductor.sitDown(m);                                                           //Making the musician sit down
                    conductor.registerMusician(m);
                    conductor.playComposition(composition);                                         //Playing the composition
                }
                for (Musician m : musiciansCollectionTemp) {                                        //Iterating through the collection of musicians
                    int choice = random.nextInt(2);                                           //Using Random object to generate a number
                    if (choice == 0) {                                                              //Checking the 50% chance of a musician leaving
                        musiciansCollection.remove(m);                                              //Removing the musician
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws MidiUnavailableException {                        //Creating a main() method
        String musiciansFile = args[0];                                                             //Assigning the file path to musicians file variable
        String compositionsFile = args[1];                                                          //Assigning the file path to compositions file variable
        int years = Integer.valueOf(args[2]);                                                       //Assigning the value to the variable
        FileReader fileReaderMusicians;                                                             //Creating a FileReader for musicians
        FileReader fileReaderCompositions;                                                          //Creating a FileReader for compositions
        BufferedReader readerMusicians;                                                             //Creating a BufferedReader for musicians
        BufferedReader readerCompositions;                                                          //Creating a BufferedReader for compositions
        String[] musiciansDetails;                                                                  //Creating a variable of String[] data type
        String musiciansName;                                                                       //Creating a variable of String data type to store the musicians name
        String instrumentName;                                                                      //Creating a variable of String data type to store the instrument name
        SoundSystem soundSystem = new SoundSystem();                                                //Creating a variable of data type SoundSystem
        Conductor newConductor = new Conductor("Aswathy", soundSystem);                  //Creating a conductor object
        ArrayList<Musician> musiciansCollection = new ArrayList<>();                                //Creating a musicians collection
        String[] compositionsDetails;                                                               //Creating a String[] data type
        String compositionsName = null;                                                             //Creating a variable of String data type to store the composition name
        String compositionsTempo = null;                                                            //Creating a variable of String data type to store the composition tempo
        String Tempo;
        int length = 0;
        String compositionsInstrument;                                                              //Creating a variable to store the compositions instrument
        boolean compositionsLoudness = false;                                                       //Creating a variable to store the composition loudness
        int compositionsLength = 0;                                                                 //Creating a variable to store the composition length
        ArrayList<Composition> compositionsCollection = new ArrayList<>();                          //Creating a compositions collection
        try {
            fileReaderMusicians = new FileReader(musiciansFile);                                    //Initializing FileReader for musicians
            fileReaderCompositions = new FileReader(compositionsFile);                              //Initializing FileReader for compositions
            readerMusicians = new BufferedReader(fileReaderMusicians);                              //Initializing BufferedReader for musicians
            readerCompositions = new BufferedReader(fileReaderCompositions);                        //Initializing BufferedReader for compositions
        } catch (FileNotFoundException e) {                                                         //Catching the FileNotFoundException
            throw new RuntimeException(e);
        }

        //Manipulating the musicians.txt file
        try {
            String musiciansLine = readerMusicians.readLine();                                      //Reading one line
            while (musiciansLine != null) {                                                         //Checking if the reader reached the end of the file
                musiciansDetails = musiciansLine.split("\\(");                                //Splitting the value
                musiciansName = musiciansDetails[0];                                                //Assigning the musicians name to the 0th index
                instrumentName = musiciansDetails[1];                                               //Assigning the instruments name to the 1st index
                if (instrumentName.contains(")")){                                                  //Removing the brackets
                    instrumentName= instrumentName.replace(")","");
                }
                if (Objects.equals(instrumentName, "Piano")) {                                   //Checking if the instrument is Piano
                    Pianist pianistObject = new Pianist(musiciansName, soundSystem);                //Creating a Pianist object
                    newConductor.registerMusician(pianistObject);                                   //Registering the musician with the conductor
                    musiciansCollection.add(pianistObject);                                         //Adding to the musicians collection
                } else if (Objects.equals(instrumentName, "Violin")) {                           //Checking if the instrument is Violin
                    Violinist violinistObject = new Violinist(musiciansName, soundSystem);          //Creating a Violinist object
                    newConductor.registerMusician(violinistObject);                                 //Registering the musician with the conductor
                    musiciansCollection.add(violinistObject);                                       //Adding to the musicians collection
                } else if (Objects.equals(instrumentName, "Cello")) {                            //Checking if the instrument is Cello
                    Cellist cellistObject = new Cellist(musiciansName, soundSystem);                //Creating a Cellist object
                    newConductor.registerMusician(cellistObject);                                   //Registering the musician with the conductor
                    musiciansCollection.add(cellistObject);                                         //Adding to the musicians collection
                }
                musiciansLine = readerMusicians.readLine();                                         //Reading the next line
            }
        } catch (IOException e) {                                                                   //Catching the IOException
            throw new RuntimeException(e);
        }


        //Manipulating the compositions.txt file
        try {
            String compositionsLine = readerCompositions.readLine();                                //Reading one line
            while (compositionsLine != null) {                                                      //Checking if the reader reached the end of the file
                compositionsDetails = compositionsLine.split(": ");                           //Splitting the value
                while (Objects.equals(compositionsDetails[0], "Name")) {                         //Checking if the first word is Name
                    compositionsName = compositionsDetails[1];                                      //Assigning the composition name to the variable
                    break;                                                                          //breaking out of the loop
                }
                while (Objects.equals(compositionsDetails[0], "Tempo")) {                        //Checking if the first word is Tempo
                    compositionsTempo = compositionsDetails[1];                                     //Assigning the composition tempo to the variable
                    break;                                                                          //breaking out of the loop
                }
                while (Objects.equals(compositionsDetails[0], "Length")) {                       //Checking if the first word is Tempo
                    compositionsLength = Integer.valueOf(compositionsDetails[1]);                   //Assigning the composition length to the variable
                    break;                                                                          //breaking out of the loop

                }
                MusicSheet musicSheet = new MusicSheet(compositionsName, compositionsTempo, compositionsLength);  //Creating a MusicSheet object
                compositionsCollection.add(musicSheet);                                             //Adding to the compositions collection
                while (!Objects.equals(compositionsDetails[0], "Name") && !Objects.equals(compositionsDetails[0], "Tempo") && !Objects.equals(compositionsDetails[0], "Length")) {  //Checking if the first words are not any of the following
                    compositionsDetails = compositionsLine.split(", ", 3);               //Splitting the value
                    compositionsInstrument = compositionsDetails[0];                                //Assigning the composition instrument to the variable
                    if (Objects.equals(compositionsDetails[1], "soft")) {                        //Checking if variable is soft
                        compositionsLoudness = true;                                                //Assigning true to compositions loudness
                    } else if (Objects.equals(compositionsDetails[1], "loud")) {                 //Checking if variable is loud
                        compositionsLoudness = false;                                               //Assigning false to compositions loudness
                    }
                    String[] notesList = compositionsDetails[2].split(", ");                  //Splitting the value
                    List<String> compositionsList = new ArrayList<>();                              //Creating a List<String>
                    for (String i : notesList) {                                                    //Removing thr brackets
                        if (i.contains("{")){
                            i=i.replace("{","");
                        }else if (i.contains("}")){
                            i = i.replace("}", "");
                        }
                        compositionsList.add(i);                                                    //Adding to the List<String>
                    }
                    musicSheet.addScore(compositionsInstrument, compositionsList, compositionsLoudness);  //Calling the addScore method
                    break;                                                                          //Breaking out of the loop
                }
                compositionsLine = readerCompositions.readLine();                                   //Reading the next line
            }
        } catch (IOException e) {                                                                   //Catching the IOException
            throw new RuntimeException(e);
        }
        EcsBandAid object = new EcsBandAid(soundSystem, musiciansCollection, compositionsCollection);  //Creating an EcsBandAid object
        for (int i = 0; i < years + 1; i++) {                                                       //Looping for the number of years
            object.performForAYear();                                                               //Calling the performForAYear method
        }
    }
}
