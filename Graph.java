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

        gg.drawLine(5, getHeight() - 5, 5, 5);
        gg.drawLine(5, getHeight() - 5, getWidth() - 5, getHeight() - 5);

        // create hatch marks for y axis.
        for (int i = 0; i < 10; i++) {
            int x0 = 5;
            int x1 = 7 + 5;
            int y0 = getHeight() - (((i + 1) * (getHeight() - 5 * 2)) / 10 + 5);
            int y1 = y0;
            gg.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        if(centroids.size() > 0){
            for (int i = 0; i < centroids.get(0).vectors.size() - 1; i++) {
                int x0 = (i + 1) * (getWidth() - 5 * 2) / (centroids.get(0).vectors.size() - 1) + 5;
                int x1 = x0;
                int y0 = getHeight() - 5;
                int y1 = y0 - 7;
                gg.drawLine(x0, y0, x1, y1);
            }
        }

        ArrayList<Color> colors = pick(this.k);
 
        for(int j = 0; j < centroids.size(); j++){
            for(int i = 0; i < centroids.get(j).vectors.size(); i++){
                Color point = colors.get(j);
                gg.setColor(point);
                Double c = centroids.get(j).vectors.get(i).list.get(0)*10;
                Double d = centroids.get(j).vectors.get(i).list.get(1)*10;
                int a = c.intValue();
                int b = d.intValue();

                Double c2 = centroids.get(j).list.get(0)*10;
                Double d2 = centroids.get(j).list.get(1)*10;
                int a2 = c2.intValue();
                int b2 = d2.intValue();
                
                gg.fillOval(a,b,10,10);
                gg.fillOval(a2,b2,10,10);
            }
        }

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
