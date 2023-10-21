import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JOptionPane;
import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import javax.swing.*;

class InterfataPlataCasiera extends JFrame{
    JButton bPlateste,bInapoiLaListaFilme,bTrimiteCodUnic;
    JPanel p1,p2;
    JLabel l1,l2;
    JTextField t1;
    Imprumut i;
    ArrayList<Imprumut>imprumuturi = new ArrayList<>();
    
    InterfataPlataCasiera(Imprumut i){
        super("Plateste casierei");
        this.i = i;
        p1 = new JPanel();
        p1.setLayout(new GridLayout(3,2));
        p1.setPreferredSize(new Dimension(550, 100));
        l1 = new JLabel();
        l2 = new JLabel("Pentru a putea confirma plata,te rugam confirma codul unic pe care ti-l vom trimite ");
        t1 = new JTextField(15);
        bPlateste = new JButton("Plateste");
        bInapoiLaListaFilme = new JButton("Inapoi la lista filmelor");
        bTrimiteCodUnic = new JButton("Trimite codul unic");
        AscultatorPlata ap = new AscultatorPlata();
        p1.add(l1);
        p1.add(l2);
        p1.add(t1);
        p2 = new JPanel();
        p2.add(bPlateste);
        bPlateste.addActionListener(ap);
        bTrimiteCodUnic.addActionListener(ap);
        
        p2.add(bTrimiteCodUnic);

        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
        ev){System.exit(0);}});
    }
  
    class AscultatorPlata implements ActionListener{
        Random cod_unic = new Random();
        int generat = cod_unic.nextInt(1001,10000);
        public void actionPerformed(ActionEvent ev){
            if(ev.getSource() == bTrimiteCodUnic){
                System.out.println(generat);
            }
            if(ev.getSource() == bPlateste){
                try{
                    if(Integer.parseInt(t1.getText()) == generat)
                    {   
                        ChitantaCumparareFilme ccf = new ChitantaCumparareFilme();
                        ccf.l1.setText("Filmele tale sunt: " + i.nume_filme);
                        ccf.l2.setText("Data returnarii: " + i.data);
                        ccf.l3.setText("Costul inchirierii: " + i.cost + " RON");
                        ccf.setVisible(true);
                        setVisible(false);

                        try (FileWriter fw = new FileWriter("Imprumuturi.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw)) {
                    //for(int j = 0 ; j < i.nume_filme.size() ; j++)
                    bw.write(i.nume_filme.toString()+ "/");
                    bw.write(String.valueOf(i.data) + "/");
                    bw.write(i.cost + "/");
                    bw.write(String.valueOf(generat));
                    bw.newLine();
                    
                    }
                    catch(Exception e){
                        System.out.print("Fisier negasit");
                    }
                    }
                }catch(NumberFormatException ex){JOptionPane.showMessageDialog(InterfataPlataCasiera.this, "Cod unic gresit");}
            }
            
        }
    }
}
