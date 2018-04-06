package com.rapsealk.genetic.nqueens;

/**
 * Created by rapsealk on 2018/04/06.
 */
public class Chromosome {

    private String genotype;
    private int collision;

    public Chromosome(int N) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < N; i++) builder.append((int)(Math.random() * 10) % N);
        this.genotype = builder.toString();
        this.collision = Controller.fitness(this);
    }

    public Chromosome(String genotype) {
        this.genotype = genotype;
        this.collision = Controller.fitness(this);
    }

    public String getGenotype() {
        return this.genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public int getCollision() {
        return this.collision;
    }

    /*
    public void setCollision(int collision) {
        this.collision = collision;
    }
    */
}
