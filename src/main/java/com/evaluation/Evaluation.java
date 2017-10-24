package com.evaluation;

import java.io.*;
import com.classification.*;

/**
 * 
 *
 */
public class Evaluation 
{
    public static void main( String[] args )
    {
        System.out.println( "Experiments are in progress..." );
        
        String[] simulatedDatasetSchema = new String[] {
            "age-visibility", "gender-visibility", "university-visibility", "country-visibility", 
            "current-city-visibility", "interests-visibility", "language-visibility", "relationship-status-visibility"
        };

        Experiment simulatedDataExperiment = new Experiment("/Users/ammar/Downloads/simulated", simulatedDatasetSchema);
        simulatedDataExperiment.run();

        String[] realDatasetSchema = new String[] {
            "ProfilePicPrivacy", "NamePrivacy", "EmailPrivacy", "GenderPrivacy", "DOBPrivacy", "LocationPrivacy", 
            "EducationPrivacy", "RelationShipPrivacy", "languagePrivacy", "ReligionPrivacy", "MobilePrivacy"
        };

        Experiment realDataExperiment = new Experiment("/Users/ammar/Downloads/real", realDatasetSchema);
        realDataExperiment.run();

        System.out.println( "Experiments are done!" );
    }
}
