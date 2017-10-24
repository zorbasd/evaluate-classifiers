package com.classification;

import java.util.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Dataset {
    /*
    * The schema of the dataset.
    */
    private String[] shcema;

    /*
    * The data source of the real dataset.
    */   
    private Instances data;

    /*
    * The dataset constructor.
    */     
    public Dataset(String[] schema, String path) {
        try {
            DataSource source = new DataSource(path);
            
            this.data   = source.getDataSet();
            this.shcema = schema;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
    }

    /*
    * getSchema returns the schema of the dataset.
    */    
    public String[] getSchema() {
        return this.shcema;
    }

    /*
    * getInstances returns the data instances of the dataset.
    */
    public Instances getInstances() {
        return this.data;
    }

    /*
    * setClassAttribute sets the class attribute of the dataset.
    */
    public void setClassAttribute(String attribute) {
        this.data.setClass(this.data.attribute(attribute));
    }
}