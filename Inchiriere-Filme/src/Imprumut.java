import java.util.ArrayList;  
import java.util.Date;  

class Imprumut {
    ArrayList<String> nume_filme = new ArrayList<>();
    Date data;
    String cost;
    Imprumut(ArrayList<String> nume_filme,Date data,String cost){
        this.nume_filme = nume_filme;
        this.data = data;
        this.cost = cost;
    }

    Imprumut(){

    }

   
    public ArrayList<String> getNumeFilme(){
        return nume_filme;
    }

    public Date getData(){
        return data;
    }

    public String getCost(){
        return cost;
    }

   

    public void setNumeFilme(ArrayList<String>nume){
        nume_filme = nume;
    }

    public void setData(Date d){
        data = d;
    }

    public void setCost(String c){
        cost = c;
    }

    @Override
    
    public String toString(){
        return nume_filme+ " " + data + " " + cost;
    }
}
