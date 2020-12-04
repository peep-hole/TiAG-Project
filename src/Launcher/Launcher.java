package Launcher;

import Statistics.Stats;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.*;


public class Launcher extends JFrame{

    public ArrayList<Stats> stats;
    boolean next = false;

    public void launchFile() throws IOException {
        LauncherAssistant assistant = new LauncherAssistant();

        assistant.readAndRun("test.txt");
        stats = LauncherAssistant.getStats();

    }
    // funkcja która wyświetla okno i statystyki
    public void buildGUI(Stats stat, int i,Container c){

        //panel south dla statystyk
        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new BorderLayout());
        JPanel statistics = new JPanel();
        statistics.setLayout( new GridLayout(7, 7));

        // poszczegolne statystyki
        JLabel label1 = new JLabel();
        label1.setText(String.valueOf("Produkcja: " + i));
        JLabel label2 = new JLabel();
        label2.setText(String.valueOf("Ilość wierzchołków: " + stat.vertexCounter()));
        JLabel label3 = new JLabel();
        label3.setText(String.valueOf("Ilość krawędzi: " + stat.edgeCounter()));
        JLabel label4 = new JLabel();
        label4.setText(String.valueOf("Ilość silnie spójnych składowych: " + stat.stronglyConnectedComponents()));
        JLabel label5 = new JLabel();
        label5.setText(String.valueOf("Średni stopień wierzchołka: " + stat.averageDegree()));
        JLabel label6 = new JLabel();
        label6.setText(String.valueOf("Średni stopień wierzchołka {a,b,c,d}: " + stat.averageVDegree()));
        JLabel label7 = new JLabel();
        label7.setText(String.valueOf("Średnia liczba węzłów w składowej spójnej: " + stat.averageNumberSCC()));

        statistics.add(label1);
        statistics.add(label2);
        statistics.add(label3);
        statistics.add(label4);
        statistics.add(label5);
        statistics.add(label6);
        statistics.add(label7);

        panelSouth.add(statistics, BorderLayout.NORTH);

        c.add(panelSouth, BorderLayout.SOUTH);

        this.setVisible(true);
        this.setResizable(false);

    }

    public Launcher() throws IOException {
        launchFile();
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setResizable(false);

//        Box itemBox = Box.createHorizontalBox();

        JButton button = new JButton("Następna produkcja");

//        itemBox.add(button);


        c.add(button,BorderLayout.NORTH);

        setTitle("Transformacje grafowe");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next = true;

            }
        });

        // kazda iteracja to kolejna produkcja
        for(int i = 0; i < stats.size(); i++ ) {
            next = false;

            buildGUI(stats.get(i), i+1,c);

            while(!next) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }

        }

    }

    public static void main(String[] args) throws IOException {
        new Launcher();
    }

}