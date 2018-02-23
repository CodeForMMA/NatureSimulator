import java.util.*;
/**
 * Mushrooms die and breed.
 *
 * @author Mansour Jalaly K1763921
 * @version 23.02.2018
 */
public class Mushroom extends Plant
{
    // instance variables - replace the example below with your own
    
    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Mushroom
     */
    public Mushroom(boolean randomAge,Field field,Location location)
    {
        super(randomAge, field, location);
        setMaxAge(15);
        setMaxSporeSize(6);
        setBreedingProbabilty(10);
        setBreedingAge(5);
        if(randomAge){
            age = rand.nextInt(getMaxAge());
        }else{
            age = 0;
        }
    }

    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Species> newMushrooms, boolean isDay, String weather)
    {
        incrementAge(getMaxAge());
        if(isAlive()) {
            System.out.println("Mushroom givenBirth");
            giveBirth(newMushrooms);
        }else {
            setDead();
            System.out.println("Mushroom has died");
        }
    }

    /**
     * Check whether or not this mushroom is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newMushrooms A list to return newly born Mushrooms.
     */
    protected void giveBirth(List<Species> newMushrooms)
    {
        // New mushrooms are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        Location newLocation = getField().freeAdjacentLocation(getLocation());
        if (newLocation != null){
            int births = breed();
            for(int b = 0; b < births && free.size() > 0; b++) {
                Location loc = free.remove(0);
                Mushroom young = new Mushroom(false, field, loc);
                newMushrooms.add(young);
                setLocation(newLocation);
            }
        }
    }
    
}
