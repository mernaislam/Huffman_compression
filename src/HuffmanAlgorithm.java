import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class HuffmanAlgorithm {
    Map<Character, Integer> characterFrequency = new HashMap<>();
    Map<Character, String> characterCode = new HashMap<>();
    String file = "";
    public String readFile(String fileName){
        Scanner sc = new Scanner(System.in);
        Path path = Paths.get(fileName);
        try {
            String content = Files.readString(path);
            file = fileName;
            return content;
        }
        catch (IOException ex) {
            return null;
        }
    }

    public void writeToFile(String str, Boolean compress) {
        if (!Objects.equals(file, "")) {
            int i = file.indexOf(".");
            int j = file.indexOf("_");
            int x = j;
            if(j == -1) x = i;
            String newName;
            if(!compress)
                newName = file.substring(0,x) + "_decompressed.txt";
            else
                newName = file.substring(0,x) + "_compressed.txt";
            Path path = Paths.get(newName);
            try {
                Files.writeString(path, str, StandardCharsets.UTF_8);
            }
            catch (IOException ex) {
                //....
            }
        }
    }

    public Map<Character, Integer> getFrequency(String input){
        Map<Character, Integer> mp = new HashMap<>();
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int val = mp.getOrDefault(characters[i], 0);
            mp.put(characters[i], val + 1);
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
        generateCodes(pq.peek(), "");
    }

    public void generateCodes(Node root, String code){
        if(root == null) return;
        if(root.c != null){
            characterCode.put(root.c, code);
        }
        generateCodes(root.left, code + '0');
        generateCodes(root.right, code + '1');
    }
    public static String convertToBinary(String input) {
        String binary = "";
        for (int i = 0; i < input.length(); i++) {
            String value = Integer.toBinaryString(input.charAt(i));
            if(value.length() < 8){
                int cnt = 8 - value.length();
                for (int j = 0; j < cnt; j++) {
                    value = "0" + value;
                }
            }
            binary += value;
        }
        return binary;
    }

    public void compress(String input){
        String compressedCode = "";
        buildTree(input, true);
        for (int i = 0; i < input.length(); i++) {
            compressedCode += characterCode.get(input.charAt(i));
        }
        int count = compressedCode.length() % 8;
        count = 8 - count;
        for (int i = 0; i < count; i++) {
            compressedCode = '0' + compressedCode;
        }

        String binaryCompressed = "";
        for (int i = 0; i < compressedCode.length()/8; i++) {
            String value = compressedCode.substring(8*i,(i+1)*8);
            int bin = Integer.parseInt(value,2);
            binaryCompressed += (char)(bin);
        }

        // overhead will include count - number of characters - each character and its corresponding frequency
        String overhead = "";
        overhead += (char)(count);
        overhead += (char) characterFrequency.size();
        for (var entry: characterFrequency.entrySet()) {
            overhead += entry.getKey();
            int freq = entry.getValue();
            overhead += (char) freq;
        }
        writeToFile(overhead + binaryCompressed, true);
    }

    public Character getCharacter(String code){
        for(var entry: characterCode.entrySet()) {
            if(entry.getValue().equals(code)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void decompress(String input){
        int count, uniqueCharacters;

        String binaryCompressed = convertToBinary(input);
        // retrieving overhead
        String cntValue = binaryCompressed.substring(0,8); // represents count
        count = Integer.parseInt(cntValue,2);

        String charValue = binaryCompressed.substring(8,16); // represents number of unique characters
        uniqueCharacters = Integer.parseInt(charValue,2);
        int cnt = 2;
        for (int i = 0; i < uniqueCharacters; i++) {                            // 0  1  2
            // convert key
            String value1 = binaryCompressed.substring(8*(i+cnt),(i+cnt+1)*8);  // 2  4  6
            char key = (char) Integer.parseInt(value1, 2);
            cnt++;
            // convert value
            String value2 = binaryCompressed.substring(8*(i+cnt),(i+cnt+1)*8);  // 3  5  7
            int value = Integer.parseInt(value2,2);
            characterFrequency.put(key, value);
        }

        // build tree
        buildTree("", false);

        // retrieving compressed code
        // 16 + 16 * unique characters
        binaryCompressed = binaryCompressed.substring(16 + 16 * uniqueCharacters + count);
        String value = "";
        String decompressedData = "";
        for (int i = 0; i < binaryCompressed.length(); i++) {
            value = "";
            value += binaryCompressed.charAt(i);
            while(!characterCode.containsValue(value)){
                i++;
                value += binaryCompressed.charAt(i);
            }
            decompressedData += getCharacter(value);
        }
        writeToFile(decompressedData, false);
    }
}
