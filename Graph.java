import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.lang.*;


public class Graph extends JPanel {
    public ArrayList<Centroids> centroids;
    public int k;
    public Graph(){
        k = 0;
        centroids = new ArrayList<Centroids>();
    }
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;

        gg.drawLine(15, getHeight() - 15, 15, 15);
        gg.drawLine(15, getHeight() - 15, getWidth() - 15, getHeight() - 15);

        // create hatch marks for y axis.
        for (int i = 0; i < 10; i++) {
            int x0 = 15;
            int x1 = 12 + 15;
            int y0 = getHeight() - (((i + 1) * (getHeight() - 15 * 2)) / 10 + 15);
            int y1 = y0;
            gg.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < centroids.get(0).vectors.size() - 1; i++) {
            int x0 = (i + 1) * (getWidth() - 15 * 2) / (centroids.get(0).vectors.size() - 1) + 15;
            int x1 = x0;
            int y0 = getHeight() - 15;
            int y1 = y0 - 12;
            gg.drawLine(x0, y0, x1, y1);
        }

        ArrayList<Color> colors = pick(this.k);
 
    
        // for(int i = 0; i < centroids.get(0).vectors.size(); i++){
        //       if(Centroids.get(i).c == 0){
        //         gg.setColor(Color.RED);
        //       }
        //       else{
        //         gg.setColor(Color.GREEN);
        //       }
        //       Double c = Centroids.get(i).x*100;
        //       Double d = Centroids.get(i).y*100;
        //       int a = c.intValue();
        //       int b = d.intValue();
        //       gg.fillOval(a,b,20,20);
        // }

    }

    public ArrayList<Color> pick(int num) {
        ArrayList<Color> colors = new ArrayList<Color>();
        if (num < 2)
            return colors;
            float dx = 1.0f / (float) (num - 1);
            for (int i = 0; i < num; i++) {
            colors.add(get(i * dx));
        }
        return colors;
    }

    public Color get(float x) {
        float r = 0.0f;
        float g = 0.0f;
        float b = 1.0f;
        if (x >= 0.0f && x < 0.2f) {
            x = x / 0.2f;
            r = 0.0f;
            g = x;
            b = 1.0f;
        } else if (x >= 0.2f && x < 0.4f) {
            x = (x - 0.2f) / 0.2f;
            r = 0.0f;
            g = 1.0f;
            b = 1.0f - x;
        } else if (x >= 0.4f && x < 0.6f) {
            x = (x - 0.4f) / 0.2f;
            r = x;
            g = 1.0f;
            b = 0.0f;
        } else if (x >= 0.6f && x < 0.8f) {
            x = (x - 0.6f) / 0.2f;
            r = 1.0f;
            g = 1.0f - x;
            b = 0.0f;
        } else if (x >= 0.8f && x <= 1.0f) {
            x = (x - 0.8f) / 0.2f;
            r = 1.0f;
            g = 0.0f;
            b = x;
        }
        return new Color(r, g, b);
    }

}
