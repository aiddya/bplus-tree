import com.iddya.trees.BPlusTree;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class treesearch {
public static void main(String[] args)
{
    int degree;
    ArrayList<String> lines = new ArrayList<String>();

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
            lines.add(line);
            line = reader.readLine();
        }
    }
    catch (Exception e) {
        System.out.println("File read error: " + e.getMessage());
        return;
    }

    try {
        degree = Integer.parseInt(lines.get(0));
    }
    catch (Exception e) {
        System.out.println("Unexpected input at line 1: " + e.getMessage());
        return;
    }

    BPlusTree tree = new BPlusTree(degree);
    Pattern insert = Pattern.compile("Insert\\(([0-9\\.\\-]*),(.*)\\)");
    Pattern search = Pattern.compile("Search\\(([0-9\\.\\-]*)\\)");

    for (int i = 1; i < lines.size(); i++) {
        Matcher im = insert.matcher(lines.get(i));
        Matcher sm = search.matcher(lines.get(i));

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

            tree.search(key);
        }
    }
}
}
