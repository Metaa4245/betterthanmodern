package me.meta4245.betterthanmodern.math;

import java.util.concurrent.ThreadLocalRandom;

public record ProbabilityTable<T>(double[] cumulative, T[] values) {
    private final static ThreadLocalRandom RNG = ThreadLocalRandom.current();

    public ProbabilityTable(double[] cumulative, T[] values) {
        this.cumulative = new double[cumulative.length];
        this.values = values;

        double total = 0;
        for (double w : cumulative) {
            total += w;
        }

        double running = 0;

        for (int i = 0; i < cumulative.length; i++) {
            running += cumulative[i] / total;
            this.cumulative[i] = running;
        }
    }

    public T roll() {
        double r = RNG.nextDouble();

        for (int i = 0; i < cumulative.length; i++) {
            if (r < cumulative[i]) {
                return values[i];
            }
        }

        return null;
    }
}
