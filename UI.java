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
    public int turn;
    public UI() {
            this.turn = 1;
            this.cluster = new Kcluster();
            this.initializeUI();
    }

    public void initializeUI() {
        JLabel stats02= new JLabel();
        JFrame frame = new JFrame("Ham & Spam");//creates a frame/window; javax.swing.JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(2000, 700));//sets initial size resolution of the frame...; java.awt.Dimensio

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
        graph.setPreferredSize(new Dimension(750,500));
        graph.setBackground(Color.WHITE);

        information.setPreferredSize(new Dimension(200,50));

        tabPanel.add(information);
        tabPanel.add(graph);

        contentPanel.setLayout(new CardLayout());
        CardLayout cardLayout= (CardLayout) contentPanel.getLayout();

        JPanel hamPanel= new JPanel();
        hamPanel.setLayout(new FlowLayout());
        hamPanel.setPreferredSize(new Dimension(250,550));

        JPanel filler = new JPanel();
        filler.setPreferredSize(new Dimension(700,50));

        JPanel pointClass= new JPanel();
        pointClass.setPreferredSize(new Dimension(800,50));
        pointClass.setBackground(Color.GRAY);
        pointClass.setLayout(new GridLayout(1,2));
        JLabel point = new JLabel("Class");
        JLabel className = new JLabel("Centroid");
        pointClass.add(point);
        pointClass.add(className);

        JPanel pointClassPanel= new JPanel();
        pointClassPanel.setPreferredSize(new Dimension(800,200));
        pointClassPanel.setBackground(Color.WHITE);
        pointClassPanel.setLayout(new GridLayout(1,2));
        JTextArea pointPanel = new JTextArea();
        JTextArea classPanel = new JTextArea();
        pointClassPanel.add(pointPanel);
        pointClassPanel.add(classPanel);

        JPanel buttonsPanel= new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(800,100));

        JButton prev= new JButton("Previous");
        JButton next= new JButton("Next");
        buttonsPanel.add(prev);
        buttonsPanel.add(next);

        information.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // JFileChooser fileChooser = new JFileChooser();
                // int result = fileChooser.showOpenDialog(frame);
                // if (result == JFileChooser.APPROVE_OPTION) {
                    // File selectedFile = fileChooser.getSelectedFile();
                    // System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    // cluster.loadFile(selectedFile.getAbsolutePath());
                    cluster.loadFile("input.txt");
                    cluster.findFinalCentroids();

                    graph.k = cluster.k;
                    graph.turn = turn;
                    if(cluster.isTwo){
                        for(int i = 0; i < cluster.iterations.get(turn).size(); i++){
                            cluster.iterations.get(turn).get(i).print();
                            graph.centroids.add(cluster.iterations.get(turn).get(i));
                        }
                        graph.repaint();

                        for(int i = 0; i < cluster.iterations.get(turn).size(); i++){
                            Centroids thisCentroid = cluster.iterations.get(turn).get(i);
                            for(int k = 0; k < thisCentroid.vectors.size(); k++){
                                ArrayList<Double> thisVList = thisCentroid.vectors.get(k).list;
                                ArrayList<Double> thisCList = thisCentroid.list;

                                String Zero = Double.toString(thisVList.get(0));
                                String One = Double.toString(thisVList.get(1));
                                String vectorPoint = Zero + " , "+One +"\n" ;

                                String Zero2 = Double.toString(thisCList.get(0));
                                String One2 = Double.toString(thisCList.get(1));
                                String centroidPoint = Zero2 + " , "+One2 +"\n" ;

                                pointPanel.append(centroidPoint);
                                classPanel.append(vectorPoint);
                            }
                        }

                    }

                frame.requestFocus();
                }
        });

        prev.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if((turn-1)!= 0){
                    pointPanel.setText("");
                    classPanel.setText("");
                    graph.removeAll();
                    turn --;
                    if(cluster.isTwo){
                        for(int i = 0; i < cluster.iterations.get(turn).size(); i++){
                            cluster.iterations.get(turn).get(i).print();
                            graph.centroids.add(cluster.iterations.get(turn).get(i));
                        }
                        graph.repaint();

                        for(int i = 0; i < cluster.iterations.get(turn).size(); i++){
                            Centroids thisCentroid = cluster.iterations.get(turn).get(i);
                            for(int k = 0; k < thisCentroid.vectors.size(); k++){
                                ArrayList<Double> thisVList = thisCentroid.vectors.get(k).list;
                                ArrayList<Double> thisCList = thisCentroid.list;

                                String Zero = Double.toString(thisVList.get(0));
                                String One = Double.toString(thisVList.get(1));
                                String vectorPoint = Zero + " , "+One +"\n" ;

                                String Zero2 = Double.toString(thisCList.get(0));
                                String One2 = Double.toString(thisCList.get(1));
                                String centroidPoint = Zero2 + " , "+One2 +"\n" ;

                                pointPanel.append(centroidPoint);
                                classPanel.append(vectorPoint);
                            }
                        }

                    }
                }
                frame.requestFocus();
                }
        });

         next.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if((turn+1)!=cluster.iterations.size()){
                    graph.repaint();
                    pointPanel.setText("");
                    classPanel.setText("");
                    turn ++;
                    if(cluster.isTwo){
                        for(int i = 0; i < cluster.iterations.get(turn).size(); i++){
                            cluster.iterations.get(turn).get(i).print();
                            graph.centroids.add(cluster.iterations.get(turn).get(i));
                        }
                        graph.repaint();

                        for(int i = 0; i < cluster.iterations.get(turn).size(); i++){
                            Centroids thisCentroid = cluster.iterations.get(turn).get(i);
                            for(int k = 0; k < thisCentroid.vectors.size(); k++){
                                ArrayList<Double> thisVList = thisCentroid.vectors.get(k).list;
                                ArrayList<Double> thisCList = thisCentroid.list;

                                String Zero = Double.toString(thisVList.get(0));
                                String One = Double.toString(thisVList.get(1));
                                String vectorPoint = Zero + " , "+One +"\n" ;

                                String Zero2 = Double.toString(thisCList.get(0));
                                String One2 = Double.toString(thisCList.get(1));
                                String centroidPoint = Zero2 + " , "+One2 +"\n" ;

                                pointPanel.append(centroidPoint);
                                classPanel.append(vectorPoint);
                            }
                        }

                    }
                }
                frame.requestFocus();
                }
        });



        hamPanel.add(filler);
        hamPanel.add(pointClass);
        hamPanel.add(pointClassPanel);
        hamPanel.add(buttonsPanel);

//--

        con.setLayout(new GridLayout(1,2));
        con.add(tabPanel);
        con.add(hamPanel);

        frame.pack();
        frame.setVisible(true);
    }
}
