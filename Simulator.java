import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;   
    // The probability that a chicken will be created in any given grid position.
    private static final double CHICKEN_CREATION_PROBABILITY = 0.08; 
    // The probability that a Wolf will be created in any given grid position.
    private static final double WOLF_CREATION_PROBABILITY = 0.02; 
    // The probability that a Wolf will be created in any given grid position.
    private static final double MUSHROOM_CREATION_PROBABILITY = 0.3; 
     
    // List of animals in the field.
    private List<Species> species;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    //  Species
    private Species spec;
    //day or night
    private boolean isItDay;
    //The weather of the simulation
    private String weather;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
        weather = "Sunny";
        isItDay = true;

    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        species = new ArrayList<>();
        field = new Field(depth, width);
        
        
        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Rabbit.class, Color.ORANGE);
        view.setColor(Fox.class, Color.BLUE);
        view.setColor(Chicken.class, Color.GREEN);
        view.setColor(Wolf.class, Color.PINK);
        view.setColor(Mushroom.class, Color.MAGENTA);
        // Setup a valid starting point.
        reset();
    }
    
    /** 
     * 
     * returns a boolean value for it being time and day 
     */
    private void isDay(){
       if(step % 24 == 0){
           isItDay = false;
        } else if (step % 12 == 0) {
            isItDay = true;
        }
    }
    
    /**
     * Every 24 steps get a new weather
     * @return String weather
     */
    private void changeWeather(){
        Weather weatherClass = new Weather();
        if (step % 6 == 0 ){
            weather = weatherClass.getWeather();
        }
    }
            
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(150);   // uncomment this to run more slowly
        }
    }
   
     
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        changeWeather();
        isDay();
        // Provide space for newborn animals.
        List<Species> newSpecies = new ArrayList<>();        
        // Let all rabbits act.
        for(Iterator<Species> it = species.iterator(); it.hasNext(); ) {
            Species species = it.next();
            species.act(newSpecies, isItDay, weather);
            if(!species.isAlive()) {
                species.setDead();
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        species.addAll(newSpecies);

        view.showStatus(step, field, isItDay, weather);
    }
    
    /**
     * Gets the time of day
     * @return true for day false for night
     */
    public boolean getTimeOfDay()
    {
        return isItDay;
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        species.clear();
        populate();
        weather = "Sunny";
        changeWeather();
        
        // Show the starting state in the view.
        view.showStatus(step, field, isItDay, weather);
    }
    
    /**
     * Returns the current step number in the simulator
     */
    public int getStepNumber(){
        return step;
    }
   
    /**
     * Randomly populate the field with foxes, rabbits, chickens and wolves.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location, rand.nextBoolean());
                    species.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location, rand.nextBoolean());
                    species.add(rabbit);
                }
                else if(rand.nextDouble() <= CHICKEN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Chicken chicken = new Chicken(true, field, location, rand.nextBoolean());
                    species.add(chicken);
                }
                else if(rand.nextDouble() <= WOLF_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Wolf wolf = new Wolf(true, field, location, rand.nextBoolean());
                    species.add(wolf);
                }
                else if(rand.nextDouble() <= MUSHROOM_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Mushroom mushroom = new Mushroom(true, field, location);
                    species.add(mushroom);
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}

