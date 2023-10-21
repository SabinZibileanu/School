//Copyright Popa Alexandru
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.BufferedWriter;
import javax.swing.*;

class InterfataAbonamentClient extends JFrame{
    JButton bCreareAbonament,bReset;
    JTextField t[];
    JLabel l[];
    int dim = 7;
    String[]date = {"Nume","Prenume","Serie CI","Numar CI","CNP","Numar Telefon"};
    String[]perioade = {"8 luni","12 luni"};
    JComboBox cb1 = new JComboBox(perioade);
    ArrayList<String> v1 = new ArrayList<>();
    
    
    InterfataAbonamentClient(){
    super("Creare Abonament Client");
    AscultatorB ab = new AscultatorB();
    Panel p1 = new Panel(); 
    p1.setLayout(new GridLayout(dim,2));
    t=new JTextField[dim];
    l=new JLabel[dim];

    for(int i = 0 ; i < dim  ; i++){
        t[i] = new JTextField(15);
        if(i != dim - 1)
            {
                l[i] = new JLabel(date[i] + ":");
                p1.add(l[i]);
                p1.add(t[i]);
            }
        if(i == dim - 1){
            l[6] = new JLabel("Periaoda Abonament:");
            p1.add(l[6]);
            p1.add(cb1);
            p1.setSize(400,500);
        }
        
    }
    bCreareAbonament = new JButton("Submit");
    bCreareAbonament.addActionListener(ab);
    bReset=new JButton("Reset");
    bReset.addActionListener(ab);

    Panel p2=new Panel();
    p2.add(bCreareAbonament);
    p2.add(bReset);

    add(p1,BorderLayout.NORTH);
    add(p2,BorderLayout.CENTER);

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
    ev){System.exit(0);}});
}
    class AscultatorB implements ActionListener{
        Client c = new Client();
        public AscultatorB(Client c)
        {
            this.c = c;
        }
        public AscultatorB(){

        }

        public void actionPerformed(ActionEvent ev){
        long total;
        String pretAB;
        ValideazaParola vae = new ValideazaParola();
           if(ev.getSource() == bCreareAbonament){   
                
                for(int i = 0 ; i < dim - 1 ; i++){
                    if(c.valideazaCNP(t[4].getText()) == true && t[i].getText().equals("") == false && c.valideazaSerie(t[2].getText()) == true && c.valideazaNrCNP(t[3].getText()) == true && c.valideazaNrTel(t[5].getText()) == true && vae.valideaza_login("ClientiExistenti.txt", t[4].getText()) == false)
                    v1.add(t[i].getText());
                    else
                        v1.clear();
                   }
                    try{
                    for(int i = 3 ; i <= 5 ; i++)
                    total = Long.parseLong(t[i].getText());

                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(InterfataAbonamentClient.this,"Nu s-a putut creea abonamentul,verificati toate optiunile");
                    v1.clear();
                    return;
                }
                if(v1.size() == 0)
                   JOptionPane.showMessageDialog(InterfataAbonamentClient.this,"Nu s-a putut creea abonamentul,verificati toate optiunile");
                else{
                    pretAB = String.valueOf(cb1.getSelectedItem());
                    if(pretAB.matches("8 luni"))
                        pretAB = "5 RON";
                    else
                        pretAB = "8 RON";
                    
                    c = new Client(t[0].getText(),t[1].getText(),t[2].getText(),t[3].getText(),t[4].getText(),t[5].getText());
                    AfisClient ac = new AfisClient();
                    ac.l1.setText("Nume: " + t[0].getText());
                    ac.l2.setText("Prenume: " + t[1].getText());
                    ac.l3.setText("Pret abonament:" + pretAB);
                    ac.l4.setText("Durata abonament:" + String.valueOf(cb1.getSelectedItem()));
                    ac.l5.setText("CNP:" + t[4].getText());
                    ac.l6.setText("Numar telefon:" + t[5].getText());
                    ac.bReturneaza.setEnabled(false);
                    ac.setVisible(true);
                    setVisible(false);
                    
                    try (FileWriter fw = new FileWriter("ClientiExistenti.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(t[4].getText());
                    bw.newLine();
                    }
                    catch(Exception e){
                        System.out.print("Fisier negasit");
                    }
                    
                }
                   
           }
           else{
               for(int i = 0 ; i < dim ; i++)
               t[i].setText("");
           }
        }
    }
}
    
