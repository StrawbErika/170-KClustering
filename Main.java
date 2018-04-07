import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Kcluster nearest = new Kcluster();
        nearest.loadFile("input.txt");
        // nearest.initializeCentroids();
        // nearest.getAllDistance();
        // nearest.getKcluster();
        // nearest.updateCentroids();
        nearest.findFinalCentroids();

        // UI ui = new UI();
    }
}
