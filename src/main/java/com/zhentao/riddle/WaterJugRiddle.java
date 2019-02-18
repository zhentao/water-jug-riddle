package com.zhentao.riddle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WaterJugRiddle {
    /**
     *
     * @param firstJug  the capacity for first jug
     * @param secondJug the capacity for second jug
     * @param target
     * @return the list of operations to get the target amount of water
     */
    public Queue<State> solve(final int firstJug, final int secondJug, final int target) {
        if (firstJug + secondJug < target) {
            return new ArrayDeque<>();
        }

        final Queue<ArrayDeque<State>> queue = new ArrayDeque<>();
        final Set<State> visited = new HashSet<>();

        // hold the path
        final ArrayDeque<State> start = new ArrayDeque<>();
        final State initState = new State(0, 0);
        start.add(initState);
        queue.add(start);
        visited.add(initState);

        while (!queue.isEmpty()) {
            final ArrayDeque<State> currentPath = queue.poll();
            final State currentState = currentPath.getLast();
            if (currentState.check(target)) {
                return currentPath;
            }

            for (final State state : currentState.newPossibleStates(firstJug, secondJug)) {
                if (!visited.contains(state)) {
                    visited.add(state);
                    final ArrayDeque<State> newPath = new ArrayDeque<>(currentPath);
                    newPath.add(state);
                    queue.add(newPath);
                }
            }
        }

        return new ArrayDeque<>();
    }

    /**
     * The state class to represent the water in each jug after an allowed operation
     *
     * @author zhentao
     *
     */
    static class State {
        int firstJug;
        int secondJug;

        /**
         *
         * @param first  the water in first jug
         * @param second the water in second jug
         */
        public State(final int first, final int second) {
            this.firstJug = first;
            this.secondJug = second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstJug, secondJug);
        }

        @Override
        public boolean equals(final Object other) {
            final State that = (State) other;
            return firstJug == that.firstJug && secondJug == that.secondJug;
        }

        @Override
        public String toString() {
            return "[" + firstJug + ", " + secondJug + "]";
        }

        /**
         * check the water volume meets the target
         *
         * @param target
         * @return true if the water in either or both buckets equals the target.
         */
        boolean check(final int target) {
            return firstJug == target || secondJug == target || firstJug + secondJug == target;
        }

        /**
         * Create a new state with first bucket full, and second bucket remains same as
         * this state
         *
         * @param water
         * @return
         */
        State fillFirstBucket(final int water) {
            return new State(water, secondJug);
        }

        State emptyFirstBucket() {
            return new State(0, secondJug);
        }

        State fillSecondBucket(final int water) {
            return new State(firstJug, water);
        }

        State emptySecondBucket() {
            return new State(firstJug, 0);
        }

        /**
         *
         * @param capacity the max amount of water the first jug can hold
         * @return a new State after the transfer
         */
        State transferFromSecondToFirst(final int capacity) {
            // max amount of water can be transferred from second to first jug
            final int max = Math.min(capacity - firstJug, secondJug);
            return new State(firstJug + max, secondJug - max);
        }

        /**
         *
         * @param capacity the max amount of water the second jug can hold
         * @return a new state after the transfer.
         */
        State transferFromFirstToSecond(final int capacity) {
            final int max = Math.min(firstJug, capacity - secondJug);
            return new State(firstJug - max, secondJug + max);
        }

        public List<State> newPossibleStates(final int capacityOfFirstJug, final int capacityOfSecondJug) {
            final List<State> states = new ArrayList<>();
            states.add(fillFirstBucket(capacityOfFirstJug));
            states.add(emptyFirstBucket());
            states.add(fillSecondBucket(capacityOfSecondJug));
            states.add(emptySecondBucket());
            states.add(transferFromSecondToFirst(capacityOfFirstJug));
            states.add(transferFromFirstToSecond(capacityOfSecondJug));

            return states;
        }
    }

    public static void main(final String[] args) throws InterruptedException {
        final WaterJugRiddle riddle = new WaterJugRiddle();
        try (final Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.println("Please enter the capacity for the first jug (enter 0 to terminate):");
                    final int first = scanner.nextInt();
                    if (first == 0) {
                        System.out.println("terminate the program");
                        break;
                    }
                    System.out.println("Please enter the capacity for the second jug (enter 0 to terminate):");
                    final int second = scanner.nextInt();
                    if (second == 0) {
                        System.out.println("terminate the program");
                        break;
                    }

                    System.out.println("Please enter the target water volume (enter 0 to terminate):");
                    final int target = scanner.nextInt();
                    if (target == 0) {
                        System.out.println("terminate the program");
                        break;
                    }

                    final Queue<State> operations = riddle.solve(first, second, target);
                    if (operations.isEmpty()) {
                        System.out.println("No Solution");
                    } else {
                        System.out.println("total steps: " + operations.size());
                        System.out.println(operations);
                    }
                    System.out.println();
                } catch (final Exception e ) {
                    e.printStackTrace();
                    TimeUnit.MILLISECONDS.sleep(20);
                    scanner.next();
                }
            }
        }
    }
}
