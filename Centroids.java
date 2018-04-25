import java.io.*;
import java.util.*;

// Centroid has
// Starting list (could be x,y or x, y, z, ...)
// Vectors that are clustered to it


public class Centroids {
    public ArrayList<Double> list; //if k > 2
    public ArrayList<Vectors> vectors; // those clustered to it

    public Centroids(ArrayList<Double> list){
        this.list = list;
        this.vectors = new ArrayList<Vectors>();
    }

    public Centroids(Centroids c){
        this.list = new ArrayList<Double>();
        this.vectors = new ArrayList<Vectors>();

        for(int i = 0; i < c.vectors.size(); i++){
            Vectors newV = new Vectors(c.vectors.get(i));
            this.vectors.add(newV);
        }

        for(int i = 0; i < c.list.size(); i++){
            Double d = new Double(c.list.get(i));
            this.list.add(d);
        }

    }

    public void print(){
        for(int q = 0; q < this.list.size(); q++){
            System.out.print(this.list.get(q) + " ");
        }
        System.out.println(" ");
        for(int q = 0; q < this.vectors.size(); q++){
            for(int s = 0; s < this.vectors.get(q).list.size(); s++){
                    System.out.print(this.vectors.get(q).list.get(s) + " ");
            }
            System.out.println(q);
        }
    }
}
