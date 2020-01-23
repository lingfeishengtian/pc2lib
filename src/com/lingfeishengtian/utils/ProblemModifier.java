package com.lingfeishengtian.utils;

import edu.csus.ecs.pc2.core.model.Problem;
import edu.csus.ecs.pc2.core.model.ProblemDataFiles;
import edu.csus.ecs.pc2.core.model.SerializedFile;

import java.io.File;
import java.util.Arrays;
import java.util.Hashtable;

public class ProblemModifier {
    /**
     * Adds a default problem that uses PC2 Validator, is active, gives judge full permissions to view problem comparison(because PC2 Validator is EXTREMELY unreliable)
     * THIS DOES NOT WRITE CHANGES TO THE DISK THOUGH!!!
     *
     * @param problemName Problem display name. This is required because it is also used to generate the problem ID.
     * @param inputFile The input data. If you do not have input data, then set this parameter to null.
     * @param outputFile The output data, this is required!!!!
     * @param config The hashtable configuration retrieved from security.
     */
    public static void addProblemWithDefaultSettings(String problemName, File inputFile, File outputFile, Hashtable config){
        if((inputFile == null || inputFile.isFile()) && outputFile.isFile()) {
            Problem problem = new Problem(problemName);
            problem.setComputerJudged(true);
            if (inputFile != null)
                problem.setDataFileName(inputFile.getName());
            problem.setAnswerFileName(outputFile.getName());
            problem.setValidatorType(Problem.VALIDATOR_TYPE.PC2VALIDATOR);
            problem.setShowCompareWindow(true);
            problem.setShowValidationToJudges(true);
            problem.setManualReview(true);

            ProblemDataFiles problemDataFiles = new ProblemDataFiles(problem);
            if (inputFile != null)
                problemDataFiles.setJudgesDataFile(new SerializedFile(inputFile.getAbsolutePath()));
            problemDataFiles.setJudgesAnswerFile(new SerializedFile(outputFile.getAbsolutePath()));

            Problem[] problemArray = (Problem[]) config.get("PROBLEMS");
            ProblemDataFiles[] problemDataArray = (ProblemDataFiles[]) config.get("PROBLEM_DATA_FILES");

            Object[] a = addObjectToArray(problemArray, problem);
            Object[] b = addObjectToArray(problemDataArray, problemDataFiles);

            Problem[] c = Arrays.copyOf(a, a.length, Problem[].class);
            ProblemDataFiles[] d = Arrays.copyOf(b, b.length, ProblemDataFiles[].class);
            config.put("PROBLEMS", c);
            config.put("PROBLEM_DATA_FILES", d);
        }else{
            System.out.println("The files given are invalid files.");
            return;
        }
    }

    private static Object[] addObjectToArray(Object[] arr, Object a){
        Object[] obj = new Object[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            obj[i] = arr[i];
        }
        obj[obj.length - 1] = a;
        return obj;
    }

    // Just for funsies
    private static Object[] removeObjectFromArray(Object[] arr, int a){
        Object[] obj = new Object[arr.length - 1];
        int tmpInd = obj.length - 1;
        for (int i = arr.length - 1; i >= 0; i--) {
            if(i != a)
                obj[tmpInd--] = arr[i];
        }
        return obj;
    }
}
