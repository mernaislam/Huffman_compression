import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HuffmanAlgorithm huffman = new HuffmanAlgorithm(new HuffmanCompression(), new HuffmanDecompression());
        System.out.println("Huffman Algorithm - choose your required functionality [1-3]:");
        while(true){
            System.out.println("1. Compression \n2. Decompression \n3. Exit");
            Scanner sc = new Scanner(System.in);
            int x = sc.nextInt();
            switch (x) {
                case 1 -> {
                    if(huffman.readFile())
                        huffman.writeToFile(huffman.c.compress(), 'c');
                }
                case 2 -> {
                    huffman.readFile();
                    System.out.println("ok");
                }
//                    l.writeToFile(l.d.decompress(), 'd');
                case 3 -> {
                    System.out.println("Thank you!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid number, please choose between 1 - 3");
            }
        }
    }
}
