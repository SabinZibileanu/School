//Copyright Popa Alexandru
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class InterfataInceput extends JFrame{
    JButton bPropietar,bClient,bExistent;

    InterfataInceput(){
        super("Bine ati venit la magazinul Hollywood Music&Film");
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1,3));
        p1.setSize(600,700);
        JLabel l1 = new JLabel("Selecteaza optiunea pe care o doresti:");
        bPropietar = new JButton("Cont:Propietar");
        bClient = new JButton("Creare Abonament Client");
        bExistent = new JButton("Am deja abonament de client");
        AscultatorButon AB = new AscultatorButon();
        bClient.addActionListener(AB);
        bExistent.addActionListener(AB);
        bPropietar.addActionListener(AB);
        p1.add(l1);
        p1.add(bPropietar);
        p1.add(bClient);
        p1.add(bExistent);

        add(p1,BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
        ev){System.exit(0);}});
    }

    class AscultatorButon implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            if(ev.getSource() == bClient){
                InterfataAbonamentClient client = new InterfataAbonamentClient();
                client.setVisible(true);
                setVisible(false);
            }
            else
                if(ev.getSource() == bExistent){
                    InterfataLoginClienti ifc = new InterfataLoginClienti();
                    ifc.setVisible(true);
                    setVisible(false);
                    
                }
            if(ev.getSource() == bPropietar){
                //OperatiiProprietar op = new OperatiiProprietar();
                //op.setVisible(true);
                LoginProprietar lp = new LoginProprietar();
                lp.setVisible(true);
                setVisible(false);
            }

        }
    }
}
