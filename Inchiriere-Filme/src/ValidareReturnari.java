import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

class ValidareReturnari extends JFrame {
    
    JButton bInapoi,bCauta;
    JLabel l;
    JPanel p1,p2;
    JTextField t1;
    Date data_returnarii;
    ValidareReturnari(){
        super("Validare imprumuturi");
        
        p1 = new JPanel(); 
        p2 = new JPanel();
        
        p1.setPreferredSize(new Dimension(750,100));
        l=new JLabel("Pentru a cauta imprumutul te rugam sa introduci codul unic ce a fost trimis la achizitionare:");
        t1 = new JTextField(15);
        p1.add(l);
        p1.add(t1);
        bInapoi = new JButton("Inapoi");
        bCauta = new JButton("Cautare imprumut");
        AscultatorReturnari ar = new AscultatorReturnari();
        bCauta.addActionListener(ar);
        bInapoi.addActionListener(ar);
        p2.add(bInapoi);
        p2.add(bCauta);

        getContentPane().add(p1,BorderLayout.NORTH);
        getContentPane().add(p2,BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    class AscultatorReturnari implements ActionListener{
        ArrayList<String> filme_returnate = new ArrayList<String>();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        public void actionPerformed(ActionEvent ev){
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            CitireImprumuturi ci = new CitireImprumuturi();

            long dif_milisecunde,dif_zile;
            if(ci.citeste("Imprumuturi.txt", t1.getText()).matches("") == false)
            {   
                String[] split = ci.citeste("Imprumuturi.txt", t1.getText()).split("/");
                filme_returnate.add(split[0]);
                
                try{
                    date = sdf.parse(split[1]);
                    cal.setTime(date);
                }catch(ParseException ex){;}
                //cal.setTime(date);
                
                Calendar data_de_azi = Calendar.getInstance();
                dif_milisecunde = data_de_azi.getTimeInMillis() - cal.getTimeInMillis();
                dif_zile = dif_milisecunde / (24 * 60 * 60 * 1000);
               

                GestionareImprumuturi gi = new GestionareImprumuturi(filme_returnate,split[2],dif_zile);
                gi.l3.setText("Data returnarii: " + split[1]);
                gi.l4.setText("Imprumutul: " + split[0]);
                gi.setVisible(true);
                setVisible(false);
                
            }
            if(ev.getSource() == bInapoi){
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
