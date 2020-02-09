import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class ReadFile {
    // read different set of Maximum Level based on isIB(is Iceborne)
    static HashMap<String, Integer> readSkillList(File file, boolean isIB) {
        HashMap<String, Integer> skillList = new HashMap<>();
        int maxLevelIndex = 1;
        if (isIB)
            maxLevelIndex = 2;

        Reader reader = null;
        BufferedReader br = null;
        try {
            System.out.println("Reading skill list: " + file.getAbsoluteFile());
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            br = new BufferedReader(reader);

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.length() == 0)
                    continue;
                if (currentLine.substring(0, 1).contentEquals("#"))
                    continue;

                String[] stringBlock = currentLine.split(";", -1);
                String inputKey = stringBlock[0];
                Integer inputValue = Integer.parseInt(stringBlock[maxLevelIndex]);
                // set the limit to -1 so the function DOESN'T discard trailing empty string
                //System.out.println(currentLine);
                //System.out.println(stringBlock.length);
                skillList.put(inputKey, inputValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return skillList;
    }

    static HashMap<String, ArrayList<Skill>> readJewelList(File file) {
        HashMap<String, ArrayList<Skill>> skillList = new HashMap<>();

        Reader reader = null;
        BufferedReader br = null;
        try {
            System.out.println("Reading jewel list: " + file.getAbsoluteFile());
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            br = new BufferedReader(reader);

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.length() == 0)
                    continue;
                if (currentLine.substring(0, 1).contentEquals("#"))
                    continue;

                String[] stringBlock = currentLine.split(";", -1);
                String inputKey = stringBlock[0];
                String skillListString = stringBlock[2];
                String[] SkillListBlock = skillListString.split(",");
                // set the limit to -1 so the function DOESN'T discard trailing empty string
                //System.out.println(currentLine);
                ArrayList<Skill> inputValue = new ArrayList();
                for (int i = 0; i < SkillListBlock.length; i += 2)
                    inputValue.add(new Skill(SkillListBlock[i], Integer.parseInt(SkillListBlock[i + 1])));
                skillList.put(inputKey, inputValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return skillList;
    }

    static HashMap<String, String> readRemainKeyEntry(String remainKeyEntryFileName) {
        HashMap<String, String> remainKeyEntry = new HashMap<>();
        Reader reader = null;
        BufferedReader br = null;
        try {
            File file = new File(remainKeyEntryFileName);
            System.out.println("Reading remain key entry list: " + file.getAbsolutePath());
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            br = new BufferedReader(reader);

            // Start reading files line by line
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.substring(0, 1).contentEquals("#"))
                    continue;
                if (currentLine.length() > 0) {
                    String[] stringBlock = currentLine.split(";", -1);
                    // set the limit to -1 so the function DOESN'T discard trailing empty string
                    //System.out.println(currentLine);
                    //System.out.println(stringBlock.length);
                    remainKeyEntry.put(stringBlock[0], stringBlock[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return remainKeyEntry;
    }

    // Read CSV files generated by Cirilla - GMD Editor
    // https://www.nexusmods.com/monsterhunterworld/mods/110
    static HashMap<String, String> readGMDEditorCSV(File file) {
        HashMap<String, String> fileContent = new HashMap<>();

        Reader reader = null;
        BufferedReader br = null;
        try {
            System.out.println("Reading CSV file exported from GMD editor: " + file.getAbsoluteFile());
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            br = new BufferedReader(reader);
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(br).withCSVParser(parser).build();

            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {
                if (record.length == 2) {
                    String inputKey = record[0];
                    String inputValue = record[1];
                    fileContent.put(inputKey, inputValue);

                } else {
                    System.out.printf("Parsing error at \"%s\".\n", record);
                    break;
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return fileContent;
    }
}

