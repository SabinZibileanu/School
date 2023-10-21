import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Calendar;
import javax.swing.*;

class InterfataFilme1 extends JFrame {
    JButton bInapoi,bVeziCos,bCatreCasiera;
    JButton[] bInchiriaza,bStergeDinCos;
    JPanel p1,p2;
    JLabel l[],l2;
    ArrayList<String> f = new ArrayList<>();
    Imprumut i;
    InterfataFilme1(){
        super("Lista cu filme");
        i = new Imprumut();
        p1 = new JPanel();
        InventarFilme cf = new InventarFilme();
        cf.citeste("Filme.txt");
        int dim = cf.Filme.size(); 
        p1.setLayout(new GridLayout(dim,2));
        p1.setSize(400,500);
        bInapoi = new JButton("Inapoi acasa");
        bVeziCos = new JButton("Vezi cosul tau");
        bCatreCasiera = new JButton("Spre casiera");
        
        bInchiriaza = new JButton[dim];
        bStergeDinCos = new JButton[dim];
        l=new JLabel[dim];
        AscultatorInchiriere aInchiriaza = new AscultatorInchiriere();
        bCatreCasiera.setEnabled(false);

        for(int i = 0 ; i < dim ; i++)
        {   
            bInchiriaza[i] = new JButton("Adauga in cos");
            bStergeDinCos[i] = new JButton("Sterge din cos");
            
            bInchiriaza[i].addActionListener(aInchiriaza);
            bStergeDinCos[i].addActionListener(aInchiriaza);
            l[i] = new JLabel(" " + cf.Filme.get(i));

            if(cf.Filme.get(i).nrCopii.matches("0") == false)
            l[i] = new JLabel(" " + cf.Filme.get(i));   
            else
                {
                    l[i] = new JLabel(" " + cf.Filme.get(i));
                    bInchiriaza[i].setEnabled(false);
                }
            bStergeDinCos[i].setEnabled(false);
            p1.add(l[i]);
            p1.add(bInchiriaza[i]);
            p1.add(bStergeDinCos[i]);
            
        }
        
        Panel p2=new Panel();
        p2.add(bVeziCos);
        bVeziCos.addActionListener(aInchiriaza);
        bCatreCasiera.addActionListener(aInchiriaza);
        bInapoi.addActionListener(aInchiriaza);
        p2.add(bInapoi);
        p2.add(bCatreCasiera);

        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
        ev){System.exit(0);}});
    }

    class AscultatorInchiriere implements ActionListener{
        double cost = 0;
       
        public void actionPerformed(ActionEvent ev) {
            InventarFilme cf = new InventarFilme();
            cf.citeste("Filme.txt");
            int dim = cf.Filme.size();
            Calendar data = Calendar.getInstance();
            data.add(Calendar.DATE,+7);
            
            for(int i = 0 ; i < dim ; i++){
                if(ev.getSource() == bInchiriaza[i]){

                    bStergeDinCos[i].setEnabled(true);
                    if(f.size() < 5)
                    {
                        f.add(cf.Filme.get(i).numeFilm);
                        
                        JOptionPane.showMessageDialog(InterfataFilme1.this,"Filmul " + cf.Filme.get(i).numeFilm + " a fost adaugat cu succes");
                        String vechi = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + cf.Filme.get(i).nrCopii;
                        int stoc = Integer.parseInt(cf.Filme.get(i).nrCopii);
                        stoc--;
                        String nou = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + String.valueOf(stoc);
                        try{
                            cf.actualizare_stoc("Filme.txt", vechi, nou);
                       }catch(IOException ex){;}
                        if(stoc == 0)
                        bInchiriaza[i].setEnabled(false);
                        bCatreCasiera.setEnabled(true);
                        if(cf.Filme.get(i).tipFilm.matches("DVD"))
                        cost+= 14;
                        else cost+= 7;
                    }
                    else{
                        JOptionPane.showMessageDialog(InterfataFilme1.this,"Nu poti inchiria in avans mai mult de 5 filme");
                        
                    } 
                }
              
            }
            for(int i = 0 ; i < dim ; i++){
            if(ev.getSource() == bStergeDinCos[i]){
                f.remove(cf.Filme.get(i).numeFilm);
                String vechi = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + cf.Filme.get(i).nrCopii;
                int stoc = Integer.parseInt(cf.Filme.get(i).nrCopii);
                stoc++;
                String nou = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + String.valueOf(stoc);
                try{
                    cf.actualizare_stoc("Filme.txt", vechi, nou);
               }catch(IOException ex){;}
                
               
                if(f.contains(cf.Filme.get(i).numeFilm) == false)
                bStergeDinCos[i].setEnabled(false);
                bInchiriaza[i].setEnabled(true);
                if(f.size() == 0)
                bCatreCasiera.setEnabled(false);

                if(cf.Filme.get(i).tipFilm.matches("DVD"))
                cost-= 14;
                    else cost-= 7;
            }
        }
            if(ev.getSource() == bVeziCos){
                InterfataCosFilme icf = new InterfataCosFilme();
                icf.setVisible(true);
                icf.l1.setText("Cosul tau contine urmatoarele filme: " + f);
               
            }
            if(ev.getSource() == bCatreCasiera){
                setVisible(false);
                
                
                i = new Imprumut(f,data.getTime(),String.valueOf(cost));
                InterfataPlataCasiera ipc = new InterfataPlataCasiera(i);
                
                ipc.setVisible(true);
                ipc.l1.setText("Va trebui sa platesti in avans pentru urmatoarele filme " + f);
                
            }
            if(ev.getSource() == bInapoi){
                InterfataInceput ii = new InterfataInceput();
                ii.setVisible(true);
                setVisible(false);
            }
        }
        
    }
}
