package org.example.towerdefense;

import javafx.application.Platform;

public class LauncherThread extends Thread {
    private final Runnable cycleRunnable;
    private final double updateFrequency;

    public LauncherThread(double updateFrequency, Runnable loopedRunnable) {
        this.cycleRunnable = loopedRunnable;
        this.updateFrequency = updateFrequency;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((int) Math.ceil(1000 / updateFrequency));
                Platform.runLater(cycleRunnable);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}