// procedure:
// 1.initialize k centroids (randomly)
// 2. while(currCentroids != prevCentroids){
//   findDistance of trainingData to prevCentroids
//   take the k/2 nearest points and assign it to currCentroids
//   average the points of each currentCentroids (x+x+x+x...) (y+y+y+y..)
//   updateCentroids with the average of the points
// }

import java.io.*;
import java.util.*;
import java.lang.Math.*;

public class Kcluster {

    public ArrayList<Centroids> currentCentroids;
    public ArrayList<Centroids> prevCentroids;
    public ArrayList<Vectors> trainingData;
    public ArrayList<Vectors> trainingDataDuplicate; 
    public Integer k;
    public Integer min;
    public Boolean isTwo;

    public Kcluster(){
        this.isTwo = true;
        this.k = 0;
        this.min = 0;
        this.currentCentroids = new ArrayList<Centroids>();
        this.prevCentroids = new ArrayList<Centroids>();
        this.trainingData = new ArrayList<Vectors>();
    }

    public void findFinalCentroids(){
        initializeCentroids();
        int i = 0;
        boolean change = true;
        while(change){
            change = comparePrevCurrent(i);
            saveFile(i);
            getAllDistance();
            getKcluster();
            updatePrevCentroids();
            updateCentroids();
            i++;
        }
    }

    public Boolean comparePrevCurrent(int i){
        Boolean change = false;
            System.out.println("");
        if(i==0){
            System.out.println(i + "ITERATION CURRENT & PREV CENTROIDS HAS NO VECTORS ----------------------");
        }else{
            System.out.println(i + "TH ITERATION - -- - -- - - -- - - - - -- - -- - -------------------------");
        }
            System.out.println("");
        
        if(isTwo){
            for(int j = 0; j < currentCentroids.size(); j++){
                System.out.println(j+"th CURRENT CENTROIDS ------------------------------------------------------------");
                currentCentroids.get(j).print();
                Boolean isInPrev = false;
                System.out.println("");
    
                for(int index = 0; index < prevCentroids.size(); index++){
                    System.out.println(prevCentroids.size());
                    System.out.println(index+"th PREVIOUS CENTROIDS ------------------------------------------------------------");
                    prevCentroids.get(j).print();
                    System.out.println("");
                    if((currentCentroids.get(j).x.equals(prevCentroids.get(index).x)) || (currentCentroids.get(j).y.equals(prevCentroids.get(index).y))){
                       isInPrev = true;
                    }
                }
                if(!isInPrev){
                    change = true;
                }
            }
        }
        return change;
    }

    public void updatePrevCentroids(){
        prevCentroids.clear();
        for(int j=0; j < currentCentroids.size(); j++){
            System.out.println("Going to replace prevCentroids ---------------------------------------------------");
            prevCentroids.add(j, new Centroids(currentCentroids.get(j)));
        }
    }


    public void clearVectorList(){
      for(int j = 0; j < currentCentroids.size(); j++){
        currentCentroids.get(j).vectors.clear();
      }

      //y trainingData?
      for(int j = 0; j < trainingData.size(); j++){
        trainingData.get(j).distance.clear();
      }
    }

    public void updateCentroids(){
        System.out.println("Updating Centroids --------------------------------------------------------------");
        System.out.println("");

        if(isTwo){
            for(int j = 0; j < currentCentroids.size(); j++){
            averageTwoDistance(currentCentroids.get(j));
            }
        }else{

        }
        clearVectorList();
        initializeDuplicate();
    }

    public void averageTwoDistance(Centroids c){
        Double x = 0.0;
        Double y = 0.0;
        for(int j = 0; j < c.vectors.size(); j++){
            Vectors cNew = c.vectors.get(j);
            x = cNew.x + x;
            y = cNew.y + y;
        }
        c.x = x/c.vectors.size();
        c.y = y/c.vectors.size();
    }

    public void getKcluster(){
        System.out.println("Getting KCluster --------------------------------------------------------------");
        System.out.println("");
        Double distance = 0.0;
        int i = 0;
        for(int j=0; j < currentCentroids.size(); j++){
            getKnearest(currentCentroids.get(j), j);
            currentCentroids.get(j).print();
        }
    }

    public void getKnearest(Centroids c, int j){
        System.out.println("Getting Knearest --------------------------------------------------------------");
        System.out.println("");
        System.out.println(min);
        for(int i = 0; i < min; i++){
            Vectors n = getNearest(j);
            if(i == 0){
                c.vectors.add(n);
                trainingDataDuplicate.remove(n);
            }
            else {
                if(!c.vectors.contains(n)){
                    c.vectors.add(n);
                    trainingDataDuplicate.remove(n);
                }
            }
            System.out.println("getKnearest: ADDING VECTOR to Centroid "+c.x +" "+c.y );
            n.print();
        }
    }

    public Vectors getNearest(int x){
        Double min = 9999999.9;
        int minIndex = 0;
        for(int i = 0; i < trainingDataDuplicate.size(); i++){
            if(trainingDataDuplicate.get(i).distance.get(x) < min){
            min = trainingDataDuplicate.get(i).distance.get(x);
            minIndex = i;
            }
        }
        return trainingDataDuplicate.get(minIndex);
    }

    public void getAllDistance(){
        System.out.println("Getting all Distance --------------------------------------------------------------");
        System.out.println("");
        if(isTwo){
            for(int i = 0; i < trainingData.size(); i++){ 
                for(int j = 0; j < currentCentroids.size(); j++){
                    computeDistance(trainingData.get(i), currentCentroids.get(j));
                }
            }
        }
        else{
            for(int i = 0; i < trainingData.size(); i++){
                for(int j = 0; j < currentCentroids.size(); j++){
                    computeMoreDistance(trainingData.get(i), currentCentroids.get(j));
                }
            }
        }
        this.initializeDuplicate();
    }


    public void computeDistance(Vectors training, Centroids currentCentroids){ //euclidean distance
      Double squareY = (currentCentroids.y - training.y) * (currentCentroids.y - training.y);
      Double squareX = (currentCentroids.x - training.x) * (currentCentroids.x - training.x);
      Double d = Math.sqrt(squareX + squareY);
      training.distance.add(d);
    }

    public void computeMoreDistance(Vectors training, Centroids currentCentroids){ //b is training data
        Double d = 0.0;
        for(int i = 0; i < training.list.size(); i+=2){
          Double squareOne = (currentCentroids.list.get(i) - training.list.get(i)) * (currentCentroids.list.get(i) - training.list.get(i));
          Double squareTwo = (currentCentroids.list.get(i+1) - training.list.get(i+1)) * (currentCentroids.list.get(i+1) - training.list.get(i+1));
          d = Math.sqrt(squareOne + squareTwo) + d;
        }
        training.distance.add(d);
    }

    public void initializeDuplicate(){
        trainingDataDuplicate = new ArrayList<Vectors>();
        for(int index = 0; index < trainingData.size(); index++){
            if(isTwo){
                Vectors newV = new Vectors(trainingData.get(index).x,trainingData.get(index).y);
                trainingDataDuplicate.add(newV);
            }
            else{
                ArrayList<Double> dble = new ArrayList<Double>();
                for(int j = 0; j < trainingData.get(index).list.size(); j++){
                    dble.add(trainingData.get(index).list.get(j));
                }
                Vectors newV = new Vectors(dble);
            }
            for(int j = 0; j < trainingData.get(index).distance.size(); j++){
                trainingDataDuplicate.get(index).distance.add(trainingData.get(index).distance.get(j));
            }
        }
    }

    public void initializeCentroids(){
        // Random rand = new Random();
        // if(isTwo){
        //     for (int index = 0 ; index < k*2; index+=2){
        //         Double x = rand.nextDouble(50);
        //         Double y = rand.nextDouble(50);
        //         Vectors centroid = new Centroids(x,y);
        //         currentCentroids.add(centroid);
        //     }
        // }
        Centroids first = new Centroids(0.0,0.0);
        Centroids second = new Centroids(2.0,3.0);
        currentCentroids.add(first);
        currentCentroids.add(second);
        Centroids prevfirst = new Centroids(999999.0,999999.0);
        Centroids prevSecond = new Centroids(999999.0,999999.0);
        prevCentroids.add(prevfirst);
        prevCentroids.add(prevSecond);
    }

    public void loadFile(String filename) { //load
        try{
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream inData = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(inData));
            String line;
            Double input;
            int in = 0;

            while((line  = br.readLine()) != null)
            {
                if(in > 0){
                    String[] values = line.split(" "); //stores all the words from the line in values
                    if(values.length > 3){
                      ArrayList<Double> l = new ArrayList<Double>();
                      for(int i = 0; i < values.length; i++){
                          l.add(Double.parseDouble(values[i]));
                      }
                      Vectors training = new Vectors(l);
                      trainingData.add(training);
                      isTwo = false;
                    }
                    else{
                      Vectors training = new Vectors(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                      trainingData.add(training);
                    }
                }
                else{
                  k = Integer.parseInt(line);
                  in++;
                }
            }
            min = trainingData.size()/2; 
            inData.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void saveFile(int x) { //writes file (wrong format tho)
        String filename = "output.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            writer.write(x+"\n");
            if(isTwo){
              writer.write("previous centroid -> ");
              for(int i = 0; i < prevCentroids.size(); i++){
                writer.write("centroid: " + prevCentroids.get(i).x +","+  prevCentroids.get(i).y+ "):  ");
              }
              writer.write("\n");
              writer.write("current centroid -> ");
              for(int i = 0; i < currentCentroids.size(); i++){
                writer.write("centroid: " + currentCentroids.get(i).x +","+  currentCentroids.get(i).y+ "):  ");
              }
              writer.write("\n");

            }
            writer.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");
        }
        catch(IOException ex) {
            System.out.println("Error writing file '" + filename + "'");
        }
    }

//     // public void saveFile() { //writes file
//     //     String filename = "output.txt";
//     //     try {
//     //         BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
//     //         if(isTwo){
//     //           writer.write("Nearest Neighbors of (" + x.x +","+  x.y+ "):");
//     //           for(int i = 0; i < answers.size(); i++){
//     //             writer.write("("+ answers.get(i).x+","+ answers.get(i).y +") ");
//     //           }
//     //           writer.write("\n");
//     //           writer.write("Class of (" + x.x +","+ x.y+ "):" + x.c);
//     //         }
//     //         else{
//     //           System.out.println("ANSWERS ");
//     //           writer.write("Nearest Neighbors of (");
//     //           for(int i = 0; i < x.list.size(); i++){
//     //             writer.write(x.list.get(i) +",");
//     //           }
//     //           writer.write("):");
//     //           for(int i = 0; i < answers.size(); i++){
//     //             writer.write("(");
//     //             for(int j = 0; j < answers.get(i).list.size(); j++){
//     //               writer.write(answers.get(i).list.get(j)+",");
//     //             }
//     //             writer.write(") ");
//     //           }
//     //           writer.write("\n");
//     //           writer.write("Class of (");
//     //           for(int i = 0; i < x.list.size(); i++){
//     //             writer.write(x.list.get(i) +",");
//     //           }
//     //           writer.write("):" + x.c);
//     //
//     //         }
//     //
//     //         writer.close();
//     //     }
//     //     catch(FileNotFoundException ex) {
//     //         System.out.println("Unable to open file '" + filename + "'");
//     //     }
//     //     catch(IOException ex) {
//     //         System.out.println("Error writing file '" + filename + "'");
//     //     }
//     // }
}
