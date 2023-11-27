import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HuffmanAlgorithm {
    Map<Character, Integer> characterFrequency = new HashMap<>();
    Map<Character, String> characterCode = new HashMap<>();
    HuffmanCompression c;
    HuffmanDecompression d;
    String file = "";
    public HuffmanAlgorithm(HuffmanCompression c, HuffmanDecompression d) {
        this.c = c;
        this.d = d;
    }
    public boolean readFile(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the filename: ");
        String fileName = sc.nextLine();
        Path path = Paths.get(fileName);
        try {
            String content = Files.readString(path);
            c.setInput(content);
            d.setInput(content);
            file = fileName;
            return true;
        }
        catch (IOException ex) {
            System.out.println("The file does not exist.");
            System.out.print("Do you want to try again? (y/n): ");
            char choice = sc.next().charAt(0);
            if(choice == 'Y' || choice == 'y') readFile();
            return false;
        }
    }

    public void writeToFile(String str, char option) {
        if (!Objects.equals(file, "")) {
            int i = file.indexOf(".");
            int j = file.indexOf("_");
            int x = j;
            if(j == -1) x = i;
            String newName;
            if(option == 'd')
                newName = file.substring(0,x) + "_decompressed.txt";
            else
                newName = file.substring(0,x) + "_compressed.txt";
            Path path = Paths.get(newName);
            try {
                Files.writeString(path, str, StandardCharsets.UTF_8);
                System.out.println("Done Successfully!");
            }
            catch (IOException ex) {
                System.out.println("The file does not exist.");
            }
        }
    }

    public Map<Character, Integer> getFrequency(String input){
        Map<Character, Integer> mp = new HashMap<>();
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            mp.put(characters[i], 0);
        }
        for (int i = 0; i < characters.length; i++) {
            int val = mp.get(characters[i]);
            mp.put(characters[i], val+1);
        }
        return mp;
    }
    public void buildTree(String input, boolean compress){
        if(compress){
            characterFrequency = getFrequency(input);
        }
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(l-> l.freq));
        for (var entry: characterFrequency.entrySet()){
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while(pq.size() != 1){
            Node left = pq.poll();
            Node right = pq.poll();
            int sum = left.freq + right.freq;
            Node newNode = new Node(null, left, right, sum);
            pq.add(newNode);
        }
        traverseTree(pq.peek());
    }

    public void traverseTree(Node root){
        String code = "0", codeRight = "1";
        Node left = root.left;
        Node right = root.right;
        Stack<Node> stack = new Stack<>();
        Node node = root;

        while (node != null) {
            stack.push(node);
            code += "0";
            if(node.left == null){
                characterCode.put(node.c, code);
            }
            node = node.left;
        }

//        int[] result = new int[stack.size()];
        int i = 0;
        while (stack.size() > 0) {
            node = stack.pop();
            code = code.substring(0, code.length()-1);
//            if(node != null) {
//
//            }
            if (node.right != null) {
                code += 1;
                node = node.right;

                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        }
    }
}
