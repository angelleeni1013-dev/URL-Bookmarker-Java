package URLBookmark;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditURLPageUI {
    private URLBookmark url;
    
    private JPanel panel;
    private JTextField jtfTitle, jtfDescription, jtfURL;
    private JComboBox jcbCategory;
    private JButton jbtExit, jbtEdit;
    
    //constructor
    public EditURLPageUI(){
        url = new URLBookmark();
                
        JLabel jlbPageTitle = new JLabel("Edit URL");
        jlbPageTitle.setFont(new Font("Arial", Font.BOLD, 30));
        jlbPageTitle.setBounds(40,30,200,30);  
        
        JSeparator jsLine = new JSeparator(SwingConstants.HORIZONTAL);
        jsLine.setForeground(Color.BLACK);
        jsLine.setBounds(30,80,720,15);
        
        
        JLabel jlbTitle = new JLabel("Title");
        jlbTitle.setFont(new Font("Arial", Font.BOLD, 18));
        jlbTitle.setBounds(130,120,120,25);
        JLabel jlbTitleSize = new JLabel("(size : 30)");
        jlbTitleSize.setFont(new Font("Arial", Font.PLAIN, 12));
        jlbTitleSize.setForeground(Color.RED);
        jlbTitleSize.setBounds(180,122,120,25);
        jtfTitle = new JTextField();
        jtfTitle.setFont(new Font("Arial", Font.PLAIN, 16));
        jtfTitle.setBounds(130,150,400,25);
        
        JLabel jlbDescription = new JLabel("Description");
        jlbDescription.setFont(new Font("Arial", Font.BOLD, 18));
        jlbDescription.setBounds(130,190,120,25);
        jtfDescription = new JTextField();
        jtfDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        jtfDescription.setBounds(130,220,400,25);
        
        JLabel jlbCategory = new JLabel("Category");
        jlbCategory.setFont(new Font("Arial", Font.BOLD, 18));
        jlbCategory.setBounds(130,265,120,25);
        jcbCategory = new JComboBox();
        jcbCategory.setFont(new Font("Arial", Font.PLAIN, 16));
        jcbCategory.setBounds(130,295,400,25);
        jcbCategory.addItem("");
        for (String category : Validation.CATEGORY_LIST) {
            jcbCategory.addItem(category);
        }//for
        jcbCategory.setBackground(Color.WHITE);
        
        JLabel jlbURL = new JLabel("URL");
        jlbURL.setFont(new Font("Arial", Font.BOLD, 18));
        jlbURL.setBounds(130,340,120,25);
        jtfURL = new JTextField();
        jtfURL.setFont(new Font("Arial", Font.PLAIN, 16));
        jtfURL.setBounds(130,370,400,25);
        
        jbtExit = new JButton("Exit");
        jbtExit.setFont(new Font("Arial", Font.BOLD, 18));
        jbtExit.setBackground(new Color(255, 255, 255));
        jbtExit.setBounds(260,440,120,30);
        
        jbtEdit = new JButton("Edit");
        jbtEdit.setFont(new Font("Arial", Font.BOLD, 18));
        jbtEdit.setBackground(new Color(50, 255, 50));
        jbtEdit.setBounds(410,440,120,30);
        
        panel = new JPanel();
        panel.setLayout(null);
        
        panel.add(jlbPageTitle);
        
        panel.add(jsLine);
        
        panel.add(jlbTitle);
        panel.add(jlbTitleSize);
        panel.add(jtfTitle);
        
        panel.add(jlbDescription);
        panel.add(jtfDescription);
        
        panel.add(jlbCategory);
        panel.add(jcbCategory);
        
        panel.add(jlbURL);
        panel.add(jtfURL);

        panel.add(jbtExit);
        panel.add(jbtEdit);
    }//EditURLPageUI
    
    public void setURL(URLBookmark url){
        this.url = url;
    }
    
    public URLBookmark getURL(){
        return this.url;
    }
    
    public JPanel getPanel(){
        return this.panel;
    }
    
    public JButton getJBTExit(){
        return this.jbtExit;
    }
    
    public JButton getJBTEdit(){
        return this.jbtEdit;
    }
    
    public void diaplayAllData(){
        this.jtfTitle.setText(this.url.getTitle());
        this.jtfDescription.setText(this.url.getDescription());
        this.jcbCategory.setSelectedItem(this.url.getCategory());
        this.jtfURL.setText(this.url.getURL());
    }//diaplayURL
    
    public URLBookmark getUserInput(){
        URLBookmark userInputURL = new URLBookmark(this.jtfTitle.getText().trim(),
                                            this.jtfDescription.getText().trim(),
                                            (String)this.jcbCategory.getSelectedItem(),
                                            this.jtfURL.getText().trim()
                                        );
        return userInputURL;
    }//getUserInput
    
    public void clearUserInput(){
        this.jtfTitle.setText("");
        this.jtfDescription.setText("");
        this.jcbCategory.setSelectedItem("");
        this.jtfURL.setText("");
    }//clearUserInput

}//EditURLPageUI
