import edu.csus.ecs.pc2.core.IStorage;
import edu.csus.ecs.pc2.core.Utilities;
import edu.csus.ecs.pc2.core.log.Log;
import edu.csus.ecs.pc2.core.model.*;
import edu.csus.ecs.pc2.core.security.FileSecurityException;
import edu.csus.ecs.pc2.profile.ProfileCloneSettings;
import edu.csus.ecs.pc2.profile.ProfileManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Logger;

public class CustomConfigurationIO {
    public enum ConfigKeys {
        SITE_NUMBER, PROBLEMS, LANGUAGES, CONTEST_TIME, SITES, ACCOUNTS, JUDGEMENTS, PROBLEM_DATA_FILES, GENERAL_PROBLEM, CONTEST_TIME_LIST, CONTEST_INFORMATION, CLIENT_SETTINGS_LIST, BALLOON_SETTINGS_LIST, GROUPS, PROFILE, PROFILES, PROFILE_CLONE_SETTINGS, FINALIZE_DATA, CATEGORIES;
    }

    private IStorage storage = null;

    private LinkedList<String> backupList = new LinkedList<>();

    public CustomConfigurationIO(IStorage storage) {
        this.storage = storage;
    }

    public Object loadFromDisk(int siteNumber, IInternalContest contest, Log log) {
        Configuration configuration = new Configuration(this.storage);
        try {
            if (configuration.loadFromDisk(getFileName())) {
                ConfigKeys key = ConfigKeys.SITE_NUMBER;
                if (configuration.containsKey(key)) {
                    Integer diskSiteNumber = (Integer)configuration.get(key.toString());
                    return configuration.configItemHash;
//                    if (diskSiteNumber.intValue() != siteNumber) {
//                        Exception exception = new Exception("FATAL ERROR Attempted to load site " + siteNumber + " from Site " + diskSiteNumber);
//                        System.err.println(exception.getMessage());
//                        //log.log(Log.SEVERE, exception.getMessage(), exception);
//                        System.exit(22);
//                    }
                    //contest.setSiteNumber(diskSiteNumber.intValue());
                    //log.info("Loading site number " + diskSiteNumber);
                } else {
                    System.err.println("WARNING Attempted to load site " + siteNumber + " but key " + ConfigKeys.SITE_NUMBER + " not found");
                    //log.info("WARNING Attempted to load site " + siteNumber + " but key " + ConfigKeys.SITE_NUMBER + " not found");
                }
                try {
                    key = ConfigKeys.LANGUAGES;
                    if (configuration.containsKey(key)) {
                        Language[] languages = (Language[])configuration.get(key.toString());
                        byte b;
                        int i;
                        Language[] arrayOfLanguage1;
                        for (i = (arrayOfLanguage1 = languages).length, b = 0; b < i; ) {
                            Language language = arrayOfLanguage1[b];
                            //contest.addLanguage(language);
                            b++;
                        }
                        //log.info("Loaded " + languages.length + " " + key.toString().toLowerCase());
                    }
                } catch (Exception e) {
                    //log.log(Log.WARNING, "Exception while loading languages ", e);
                }
                try {
                    key = ConfigKeys.PROBLEMS;
                    if (configuration.containsKey(key)) {
                        Problem[] problems = (Problem[])configuration.get(key.toString());
                        byte b;
                        int i;
                        Problem[] arrayOfProblem1;
                        for (i = (arrayOfProblem1 = problems).length, b = 0; b < i; ) {
                            Problem problem = arrayOfProblem1[b];
                            //contest.addProblem(problem);
                            b++;
                        }
                        //log.info("Loaded " + problems.length + " " + key.toString().toLowerCase());
                    }
                } catch (Exception e) {
                    //log.log(Log.WARNING, "Exception while loading problems ", e);
                }
                try {
                    key = ConfigKeys.CONTEST_TIME_LIST;
                    if (configuration.containsKey(key)) {
                        ContestTime[] contestTimes = (ContestTime[])configuration.get(key.toString());
                        byte b;
                        int i;
                        ContestTime[] arrayOfContestTime1;
                        for (i = (arrayOfContestTime1 = contestTimes).length, b = 0; b < i; ) {
                            ContestTime contestTime = arrayOfContestTime1[b];
                            //contest.addContestTime(contestTime);
                            b++;
                        }
                        //log.info("Loaded " + contestTimes.length + " " + key.toString().toLowerCase());
                    }
                } catch (Exception e) {
                    //log.log(Log.WARNING, "Exception while loading contest times ", e);
                }
                try {
                    key = ConfigKeys.ACCOUNTS;
                    if (configuration.containsKey(key)) {
                        Account[] accounts = (Account[])configuration.get(key.toString());
                        byte b;
                        int i;
                        Account[] arrayOfAccount1;
                        for (i = (arrayOfAccount1 = accounts).length, b = 0; b < i; ) {
                            Account account = arrayOfAccount1[b];
                            contest.addAccount(account);
                            b++;
                        }
                        //log.info("Loaded " + accounts.length + " " + key.toString().toLowerCase());
                    }
                } catch (Exception e) {
                    //log.log(Log.WARNING, "Exception while loading accounts ", e);
                }
                try {
                    key = ConfigKeys.JUDGEMENTS;
                    if (configuration.containsKey(key)) {
                        Judgement[] judgements = (Judgement[])configuration.get(key.toString());
                        byte b;
                        int i;
                        Judgement[] arrayOfJudgement1;
                        for (i = (arrayOfJudgement1 = judgements).length, b = 0; b < i; ) {
                            Judgement judgement = arrayOfJudgement1[b];
                            contest.addJudgement(judgement);
                            b++;
                        }
                        //log.info("Loaded " + judgements.length + " " + key.toString().toLowerCase());
                    }
                } catch (Exception e) {
                    //log.log(Log.WARNING, "Exception while loading judgements ", e);
                }
                //loadProblemDataFilesInfo(contest, configuration, (Logger)log);
                //loadSomeSettings(configuration, contest, (Logger)log);
                return true;
            }
            return false;
        } catch (FileNotFoundException fileNotFoundException) {
            log.info("No configuration file exists " + getFileName());
            return false;
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception Loading configuration from disk, corrupt", e);
            throw new RuntimeException("Error contest config file corrupt: " + getFileName(), e);
        }
    }

    private void loadSomeSettings(Configuration configuration, IInternalContest contest, Logger log) {
        try {
            ConfigKeys key = ConfigKeys.BALLOON_SETTINGS_LIST;
            if (configuration.containsKey(key)) {
                BalloonSettings[] balloonSettings = (BalloonSettings[])configuration.get(key.toString());
                byte b;
                int i;
                BalloonSettings[] arrayOfBalloonSettings1;
                for (i = (arrayOfBalloonSettings1 = balloonSettings).length, b = 0; b < i; ) {
                    BalloonSettings balloonSetting = arrayOfBalloonSettings1[b];
                    contest.addBalloonSettings(balloonSetting);
                    b++;
                }
                log.info("Loaded " + balloonSettings.length + " " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading balloon settings ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.CONTEST_TIME;
            if (configuration.containsKey(key)) {
                ContestTime contestTime = (ContestTime)configuration.get(key.toString());
                contest.updateContestTime(contestTime);
                log.info("Loaded " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading contest time ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.GENERAL_PROBLEM;
            if (configuration.containsKey(key)) {
                Problem genProblem = (Problem)configuration.get(key.toString());
                contest.setGeneralProblem(genProblem);
                log.info("Loaded " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading general problem ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.PROFILES;
            if (configuration.containsKey(key)) {
                Profile[] profiles = (Profile[])configuration.get(key.toString());
                byte b;
                int i;
                Profile[] arrayOfProfile1;
                for (i = (arrayOfProfile1 = profiles).length, b = 0; b < i; ) {
                    Profile profile = arrayOfProfile1[b];
                    contest.addProfile(profile);
                    b++;
                }
                log.info("Loaded " + profiles.length + " " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading profiles ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.FINALIZE_DATA;
            if (configuration.containsKey(key)) {
                FinalizeData finalizeData = (FinalizeData)configuration.get(key.toString());
                contest.setFinalizeData(finalizeData);
                log.info("Loaded " + finalizeData + " " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading Finalize Data ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.CATEGORIES;
            if (configuration.containsKey(key)) {
                Category[] categories = (Category[])configuration.get(key.toString());
                byte b;
                int i;
                Category[] arrayOfCategory1;
                for (i = (arrayOfCategory1 = categories).length, b = 0; b < i; ) {
                    Category category = arrayOfCategory1[b];
                    contest.addCategory(category);
                    b++;
                }
                log.info("Loaded " + categories.length + " " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading judgements ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.PROFILE;
            if (configuration.containsKey(key)) {
                Profile profile = (Profile)configuration.get(key.toString());
                contest.setProfile(profile);
                log.info("Loaded " + key.toString().toLowerCase());
            } else {
                Profile profile = ProfileManager.createNewProfile();
                contest.setProfile(profile);
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading current profile ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.PROFILE_CLONE_SETTINGS;
            if (configuration.containsKey(key)) {
                ProfileCloneSettings profileCloneSettings = (ProfileCloneSettings)configuration.get(key.toString());
                contest.setProfileCloneSettings(profileCloneSettings);
                log.info("Loaded " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading profile clone settings ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.SITES;
            if (configuration.containsKey(key)) {
                Site[] sites = (Site[])configuration.get(key.toString());
                byte b;
                int i;
                Site[] arrayOfSite1;
                for (i = (arrayOfSite1 = sites).length, b = 0; b < i; ) {
                    Site site = arrayOfSite1[b];
                    contest.addSite(site);
                    b++;
                }
                log.info("Loaded " + sites.length + " " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading sites ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.CONTEST_INFORMATION;
            if (configuration.containsKey(key)) {
                ContestInformation contestInformation = (ContestInformation)configuration.get(key.toString());
                contest.addContestInformation(contestInformation);
                log.info("Loaded Contest Information " + contestInformation.getContestTitle());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading contest information/title ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.CLIENT_SETTINGS_LIST;
            if (configuration.containsKey(key)) {
                ClientSettings[] clientSettingsList = (ClientSettings[])configuration.get(key.toString());
                byte b;
                int i;
                ClientSettings[] arrayOfClientSettings1;
                for (i = (arrayOfClientSettings1 = clientSettingsList).length, b = 0; b < i; ) {
                    ClientSettings clientSettings = arrayOfClientSettings1[b];
                    contest.addClientSettings(clientSettings);
                    b++;
                }
                log.info("Loaded " + clientSettingsList.length + " " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while updating client settings ", e);
        }
        try {
            ConfigKeys key = ConfigKeys.GROUPS;
            if (configuration.containsKey(key)) {
                Group[] groups = (Group[])configuration.get(key.toString());
                byte b;
                int i;
                Group[] arrayOfGroup1;
                for (i = (arrayOfGroup1 = groups).length, b = 0; b < i; ) {
                    Group group = arrayOfGroup1[b];
                    contest.addGroup(group);
                    b++;
                }
                log.info("Loaded " + groups.length + " " + key.toString().toLowerCase());
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading groups ", e);
        }
    }

    private void loadProblemDataFilesInfo(IInternalContest contest, Configuration configuration, Logger log) {
        try {
            ConfigKeys key = ConfigKeys.PROBLEM_DATA_FILES;
            if (configuration.containsKey(key)) {
                ProblemDataFiles[] problemDataFiles = (ProblemDataFiles[])configuration.get(key.toString());
                int count = 0;
                byte b;
                int i;
                ProblemDataFiles[] arrayOfProblemDataFiles1;
                for (i = (arrayOfProblemDataFiles1 = problemDataFiles).length, b = 0; b < i; ) {
                    ProblemDataFiles problemDataFiles2 = arrayOfProblemDataFiles1[b];
                    Problem problem = contest.getProblem(problemDataFiles2.getProblemId());
                    if (problem != null) {
                        contest.updateProblem(problem, problemDataFiles2);
                        count++;
                    } else {
                        log.warning("Could not find problem for problemDataFiles problem id=" + problemDataFiles2.getProblemId());
                    }
                    log.info("Loaded " + count + " of " + problemDataFiles.length + " " + key.toString().toLowerCase());
                    b++;
                }
            }
        } catch (Exception e) {
            log.log(Log.WARNING, "Exception while loading problem data files ", e);
        }
    }

    private Account[] getAllAccounts(IInternalContest contest) {
        Vector<Account> allAccounts = new Vector<>();
        byte b;
        int i;
        ClientType.Type[] arrayOfType;
        for (i = (arrayOfType = ClientType.Type.values()).length, b = 0; b < i; ) {
            ClientType.Type ctype = arrayOfType[b];
            if (contest.getAccounts(ctype).size() > 0) {
                Vector<Account> vector = contest.getAccounts(ctype);
                allAccounts.addAll(vector);
            }
            b++;
        }
        Account[] accounts = allAccounts.<Account>toArray(new Account[allAccounts.size()]);
        return accounts;
    }

    public synchronized boolean store(IInternalContest contest, Log log) throws IOException, ClassNotFoundException, FileSecurityException {
        Configuration configuration = new Configuration(this.storage);
        configuration.add(ConfigKeys.SITE_NUMBER, new Integer(contest.getSiteNumber()));
        configuration.add(ConfigKeys.ACCOUNTS, (Serializable)getAllAccounts(contest));
        configuration.add(ConfigKeys.CONTEST_TIME, contest.getContestTime());
        configuration.add(ConfigKeys.CONTEST_TIME_LIST, (Serializable)contest.getContestTimes());
        configuration.add(ConfigKeys.BALLOON_SETTINGS_LIST, (Serializable)contest.getBalloonSettings());
        if (contest.getGeneralProblem() != null)
            configuration.add(ConfigKeys.GENERAL_PROBLEM, contest.getGeneralProblem());
        configuration.add(ConfigKeys.PROBLEM_DATA_FILES, (Serializable)contest.getProblemDataFiles());
        configuration.add(ConfigKeys.JUDGEMENTS, (Serializable)contest.getJudgements());
        configuration.add(ConfigKeys.LANGUAGES, (Serializable)contest.getLanguages());
        configuration.add(ConfigKeys.PROBLEMS, (Serializable)contest.getProblems());
        configuration.add(ConfigKeys.SITES, (Serializable)contest.getSites());
        configuration.add(ConfigKeys.CONTEST_INFORMATION, contest.getContestInformation());
        configuration.add(ConfigKeys.CLIENT_SETTINGS_LIST, (Serializable)contest.getClientSettingsList());
        configuration.add(ConfigKeys.GROUPS, (Serializable)contest.getGroups());
        configuration.add(ConfigKeys.PROFILES, (Serializable)contest.getProfiles());
        configuration.add(ConfigKeys.PROFILE, contest.getProfile());
        if (contest.getFinalizeData() != null)
            configuration.add(ConfigKeys.FINALIZE_DATA, contest.getFinalizeData());
        if (contest.getCategories() != null)
            configuration.add(ConfigKeys.CATEGORIES, (Serializable)contest.getCategories());
        if (contest.getProfileCloneSettings() != null)
            configuration.add(ConfigKeys.PROFILE_CLONE_SETTINGS, (Serializable)contest.getProfileCloneSettings());
        configuration.writeToDisk(getFileName());
        String backupFilename = getBackupFilename();
        configuration.writeToDisk(backupFilename);
        this.backupList.add(backupFilename);
        while (this.backupList.size() > 100) {
            String removeBackupFile = this.backupList.removeFirst();
            File file = new File(removeBackupFile);
            if (file.exists())
                file.delete();
        }
        configuration = null;
        return true;
    }

    public static class Configuration {
        private Hashtable<String, Object> configItemHash = new Hashtable<>();

        private IStorage storage;

        public Configuration(IStorage storage) {
            this.storage = storage;
        }

        public boolean add(CustomConfigurationIO.ConfigKeys key, Serializable object) {
            if (object == null)
                return false;
            this.configItemHash.put(key.toString(), object);
            return true;
        }

        public boolean containsKey(CustomConfigurationIO.ConfigKeys key) {
            return this.configItemHash.containsKey(key.toString());
        }

        public Object get(String key) {
            return this.configItemHash.get(key);
        }

        public synchronized boolean writeToDisk(String fileName) throws IOException, ClassNotFoundException, FileSecurityException {
            return this.storage.store(fileName, this.configItemHash);
        }

        public synchronized boolean loadFromDisk(String filename) throws IOException, ClassNotFoundException, FileSecurityException {
            Object readObject = this.storage.load(filename);
            System.out.println(readObject);
            if (readObject instanceof Hashtable) {
                this.configItemHash = (Hashtable<String, Object>)readObject;
                return true;
            }
            return false;
        }
    }

    public String getFileName() {
        return String.valueOf(this.storage.getDirectoryName()) + File.separator + "settings.dat";
    }

    public String getBackupFilename() {
        return String.valueOf(this.storage.getDirectoryName()) + File.separator + "settings." + Utilities.getDateTime() + "." + System.nanoTime() + ".dat";
    }
}
