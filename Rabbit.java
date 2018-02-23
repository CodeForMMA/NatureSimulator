import java.util.*;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Rabbit extends Prey implements DiseaseProne
{
    // Characteristics shared by all rabbits (class variables).
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    //Checks if they can mate
    private boolean canWeMate;
    
    // Individual characteristics (instance fields).
    private static final int MUSHROOM_FOOD_VALUE = 5;
    
    private static final double DISEASE_PROBABILITY = 0.3;
    private static final double RECOVER_PROBABILITY = 0.4;
    private static final double DEATH_PROBABILITY = 0.6;
    
    private boolean hasDisease = false;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location, boolean gender)
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
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Species> newRabbits, boolean isDay, String weather)
    {
        incrementAge(getMaxAge());
        incrementHunger();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null){
                //No food here
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
    
    /**
     * Check whether or not this Rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbites A list to return newly born Rabbites.
     */
    protected void giveBirth(List<Species> newRabbits)
    {
        // New Rabbites are born into adjacent locations.
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
                        Rabbit young = new Rabbit(false, field, loc, genderGen());
                        newRabbits.add(young);
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
        if ((animalNextToMe != null) && (animalNextToMe instanceof Rabbit)){
            Rabbit partner = (Rabbit) animalNextToMe;
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
                    getDisease();
                    mushroom.setDead();
                    foodLevel = MUSHROOM_FOOD_VALUE;
                }
            }
            return where;
        } 
        return null;
    }
    
    /**
     * Checks if the animal has a disease
     */
   private void amIInfected (boolean hasDisease){
       if(hasDisease) {
           boolean death = die();
           boolean heal = heal();
           if(death) {
               setDead();
               System.out.println("Has died from disease");
            } else if(heal) {
                hasDisease = false;
                System.out.println("Has healed from disease");
            }
        }
    }
    
    public void getDisease() {
        hasDisease = (rand.nextDouble() <= DISEASE_PROBABILITY ? true : false);
        System.out.println("Has disease: " + hasDisease);
    }
    public boolean hasDisease() {
        return hasDisease;
    }
    public boolean heal() {
         return (rand.nextDouble() <= RECOVER_PROBABILITY ? true : false);
    }
    public boolean die() {
        return (rand.nextDouble() <= DEATH_PROBABILITY ? true : false);
    }
}
