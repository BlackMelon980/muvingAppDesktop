package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Review {
    /*le Property permettono di avere una notifica automatica quando una variabile viene modificata.
    Questo permette di tenere sincronizzato ció che é visualizzato con i dati */

    private StringProperty luogo;
    private StringProperty struttura;
    private StringProperty autore;
    private StringProperty titolo;
    private StringProperty recensione;
    private StringProperty data;

    public Review(String luogo, String struttura, String autore, String titolo,String recensione, String data){
        this.luogo = new SimpleStringProperty(luogo);
        this.struttura = new SimpleStringProperty(struttura);
        this.autore = new SimpleStringProperty(autore);
        this.titolo = new SimpleStringProperty(titolo);
        this.recensione = new SimpleStringProperty(recensione);
        this.data = new SimpleStringProperty(data);
    }

    public String getLuogo() {
        return luogo.get();
    }

    public StringProperty luogoProperty() {
        return luogo;
    }

    public String getStruttura() {
        return struttura.get();
    }

    public StringProperty strutturaProperty() {
        return struttura;
    }

    public String getAutore() {
        return autore.get();
    }

    public StringProperty autoreProperty() {
        return autore;
    }

    public String getData() {
        return data.get();
    }

    public StringProperty dataProperty() {
        return data;
    }

    public String getRecensione() {
        return recensione.get();
    }

    public String getTitolo() {
        return titolo.get();
    }

    public StringProperty titoloProperty() {
        return titolo;
    }

    public StringProperty recensioneProperty() {
        return recensione;
    }
}
