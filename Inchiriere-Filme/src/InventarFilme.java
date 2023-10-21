import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class InventarFilme {
    
    ArrayList<Filme> Filme = new ArrayList<Filme>();
    public void citeste(String numeFis){
        try(BufferedReader br = new BufferedReader(new FileReader(numeFis))) {
            String linie;
            while ((linie = br.readLine()) != null){
            Filme p = new Filme();

            StringTokenizer cnt = new StringTokenizer(linie,"/");

            while(cnt.hasMoreTokens()){
               p.setNume(cnt.nextToken());
               p.setGenFilm(cnt.nextToken());
               p.setAnAparitie(cnt.nextToken());
               p.setTipFilm(cnt.nextToken());
               p.setNrCopii(cnt.nextToken());
            }
            Filme.add(p);
        }
    }catch(IOException e){System.err.println(e.getMessage());}
    Collections.sort(Filme);
}

    public void actualizare_stoc(String nume_fis,String vechi,String nou) throws IOException{
        Scanner sc = new Scanner(new File(nume_fis));
        StringBuffer buffer = new StringBuffer();

        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine()+System.lineSeparator());
        }

        String fileContents = buffer.toString();
        sc.close();
        fileContents = fileContents.replaceAll(vechi, nou);
        FileWriter writer = new FileWriter(nume_fis);
        writer.append(fileContents);
        writer.flush();

    }
}
