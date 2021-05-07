import java.io.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * FileSearch.java
 * Class that returns the following data in an array:
 * int results[], where
 * 
 * [number of occurances, runtime]
 * 
 * Implements both a baseline implentation and a multithreaded 
 * implementation of searching through a particular file. 
 */
public class FileSearch{
    public final File file;
    public  String toFind;

    public FileSearch(File f, String s){
        file = f;
        toFind = s;
        toFind = toFind.toLowerCase();
    }

    /**
     * Multithreaded approach:
     */

    public int[] multithreadedCounter() throws IOException{
        int[] results = new int[2];

        double start = System.nanoTime();

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);
        String line = "";

        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];
        int[] sharedCounter = new int[cores];

        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        String[] fileArray = lines.toArray(new String[lines.size()]);

        int chunkSize = (fileArray.length / cores);
        int lowIndex = 0;
        int highIndex = chunkSize;

        for(int j=0; j<(cores); j++){
            Thread t = new Thread(new FileSearchThread(j, fileArray, toFind, lowIndex, highIndex, sharedCounter));
            if(j==(cores-1)){   
                t = new Thread(new FileSearchThread(j, fileArray, toFind, lowIndex, (fileArray.length-1), sharedCounter));
            }
            threads[j] = t;
            lowIndex = highIndex+1;
            highIndex = highIndex+chunkSize;
        }
    

        for(Thread t : threads){
            t.start();
        }
        for (Thread t : threads){
            try{
                t.join();
        }
            catch (InterruptedException e) {}
        }

        int total = 0;
        for(int i = 0; i<sharedCounter.length; i++){
            total = total+sharedCounter[i];
        }

        double stop = System.nanoTime();
        double timeElapsed = (double) stop - start;

        results[0] = total;
        results[1] = (int) timeElapsed;

        return results;
    }


    /**
     * Baseline approach:
     * Brute force method of counting the occurances of a string. Use
     * BufferedReader to use a rudimentary while loop to read through the 
     * file line by line. 
     */
    public int[] baselineCounter() throws IOException{
        int[] results = new int[2];

        int result = 0;
        double start = System.nanoTime();

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);
        String line = "";

        try{
            while((line = reader.readLine())!=null){
                String[] list = {};
                if(line!=null){
                    list = line.replaceAll("[^a-zA-Z ]", "").split("\\s+");
                }

                for(int i=0; i<list.length; i++){
                    String c = list[i];
                    c=c.toLowerCase();
                    if(toFind.equals(c)){
                        result++;
                    }
                }
            }
        } catch (IOException e) {}

        double stop = System.nanoTime();
        double timeElapsed = (double) stop - start;

        results[0] = result;
        results[1] = (int) timeElapsed;

        return results;
    }
}