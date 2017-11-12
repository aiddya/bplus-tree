import com.iddya.trees.BPlusTree;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

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
        System.out.println("Unexpected input at line 1: " + lines.get(0));
        return;
    }

    BPlusTree tree = new BPlusTree(degree);
}
}
