package org.goal;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by rmartins on 06/12/16.
 */
public class EvalReverse {
    public static void main(String[] args) throws Exception {
        Run(StringsArff.BB_ARRAY, "bb/");
        Run(StringsArff.BNB_ARRAY, "bnb/");
    }

    public static void Run(Map<String, String> array, String path) throws Exception {
        for (Map.Entry<String, String> entry : array.entrySet()) {

            BufferedReader reader = new BufferedReader(new FileReader("datafiles_new/" + path + entry.getKey()));
            BufferedReader reader2 = new BufferedReader(new FileReader("datafiles_new/" + path + entry.getValue()));
            BufferedReader reader3 = new BufferedReader(new FileReader("datafiles/" + path + entry.getValue()));
            Instances test_name = new Instances(reader3);
            Instances train = new Instances(reader2);
            Instances test;// = new Instances(reader);
            reader.close();
            reader2.close();
            reader3.close();

            train.setClassIndex(train.numAttributes() - 1);

            test_name.setClassIndex(test_name.numAttributes() - 1);

            Remove remove = new Remove();

            if (path.equalsIgnoreCase("bb/")){
                remove.setAttributeIndices("1,18,63,64"); //bb
            }
            else {
                remove.setAttributeIndices("1,18,39,40");   //bnb
            }

            remove.setInputFormat(test_name);

            test = Filter.useFilter(test_name, remove);
            test.setClassIndex(test.numAttributes() - 1);

            for (Classifier cls : Classifiers.classifiers) {
                System.out.println("[Start: " + new Date() + "]" + cls.getClass().getSimpleName() + " = " + entry.getKey());
                cls.buildClassifier(train);
                Evaluation eval = new Evaluation(train);
                eval.evaluateModel(cls, test);

                String output = cls.getClass().getSimpleName() + " - " + Arrays.toString(Classifiers.opt.get(cls.getClass().getSimpleName())) + "\n" +
                        eval.correlationCoefficient() +";"+ eval.meanAbsoluteError()+";"+ eval.rootMeanSquaredError() +";"+
                        eval.relativeAbsoluteError() +";"+ eval.rootRelativeSquaredError()+";" +eval.numInstances() + "\n";

                System.out.println("[End: " + new Date() + "] = " +cls.getClass().getSimpleName() + " - " + Arrays.toString(Classifiers.opt.get(cls.getClass().getSimpleName())));

                String rev = "name;ename;atual;atual_ver;predict\n";

                for (int i = 0; i < test.numInstances(); i++) {
                    rev += test_name.get(i).stringValue(test_name.get(i).attribute(0)) +";"+
                           test_name.get(i).stringValue(test_name.get(i).attribute(17)) +";"+
                           test.get(i).classValue() + ";" +
                           eval.predictions().get(i).actual() +";"+
                           eval.predictions().get(i).predicted() + "\n";
                }

                File arq = new File("models/" + path + "/" +cls.getClass().getSimpleName() + "/" + entry.getKey()+"."+cls.getClass().getSimpleName());
                File arq2 = new File("result_rvs/" + path + "/" +cls.getClass().getSimpleName() + "/" + entry.getKey()+"."+cls.getClass().getSimpleName());
                arq.getParentFile().mkdirs();
                arq2.getParentFile().mkdirs();
                BufferedWriter writer = new BufferedWriter(new FileWriter(arq));
                BufferedWriter writer2 = new BufferedWriter(new FileWriter(arq2));
                writer.write(output);
                writer.flush();
                writer.close();
                writer2.write(rev);
                writer2.flush();
                writer2.close();
            }
        }
    }
}
