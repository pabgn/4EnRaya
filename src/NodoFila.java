/**
 * Created by pablo on 04/06/14.
 */
public class NodoFila{
    int num;
    NodoFila siguiente;
    NodoFila (int n){
        num=n;
        siguiente=null;
    }

    NodoFila (int n, NodoFila s){
        num=n;
        siguiente=s;
    }
}


