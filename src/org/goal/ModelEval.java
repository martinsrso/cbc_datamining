package org.goal;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.output.prediction.AbstractOutput;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Attribute;
import weka.core.Instances;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by rmartins on 28/11/16.
 */
public class ModelEval {
    public static void main2(String[] args) throws Exception {
        Run(StringsArff.BB_ARRAY, "bb/");
        Run(StringsArff.BNB_ARRAY, "bnb/");
    }

    public static void Run(Map<String, String> array, String path) throws Exception{
        for (Map.Entry<String, String> entry: array.entrySet()) {

            BufferedReader reader = new BufferedReader(new FileReader("datafiles_new/" + path + entry.getKey()));
            BufferedReader reader2 = new BufferedReader(new FileReader("datafiles_new/" + path + entry.getValue()));
            Instances train = new Instances(reader2);
            Instances test = new Instances(reader);
            reader.close();
            reader2.close();

            train.setClassIndex(train.numAttributes() - 2);
            test.setClassIndex(test.numAttributes() - 2);

//            for (Classifier cls : Classifiers.classifiers) {
//                System.out.println("[Start: " + new Date() + "]" + cls.getClass().getSimpleName() + " = " + entry.getKey());
//                cls.buildClassifier(train);
//                Evaluation eval = new Evaluation(train);
//                eval.evaluateModel(cls, test);
//
//                String output = cls.getClass().getSimpleName() + " - " + Arrays.toString(Classifiers.opt.get(cls.getClass().getSimpleName())) + "\n" +
//                        eval.correlationCoefficient() +";"+ eval.meanAbsoluteError()+";"+ eval.rootMeanSquaredError() +";"+
//                        eval.relativeAbsoluteError() +";"+ eval.rootRelativeSquaredError()+";" +eval.numInstances() + "\n";
//
//                System.out.println("[End: " + new Date() + "] = " +cls.getClass().getSimpleName() + " - " + Arrays.toString(Classifiers.opt.get(cls.getClass().getSimpleName())));
//
//                File arq = new File("models/" + path + "/" +cls.getClass().getSimpleName() + "/" + entry.getKey()+"."+cls.getClass().getSimpleName());
//                arq.getParentFile().mkdirs();
//                BufferedWriter writer = new BufferedWriter(new FileWriter(arq));
//                writer.write(output);
//                writer.flush();
//                writer.close();
//
//            }
        }
    }
}
