package edu.farmingdale.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.util.Objects;
import java.util.Random;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label answerLabel;
    @FXML
    private Button refreshButton, verifyButton;
    @FXML
    private TextField textField;
    @FXML
    private ImageView firstView, secondView, thirdView, fourthView;

    Image[][] cardImageViews = new Image[13][4];
    public Random random = new Random();

    int index1 = random.nextInt(13);
    int index2 = random.nextInt(13);
    int index3 = random.nextInt(13);
    int index4 = random.nextInt(13);

    int suit1 = random.nextInt(4);
    int suit2 = random.nextInt(4);
    int suit3 = random.nextInt(4);
    int suit4 = random.nextInt(4);

    int[] cardValues = new int[4];
    String[] ranks = {"1","2", "3", "4", "5", "6", "7", "8", "9", "10","11", "12", "13"};
    String[] suits = {"clubs","diamonds" , "hearts","spades" };

    @FXML
    void initialize() {
        String folderPath = "src/main/resources/edu/farmingdale/demo/img";
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, img) -> img.toLowerCase().endsWith(".png"));

        if (files != null) {


            for (int rankIndex = 0; rankIndex < ranks.length; rankIndex++) {
                for (int suitIndex = 0; suitIndex < suits.length; suitIndex++) {
                    String fileName = ranks[rankIndex] + "_of_" + suits[suitIndex] + ".png";
                    File cardFile = new File(folderPath + "/" + fileName);
                    if (cardFile.exists()) {
                        Image image = new Image(cardFile.toURI().toString());
                        cardImageViews[rankIndex][suitIndex] = image;
                    }
                }
            }
        }
        firstView.setImage(cardImageViews[index1][suit1]);
        secondView.setImage(cardImageViews[index2][suit2]);
        thirdView.setImage(cardImageViews[index3][suit3]);
        fourthView.setImage(cardImageViews[index4][suit4]);
    }
    @FXML
    protected void onHelloButtonClick() {
        String expression = textField.getText();
        if (isValidExpression(expression)) {
            try {
                double result = evaluateExpression(expression);

                if (result == 24) {
                    answerLabel.setText("Correct! The expression evaluates to 24.");
                }else{
                    answerLabel.setText("The expression does not evaluates to 24. Try again.");
                }

            } catch (Exception e) {
                answerLabel.setText("Error evaluating expression.");
                System.out.println(e);
            }
        }
    }
    @FXML
    protected void refreshImage() {
        index1 = random.nextInt(13);
        index2 = random.nextInt(13);
        index3 = random.nextInt(13);
        index4 = random.nextInt(13);

        suit1 = random.nextInt(4);
        suit2 = random.nextInt(4);
        suit3 = random.nextInt(4);
        suit4 = random.nextInt(4);

        firstView.setImage(cardImageViews[index1][suit1]);
        secondView.setImage(cardImageViews[index2][suit2]);
        thirdView.setImage(cardImageViews[index3][suit3]);
        fourthView.setImage(cardImageViews[index4][suit4]);

        textField.clear();
        answerLabel.setText("");

    }
    private boolean isValidExpression(String expression) {

        int firstRank,secondRank,thirdRank,fourthRank;
        String first,second,third,fourth;
        first = Integer.toString(index1+1);
        second = Integer.toString(index2+1);
        third = Integer.toString(index3+1);
        fourth = Integer.toString(index4+1);
        String expresions = "^[0-9\\+\\-\\*/\\(\\)\\s]+$";
        //split text field
        return expression.matches(expresions);
    }
    private double evaluateExpression(String expression) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        Object result = engine.eval(expression);
        return ((Number) result).doubleValue();
    }

}
