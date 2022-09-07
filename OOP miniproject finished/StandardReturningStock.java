import java.io.IOException;
import java.util.ArrayList;

public class StandardReturningStock extends StockOptions implements ReturningStockAddons {

    AppGUI  gui = InvestmentApp.getGUI(); 

    int valueTooHigh;

    int valueTooLow;

    int fixedReturnValue;

    int timePassed;

    ///////////////////////////////////////////////////////////////////////////////////
    //constructors 

    public StandardReturningStock(String name, int returns) throws IOException {
        super(name);
        timePassed=0;
        this.fixedReturnValue = returns;
        valueTooHigh = 1500;
        valueTooLow = 400;
    }

    public StandardReturningStock(String name, int defaultValue, int returns) throws IOException {
        super(name, defaultValue);
        timePassed=0;
        this.fixedReturnValue = returns ;
    }

    public StandardReturningStock(String name, int defaultValue, int changeLimit, int returns) throws IOException {
        super(name, defaultValue, changeLimit);
        timePassed=0;
        this.fixedReturnValue = returns;
        valueTooHigh = 1500;
        valueTooLow = 400;
    }
    
    public StandardReturningStock(String name, int defaultValue, int changeLimit, int returns, int valueTooHigh, int valueTooLow ) throws IOException {
        super(name, defaultValue, changeLimit);
        timePassed=0;
        this.fixedReturnValue = returns;
        this.valueTooHigh = valueTooHigh;
        this.valueTooLow = valueTooLow;
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
        return fixedReturnValue;
    }





    public void tooHigh() {
        if(super.value > valueTooHigh){
            double multiplier = -((randomValue.nextInt(precentageChangeLimit+1) - (double)(precentageChangeLimit/3))/100.0);
            
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

    @Override
    public String getInformation() {
        return "A reliable stock that yeilds diviends at fixed amounts ";
    }

    
}
