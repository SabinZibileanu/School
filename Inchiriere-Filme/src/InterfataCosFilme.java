import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class InterfataCosFilme extends JFrame{
    JButton bInapoi,bReset,bCatrePlata;
    JPanel p1,p2;
    JLabel l1,l2;
    JTextField t1;
    ArrayList<String>filme_selectate = new ArrayList<String>();
    InventarFilme cf = new InventarFilme();
   
    InterfataCosFilme(){
        super("Selectie copii film");
        p1 = new JPanel();
        p2 = new JPanel();
        l1 = new JLabel();
        bInapoi = new JButton("Inapoi la tab-ul cu filme");
       
        AscultatorSelector ab = new AscultatorSelector();
        p1.setPreferredSize(new Dimension(450,200));
        p2.setSize(400,500);
        p1.add(l1);
    
        p2.add(bInapoi);      
        bInapoi.addActionListener(ab);
        
        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
        ev){System.exit(0);}});

    }

    class AscultatorSelector implements ActionListener{
    
        public void actionPerformed(ActionEvent ev){
            InventarFilme cf = new InventarFilme();
            cf.citeste("filme.txt");
            if(ev.getSource() == bInapoi){
                setVisible(false);     
            }   
    }
}
}