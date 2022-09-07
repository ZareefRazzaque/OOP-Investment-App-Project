
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;


public abstract class StockOptions {

    //variables

    JButton stockButton;    //each stock will hold their own button  

    int stockNumber;        //a unique number for the stock

    String stockName;       //name of the stock

    Recorder record;        //stores the object that will record the investment 
    
    int value;              //this will be the value of the stock

    int influence = 0;          //helps influence the rate of change for the stock, will be determined by a number of factors
    
    int maxInfluenced = 3; //checks to see if the stock has been influenced too many times 

    int numTimesInfluenced = 0; // the number of times a stock can be influenced before influence resets

    int change;             //the amount the value of the stock will change by

    int precentageChangeLimit; //the extremes the precentage change by the random number generator can be


    Random randomValue = new Random();
    static Timer influenceTimer = new Timer();
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //costructors 
    
    public StockOptions(String name) throws IOException{
        
        this.stockName = name;
        this.value = randomValue.nextInt(50) + 20;
        this.precentageChangeLimit = randomValue.nextInt(10);
        this.record = new Recorder(this.stockName);
        setToPreviousValue();
        makebutton();
        
    }



    public StockOptions(String name, int defaultValue) throws IOException{
        
        this.stockName = name;
        this.value = defaultValue;
        this.precentageChangeLimit = randomValue.nextInt(10) ;
        this.record = new Recorder(stockName);
        setToPreviousValue();
        makebutton();
    }



    public StockOptions(String name, int defaultValue, int changeLimit) throws IOException{
        
        this.stockName = name;
        this.value = defaultValue;
        this.precentageChangeLimit = changeLimit;
        this.record = new Recorder(stockName);
        setToPreviousValue();
        makebutton();


    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //methods


    public void setToPreviousValue() throws IOException{
        
        try{
        value = Integer.parseInt(record.readLastLine());

        }
        catch(NumberFormatException e){
            System.out.println("could not find previous value, keeping value at default value");
            System.out.println(value);
        }
        
    }

    //creates the button for the stock
    public void makebutton(){
        stockButton = new JButton(this.stockName);
        stockButton.addActionListener((e) -> new BuySellGui(this.stockName) );
    }

    //method for changing the value of the investment option 
    public void changeValue() throws IOException{
        //System.out.println(" change: " +this.change);
        this.value = this.value + this.change + this.influence;

        if (value < 100 ){
            value += influence;

        }
        //System.out.println(" new value: " + value);
        record.saveValue(value);


    }


    //deals with the influencing of stock pricings 
    public void changeInfluence(int amount){
        if (numTimesInfluenced < maxInfluenced){
            influence += amount;
            numTimesInfluenced += 1;
            influenceTimer.schedule(new TimerTask() {

                public void run() {
                    influence -= amount;
                    numTimesInfluenced -=1;
                    
                }
            }, randomValue.nextInt(60000));
        }
        new QuickInfoGUI("You have attempted to influence the stock");
    }


    //method for calculating the change about to be applied 
    public void calculateChange(){
        double multiplier = (randomValue.nextInt(precentageChangeLimit+1) - (double)(precentageChangeLimit/2))/100.0;
        this.change = (int)(this.value * multiplier );
        //System.out.println("change = " +this.change);

    }

    public void setStockNumber(int number){
        this.stockNumber = number;
    }
    
    public int getStockNumber(){
        return stockNumber;
    }


    //method getter which returns price 
    public int getValue(){
        return this.value;
    }

    //method getter which returns name 
    public String getName(){
        return this.stockName;
    }

    public JButton getButton(){
        return stockButton;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //abstract methods 

    
    public abstract void tooHigh();
    
    public abstract void tooLow();

    public abstract String getInformation();    


} 