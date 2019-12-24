package Huffman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class JPEGGUI {
    private JPanel panel1;
    private JButton encodeButton;
    private JButton decodeButton;
    private JTextField SeqTextField;
    private JTextField pathTextField;
    private JTextField nTextFiled;
    private JPanel panelGUI;

    public JPEGGUI(){
        encodeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(nTextFiled.getText());
                String path = pathTextField.getText();
                String  seq = SeqTextField.getText();
                System.out.println(seq);
                //"C:/Users/lamya/Desktop/New folder (2)/Multimedia-Projects/RLE-Jpeg-huffman/test1.txt"
                //21
                //-2 0 0 2 0 0 3 2 0 1 0 0 -2 0 -1 0 0 1 0 0 -1
                String[] seqSplit = seq.split(" ");
                int []arr= new int[seqSplit.length];
                for (int i = 0 ; i < seqSplit.length ; ++i){
                    arr[i] = Integer.parseInt(seqSplit[i]);
                }
                Tree t = new Tree() ;
                t.build(arr, n, path);
            }
        });
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = pathTextField.getText();
                String seq = SeqTextField.getText();
                Tree t = new Tree();
                //0001111011110010101111011010011010
                t.Decode(path, seq);
            }
        });
    }
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("JPEG Compression");
        jFrame.setContentPane(new JPEGGUI().panelGUI);
        jFrame.setSize(800, 400);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
