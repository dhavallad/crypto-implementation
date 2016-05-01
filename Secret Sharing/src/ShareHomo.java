import com.shamir.SecretShare;
import com.shamir.Shamir;
/**
 * Created by dhavallad on 4/27/16.
 */
/* 
 *  Files: Share.java
 *  Courtesy: Robert Bisewski
 */
 
 //the necessary imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.util.Vector;

//this is where the class starts
public class ShareHomo extends JFrame implements ActionListener {

    //this string defines the text of the About Program button
    String aboutTxt = "Courtesy: Robert Bisewski, Former ACS Student of University of Winnipeg";
                         
    //this string defines the text of Display Instructions button
    String instructTxt = "Instructions:\n" +
                      "------------------\n\n" +
      
                         "Load/Enter Text: This opens a text\n" +
                         "file that the user selects and dumps\n" +
                         "its contents to the original text box.\n\n" +
                         
                         "Create Shares: This takes the contents\n" +
                         "of the original text box, creates five\n" +
                         "shares, and places them into their boxes.\n\n" +
                          
                         "Reconstruct Original: This takes the\n" +
                         "shares placed in the share boxes and\n" +
                         "uses them to recreate the original text.\n" +
                         "Click the checkboxes to decide which\n" +
                         "shares you wish to use.";

    //the filter that describes what files can be opened
    FileNameExtensionFilter fnef = new FileNameExtensionFilter("Text File", "txt", "TXT");

    //the text-areas and the scrollbars used in this program
    private JTextArea originaltxt = new JTextArea("Original Numbers");
    private JScrollPane scrBar    = new JScrollPane(originaltxt);
    private JTextArea retxt       = new JTextArea("");
    private JScrollPane RscrBar   = new JScrollPane(retxt);
    private JTextArea share1      = new JTextArea("");
    private JScrollPane scrBar1   = new JScrollPane(share1);
    private JTextArea share2      = new JTextArea("");
    private JScrollPane scrBar2   = new JScrollPane(share2);
    private JTextArea share3      = new JTextArea("");
    private JScrollPane scrBar3   = new JScrollPane(share3);
    private JTextArea share4      = new JTextArea("");
    private JScrollPane scrBar4   = new JScrollPane(share4);
    private JTextArea share5      = new JTextArea("");
    private JScrollPane scrBar5   = new JScrollPane(share5);    

    //this specifies the checkboxes used with this program
    final JCheckBox chk1 = new JCheckBox("", false);
    final JCheckBox chk2 = new JCheckBox("", false);
    final JCheckBox chk3 = new JCheckBox("", false);
    final JCheckBox chk4 = new JCheckBox("", false);
    final JCheckBox chk5 = new JCheckBox("", false);

    //this buttons used in this program
    final JButton bttLoad    = new JButton("Load Numbers from File");
    final JButton bttCreate  = new JButton("Create Shares");
    final JButton bttRecon   = new JButton("Reconstruct Average");
    final JButton bttAveg    = new JButton("Compute Average");
    final JButton bttDisplay = new JButton("Display Instructions");
    final JButton bttAbout   = new JButton("About Program");
    final JButton bttQuit    = new JButton("Quit Program");
  
  //the default constructor for this program
  public ShareHomo() {
  
    //defines the details of the scrollbars
    originaltxt.setLineWrap(true);
    originaltxt.setWrapStyleWord(true);
    scrBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
    scrBar1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    share1.setLineWrap(true);
    share1.setWrapStyleWord(true);
    
    scrBar2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    share2.setLineWrap(true);
    share2.setWrapStyleWord(true);
    
    scrBar3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    share3.setLineWrap(true);
    share3.setWrapStyleWord(true);
    
    scrBar4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    share4.setLineWrap(true);
    share4.setWrapStyleWord(true);
    
    scrBar5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    share5.setLineWrap(true);
    share5.setWrapStyleWord(true);
    
    RscrBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    retxt.setLineWrap(true);
    retxt.setWrapStyleWord(true);
    
    //defines the size of several objects
    scrBar.setPreferredSize(new Dimension(170,160));
    scrBar1.setPreferredSize(new Dimension(170,160));
    chk1.setPreferredSize(new Dimension(175,15));
    scrBar2.setPreferredSize(new Dimension(170,160));
    chk2.setPreferredSize(new Dimension(175,15));
    scrBar3.setPreferredSize(new Dimension(170,160));
    chk3.setPreferredSize(new Dimension(175,15));
    scrBar4.setPreferredSize(new Dimension(170,160));
    chk4.setPreferredSize(new Dimension(175,15));
    scrBar5.setPreferredSize(new Dimension(170,160));
    chk5.setPreferredSize(new Dimension(175,15));
    RscrBar.setPreferredSize(new Dimension(170,160));
    bttLoad.setPreferredSize(new Dimension(170,25));
    bttCreate.setPreferredSize(new Dimension(170,25));
    bttRecon.setPreferredSize(new Dimension(170,25));
    bttAveg.setPreferredSize(new Dimension(170,25));
    bttDisplay.setPreferredSize(new Dimension(170,25));       
    bttAbout.setPreferredSize(new Dimension(170,25));
    bttQuit.setPreferredSize(new Dimension(170,25));
  
    //defines the panels used in this program
    JPanel menu = new JPanel(); menu.setBorder(BorderFactory.createTitledBorder("Menu Options:"));
    menu.setPreferredSize(new Dimension(200,220));   
    JPanel originalPan = new JPanel(); originalPan.setBorder(BorderFactory.createTitledBorder("Secret Numbers:"));
    originalPan.setPreferredSize(new Dimension(200,220));
    JPanel share1Pan = new JPanel(); 
    share1Pan.setBorder(BorderFactory.createTitledBorder("Share 1:"));
    share1Pan.setPreferredSize(new Dimension(200,220));
    JPanel share2Pan = new JPanel();
    share2Pan.setBorder(BorderFactory.createTitledBorder("Share 2:"));
    share2Pan.setPreferredSize(new Dimension(200,220));
    JPanel share3Pan = new JPanel();
    share3Pan.setBorder(BorderFactory.createTitledBorder("Share 3:"));
    share3Pan.setPreferredSize(new Dimension(200,220));
    JPanel share4Pan = new JPanel();
    share4Pan.setBorder(BorderFactory.createTitledBorder("Share 4:"));
    share4Pan.setPreferredSize(new Dimension(200,220));
    JPanel share5Pan = new JPanel();
    share5Pan.setBorder(BorderFactory.createTitledBorder("Share 5:"));
    share5Pan.setPreferredSize(new Dimension(200,220));
    JPanel rePan = new JPanel();
    rePan.setBorder(BorderFactory.createTitledBorder("Reconstructed Average:"));
    rePan.setPreferredSize(new Dimension(200,220));
    
    //defines the layouts of the Content Pane
    getContentPane().setLayout(new FlowLayout());
    
   //adds the panels to the Content Pane
   menu.add(bttLoad);
   menu.add(bttCreate);
   menu.add(bttRecon);
   menu.add(bttAveg);
   menu.add(bttDisplay);
   menu.add(bttAbout);
   menu.add(bttQuit);
   originalPan.add(scrBar);
   rePan.add(RscrBar);
   share1Pan.add(scrBar1);
   share1Pan.add(chk1);
   share2Pan.add(scrBar2);
   share2Pan.add(chk2);
   share3Pan.add(scrBar3);
   share3Pan.add(chk3);
   share4Pan.add(scrBar4);
   share4Pan.add(chk4);
   share5Pan.add(scrBar5);
   share5Pan.add(chk5);
   getContentPane().add(originalPan);
   getContentPane().add(share1Pan);
   getContentPane().add(share2Pan);
   getContentPane().add(share3Pan);
   getContentPane().add(rePan);
   getContentPane().add(share4Pan);
   getContentPane().add(share5Pan);
   getContentPane().add(menu);
   
   //adds ActionListeners to this program's components
   bttLoad.addActionListener(this);
   bttCreate.addActionListener(this);
   bttRecon.addActionListener(this);
   bttAveg.addActionListener(this);
   bttDisplay.addActionListener(this);
   bttAbout.addActionListener(this);
   bttQuit.addActionListener(this);
   
   //this sets essential values of the GUI
   setTitle("ICSI-526/426 Ass#4: SSS & Homomorphism");
   setSize(840,540);
   setResizable(false);
   addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent e) {System.exit(0);}});
  }

  ////////////////////////////////////////////////////////
  //Basic Program Functions for Input/Output Starts Here// 
  ////////////////////////////////////////////////////////
  
  //this opens a file via the JFileChooser, returns file name & location
  public String SelectFile() {
  
    //variable declaration
    String path = null;
  
    //this creates a JFileChooser gui in order to select a file
    JFileChooser jfc = new JFileChooser();
    jfc.setFileFilter(fnef);
    int buttonClicked = jfc.showOpenDialog(new JLabel("Open File"));
    
    //this checks which button was pressed on the JFileChooser GUI
    if (buttonClicked == JFileChooser.APPROVE_OPTION) {
      path = "" + jfc.getCurrentDirectory() + "\\"
               + (jfc.getSelectedFile().getName()).toLowerCase();
    }
    return path;
  }

  //this reads the contents of the text area to a text file
  public void txtRead(String file) {
   
   //this buffers, then reads the file
    StringBuffer s = new StringBuffer();
    try {
      BufferedReader input = new BufferedReader(new FileReader(file));
      try {
        String line = "";
        while ((line = input.readLine()) != null) {s.append(line + "\n");}
      }
      finally {input.close();}
     }
     catch (Exception x){JOptionPane.showMessageDialog(null, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);}
    
   //this display the text
   originaltxt.setText(s.toString());
  }

  //this divides the original text into 5 separate shares
  public void txtShare() 
  {
    //YOU WILL WRITE YOUR CODE HERE
  }

  //this compute the average of shares and dumps the result to the shares area
  public void txtAveg() 
  {
  //YOU WILL WRITE YOUR CODE HERE
  }
  
  //this reconstructs the shares and dumps the result to the reconstructed text area
  public void txtRecon() 
  {
  //YOU WILL WRITE YOUR CODE HERE
  }

  //////////////////////////////////////////////////////
  //Basic Program Functions for Input/Output Ends Here// 
  //////////////////////////////////////////////////////
  

  //this determines what button has been pressed on the GUI
  public void actionPerformed(ActionEvent aE) {
    if (aE.getSource() == bttLoad)    {txtRead(SelectFile());}
    if (aE.getSource() == bttCreate)  {txtShare();}
    if (aE.getSource() == bttRecon)   {txtRecon();}
    if (aE.getSource() == bttAveg)    {txtAveg();}
    if (aE.getSource() == bttDisplay) {JOptionPane.showMessageDialog(null,instructTxt);}
    if (aE.getSource() == bttAbout)   {JOptionPane.showMessageDialog(null,aboutTxt);}
    if (aE.getSource() == bttQuit)    {System.exit(0);}
  }
  
  //this is the program's main method, which creates a new application with title, then checks if closes
  public static void main(String args[]) {
     SwingUtilities.invokeLater(new Runnable() {public void run() {ShareHomo gui = new ShareHomo(); gui.setVisible(true);}});
  }
}