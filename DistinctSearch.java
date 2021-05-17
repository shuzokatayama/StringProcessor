import java.io.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.*;

/**
 * DistinctSearch.java
 * Class that returns the following data in an array:
 * int results[], where
 * 
 * [number of distinct words, runtime]
 * 
 * Implements both a baseline implentation and a multithreaded 
 * implementation of searching through a particular file. 
 */
public class DistinctSearch{
    public final File file;

    public DistinctSearch(File f){
        file = f;
    }

    /**
     * Multithreaded approach:
     * Use a Java concurrent data structure, the BlockingQueue, to keep 
     * track of the unique words
     */

    public int[] multithreadedCounter() throws IOException{
        int[] results = new int[2];

        double start = System.nanoTime();

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);
        String line = "";

        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        String[] fileArray = lines.toArray(new String[lines.size()]);

        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];

        ArrayBlockingQueue<String> uniqueStrings = new ArrayBlockingQueue<String>(100000);

        int chunkSize = (fileArray.length / cores);
        int lowIndex = 0;
        int highIndex = chunkSize;

        for(int j=0; j<(cores); j++){
            Thread t = new Thread(new DistinctSearchThread(fileArray, lowIndex, highIndex, uniqueStrings));
            if(j==(cores-1)){   
                t = new Thread(new DistinctSearchThread(fileArray, lowIndex, (fileArray.length-1), uniqueStrings));
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

        Object[] totals = uniqueStrings.toArray();
        int total = totals.length;

        
      /*  for(int i=0; i<totals.length; i++){
            System.out.println(totals[i]);
        } */

        double stop = System.nanoTime();
        double timeElapsed = (double) stop - start;

        
        results[0] = total;
        results[1] = (int) timeElapsed;

        return results;
    }

    /**
     * Baseline approach:
     * Brute force method of counting the unique strings. Loop through the 
     * file and add distinct words into a List, then count the list
     */
    public int[] baselineCounter() throws IOException{
        int[] results = new int[2];

        double start = System.nanoTime();

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);
        String line = "";
        
        List<String> distinctWordList = new ArrayList<String>();

        try{
            while((line = reader.readLine())!=null){
                String[] list = {};
                if(line!=null){
                    list = line.replaceAll("[^a-zA-Z ]", "").split("\\s+");
                }

                for(int i=0; i<list.length; i++){
                    String c = list[i];
                    c=c.toLowerCase();
                    
                    // Try to find the current word in the distinct list
                    boolean found = false;
                    for(String s : distinctWordList){
                        if(s.equals(c)){
                            found = true;
                        }
                    }

                    // If its not in the distinct list, add it
                    if(!found){
                        distinctWordList.add(c);
                    }
                }
            }
        } catch (IOException e) {}

        double stop = System.nanoTime();
        double timeElapsed = (double) stop - start;

       /* for(int i=0; i<distinctWordList.size(); i++){
            System.out.println(distinctWordList.get(i));
        }*/


        results[0] = distinctWordList.size();
        results[1] = (int) timeElapsed;

        return results;
    }
}