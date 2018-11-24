/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.benchmarking;

import java.util.ArrayList;

/**
 *
 * @author Jackjan
 */
public class Benchmark {

    private final String title;
    private final ArrayList<BenchmarkStamp> captures;

    public Benchmark(String title) {
        this.title = title;
        captures = new ArrayList<>();
    }

    /**
     * Constructs a Benchmark. UIse this constructor if you already know how
     * many captures you will make.
     *
     * @param title
     * @param count
     */
    public Benchmark(String title, int count) {
        this.title = title;
        captures = new ArrayList<>(count);
    }

    /**
     * Captures a benchmark.
     *
     * @param name
     */
    public void capture(String name) {
        captures.add(new BenchmarkStamp(name, System.nanoTime()));
    }

    
    /**
     * Returns a captured BenchMarkStamps
     * @return 
     */
    public ArrayList<BenchmarkStamp> getCaptures() {
        return captures;
    }

    
    /**
     * Gets all times of the captured stamps
     * @return 
     */
    public ArrayList<Long> getTimes() {
        ArrayList<Long> result = new ArrayList<>(captures.size());

        for (BenchmarkStamp b : captures) {
            result.add(b.getTime());
        }

        return result;
    }
    
    
    /**
     * Gets all differences beteen the captured times
     * @return 
     */
    public ArrayList<Long> getDiffs()
    {
        ArrayList<Long> result = new ArrayList<>(captures.size());
        
        for(int i = 1; i < captures.size(); i++)
        {
            result.add(Math.abs(captures.get(i).getTime() - captures.get(i-1).getTime()));
        }
        
        return result;
    }
    
    
    /**
     * Prints the benchmark into the connected console of the application
     */
    public void printBenchmark()
    {
        System.out.println("=== Benchmark " + title + " ===");
        
        for(int i = 1; i < captures.size(); i++)
        {
            System.out.println("Capture + " + captures.get(i).getTitle() + ": " + Math.abs(captures.get(i).getTime() - captures.get(i-1).getTime()) + " | Stamp: " + captures.get(i).getTime());
        }
        
        System.out.println("=== Benchmark end ===");
    }
}
