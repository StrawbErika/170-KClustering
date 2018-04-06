import java.io.*;
import java.util.*;

// Centroid has
// Starting list (could be x,y or x, y, z, ...)
// Vectors that are clustered to it


public class Centroids {
  public Double x, y; //if k = 2
  public ArrayList<Double> list; //if k > 2
  public ArrayList<Vectors> vectors; // those clustered to it

  public Centroids(Double x, Double y){
    this.x = x;
    this.y = y;
    this.vectors =  new ArrayList<Vectors>();
  }
  public Centroids(ArrayList<Double> list){
    this.list = list;
    this.vectors =  new ArrayList<Vectors>();
  }

  public void print(){
    System.out.println(this.x + " " + this.y);
    for(int i = 0; i < this.vectors.size(); i++){
        vectors.get(i).print();
    }
  }
  public void printMore(){
    for(int i = 0; i < this.list.size(); i++){
      System.out.print(this.list.get(i) + " ");
    }
  }


}
