import java.util.List;
/**
 * Abstract class Predator - Has all the character class 
 *
 * @author Mansour Jalaly & Mohamad Haider Alwaie 
 * @version 16/02/2018
 */
public abstract class Predator extends Species
{
    
    

    public Predator (Boolean randomAge, Field field, Location location){
        super(field, location);
    }
    
    abstract protected int breed();
    abstract protected void giveBirth(List<Species> newSpecies);
}
