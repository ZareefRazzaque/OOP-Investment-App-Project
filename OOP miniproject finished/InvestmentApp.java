import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class InvestmentApp{
    static AppGUI GUI;

    static User currentUser;
    static Recorder userRecord;
    static ArrayList<User> createdUsers;
    static ArrayList<StockOptions> stockAvaliable;


    ////////////////////////////////////////////////////////////////////
    //static variables

    //after a stock has been created it needs to be added to the list of avaliable stock to work
    public static void makeStockAvailable(StockOptions stock){
       
        System.out.println(stock.getName() + " has been added to the list of stocks");
        
        stockAvaliable.add(stock);
        stock.setStockNumber(stockAvaliable.size()-1);
        for(int i = 0; i<createdUsers.size();i++){
            createdUsers.get(i).increeseOwnedStockArray();
        }
    }


    //converts a char[] list to a string
    public static String passwordToString(char[] enteredPassword){
        String password = "";
        for (int i = 0; i < enteredPassword.length;i++){
            password += enteredPassword[i];
        }

        return password;
    }

    //this update the stock market to calculate the new values 
    public static void update() throws IOException{
        for (int x = 0; x<stockAvaliable.size();x++){

            InvestmentApp.stockAvaliable.get(x).calculateChange();
            InvestmentApp.stockAvaliable.get(x).tooHigh();
            InvestmentApp.stockAvaliable.get(x).tooLow();
            InvestmentApp.stockAvaliable.get(x).changeValue();

        }
    }

    //this allows the stocks to update periodically 
    static TimerTask doUpdate = new TimerTask() {
        public void run() {
            try {
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };



    //method to check if a user exists
    public static boolean findUser(String enteredName) {
        
        for(int i=0 ;i < createdUsers.size();i++){

            if(createdUsers.get(i).getName().equals(enteredName)){
                System.out.println("user found " + enteredName +  " has logged in");
                return true;
            }

        }
        return false;
    }


    //checks if the user name and password exists and sets the found user to the current user
    public static boolean loginUser(String enteredName, char[] enteredPassword) {

        String password =passwordToString(enteredPassword);
        
        for(int i=0 ;i < createdUsers.size();i++){

            if((createdUsers.get(i).getName().equals(enteredName)) && (createdUsers.get(i).getPassword().equals(password))){
                currentUser = createdUsers.get(i);
                System.out.println("user found " + enteredName +  " has logged in");
                return true;
            }

        }
        return false;
    }


    //method for creating a new user
    public static void createUser(String name, char[] enteredPassword) throws IOException{

        String password = passwordToString(enteredPassword);
        
        createdUsers.add(new User(name, password));
    
        userRecord.saveValue(name);
    
    }

    


    public static void addPreviousUsers() throws IOException{

        ArrayList<String> previousUsers = userRecord.returnAllData();

        for (int i =0; i < previousUsers.size(); i++ ){
            Recorder record = new Recorder(previousUsers.get(i));
            try{
            
                String password = record.readline(1);
                int funds = Integer.parseInt(record.readline(2));
                ArrayList<Integer> ownedStocks = new ArrayList<Integer>();
                

                String ownedStockAsString = record.readline(3);
                System.out.println("String"+ ownedStockAsString);

                String[] ownedStockAsArray = ownedStockAsString.split("," );
                System.out.println(ownedStockAsArray.toString());
                
                System.out.println(ownedStockAsArray.length);
                for (int x = 0; x<ownedStockAsArray.length;x++){
                    ownedStocks.add(Integer.parseInt(ownedStockAsArray[x]));

                }

                if (password.equals("")){
                    System.out.println("users password returned blank moving onto next user");
                }
                else{
                    createdUsers.add(new User(previousUsers.get(i), password, funds, ownedStocks));
                }
            
            
            }
            catch(NumberFormatException e){
                System.out.println("user details failed to return an integer moving onto next user");
            }
        }
    }


    public static void saveUsers() throws IOException{
        for (int i = 0; i < createdUsers.size();i++){
            createdUsers.get(i).saveDetails();
        }
    } 

    public static User getCurrentUser(){
        return currentUser;
    }

    public static ArrayList<StockOptions> getStockAvaliable(){
        return stockAvaliable;
    }

    public static StockOptions findStock(String name){
        
        for(int i = 0; i<stockAvaliable.size(); i++ ){
        
            if (name.equals(stockAvaliable.get(i).getName())){
                return stockAvaliable.get(i);
            }

        }
        return null;
    }



    public static int getNumOfStock(){
        return stockAvaliable.size();
    }

    public static AppGUI getGUI(){
        return GUI;
    }

    public static ArrayList<User> getUsers(){
        return createdUsers;
    }

    /////////////////////////////////////////////////////////////////////
    //stating method to start the program
    public static void start() throws IOException{
        createdUsers = new ArrayList<User>();
        stockAvaliable = new ArrayList<StockOptions>();
        userRecord = new Recorder("User Record");
        

        Timer updater = new Timer();
        
        updater.scheduleAtFixedRate(doUpdate, 1000, 1000);
            
        GUI = new AppGUI();

        //creating some users
        createdUsers.add(new User("testuser", "password"));
        createdUsers.add(new InfluentialUser("Elon Musk", "dogecoin"));

        addPreviousUsers();

        //creating the stock instances and adding them to the list of avaliable stock here
        makeStockAvailable(new CryptoStock("Crypto A", 500, 10));
        makeStockAvailable(new CryptoStock("Crypto B", 1000, 20));
        makeStockAvailable(new CryptoStock("Crypto C", 800, 10));
        makeStockAvailable(new CryptoStock("Crypto D", 50, 2));
        
        makeStockAvailable(new PrecentageReturnStock("comapany A", 500,PrecentageReturnStock.day, 10, 20));
        makeStockAvailable(new PrecentageReturnStock("comapany B", 500,PrecentageReturnStock.month, 20, 50));
        makeStockAvailable(new PrecentageReturnStock("comapany C", 500,PrecentageReturnStock.month, 50, 30));
        
        makeStockAvailable(new StandardReturningStock("Government Bond A", 1000, 400));
        makeStockAvailable(new StandardReturningStock("Government Bond B", 1000, 10, 200));



    }



    






    
}
