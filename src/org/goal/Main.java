package org.goal;

import java.io.*;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Main {

    public static void main(String[] args) {

        try {
            File dir = new File("datafiles/bnb/");
            String[] filesInDir = dir.list();

            for (String insta: filesInDir) {
                BufferedReader reader = new BufferedReader(new FileReader("datafiles/bnb/"+insta));
                Instances data = new Instances(reader);
                Instances data_new;
                reader.close();

                Remove remove = new Remove();
//                remove.setAttributeIndices("1,18,63,64"); //bb
                remove.setAttributeIndices("1,18,39,40");   //bnb
                remove.setInputFormat(data);

                data_new = Filter.useFilter(data, remove);
                System.out.println(insta);

                BufferedWriter writer = new BufferedWriter(new FileWriter("datafiles_new/bnb/"+insta));
                writer.write(data_new.toString());
                writer.flush();
                writer.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
