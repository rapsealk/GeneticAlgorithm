package com.rapsealk.genetic.nqueens;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rapsealk on 2018-03-28.
 */
public class Controller {

    public Controller() {

    }

    /**
     * Crossover operation between two chromosomes.
     * @param one is Chromosome A.
     * @param other is Chromosome B.
     * @return List of crossovered chromosomes.
     */
    public static List<Chromosome> crossover(Chromosome one, Chromosome other) {
        String genoX = one.getGenotype();
        String genoY = other.getGenotype();
        int position = (int)(Math.random() * 10) % GlobalVariable.NUMBER_OF_QUEENS;
        //one.setGenotype(genoX.substring(0, position) + genoY.substring(position));
        //other.setGenotype(genoY.substring(0, position) + genoX.substring(position));
        //one.setCollision(fitness(one));
        //other.setCollision(fitness(other));
        List<Chromosome> newChromosomes = new ArrayList<>();
        newChromosomes.add(new Chromosome(genoX.substring(0, position) + genoY.substring(position)));
        newChromosomes.add(new Chromosome(genoY.substring(0, position) + genoX.substring(position)));
        return newChromosomes;
    }

    /**
     * Make mutation (by the ratio 0.04%)
     * @param chromosome is a single chromosome to apply mutation.
     */
    public static void mutate(Chromosome chromosome) {
        boolean isNaturalMutation = (Math.random() >= (1 - GlobalVariable.RATE_MUTATION));
        if (!isNaturalMutation) return;
        int position = (int)(Math.random() * 10) % GlobalVariable.NUMBER_OF_QUEENS;
        int newGene = (int)(Math.random() * 10) % GlobalVariable.NUMBER_OF_QUEENS;
        String newGenotype = chromosome.getGenotype().substring(0, position) + newGene + chromosome.getGenotype().substring(position+1);
        chromosome.setGenotype(newGenotype);
    }

    /**
     * Calculate collision point (the lower the better)
     * @param chromosome is a single chromosome.
     * @return half penalty.
     */
    public static int fitness(Chromosome chromosome) {
        int penalty = 0;
        for (int i = 0; i < GlobalVariable.NUMBER_OF_QUEENS; i++) {
            int gene = chromosome.getGenotype().charAt(i);
            for (int j = 0; j < GlobalVariable.NUMBER_OF_QUEENS; j++) {
                if (i == j) continue;
                if (gene == chromosome.getGenotype().charAt(j)) penalty += 1;
            }
        }
        return (penalty / 2);
    }

    public static void order(ArrayList<Chromosome> pool) {
        pool.sort((o1, o2) -> o1.getCollision() - o2.getCollision());
    }

    public static void log(ArrayList<Chromosome> pool) {
        for (int i = 0; i < GlobalVariable.NUMBER_OF_POPULATIONS; i++) {
            System.out.printf("Chromosome[%d]: %s, fitness: %d\n", i+1, pool.get(i).getGenotype(), -1 * pool.get(i).getCollision());
        }
    }

    public static void log(ArrayList<Chromosome> pool, int maxLines) {
        for (int i = 0; i < GlobalVariable.NUMBER_OF_POPULATIONS; i++) {
            System.out.printf("Chromosome[%d]: %s, fitness: %d\n", i+1, pool.get(i).getGenotype(), -1 * pool.get(i).getCollision());
            if (i == maxLines) break;
        }
    }

    public static void draw(Chromosome chromosome) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < GlobalVariable.NUMBER_OF_QUEENS; i++) builder.append("□");
        for (int i = 0; i < chromosome.getGenotype().length(); i++) {
            int position = chromosome.getGenotype().charAt(i) - '0';
            builder.replace(position, position+1, "■");
            System.out.println(builder.toString());
            builder.replace(position, position+1, "□");
        }
    }
}
