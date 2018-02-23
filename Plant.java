import java.util.*;
/**
 * Abstract class Plants - Contains all the characteristics of plants
 *
 * @author Mansour Jalaly K1763921
 * @version 23.02.2018
 */
public abstract class Plant extends Species
{
    // instance variables - replace the example below with your own
    private static int MAX_SPORE_SIZE;
    private static int MAX_AGE;
    private static int BREEDING_AGE;
    private static double BREEDING_PROBABILTY;
    protected int age;
    private static final Random rand = Randomizer.getRandom();
    

    public Plant (Boolean randomAge, Field field, Location location){
        super(field, location);
    }
    
    public static int getMaxSporeSize() {
        return MAX_SPORE_SIZE;
    }

    public static void setMaxSporeSize(int maxSporeSize) {
        MAX_SPORE_SIZE = maxSporeSize;
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
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILTY) {
            births = rand.nextInt(MAX_SPORE_SIZE) + 1;
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
    
}
