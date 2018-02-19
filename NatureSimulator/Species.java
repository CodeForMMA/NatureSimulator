import java.util.List;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Species
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The Species's field.
    private Field field;
    // The Species's position in the field.
    private Location location;
    //Age Field to store the age of the animal
    protected int age ;
 
    /**
     * Create a new Species at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Species(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this Species act - that is: make it do
     * whatever it wants/needs to do.
     * @param newSpecies A list to receive newly born Species.
     *                   Change this so different speices act differently
     */
    abstract public void act(List<Species> newSpecies);

    /**
     * Check whether the Species is alive or not.
     * @return true if the Species is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Increments the Age animals, may result in death if max age is reached.
     */
    protected void incrementAge(int maxAge)
    {
        age++;
        if(age > maxAge) {
            setDead();
        }
    }
   
    /**
     * Indicate that the Species is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Return the Species's location.
     * @return The Species's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the Species at the new location in the given field.
     * @param newLocation The Species's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the Species's field.
     * @return The Species's field.
     */
    protected Field getField()
    {
        return field;
    }
    //Abstact method, for the breed method
    abstract protected int breed();
    //Abstract Method, for the giveBirthMethod
    abstract protected void giveBirth(List<Species> newSpecies);
    //Abstract Method for the CanBreedMethod
    abstract protected boolean canBreed();
}
