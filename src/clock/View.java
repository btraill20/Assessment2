package clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import priorityqueue.PriorityQueue;
import priorityqueue.QueueUnderflowException;

public class View implements Observer {
    Controller controller;
    PriorityQueue<Alarms> q; 
    
    JOptionPane optionPane;
    ClockPanel panel;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    private View view;
    
    public View(Model model) {
        final Controller control = new Controller(model, view);
        final JFrame frame = new JFrame();
        panel = new ClockPanel(model);
        //frame.setContentPane(panel);
        frame.setTitle("Java Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //start of menu bar
        menuBar = new JMenuBar();
        
        menu = new JMenu("Settings");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
        "The only menu in this program that has menu items");
        menuBar.add(menu);
        
        //menu item for opening a alarm
        JMenuItem menuOpen = new JMenuItem("Open..", KeyEvent.VK_O);
        Action openAction = new AbstractAction("Open") {
        @Override
            public void actionPerformed(ActionEvent e) {
                            // create a dialog Box 
            JDialog d = new JDialog(frame, "Open Alarm"); 
  
            // create a label 
            JLabel l = new JLabel("Select alarm below:"); 
            
            //create a button
            JButton b = new JButton("Open");
            b.addActionListener(this);
            
            //adds items to the dialog
            d.add(l); 
            d.add(b);
            // setsize of dialog 
            d.setSize(200, 200); 
            // set visibility of dialog 
            d.setVisible(true);
            }
        };
        openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        menuOpen.setAction(openAction);
        menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        menu.add(menuOpen);
        
        //menu item for saving a alarm
        JMenuItem menuSave = new JMenuItem("Save alarm", KeyEvent.VK_S);
        Action saveAction = new AbstractAction("Save") {
        @Override
            public void actionPerformed(ActionEvent e) {
            // create a dialog Box 
            JDialog d = new JDialog(frame, "Save Alarms"); 
  
            // create a label 
            JLabel l = new JLabel("Set a Alarm to save"); 
            
            //create a button
            JButton b = new JButton("Set");
            b.addActionListener(this);
            
            //adds items to the dialog
            d.add(l); 
            d.add(b);
            // setsize of dialog 
            d.setSize(200, 200); 
            // set visibility of dialog 
            d.setVisible(true);
            }
        };
        saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        menuSave.setAction(saveAction);
        menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menu.add(menuSave);
        
        JMenuItem menuexit = new JMenuItem("Exit Application", KeyEvent.VK_Q);
        Action exitAction = new AbstractAction("Exit") {
        @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Googbye.");
                System.exit(0);
            }
        };
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        menuexit.setAction(exitAction);
        menuexit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menu.add(menuexit);
        
        frame.setJMenuBar(menuBar);
        //end of menu bar
        
        // Start of border layout code        
        Container pane = frame.getContentPane();
        
        JButton alarmHead = new JButton("Next Alarm (Top)");
        alarmHead.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            try {
                JOptionPane.showMessageDialog(frame, "Next alarm: "+ control.head());
            } catch (QueueUnderflowException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        pane.add(alarmHead, BorderLayout.PAGE_START);
         
        panel.setPreferredSize(new Dimension(200, 200));
        pane.add(panel, BorderLayout.CENTER);
         
        JButton alarmsAdd = new JButton("Set Alarm (Left)");
        alarmsAdd.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            // create a dialog Box 
            JDialog d = new JDialog(frame, "Set Alarm"); 
  
            // create a label 
            JLabel l = new JLabel("Set alarm below"); 
            
            //create a button
            JButton b = new JButton("Set");
            b.addActionListener(this);
            
            //adds items to the dialog
            d.add(l); 
            d.add(b);
            // setsize of dialog 
            d.setSize(200, 200); 
            // set visibility of dialog 
            d.setVisible(true);
        }
        });
        pane.add(alarmsAdd, BorderLayout.LINE_START);
         
        JButton button = new JButton("Edit Alarms (Bottom)");
        button.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
                        // create a dialog Box 
            JDialog d = new JDialog(frame, "Edit Current Alarms"); 
  
            // create a label 
            JLabel l = new JLabel("All alarms set shown below:"); 
            

            
            //create a button
            JButton b = new JButton("Update");
            b.addActionListener(this);
            
            //adds items to the dialog
            d.add(l); 
            d.add(b);
            // setsize of dialog 
            d.setSize(200, 200); 
            // set visibility of dialog 
            d.setVisible(true);
        }
        });
        pane.add(button, BorderLayout.PAGE_END);
         
        JButton alarmPrint = new JButton("Current Alarms (right)");
        alarmPrint.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
                        // create a dialog Box 
            JDialog d = new JDialog(frame, "All Current Alarms"); 
            JLabel l = new JLabel("Current alarms shown below:"); 
            
            String[] data = {"one", "two", "free", "four"};
            JList L = new JList(data);
            L.setSelectedIndex(1);  // select "two"
            L.getSelectedValue();  
            
            //create a button
            JButton b = new JButton("Close");
            b.addActionListener(this);
            
            //adds items to the dialog
            d.add(l);
            d.add(L);
            d.add(b);
            // setsize of dialog
            d.setSize(300, 300); 
            // set visibility of dialog 
            d.setVisible(true);
        }
        });
        pane.add(alarmPrint, BorderLayout.LINE_END);
        
        // End of borderlayout code
        frame.pack();
        frame.setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        panel.repaint();
    }
}
