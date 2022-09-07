import java.io.IOException;
import java.util.ArrayList;

public class User {
    private String name;
    private String password;
    private int funds;
    private ArrayList<Integer> amountOfEachStockOwned;
    Recorder userRecorder;
    Recorder stockRecord;

    ////////////////////////////////////////////////////////////////////////
    //contructors

    public User(String name, String password, int funds) throws IOException{
        this.name = name;
        this.password = password;
        this.funds = funds;
        this.amountOfEachStockOwned = new ArrayList<Integer>();
        userRecorder = new Recorder(this.name);
        userRecorder.saveValue(password);
        setupOwnedArray();
    } 

    public User(String name, String password, int funds, ArrayList<Integer> amountOfEachStockOwned) throws IOException{
        this.name = name;
        this.password = password;
        this.funds = funds;
        this.amountOfEachStockOwned = amountOfEachStockOwned;
        userRecorder = new Recorder(this.name);
        setupOwnedArray();
    } 

    public User(String name, String password) throws IOException{
        this.name = name;
        this.password = password;
        this.funds = 3000;
        this.amountOfEachStockOwned = new ArrayList<Integer>();
        userRecorder = new Recorder(this.name);
        setupOwnedArray();
    } 
    //////////////////////////////////////////////////////////////////////////


    // these methods return the value of how many of a stock a user owns 
    public int getOwnedInt(StockOptions stock){
        int owned = Integer.parseInt(amountOfEachStockOwned.get(stock.getStockNumber()).toString());
        return owned;
    }

    public int getOwnedInt(int position){
        int owned = Integer.parseInt(amountOfEachStockOwned.get(position).toString());
        return owned;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public int getFunds(){
        return this.funds;
    }

    public void changeFunds(int amount){
        funds += amount; 
    }
  






    
    
    //method to allow the user to buy stocks
    public boolean buyStock(StockOptions stock, int amount) throws IOException{
        int cost = stock.getValue()*amount;
        if (cost < funds){

            
            changeFunds(-cost);
            this.amountOfEachStockOwned.set(stock.getStockNumber(), ( getOwnedInt(stock)  + amount));
            
            System.out.println("you have bought "+ amount+ " of " + stock.getName());
            System.out.println("You now have " + amountOfEachStockOwned.get(stock.getStockNumber()) + " of " + stock.getName());
            return true;
        }
            
        else{
            System.out.println("you do not have the funds to buy that stock");
            return false;
        }
        
    }











    //allows the selling of a stock 
    public boolean sellStock(StockOptions stock, int amount) throws IOException{

        if (Integer.parseInt(amountOfEachStockOwned.get(stock.getStockNumber()).toString()) >= amount){


            changeFunds(amount*stock.getValue());
            this.amountOfEachStockOwned.set(stock.getStockNumber(), ( getOwnedInt(stock)  - amount));
            
            System.out.println("you have sold "+ amount+ " of " + stock.getName());
            System.out.println("You now have " + amountOfEachStockOwned.get(stock.getStockNumber()) + " of " + stock.getName());
            return true;
        }
            
        else{
            System.out.println("you do not have enough shares to sell of that stock"); 
            return false;   
        }
    }


        
    //method to setup stock owned array when a new account is created
    public void setupOwnedArray(){
        for (int i = amountOfEachStockOwned.size(); i<InvestmentApp.getStockAvaliable().size();i++){
            increeseOwnedStockArray();
        }
    }


    //method keeping sure the number of stocks owned stocks is the same size as the num of stocks available
    public void increeseOwnedStockArray(){
        this.amountOfEachStockOwned.add(0);
    }

    public void saveDetails() throws IOException{
        userRecorder.clearFile();
        userRecorder.saveValue(password);
        userRecorder.saveValue(funds);
        userRecorder.saveIntArray(amountOfEachStockOwned);
    }

    
}






