package org.goal;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.SimpleLinearRegression;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LWL;
import weka.classifiers.meta.*;
import weka.classifiers.rules.M5Rules;
import weka.classifiers.trees.M5P;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rmartins on 29/11/16.
 */
public class Classifiers {

    public static final Map<String, String[]> opt = new HashMap<String, String[]>(){{
        put("LinearRegression", new LinearRegression().getOptions());
        put("MultilayerPerceptron", new MultilayerPerceptron().getOptions());
        put("SMOreg", new SMOreg().getOptions());
        put("IBk", new IBk().getOptions());
        put("KStar", new KStar().getOptions());
        put("LWL", new LWL().getOptions());
        put("AdditiveRegression", new AdditiveRegression().getOptions());
        put("RandomCommittee", new RandomCommittee().getOptions());
        put("RandomizableFilteredClassifier", new RandomizableFilteredClassifier().getOptions());
        put("Bagging", new Bagging().getOptions());
        put("RandomSubSpace", new RandomSubSpace().getOptions());
        put("RegressionByDiscretization", new RegressionByDiscretization().getOptions());
        put("M5Rules", new M5Rules().getOptions());
        put("M5P", new M5P().getOptions());
        put("RandomTree", new RandomTree().getOptions());
        put("RandomForest", new RandomForest().getOptions());
        put("REPTree", new REPTree().getOptions());

    }};

    public static Classifier[] classifiers = {
           // new LinearRegression(),
           // new MultilayerPerceptron(),
           // new SMOreg(),
           // new IBk(),
           // new KStar(),
           // new LWL(),
           // new AdditiveRegression(),
           // new RandomCommittee(),
           // new RandomizableFilteredClassifier(),
           // new Bagging(),
           // new RandomSubSpace(),
           // new RegressionByDiscretization(),
           // new M5Rules(),
           // new M5P(),
           new RandomTree(),
           // new RandomForest(),
           // new REPTree(),
    };


}
