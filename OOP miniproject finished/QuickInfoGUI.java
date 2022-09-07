import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class QuickInfoGUI {
    public QuickInfoGUI(String Message){
        JFrame info = new JFrame();
        info.setSize(400, 100);
        JLabel information = new JLabel( Message, SwingConstants.CENTER); 
        info.add(information);
        info.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        info.setVisible(true);
    }
}
