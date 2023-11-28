import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HuffmanAlgorithm huffman = new HuffmanAlgorithm();
        System.out.println("Huffman Algorithm - choose your required functionality [1-3]:");
        System.out.println("1. Compression \n2. Decompression \n3. Exit");
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        switch (x) {
            case 1 -> {
                String content = huffman.readFile();
                if(content != null) huffman.compress(content);
            }
            case 2 -> {
                String content = huffman.readFile();
                if(content != null) huffman.decompress(content);
            }
            case 3 -> {
                System.out.println("Thank you!");
                System.exit(0);
            }
            default -> System.out.println("Invalid number, please choose between 1 - 3");
        }
    }
}
