import java.util.HashMap;
import java.util.Map;

public class HuffmanCompression {
    private String input;
    private Map<Character, Integer> mp = new HashMap<>();

    public void setInput(String input) {
        this.input = input;
    }

    public String compress(){
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            mp.put(characters[i], 0);
        }
        for (int i = 0; i < characters.length; i++) {
            int val = mp.get(characters[i]);
            mp.put(characters[i], val+1);
        }
        for (Map.Entry<Character,Integer> entry : mp.entrySet())
            System.out.println(entry.getKey() + " = " + entry.getValue());
        return "";
    }
}
