import java.util.HashMap;
import java.util.Map;

public class Node {
    Character c;
    Node left;
    Node right;
    Integer freq;

    public Node(){
        c = null;
        left = null;
        right = null;
        freq = 0;
    }

    public Node(Character c, Integer freq){
        this.c = c;
        this.left = null;
        this.right = null;
        this.freq = freq;
    }
    public Node(Character c, Node left, Node right, Integer freq){
        this.c = c;
        this.left = left;
        this.right = right;
        this.freq = freq;
    }


}
