package people;

public class Person {                                                                               //Creating a Person class
    private String name;                                                                            //Creating a variable which stores the name

    public Person(String name) {                                                                                        //Creating a constructor
        this.name = name;
    }

    public String getName() {                                                                                           //Creating a getName() method which returns the name
        return name;
    }
}
