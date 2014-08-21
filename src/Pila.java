/**
 * Created by pablo on 04/06/14.
 */
public class Pila {
    private NodoFila cima;
    private int talla;
    public Pila(){
        cima=null;
        talla=0;

    }
    public void apilar (int x) {
        cima = new NodoFila(x,cima);
        talla++;
    }
    public int desapilar () {
        int x = cima.num;
        cima = cima.siguiente;
        talla--;
        return x;
    }

    public int cima () {
        return cima.num;
    }
    public boolean vacia () {
        return cima==null;
        // o return talla==0;
    }
    public int talla () {
        return talla;
    }
}
