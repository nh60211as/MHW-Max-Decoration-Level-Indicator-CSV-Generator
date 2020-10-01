import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class WriteFile {
    static void writeRemainKeyEntryFile(HashMap<String, String> levelIndicatorList, File file) {
        Writer writer = null;
        BufferedWriter bw = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            bw = new BufferedWriter(writer);
            // Start writing files line by line
            for (String keyEntry : levelIndicatorList.keySet()) {
                // GMD Key
                bw.append(keyEntry);
                // separator
                bw.append(';');

                // max level
                bw.append("[<STYL MOJI_YELLOW_DEFAULT>");
                bw.append(levelIndicatorList.get(keyEntry));
                bw.append("</STYL>]");
                bw.append('\n');
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (writer != null)
                    writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    static void writeGMDEditorCSV(HashMap<String, String> fileContent, HashMap<String, String> remainKeyEntries, File file) {
        Writer writer = null;
        BufferedWriter bw = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            bw = new BufferedWriter(writer);
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            ICSVWriter csvWriter = new CSVWriterBuilder(bw).withParser(parser).build();
            // Start writing files line by line
            for (String keyEntry : remainKeyEntries.keySet()) {
                String[] entries = {keyEntry, fileContent.get(keyEntry) + ' ' + remainKeyEntries.get(keyEntry)};
                csvWriter.writeNext(entries);
//                bw.append(keyEntry);
//                bw.append(';');
//                bw.append(fileContent.get(keyEntry));
//                bw.append(' ');
//                bw.append(remainKeyEntries.get(keyEntry));
//                bw.append('\n');
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (writer != null)
                    writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
