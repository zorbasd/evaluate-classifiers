package com.classification;

import java.io.*;
import java.util.*;
import com.classification.*;
import java.text.DecimalFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 * 
 *
 */
public class Experiment 
{
    /*
    * A list of all classifiers to be experimented with.
    */
    private String[] classifiers = new String[] {
        "BFTree", "DecisionStump", "FT", "J48", "J48graft", "LADTree", "LMT", 
        "NBTree", "RandomForest", "RandomTree", "REPTree", "SimpleCart", "NaiveBayes"
    };

    /*
    * A csv header for the experiment output
    */
    public String[] CSVHeader = new String[] {
        "Size/Algorithm",  "BFTree", "DecisionStump", "FT", "J48", "J48graft", "LADTree", "LMT", 
         "NBTree", "RandomForest", "RandomTree", "REPTree", "SimpleCart", "NaiveBayes"
    };

    /*
    * directory is the data directory that will be used during the experiment.
    */    
    private String directory;

    /*
    * schema is the data schema that will be used during the experiment.
    * ATTENTION: all datasets in the data directory must this exact schema
    */    
    private String[] schema;

    /*
    * Experiment constructs the experiment.
    */    
    public Experiment(String directory, String[] schema) {
        this.directory = directory;
        this.schema    = schema;
    }

    /*
    * getBufferedFileWriter takes a full path (string) for a specific file, and returns a BufferedWriter object for that file.
    */
    private BufferedWriter getBufferedFileWriter(String filePath) throws IOException{       
        File file = new File(filePath);
        
        // if file doesnt exists, then create it.
        if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
        }
            
        FileWriter     fileWriter     = new FileWriter( file.getAbsoluteFile() );
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
         
        return bufferedWriter;
    }

    /*
    * run runs the experiment.
    */
    public void run() {
        try {
            ClassificationEngine engin         = new ClassificationEngine("");
            File                 directory     = new File(this.directory);
            CSVFormat            csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
            List                 csvLine       = new ArrayList();
            FileFilter           fileFilter    = new WildcardFileFilter("*.arff");
    
            csvFileFormat.DEFAULT.withDelimiter(';');
            
            if (!directory.exists()) {
                return;
            }
    
            for ( String attribute : this.schema ) {
                String         path           = this.directory + "/experimentalResults/" + attribute  + ".csv";
                BufferedWriter csvFileWriter  = this.getBufferedFileWriter(path);
                CSVPrinter     csvFilePrinter = new CSVPrinter(csvFileWriter, csvFileFormat);
    
                csvFilePrinter.printRecord(CSVHeader);
    
                for (File file : directory.listFiles(fileFilter)) {
                    csvLine.add(FilenameUtils.removeExtension(file.getName()));

                    Dataset dataset = new Dataset(this.schema, file.getAbsolutePath());
                    dataset.setClassAttribute(attribute);

                    for (String classifier : this.classifiers) {
                        engin.setClassifier(classifier);
                        csvLine.add(engin.evaluateClassifier(dataset));  
                    }

                    csvFilePrinter.printRecord(csvLine);
                    csvLine.clear();
                }
    
                csvLine.clear();
                csvFileWriter.flush();
                csvFileWriter.close();
                csvFilePrinter.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
