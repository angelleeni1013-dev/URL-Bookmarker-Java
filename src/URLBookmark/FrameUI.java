package URLBookmark;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameUI extends JFrame {
    private JPanel panel;
    
    //constructor
    public FrameUI(){      
        setTitle("URL Bookmarker Software");
        
        ImageIcon icon = new ImageIcon("frameIcon.png");
        setIconImage(icon.getImage());
        
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void setPanel(JPanel panel){
        this.panel = panel;
    }
    
    public JPanel getPanel(){
        return this.panel;
    }
    
    public void refrashPanel(){
        getContentPane().removeAll();
        add(this.panel);
        revalidate();
        repaint();
    }//refrashPanel
}
