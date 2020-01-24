package com.lingfeishengtian.utils;

import edu.csus.ecs.pc2.core.model.*;

import java.util.Hashtable;
import java.util.Properties;

public class DefaultContest {
    /**
     * Modifies the contest to have specific properties tailored toward standard UIL competitions. These include
     * - 60 points for yes
     * - -5 points for no
     * - resetting the competition time
     * - 120 minutes
     * - Sets auto-judge to all judges
     * @param config
     */
    public static void modifyContestToDefaultSettings(Hashtable config){
        // Sets points for competition
        ContestInformation contestInformation = ((ContestInformation) config.get("CONTEST_INFORMATION"));
        Properties prop = contestInformation.getScoringProperties();
        prop.setProperty("Base Points per Yes", "60");
        prop.setProperty("Points per No", "-5");
        prop.setProperty("Points per Minute (for 1st yes)", "0");

        // Sets all judges to be in auto judge :)
        ClientSettings[] client_settings = (ClientSettings[]) config.get("CLIENT_SETTINGS_LIST");
        Problem[] problems = (Problem[]) config.get("PROBLEMS");
        for (ClientSettings c :
                client_settings) {
            Filter filter = c.getAutoJudgeFilter();
            if (filter.isFilteringProblems()){
                for (Problem p :
                        problems) {
                    filter.addProblem(p);
                }
            }
        }

        // Sets contest time
        ContestTime contestTime = (ContestTime) config.get("CONTEST_TIME");
        contestTime.resetClock();
        contestTime.setContestLengthSecs(7200);
    }
}
