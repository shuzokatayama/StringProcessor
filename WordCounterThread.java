import java.awt.*;

public class WordCounterThread implements Runnable{

    int id;
    String[] fileArray;
    int rangeLow;
    int rangeHigh;
    int type;
    int[] total;

    public WordCounterThread(int i, String[] f, int l, int h, int t, int[] to){
        id = i;
        fileArray = f;
        rangeLow = l;
        rangeHigh = h;
        type = t;
        total = to;
    }

    public void run (){
        int result = 0;
        switch(type){
            case 0:
                int characters = 0;
                for(int i=rangeLow; i<=rangeHigh; i++){
                    characters = characters+(fileArray[i].length());
                }
                result = characters;
                break;
            
            case 1:
                result = rangeHigh - rangeLow;
                break;

            case 2:
                int lines = 0;
                for(int i=rangeLow; i<=rangeHigh; i++){
                    if(fileArray[i].equals("\n")){
                        lines++;
                    }
                }
                result = lines;
                break;            
        }
        total[id] = total[id]+result;
    }
}