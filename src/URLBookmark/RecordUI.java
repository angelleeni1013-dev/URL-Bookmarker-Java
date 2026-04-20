package URLBookmark;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class RecordUI {
    private URLBookmark url;
    
    private JPanel panel;
    private JButton jbtURL, jbtDelete;
            
    public RecordUI(){
        
        jbtURL = new JButton();
        jbtURL.setFont(new Font("Arial", Font.PLAIN, 16));
        jbtURL.setBounds(0, 0, 520, 50);
        jbtURL.setBackground(Color.WHITE);
        jbtURL.setHorizontalAlignment(JButton.LEFT);
        
        ImageIcon deleteIcon = new ImageIcon("deleteIcon.png");
        Image img = deleteIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        jbtDelete = new JButton(new ImageIcon(img));
        jbtDelete.setBounds(520, 0, 100, 50);
        jbtDelete.setBackground(Color.WHITE);
        
        panel = new JPanel();
        panel.setLayout(null);
        
        panel.add(jbtURL);
        panel.add(jbtDelete);
        
        panel.setVisible(false);
    }
    
    public void setURL(URLBookmark url){
        this.url = url;
    }
    
    public URLBookmark getURL(){
        return this.url;
    }
    
    public JPanel getPanel(){
        return this.panel;
    }
    
    public JButton getJBTURL(){
        return this.jbtURL;
    }
    
    public JButton getJBTDelete(){
        return this.jbtDelete;
    }
    
    public void displayALLData(){
        this.jbtURL.setText("<html>Title: " + this.url.getTitle() + "<br>" + "URL:" + this.url.getURL() + "</html>");
    }//diaplayALLData
    
    public void clearALLData(){
        url = new URLBookmark();
        this.jbtURL.setText("<html>Title: " + this.url.getTitle() + "<br>" + "URL:" + this.url.getURL() + "</html>");
    }//claerALLData
    
}//RecordUI
