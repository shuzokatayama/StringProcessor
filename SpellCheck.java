import java.io.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.*;

/**
 * SpellCheck.java
 * Class that returns the following data in an array:
 * int results[], where
 * 
 * [number of misspelt words, runtime]
 * 
 * Implements both a baseline implentation and a multithreaded 
 * implementation of searching through a particular file. 
 */
public class SpellCheck{
    public final File file;
    public final File dictionary = new File("english3.txt");

    public SpellCheck(File f){
        file = f;
    }

    /**
     * Multithreaded approach:
     * Convert the file into an array of lines, and have each thread 
     * work on a set of lines. Have the threads put their results in a 
     * shared array, and then add the results.
     */

    public double[] multithreadedCounter() throws IOException{
        double[] results = new double[2];

        double start = System.nanoTime();

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);
        String line = "";

        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        String[] fileArray = lines.toArray(new String[lines.size()]);

        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];

        List<String> dictionaryList = Files.readAllLines(dictionary.toPath(), StandardCharsets.UTF_8);
        int[] sharedArray = new int[cores];

        int chunkSize = (fileArray.length / cores);
        int lowIndex = 0;
        int highIndex = chunkSize;

        for(int j=0; j<(cores); j++){
            Thread t = new Thread(new SpellCheckThread(j, fileArray, lowIndex, highIndex, dictionary, sharedArray));
            if(j==(cores-1)){   
                t = new Thread(new SpellCheckThread(j, fileArray, lowIndex, (fileArray.length-1),dictionary, sharedArray));
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
        for(int i = 0; i<sharedArray.length; i++){
            total = total+sharedArray[i];
        }

        double stop = System.nanoTime();
        double timeElapsed = (double) stop - start;

        
        results[0] = total;
        results[1] = (int) timeElapsed;

        return results;
    }

     /**
     * Baseline approach:
     * Brute force method of counting the misspelt words. Loop through the 
     * file and check to see if the word is in the Dictionary (english3.txt)
     * Source: http://www.gwicks.net/dictionaries.htm
     */
    public double[] baselineCounter() throws IOException{
        double[] results = new double[2];

        double start = System.nanoTime();

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);
        String line = "";

        List<String> dictionaryList = Files.readAllLines(dictionary.toPath(), StandardCharsets.UTF_8);
        
        int misspeltCounter = 0;

        try{
            while((line = reader.readLine())!=null){
                String[] list = {};
                if(line!=null){
                    list = line.replaceAll("[^a-zA-Z ]", "").split("\\s+");
                }

                for(int i=0; i<list.length; i++){
                    String c = list[i];
                    c=c.toLowerCase();
                    
                    // If the word is not in the dictionary, increment
                    if(!dictionaryList.contains(c)){
                        misspeltCounter++;
                    }
                }
            }
        } catch (IOException e) {}

        double stop = System.nanoTime();
        double timeElapsed = (double) stop - start;

        results[0] = misspeltCounter;
        results[1] = timeElapsed;

        return results;
    }
}