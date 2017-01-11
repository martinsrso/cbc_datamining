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


//            System.out.println("Key: " + entry.getKey());
//            System.out.println("Value: " + entry.getValue());

//            BufferedReader reader = new BufferedReader(new FileReader("datafiles_new/" + path + entry.getKey()));
//            BufferedReader reader2 = new BufferedReader(new FileReader("datafiles_new/" + path + entry.getValue()));
            BufferedReader reader_test = new BufferedReader(new FileReader("datafiles/" + path + entry.getKey()));
            BufferedReader reader_train = new BufferedReader((new FileReader("datafiles/" + path + entry.getValue())));

            Instances datatest = new Instances(reader_test);
            Instances datatrain = new Instances(reader_train);

            Instances teste, train;

            reader_test.close();
            reader_train.close();

            for (Classifier cls : Classifiers.classifiers) {
                Remove remove = new Remove();

                if (path.equalsIgnoreCase("bb/")){
                    remove.setAttributeIndices("1,18,63,64"); //bb
                }
                else {
                    remove.setAttributeIndices("1,18,39,40");   //bnb
                }

                remove.setInputFormat(datatest);
                remove.setInputFormat(datatrain);

                teste = Filter.useFilter(datatest, remove);
                train = Filter.useFilter(datatrain, remove);


                teste.setClassIndex(teste.numAttributes() - 1);
                train.setClassIndex(train.numAttributes() - 1);

                System.out.println("[Start: " + new Date() + "]" + cls.getClass().getSimpleName() + " = " + entry.getKey());
                cls.buildClassifier(train);

                Evaluation eval = new Evaluation(train);
                eval.evaluateModel(cls, teste);


                String output = cls.getClass().getSimpleName() + " - " + Arrays.toString(Classifiers.opt.get(cls.getClass().getSimpleName())) + "\n" +
                        eval.correlationCoefficient() +";"+ eval.meanAbsoluteError()+";"+ eval.rootMeanSquaredError() +";"+
                        eval.relativeAbsoluteError() +";"+ eval.rootRelativeSquaredError()+";" +eval.numInstances() + "\n";

                System.out.println("[End: " + new Date() + "] = " +cls.getClass().getSimpleName() + " - " + Arrays.toString(Classifiers.opt.get(cls.getClass().getSimpleName())));

                String rev = "name;ename;atual;atual_ver;predict\n";

                for (int i = 0; i < teste.numInstances(); i++) {
//                    double pred = cls.classifyInstance(teste.instance(i));
//                    System.out.print("ID: " + teste.instance(i).value(0));
//                    System.out.print(",ID 2: " + datatest.instance(i).value(0));
//                    System.out.print(", actual: " + teste.classAttribute().value((int) teste.instance(i).classValue()));
//                    System.out.println(", predicted: " + teste.classAttribute().value((int) pred));
//
//                    System.exit(1);

                    rev += datatest.get(i).stringValue(datatest.get(i).attribute(0)) +";"+
                           datatest.get(i).stringValue(datatest.get(i).attribute(17)) +";"+
                           teste.get(i).classValue() + ";" +
                           eval.predictions().get(i).actual() +";"+
                           eval.predictions().get(i).predicted()+ "\n";
//                    System.out.println(rev);
//                    System.exit(1);
                }

//                System.out.println("Numero de Instancias teste: " + test.numInstances());
//                System.out.println("Numero de Instancias treino: " + train.numInstances());

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
