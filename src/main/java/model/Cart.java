package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private ArrayList<Prodotto> listaProdotti;
    
    public Cart(){
        listaProdotti = new ArrayList<Prodotto>();
    }
    
    public boolean isEmpty(){
        return listaProdotti.isEmpty();
    }
    
    public List<Prodotto> getListaProdotti(){
        return listaProdotti;
    }
    
    
    public void rimuoviProdotto(Prodotto p){
        listaProdotti.remove(p);
    }
    
    public void addProdotto(Prodotto prodotto){

        listaProdotti.add(prodotto);
    }


    public boolean cartRead(Prodotto prodotto){
        return listaProdotti.contains(prodotto);
    }



    public double getTotalPrice(){
        double total = 0;
        
        for(Prodotto prodotto : listaProdotti){
            total += prodotto.getPrezzo();
        }
        return total;
    }
}
