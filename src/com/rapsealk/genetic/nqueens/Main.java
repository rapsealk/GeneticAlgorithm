package com.rapsealk.genetic.nqueens;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rapsealk on 2018-03-28.
 */
public class Main {

    public static void main(String[] args) {

        // init
        ArrayList<Chromosome> pool = new ArrayList<>();
        for (int i = 0; i < GlobalVariable.NUMBER_OF_POPULATIONS; i++) pool.add(new Chromosome(GlobalVariable.NUMBER_OF_QUEENS));

        // log the first generation
        Controller.log(pool);

        int epoch = GlobalVariable.TOTAL_EPOCH;

        while (epoch-- > 0) {
            Controller.order(pool);

            // escape condition
            if (pool.get(0).getCollision() == 0) break;

            // drop the worst
            int aliveCount = (int) (GlobalVariable.NUMBER_OF_POPULATIONS * (1 - GlobalVariable.RATE_FALL));
            pool = new ArrayList<>(pool.subList(0, aliveCount));
            // crossover
            // System.out.printf("pool.size(): %d / aliveIndex: %d\n", pool.size(), aliveCount);
            for (int i = 0; i < aliveCount; i++) {
                List<Chromosome> children = Controller.crossover(pool.get(i), pool.get((i+1) % aliveCount));
                pool.addAll(children);
            }
            // mutate
            for (int i = 0; i < GlobalVariable.NUMBER_OF_POPULATIONS; i++) Controller.mutate(pool.get(i));
            // log the next generation
            System.out.printf("Generation %d / %d, best fitness: %d\n", (GlobalVariable.TOTAL_EPOCH - epoch + 1), GlobalVariable.TOTAL_EPOCH, pool.get(0).getCollision() * -1);
            // Controller.log(pool);
        }

        Controller.order(pool);
        System.out.println("Genetic Algorithm Process finished.");
        Controller.log(pool, 20);
        System.out.printf("Epoch: %d\n", (GlobalVariable.TOTAL_EPOCH - epoch));
        Controller.draw(pool.get(0));
        Controller.fitness(pool.get(0));
        System.out.printf("Total Mutation Count: %d (ratio: %f)\n", Controller.mutationCount, ((double)Controller.mutationCount / (GlobalVariable.TOTAL_EPOCH - epoch)));
    }
}