package voxel;

/**
 * A 3d voxel model.
 * <p>
 * There are three axes: x / width, y / height, z / depth.
 */
public class Model {

    /**
     * Which voxels are present and which are missing.
     * <p>
     * The x and y axes are switched.
     */
    private final boolean[][][] voxels;

    public Model(int width, int height, int depth) {
        voxels = new boolean[height][width][depth];
    }

    public void addVoxel(int x, int y, int z) {
        voxels[y][x][z] = true;
    }

    public boolean isVoxelPresent(int x, int y, int z) {
        return voxels[y][x][z];
    }

    public boolean[][] layer(int layer) {
        return voxels[layer];
    }

    public int width() {
        return voxels[0].length;
    }

    public int depth() {
        return voxels[0][0].length;
    }
}
