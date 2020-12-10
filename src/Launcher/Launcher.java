package Launcher;

import Statistics.Stats;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Launcher extends JFrame {
    public ArrayList<Stats> stats;
    public ArrayList<String> images;
    public ArrayList<Integer> prodNum;
    private int i = 0;

    //function for launching LauncherAssistant to run productions on "prezentacja.txt" file
    public void launchFile() throws IOException {
        LauncherAssistant assistant = new LauncherAssistant();

        assistant.readAndRun("prezentacja.txt");

        //getter for statistics array
        stats = LauncherAssistant.getStats();
        //getter for paths to png with graphs
        images = LauncherAssistant.getImages();
        //getter for productions id
        prodNum = LauncherAssistant.getProductionsNumbers();
    }

    public Launcher() throws IOException {
        launchFile();
        //tworzenie jframe i ustawienia różne
        JFrame jf = new JFrame();
        jf.setTitle("Transformacje grafowe");
        jf.setLayout(new BorderLayout());
        jf.setSize(800, 600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        //utworzenie horizontal boxa na przyciski
        Box itemBox = Box.createHorizontalBox();
        //utworzenie kolejnych przycikow i dodanie ich do boxa
        JButton button1 = new JButton("Początkowy graf");
        JButton button2 = new JButton("Poprzednia produkcja");
        JButton button3 = new JButton("Następna produkcja");

        itemBox.add(button1);
        itemBox.add(button2);
        itemBox.add(button3);

        //dodanie boxa do ramki na północy
        jf.add(itemBox,BorderLayout.NORTH);
        jf.setVisible(true);

        //zdefiniowane akcje dla poszczególnych przycisków
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                i=0;
                setFrame(jf,stats.get(0),images.get(0));
                repaint();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(i>=2){
                    i-=1;
                    setFrame(jf,stats.get(i),images.get(i));
                    repaint();
                }

            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(i+1<stats.size()){
                    i+=1;
                    setFrame(jf,stats.get(i),images.get(i));
                    repaint();

                }
            }
        });
    }

    // funkcja odpowiadająca za ustawienie ramki dla poszczególnej produkcji (grafu)
    public void setFrame(JFrame jf, Stats stat,String filename){
        //stworzenie panelu dla statystyk
        JPanel statistics = new JPanel();
        statistics.setLayout( new GridLayout(8, 8));
        statistics.setBackground(Color.WHITE);

        //dodanie wszytskich statystyk
        JLabel label1 = new JLabel();
        if(i!=0) label1.setText(String.valueOf("Ostatnio wykonana produkcja: " + prodNum.get(i-1)));
        System.out.println(i);
        JLabel label2 = new JLabel();
        label2.setText(String.valueOf("Ilość wierzchołków: " + stat.vertexCounter()));
        JLabel label3 = new JLabel();
        label3.setText(String.valueOf("Ilość krawędzi: " + stat.edgeCounter()));
        JLabel label4 = new JLabel();
        label4.setText(String.valueOf("Ilość silnie spójnych skałdowych: " + stat.stronglyConnectedComponents()));
        JLabel label5 = new JLabel();
        label5.setText(String.valueOf("Średni stopień wierzchołka: " + stat.averageDegree()));
        JLabel label6 = new JLabel();
        label6.setText(String.valueOf("Średni stopień wierzchołka {a,b,c,d} : " + stat.averageVDegree()));
        JLabel label7 = new JLabel();
        label7.setText(String.valueOf("Średnia ilość wierzchołków w spójnych składowych: " + stat.averageNumberSCC()));

        statistics.repaint();

        if(i!=0) statistics.add(label1,BorderLayout.CENTER);
        statistics.add(label2,BorderLayout.CENTER);
        statistics.add(label3);
        statistics.add(label4);
        statistics.add(label5);
        statistics.add(label6);
        statistics.add(label7);

        //dodanie statystyk na poludnie ramki
        jf.add(statistics,BorderLayout.SOUTH);

        //stworzenie panelu z png grafu
        JPanel imgPanel = new GraphPanel(filename,jf.getWidth(), jf.getHeight());
        imgPanel.setBackground(Color.WHITE);

        jf.add(imgPanel,BorderLayout.CENTER);

        statistics.repaint();
        repaint();
        jf.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Launcher();

    }
}