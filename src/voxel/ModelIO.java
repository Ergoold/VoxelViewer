package voxel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Static IO methods for models.
 */
public class ModelIO {

    /**
     * Private constructor prevents instantiation.
     */
    private ModelIO() {
    }

    /**
     * Import a {@link Model} object from a CSV file.
     *
     * @param path the path of the CSV file to import from
     * @return a {@code Model} that contains all of the voxels in the CSV file, and only those
     * @throws IOException when failing to read file
     */
    public Model fromCSV(Path path) throws IOException {
        var lines = Files.readAllLines(path);
        var coordinates = new ArrayList<int[]>();
        for (var line : lines) {
            var coordinate = read(line);
            if (coordinate.length != 3) throw new IllegalArgumentException("expected 3 fields per line");
            coordinates.add(coordinate);
        }
        int maxX = 0, maxY = 0, maxZ = 0;
        for (var coordinate : coordinates) {
            if (maxX < coordinate[0]) maxX = coordinate[0];
            if (maxY < coordinate[1]) maxY = coordinate[1];
            if (maxZ < coordinate[2]) maxZ = coordinate[2];
        }
        var model = new Model(maxX, maxY ,maxZ);
        for (var coordinate : coordinates) model.addVoxel(coordinate[0], coordinate[1], coordinate[2]);
        return model;
    }

    /**
     * Reads a row of csv data into an array of integers.
     *
     * @param csvRow a row of csv data
     * @return an array of the integers in the csv row
     */
    private int[] read(String csvRow) {
        var values = csvRow.split(", ");
        var ints = new int[values.length];
        for (var i = 0; i < values.length; i++) ints[i] = Integer.parseInt(values[i]);
        return ints;
    }
}
