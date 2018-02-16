import java.util.*;

/**
 * Abstract class Predator - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Predator extends Species 
{
    // instance variables - replace the example below with your own
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;

    public Predator (Boolean randomAge, Field field, Location location){
        
        super(field, location);
        
        
    }
    abstract protected int breed();
    abstract protected boolean canBreed();
    abstract protected void giveBirth(List<Species> newSpecies);
}
