package me.meta4245.betterthanmodern.math;

import java.util.concurrent.ThreadLocalRandom;

public class Binomial {
    private final static ThreadLocalRandom RNG = ThreadLocalRandom.current();

    /**
     * @param prob probability as a uniform double
     * @return true or false roll
     */
    public static boolean roll(double prob) {
        return RNG.nextDouble() < prob;
    }

    /**
     * @param prob probability as a percent
     * @return true or false roll
     */
    public static boolean rollPercent(double prob) {
        return roll(prob / 100);
    }

    /**
     * @param trials number of trials
     * @param prob   probability as a uniform double
     * @return how many rolls were true
     */
    public static int roll(int trials, double prob) {
        int r = 0;

        for (int i = 0; i < trials; i++) {
            if (roll(prob)) {
                r++;
            }
        }

        return r;
    }

    /**
     * @param trials number of trials
     * @param prob   probability as a percent
     * @return how many rolls were true
     */
    public static int rollPercent(int trials, double prob) {
        int r = 0;

        for (int i = 0; i < trials; i++) {
            if (rollPercent(prob)) {
                r++;
            }
        }

        return r;
    }

    /**
     * @param min    minimum amount not random
     * @param trials number of trials
     * @param prob   probability as a percent
     * @return how many rolls were true
     */
    public static int rollRange(int min, int trials, double prob) {
        return min + roll(trials, prob);
    }

    /**
     * @param min    minimum amount not random
     * @param trials number of trials
     * @param prob   probability as a percent
     * @return how many rolls were true
     */
    public static int rollRangePercent(int min, int trials, double prob) {
        return min + rollPercent(trials, prob);
    }
}
