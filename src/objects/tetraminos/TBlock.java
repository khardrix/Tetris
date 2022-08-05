package objects.tetraminos;

import objects.Block;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class TBlock extends Block {

    private List<int[][]> blockMatrix = new ArrayList<>();

    public TBlock() {
        super(Color.PINK);

        blockMatrix.add(new int[][] {
                {7, 7, 7, 0},
                {0, 7, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {0, 7, 0, 0},
                {7, 7, 7, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {0, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 7, 0, 0},
                {0, 0, 0, 0}
        });
    }


    public List<int[][]> getBlockMatrix() {
        return blockMatrix;
    }
}