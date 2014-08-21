/**
 * Created by pablo on 04/06/14.
 */
public class NodoColumna {
    int num;
    NodoColumna siguiente;
    Pila pila;
    NodoColumna (int n){
        num=n;
        siguiente=null;
        pila = new Pila();
    }

    NodoColumna (int n, NodoColumna s){
        num=n;
        siguiente=s;
        pila = new Pila();
    }
}
