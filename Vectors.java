import java.io.*;
import java.util.*;

public class Vectors {
  public ArrayList<Double> list;
  public ArrayList<Double> distance;

  public Vectors(ArrayList<Double> list){
    this.list = list;
    this.distance = new ArrayList<Double>();
  }

  public Vectors(Vectors v){
    this.distance =  new ArrayList<Double>();
    this.list =  new ArrayList<Double>();
    
    for(int i = 0; i < v.distance.size(); i++){
      Double newV = new Double(v.distance.get(i));
      this.distance.add(newV);
    }

    for(int i = 0; i < v.list.size(); i++){
      Double newV = new Double(v.list.get(i));
      this.list.add(newV);
    }
  }

  public void print(){
    for(int i = 0; i < this.list.size(); i++){
      System.out.print(this.list.get(i) + " ");
    }
  }
  public void emptyDistances(){
      this.distance = new ArrayList<Double>();
  }


}
