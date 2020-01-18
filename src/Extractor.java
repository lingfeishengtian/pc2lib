import edu.csus.ecs.pc2.core.model.Problem;
import edu.csus.ecs.pc2.core.model.ProblemDataFiles;
import edu.csus.ecs.pc2.core.security.FileSecurity;
import edu.csus.ecs.pc2.core.security.FileSecurityException;
import edu.csus.ecs.pc2.core.security.FileStorage;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class Extractor {
    public static void main(String[] args) throws FileSecurityException, IOException, ClassNotFoundException {
        FileSecurity secure = new FileSecurity("profiles/P62d231b8-4de3-4ad6-80ee-2c1e04418419/db.1");
        System.out.println(secure.verifyPassword("xd".toCharArray()));
        FileStorage storage = new FileStorage();
        ConfigurationIO configIO = new ConfigurationIO(secure);
        Hashtable config = (Hashtable) configIO.loadFromDisk(1, null, null);

        // Edit Problem Data
        System.out.println(((Problem[]) config.get("PROBLEMS"))[1].getDataFileName());
        ((Problem[]) config.get("PROBLEMS"))[0].setDisplayName("PEEPOO");
        Problem[] aea = new Problem[((Problem[]) config.get("PROBLEMS")).length];

        // Data File Modification
        System.out.println(new String(((ProblemDataFiles[]) config.get("PROBLEM_DATA_FILES"))[0].getJudgesAnswerFile().getBuffer()));

        writeConfigurationToDisk(config, secure);
    }

    public static void writeConfigurationToDisk(Hashtable config, FileSecurity secure) throws FileSecurityException, IOException, ClassNotFoundException {
        ConfigurationIO.Configuration configuration = new ConfigurationIO.Configuration(secure);
        configuration.add(ConfigurationIO.ConfigKeys.SITE_NUMBER, (Serializable) config.get("SITE_NUMBER"));
        configuration.add(ConfigurationIO.ConfigKeys.ACCOUNTS, (Serializable) config.get("ACCOUNTS"));
        configuration.add(ConfigurationIO.ConfigKeys.CONTEST_TIME, (Serializable) config.get("CONTEST_TIME"));
        configuration.add(ConfigurationIO.ConfigKeys.CONTEST_TIME_LIST, (Serializable) config.get("CONTEST_TIME_LIST"));
        configuration.add(ConfigurationIO.ConfigKeys.BALLOON_SETTINGS_LIST, (Serializable) config.get("BALLOON_SETTINGS_LIST"));
        if (config.get("GENERAL_PROBLEM") != null)
            configuration.add(ConfigurationIO.ConfigKeys.GENERAL_PROBLEM, (Serializable) config.get("GENERAL_PROBLEM"));
        configuration.add(ConfigurationIO.ConfigKeys.PROBLEM_DATA_FILES, (Serializable) config.get("PROBLEM_DATA_FILES"));
        configuration.add(ConfigurationIO.ConfigKeys.JUDGEMENTS, (Serializable) config.get("JUDGEMENTS"));
        configuration.add(ConfigurationIO.ConfigKeys.LANGUAGES, (Serializable) config.get("LANGUAGES"));
        configuration.add(ConfigurationIO.ConfigKeys.PROBLEMS, (Serializable) config.get("PROBLEMS"));
        configuration.add(ConfigurationIO.ConfigKeys.SITES, (Serializable) config.get("SITES"));
        configuration.add(ConfigurationIO.ConfigKeys.CONTEST_INFORMATION, (Serializable) config.get("CONTEST_INFORMATION"));
        configuration.add(ConfigurationIO.ConfigKeys.CLIENT_SETTINGS_LIST, (Serializable) config.get("CLIENT_SETTINGS_LIST"));
        configuration.add(ConfigurationIO.ConfigKeys.GROUPS, (Serializable) config.get("GROUPS"));
        configuration.add(ConfigurationIO.ConfigKeys.PROFILES, (Serializable) config.get("PROFILES"));
        configuration.add(ConfigurationIO.ConfigKeys.PROFILE, (Serializable) config.get("PROFILE"));
        if (config.get("FINALIZE_DATA") != null)
            configuration.add(ConfigurationIO.ConfigKeys.FINALIZE_DATA, (Serializable) config.get("FINALIZE_DATA"));
        if (config.get("CATEGORIES") != null)
            configuration.add(ConfigurationIO.ConfigKeys.CATEGORIES, (Serializable) config.get("CATEGORIES"));
        if (config.get("PROFILE_CLONE_SETTINGS") != null)
            configuration.add(ConfigurationIO.ConfigKeys.PROFILE_CLONE_SETTINGS, (Serializable) config.get("PROFILE_CLONE_SETTINGS"));
        configuration.writeToDisk("profiles/P62d231b8-4de3-4ad6-80ee-2c1e04418419/db.1/settings.dat");
    }
}
