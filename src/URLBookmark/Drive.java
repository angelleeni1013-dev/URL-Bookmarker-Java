package URLBookmark;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;

public class Drive {
    public static void main(String args[]){
        try{
            Controller controller = new Controller();
        }
        catch(NullPointerException e){
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                                "System Error: The program will close now.",
                                "System Error",
                                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                                "System Error: The program will close now.",
                                "System Error",
                                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch(IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                                "System Error: The program will close now.",
                                "System Error",
                                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch(ClassCastException e){
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                                "System Error: The program will close now.",
                                "System Error",
                                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch(HeadlessException e){
            System.out.println("System Error: The program will close now.");
            System.exit(1);
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                                "System Error: The program will close now.",
                                "System Error",
                                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
