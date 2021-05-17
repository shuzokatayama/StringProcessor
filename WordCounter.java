import java.io.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

/**
 * WordCounter.java
 * Class that returns the following data in an array:
 * int results[][], where
 * 
 * [[0: number of characters, runtime],
 *  [1: number of words, runtime],
 *  [2: number of lines, runtime]]
 * 
 * Implements both a baseline implentation and a multithreaded 
 * implementation of searching through a particular file. 
 */
public class WordCounter{
    public final File file;

    public WordCounter(File f){
        file = f;
    }

    /**
     * Baseline approach:
     * Brute force method of counting characters, words, and lines. Use
     * BufferedReader to use a rudimentary while loop to read through the 
     * file line by line. 
     */
    public int[][] baselineCounter(){
        int[][] results = new int[3][2];

        // Go through all 3 tasks using the baseline implementation
        for(int i=0; i<3; i++){
            int result = 0;
            double start = System.nanoTime();
            try{
                result = countType(i);
            } catch (IOException e){
                System.out.println("error in baselineCoutner");
            }
            double stop = System.nanoTime();
            double timeElapsed = (double) stop - start;

            results[i][0] = result;
            results[i][1] = (int) timeElapsed;
            // 0: Result from each countType result
            // 1: time elapsed
            
        }
        return results;
    }   
    
    /**
     * Multithreading approach: 
     * Use the readAllLines method to convert the entire file to a String[], and 
     * then use multithreading to parse through the document in parallel
     */
    public int[][] multithreadedCounter() throws IOException {
        int[][] results = new int[3][2];
        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];

        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);

        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        String[] fileArray = lines.toArray(new String[lines.size()]);
        int[] sharedCounter = new int[cores];

        int chunkSize = (fileArray.length / cores);
        int lowIndex = 0;
        int highIndex = chunkSize;

        // Loop to perform each task separately
        // Types: 
        // 0: count the number of characters
        // 1: count the number of words
        // 2: count the number of lines
        for(int i=0; i<3; i++){
            double start = System.nanoTime();
            int total = 0;
            
            for(int k = 0; k<sharedCounter.length; k++){
                sharedCounter[k]=0;
            }
            
            for(int j=0; j<(cores); j++){
                Thread t = new Thread(new WordCounterThread(j, fileArray, lowIndex, highIndex, i, sharedCounter));
                if(j==(cores-1)){   
                    t = new Thread(new WordCounterThread(j, fileArray, lowIndex, (fileArray.length-1), i, sharedCounter));
                }
                threads[j] = t;
                lowIndex = highIndex+1;
                highIndex = lowIndex+chunkSize;
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

            for(int k = 0; k<sharedCounter.length; k++){
                total = total+sharedCounter[k];
            }
            System.out.println(total);

            double stop = System.nanoTime();
            double timeElapsed = (double) stop - start;

            results[i][0] = total;
            results[i][1] = (int) timeElapsed;
        }

        return results;
    }

    // Types: 
    // 0: count the number of characters
    // 1: count the number of words
    // 2: count the number of lines
    public int countType(int type) throws IOException{
        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);
        String line = "";
        int result = 0;
        switch(type){
            case 0:
                int characters = 0;
                try{
                    while((line = reader.readLine())!=null){
                        if(line!=null){
                            characters = characters + line.length();
                        }
                    }
                } catch (IOException e) {}
                result = characters;
                break;
            
            case 1:
                int words = 0;
                try{
                    while((line = reader.readLine())!=null){
                        if(line!=null){
                            String[] list = line.replaceAll("[^a-zA-Z ]", "").split("\\s+");
                            words = words+list.length;
                        }
                    }
                } catch (IOException e){ }
                result = words;
                break;

            case 2:
                int lines = 0;
                try{
                    while((line = reader.readLine())!=null){
                        lines++;
                    }
                } catch (IOException e) {}
                result = lines;
                break;            
        }
        return result;
    }
}