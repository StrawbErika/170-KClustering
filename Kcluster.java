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
      while(i!=1){
        System.out.println(i+" *************************************************************************************");
        getAllDistance();
        getKcluster();
        updateCentroids();
        i++;
      }
    }

    public Boolean comparePrevCurrent(){
      Boolean change = false;
      if(isTwo){
        for(int j = 0; j < currentCentroids.size(); j++){
          for(int index = 0; index < prevCentroids.size(); index++){
            if((currentCentroids.get(j).x!=prevCentroids.get(index).x) && (currentCentroids.get(j).y!=prevCentroids.get(index).y)){
              change = true;
            }
          }
        }
      }
      return change;
    }

    public void updateCentroids(){
      if(isTwo){
        for(int j=0; j < prevCentroids.size(); j++){
          prevCentroids.get(j).print();
        }
        for(int j = 0; j < currentCentroids.size(); j++){
          averageTwoDistance(currentCentroids.get(j));
        }
      }else{

      }

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

    // public void averageMoreDistance(Centroids c){
    //   // vectors.list
    //   //currentCentroids.vectors
    //
    //   //need to add all first index, second index third etc
    //   for(int j = 0; j < c.vectors.size(); j++){
    //     Vectors cNew = c.vectors.get(j);
    //     for(int index = 0; index < cNew.list.size(); index++){
    //       Double x = cNew.list.get(i);
    //   }
    //
    // }


    public void getKcluster(){
      Double distance = 0.0;
      int i = 0;
      for(int j=0; j < currentCentroids.size(); j++){
        getKnearest(currentCentroids.get(j), j);
      }
      initializeDuplicate();
    }
    public void getKnearest(Centroids c, int j){
      System.out.println("Getting " +min +" Nearest .............................................");
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
      System.out.println("Got Nearest .............................................");
      for(int x=0; x < currentCentroids.size(); x++){
        System.out.println("Centroid .............................................");
        currentCentroids.get(x).print();
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
        System.out.println("Computing Distance.......................................");
        for(int i = 0; i < trainingData.size(); i++){
            for(int j = 0; j < currentCentroids.size(); j++){
                computeDistance(trainingData.get(i), currentCentroids.get(j));
            }
        }
        System.out.println("These are the distances..................................");
        for(int i = 0; i < trainingData.size(); i++){
          trainingData.get(i).print();
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


// compute distance of k = 2
// 2 clusters too
// each point has a distance to 1 cluster so ArrayList<Double>

    public void computeDistance(Vectors training, Centroids currentCentroids){
      Double squareX = (currentCentroids.x - training.x) * (currentCentroids.x - training.x);
      Double squareY = (currentCentroids.y - training.y) * (currentCentroids.y - training.y);
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
        Centroids first = new Centroids(1.0,4.0);
        Centroids second = new Centroids(4.0,4.0);
        currentCentroids.add(first);
        currentCentroids.add(second);
        Centroids prevfirst = new Centroids(999999.0,999999.0);
        Centroids prevSecond = new Centroids(999999.0,999999.0);
        prevCentroids.add(first);
        prevCentroids.add(second);
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
            min = trainingData.size()/2;
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
