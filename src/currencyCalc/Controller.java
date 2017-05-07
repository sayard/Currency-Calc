package currencyCalc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private Label mainRateLabel;
    @FXML
    private Label subRateLabel;

    @FXML
    private TextField textField;

    @FXML
    private Button calculateButton;

    @FXML
    private ComboBox<String> firstCurrencyComboBox;
    @FXML
    private ComboBox<String> secondCurrencyComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calculateButton.setOnAction(calculateEvent());
        populateComboBoxes();
    }

    private void populateComboBoxes(){
        ObservableList<String> currencies =
                FXCollections.observableArrayList(
                        "Euro (EUR)",
                        "US Dollar (USD)",
                        "British Pound (GBP)",
                        "Japanese Yen (JPY)",
                        "Canadian Dollar (CAD)",
                        "Australian Dollar (AUD)",
                        "Swiss Franc (CHF)",
                        "Polish Zloty (PLN)"
                );
        firstCurrencyComboBox.setItems(currencies);
        secondCurrencyComboBox.setItems(currencies);
        firstCurrencyComboBox.setValue(currencies.get(0));
        secondCurrencyComboBox.setValue(currencies.get(1));
    }

    private EventHandler<ActionEvent> calculateEvent(){
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String firstComboBoxValue = firstCurrencyComboBox.getValue();
                String firstCurrencyAbbrev = firstComboBoxValue.substring(firstComboBoxValue.indexOf("(")+1,firstComboBoxValue.indexOf(")"));
                String secondComboBoxValue = secondCurrencyComboBox.getValue();
                String secondCurrencyAbbrev = secondComboBoxValue.substring(secondComboBoxValue.indexOf("(")+1,secondComboBoxValue.indexOf(")"));

                JSONObject object = Calculator.readJsonFromURL(firstCurrencyAbbrev);
                Double rates = (Double) object.getJSONObject("rates").get(secondCurrencyAbbrev);

                try{
                    Float valueToCalculate = Float.valueOf(textField.getText());
                    rates *= valueToCalculate;
                    mainRateLabel.setText(valueToCalculate + " " + firstCurrencyAbbrev + " = " + rates + " " + secondCurrencyAbbrev);

                    object = Calculator.readJsonFromURL(secondCurrencyAbbrev);
                    rates = (Double) object.getJSONObject("rates").get(firstCurrencyAbbrev);
                    rates *= valueToCalculate;

                    subRateLabel.setText(valueToCalculate + " " + secondCurrencyAbbrev + " = " + rates + " " + firstCurrencyAbbrev);

                }catch (NumberFormatException e){
                    mainRateLabel.setText("Invalid value");
                    subRateLabel.setText("Please enter a number");
                }
            }
        };
    }
}
