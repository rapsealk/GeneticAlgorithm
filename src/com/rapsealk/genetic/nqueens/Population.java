package com.rapsealk.genetic.nqueens;

/**
 * Created by rapsealk on 2018-03-28.
 */
class Population {

    private String[] chromosomes;
    private int collision = 0;

    Population(int queenCount) {
        // set queens
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < queenCount; i++) builder.append(GlobalVariable.theCell);
        chromosomes = new String[queenCount];
        for (int i = 0 ; i < queenCount; i++) {
            int position = ((int) (Math.random() * 10)) % queenCount;
            builder.setCharAt(position, GlobalVariable.theQueen);
            this.chromosomes[i] = builder.toString();
            builder.setCharAt(position, GlobalVariable.theCell);
        }
        this.collision = Controller.fitness(this) / 2;
    }

    public String getChromosome(int position) {
        return this.chromosomes[position];
    }

    public void setChromosomes(int position, String chromosome) {
        this.chromosomes[position] = chromosome;
    }

    public int getCollision() {
        return this.collision;
    }

    public void setCollision(int collision) {
        this.collision = collision;
    }

    public void print() {
        System.out.println("=== Chromosome ===");
        for (String chromosome: this.chromosomes) {
            System.out.println(chromosome);
        }
        System.out.println("collision: " + this.collision);
    }
}
