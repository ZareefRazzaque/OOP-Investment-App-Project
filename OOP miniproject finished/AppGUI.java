import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.ColorUIResource;


public class AppGUI {
    

    JFrame appFrame;
    int GUIstage;
    JLabel funds;
    


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    //contructor

    //contructor calling the function to start app gui
    public AppGUI() throws IOException{
        startAppGUi();
        
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    //the basic methods

    

    //starts the gui
    public void startAppGUi(){
        appFrame = new JFrame();;
        
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        //starts the method allowing all user to be saved before closing 
        appFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try {
                    InvestmentApp.saveUsers();
                } 
                catch (IOException e1) { 
                    e1.printStackTrace();
                }
                super.windowClosing(e);
            }
        });



        GUIstage=0;
        UpdateGUI();
    }


    
    //updates the investment app when called 
    public void UpdateGUI(){
        
        resetGUI();
        if (GUIstage == 0){
            loginStage();   
        }
        else if (GUIstage == 1){
            InvestmentStage();
        }

        
        appFrame.setVisible(false);
        appFrame.setVisible(true);

    }




    //resets the gui so that it is clean before loading in next set of stuff 
    public void resetGUI(){
        appFrame.getContentPane().removeAll();
        appFrame.setTitle("InvestmentApp");
        appFrame.getContentPane().setBackground(new Color(125,100,250));
        appFrame.setSize(600, 600);
        appFrame.setLayout(null);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////
    //the investment app stage setup methods

    //this method creates the login part of the investment app 
    public void loginStage(){
        JPanel appPanel = new JPanel();
        
        JLabel username = new JLabel("Username:");
        JLabel password = new JLabel("Password:");


        JTextField usernameField = new JTextField();
        usernameField.setColumns(10);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setColumns(10);

        JButton submitButton = new JButton("submit");
        JButton createAccountButton = new JButton("create new account");

        JPanel usernamePanel = new JPanel() ;
        JPanel passwordPanel = new JPanel();

        usernamePanel.add(username);
        usernamePanel.add(usernameField);

        passwordPanel.add(password);
        passwordPanel.add(passwordField);
        
        appPanel.setBounds(100, 100, 400, 400);
        appPanel.setBackground(new ColorUIResource(255, 255, 255));
        
        appPanel.add(usernamePanel);
        appPanel.add(passwordPanel);
        appPanel.add(submitButton);
        appPanel.add(createAccountButton);

        appPanel.setLayout(new GridLayout(4,2));
        appFrame.add(appPanel);
        appFrame.setVisible(true);

        //button methods for the login 
        submitButton.addActionListener((e) -> login( usernameField.getText(), passwordField.getPassword()));

        createAccountButton.addActionListener((e) -> {
            try {addUser(); } catch (IOException e1) {
                e1.printStackTrace(); 
            }     
        });

        appFrame.revalidate();
    }




    
    //method that allows the user to login 
    public void login(String name, char[] password){

        if (InvestmentApp.loginUser(name, password)){
            appFrame.setVisible(false);
            GUIstage = 1;
            UpdateGUI();
        }
        else{
            new QuickInfoGUI("Incorrect Username or Password");
        }

    }

    //produces a seperate GUI for creating a new user
    public void addUser() throws IOException{
        new NewAccountGUI();
    }
    
    //the main gui of the banking app, main page for the user to trade, look at stocks etc
    public void InvestmentStage(){
        
        appFrame.setLayout(new GridLayout(3,0));

        addUserSection();

        addStockSection();

        addExtrasSection();

        appFrame.setVisible(true); 

    }



    
    //adds the user section of the investment app 
    public void addUserSection(){
        User currentuser = InvestmentApp.getCurrentUser();
        
        
        JPanel userSection = new JPanel();
        userSection.setLayout(new GridLayout(1,2));
        userSection.setBackground(new Color(200,170,150));
        
        JLabel name = new JLabel(currentuser.getName());
        name.setHorizontalAlignment(JLabel.CENTER);

        funds = new JLabel("Funds: " + currentuser.getFunds());
        funds.setHorizontalAlignment(JLabel.CENTER);
        
        updateFunds();

        userSection.add(name);
        userSection.add(funds);

        appFrame.add(userSection);
        }


        public void updateFunds() {
            if (!(InvestmentApp.getCurrentUser() == null )){
                User currentuser = InvestmentApp.getCurrentUser();
                funds.setText("Funds: " + currentuser.getFunds());
            }
                appFrame.revalidate();
            
        }



    //adds the stock section of the investment app 
    public void addStockSection(){
        
        JPanel StocksSection = new JPanel();
        StocksSection.setBackground(new Color(25,170,150));        
        StocksSection.setLayout(new GridLayout(2,0));

        JPanel stockButtonPanel = new JPanel();
        stockButtonPanel.setBackground(new Color(25,170,150));


        JLabel stocksAvaliableTitle = new JLabel("Stocks Currently Avaliable", JLabel.CENTER);
        stocksAvaliableTitle.setHorizontalAlignment(JLabel.CENTER);
       
        StocksSection.add(stocksAvaliableTitle);

        System.out.println("attempting to get buttons");
        System.out.println(InvestmentApp.getNumOfStock());

        for (int i = 0; i < InvestmentApp.stockAvaliable.size();i++){
            System.out.println("getting button " + i);
            stockButtonPanel.add(InvestmentApp.stockAvaliable.get(i).getButton());

        }

        StocksSection.add(stockButtonPanel);
        appFrame.add(StocksSection);

    }

    public void addExtrasSection(){

        JPanel extrasPanel = new JPanel();
        extrasPanel.setBackground(new Color(100,170,100));
        
        JButton logOutButton = new JButton("Log Out");
        logOutButton.addActionListener((e) ->  {
            GUIstage = 0;
            UpdateGUI();
        });


        logOutButton.setHorizontalAlignment(JButton.CENTER);
        extrasPanel.add(logOutButton);

        appFrame.add(extrasPanel);
    }


}
