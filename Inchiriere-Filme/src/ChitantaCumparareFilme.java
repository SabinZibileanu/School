import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ChitantaCumparareFilme extends JFrame{
    JButton bInapoiAcasa,bPrint;
    JLabel l1,l2,l3;
    JPanel p1,p2;
    
    ChitantaCumparareFilme(){
        super("Chitanta filmelor");
       
        p1 = new JPanel(); 
        p1.setLayout(new GridLayout(4,2));
        p1.setPreferredSize(new Dimension(450,100));
        bInapoiAcasa = new JButton("Inapoi la ecranul principal");
        bPrint = new JButton("Printeaza chitanta");
        l1 = new JLabel();
        l2 = new JLabel();
        l3 = new JLabel();
        p1.add(l1);
        p1.add(l2);
        p1.add(l3);
        
        p2 = new JPanel();
        p2.add(bInapoiAcasa);
        p2.add(bPrint);
        AscultatorC ac = new AscultatorC();
        bInapoiAcasa.addActionListener(ac);
        getContentPane().add(p1,BorderLayout.NORTH);
        getContentPane(). add(p2,BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    class AscultatorC implements ActionListener{
        //InterfataFilme1 if1 = new InterfataFilme1();
        public void actionPerformed(ActionEvent ev){
            if(ev.getSource() == bInapoiAcasa){
                InterfataInceput ii = new InterfataInceput();
                ii.setVisible(true);
                
                setVisible(false);
            }
        }
    }
}
