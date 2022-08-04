package tetraminos;

import objects.Block;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class OBlock extends Block {

    private List<int[][]> blockMatrix = new ArrayList<>();

    public OBlock() {
        super(Color.YELLOW);

        blockMatrix.add(new int[][] {
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }


    public List<int[][]> getBlockMatrix() {
        return blockMatrix;
    }
}