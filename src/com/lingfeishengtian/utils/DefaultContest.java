package com.lingfeishengtian.utils;

import edu.csus.ecs.pc2.core.exception.ProfileCloneException;
import edu.csus.ecs.pc2.core.model.*;
import edu.csus.ecs.pc2.core.security.FileSecurityException;
import edu.csus.ecs.pc2.profile.ProfileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Properties;

public class DefaultContest {
    public final static String defaultProfileName = "P2d518d00-7dab-418f-88d5-b613f214fae7";
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
        Account[] accounts = (Account[]) config.get("ACCOUNTS");

        Account[] judges = new Account[0];
        for (Account a : accounts) {
            if(a.getClientId().getClientType().equals(ClientType.Type.JUDGE)){
                Account[] tmp = judges;
                judges = new Account[judges.length + 1];
                for (int i = 0; i < tmp.length; i++) {
                    judges[i] = tmp[i];
                }
                judges[tmp.length] = a;
            }
        }

        ClientSettings[] clientSettings = new ClientSettings[0];
        Problem[] problems = (Problem[]) config.get("PROBLEMS");
        for (Account acc : judges){
            ClientSettings c = new ClientSettings(acc.getClientId());
            c.setAutoJudging(true);
            Filter filter = c.getAutoJudgeFilter();
            filter.setFilterOn();
            filter.setUsingProblemFilter(true);
            if (filter.isFilteringProblems()){
                for (Problem p : problems) {
                    filter.addProblem(p);
                }
            }

            ClientSettings[] tmp = clientSettings;
            clientSettings = new ClientSettings[clientSettings.length + 1];
            for (int i = 0; i < tmp.length; i++) {
                clientSettings[i] = tmp[i];
            }
            clientSettings[tmp.length] = c;
        }
        config.put("CLIENT_SETTINGS_LIST", clientSettings);

        // Sets contest time
        ContestTime contestTime = (ContestTime) config.get("CONTEST_TIME");
        contestTime.resetClock();
        contestTime.setContestLengthSecs(7200);
    }

    public static void setupDefaultContestWithPasscode(String passcode, File contestRootDir) throws FileSecurityException, ProfileCloneException, FileNotFoundException {
        ProfileManager manager = new ProfileManager();
        Profile profile = ProfileManager.createNewProfile();
        profile.setSiteNumber(1);
        char sep = File.separatorChar;
        profile.setProfilePath(profile.createProfilePath(contestRootDir.getAbsolutePath() + sep + "bin" + sep));
        manager.createProfilesPathandFiles(profile, 1, passcode);
        System.out.println(profile.getDescription());
          PrintWriter write = new PrintWriter(contestRootDir.getAbsolutePath() + sep + "bin" + sep + "profiles.properties");
          write.println(profile.getContestId() + "=\"Default\",\"Default Contest\",\"profiles/" + new File(profile.getProfilePath()).getName() + "\",active\\=true,\n" +
                  "current="+ profile.getContestId() + "\n");
          write.close();
//        Hashtable config = new Hashtable();
//        Account[] accounts = new Account[7];
//        for (int i = 0; i < 5; i++) {
//            accounts[i] = new Account(new ClientId(1, ClientType.Type.JUDGE, i+1), "judge" + (i + 1), 1);
//        }
//        accounts[5] = new Account(new ClientId(1, ClientType.Type.ADMINISTRATOR, 1), "administrator" + 1, 1);
//
//        Site site1 = new Site("Site 1", 1);
//        site1.setPassword("site1");
//        site1.setActive(true);
//
//        SiteList sites = new SiteList();
//        sites.add(site1);
//
//        config.put(ConfigurationIO.ConfigKeys.SITE_NUMBER, "1");
//        config.put(ConfigurationIO.ConfigKeys.ACCOUNTS, accounts);
//        config.put(ConfigurationIO.ConfigKeys.CONTEST_TIME, new ContestTime());
//        config.put(ConfigurationIO.ConfigKeys.CONTEST_TIME_LIST, new ContestTime[0]);
//        config.put(ConfigurationIO.ConfigKeys.BALLOON_SETTINGS_LIST, new BalloonSettings[0]);
//        config.put(ConfigurationIO.ConfigKeys.PROBLEM_DATA_FILES, new ProblemDataFiles[0]);
//        config.put(ConfigurationIO.ConfigKeys.JUDGEMENTS, new Judgement[0]);
//        config.put(ConfigurationIO.ConfigKeys.LANGUAGES, new Language[0]);
//        config.put(ConfigurationIO.ConfigKeys.PROBLEMS, new Problem[0]);
//        config.put(ConfigurationIO.ConfigKeys.SITES, sites);
//        config.put(ConfigurationIO.ConfigKeys.CONTEST_INFORMATION, new ContestInformation());
//        config.put(ConfigurationIO.ConfigKeys.CLIENT_SETTINGS_LIST, new ClientSettings[0]);
//        config.put(ConfigurationIO.ConfigKeys.GROUPS, new Group[0]);
//        config.put(ConfigurationIO.ConfigKeys.PROFILES, new Profile[]{profile});
//        config.put(ConfigurationIO.ConfigKeys.PROFILE, profile);
//
//        return config;
    }
}
