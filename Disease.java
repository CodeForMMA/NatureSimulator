import java.util.*;
/**
 * Write a description of class Disease here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Disease
{
    // instance variables - replace the example below with your own
    private boolean isDiseased;
    
    private static final Random rand = Randomizer.getRandom();
    //The probability to kill an animal
    private static double killProb;
    //The probability that the animal can overcome the diesease
    private static double cureProb;
    //The probabilty that can infect other animals
    private static double infectProb;
    //The probability that can create disease
    private static double createProb;
    /**
     * Constructor for objects of class Disease
     */
    public Disease()
    {
       
    }

    private void infect()
    {
        isDiseased = true;
    }
    
    private void cure()
    {
        isDiseased = false;
    }
    
    public void act(Species species){
        if(species.disease.isInfected() && species.isAlive()){
            if(rand.nextDouble() <= killProb){
                species.setDead();
                return;
            }
            else if (rand.nextDouble() <= cureProb){
            species.disease.cure();
            }
            else{
            Field field = species.getField();
            List<Location> adjecent = field.adjacentLocations(species.getLocation());
            Iterator<Location> it = adjacent.iterator();
            while(it.hasNext()){
                Location where = it.next();
                Object organsim = field.getObjectAt(where);
                if(organsim instanceof Species && rand.nextDouble() <= infectProb){
                    Species x = (Species) organsim;
                    x.disease.infect();
                }
            }
        }
        }
        else{
            if(rand.nextDouble() <= createProb){
                animal.disease.infect();
            }
        }
    }
}