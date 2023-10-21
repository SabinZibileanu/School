//Copyright Popa Alexandru
class Filme implements Comparable<Filme>{
   String numeFilm,genFilm,anAparitie,tipFilm,nrCopii;
    
    Filme(){
        
    }

    Filme(String numeFilm,String genFilm,String anAparitie,String tipFilm,String nrCopii){
        this.numeFilm = numeFilm;
        this.genFilm = genFilm;
        this.anAparitie = anAparitie;
        this.tipFilm = tipFilm;
        this.nrCopii = nrCopii;
    }

    public String getNume(){
        return numeFilm;
    }

    public String getGenFilm(){
        return genFilm;
    }

    public String getAnAparitie(){
        return anAparitie;
    }

    public String getTipFilm(){
        return tipFilm;
    }

    public String getNrCopii(){
        return nrCopii;
    }

    public void setNume(String n){
        numeFilm = n;
    }

    public void setGenFilm(String g){
        genFilm = g;
    }

    public void setAnAparitie(String a){
        anAparitie = a;
    }

    public void setTipFilm(String t){
        tipFilm = t;
    }
    
    public void setNrCopii(String n){
        nrCopii = n;
    }

    @Override
    public String toString() {
        return numeFilm + " " + genFilm + " " + anAparitie + " " + tipFilm + " ";
    }
    @Override
    public int compareTo(Filme p1){
        int compara = this.numeFilm.compareTo(p1.numeFilm);
        if(compara < 0) return -1;
        if(compara > 0) return 1;
        return 0;
    }
}

