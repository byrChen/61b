package creatures;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals("clorus", c.name());
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.stay();
        assertEquals(1.96, c.energy(), 0.01);
        c.attack(new Plip(1));
        assertEquals(2.96, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        Clorus copy = c.replicate();
        assertEquals(1, c.energy(), 0.01);
        assertEquals(1, copy.energy(), 0.01);
        assertNotEquals(c, copy);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(2);
        HashMap<Direction, Occupant> surounded = new HashMap<>();
        surounded.put(Direction.TOP, new Impassible());
        surounded.put(Direction.BOTTOM, new Impassible());
        surounded.put(Direction.LEFT, new Impassible());
        surounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surounded);
        Action expect = new Action(Action.ActionType.STAY);

        assertEquals(expect, actual);

        // No empty and all Plips; stay
        c = new Clorus(2);
        HashMap<Direction, Occupant> allPlip = new HashMap<>();
        surounded.put(Direction.TOP, new Plip(1.5));
        surounded.put(Direction.BOTTOM, new Plip(1.5));
        surounded.put(Direction.LEFT, new Plip(1.5));
        surounded.put(Direction.RIGHT, new Plip(1.5));

        actual = c.chooseAction(allPlip);
        expect = new Action(Action.ActionType.STAY);

        assertEquals(expect, actual);

        // 3 empty and 1 Plip at TOP; attack towards TOP
        c = new Clorus(2);
        HashMap<Direction, Occupant> topPlip = new HashMap<>();
        topPlip.put(Direction.TOP, new Plip(1.5));
        topPlip.put(Direction.BOTTOM, new Empty());
        topPlip.put(Direction.LEFT, new Empty());
        topPlip.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(topPlip);
        expect = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expect, actual);

        // 1 empty and 3 impassible, energy >= 1; replicate towards TOP
        c = new Clorus(2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topEmpty);
        expect = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expect, actual);

        // 1 empty and 3 impassible, energy <= 1; move towards top
        c = new Clorus(0.5);

        actual = c.chooseAction(topEmpty);
        expect = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expect, actual);
    }
}
