import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Kcluster nearest = new Kcluster();
        nearest.loadFile("input.txt");
        nearest.findFinalCentroids();
        UI ui = new UI();
        // UI ui = new UI();
    }
}
