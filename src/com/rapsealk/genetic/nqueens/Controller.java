package com.rapsealk.genetic.nqueens;

import java.util.ArrayList;

/**
 * Created by rapsealk on 2018-03-28.
 */
public class Controller {

    public Controller() {

    }

    /*
    public static void fall(ArrayList<Population> pool) {
        int poolSize = pool.size();
        int fallCount = (int) Math.round(poolSize * 0.2);
        pool.subList(0, fallCount-1);
    }
    */

    public static void crossover(Population population, int x, int y) {
        int position = ((int) Math.round(Math.random() * 100) % (GlobalVariable.numberOfQueens - 1)) + 1;
        // System.out.println("crossover position: " + position);
        // System.out.println("original1: " + population.getChromosome(x));
        // System.out.println("original2: " + population.getChromosome(y));
        String x1 = population.getChromosome(x).substring(0, position);
        String x2 = population.getChromosome(x).substring(position);
        String y1 = population.getChromosome(y).substring(0, position);
        String y2 = population.getChromosome(y).substring(position);
        String new1 = x1 + y2;
        String new2 = y1 + x2;
        // System.out.println(String.format("x1: %s, x2: %s", x1, x2));
        // System.out.println(String.format("y1: %s, y2: %s", y1, y2));
        // System.out.println("new1: " + new1);
        // System.out.println("new2: " + new2);
        population.setChromosomes(x, new1);
        population.setChromosomes(y, new2);
        population.setCollision(fitness(population));
    }

    public static int fitness(Population population) {
        int score = 0;
        // calculate collision point
        // System.out.println("=== Calculating collision point ===");
        for (int i = 0; i < GlobalVariable.numberOfQueens; i++) {
            for (int j = 0; j < GlobalVariable.numberOfQueens; j++) {
                boolean isQueen = (population.getChromosome(i).charAt(j) == GlobalVariable.theQueen);
                if (!isQueen) continue;
                // System.out.println("Queen at [" + i + ", " + j + "]");
                // check row
                for (int k = 0; k < GlobalVariable.numberOfQueens; k++) {
                    if (k == j) continue;
                    // System.out.println("Checking Row at [" + i + ", " + k + "] = " + chromosomes[i].charAt(k));
                    if (population.getChromosome(i).charAt(k) == GlobalVariable.theQueen) score += 1;
                }
                // check column
                for (int k = 0; k < GlobalVariable.numberOfQueens; k++) {
                    if (k == i) continue;
                    // System.out.println("Checking Column at [" + k + ", " + j + "] = " + chromosomes[k].charAt(j));
                    if (population.getChromosome(k).charAt(j) == GlobalVariable.theQueen) score += 1;
                }
                // check diagonal(LT)
                for (int m = i-1, n = j-1; m >= 0 && n >= 0; m--, n--) {
                    // System.out.println("Checking Diagonal(LT) at [" + m + ", " + n + "] = " + chromosomes[m].charAt(n));
                    if (population.getChromosome(m).charAt(n) == GlobalVariable.theQueen) score += 1;
                }
                // check diagonal(RT)
                for (int m = i-1, n = j+1; m >= 0 && n < GlobalVariable.numberOfQueens; m--, n++) {
                    // System.out.println("Checking Diagonal(RT) at [" + m + ", " + n + "] = " + chromosomes[m].charAt(n));
                    if (population.getChromosome(m).charAt(n) == GlobalVariable.theQueen) score += 1;
                }
                // check diagonal(LB)
                for (int m = i+1, n = j-1; m < GlobalVariable.numberOfQueens && n >= 0; m++, n--) {
                    // System.out.println("Checking Diagonal(LB) at [" + m + ", " + n + "] = " + chromosomes[m].charAt(n));
                    if (population.getChromosome(m).charAt(n) == GlobalVariable.theQueen) score += 1;
                }
                // check diagonal(RB)
                for (int m = i+1, n = j+1; m < GlobalVariable.numberOfQueens && n < GlobalVariable.numberOfQueens; m++, n++) {
                    // System.out.println("Checking Diagonal(RB) at [" + m + ", " + n + "] = " + chromosomes[m].charAt(n));
                    if (population.getChromosome(m).charAt(n) == GlobalVariable.theQueen) score += 1;
                }
            }
        }
        return score;
    }

    /**
     * @deprecated Invalid/Unnecessary for solving N-Queens problem.
     */
    public Population mutate(Population population) {
        boolean isMutatable = (Math.round(Math.random() * 10000) <= 4);
        if (!isMutatable) return population;
        // int position =
        return population;
    }
}
