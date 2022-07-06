import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class LBlock extends Block {

    private List<int[][]> blockMatrix = new ArrayList<>();

    public LBlock() {
        super(Color.GREEN);

        blockMatrix.add(new int[][] {
                {0, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 5, 5, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {0, 5, 5, 5},
                {0, 5, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {5, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {0, 0, 5, 0},
                {5, 5, 5, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }


    public List<int[][]> getBlockMatrix() {
        return blockMatrix;
    }
}