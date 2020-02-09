import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String skillListFileName = "skillList.txt";
    private static final String jewelListFileName = "jewelList.txt";
    private static final String remainKeyEntryFileName = "remainKeyEntry.txt";
    private static HashMap<String, String> remainKeyEntries;

    public static void main(String[] args) {
        File skillListFile = new File(skillListFileName);
        File jewelListFile = new File(jewelListFileName);
        HashMap<String, Integer> skillList = ReadFile.readSkillList(skillListFile, true);
        HashMap<String, ArrayList<Skill>> jewelList = ReadFile.readJewelList(jewelListFile);

        int method = Integer.parseInt(args[1]);
        HashMap<String, String> maxLevelList = generateMaxLevelList(skillList, jewelList, method);

        File remainKeyEntryFile = new File(remainKeyEntryFileName);
        WriteFile.writeRemainKeyEntryFile(maxLevelList, remainKeyEntryFile);

        remainKeyEntries = ReadFile.readRemainKeyEntry(remainKeyEntryFileName);

        File itemFile = new File(args[0]);
        if (itemFile.isDirectory()) {
            FileFilter filter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.getName().toLowerCase().endsWith(".csv"))
                        return true;
                    return false;
                }
            };
            File[] fileList = itemFile.listFiles(filter);
            File outputFileDir = new File(itemFile.getAbsolutePath() + "_remain");
            if (!outputFileDir.exists())
                if (outputFileDir.mkdir()) {
                    System.out.println(outputFileDir.getAbsolutePath() + "folder created");
                } else {
                    System.out.println("Unable to create " + outputFileDir.getAbsolutePath() + "folder");
                    return;
                }
            for (File file : fileList) {
                File outputFileName = new File(outputFileDir.getAbsolutePath() + "/" + file.getName());
                //System.out.println(outputFileName.getAbsoluteFile());
                generateRemainFile(file, addStringBeforeExtension(outputFileName, "_remain"));
            }
        } else {
            generateRemainFile(itemFile, addStringBeforeExtension(itemFile, "_remain"));
        }

        System.out.println("Program exited");
    }

    // different methods to show the level indicator of level 4 jewels
    // Example: Offensive Guard [3], Attack Boost [7]
    // method 1: Higher level needed -> max(3,7) -> [7]
    // method 2: Separate numbers                -> [3,7]
    // method 3: Lower level needed  -> min(3,7) -> [3]
    private static HashMap<String, String> generateMaxLevelList(HashMap<String, Integer> skillList, HashMap<String, ArrayList<Skill>> jewelList, int method) {
        HashMap<String, String> levelIndicatorList = new HashMap<>();
        for (String keyEntry : jewelList.keySet()) {
            switch (method) {
                case 1:
                default: {
                    Integer maxLevelNeeded = 0;
                    for (Skill skill : jewelList.get(keyEntry)) {
                        //System.out.println(skill.skillName);
                        int skillMaxLevel = skillList.get(skill.skillName);
                        int maxLevelNeededNow = (int) Math.ceil((float) skillMaxLevel / (float) skill.level);
                        maxLevelNeeded = Math.max(maxLevelNeeded, maxLevelNeededNow);
                    }
                    levelIndicatorList.put(keyEntry, maxLevelNeeded.toString());
                    break;
                }
                case 2: {
                    String maxLevelNeeded = "";
                    for (Skill skill : jewelList.get(keyEntry)) {
                        //System.out.println(skill.skillName);
                        int skillMaxLevel = skillList.get(skill.skillName);
                        Integer maxLevelNeededNow = (int) Math.ceil((float) skillMaxLevel / (float) skill.level);
                        maxLevelNeeded += maxLevelNeededNow.toString() + ",";
                    }
                    levelIndicatorList.put(keyEntry, maxLevelNeeded.substring(0, maxLevelNeeded.length() - 1));
                    break;
                }
                case 3: {
                    Integer minLevelNeeded = Integer.MAX_VALUE;
                    for (Skill skill : jewelList.get(keyEntry)) {
                        //System.out.println(skill.skillName);
                        int skillMaxLevel = skillList.get(skill.skillName);
                        int maxLevelNeededNow = (int) Math.ceil((float) skillMaxLevel / (float) skill.level);
                        minLevelNeeded = Math.min(minLevelNeeded, maxLevelNeededNow);
                    }
                    levelIndicatorList.put(keyEntry, minLevelNeeded.toString());
                    break;
                }
            }
        }
        return levelIndicatorList;
    }

    private static void generateRemainFile(File inputFileName, File outputFile) {
        HashMap<String, String> CSVContent = ReadFile.readGMDEditorCSV(inputFileName);
        //System.out.println(CSVContent.toString());
        //System.out.println(CSVContent.size());
        WriteFile.writeGMDEditorCSV(CSVContent, remainKeyEntries, outputFile);
    }

    // It's a hack
    private static File addStringBeforeExtension(File file, String addedString) {
        String fileName = file.getAbsolutePath();
        String fileNameWithExtension = fileName.substring(0, fileName.lastIndexOf("."));
        String extension = fileName.substring(fileName.lastIndexOf("."));

        return new File(fileNameWithExtension + addedString + extension);
    }
}
