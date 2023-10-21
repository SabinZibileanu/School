//Copyright Popa Alexandru
import java.io.*;
class ValideazaParola {
    public boolean valideaza_login(String nume_fis,String identificator){
        int ok = 0;
        File fisier = new File(nume_fis);
        try (BufferedReader br = new BufferedReader(new FileReader(fisier))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                if(line.matches(identificator))
                ok = 1;
            }
       
} catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
       if(ok == 1)
        return true;
    else
        return false;
    }
}

