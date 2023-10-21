import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.io.*;
import java.io.FileReader;


class OperatiiProprietar extends JFrame {
    JTextField tNume,tGen,tAn,tTip,tNumarCopii;
    JButton bAdauga,bModifica,bSterge,bImportaTabel,bSalveazaModificarile;
    JTable tabel_inventar;
    JFrame f;

    OperatiiProprietar(){
        Object[] coloane = {"Nume","Gen","An","Tip","Numar copii film"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(coloane);
        
        f = new JFrame();

        tabel_inventar = new JTable();
        tabel_inventar.setBackground(Color.LIGHT_GRAY);
        tabel_inventar.setForeground(Color.black);
        Font font = new Font("",1,22);
        tabel_inventar.setFont(font);
        tabel_inventar.setRowHeight(30);
        tabel_inventar.setModel(model);
        tNume = new JTextField();
        tGen = new JTextField();
        tAn = new JTextField();
        tTip = new JTextField();
        tNumarCopii = new JTextField();
        
        bAdauga = new JButton("Adauga");
        bModifica = new JButton("Modifica");
        bSterge = new JButton("Sterge");
        bImportaTabel = new JButton("Importa date");
        bSalveazaModificarile = new JButton("Salveaza modificarile");
        
        tNume.setBounds(20, 220, 100, 25);
        tGen.setBounds(20, 250, 100, 25);
        tAn.setBounds(20, 280, 100, 25);
        tTip.setBounds(20, 310, 100, 25);
        tNumarCopii.setBounds(20, 340, 100, 25);

        
        bAdauga.setBounds(150, 220, 107, 25);
        bModifica.setBounds(150, 265, 107, 25);
        bSterge.setBounds(150, 310, 107, 25);
        bImportaTabel.setBounds(150,355,108,25);
        bSalveazaModificarile.setBounds(150,400,107,25);

        JScrollPane pane = new JScrollPane(tabel_inventar);
        pane.setBounds(0, 0, 880, 200);
        f.setLayout(null);
        f.add(pane);

        f.add(tNume);f.add(tGen);f.add(tAn);f.add(tTip);f.add(tNumarCopii);
        f.add(bAdauga);f.add(bModifica);f.add(bSterge);f.add(bImportaTabel);f.add(bSalveazaModificarile);
        
        Object[] rand = new Object[5];

        bAdauga.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                rand[0] = tNume.getText();
                rand[1] = tGen.getText();
                rand[2] = tAn.getText();
                rand[3] = tTip.getText();
                rand[4] = tNumarCopii.getText();
                model.addRow(rand);
            }
        });

        bSterge.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                int i = tabel_inventar.getSelectedRow();
                if(i >= 0){
                    model.removeRow(i);
                }
                else{
                    System.out.print("Eroare");
                }
            }
        });
        tabel_inventar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tabel_inventar.getSelectedRow();
                tNume.setText(model.getValueAt(i,0).toString());
                tGen.setText(model.getValueAt(i,1).toString());
                tAn.setText(model.getValueAt(i,2).toString());
                tTip.setText(model.getValueAt(i,3).toString());
                tNumarCopii.setText(model.getValueAt(i,4).toString());

            }
        });

        bModifica.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int i = tabel_inventar.getSelectedRow();
                if(i >= 0)
                {
                    model.setValueAt(tNume.getText(),i,0);
                    model.setValueAt(tGen.getText(),i,1);
                    model.setValueAt(tAn.getText(),i,2);
                    model.setValueAt(tTip.getText(),i,3);
                    model.setValueAt(tNumarCopii.getText(),i,4);
                }
                else{
                    System.out.println("Update Error");
                }
            }
        });

        
        bImportaTabel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String nume_fisier = "Filme.txt";
                File fisier = new File(nume_fisier);
                try{
                    FileReader fr = new FileReader(fisier);
                    BufferedReader br = new BufferedReader(fr);
                    DefaultTableModel model = (DefaultTableModel)tabel_inventar.getModel();
                    Object[] lines = br.lines().toArray();
            
            for(int i = 0; i < lines.length; i++){
                String[] row = lines[i].toString().split("/");
                model.addRow(row);
            }
                }catch(FileNotFoundException ex){;}
            }
        });

        bSalveazaModificarile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String nume_fisier = "Filme.txt";
                File fisier = new File(nume_fisier);
                try{
                    FileWriter fw = new FileWriter(fisier);
                    BufferedWriter bw = new BufferedWriter(fw);

                    for(int i = 0 ; i < tabel_inventar.getRowCount() ; i++){//linii
                        for(int j = 0; j < tabel_inventar.getColumnCount(); j++){//coloane
                            if(j != 4)
                            bw.write(tabel_inventar.getValueAt(i, j).toString()+"/");
                            else
                            bw.write(tabel_inventar.getValueAt(i, j).toString());
                        }
                        bw.newLine();
                    }
                    
                    bw.close();
                    fw.close();
                }catch(IOException ex){;}
            }
        });

        f.setSize(900,550);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
    }
   
    
}
