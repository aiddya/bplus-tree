import com.iddya.trees.BPlusTree;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class treesearch {
public static void main(String[] args)
{
    int degree;
    ArrayList<String> inputLines  = new ArrayList<String>();
    ArrayList<String> outputLines = new ArrayList<String>();

    try {
        String line;
        File input = new File(args[0]);

        if (!input.exists() || !input.isFile()) {
            System.out.println("Invalid file name!");
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(input));

        line = reader.readLine();
        while (line != null) {
            inputLines.add(line);
            line = reader.readLine();
        }
        reader.close();
    }
    catch (Exception e) {
        System.out.println("File read error: " + e.getMessage());
        return;
    }

    try {
        degree = Integer.parseInt(inputLines.get(0));
    }
    catch (Exception e) {
        System.out.println("Unexpected input at line 1: " + e.getMessage());
        return;
    }

    BPlusTree tree = new BPlusTree(degree);
    Pattern insert = Pattern.compile("Insert\\(([0-9\\.\\-]*),(.*)\\)");
    Pattern search = Pattern.compile("Search\\(([0-9\\.\\-]*)\\)");

    for (int i = 1; i < inputLines.size(); i++) {
        Matcher im = insert.matcher(inputLines.get(i));
        Matcher sm = search.matcher(inputLines.get(i));

        if (im.matches()) {
            double key;
            String value;

            try {
                key   = Double.parseDouble(im.group(1));
                value = im.group(2);
            }
            catch (Exception e)
            {
                System.out.println("Unexpected input at line " + i + ": "
                                   + e.getMessage());
                continue;
            }

            tree.insert(key, value);
        } else if (sm.matches()) {
            double key;

            try {
                key = Double.parseDouble(sm.group(1));
            }
            catch (Exception e)
            {
                System.out.println("Unexpected input at line " + i + ": "
                                   + e.getMessage());
                continue;
            }

            outputLines.add(tree.search(key));
        }
    }

    try {
        File out              = new File("output_file.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(out));

        for (int i = 0; i < outputLines.size(); i++) {
            String tempLine = outputLines.get(i);
            writer.write(tempLine, 0, tempLine.length());
            writer.newLine();
            writer.flush();
        }
    } catch (Exception e) {
        System.out.println("File write error: " + e.getMessage());
        return;
    }
}
}
