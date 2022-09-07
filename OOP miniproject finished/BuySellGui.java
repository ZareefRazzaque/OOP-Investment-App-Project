import java.awt.event.*;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BuySellGui {

    AppGUI GUI = InvestmentApp.getGUI();

    JFrame buySellFrame ;

    User currentUser; 
    
    JLabel stockDetails;
    JLabel stockOwned;
    JLabel information;

    JTextField amount;
   
    JPanel stockPanel;

    StockOptions concerningStock;
   
    JButton buyButton;
    JButton sellButton;

    Timer buySellUpdateTimer;

    
    TimerTask doUpdate = new TimerTask() { 
        public void run() {
            buySellupdater();
        }
    };
 
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    //static methods
    public static void notNumber(){
        System.out.println("the user failed to write a number");
        new QuickInfoGUI("Please write a number");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////

    //constructor sets up the basic elements to the gui
    public BuySellGui(String name){


        currentUser = InvestmentApp.getCurrentUser();        
        concerningStock = InvestmentApp.findStock(name);


        
        System.out.println("showing buy/sell screen");
        stockDetails = new JLabel(concerningStock.getName() + ": £" + concerningStock.getValue() );
        stockDetails.setHorizontalAlignment(JLabel.CENTER);


        stockOwned = new JLabel( "Stock Owned: " + InvestmentApp.getCurrentUser().getOwnedInt(concerningStock) );
        stockOwned.setHorizontalAlignment(JLabel.CENTER);

        setinformation();

        stockPanel = new JPanel();

        amount = new JTextField();
        
        buySellFrame  = new JFrame();
        buySellFrame.setTitle("buy/sell "+ name);
        buySellFrame.setSize(600, 300);

        buyButton = new JButton("buy");
        sellButton = new JButton("sell");
        

        buySellFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                buySellUpdateTimer.cancel();
                buySellUpdateTimer.purge();
                super.windowClosing(e);
            }
        });

        addcomponents();
        
        

        if (currentUser instanceof InfluentialUser){

            JButton influenceStockUpButton = new JButton("Influence Upwards");
            influenceStockUpButton.addActionListener((e) -> ( (InfluentialUser) currentUser).addInfluence(concerningStock) );
            
            
            JButton influenceStockDownButton = new JButton("Influence Downwards");
            influenceStockDownButton.addActionListener((e) -> ( (InfluentialUser) currentUser).substractInfluence(concerningStock) );

            stockPanel.add(influenceStockDownButton);
            stockPanel.add(influenceStockUpButton);

            stockPanel.setLayout(new GridLayout(9,1));


        }


        
        buyButton.addActionListener((e) -> {
            try { buyStock();}
            catch (IOException e2) {
                e2.printStackTrace();
            }
        } );
    
        
        sellButton.addActionListener((e) -> {
            try {sellStock();} 
            catch (IOException e1) {
                e1.printStackTrace();
            }
        } );

        
        buySellUpdateTimer = new Timer();
        buySellUpdateTimer.scheduleAtFixedRate(doUpdate, 0, 500);

        

        buySellFrame.setVisible(true);
        
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //methods 

    public void addcomponents(){
        stockPanel.add(stockDetails);
        stockPanel.add(information);
        stockPanel.add(stockOwned);
        stockPanel.add(amount);
        stockPanel.add(buyButton);
        stockPanel.add(sellButton);
        stockPanel.setLayout(new GridLayout(7,1));

        buySellFrame.add(stockPanel);
    }

    //sets the information to be shown on the stock
    public void setinformation(){
        information = new JLabel(concerningStock.getInformation());
        information.setHorizontalAlignment(JLabel.CENTER);
    }

    //updates the price of the stock in the buy/sell menu
    public void buySellupdater(){
        stockDetails.setText(concerningStock.getName() + ": £" + concerningStock.getValue()); 
        stockOwned.setText( "Stock Owned: " + InvestmentApp.getCurrentUser().getOwnedInt(concerningStock) );       
        buySellFrame.revalidate();
    }

    
    //method for buying stock
    public void buyStock() throws IOException{

        int amountOfStocks;
        
        try { 

            amountOfStocks = Integer.parseInt(amount.getText());
            if (currentUser.buyStock(concerningStock, amountOfStocks)){
                GUI.updateFunds();
                buySellFrame.dispose();
            }
            else{
                new QuickInfoGUI("You do not have enough funds to buy that stock");
            }

        }
        catch(NumberFormatException e){
            notNumber();
        }

        
        
    }

        
    
    //method for selling stock
    public void sellStock() throws IOException{
        int amountOfStocks;
        
        try { 
            
            amountOfStocks = Integer.parseInt(amount.getText());
            if (currentUser.sellStock(concerningStock, amountOfStocks)){
                GUI.updateFunds();
                buySellFrame.dispose();
            }
            else{
                new QuickInfoGUI("You do not have enough shares of that stock to sell");

            }
        }
        catch(NumberFormatException e){
            notNumber();
        }

    }



}
