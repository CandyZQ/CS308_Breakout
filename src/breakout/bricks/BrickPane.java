package breakout.bricks;

import breakout.Engine;
import breakout.elements.PowerUp;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The BrickPane class is a container of all bricks.Instead of explicitly
 * creating all bricks, creates a BrickPane that reads brick layout from
 * a txt file.
 * <p></p>
 *
 * Example code: the following code creates a BrickPane with
 * "map_level_0.txt" and simulates when a ball hits brick at
 * (0,0).
 * <p></p>
 * {@code
 *  import breakout.bricks.BrickPane;
 *
 *  BrickPane bp = new BrickPane("map_level_0.txt");
 *  bp.updateBrickStatus(0, 0);
 * }
 * author Cady Zhou
 * @version 1.1
 * @since 1.1
 * @see Brick
 */

public class BrickPane {
    public static final int BRICK_GAP = 5;
    public static final int ROW_NUM = 8;
    public static final int COL_NUM = 6;

    private Brick[][] bricks;
    private Integer[][] brickRepresentations;
    private GridPane gridPane;

    /**
     * Creates a brickPane with given brick configuration.
     * @param filename  a txt file that contains brick configuration
     */
    public BrickPane(String filename) {
        bricks = new Brick[ROW_NUM][COL_NUM];
        brickRepresentations = new Integer[ROW_NUM][COL_NUM];
        readRepresentations(filename);
        makeBricks();
        setupPane();
    }

    private void readRepresentations(String filename) {
        String s = fileInput(filename);

        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                brickRepresentations[r][c] = (int) s.charAt(r * COL_NUM * 2 + r + c * 2) - 48;
            }
        }
    }

    private String fileInput(String filename) {
        FileInputStream in = null;
        String s = null;
        try {
            File inputFile = new File(filename);
            in = new FileInputStream(inputFile);
            byte[] bt = new byte[(int) inputFile.length()];
            in.read(bt);
            s = new String(bt);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    private void makeBricks() {
        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                if (brickRepresentations[r][c] == 0) {
                    bricks[r][c] = null;
                } else {
                    bricks[r][c] = new Brick(brickRepresentations[r][c], r, c);
                }
            }
        }
    }

    private void setupPane() {
        gridPane = new GridPane();
        gridPane.setPrefSize(Engine.BG_WIDTH, Engine.BG_HEIGHT);

        gridPane.setHgap(BRICK_GAP);
        gridPane.setVgap(BRICK_GAP);

        for (int c = 0; c < COL_NUM; c++) {
            ColumnConstraints columnConstraints = new ColumnConstraints( (Engine.BG_WIDTH - (COL_NUM - 1) * BRICK_GAP) / (double) COL_NUM);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int r = 0; r < ROW_NUM; r++) {
            RowConstraints rowConstraints = new RowConstraints(Brick.BRICK_HEIGHT + (double) BRICK_GAP / 2);
            gridPane.getRowConstraints().add(rowConstraints);
            for (int c = 0; c < COL_NUM; c++) {
                if (bricks[r][c] != null) {
                    gridPane.add(bricks[r][c].getInstance(), c, r);
                }
            }
        }
    }

    /**
     * Update brick color to reflect its {@code level}
     * @param r     row of that brick
     * @param c     col of that brick
     * @see Brick
     */
    public void updateBrickStatus(int r, int c) {
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == r && GridPane.getColumnIndex(node) == c);

        if (bricks[r][c] != null) {
            gridPane.add(bricks[r][c].getInstance(), c, r);
        }
    }

    /**
     * Gets the {@link PowerUp} before setting a brick to null when being
     * hit and about to disappear.
     * @param r     row of that brick
     * @param c     col of that brick
     * @return      a {@link PowerUp} of that brick, null if that brick
     *              does not have a {@link PowerUp}
     * @see PowerUp
     */
    public PowerUp checkPowerUp(int r, int c) {
        PowerUp powerUp = null;
        if (bricks[r][c].beingHit()) {
            if (bricks[r][c].hasPowerUp()) {
                powerUp = bricks[r][c].getPowerUp();
            }
            bricks[r][c] = null;
        }
        return powerUp;
    }

    /**
     * Gets an instance of this gridPane.
     * @return  this gridPane
     */
    public GridPane getInstance() {
        return gridPane;
    }

    /**
     * Gets all brick instances in this brickPane.
     * @return  a 2D array of bricks
     */
    public Brick[][] getBricks() {
        return bricks;
    }


}
