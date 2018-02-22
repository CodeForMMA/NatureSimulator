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
    //Maximum number of offspring
    private static int MAX_LITTER_SIZE;
    //Maximum age animal can reach
    private static int MAX_AGE;
    //The chances they can produce offspring
    private static double BREEDING_PROBABILTY;
    //Age the prey can breed
    private static int BREEDING_AGE;
    //The age of the prey
    protected int age;
    //The gender of the animal
    protected boolean gender;
    //Randomizer
    private static final Random rand = Randomizer.getRandom();
    // instance variables - replace the example below with your own
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animals food level
    protected int foodLevel;

    public Predator (Boolean randomAge, Field field, Location location, boolean gender){
        
        super(field, location);
        this.gender = gender;
        
        
    }
    
    public static int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    public static void setMaxLitterSize(int maxLitterSize) {
        MAX_LITTER_SIZE = maxLitterSize;
    }

    public static void setMaxAge(int maxAge) {
        MAX_AGE = maxAge;
    }
    
    public static int getMaxAge(){
        return MAX_AGE;
    }

    public static double getBreedingProbabilty() {
        return BREEDING_PROBABILTY;
    }

    public static void setBreedingProbabilty(double breedingProbabilty) {
        BREEDING_PROBABILTY = breedingProbabilty;
    }

    public static int getBreedingAge() {
        return BREEDING_AGE;
    }

    public static void setBreedingAge(int breedingAge) {
        BREEDING_AGE = breedingAge;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
    
    public void setFoodLevel(int foodLevel){
        this.foodLevel = foodLevel;
    }
    
    public int getFoodLevel(){
        return foodLevel;
    }
    
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILTY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A chicken can breed if it has reached the breeding age.
     * @return true if the chicken can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
}
