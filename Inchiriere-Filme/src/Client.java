//Copyright Popa Alexandru
class Client {
    String nume,prenume,CNP,nrCNP,serie,nrTel;
    protected int id;
    String validatorNrTel = "07[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
    String validatorSerie = "[1-6][0-9][0-9][0-9][0-9][0-9]";
    
    Client(String nume,String prenume,String serie,String nrCNP,String CNP,String nrTel){
        ++id;
        this.nume = nume;
        this.prenume = prenume;
        this.serie = serie;
        this.nrCNP = nrCNP;
        this.CNP = CNP;
        this.nrTel = nrTel;
    }

    Client(){
        
    }

    public String getNume(){
        return nume;
    }

    public String getPrenume(){
        return prenume;
    }

    public String getSerie(){
        return serie;
    }

    public String getCNP(){
        return CNP;
    }

    public String getnrCNP(){
        return nrCNP;
    }

    public String getNrTel(){
        return nrTel;
    }

    

    public boolean valideazaCNP(String CNP){
        int index,verif;
        if(CNP.length() != 13)
            return false;
        else
            {
                
                int suma = 0;
                int valid[] = {2,7,9,1,4,6,3,5,8,2,7,9};
                for(int i = 0 ; i < 13 ; i++)
                {   char c = CNP.charAt(i);
                    index = Character.getNumericValue(c);
                    if(i < 12)
                        suma = suma + (index * valid[i]);
                }
                suma = suma % 11;
                char ultim = CNP.charAt(12);
                verif = Character.getNumericValue(ultim);
                if(suma < 10 && suma == verif && suma != 0)
                    return true;
                else
                    return false;
            }
    }
    
    public boolean valideazaSerie(String serie){
        if(serie.matches("KZ") == false)
            return false;
        else
            return true;
    }

    public boolean valideazaNrTel(String nrTel){
        if(nrTel.matches(validatorNrTel) == false)
            return false;
        else
            return true;
    }

    public boolean valideazaNrCNP(String nrCNP){
        if(nrCNP.matches(validatorSerie) == false)
            return false;
        else
            return true;
    }
    
}
