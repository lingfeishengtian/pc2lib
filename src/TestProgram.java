import edu.csus.ecs.pc2.core.model.Problem;
import edu.csus.ecs.pc2.core.model.ProblemDataFiles;
import edu.csus.ecs.pc2.core.model.SerializedFile;
import edu.csus.ecs.pc2.core.security.FileSecurity;
import edu.csus.ecs.pc2.core.security.FileSecurityException;
import lib.security.Extractor;
import lib.utils.PC2AutoRun;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class TestProgram {
    // Test Data
    /*
    REQUIRED VM OPTION
    -Djdk.crypto.KeyAgreement.legacyKDF=true
     */
    public static void main(String[] args) throws FileSecurityException, IOException, ClassNotFoundException {
        Extractor extractor = new Extractor("exampleDir/bin/profiles/P62d231b8-4de3-4ad6-80ee-2c1e04418419/db.1");
        FileSecurity secure = extractor.getFileSecurity("NONO");
        Hashtable config = extractor.getConfigHashTable(secure);

        // Edit Problem Data
        ((Problem[]) config.get("PROBLEMS"))[1].addTestCaseFilenames(new File("TestData/playtime1.in").getAbsolutePath(), new File("TestData/playtime1.out").getAbsolutePath());
        //((Problem[]) config.get("PROBLEMS"))[3].setDisplayName("neue");
        Problem[] aea = new Problem[((Problem[]) config.get("PROBLEMS")).length + 1];
        for (int i = 0; i < ((Problem[]) config.get("PROBLEMS")).length; i++){
            aea[i] = ((Problem[]) config.get("PROBLEMS"))[i];
        }

        Problem testProblem = new Problem("NEWTEST");
        testProblem.setComputerJudged(true);
        testProblem.setDataFileName("playtime1.in");
        testProblem.setAnswerFileName("playtime1.out");
        testProblem.setValidatorType(Problem.VALIDATOR_TYPE.PC2VALIDATOR);
        testProblem.setShowCompareWindow(true);
        testProblem.setShowValidationToJudges(true);
        testProblem.setManualReview(true);
        //testProblem.setLetter("");
        aea[aea.length - 1] = testProblem;

        ProblemDataFiles[] aa = new ProblemDataFiles[((ProblemDataFiles[]) config.get("PROBLEM_DATA_FILES")).length + 1];
        for (int i = 0; i < ((ProblemDataFiles[]) config.get("PROBLEM_DATA_FILES")).length; i++){
            aa[i] = ((ProblemDataFiles[]) config.get("PROBLEM_DATA_FILES"))[i];
        }
        ProblemDataFiles bb = new ProblemDataFiles(testProblem);
        aa[aa.length - 1] = bb;
        bb.setJudgesAnswerFile(new SerializedFile("TestData/playtime1.out", Integer.MAX_VALUE));
        bb.setJudgesDataFile(new SerializedFile("TestData/playtime1.in", Integer.MAX_VALUE));

        for (ProblemDataFiles a :
                aa){
            try {
                System.out.println(new String(a.getJudgesDataFile().getBuffer()));
            }catch (Exception e){
                System.out.println("PRObably ROoK");
            }
        }
        for (Problem a :
                aea){
            System.out.println(a.toStringDetails());
        }

        config.put("PROBLEMS", aea);
        config.put("PROBLEM_DATA_FILES", aa);

        extractor.writeConfigurationToDisk(config, secure);
        //PC2AutoRun.runPC2AdminSuite("site1", "site1", "NONO", new File(new File("exampleDir/bin").getAbsolutePath()));
    }
}
