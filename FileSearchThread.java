import java.awt.*;

public class FileSearchThread implements Runnable{

    int id;
    String[] fileArray;
    String toFind;
    int rangeLow;
    int rangeHigh;
    int[] total;

    public FileSearchThread(int i, String[] f, String s, int l, int h, int[] to){
        id = i;
        fileArray = f;
        toFind = s;
        rangeLow = l;
        rangeHigh = h;
        total = to;
    }

    public void run (){
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
                if(toFind.equals(c)){
                    result++;
                }
            }
        }

        total[id] = result;
    }
}