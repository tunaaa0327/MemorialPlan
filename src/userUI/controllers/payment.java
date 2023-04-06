package userUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import userUI.model.Plan;

import userUI.main.mainUser;

import java.nio.file.Watchable;


public class payment {

    @FXML
    private Button calculateButton;

    @FXML
    private Label contracOrSellPrice;

    @FXML
    private Label contractOrSellPriceLabel;

    @FXML
    private TextField downOrAmountField;

    @FXML
    private Label downOrAmountTitle;

    @FXML
    private AnchorPane fourBox;

    @FXML
    private Label interestOrOffTitle;

    @FXML
    private Label interestorOffLabel;

    @FXML
    private Label monthlyOrChangeLabel;

    @FXML
    private Label monthlyOrChangeTitle;

    @FXML
    private Label nameFour;

    @FXML
    private Label nameOne;

    @FXML
    private Label nameThree;

    @FXML
    private Label nameTwo;

    @FXML
    private AnchorPane oneBox;

    @FXML
    private VBox paymentCard;

    @FXML
    private Label planNameLabel1;

    @FXML
    private Label priceFour;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button selectButton;

    @FXML
    private AnchorPane threeBox;

    @FXML
    private AnchorPane twoBox;

    @FXML
    private TextField yearField;

    @FXML
    private Label yearTitle;

    @FXML
    private Label selectedPlan;

    @FXML
    private Label selectedPrice;

    @FXML
    private Label warningLabel;

    @FXML
    private Label paymentTitle;

    @FXML
    private HBox yearFieldBox;


    @FXML
    private HBox yearTitleBox;


    Parent root;
    Stage stage;
    Scene scene;

    int ID;
    Plan plan;



    public void setID(Plan plan){
        this.plan = plan;
        selectedPlan.setText(plan.getCategoryName());
        selectedPrice.setText(mainUser.Currency+plan.getSellingPrice());
        toSpotCashCard();
    }



    public void setPaymentCard(MouseEvent e){

        if(e.getSource()==oneBox){
            plan.setSpotDiscount(15);
            toSpotCashCard();
            toSpotUpdate();

        }else if(e.getSource()==twoBox){
            plan.setSpotDiscount(10);
            toSpotCashCard();
            toSpotUpdate();


        }else if(e.getSource()==threeBox){
            plan.setSpotDiscount(5);
            toSpotCashCard();
            toSpotUpdate();

        }else {
            toInstallmentCard();

        }
    }

    public void toSpotUpdate(){
        interestorOffLabel.setText(plan.getSpotDiscount()+"%");
        contractOrSellPriceLabel.setText(mainUser.Currency+plan.getSpotPrice());
    }

    public void toSpotCashCard(){
        downOrAmountField.setText(null);
        contracOrSellPrice.setText("Selling Price:");
        interestOrOffTitle.setText("Discount:");
        monthlyOrChangeTitle.setText("Change:");
        paymentCard.getChildren().remove(yearFieldBox);
        paymentCard.getChildren().remove(yearTitleBox);
        downOrAmountTitle.setText("Amount:");
        paymentTitle.setText("Spot Cash");

    }

    public void toInstallmentCard(){
        downOrAmountField.setText(null);
        interestorOffLabel.setText(null);
        contractOrSellPriceLabel.setText(null);
        paymentCard.getChildren().add(4,yearTitleBox);
        paymentCard.getChildren().add(5,yearFieldBox);
        downOrAmountTitle.setText("Down Payment:");
        paymentTitle.setText("Installment");
        contracOrSellPrice.setText("Contract Price:");
        interestOrOffTitle.setText("Interest:");
        monthlyOrChangeTitle.setText("Monthly:");
        yearTitle.setVisible(true);
        yearField.setVisible(true);
        calculateButton.setVisible(true);

    }


    public void toCalculate() {
        try{
            if(downOrAmountField.getText().isBlank()){
                warningLabel.setText("Amount is Blank");
            }else {
                int amount = Integer.parseInt(downOrAmountField.getText());
                int dis = plan.getSpotPrice();
                int sell = plan.getSellingPrice();
                if(paymentTitle.getText().equals("Spot Cash")){
                    if(amount<dis){
                        warningLabel.setText("Insufficient Amount");
                    }else {
                        warningLabel.setText(null);
                        int change = amount - dis;
                        monthlyOrChangeLabel.setText(mainUser.Currency+change);
                    }

                    //Installment
                }else {
                    if(yearField.getText().isBlank()){
                        warningLabel.setText("Year is Blank");
                    }else {
                        if (amount > sell) {
                            warningLabel.setText("Amount is greater than Selling Price");
                        } else {
                            int year = Integer.parseInt(yearField.getText());
                            warningLabel.setText(null);
                            if (year > 40) {
                                warningLabel.setText("Year cannot exceeded 40!");
                            } else {
                                warningLabel.setText(null);
                                plan.setYear(year);
                                plan.setDownPayment(amount);
                                contractOrSellPriceLabel.setText(mainUser.Currency + plan.getContractPrice());
                                interestorOffLabel.setText(plan.getInterest() + "%");
                                monthlyOrChangeLabel.setText(mainUser.Currency + plan.getMonthlyPayment());
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            warningLabel.setText("Field is Empty");
        }

    }




    public void toMarket(ActionEvent e){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/userUI/fxmls/market.fxml"));
            root = loader.load();
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



}
