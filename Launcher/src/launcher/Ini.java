package launcher;

import java.io.*;
import java.util.*;

public class Ini {

    static String filename = "global.ini";

    public static String getValue(String key) {
        if (filename.isEmpty()) return "null";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int eqPos = line.indexOf('=');
                if (eqPos == -1) continue;

                String fileKey = line.substring(0, eqPos);
                String fileValue = line.substring(eqPos + 1);

                if (fileKey.equals(key)) {
                	return fileValue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return "null";
    }


    public static boolean setValue(String key, String value) {
        if (filename.isEmpty()) return false;

        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int eqPos = line.indexOf('=');
                if (eqPos == -1) {
                    lines.add(line);
                    continue;
                }

                String fileKey = line.substring(0, eqPos);
                if (fileKey.equals(key)) {
                    lines.add(key + "=" + value);
                    found = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            return false;
        }

        if (!found) {
            lines.add(key + "=" + value);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static void setFilename(String fname) {
        filename = fname;
    }
}
