import java.util.List;
/**
 * Abstract class Predator - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Predator extends Species
{
    // instance variables - replace the example below with your own
    

    public Predator (Boolean randomAge, Field field, Location location){
        
        super(field, location);
        
        
    }
    abstract protected int breed();
    abstract protected void giveBirth(List<Species> newSpecies);
}
