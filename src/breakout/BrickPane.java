package breakout;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BrickPane {

    public static final int BRICK_GAP = 5;
    public static final int ROW_NUM = 8;
    public static final int COL_NUM = 6;

    private Brick[][] bricks;
    private Integer[][] brickRepresentations;
    private GridPane gridPane;

    public BrickPane(String filename) {
        bricks = new Brick[ROW_NUM][COL_NUM];
        brickRepresentations = new Integer[ROW_NUM][COL_NUM];
        readRepresentations(filename);
        makeBricks();
        setupPane();
    }

    private void readRepresentations(String filename) {
        String s = fileInput(filename);
        System.out.println(s);

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
            byte bt[] = new byte[(int) inputFile.length()];
            in.read(bt);
            s = new String(bt);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
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
            ColumnConstraints columnConstraints = new ColumnConstraints((double) (Engine.BG_WIDTH - (COL_NUM - 1) * BRICK_GAP) / COL_NUM);
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

    public GridPane getInstance() {
        return gridPane;
    }

    public Brick[][] getBricks() {
        return bricks;
    }

    public void updateBrickStatus(int r, int c) {
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == r && GridPane.getColumnIndex(node) == c);

        if (bricks[r][c] != null) {
            gridPane.add(bricks[r][c].getInstance(), c, r);
        }
    }

    public PowerUp checkPowerUp(int r, int c) {
        PowerUp powerUp = null;
        if (bricks[r][c].takeDamage()) {
            if (bricks[r][c].hasPowerUp()) {
                powerUp = bricks[r][c].getPowerUp();
            }
            bricks[r][c] = null;
        }
        return powerUp;
    }
}
