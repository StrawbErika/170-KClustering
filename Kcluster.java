import java.io.*;
import java.util.*;
import java.lang.Math.*;

public class Kcluster {

    public ArrayList<Centroids> centroids;
    public ArrayList<Vectors> trainingData;
    public ArrayList<Vectors> trainingDataDuplicate;
    public Integer k;
    public Integer min;
    public Boolean isTwo;

    public Kcluster(){
        this.isTwo = true;
        this.k = 0;
        this.min = 0;
        this.centroids = new ArrayList<Centroids>();
        this.trainingData = new ArrayList<Vectors>();
    }

    public void updateCentroids(){

    }

    public void averageTwoDistance(Centroids c){
        Double x = 0.0;
        Double y = 0.0;
        for(int j = 0; j < c.vectors.size(); j++){
            Vectors cNew = c.vectors.get(j);
            x = cNew.x + x;
            y = cNew.y + y;
        }
    }


    public void getKcluster(){
      Double distance = 0.0;
      int i = 0;
      while(i!=min){
          for(int j=0; j < centroids.size(); j++){
              Vectors n = getNearest(j);
              if(i == 0){
                  centroids.get(j).vectors.add(n);
                  trainingDataDuplicate.remove(n);
                  i++;
              }
              else {
                  if(!centroids.get(j).vectors.contains(n)){
                      centroids.get(j).vectors.add(n);
                      trainingDataDuplicate.remove(n);
                      i++;
                  }
              }
          }
        }
        for(int j=0; j < centroids.size(); j++){
            centroids.get(j).print();
        }
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
      if(isTwo){
        for(int i = 0; i < trainingData.size(); i++){
            for(int j = 0; j < centroids.size(); j++){
                computeDistance(trainingData.get(i), centroids.get(j));
            }
        }
      }
      else{
        for(int i = 0; i < trainingData.size(); i++){
            for(int j = 0; j < centroids.size(); j++){
                computeMoreDistance(trainingData.get(i), centroids.get(j));
            }
        }
      }
      this.initializeDuplicate();
    }


// compute distance of k = 2
// 2 clusters too
// each point has a distance to 1 cluster so ArrayList<Double>

    public void computeDistance(Vectors training, Centroids centroids){
      Double squareX = (centroids.x - training.x) * (centroids.x - training.x);
      Double squareY = (centroids.y - training.y) * (centroids.y - training.y);
      Double d = Math.sqrt(squareX + squareY);
      training.distance.add(d);
    }

    public void computeMoreDistance(Vectors training, Centroids centroids){ //b is training data
        Double d = 0.0;
        for(int i = 0; i < training.list.size(); i+=2){
          Double squareOne = (centroids.list.get(i) - training.list.get(i)) * (centroids.list.get(i) - training.list.get(i));
          Double squareTwo = (centroids.list.get(i+1) - training.list.get(i+1)) * (centroids.list.get(i+1) - training.list.get(i+1));
          d = Math.sqrt(squareOne + squareTwo) + d;
        }
        training.distance.add(d);
    }

    public void initializeCentroids(){
        // Random rand = new Random();
        // if(isTwo){
        //     for (int index = 0 ; index < k*2; index+=2){
        //         Double x = rand.nextDouble(50);
        //         Double y = rand.nextDouble(50);
        //         Vectors centroid = new Centroids(x,y);
        //         centroids.add(centroid);
        //     }
        // }
        Centroids first = new Centroids(1.0,4.0);
        Centroids second = new Centroids(4.0,4.0);
        centroids.add(first);
        centroids.add(second);
    }
    //
    // public void updateCentroids(){
    //
    // }
    //

    public void loadFile(String filename) {
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
            min = trainingData.size();
            // System.out.println("hoi");
            // for(int j = 0 ; j < trainingData.size(); j++){
            //     trainingData.get(j).print();
            // }
            inData.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    // public void saveFile() { //writes file
    //     String filename = "output.txt";
    //     try {
    //         BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
    //         if(isTwo){
    //           writer.write("Nearest Neighbors of (" + x.x +","+  x.y+ "):");
    //           for(int i = 0; i < answers.size(); i++){
    //             writer.write("("+ answers.get(i).x+","+ answers.get(i).y +") ");
    //           }
    //           writer.write("\n");
    //           writer.write("Class of (" + x.x +","+ x.y+ "):" + x.c);
    //         }
    //         else{
    //           System.out.println("ANSWERS ");
    //           writer.write("Nearest Neighbors of (");
    //           for(int i = 0; i < x.list.size(); i++){
    //             writer.write(x.list.get(i) +",");
    //           }
    //           writer.write("):");
    //           for(int i = 0; i < answers.size(); i++){
    //             writer.write("(");
    //             for(int j = 0; j < answers.get(i).list.size(); j++){
    //               writer.write(answers.get(i).list.get(j)+",");
    //             }
    //             writer.write(") ");
    //           }
    //           writer.write("\n");
    //           writer.write("Class of (");
    //           for(int i = 0; i < x.list.size(); i++){
    //             writer.write(x.list.get(i) +",");
    //           }
    //           writer.write("):" + x.c);
    //
    //         }
    //
    //         writer.close();
    //     }
    //     catch(FileNotFoundException ex) {
    //         System.out.println("Unable to open file '" + filename + "'");
    //     }
    //     catch(IOException ex) {
    //         System.out.println("Error writing file '" + filename + "'");
    //     }
    // }
}
