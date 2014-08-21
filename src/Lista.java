/**
 * Created by pablo on 05/06/14.
 */
public class Lista {
    private NodoColumna primero, PI, antPI;
    private int talla;

    public Lista () {
        primero = null;
        PI = null;
        antPI = null;
        talla = 0;
    }
    public boolean vacia () { return talla==0; }
    public boolean fin () { return PI==null; }
    public int talla () { return talla; }
    public void inicio () {
        PI = primero;
        antPI = null;
    }
    public void siguiente () {
        antPI = PI;
        PI = PI.siguiente;
    }
    public NodoColumna recuperar () {
        return PI;
    }
    public void insertar (int x) {
        if (PI==primero) {
            primero = new NodoColumna(x,PI);
            antPI = primero;
        } else {
            antPI.siguiente = new NodoColumna(x,PI);
            antPI = antPI.siguiente;
        }
        talla++;
    }



}