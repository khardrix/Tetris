package objects.tetraminos;

import objects.Block;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class JBlock extends Block {

    private List<int[][]> blockMatrix = new ArrayList<>();

    public JBlock() {
        super(Color.BLUE);

        blockMatrix.add(new int[][] {
                {0, 6, 0, 0},
                {0, 6, 0, 0},
                {6, 6, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {0, 6, 0, 0},
                {0, 6, 6, 6},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {0, 6, 6, 0},
                {0, 6, 0, 0},
                {0, 6, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {6, 6, 6, 0},
                {0, 0, 6, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }


    public List<int[][]> getBlockMatrix() {
        return blockMatrix;
    }
}
