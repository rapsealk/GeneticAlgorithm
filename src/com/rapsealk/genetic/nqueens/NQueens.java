package com.rapsealk.genetic.nqueens;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rapsealk on 2018-03-28.
 */
public class NQueens {

    private static int epoch = 100000;

    public static void main(String[] args) {

        // init
        ArrayList<Population> queens = new ArrayList<>();
        for (int i = 0; i < GlobalVariable.numberOfPopulations; i++) queens.add(new Population(GlobalVariable.numberOfQueens));

        // print initial pool
        System.out.println("=== Before process ===");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < GlobalVariable.numberOfPopulations; i++) builder.append(queens.get(i).getCollision() + " ");
        System.out.println("collisions: " + builder.toString());

        while (--epoch > 0) {
            // Circular cross-over
            for (int i = 0; i < GlobalVariable.numberOfQueens; i++) Controller.crossover(queens.get(i), i, (i + 1) % GlobalVariable.numberOfQueens);

            // finish the process if the best case has been made.
            sort(queens);
            if (queens.get(0).getCollision() == 0) break;

            // Selection - there might be a better way..
            int poolSize = queens.size();
            int fallCount = (int) Math.round(poolSize * GlobalVariable.fallRate);
            ArrayList<Population> nextPool = new ArrayList<>();
            nextPool.addAll(queens);
            queens.clear();
            for (int i = 0; i < poolSize-fallCount; i++) queens.add(nextPool.get(i));
            for (int i = poolSize-fallCount; i < poolSize; i++) queens.add(new Population(GlobalVariable.numberOfQueens));
        }
        System.out.println("Genetic Algorithm finished.");
        System.out.println("epoch: " + (100000 - epoch));

        sort(queens);
        System.out.println("=== best queens ===");
        queens.get(0).print();
        System.out.println("best collision: " + queens.get(0).getCollision());

        // print processed pool
        System.out.println("=== After process ===");
        builder = new StringBuilder();
        for (int i = 0; i < GlobalVariable.numberOfPopulations; i++) builder.append(queens.get(i).getCollision() + " ");
        System.out.println("collisions: " + builder.toString());
    }

    public static void sort(ArrayList<Population> populations) {
        Collections.sort(populations, (o1, o2) -> {
            if (o1.getCollision() > o2.getCollision()) return 1;
            if (o1.getCollision() < o2.getCollision()) return -1;
            return 0;
        });
    }
}