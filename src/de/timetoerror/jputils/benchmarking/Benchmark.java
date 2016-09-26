/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.timetoerror.jputils.benchmarking;

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
     * Captures a benchmark. This process is running in the thread which calls
     * this method, so this method will also pass time. For async benchmark use
     * the asyncCapture(...) method
     *
     * @param name
     */
    public void capture(String name) {
        captures.add(new BenchmarkStamp(name, System.nanoTime()));
    }

    public void asyncCapture(String name) {

    }

    public ArrayList<BenchmarkStamp> getCaptures() {
        return captures;
    }

    public ArrayList<Long> getTimes() {
        ArrayList<Long> result = new ArrayList<>(captures.size());

        for (BenchmarkStamp b : captures) {
            result.add(b.getTime());
        }

        return result;
    }
    
    public ArrayList<Long> getDiffs()
    {
        ArrayList<Long> result = new ArrayList<>(captures.size());
        
        for(int i = 1; i < captures.size(); i++)
        {
            result.add(Math.abs(captures.get(i).getTime() - captures.get(i-1).getTime()));
        }
        
        return result;
    }
    
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
