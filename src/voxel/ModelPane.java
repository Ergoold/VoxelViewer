package voxel;

import javax.swing.*;
import java.awt.*;

/**
 * A pane that displays a layer or layers of a model.
 */
public class ModelPane extends JPanel {

    /**
     * The size of each voxel in the view.
     */
    private static final int VOXEL_SIZE = 16;

    /**
     * The model this pane displays.
     */
    private Model model;

    /**
     * The layer of the model that is displayed.
     */
    private int layer = 0;

    /**
     * The amount of ghost layers below this layer to display.
     */
    private int ghosts = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (var i = ghosts; i >= 0; i--) {
            var x = (int) (255. / (ghosts + 1) * i);
            g.setColor(new Color(x, x, x));
            var coordinates = model.layer(layer - i);
            for (int row = 0; row < coordinates.length; row++) {
                final boolean[] rowArr = coordinates[row];
                for (int col = 0; col < rowArr.length; col++) {
                    if (rowArr[col]) {
                        g.fillRect(row * VOXEL_SIZE, col * VOXEL_SIZE, VOXEL_SIZE, VOXEL_SIZE);
                    }
                }
            }
        }
    }

    public Model model() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        setPreferredSize(new Dimension(model.width() * VOXEL_SIZE, model.depth() * VOXEL_SIZE));
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setGhosts(int ghosts) {
        this.ghosts = ghosts;
    }
}
