import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HuffmanAlgoGUI extends JFrame{
    private JPanel panelMain;
    private JTextField txtFileName;
    private JButton compressButton;
    private JButton DecompressButton;

    public HuffmanAlgoGUI() {
        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HuffmanAlgorithm huffman = new HuffmanAlgorithm();
                String content = huffman.readFile(txtFileName.getText());
                if(content != null) {
                    huffman.compress(content);
                    JOptionPane.showMessageDialog(compressButton, "File Compressed Successfully!");
                } else {
                    JOptionPane.showMessageDialog(compressButton, "Invalid File Name given.");
                }
            }
        });
        DecompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HuffmanAlgorithm huffman = new HuffmanAlgorithm();
                String content = huffman.readFile(txtFileName.getText());
                if(content != null) {
                    huffman.decompress(content);
                    JOptionPane.showMessageDialog(compressButton, "File Decompresssed Successfully!");
                } else {
                    JOptionPane.showMessageDialog(compressButton, "Invalid File Name given.");
                }
            }
        });
    }

    public static void main(String[] args) {
        HuffmanAlgoGUI huff = new HuffmanAlgoGUI();
        huff.setContentPane(huff.panelMain);
        huff.setTitle("Huffman Algorithm");
        huff.setSize(700,400);
        huff.setVisible(true);
        huff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
