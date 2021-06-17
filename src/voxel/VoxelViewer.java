package voxel;

import javax.swing.*;
import java.io.IOException;

public class VoxelViewer {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Voxel Viewer");
        var pane = new VoxelContentPane(frame::pack);
        frame.setContentPane(pane);
        // The first pack must be done here because pane is not the content pane when it is created, and the callback
        // will not have and effect yet.
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
