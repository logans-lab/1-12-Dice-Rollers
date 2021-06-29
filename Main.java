package roller;
/*
 *  Attribution for dice icons: Icons made by Freepik and www.flaticon.com
 */

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

/**
 * Main class. Launches GUI interface to display dice and allow user to roll the dice.
 * User can set NUM_DICE between 1-12 before running application.
 * Currently, stage grows with the number of dice and remains proportional.
 * Greater than 12 dice is too wide for the screen. Future development will include continuing onto next row.
 * @author logan
 */
public class Main extends Application {

    public static final int NUM_DICE = 1; //TODO: Set number of dice here before launching application. (1 - 12)
    ArrayList<ImageView> diceArrayList = new ArrayList<>();
    Random random = new Random();

    /**
     * RollDie produces a random number for the next die roll.
     * @return A random number between 1 and 6.
     */
    public int rollDie() {
        return random.nextInt(6) + 1;
    }

    /**
     * Sets the gridpane, sets the starting displayed dice with values, which displays a corresponding dice image.
     * Sets Roll Dice button to the stage and utilizes 2 lambdas to rotate and then display new die faces upon button
     * click.
     * @param stage default stage for a GUI application.
     */
    @Override
    public void start(Stage stage) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(30);
        gridPane.setHgap(20);

        for (int i = 0; i < NUM_DICE; i++) {
            ImageView iv = new ImageView(new Image("/images/dice" + rollDie() + ".png", 100, 0, true, true));
            diceArrayList.add(i, iv);
        }

        //Roll Dice button
        Button roll = new Button("Roll Dice");
        roll.setMinSize(15, 25);
        roll.setMinWidth(100);
        roll.setMinHeight(50);
        roll.fontProperty().setValue(Font.font(18));

        roll.setOnAction(e -> {
            for (int i = 0; i < NUM_DICE; i++) {
                RotateTransition rt = new RotateTransition();
                rt.setByAngle(360);
                rt.setNode(diceArrayList.get(i));
                rt.setDuration(Duration.millis(600));
                rt.play();
                int x = i;
                rt.setOnFinished(j -> diceArrayList.get(x).setImage(new Image("/images/dice" + rollDie() + ".png", 100, 0, true, true)));
            }
        });

        //add each die node to gridPane layout. column, row
        for (int i = 0; i < NUM_DICE; i++) {
            gridPane.add(diceArrayList.get(i), i, 0);
        }
        gridPane.add(roll, 0, 1);
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane, 200 + 110 * NUM_DICE, 400);
        stage.setTitle("1 to 12 Dice Roller");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to launch application.
     * @param args standard args.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
