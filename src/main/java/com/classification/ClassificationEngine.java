package com.classification;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.Evaluation;
import weka.classifiers.Classifier;
import java.util.Random;

public class ClassificationEngine {
    /*
    * The schema of the dataset.
    */
    private Classifier classifier;

    /*
    * The dataset constructor.
    */     
    public ClassificationEngine(String name) {
        this.setClassifier(name);
    }

    /*
    * This method is provided with a classifier name ( a string),
    * and it returns an instance of that classifier.
    */
    public void setClassifier(String name) {
        if (name == "BFTree") {
            this.classifier = new BFTree();
        } else if (name == "DecisionStump") {
            this.classifier = new DecisionStump();
        } else if (name == "FT") {
            this.classifier = new FT();
        } else if (name == "J48") {
            this.classifier = new J48();
        } else if (name == "J48graft") {
            this.classifier = new J48graft();
        } else if (name == "LADTree") {
            this.classifier = new LADTree();
        } else if (name == "LMT") {
            this.classifier = new LMT();
        } else if (name == "NBTree") {
            this.classifier = new NBTree();
        } else if (name == "RandomForest") {
            this.classifier = new RandomForest();
        } else if (name == "RandomTree") {
            this.classifier = new RandomTree();
        } else if (name == "REPTree") {
            this.classifier = new REPTree();
        } else if (name == "SimpleCart") {
            this.classifier = new SimpleCart();
        } else if (name == "NaiveBayes") {
            this.classifier = new NaiveBayes();
        }
    }    

    /*
    * getClassifier returns the classifier of the classification engin.
    */
    public Classifier getClassifier(String name) {
        return this.classifier;
    }
                
    /*
    * trainClassifier trains the classification engins classifier.
    */    
    public void trainClassifier(Dataset dataset) {
        try {
            this.classifier.buildClassifier(dataset.getInstances());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    * evaluateClassifier evaluates the performance of the classification engins classifier.
    */
    public double evaluateClassifier(Dataset dataset) {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");

        try {
            // Training the classifier
            this.trainClassifier(dataset);

            Evaluation evaluation = new Evaluation(dataset.getInstances());

            // Eveluating the accuracy of the trained classifier
            evaluation.crossValidateModel(this.classifier, dataset.getInstances(), 10, new Random(1));

            return Double.valueOf(decimalFormat.format(evaluation.pctCorrect()));
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return -1;
        }      
    }
}