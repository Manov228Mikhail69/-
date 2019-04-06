package risov;


import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


public class Main extends Application {

    private static final int width = 800;
    private static final int height = 600;

    private static final int RACKET_WIDTH = 10;
    private static final int RACKET_HEIGHT = 90;

    private static final int BALL_RAD = 30;

    double playerX = 0;
    double playerY = height / 2;

    double compX = width - RACKET_WIDTH;
    double compY = height / 2;

    double ballX = width / 2;
    double ballY = height / 2;

    GraphicsContext gc;

    double ballSpeed = 3;

    double directionX = 1;
    double directionY  = 1;

    boolean gameStarted;

    private void drawTable() {
        
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, width, height);
       
        gc.setFill(Color.CYAN);
        gc.fillRect(width / 2, 0, 2, height);
    
        if (gameStarted) {

            if(ballY >= height )
            {
                directionY = -1;
            }

            if(ballX >= width)
            {
                directionX = -1;
            }

            if(ballY <= 0 )
            {
                directionY = 1;
            }

            if(ballX <= 0)
            {
                directionX = 1;
            }

            ballX += directionX * ballSpeed;
            ballY += directionY * ballSpeed;

            if (ballX < width - width / 4) {
                compY = ballY - RACKET_HEIGHT / 2;
            }

            gc.fillOval(ballX, ballY, BALL_RAD, BALL_RAD);

        } else {
            gc.setStroke(Color.CYAN);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to start", width / 2, height / 2);
        }

        
        gc.fillRect(playerX, playerY, RACKET_WIDTH, RACKET_HEIGHT);
        gc.fillRect(compX, compY, RACKET_WIDTH, RACKET_HEIGHT);       
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, width, 2);
        
        gc.setFill(Color.RED);
        gc.fillRect(0, height - 2, width, 2);

        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, 2, height);

        gc.setFill(Color.BLUE);
        gc.fillRect(width - 2, 0, 2, height);
    }

    @Override
    public void start(Stage root) {
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        drawTable();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e -> drawTable()));
        t1.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseClicked(e -> gameStarted = true);
        canvas.setOnMouseMoved(e -> playerY = e.getY());

        root.setScene(new Scene(new StackPane(canvas)));
        root.setTitle("Ping-pong");
        root.show();
        t1.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}