import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class DistinctSearchThread implements Runnable{

    String[] fileArray;
    int rangeLow;
    int rangeHigh;
    ArrayBlockingQueue<String> uniqueStrings;
    
    public DistinctSearchThread(String[] f, int l, int h, ArrayBlockingQueue<String> u){
        fileArray = f;
        rangeLow = l;
        rangeHigh = h;
        uniqueStrings = u;
    }
    
    public void run(){
        String line = "";
        for(int i=rangeLow; i<=rangeHigh; i++){
            line = fileArray[i];
            String[] list = {};
            if(line!=null){
                list = line.replaceAll("[^a-zA-Z ]", "").split("\\s+");
            }

            for(int j=0; j<list.length; j++){
                String c = list[j];
                c=c.toLowerCase();
                if(!uniqueStrings.contains(c)){
                    uniqueStrings.add(c);
                }
            }
        }
    }

}