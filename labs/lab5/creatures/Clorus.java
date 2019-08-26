package creatures;

import edu.princeton.cs.algs4.StdRandom;
import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    private int r = 34;
    private int g = 0;
    private int b = 231;

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
        if (energy <= 0) {
            energy = 0;
        }
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        Clorus copy = new Clorus(energy / 2);
        energy = energy / 2;
        return copy;
    }

    @Override
    public void stay() {
        energy -= 0.01;
        if (energy <= 0) {
            energy = 0;
        }
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {

            if (entry.getValue().name().equals("empty")) {
                emptyNeighbors.add(entry.getKey());
            }

            if (entry.getValue().name().equals("plip")) {
                plipNeighbors.add(entry.getKey());
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        if (!plipNeighbors.isEmpty()) {
            int index = StdRandom.uniform(0, plipNeighbors.size());
            Direction dir = (Direction) plipNeighbors.toArray()[index];
            return new Action(Action.ActionType.ATTACK, dir);
        } else {
            int index = StdRandom.uniform(0, emptyNeighbors.size());
            Direction dir = (Direction) emptyNeighbors.toArray()[index];
            if (energy >= 1) {
                return new Action(Action.ActionType.REPLICATE, dir);
            }
            return new Action(Action.ActionType.MOVE, dir);
        }
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }
}
