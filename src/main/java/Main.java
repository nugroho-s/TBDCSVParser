import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    static HashMap<String,Integer> mappingMap = new HashMap<String, Integer>(300000);
    static int idnow = 0;

    public static void main(String[] args) throws IOException {
        CSVReader reader = null;
        File file = new File("outdata/lastfm.txt");
        FileWriter writer = new FileWriter(file);

        try {
            reader = new CSVReader(new FileReader("data/lastfm.csv"), ',' , '"' , 1);
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
                    if((!prev.equals(nextLine[0]))){
                        cbaris++;
                        int i;
                        if(cbaris!=1) {
                            writer.write(prev+" ");
                            for (i = 0; i < baris.size() - 1; i++)
                                writer.write(baris.get(i) + " ");
                            writer.write(baris.get(i) + "\n");
                        }
                        baris.clear();
                        prev = nextLine[0];
                    }
                    idmap = getId(nextLine[1]);
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
