import edu.csus.ecs.pc2.core.model.Problem;
import edu.csus.ecs.pc2.core.model.ProblemDataFiles;
import edu.csus.ecs.pc2.core.security.FileSecurity;
import edu.csus.ecs.pc2.core.security.FileSecurityException;
import edu.csus.ecs.pc2.core.security.FileStorage;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class Extractor {
    public static Hashtable getConfigHashTable(String pathToDb1, String contestPasscode){
        try{
            FileSecurity secure = new FileSecurity(pathToDb1);
            secure.verifyPassword(contestPasscode.toCharArray());
            CustomConfigurationIO configIO = new CustomConfigurationIO(secure);
            return (Hashtable) configIO.loadFromDisk(1, null, null);
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static void writeConfigurationToDisk(Hashtable config, FileSecurity secure) throws FileSecurityException, IOException, ClassNotFoundException {
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
        configuration.writeToDisk("profiles/P62d231b8-4de3-4ad6-80ee-2c1e04418419/db.1/settings.dat");
    }
}
