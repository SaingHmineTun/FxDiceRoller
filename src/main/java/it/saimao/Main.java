package it.saimao;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static final Image[] images = new Image[]{
            new Image(Main.class.getResource("/images/dice_1.png").toExternalForm()),
            new Image(Main.class.getResource("/images/dice_2.png").toExternalForm()),
            new Image(Main.class.getResource("/images/dice_3.png").toExternalForm()),
            new Image(Main.class.getResource("/images/dice_4.png").toExternalForm()),
            new Image(Main.class.getResource("/images/dice_5.png").toExternalForm()),
            new Image(Main.class.getResource("/images/dice_6.png").toExternalForm())
    };

    private static final List<String> numbers = List.of("ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX");

    private static final Font appFont = Font.loadFont(Main.class.getResource("/fonts/aj_kunheing_04.ttf").toExternalForm(), 14);
    private Label lbInfo;

    @Override
    public void start(Stage primaryStage) throws Exception {

        lbInfo = new Label("Welcome");
        lbInfo.setFont(Font.loadFont(Main.class.getResource("/fonts/aj_kunheing_04.ttf").toExternalForm(), 20));

        // Dic images : for showing dice numbers
        ImageView ivDice = new ImageView();
        ivDice.setImage(images[0]);
        ivDice.setFitWidth(125);
        ivDice.setFitHeight(125);

        // Button : for betting
        Button btBet = new Button("BET");
        btBet.setFont(appFont);
        btBet.setPrefSize(100, 40);
        Random rd = new Random();
        Timeline timeline = new Timeline();
        btBet.setOnAction(event -> {
            if (pickedButton == null) {
                lbInfo.setText("Please BET first!");
                lbInfo.setTextFill(Paint.valueOf("#781234"));
                return;
            }
            lbInfo.setText("Dice Rolling");
            for (int i = 0; i < 20; i++) {
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100 * i), e -> {
                    ivDice.setImage(images[rd.nextInt(numbers.size())]);
                }));
            }

            timeline.setOnFinished(e -> {

                String bet = pickedButton.getText();
                int diceNumber = rd.nextInt(numbers.size());
                ivDice.setImage(images[diceNumber]);
                if (diceNumber == numbers.indexOf(bet)) {
                    lbInfo.setText("You WON!");
                    lbInfo.setTextFill(Paint.valueOf("#127834"));
                } else {
                    lbInfo.setText("Try AGAIN!");
                    lbInfo.setTextFill(Paint.valueOf("#781234"));
                }
            });

            timeline.play();

        });

        // GridPane : for choosing the number
        GridPane gridPane = new GridPane();
        Button btOne = createNumberButton("ONE");
        Button btTwo = createNumberButton("TWO");
        Button btThree = createNumberButton("THREE");
        Button btFour = createNumberButton("FOUR");
        Button btFive = createNumberButton("FIVE");
        Button btSix = createNumberButton("SIX");
        gridPane.add(btOne, 0, 0);
        gridPane.add(btTwo, 1, 0);
        gridPane.add(btThree, 2, 0);
        gridPane.add(btFour, 0, 1);
        gridPane.add(btFive, 1, 1);
        gridPane.add(btSix, 2, 1);
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setAlignment(Pos.CENTER);

        VBox main = new VBox();
        main.setSpacing(20);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(10));
        main.getChildren().addAll(lbInfo, ivDice, btBet, gridPane);

        Scene scene = new Scene(main);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dice Roller");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(images[3]);
        primaryStage.show();
    }

    private Button pickedButton;
    private final Background normalBackground = Background.fill(Paint.valueOf("#123478"));
    private final Background activeBackground = Background.fill(Paint.valueOf("#127834"));

    private Button createNumberButton(String number) {
        Button btn = new Button(number);
        btn.setFont(appFont);
        btn.setOnAction(event -> {
            if (pickedButton != null) pickedButton.setBackground(normalBackground);
            pickedButton = (Button) event.getSource();
            pickedButton.setBackground(activeBackground);
        });
        btn.setBackground(Background.fill(Paint.valueOf("#123478")));

        Rectangle rectangle = new Rectangle(75, 75);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        btn.setShape(rectangle);

        btn.setTextFill(Paint.valueOf("#EEEEEE"));

        btn.setMinSize(75, 75);
        btn.setMaxSize(75, 75);
        return btn;
    }

}