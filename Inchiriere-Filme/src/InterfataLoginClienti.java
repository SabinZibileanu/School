import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

import javax.swing.*;

class InterfataLoginClienti extends JFrame{
    JLabel l1,l2,l3;
    JTextField t1,t2,t3;
    JPanel p1,p2;
    JButton bLogin,bClientNou;

    InterfataLoginClienti(){
        super("Login");
        p1 = new JPanel();
        p2 = new JPanel();
        l3 = new JLabel("Introdu aici CNP-ul");
        t3 = new JTextField(20);
        bLogin = new JButton("Login");
        bClientNou = new JButton("Nu ai cont de client? Te invitam sa iti creezi unul chiar acum!");
        AscultatorLogin ab = new AscultatorLogin();
        bLogin.addActionListener(ab);
        bClientNou.addActionListener(ab);

        p1.setLayout(new GridLayout(1,2));
        p1.setSize(400,500);
        p1.add(l3);
        p1.add(t3);

        p2.setSize(400,500);
        p2.add(bLogin);
        p2.add(bClientNou);
        

        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
        ev){System.exit(0);}});
    }

    class AscultatorLogin implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            if(ev.getSource() == bClientNou){
                InterfataAbonamentClient client_nou = new InterfataAbonamentClient();
                client_nou.setVisible(true);
                setVisible(false);
            }
            if(ev.getSource() == bLogin){
                ValideazaParola vae = new ValideazaParola();
                if(vae.valideaza_login("ClientiExistenti.txt",t3.getText())){
                    AfisClient ac = new AfisClient();
                    ac.l1.setText("Bine ai revenit pe site-ul nostru.Ne bucuram ca ne-ai ales din nou");
                    ac.l2.setText("Stocul nostru de filme este actualizat zilnic de catre proprietar");
                    ac.l3.setText("Data de astazi este " + Calendar.getInstance().getTime());
                    ac.setVisible(true);
                    setVisible(false);
                }

            }
        }
    }
}
