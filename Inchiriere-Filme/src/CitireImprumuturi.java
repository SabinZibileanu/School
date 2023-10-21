//Copyright Popa Alexandru
import java.io.*;
import java.util.*;
import java.io.IOException;


class CitireImprumuturi {
    
    int ok = 0;
    String aux = "";
    public String citeste(String numeFis,String identificator){
        try(BufferedReader br = new BufferedReader(new FileReader(numeFis))) {
            String linie;
            while ((linie = br.readLine()) != null){
            StringTokenizer cnt = new StringTokenizer(linie,"/");
            while(cnt.hasMoreTokens()){
                if(cnt.nextToken().matches(identificator)){
                aux = linie;
                return aux;
            }
                
            }
        }
    }catch(IOException e){System.err.println(e.getMessage());}
    return aux;
}
}
