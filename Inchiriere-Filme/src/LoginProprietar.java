import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class LoginProprietar extends JFrame{
    JLabel l3;
    JTextField t3;
    JPanel p1,p2;
    JButton bLogin;

    LoginProprietar(){
        super("LoginProprietar");
        p1 = new JPanel();
        p2 = new JPanel();
        l3 = new JLabel("Introdu aici parola unica a proprietarului");
        t3 = new JTextField(20);
        bLogin = new JButton("Login");
        AscultatorLoginProprietar ap = new AscultatorLoginProprietar();
        bLogin.addActionListener(ap);
        

        p1.setLayout(new GridLayout(1,2));
        p1.setSize(400,500);
        p1.add(l3);
        p1.add(t3);

        p2.setSize(400,500);
        p2.add(bLogin);
        
        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
        ev){System.exit(0);}});
    }
    class AscultatorLoginProprietar implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            if(ev.getSource() == bLogin){
                ValideazaParola vae = new ValideazaParola();
                if(vae.valideaza_login("ParolaProprietar.txt",t3.getText())){
                    OperatiiProprietar op = new OperatiiProprietar();
                    setVisible(false);
                }
            }
        }
    }
}