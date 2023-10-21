import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class AfisClient extends JFrame {
    JButton bExit,bInchiriazaFilme,bVeziFilmele,bReturneaza;
    JLabel l1,l2,l3,l4,l5,l6,l7;
    AfisClient(){
        super("Informatii abonament");
        
        JPanel p1 = new JPanel(); 
        p1.setLayout(new GridLayout(7,2));
        p1.setPreferredSize(new Dimension(400,500));
        bInchiriazaFilme = new JButton("Inchiriaza filme");
        bExit = new JButton("Exit");
        bVeziFilmele = new JButton("Vezi toate filmele din magazin(inchiriate/neinchiriate)");
        bReturneaza = new JButton("Returneaza un film");
        AscultatorB abVeziFilme = new AscultatorB();
        bVeziFilmele.addActionListener(abVeziFilme);
        AscultatorB abInchiriaza = new AscultatorB();
        bInchiriazaFilme.addActionListener(abInchiriaza);
        bReturneaza.addActionListener(abInchiriaza);
        bExit.addActionListener(abInchiriaza);
        l1 = new JLabel();
        l2 = new JLabel();
        l3 = new JLabel();
        l4 = new JLabel();
        l5 = new JLabel();
        l6 = new JLabel();
        p1.add(l1);
        p1.add(l2);
        p1.add(l3);
        p1.add(l4);
        p1.add(l5);
        p1.add(l6);
        Panel p2=new Panel();
        p2.add(bInchiriazaFilme);
        p2.add(bExit);
        p2.add(bVeziFilmele);
        p2.add(bReturneaza);

        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
        ev){System.exit(0);}});
    }

    class AscultatorB implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            if(ev.getSource() == bVeziFilmele){
                InterfataFilme1 if1 = new InterfataFilme1();
                if1.setVisible(true);
                setVisible(false);
            }
            if(ev.getSource() == bInchiriazaFilme){
                VizualizareInchiriereFilme inchiriere = new VizualizareInchiriereFilme();
                inchiriere.setVisible(true);
                setVisible(false);
            }
            if(ev.getSource() == bReturneaza){
                ValidareReturnari gr = new ValidareReturnari();
                gr.setVisible(true);
                setVisible(false);
                
            }
            if(ev.getSource() == bExit){
                InterfataInceput ii = new InterfataInceput();
                ii.setVisible(true);
                setVisible(false);
            }
        }
    }
}
