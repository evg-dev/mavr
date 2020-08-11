package ru.mavr;

import com.badlogic.gdx.Gdx;

public class Testing {

    public static void printAnalytics() {
        int mb = 1024 * 1024;

        // get Runtime instance
        Runtime instance = Runtime.getRuntime();

        System.out.println("\n***** Heap utilization statistics [MB] *****\n");

        // available memory
        System.out.println("Total Memory: " + (instance.totalMemory() / mb));

        // free memory
        System.out.println("Free Memory: " + (instance.freeMemory() / mb));

        // used memory
        System.out.println("Used Memory: " + ((instance.totalMemory() - instance.freeMemory()) / mb));

        // Maximum available memory
        System.out.println("Max Memory: " + (instance.maxMemory() / mb));

        // Gdx heap
        System.out.println("Gdx Java Heap: " + (Gdx.app.getJavaHeap() / mb));

        // Gdx heap
        System.out.println("Gdx Native Heap: " + (Gdx.app.getNativeHeap() / mb)); // Heare leaking
    }
}
