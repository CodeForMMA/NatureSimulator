import java.util.*;
/**
 * Chickens move, breed, die, and eat
 * 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Chicken extends Prey
{
    // instance variables - replace the example below with your own
    
    // Characteristics shared by all chickens (class variables).
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    //Checks if they can mate
    private boolean canWeMate;
    
    private static final int MUSHROOM_FOOD_VALUE = 5;
    
    // Individual characteristics (instance fields).

    /**
     * Create a new chicken. A chicken may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the chicken will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Chicken(boolean randomAge, Field field, Location location, boolean gender)
    {
        super(randomAge, field, location, gender);
        setBreedingProbabilty(0.24);
        setMaxAge(40);
        setMaxLitterSize(8);
        setBreedingAge(10);
        setFoodLevel(5);
        if(randomAge) {
            age = rand.nextInt(getMaxAge());
        }
        else {
            age = 0;
        }
    }
    
    /**
     * This is what the chicken does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newChickens A list to return newly born chickens.
     */
    public void act(List<Species> newChickens, boolean isDay, String weather)
    {
        {
        incrementAge(getMaxAge());
        incrementHunger();
        if(isAlive()) {
            giveBirth(newChickens);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    }

    /**
     * Check whether or not this chicken is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newChickens A list to return newly born Chickens.
     */
    protected void giveBirth(List<Species> newChickens)
    {
        // New Chickens are born into adjacent locations.
        // Get a list of adjacent free locations.
        
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        List<Location> whatIsNext = field.adjacentLocations(getLocation());
        Iterator<Location> it = whatIsNext.iterator();
        while(it.hasNext()){
            Location nextToMe = it.next();
            if (nextToMe != null){
                if(canIMate(nextToMe)){
                    int births = breed();
                    //System.out.println("Chickens have bred");
                    for(int b = 0; b < births && free.size() > 0; b++) {
                        if(free.size() == 0){
                            break;
                        }
                        Location loc = free.remove(0);
                        Chicken young = new Chicken(false, field, loc, genderGen());
                        newChickens.add(young);
                    }
                }
            }
        }
    }
    
    /**
     * Checks the surrounding animal if they of the same sex
     * @return Boolean
     */
    protected boolean canIMate(Location nextToMe)
    {
        Field field = getField();
        Object animalNextToMe = field.getObjectAt(nextToMe);
        if ((animalNextToMe != null) && (animalNextToMe instanceof Chicken)){
            Chicken partner = (Chicken) animalNextToMe;
            if(this.isGender() != partner.isGender()){
                canWeMate = true;
            } else {
                canWeMate = false;
            }
        }
        return canWeMate;
    }
    
    /**
     * Look for mushrooms adjacent to the current location.
     * Only the first live mushroom is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object plant = field.getObjectAt(where);
            if(plant instanceof Mushroom) {
                Mushroom mushroom = (Mushroom) plant;
                if(mushroom.isAlive()) { 
                    mushroom.setDead();
                    System.out.println("Chicken has eaten Mushroom");
                    foodLevel = MUSHROOM_FOOD_VALUE;
                    return where;
                }
            }
        } 
        return null;
    }
}