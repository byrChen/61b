package creatures;

import edu.princeton.cs.algs4.StdRandom;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        energy = e;
        r = 99;
        g = (int) (63 + 96 * energy);
        b = 76;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        return color(r, g, b);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        // TODO
        energy -= 0.15;
        if (energy <= 0) {
            energy = 0;
        }
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        // TODO
        energy += 0.2;
        if (energy >= 2) {
            energy = 2;
        }
    }

    /**
     * Plips and their copy each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        Plip copy = new Plip(energy / 2);
        energy = energy / 2;
        return copy;
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        boolean anyClorus = false;
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        // for () {...}
        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            if (entry.getValue().name().equals("empty")) {
                emptyNeighbors.add(entry.getKey());
            }
            if (entry.getValue().name().equals("clorus")) {
                anyClorus = true;
            }
        }

        if (emptyNeighbors.isEmpty()) {
            // TODO
            return new Action(Action.ActionType.STAY);
        }

        if (energy >= 1) {
            int index = StdRandom.uniform(0, emptyNeighbors.size());
            Direction dir = (Direction) emptyNeighbors.toArray()[index];
            return new Action(Action.ActionType.REPLICATE, dir);
        }

        if (anyClorus && StdRandom.uniform(2) == 0) {
            int index = StdRandom.uniform(0, emptyNeighbors.size());
            Direction dir = (Direction) emptyNeighbors.toArray()[index];
            return new Action(Action.ActionType.MOVE, dir);
        }

        return new Action(Action.ActionType.STAY);
    }
}
