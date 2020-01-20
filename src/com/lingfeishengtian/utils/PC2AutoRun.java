package com.lingfeishengtian.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class PC2AutoRun {
    /**
     * Runs server and admin processes. Very buggy though and for some reason does not close the server when exited.
     * **WARNING** THIS ONLY WORKS ON WINDOWS
     *
     * @param site1Login Site1
     * @param site1Password Site1
     * @param contestPassword Contest passcode set
     * @param binDirectory Where all the bash and bat files are
     */
    // Runs the server in the background while launching administrator
    public static void runPC2AdminSuite(String site1Login, String site1Password, String contestPassword, File binDirectory){
        try {
            String addBat = System.getProperty("os.name").toLowerCase().indexOf("win") >= 0 ? ".bat" : "";
            System.out.println(binDirectory.getAbsolutePath());
            ProcessBuilder builder = new ProcessBuilder(
                    new String[] {"cmd", "/c", "pc2server" + addBat + " --nogui --login \"" + site1Login +  "\" --password \"" + site1Password + "\" --contestpassword " + contestPassword}
            );
            builder.directory(binDirectory);
            builder.redirectErrorStream(true);
            builder.start();

            ProcessBuilder builderAdmin = new ProcessBuilder(
                    new String[] {"cmd.exe", "/c", "pc2admin" + addBat}
            );
            builderAdmin.directory(binDirectory);
            builderAdmin.redirectErrorStream(true);
            Process pAdmin = builderAdmin.start();
            BufferedReader rAdmin = new BufferedReader(new InputStreamReader(pAdmin.getInputStream()));
            String lineAdmin;
            while (true) {
                lineAdmin = rAdmin.readLine();
                if (lineAdmin == null) { break; }
                System.out.println(lineAdmin);
            }
        }catch(Exception e){
            System.out.println("An error occurred and we could not complete your run request.");
            e.printStackTrace();
        }
    }
}
