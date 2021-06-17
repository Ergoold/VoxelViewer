package voxel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static java.awt.GridBagConstraints.*;

/**
 * The main content pane for the voxel viewer, containing the viewer itself and the controls.
 */
public class VoxelContentPane extends JPanel {

    /**
     * The default blank model displayed before a model is opened by the user.
     */
    private static final Model DEFAULT_MODEL = new Model(16, 16, 16);

    /**
     * The inset above and below each component.
     */
    private static final int VERTICAL_INSET = 4;

    /**
     * The inset to the left and right of each component.
     */
    private static final int HORIZONTAL_INSET = 8;

    /**
     * The pane that displays the actual model.
     */
    private ModelPane modelPane;

    /**
     * The slider determining the currently viewed layer.
     */
    private JSlider layer;

    /**
     * The spinner determining the amount of ghost layers displayed.
     */
    private JSpinner ghosts;

    /**
     * The button for opening a new model.
     */
    private JButton open;

    /**
     * The file chooser for opening new model files.
     */
    private final JFileChooser fileChooser = new JFileChooser();

    /**
     * The callback for packing the containing frame.
     */
    private final VoidCallback pack;

    public VoxelContentPane(VoidCallback pack) {
        this.pack = pack;
        modelPane = new ModelPane();
        layer = new JSlider(JSlider.VERTICAL, 0, 0, 0);
        layer.setMajorTickSpacing(5);
        layer.setMinorTickSpacing(1);
        layer.setPaintLabels(true);
        layer.setPaintTicks(true);
        ghosts = new JSpinner();
        openModel(DEFAULT_MODEL);
        open = new JButton("Open");
        open.setMnemonic('o');
        setLayout(new GridBagLayout());
        add(modelPane, gridBagConstraints(0, 0, 2));
        add(layer, gridBagConstraints(2, 0));
        add(ghosts, gridBagConstraints(0, 1));
        add(open, gridBagConstraints(1, 1, 2, LINE_END));
        open.addActionListener(e -> {
            var option = fileChooser.showOpenDialog(this);
            switch (option) {
                case JFileChooser.APPROVE_OPTION -> {
                    try {
                        var model = ModelIO.fromCSV(fileChooser.getSelectedFile().toPath());
                        openModel(model);
                    } catch (IOException ioException) {
                        showError();
                    }
                }
                case JFileChooser.CANCEL_OPTION -> {
                }
                case JFileChooser.ERROR_OPTION -> showError();
                default -> throw new IllegalStateException("Unexpected value: " + option);
            }
        });
        layer.addChangeListener(e -> {
            modelPane.setLayer(layer.getValue());
            repaint();
        });
        ghosts.addChangeListener(e -> {
            modelPane.setGhosts((int) ghosts.getValue());
            repaint();
        });
    }

    private void openModel(Model model) {
        modelPane.setLayer(0);
        modelPane.setGhosts(0);
        modelPane.setModel(model);
        var maxY = modelPane.model().height() - 1;
        System.out.println(maxY);
        layer.setValue(0);
        layer.setMaximum(maxY);
        ghosts.setModel(new SpinnerNumberModel(0, 0, maxY, 1));
        repaint();
        pack.invoke();
    }

    private void showError() {
        JOptionPane.showMessageDialog(this, "Error when opening file.", "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private GridBagConstraints gridBagConstraints(int x, int y, int width, int anchor) {
        var insets = new Insets(VERTICAL_INSET, HORIZONTAL_INSET, VERTICAL_INSET, HORIZONTAL_INSET);
        return new GridBagConstraints(x, y, width, 1, 0., 0., anchor, NONE, insets, 0, 0);
    }

    private GridBagConstraints gridBagConstraints(int x, int y, int width) {
        return gridBagConstraints(x, y, width, CENTER);
    }

    private GridBagConstraints gridBagConstraints(int x, int y) {
        return gridBagConstraints(x, y, 1);
    }
}
