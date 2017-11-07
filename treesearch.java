import com.iddya.trees.BPlusTree;

class treesearch {
    public static void main(String[] args) {
        BPlusTree tree = new BPlusTree(3);

        String suffix = args.length > 0 ? args[0] : "none";
        System.out.println("Hello World " + suffix);
    }
}
