import java.io.*;
import java.util.Arrays;

import javax.management.monitor.CounterMonitorMBean;

public class Tester{

    /**
     * Tester.java
     * This is where main is, and the program to run to test all the tasks
     * main contains instructions to execute each task. Each task method 
     * calls and runs their respective tasks, by calling the baseline implementation
     * and the multithreaded implementation, then prints the results.
     */

    public static void main(String args[]){
        File folder = new File("D:\\Projects\\School\\COSC-273\\stringProcessor\\tests");
        final File[] testCases = getFilesFromFolder(folder);

        // Test 1
        /*
        try{
            task1(testCases);
        } catch(IOException e){}
        System.out.println(); */

        // Test 2
        try{
            task2(testCases);
        } catch(IOException e) {}

         // Test 3
        try{
            task3(testCases);
        } catch(IOException e) {}
    
        // Test 4
        try{
            task4(testCases);
        } catch(IOException e) {}
    }

    // TESTS 

    // TESTER for TASK 1: Characters, Words, Lines
    public static void task1(File[] testCases) throws IOException{
        System.out.println("TASK 1: Characters, Words, Lines");
        String[] types = {"Characters", "Words", "Lines"};

        for(File file : testCases){
            WordCounter wCounter = new WordCounter(file);
            double[][] baselineResults = wCounter.baselineCounter();
            double[][] multithreadedResults = wCounter.multithreadedCounter();

            System.out.println("TASK 1, Test case: "+file.getName());
            System.out.printf("|-----------|------------|------------------|---------|\n" +
            "|   count   |    type    |   improvement    | passed? |\n" +
            "|-----------|------------|------------------|---------|\n");
            
            // Print results for all three tasks
            for(int i=0; i<3; i++){
                
                boolean passed = true;
                if(baselineResults[i][0] != multithreadedResults[i][0]){
                    passed = false;
                }

                double improvement = (double) (multithreadedResults[i][1] / baselineResults[i][1]);
                int count = (int) baselineResults[i][0];

                System.out.printf("|   %7d | %11s|         %8f |   %5s |\n", count, types[i], improvement/1_000, passed);
            }

            System.out.printf("|-----------|------------|------------------|---------|\n");
            System.out.println();
        }
    }

    // TESTER for TASK 2: Search for a String
    public static void task2(File[] testCases) throws IOException{
        System.out.println("TASK 2: Search for a String");
        //String[] tests = {"test", "multithreading", "bee", "McQueen", "the", "madcap"};
        String[] tests = {"the", "the", "the", "the", "the", "the"};

        int c = 0;
        for(File file : testCases){
            FileSearch searcher = new FileSearch(file, tests[c]);
            double[] baselineResults = searcher.baselineCounter();
            double[] multithreadedResults = searcher.multithreadedCounter();

            System.out.println("TASK 2, Test case: "+file.getName());
            System.out.printf("|-----------|---------------|---------------------|---------|\n" +
            "|   count   |     string    |     improvement     | passed? |\n" +
            "|-----------|---------------|---------------------|---------|\n");

            double t1 = (double) baselineResults[1];
            double t2 = (double) multithreadedResults[1];
            double improvement =  t1/t2;  
            
            boolean passed = true;
            if(baselineResults[0] != multithreadedResults[0]){
                passed = false;
            }

            int count = (int) baselineResults[0];

            System.out.printf("|   %7d | %14s|        %12f |   %5s |\n", count, tests[c], improvement/1_000, passed);
            System.out.printf("|-----------|---------------|---------------------|---------|\n");
            System.out.println();
            c++;
        }
    }

    // TESTER for TASK 3: Find distinct words in files
    public static void task3(File[] testCases) throws IOException{
        System.out.println("TASK 3: Count the Distinct Words");

        for(File file : testCases){
            DistinctSearch distinctSearch = new DistinctSearch(file);
            double[] baselineResults = distinctSearch.baselineCounter();
            double[] multithreadedResults = distinctSearch.multithreadedCounter();
        
            System.out.println("TASK 3, Test case: "+file.getName());
            System.out.printf("|-----------|---------------------|---------|\n" +
            "|   found   |     improvement     | passed? |\n" +
            "|-----------|---------------------|---------|\n");

            double t1 = (double) baselineResults[1];
            double t2 = (double) multithreadedResults[1];
            double improvement =  t1/t2;  

            // System.out.println(t1+ " "+t2);

            boolean passed = true;
            if(baselineResults[0] != multithreadedResults[0]){
                passed = false;
                // System.out.println(baselineResults[0] + " "+multithreadedResults[0]);
            }

            int count = (int) baselineResults[0];

            System.out.printf("|   %7d |        %12f |   %5s |\n", count,  improvement, passed);
            System.out.printf("|-----------|---------------------|---------|\n");
            System.out.println();
        }
    }

    // TESTER for TASK 4: Spell check
    public static void task4(File[] testCases) throws IOException{
        System.out.println("TASK 4: Spell Check");

        for(File file : testCases){
            SpellCheck spellCheck = new SpellCheck(file);
            double[] baselineResults = spellCheck.baselineCounter();
            double[] multithreadedResults = spellCheck.multithreadedCounter();
        
            System.out.println("TASK 4, Spell Check: "+file.getName());
            System.out.printf("|-----------|---------------------|---------|\n" +
            "|   found   |     improvement     | passed? |\n" +
            "|-----------|---------------------|---------|\n");

            double t1 = (double) baselineResults[1];
            double t2 = (double) multithreadedResults[1];
            double improvement =  t1/t2;  

            // System.out.println(t1+ " "+t2);

            boolean passed = true;
            if(baselineResults[0] != multithreadedResults[0]){
                passed = false;
                // System.out.println(baselineResults[0] + " "+multithreadedResults[0]);
            }

            int count = (int) baselineResults[0];

            System.out.printf("|   %7d |        %12f |   %5s |\n", count,  improvement, passed);
            System.out.printf("|-----------|---------------------|---------|\n");
            System.out.println();
        }
    }

    // HELPER METHODS

    // helper method to get all test case files from test folder
    public static File[] getFilesFromFolder(File folder) {
        int c = 0;
        File[] testCases = new File[folder.listFiles().length];
        for (File f : folder.listFiles()) {
            if (f.isDirectory()) {
                getFilesFromFolder(f);
            } else {
                testCases[c] = f;
                c++;
                System.out.println("Read in "+f.getName());
            }
        }
        System.out.println();
        return testCases;
    }
    

}