import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



class VizualizareInchiriereFilme extends JFrame {
    JButton bVeziFilme,bReset,bExit;
    JTextField t[];
    JLabel l[];
    String[]date = {"Nume film(daca nu cunosti,introdu genul sau anul aparitiei)","Anul aparitiei"};
    String[]gen = {"Actiune","Comedie","Drama","Groaza","SF","Thriller","Dragoste"};
    JComboBox cbGEN = new JComboBox(gen);
    

    VizualizareInchiriereFilme(){
        super("Meniu inchiriere");
        Panel p1 = new Panel(); 
        p1.setLayout(new GridLayout(3,2));
        t=new JTextField[3];
        l=new JLabel[3];

        for(int i = 0 ; i < 3  ; i++){
            t[i] = new JTextField(15);
            if(i != 2)
                {
                    l[i] = new JLabel(date[i] + " :");
                    p1.add(l[i]);
                    p1.add(t[i]);
                }
            if(i == 2){
                l[2] = new JLabel("Genul filmului :");
                p1.add(l[2]);
                p1.add(cbGEN);
                p1.setSize(400,500);
            }
            
        }

        bVeziFilme = new JButton("Vezi filmele");
        bReset = new JButton("Reseteaza informatiile");
        bExit = new JButton("Inapoi acasa");
        AscultatorInchiriere abVeziFilme = new AscultatorInchiriere();
        bVeziFilme.addActionListener(abVeziFilme);
        bReset.addActionListener(abVeziFilme);
        bExit.addActionListener(abVeziFilme);
        Panel p2 = new Panel();
        p2.add(bVeziFilme);
        p2.add(bReset);
        p2.add(bExit);

        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent
        ev){System.exit(0);}});
    }

    class AscultatorInchiriere implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            InventarFilme cf = new InventarFilme();
            cf.citeste("Filme.txt");
            int dim = cf.Filme.size();

            if(ev.getSource() == bVeziFilme){
                InchiriereFilmeDorite ifd = new InchiriereFilmeDorite();

                for(int i = 0 ; i < dim ; i++){
                    if(t[0].getText().equals("") == true && t[1].getText().equals("") == true)
                    {
                        if(cf.Filme.get(i).genFilm.contains(cbGEN.getSelectedItem().toString()))
                    {   
                        ifd.l[i].setText(cf.Filme.get(i).numeFilm);
                        ifd.bInchiriaza[i].setVisible(true);
                        ifd.bSterge[i].setVisible(true);
                        ifd.setVisible(true);
                        ifd.l[i].setVisible(true);
                        setVisible(false);
                    }
                }
                if(t[0].getText().equals("") == false && t[1].getText().equals("") == true){
                    if(cf.Filme.get(i).numeFilm.contains(t[0].getText())){
                        ifd.l[i].setText(cf.Filme.get(i).numeFilm);
                        ifd.bInchiriaza[i].setVisible(true);
                        ifd.bSterge[i].setVisible(true);
                        ifd.setVisible(true);
                        ifd.l[i].setVisible(true);
                        setVisible(false);
                    }
                }
                if(t[0].getText().equals("") == true && t[1].getText().equals("") == false){
                    if(cf.Filme.get(i).anAparitie.contains(t[1].getText())){
                        ifd.l[i].setText(cf.Filme.get(i).numeFilm);
                        ifd.bInchiriaza[i].setVisible(true);
                        ifd.bSterge[i].setVisible(true);
                        ifd.setVisible(true);
                        ifd.l[i].setVisible(true);
                        setVisible(false);
                    }
                }

                }
                
            }
            if(ev.getSource() == bReset)
                {
                    t[0].setText("");
                    t[1].setText("");
                }
            if(ev.getSource() == bExit){
                InterfataInceput ii = new InterfataInceput();
                ii.setVisible(true);
                setVisible(false);
            }
        }
    }
}
