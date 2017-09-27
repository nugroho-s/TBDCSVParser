import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Main {
    static HashMap<String,Integer> mappingMap = new HashMap<String, Integer>(300000);
    static int idnow = 0;

    public static void main(String[] args) throws Exception {
        if(args.length<3){
            throw new Exception("must pass 3 argument, file name without space" +
                    " row index and items index of csv");
        }

        int idxbaris = Integer.parseInt(args[1]);
        int idxitems = Integer.parseInt(args[2]);

        CSVReader reader = null;
        File file = new File("outdata/"+args[0]+".txt");
        FileWriter writer = new FileWriter(file);

        try {
            reader = new CSVReader(new FileReader("data/"+args[0]+".csv"), ',' , '"' , 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] nextLine;
        String prev = "kosong";
        ArrayList<Integer> baris = new ArrayList<>();
        int cbaris=0;

        int idmap;

        try {
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine != null) {
                    if((!prev.equals(nextLine[idxbaris]))){
                        cbaris++;
                        int i;
                        if(cbaris!=1) {
//                            writer.write(prev+" ");
                            Collections.sort(baris);
                            for (i = 0; i < baris.size() - 1; i++)
                                writer.write(baris.get(i) + " ");
                            writer.write(baris.get(i) + "\n");
                        }
                        baris.clear();
                        prev = nextLine[idxbaris];
                    }
                    idmap = getId(nextLine[idxitems]);
                    baris.add(idmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.flush();
        writer.close();
    }

    static int getId(String id){
        if(mappingMap.containsKey(id))
            return mappingMap.get(id);
        mappingMap.put(id,++idnow);
        return idnow;
    }
}
