import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

class GestionareImprumuturi extends JFrame{
    JLabel l1,l2,l3,l4;
    JButton bReturneaza,bAnuntaPierdere,bInapoiAcasa;
    JTextField t1,t2;
    JPanel p1,p2;
    ArrayList<String> filme_returnate = new ArrayList<String>();
    String cost;
    long perioada;

    GestionareImprumuturi(ArrayList<String>filme_returnate,String cost,long perioada){
        super("Meniu returnari");
        this.filme_returnate = filme_returnate;
        this.cost = cost;
        this.perioada = perioada;
        
        p1 = new JPanel();
        p2 = new JPanel();
        p1.setLayout(new GridLayout(4,2));
        p1.setPreferredSize(new Dimension(950,150));
        bReturneaza = new JButton("Returneaza");
        bAnuntaPierdere = new JButton("Am pierdut filmul");
        bInapoiAcasa = new JButton("Inapoi acasa");
        AscultatorReturneaza aR = new AscultatorReturneaza();
        l1 = new JLabel("Ce film vrei sa returnezi?");
        l2 = new JLabel("Ai pierdut vreun film?Daca da introdu numele,daca nu lasa spatiul gol");
        l3 = new JLabel();
        l4 = new JLabel();
        t1 = new JTextField(30);
        t2 = new JTextField(30);

        p1.add(l1);p1.add(t1);
        p1.add(l2);p1.add(t2);
        p1.add(l3);p1.add(l4);
        p2.add(bReturneaza);
        p2.add(bAnuntaPierdere);
        p2.add(bInapoiAcasa);
        bReturneaza.addActionListener(aR);
        bAnuntaPierdere.addActionListener(aR);
        bInapoiAcasa.addActionListener(aR);

        getContentPane().add(p1,BorderLayout.NORTH);
        getContentPane().add(p2,BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class AscultatorReturneaza implements ActionListener{
       int s = 0;
        public void actionPerformed(ActionEvent ev){
            InventarFilme cf = new InventarFilme();
            cf.citeste("Filme.txt");
            int dim = cf.Filme.size();
            //double taxa = Double.parseDouble(cost);
            
            if(ev.getSource() == bReturneaza){
                double taxa = 0;
                if(filme_returnate.get(0).contains(t1.getText()))
                {   
                    for(int i = 0 ; i < dim ; i++)
                    {
                        if(t1.getText().matches(cf.Filme.get(i).numeFilm))
                        {
                            int stoc = Integer.parseInt(cf.Filme.get(i).nrCopii);
                            stoc++;
                            //if(perioada > 7)
                            //taxa = taxa + 1.5*taxa;
                            //System.out.print(perioada);
                            if(cf.Filme.get(i).tipFilm.matches("DVD")) 
                                taxa = 14;
                            else taxa = 7;
                            if(perioada > 7)
                            taxa = taxa+1.5*taxa;

                            String vechi = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + cf.Filme.get(i).nrCopii;
                            String nou = cf.Filme.get(i).numeFilm + "/" + cf.Filme.get(i).genFilm + "/" + cf.Filme.get(i).anAparitie + "/" + cf.Filme.get(i).tipFilm + "/" + String.valueOf(stoc);

                            try{
                                cf.actualizare_stoc("Filme.txt", vechi, nou);
                                JOptionPane.showMessageDialog(GestionareImprumuturi.this,"Bonul tau: " + taxa + " RON");
                           }catch(IOException ex){;}

                        }
                    }
                }
                
            }
                if(ev.getSource() == bAnuntaPierdere && t2.getText().matches("") == false)
                {   double taxa = 0;
                    if(filme_returnate.get(0).contains(t2.getText())){
                    
                    for(int i = 0 ; i < dim ; i++){
                        if(t2.getText().matches(cf.Filme.get(i).numeFilm)){
                            if(cf.Filme.get(i).tipFilm.matches("DVD")){
                                taxa = 28;
                            }
                            else
                                {
                                    taxa = 14;
                                }
                        }
                    }
                    JOptionPane.showMessageDialog(GestionareImprumuturi.this,"Bonul tau: " + taxa);
                }
            }
            if(ev.getSource() == bInapoiAcasa){
                InterfataInceput ii = new InterfataInceput();
                ii.setVisible(true);
                setVisible(false);
            }
        }
    }
}
