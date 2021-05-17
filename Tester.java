import java.io.*;
import java.util.Arrays;

public class Tester{

    public static void main(String args[]){
        File folder = new File("D:\\Projects\\School\\COSC-273\\stringProcessor\\tests");
        final File[] testCases = getFilesFromFolder(folder);

        // Test 1
        /* SCRAPPED
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
    }

    // TESTS 

    // TESTER for TASK 1: Characters, Words, Lines
    public static void task1(File[] testCases) throws IOException{
        System.out.println("TASK 1: Characters, Words, Lines");
        String[] types = {"Characters", "Words", "Lines"};

        for(File file : testCases){
            WordCounter wCounter = new WordCounter(file);
            int[][] baselineResults = wCounter.baselineCounter();
            int[][] multithreadedResults = wCounter.multithreadedCounter();

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

                double improvement = (double) (baselineResults[i][1] / multithreadedResults[i][1]);

                System.out.printf("|   %7d | %11s|         %8f |   %5s |\n", baselineResults[i][0], types[i], improvement/1_000, passed);
            }

            System.out.printf("|-----------|------------|------------------|---------|\n");
            System.out.println();
        }
    }

    // TESTER for TASK 2: Search for a String
    public static void task2(File[] testCases) throws IOException{
        System.out.println("TASK 2: Search for a String");
        String[] tests = {"test", "multithreading", "bee", "McQueen", "the"};

        int c = 0;
        for(File file : testCases){
            FileSearch searcher = new FileSearch(file, tests[c]);
            int[] baselineResults = searcher.baselineCounter();
            int[] multithreadedResults = searcher.multithreadedCounter();

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

            System.out.printf("|   %7d | %14s|        %12f |   %5s |\n", baselineResults[0], tests[c], improvement/1_000, passed);
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
            int baselineResults[] = distinctSearch.baselineCounter();
            int[] multithreadedResults = distinctSearch.multithreadedCounter();
        
            System.out.println("TASK 3, Test case: "+file.getName());
            System.out.printf("|-----------|---------------------|---------|\n" +
            "|   found   |     improvement     | passed? |\n" +
            "|-----------|---------------------|---------|\n");

            double t1 = (double) baselineResults[1];
            double t2 = (double) multithreadedResults[1];
            double improvement =  t1/t2;  

            System.out.println(t1+ " "+t2);

            boolean passed = true;
            if(baselineResults[0] != multithreadedResults[0]){
                passed = false;
                System.out.println(baselineResults[0] + " "+multithreadedResults[0]);
            }

            System.out.printf("|   %7d |        %12f |   %5s |\n", baselineResults[0],  improvement, passed);
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