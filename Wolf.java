import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * Write a description of class Wolf here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Wolf extends Predator
{
    // instance variables - replace the example below with your own
    // Characteristics shared by all wolves (class variables).
    
    // The food value of a single rabbit. In effect, this is the
    // number of steps a wolf can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    private static final int CHICKEN_FOOD_VALUE = 10;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    //Checks if they can mate
    private boolean canWeMate;
    
    // Individual characteristics (instance fields).

    /**
     * Create a wolf. A wolf can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the wolf will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wolf(boolean randomAge, Field field, Location location, boolean gender)
    {
        super(randomAge,field, location, gender);
        setBreedingProbabilty(0.08);
        setMaxAge(150);
        setMaxLitterSize(2);
        setBreedingAge(15);
        setFoodLevel(15);
        if(randomAge) {
            age = rand.nextInt(getMaxAge());
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the wolf does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newWolves A list to return newly born wolves.
     */
    public void act(List<Species> newWolves, boolean isDay)
    {
        incrementAge(getMaxAge());
        incrementHunger();
        if(isDay){
                if(isAlive()) {
                    giveBirth(newWolves);            
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
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        
            else if (animal instanceof Chicken) {
                Chicken chicken = (Chicken) animal;
                if(chicken.isAlive()){
                    chicken.setDead();
                    foodLevel = CHICKEN_FOOD_VALUE;
                    return where;   
                }
            }
        } 
        return null;
    }
    
    /**
     * Check whether or not this Wolf is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newWolfes A list to return newly born Wolfes.
     */
    protected void giveBirth(List<Species> newWolf)
    {
        // New Wolfes are born into adjacent locations.
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
                    for(int b = 0; b < births && free.size() > 0; b++) {
                        if(free.size() == 0){
                            break;
                        }
                        Location loc = free.remove(0);
                        Wolf young = new Wolf(false, field, loc, genderGen());
                        newWolf.add(young);
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
        if ((animalNextToMe != null) && (animalNextToMe instanceof Wolf)){
            Wolf partner = (Wolf) animalNextToMe;
            if(this.isGender() != partner.isGender()){
                canWeMate = true;
            } else {
                canWeMate = false;
            }
        }
        return canWeMate;
    }
        
   
}