package org.example.towerdefense.Units;

public class Timer {
    double timerCount = 0;
    double fps;
    double allottedTime;
    Runnable task = null;

    public Timer(double allottedTime, Runnable task, double fps) {
        this.allottedTime = allottedTime;
        this.task = task;
        this.fps = fps;
    }

    public void countingDown(){
        timerCount += 1;
        if (timerCount == allottedTime * fps) {
            task.run();
            timerCount = 0;
        }
    }
}
