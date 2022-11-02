import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class SpellCheckThread implements Runnable{
    int id;
    String[] fileArray;
    int rangeLow;
    int rangeHigh;
    File dictionary;
    int[] sharedCounter;

    List<String> dictionaryList;
    
    public SpellCheckThread(int i, String[] f, int l, int h, File d, int[] s){
        id = i;
        fileArray = f;
        rangeLow = l;
        rangeHigh = h;
        dictionary = d;
        sharedCounter = s;
    }

    public void run(){
        try{
            dictionaryList = Files.readAllLines(dictionary.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e){}
        String line = "";
        int result = 0;
        for(int i=rangeLow; i<=rangeHigh; i++){
            line = fileArray[i];
            String[] list = {};
            if(line!=null){
                list = line.replaceAll("[^a-zA-Z ]", "").split("\\s+");
            }

            for(int j=0; j<list.length; j++){
                String c = list[j];
                c=c.toLowerCase();
                if(!dictionaryList.contains(c)){
                    result++;
                }
            }
        }
        sharedCounter[id] = result;
    }
}
