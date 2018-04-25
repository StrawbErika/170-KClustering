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
    public ArrayList<ArrayList<Centroids>> iterations;
    public ArrayList<Centroids> currentCentroids;
    public ArrayList<Centroids> prevCentroids;
    public ArrayList<Vectors> trainingData;
    public ArrayList<Vectors> trainingDataDuplicate; 
    public Integer k;
    public Integer min;
    public Boolean isTwo;

    public Kcluster(){
        this.k = 0;
        this.min = 0;
        this.currentCentroids = new ArrayList<Centroids>();
        this.prevCentroids = new ArrayList<Centroids>();
        this.trainingData = new ArrayList<Vectors>();
        this.iterations = new ArrayList<ArrayList<Centroids>>();
    }

    public void findFinalCentroids(){
        initializeCentroids();
        int i = 0;
        boolean change = true;
        while(change){  
            if(i == 1){
                iterations.add(new ArrayList<Centroids>(prevCentroids));
            }
            saveThisFile(i);
            iterations.add(new ArrayList<Centroids>(prevCentroids));
            change = comparePrevCurrent(i);
            getAllDistance();
            getKcluster();
            updatePrevCentroids();
            updateCentroids();
            i++;
        }
        saveFile(i);
    }

    public void printThis(int x){
        for(int p = 0; p < iterations.size(); p++){
            for(int r = 0; r < iterations.get(p).size(); r++){
                for(int q = 0; q < iterations.get(p).get(r).list.size(); q++){
                    System.out.print(Double.toString(iterations.get(p).get(r).list.get(q)) + " ");
                }
                System.out.print("\n");
            }
        }

    }

    public Boolean comparePrevCurrent(int i){
        Boolean change = false;
        for(int j = 0; j < currentCentroids.size(); j++){
            
            Boolean isNotInPrev = false;
            isNotInPrev = isInArray(prevCentroids, currentCentroids.get(j));
            if(!isNotInPrev){
                change = true;
            }
        }
        return change;
    }

    public Boolean isInArray(ArrayList<Centroids> d, Centroids c){
        Boolean ans = false;
        for(int index = 0; index < d.size(); index++){
            for(int p = 0; p < c.list.size(); p++){
                if(d.get(index).list.get(p).equals(c.list.get(p))){
                    ans = true;
                }
            }
        }
        return ans;
    }

    public void updatePrevCentroids(){
        prevCentroids.clear();
        for(int j=0; j < currentCentroids.size(); j++){
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
        for(int j = 0; j < currentCentroids.size(); j++){
            averageDistance(currentCentroids.get(j));
        }

        clearVectorList();
        initializeDuplicate();
    }

    public Double averageColumn(Centroids c, int x){
        Double d = 0.0;
        for(int j = 0; j < c.vectors.get(x).list.size(); j++){
            for(int index = 0; index < c.vectors.size(); index++){ // c.v.l
               d = c.vectors.get(j).list.get(x) + d;
           }
        }
        d = d / c.vectors.size();
        return d;
    }

    public void averageDistance(Centroids c){
        ArrayList<Double> l = new ArrayList<Double>();
        for(int index = 0; index < c.vectors.get(1).list.size(); index++){
            Double x = averageColumn(c, index);
            l.add(x);
        }   
        c.list = l;
    }

    public void getKcluster(){
        Double distance = 0.0;
        int i = 0;
        for(int j=0; j < currentCentroids.size(); j++){
            getKnearest(currentCentroids.get(j), j);
        }
    }

    public void getKnearest(Centroids c, int j){
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
        for(int i = 0; i < trainingData.size(); i++){
            for(int j = 0; j < currentCentroids.size(); j++){
                computeDistance(trainingData.get(i), currentCentroids.get(j));
            }
        }
        this.initializeDuplicate();
    }

    public void computeDistance(Vectors training, Centroids currentCentroids){ //b is training data
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
            ArrayList<Double> dble = new ArrayList<Double>();
            for(int j = 0; j < trainingData.get(index).list.size(); j++){
                Double d = trainingData.get(index).list.get(j);
                dble.add(d);
            }
            Vectors newV = new Vectors(dble);
            trainingDataDuplicate.add(newV);
            
            for(int j = 0; j < trainingData.get(index).distance.size(); j++){
                trainingDataDuplicate.get(index).distance.add(trainingData.get(index).distance.get(j));
            }
        }
    }

    public Double getRandomDouble(){
        Random rand = new Random();
        int xInt = rand.nextInt(50);
        Double xD = rand.nextDouble();
        Double ans = xInt + xD;
        return ans;
    }

    public void initializeCentroids(){
        Random rand = new Random();
        for (int index = 0 ; index < k; index++){
            ArrayList<Double> l = new ArrayList<Double>();
            ArrayList<Double> l2 = new ArrayList<Double>();
            for(int j = 0; j < trainingData.get(0).list.size(); j++){
                Double x = getRandomDouble();
                l.add(x);

                Double x2 = 999999.0;
                l2.add(x2);
            }   
            Centroids centroid = new Centroids(l);
            Centroids centroid2 = new Centroids(l2);
            currentCentroids.add(centroid);
            prevCentroids.add(centroid2);
        }
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

                    if(values.length == 2){
                        isTwo = true;
                    }
                    else{
                        isTwo = false;
                    }

                    ArrayList<Double> l = new ArrayList<Double>();
                    for(int i = 0; i < values.length; i++){
                        l.add(Double.parseDouble(values[i]));
                    }
                    Vectors training = new Vectors(l);
                    trainingData.add(training);
                }
                else{
                  k = Integer.parseInt(line);
                  in++;
                }
            }
            min = trainingData.size()/k; 
            this.initializeDuplicate();
            
            inData.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void saveFile(int x) { //writes file (wrong format tho)
        String filename = "outputAnger.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for(int p = 0; p < iterations.size(); p++){
                writer.write(p + "\n");
                for(int r = 0; r < iterations.get(p).size(); r++){
                    Centroids n = iterations.get(p).get(r);
                    for(int q = 0; q < n.list.size(); q++){
                        writer.write(Double.toString(n.list.get(q)) + " ");
                    }
                    writer.write("\n");
                    for(int q = 0; q < n.vectors.size(); q++){
                        for(int s = 0; s < n.vectors.get(q).list.size(); s++){
                            writer.write(Double.toString(n.vectors.get(q).list.get(s)) + " ");
                        }
                        writer.write(q+"\n");
                    }
                    writer.write("\n");
                }
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
    public void saveThisFile(int x) { //writes file (wrong format tho)
        String filename = "output.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            writer.write("previous centroid -> ");
            writer.write("centroid: (");
            for(int i = 0; i < prevCentroids.size(); i++){
                writer.write("\n");
                for(int j = 0; j < prevCentroids.get(i).list.size(); j++){
                    writer.write(prevCentroids.get(i).list.get(j) + ", ");
                }
            }
            writer.write("\n");
            
            writer.write("current centroid -> ");
            writer.write("centroid: (");
            for(int i = 0; i < currentCentroids.size(); i++){
                writer.write("\n");
                for(int j = 0; j < currentCentroids.get(i).list.size(); j++){
                    writer.write(currentCentroids.get(i).list.get(j) + ", ");
                }
            }
            writer.write("\n");

            writer.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");
        }
        catch(IOException ex) {
            System.out.println("Error writing file '" + filename + "'");
        }

    }
}