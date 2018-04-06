import java.io.*;
import java.util.*;

public class Vectors {
  public Double x, y;
  public ArrayList<Double> list;
  public ArrayList<Double> distance;

  public Vectors(Double x, Double y){
    this.x = x;
    this.y = y;
    this.distance = new ArrayList<Double>();
  }
  public Vectors(ArrayList<Double> list){
    this.list = list;
    this.distance = new ArrayList<Double>();
  }

  public void print(){
    System.out.println(this.x + " " + this.y +" " + this.distance);
  }
  public void printMore(){
    for(int i = 0; i < this.list.size(); i++){
      System.out.print(this.list.get(i) + " ");
    }
  }
  public void emptyDistances(){
      this.distance = new ArrayList<Double>();
  }


}
