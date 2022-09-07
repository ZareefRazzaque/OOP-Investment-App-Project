import java.awt.Color;
import java.io.IOException;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class NewAccountGUI {

    JFrame addAccount;
    JButton SubmitButton = new JButton("Submit Information");
    
    JLabel enterName= new JLabel("Enter Username: ");
    JTextField usernameField = new JTextField();

    JLabel enterPassword= new JLabel("Enter Password:");
    JPasswordField password = new JPasswordField();
    
    JLabel repeatPassword = new JLabel("Confirm Password:");
    JPasswordField repeatPasswordField = new JPasswordField();
    

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //constructor

    //this constructor creates the gui for creating a new account
    public  NewAccountGUI() throws IOException{
        this.addAccount = new JFrame();
        addAccount.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addAccount.setTitle("Create New Account");
        addAccount.getContentPane().setBackground(new Color(125,100,250));
        addAccount.setSize(300, 300);
        addAccount.setLayout(null);
  
      
        JPanel namePanel = new JPanel();
        usernameField.setColumns(10);
        namePanel.add(enterName);
        namePanel.add(usernameField);
        addAccount.setLayout(new GridLayout(2,1));

        JPanel passwordPanel = new JPanel();
        password.setColumns(10);
        passwordPanel.add(enterPassword);
        passwordPanel.add(password);
        addAccount.setLayout(new GridLayout(2,1));

        JPanel confirmPasswordPanel = new JPanel();
        repeatPasswordField.setColumns(10);
        confirmPasswordPanel.add(repeatPassword);
        confirmPasswordPanel.add(repeatPasswordField);
        addAccount.setLayout(new GridLayout(2,1));


        addAccount.add(namePanel);
        addAccount.add(passwordPanel);
        addAccount.add(confirmPasswordPanel);
        addAccount.add(SubmitButton);
        addAccount.setLayout(new GridLayout(4,1));
        
        addAccount.setVisible(true);

        SubmitButton.addActionListener((e) -> {
            try { createUser();} 
            catch (IOException e1) {
                e1.printStackTrace();
            }
            addAccount.dispose();;
        } );

    }

    public void createUser () throws IOException{
    
        if ((this.usernameField.getText().equals(""))||(this.repeatPasswordField.getText().equals(""))){
            System.out.println("user left a field blank");
            new QuickInfoGUI("please do not leave any fields blank");
        }

        else if (InvestmentApp.findUser(this.usernameField.getText())){
            System.out.println("user already exists");
            new QuickInfoGUI("This User already exists");

        }
        
        else if(password.getText().equals(repeatPasswordField.getText())) {
         
            InvestmentApp.createUser(this.usernameField.getText(), password.getPassword());
            System.out.println("New User Created");
            new QuickInfoGUI("New User Created");

        }
        else{
            new QuickInfoGUI("Passwords do not match");
        }

    }

}
