import java.io.IOException;

public class CryptoStock extends StockOptions {

    //simulating a crypto currency stock, prices for the stock can vary between 500  to anything above that theorectically however the higher it goes the more likely it is too fall,  
    //there is no returns when holding onto this stock 

    int valueTooLow;

    int valueTooHigh;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //contructors

    public CryptoStock(String name) throws IOException{
        super(name);
        this.valueTooHigh += (int)((super.value)*1.60);    
            this.valueTooLow += (int)((super.value)*0.60);
    }

    public CryptoStock(String name,int defaultValue ) throws IOException{
        super(name, defaultValue);
        this.valueTooHigh += (int)((super.value)*1.60);    
            this.valueTooLow += (int)((super.value)*0.60);
    }
    
    public CryptoStock(String name,int defaultValue, int changeLimit) throws IOException{
        super(name, defaultValue, changeLimit);
        this.valueTooHigh += (int)((super.value)*1.60);    
            this.valueTooLow += (int)((super.value)*0.60);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //methods

    //this method makes it more likely for a stock to fall in value rather than continuing to increese after a certain point 
    public void tooHigh(){
        if(super.value > valueTooHigh){
            double multiplier = (randomValue.nextInt(precentageChangeLimit+1) - (double)(precentageChangeLimit/3))/100.0;
            change = (int)(this.value * multiplier );
            this.valueTooHigh += (int)((super.value)*1.60);    //changes the limtis of the stock allowing it to seem more dynamic
            this.valueTooLow += (int)((super.value)*0.60);
            //System.out.println(" Too high is " + valueTooHigh + " Too low is " + valueTooLow);
        }
        
    }
    //this makes it more like for stocks to increese in value after reaching a certain point, however forces an increese below 30
    public void tooLow(){
        
        if (super.value<500) {
            change = 50 + randomValue.nextInt(55);
        }
        
        else if(super.value < valueTooLow){
            double multiplier = (randomValue.nextInt(precentageChangeLimit+1) - (double)(precentageChangeLimit/3))/100.0;
            change = (int)(this.value * multiplier );
            this.valueTooHigh = (int)((super.value)*1.50);    //changes the limits of the stock allowing it to seem more dynamic 
            this.valueTooLow = (int)((super.value)*0.50);

            //System.out.println("Too high is " + valueTooHigh + "Too low is " + valueTooLow);
        }
    }


    public String getInformation() {

        return "Crypto is an unpredictable and volatile stock that yields no dividends";
    }


}
