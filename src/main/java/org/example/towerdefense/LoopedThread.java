package org.example.towerdefense;

import javafx.application.Platform;

public class LoopedThread extends Thread {
    private final Runnable cycleRunnable;
    private final double updateFrequency;

    public LoopedThread(double updateFrequency, Runnable loopedRunnable) {
        this.cycleRunnable = loopedRunnable;
        this.updateFrequency = updateFrequency;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((int) Math.ceil(1000 / updateFrequency));
                cycleRunnable.run();
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}