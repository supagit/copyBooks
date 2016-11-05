import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Supa on 08.10.2015.
 */
public class Main {

    static Map<String, String> letterMap = new HashMap<String, String>();
    static {
        letterMap.put("I", "IJ");
        letterMap.put("J", "IJ");

        letterMap.put("P", "PQ");
        letterMap.put("Q", "PQ");

        letterMap.put("U", "UV");
        letterMap.put("V", "UV");

        letterMap.put("X", "XYZ");
        letterMap.put("Y", "XYZ");
        letterMap.put("Z", "XYZ");
    }

    public static void main(String[] args) {

        String sourceFolder = "E:\\Download\\Update KW39-442016";
        String targetFolder = "E:\\diskstation\\docs\\eBooks\\epubs a-z";
        boolean dryRun = true;

        File folder = new File(sourceFolder);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            String fileName = file.getName();

            String destFileName = fileName;
            String destFolder = "";

            if (fileName.startsWith("[")) {
                int i = fileName.indexOf("]");
                if (i!=-1) {
                    destFolder = fileName.substring(0,i+1);
                    destFileName = fileName.substring(i+1);
                } else {
                    continue;
                }
            } else {
                String firstLetter = fileName.substring(0,1).toUpperCase();
                destFolder = firstLetter;
                if (letterMap.get(firstLetter) != null) {
                    destFolder = letterMap.get(firstLetter);
                }
            }

            destFileName = destFileName.trim();


            File subdir = new File(targetFolder, destFolder);
            subdir.mkdirs();

            File destFile = new File(targetFolder + "/" + destFolder + "/" + destFileName);
            if (destFile.exists()) {
                if (!dryRun) {
                    file.delete();
                }
                System.out.println("DELE " + fileName + " - " + destFolder + "/" + destFileName);
            } else {
                if (!dryRun) {
                    file.renameTo(destFile);
                }
                System.out.println("COPY " + fileName + " - " + destFolder + "/" + destFileName);
            }


        }

        File file = new File(sourceFolder);
        file.delete();


    }
}
