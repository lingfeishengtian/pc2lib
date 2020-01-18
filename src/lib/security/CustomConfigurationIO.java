package lib.security;

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

/**
 * A bunch of junk and modified PC2 API to retrieve configuration data.
 */
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
