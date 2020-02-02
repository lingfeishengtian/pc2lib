package com.lingfeishengtian.security;

import edu.csus.ecs.pc2.core.model.ConfigurationIO;
import edu.csus.ecs.pc2.core.security.FileSecurity;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Global extractor tool to extract the configuration. You should only instantiate one of these and use it throughout your whole project.
 */
public class Extractor {
    /**
     * The directory of the db1 folder in your pc2 profiles folder
     */
    private String folderDirectory;

    /**
     * Instantiates and automatically removes an ending / if one is present in the db.1 directory of your profiles folder
     * @param db1Dir
     */
    public Extractor(String db1Dir){
        if (db1Dir.endsWith("/"))
            folderDirectory = db1Dir.substring(0, db1Dir.length() - 1);
        else
            folderDirectory = db1Dir;
    }

    /**
     * Retrieves deserialization keys and important information to unlock the configuration files.
     * @param contestPasscode The contest passcode set.
     * @return A FileSecurity that is used throughout the API. EXTREMELY important and is necessary to unlock many files.
     */
    public FileSecurity getFileSecurity(String contestPasscode){
        try {
            FileSecurity secure = new FileSecurity(folderDirectory);
            secure.verifyPassword(contestPasscode.toCharArray());
            return secure;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    /**
     * Retrieves the hash table version of the configuration table
     * @param secure The FileSecurity object retrieved
     * @return Configuration in hash table form
     */
    public Hashtable getConfigHashTable(FileSecurity secure){
        try{
            CustomConfigurationIO configIO = new CustomConfigurationIO(secure);
            return (Hashtable) configIO.loadFromDisk(1, null, null);
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    /**
     * Re-serializes and writes the configuration back onto the PC2 profiles folder
     * @param config Configuration hashtable you want to save
     * @param secure The FileSecurity object retrieved
     */
    public void writeConfigurationToDisk(Hashtable config, FileSecurity secure) {
        try {
            CustomConfigurationIO.Configuration configuration = new CustomConfigurationIO.Configuration(secure);
            configuration.add("SITE_NUMBER", (Serializable) config.get("SITE_NUMBER"));
            configuration.add("ACCOUNTS", (Serializable) config.get("ACCOUNTS"));
            configuration.add("CONTEST_TIME", (Serializable) config.get("CONTEST_TIME"));
            configuration.add("CONTEST_TIME_LIST", (Serializable) config.get("CONTEST_TIME_LIST"));
            configuration.add("BALLOON_SETTINGS_LIST", (Serializable) config.get("BALLOON_SETTINGS_LIST"));
            if (config.get("GENERAL_PROBLEM") != null)
                configuration.add("GENERAL_PROBLEM", (Serializable) config.get("GENERAL_PROBLEM"));
            configuration.add("PROBLEM_DATA_FILES", (Serializable) config.get("PROBLEM_DATA_FILES"));
            configuration.add("JUDGEMENTS", (Serializable) config.get("JUDGEMENTS"));
            configuration.add("LANGUAGES", (Serializable) config.get("LANGUAGES"));
            configuration.add("PROBLEMS", (Serializable) config.get("PROBLEMS"));
            configuration.add("SITES", (Serializable) config.get("SITES"));
            configuration.add("CONTEST_INFORMATION", (Serializable) config.get("CONTEST_INFORMATION"));
            configuration.add("CLIENT_SETTINGS_LIST", (Serializable) config.get("CLIENT_SETTINGS_LIST"));
            configuration.add("GROUPS", (Serializable) config.get("GROUPS"));
            configuration.add("PROFILES", (Serializable) config.get("PROFILES"));
            configuration.add("PROFILE", (Serializable) config.get("PROFILE"));
            if (config.get("FINALIZE_DATA") != null)
                configuration.add("FINALIZE_DATA", (Serializable) config.get("FINALIZE_DATA"));
            if (config.get("CATEGORIES") != null)
                configuration.add("CATEGORIES", (Serializable) config.get("CATEGORIES"));
            if (config.get("PROFILE_CLONE_SETTINGS") != null)
                configuration.add("PROFILE_CLONE_SETTINGS", (Serializable) config.get("PROFILE_CLONE_SETTINGS"));
            configuration.writeToDisk(folderDirectory + "/settings.dat");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}