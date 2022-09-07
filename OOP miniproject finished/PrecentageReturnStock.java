import java.io.IOException;
import java.util.ArrayList;

public class PrecentageReturnStock extends StockOptions implements ReturningStockAddons {

    AppGUI  gui = InvestmentApp.getGUI(); 

    double precentageToBeReturned;
    int timeBetweenDividends; 
    int timePassed;
    int valueTooHigh;
    int valueTooLow;


    ///////////////////////////////////////////////////////////////////////////////////
    //constructors 

    public PrecentageReturnStock(String name, int timeBetweenDividends, int precentageToBeReturned ) throws IOException {
        super(name);
        this.timeBetweenDividends = timeBetweenDividends;
        timePassed=0;
        this.precentageToBeReturned = (double)precentageToBeReturned/100;
        valueTooHigh = 1500;
        valueTooLow = 400;

    }

    public PrecentageReturnStock(String name, int defaultValue, int timeBetweenDividends, int precentageToBeReturned) throws IOException {
        super(name, defaultValue);
        this.timeBetweenDividends = timeBetweenDividends;
        this.timePassed=0;
        this.precentageToBeReturned = (double)precentageToBeReturned/100;
        valueTooHigh = 1500;
        valueTooLow = 400;
    }

    public PrecentageReturnStock(String name, int defaultValue, int timeBetweenDividends, int changeLimit, int precentageToBeReturned) throws IOException {
        super(name, defaultValue, changeLimit);
        this.timeBetweenDividends = timeBetweenDividends;
        timePassed=0;
        this.precentageToBeReturned = (double)(precentageToBeReturned/100);
        valueTooHigh = 1500;
        valueTooLow = 400;
    }

    public PrecentageReturnStock(String name, int defaultValue, int timeBetweenDividends , int changeLimit, int precentageToBeReturned, int valueTooHigh, int valueTooLow ) throws IOException {
        super(name, defaultValue, changeLimit);
        this.timeBetweenDividends = timeBetweenDividends;
        timePassed=0;
        this.precentageToBeReturned = (double)(precentageToBeReturned/100);
        this.valueTooHigh = valueTooHigh;
        this.valueTooLow = valueTooLow;
        valueTooHigh = 1500;
        valueTooLow = 400;
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //methods 

    public void changeValue() throws IOException {
        super.changeValue();
        timePassed += 1;

        if (timePassed == day){
            doreturns();
            timePassed = 0;
        }
    }





    public void doreturns() {
        int returnAmountPerStock = calculateReturnPerStock();

        ArrayList<User> users = InvestmentApp.getUsers();

        for (int i= 0; i< users.size(); i++){
            User user = users.get(i);
            
            user.changeFunds(user.getOwnedInt(stockNumber)*returnAmountPerStock);
            gui.updateFunds();
            
        }
    }

    public int calculateReturnPerStock() {
        return (int) (value * precentageToBeReturned);
    }





    public void tooHigh() {
        if(super.value > valueTooHigh){
            double multiplier = -(randomValue.nextInt(precentageChangeLimit+1) - (double)(precentageChangeLimit/3))/100.0;
            change = (int)(this.value * multiplier );
        }
        
    }



    public void tooLow() {
        if (super.value<200) {
            change = 50 + randomValue.nextInt(55);
        }
        
        else if(super.value < valueTooLow){
            double multiplier = (randomValue.nextInt(precentageChangeLimit+1) - (double)(precentageChangeLimit/3))/100.0;
            change = (int)(this.value * multiplier );

            //System.out.println("Too high is " + valueTooHigh + "Too low is " + valueTooLow);
        }
        
    }

    public String getInformation() {
        return "a reliable stock that yields dividends at a precentage of the stocks value";
    }

    
}
