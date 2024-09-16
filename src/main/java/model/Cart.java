package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private ArrayList<Prodotto> listaProdotti;
    private ArrayList<Integer> quantitaProdotti;

    public Cart(){
        listaProdotti = new ArrayList<>();
        quantitaProdotti = new ArrayList<>();
    }

    public boolean isEmpty(){
        return listaProdotti.isEmpty();
    }

    public List<Prodotto> getListaProdotti(){
        return listaProdotti;
    }

    public void rimuoviProdotto(Prodotto prodotto){
        int index = listaProdotti.indexOf(prodotto);
        if (index >= 0) {
            listaProdotti.remove(index);
            quantitaProdotti.remove(index);
        }
    }

    public void addProdotto(Prodotto prodotto, int quantita){
        int index = listaProdotti.indexOf(prodotto);
        if (index >= 0) {
            // Se il prodotto è già presente, aumenta la quantità
            quantitaProdotti.set(index, quantitaProdotti.get(index) + quantita);
        } else {
            // Se il prodotto non è presente, lo aggiunge
            listaProdotti.add(prodotto);
            quantitaProdotti.add(quantita);
        }
    }

    public boolean cartRead(Prodotto prodotto){
        return listaProdotti.contains(prodotto);
    }

    public int getQuantita(Prodotto prodotto){
        int index = listaProdotti.indexOf(prodotto);
        if (index >= 0) {
            return quantitaProdotti.get(index);
        }
        return 0;
    }

    public double getTotalPrice(){
        double total = 0;
        for (int i = 0; i < listaProdotti.size(); i++) {
            total += listaProdotti.get(i).getPrezzo() * quantitaProdotti.get(i);
        }
        return total;
    }
}
