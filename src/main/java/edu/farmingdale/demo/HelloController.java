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
import java.util.Random;
/**
 * Controller class for handling the logic behind the card game.
 * The user will try to create a mathematical expression that evaluates to 24 using four random cards.
 */
public class HelloController {
    @FXML
    private Label welcomeText, answerLabel , hintLabel;
    @FXML
    private Button refreshButton, verifyButton, hintButton;
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

    /**
     * Initializes the view by loading the card images and setting random card images for the four slots.
     */
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

    /**
     * Verifies the user's input expression and checks if it evaluates to 24.
     */
    @FXML
    protected void onHelloButtonClick() {
        String expression = textField.getText();
        if (isValidExpression(expression)) {
            try {
                double result = evaluateExpression(expression);
                if (result == 24) {
                    answerLabel.setText("Correct! The expression evaluates to 24.");
                }else{
                    answerLabel.setText("The expression does not evaluates to 24." +
                            " Hint: PEMDAS rule");
                }

            }catch (NullPointerException e){
                answerLabel.setText("Please enter an Input");
            }
            catch (Exception e) {
                answerLabel.setText("Error evaluating expression.");
                System.out.println(e);
            }
        }else {
            answerLabel.setText("Only use the numbers from the Cards");
        }
    }

    /**
     * Refreshes the card images and clears the input field.
     */
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

    /**
     * Fetches a hint for the user from the OpenAI API.
     */
    @FXML
    protected void getHint() {
        try {
            String inputforAI = generateHintPrompt();
            String hint = OpenAI.getTextResponse(inputforAI);
            System.out.println(hint);
            hintLabel.setText("Hint: " + hint);
        }catch (Exception e){
            System.out.println(e);

        }

    }

    // Helper methods for validation, expression evaluation, and hint generation below

    private boolean isValidExpression(String expression) {
        String first,second,third,fourth;
        first = Integer.toString(index1+1);
        second = Integer.toString(index2+1);
        third = Integer.toString(index3+1);
        fourth = Integer.toString(index4+1);
        String expresions = String.format("^(%s|%s|%s|%s|\\+|\\-|\\*|\\/|\\(|\\)|\\s)*$",first, second, third, fourth);
        return expression.matches(expresions);
    }
    private double evaluateExpression(String expression) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        Object result = engine.eval(expression);
        return ((Number) result).doubleValue();
    }
    private String generateHintPrompt() {
        String cardInfo = String.format("You have the numbers: %s, %s, %s, %s ",
                ranks[index1], ranks[index2], ranks[index3], ranks[index4]);

        return cardInfo + "Generate an arithmetic expression using the numbers provided before. The expression should follow the PEMDAS order of operations (Parentheses, Exponents, Multiplication, Division, Addition, Subtraction). The goal is for the final result to equal 24. Each number should only be used once, and the operations should be a combination of multiplication, division, addition, subtraction, or exponentiation, strictly adhering to the correct order of operations. The output should be just the numbers and the operators, not a detailed explanation of the logic or steps. Ensure the expression is solvable and results in exactly 24 when evaluated. Do not repeat any number. The model should verify that the solution is correct multiple times and only output a valid expression. The model should focus on accuracy and correctness, ensuring the final answer is precisely 24. Avoid approximations or partial solutions. Do not provide any explanation, just the arithmetic expression formatted in terms of numbers and operators that equals 24. Example (based on previous requests): Numbers: 7,9,4,11 Expected output: (7*(9-4)) - 11 which equals 24 or (11 * (7-4)) - 9 or with the numbers 9,3,12,8 the answer looked like this (12 - 8) * (9 - 3) which also ends up being 24, Important: Only provide the arithmetic expression in the correct order (PEMDAS). The output must evaluate to 24 when the operations are applied in sequence. Avoid overcomplicating the expression unnecessarily—keep it simple and efficient. Please make sure the output is verified and results in exactly 24 when following PEMDAS rules. Only give me the expression." ;

    }
}
