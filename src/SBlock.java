import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SBlock extends Block {

    private List<int[][]> blockMatrix = new ArrayList<>();

    public SBlock() {
        super(Color.ORANGE);

        blockMatrix.add(new int[][] {
                {0, 3, 3, 0},
                {3, 3, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {3, 0, 0, 0},
                {3, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });
    }


    public List<int[][]> getBlockMatrix() {
        return blockMatrix;
    }
}