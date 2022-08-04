package tetraminos;

import objects.Block;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class IBlock extends Block {

    private List<int[][]> blockMatrix = new ArrayList<>();

    public IBlock() {
        super(Color.PURPLE);

        blockMatrix.add(new int[][] {
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {0, 0, 0, 0},
                {2, 2, 2, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }


    public List<int[][]> getBlockMatrix() {
        return blockMatrix;
    }
}