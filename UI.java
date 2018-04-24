import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.*;

public class UI {
    private JFrame frame;
    public Kcluster cluster;
    public UI() {
            this.cluster = new Kcluster();
            this.initializeUI();
    }

    public void initializeUI() {
        JLabel stats02= new JLabel();
        JFrame frame = new JFrame("Ham & Spam");//creates a frame/window; javax.swing.JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200, 700));//sets initial size resolution of the frame...; java.awt.Dimensio

        Container con = frame.getContentPane();
        JPanel contentPanel= new JPanel();

        JPanel tabPanel= new JPanel();
        tabPanel.setLayout(new FlowLayout());
        tabPanel.setPreferredSize(new Dimension(250,500));
        // tabPanel.setBackground(Color.WHITE);

        JTextArea wordsSpam = new JTextArea();
        JTextArea frequenciesSpam = new JTextArea();
        JButton information= new JButton("Select input text");

        Graph graph= new Graph();
        graph.setPreferredSize(new Dimension(550,550));
        graph.setBackground(Color.WHITE);

        information.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    cluster.loadFile(selectedFile.getAbsolutePath());
                    graph.k = cluster.k;
                    if(cluster.isTwo){
                        for(int i = 0; i < cluster.currentCentroids.size(); i++){
                                graph.centroids.add(cluster.currentCentroids.get(i));
                        }
                        graph.repaint();
                    }
                }
                frame.requestFocus();
                }
        });

        information.setPreferredSize(new Dimension(200,50));


        tabPanel.add(information);
        tabPanel.add(graph);


        contentPanel.setLayout(new CardLayout());
        CardLayout cardLayout= (CardLayout) contentPanel.getLayout();

        //panel 1

        JPanel hamPanel= new JPanel();
        hamPanel.setLayout(new FlowLayout());
        hamPanel.setPreferredSize(new Dimension(250,550));

        JPanel filler = new JPanel();
        filler.setPreferredSize(new Dimension(700,50));

        JPanel pointClass= new JPanel();
        pointClass.setPreferredSize(new Dimension(550,50));
        pointClass.setBackground(Color.GRAY);
        pointClass.setLayout(new GridLayout(1,2));
        JLabel point = new JLabel("Point");
        JLabel className = new JLabel("Centroid");
        pointClass.add(point);
        pointClass.add(className);

        JPanel pointClassPanel= new JPanel();
        pointClassPanel.setPreferredSize(new Dimension(550,190));
        pointClassPanel.setBackground(Color.WHITE);
        pointClassPanel.setLayout(new GridLayout(1,2));
        JLabel pointPanel = new JLabel();
        JLabel classPanel = new JLabel();
        pointClassPanel.add(pointPanel);
        pointClassPanel.add(classPanel);

        hamPanel.add(filler);
        hamPanel.add(pointClass);
        hamPanel.add(pointClassPanel);

//--

        con.setLayout(new GridLayout(1,2));
        con.add(tabPanel);
        con.add(hamPanel);

        frame.pack();
        frame.setVisible(true);
    }
}
