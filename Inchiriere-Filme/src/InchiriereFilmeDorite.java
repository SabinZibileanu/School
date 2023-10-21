import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.*;

class InchiriereFilmeDorite extends JFrame{
        JLabel l[];
        JButton bInapoi,bSpreCasiera,bVeziCos;
        JButton[]bInchiriaza,bSterge;
        ArrayList<String>filme_preferinta = new ArrayList<>();
        Imprumut i;
        //ArrayList<Imprumut>imprumuturi = new ArrayList<>();

    InchiriereFilmeDorite(){
        super("Meniu filme selectate");
        Panel p1 = new Panel();
        InventarFilme cf = new InventarFilme();
        AscultatorFiltru ab = new AscultatorFiltru();
        cf.citeste("Filme.txt");
        int dim = cf.Filme.size(); 
        i = new Imprumut();
        p1.setLayout(new GridLayout(dim,2));
        p1.setSize(400,500);
        bInapoi = new JButton("Inapoi");
        bSpreCasiera = new JButton("Spre casiera");
        bVeziCos = new JButton("Vezi cosul tau");
        bInapoi.addActionListener(ab);
        l=new JLabel[dim];
        bInchiriaza = new JButton[dim];
        bSterge = new JButton[dim];

        for(int i = 0 ; i < dim ; i++){
            bInchiriaza[i] = new JButton("Adauga in cos");
            bSterge[i] = new JButton("Sterge din cos");
            
            bInchiriaza[i].addActionListener(ab);
            bSterge[i].addActionListener(ab);
            l[i] = new JLabel(" " + cf.Filme.get(i));

            if(cf.Filme.get(i).nrCopii.matches("0") == false)
            l[i] = new JLabel(" " + cf.Filme.get(i));   
            else
                {
                    l[i] = new JLabel(" " + cf.Filme.get(i));
                    bInchiriaza[i].setEnabled(false);
                }
            bSterge[i].setEnabled(false);
            bInchiriaza[i].setVisible(false);
            bSterge[i].setVisible(false);
            p1.add(l[i]);
            p1.add(bInchiriaza[i]);
            p1.add(bSterge[i]);
            l[i].setVisible(false);
        }
        
        bVeziCos.addActionListener(ab);
        bSpreCasiera.addActionListener(ab);
        bInapoi.addActionListener(ab);
        Panel p2=new Panel();
        p2.add(bVeziCos);
        p2.add(bInapoi);
        p2.add(bSpreCasiera);
        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
        ev){System.exit(0);}});
    }
    class AscultatorFiltru implements ActionListener{
        VizualizareInchiriereFilme vif = new VizualizareInchiriereFilme();
        double cost = 0;
        public void actionPerformed(ActionEvent ev){
            InventarFilme cf = new InventarFilme();
            cf.citeste("Filme.txt");
            int dim = cf.Filme.size();
            Calendar data = Calendar.getInstance();
            data.add(Calendar.DATE,+7);
            
            if(ev.getSource() == bInapoi){
               setVisible(false);
            }

            for(int i = 0 ; i < dim ; i++){
                if(ev.getSource() == bInchiriaza[i]){
                    bSterge[i].setEnabled(true);
                    if(filme_preferinta.size() < 5)
                    {
                        JOptionPane.showMessageDialog(InchiriereFilmeDorite.this,"Filmul " + cf.Filme.get(i) + " a fost adaugat cu succes");
                        filme_preferinta.add(l[i].getText());
                        String vechi = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + cf.Filme.get(i).nrCopii;
                        int stoc = Integer.parseInt(cf.Filme.get(i).nrCopii);
                        stoc--;
                        
                        String nou = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + String.valueOf(stoc);
                        try{
                            cf.actualizare_stoc("Filme.txt", vechi, nou);
                       }catch(IOException ex){;}
                        if(stoc == 0)
                        bInchiriaza[i].setEnabled(false);
                        bSpreCasiera.setEnabled(true);
                        if(cf.Filme.get(i).tipFilm.matches("DVD"))
                        cost+= 14;
                        else cost+= 7;
                    }
                    else{
                        JOptionPane.showMessageDialog(InchiriereFilmeDorite.this,"Nu poti inchiria in avans mai mult de 5 filme");
                    }
                }
            }

            for(int i = 0 ; i < dim ; i++){
                if(ev.getSource() == bSterge[i]){
                    filme_preferinta.remove(l[i].getText());
                    String vechi = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + cf.Filme.get(i).nrCopii;
                    int stoc = Integer.parseInt(cf.Filme.get(i).nrCopii);
                    stoc++;
                    
                    String nou = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + String.valueOf(stoc);
                try{
                    cf.actualizare_stoc("Filme.txt", vechi, nou);
               }catch(IOException ex){;}
                    if(filme_preferinta.contains(l[i].getText()) == false)
                    bSterge[i].setEnabled(false);
                    bInchiriaza[i].setEnabled(true);
                    if(filme_preferinta.size() == 0)
                    bSpreCasiera.setEnabled(false);
    
                    if(cf.Filme.get(i).tipFilm.matches("DVD"))
                    cost-= 14;
                        else cost-= 7;
                }
            }
            if(ev.getSource() == bVeziCos){
                InterfataCosFilme icf = new InterfataCosFilme();
                icf.setVisible(true);
                icf.l1.setText("Cosul tau contine urmatoarele filme: " + filme_preferinta);
                
            }
            if(ev.getSource() == bSpreCasiera){
                vif.setVisible(false);
                setVisible(false);
                i = new Imprumut(filme_preferinta,data.getTime(),String.valueOf(cost));
                
                InterfataPlataCasiera ipc = new InterfataPlataCasiera(i);
                ipc.setVisible(true);
                ipc.l1.setText("Va trebui sa platesti in avans pentru urmatoarele filme " + filme_preferinta);
                //vif.setVisible(false);
            }
        }
    }
}
