package com.zhentao.riddle;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.zhentao.riddle.WaterJugRiddle.State;

public class WaterJugRiddleTest {
    private WaterJugRiddle riddle;

    @BeforeEach
    public void setup() {
        riddle = new WaterJugRiddle();
    }

    @Test
    public void test() {
        final Queue<State> operations = riddle.solve(3, 5, 4);
        assertThat(operations.size(), is(7));
        assertThat(new ArrayList<>(operations), is(List.of(new State(0, 0), new State(0, 5), new State(3, 2),
                new State(0, 2), new State(2, 0), new State(2, 5), new State(3, 4))));
    }

    @Test
    @DisplayName("The target is more than 2 jugs can hold")
    public void testWithTargetTooBig() {
        final Queue<State> operations = riddle.solve(3, 5, 9);
        assertThat(operations.size(), is(0));
    }

    @Test
    @DisplayName("No possible solutions")
    public void testNoSolutions() {
        final Queue<State> operations = riddle.solve(8, 2, 5);
        assertThat(operations.size(), is(0));
    }
}
