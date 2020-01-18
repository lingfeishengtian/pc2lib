package lib.security;

import edu.csus.ecs.pc2.core.security.FileSecurity;

import java.io.Serializable;
import java.util.Hashtable;

public class Extractor {
    private String folderDirectory;

    public Extractor(String db1Dir){
        if (db1Dir.endsWith("/"))
            folderDirectory = db1Dir.substring(0, db1Dir.length() - 1);
        else
            folderDirectory = db1Dir;
    }

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

    public Hashtable getConfigHashTable(FileSecurity secure){
        try{
            CustomConfigurationIO configIO = new CustomConfigurationIO(secure);
            return (Hashtable) configIO.loadFromDisk(1, null, null);
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void writeConfigurationToDisk(Hashtable config, FileSecurity secure) {
        try {
            CustomConfigurationIO.Configuration configuration = new CustomConfigurationIO.Configuration(secure);
            configuration.add(CustomConfigurationIO.ConfigKeys.SITE_NUMBER, (Serializable) config.get("SITE_NUMBER"));
            configuration.add(CustomConfigurationIO.ConfigKeys.ACCOUNTS, (Serializable) config.get("ACCOUNTS"));
            configuration.add(CustomConfigurationIO.ConfigKeys.CONTEST_TIME, (Serializable) config.get("CONTEST_TIME"));
            configuration.add(CustomConfigurationIO.ConfigKeys.CONTEST_TIME_LIST, (Serializable) config.get("CONTEST_TIME_LIST"));
            configuration.add(CustomConfigurationIO.ConfigKeys.BALLOON_SETTINGS_LIST, (Serializable) config.get("BALLOON_SETTINGS_LIST"));
            if (config.get("GENERAL_PROBLEM") != null)
                configuration.add(CustomConfigurationIO.ConfigKeys.GENERAL_PROBLEM, (Serializable) config.get("GENERAL_PROBLEM"));
            configuration.add(CustomConfigurationIO.ConfigKeys.PROBLEM_DATA_FILES, (Serializable) config.get("PROBLEM_DATA_FILES"));
            configuration.add(CustomConfigurationIO.ConfigKeys.JUDGEMENTS, (Serializable) config.get("JUDGEMENTS"));
            configuration.add(CustomConfigurationIO.ConfigKeys.LANGUAGES, (Serializable) config.get("LANGUAGES"));
            configuration.add(CustomConfigurationIO.ConfigKeys.PROBLEMS, (Serializable) config.get("PROBLEMS"));
            configuration.add(CustomConfigurationIO.ConfigKeys.SITES, (Serializable) config.get("SITES"));
            configuration.add(CustomConfigurationIO.ConfigKeys.CONTEST_INFORMATION, (Serializable) config.get("CONTEST_INFORMATION"));
            configuration.add(CustomConfigurationIO.ConfigKeys.CLIENT_SETTINGS_LIST, (Serializable) config.get("CLIENT_SETTINGS_LIST"));
            configuration.add(CustomConfigurationIO.ConfigKeys.GROUPS, (Serializable) config.get("GROUPS"));
            configuration.add(CustomConfigurationIO.ConfigKeys.PROFILES, (Serializable) config.get("PROFILES"));
            configuration.add(CustomConfigurationIO.ConfigKeys.PROFILE, (Serializable) config.get("PROFILE"));
            if (config.get("FINALIZE_DATA") != null)
                configuration.add(CustomConfigurationIO.ConfigKeys.FINALIZE_DATA, (Serializable) config.get("FINALIZE_DATA"));
            if (config.get("CATEGORIES") != null)
                configuration.add(CustomConfigurationIO.ConfigKeys.CATEGORIES, (Serializable) config.get("CATEGORIES"));
            if (config.get("PROFILE_CLONE_SETTINGS") != null)
                configuration.add(CustomConfigurationIO.ConfigKeys.PROFILE_CLONE_SETTINGS, (Serializable) config.get("PROFILE_CLONE_SETTINGS"));
            configuration.writeToDisk(folderDirectory + "/settings.dat");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
