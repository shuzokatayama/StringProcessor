import java.io.*;
import java.util.Arrays;

public class Tester{

    public static void main(String args[]){
        File folder = new File("D:\\Projects\\School\\COSC-273\\stringProcessor\\tests");
        final File[] testCases = getFilesFromFolder(folder);

        // Test 1
        try{
            task1(testCases);
        } catch(IOException e){}
        System.out.println();

        // Test 2
        try{
            task2(testCases);
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
            "|    size   |    type    |   improvement    | passed? |\n" +
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
        String[] tests = {"test", "multithreading", "bee", "McQueen"};

        int c = 0;
        for(File file : testCases){
            FileSearch searcher = new FileSearch(file, tests[c]);
            int[] baselineResults = searcher.baselineCounter();
            int[] multithreadedResults = searcher.multithreadedCounter();

            System.out.println("TASK 2, Test case: "+file.getName());
            System.out.printf("|-----------|---------------|---------------------|---------|\n" +
            "|   found   |     string    |     improvement     | passed? |\n" +
            "|-----------|---------------|---------------------|---------|\n");

            double improvement =  baselineResults[1] / multithreadedResults[1];
            
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