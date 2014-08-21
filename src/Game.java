/**
 * Clase principal controladora del juego.
 * Contiene la parte grafica y metodos necesarios de calculo
 * para el camino de los jugadores y suma de puntos.
 *
 * Creado por Pablo Garcia-Nieto Rodriguez (H2)
 */
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
public class Game extends JPanel{
    //Creamos el Panel extendiendo a JPanel (Swing)
    private static int[][] tablero;
    private static JFrame frame;
    private static DefaultTableModel tableModel;
    private static DefaultTableModel pointsModel;
    static Lista lista;
    private static int turno=1;
    static String winner="";
    static int[] remaining = new int[2];
    public Game() throws IOException {
        //Generamos la tabla de puntuacion
        JPanel p = new JPanel();
        pointsModel =  new DefaultTableModel()
        {
            @Override

            public boolean isCellEditable(int row, int col) {
                return false;

            }
        };
        final JTable  pointsTable = new JTable(pointsModel);
        pointsModel.addColumn("Jugador");
        pointsModel.addColumn("Puntos");
        pointsModel.addRow(new Object[]{"Jugador 1", ""});
        pointsModel.addRow(new Object[]{"Jugador 2", ""});
        pointsTable.setPreferredScrollableViewportSize(new Dimension(500, 500));
        pointsTable.setFillsViewportHeight(true);
        pointsTable.setRowHeight(100);
        pointsTable.getTableHeader().setReorderingAllowed(false);
        p.add(pointsTable);
        add(p);
        //Generamos la tabla del juego
        tableModel = new DefaultTableModel()
        {
            @Override
            public Class getColumnClass(int column)
            {
                //Nos aseguramos que la respuesta a las celdas sea siempre un tipo ImageIcon
                return ImageIcon.class;
            }
            public boolean isCellEditable(int row, int col) {
                return false;

            }
        };
        //A??adimos las columnas
        tableModel.addColumn("1");
        tableModel.addColumn("2");
        tableModel.addColumn("3");
        tableModel.addColumn("4");
        tableModel.addColumn("5");
        tableModel.addColumn("6");
        tableModel.addColumn("7");

        // Ajustes de tama??os y colores
        final JTable  table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(700, 600));
        table.setFillsViewportHeight(true);
        table.setRowHeight(100);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(Color.white);
        table.setTableHeader(null);
        //Interceptamos el doble click en una celda
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    ponerFicha(row, column);

                }
            }
        });
        //A??adimos zona de scroll si fuese necesario
        JScrollPane panelConScroll = new JScrollPane(table);

        add(panelConScroll);
    }

    private static void crearUI() {
        //Creamos la pantalla inicial con su t?tulo e icono
        frame = new JFrame("Juego 4EnRaya");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Configuramos el contenido
        Game panel;
        try{
            panel = new Game();
            panel.setOpaque(true);
            frame.setContentPane(panel);

            //Metodos a ser llamados para mostrar la ventana
            frame.pack();
            frame.setVisible(true);
        }catch(IOException e){
            e.printStackTrace();
        }



    }
    private static void rellenar(int[][] tablero){
        for(int x=0;x<tablero.length;x++){
            for(int y=0;y<tablero[x].length;y++){
                tablero[x][y]=0;
            }
            tableModel.insertRow(x, new Object[]{});


        }
    }
    private static void updateRows(){
        for(int x=0;x<tablero.length;x++){
            for(int y=0;y<tablero[x].length;y++){

                tableModel.setValueAt(new ImageIcon(Game.class.getResource("icons/t0.png")), x, y);
            }
        }

    }

    private static void ponerFicha(int x, int y){
        lista.inicio();
        while(lista.recuperar().num!=y+1){
            lista.siguiente();
        }
        //Apilamos la ficha
        lista.recuperar().pila.apilar(turno);
        //Dibujamos segun talla
        tableModel.setValueAt(new ImageIcon(Game.class.getResource("icons/t"+turno+".png")), 6-lista.recuperar().pila.talla(), y);
        //Comprobamos si hay ganador
        //Cambiamos el turno
        //Apuntamos la jugada
        tablero[6-lista.recuperar().pila.talla()][y]=turno;
        if(hayGanador()){
            JOptionPane.showMessageDialog(frame, "¡Fin del juego! Ganador: "+winner);
        }
        changeTurn();

    }
    private static boolean es4EnRaya(int x, int y, int sx, int sy, int n, int turno){
        try{
            if (tablero[x][y]!=turno){ return false; }
        }catch(Exception IndexOutOfBoundsException){
           return false;
        }
        if(n<3){
            //JOptionPane.showMessageDialog(frame, "Jugada ganadora en "+sx+" "+sy);
           return es4EnRaya(x+(1*sx), y+(1*sy), sx, sy, n+1, turno);

        }else{
            winner="Jugador "+turno;
            return true;
        }




    }
    private static boolean hayGanador(){
        boolean t = false;
        for(int x=0;x<tablero.length;x++){
            for(int y=0;y<tablero[x].length;y++){
                if(tablero[x][y]!=0){
                    int n=0;
                    boolean b = es4EnRaya(x,y+1, 0, 1, 1, tablero[x][y]) || es4EnRaya(x+1,y, 1, 0, 1, tablero[x][y]) || es4EnRaya(x+1,y+1, 1, 1, 1, tablero[x][y]) || es4EnRaya(x-1,y+1, -1, 1, 1, tablero[x][y]) ;
                    t = t || b;

                }
            }
            }
       return t;
    }
    private static void changeTurn(){
        turno = turno == 1 ? 2:1;
        setNames();
        pointsModel.setValueAt("<html><b>Jugador "+turno+"</b></html>", turno-1, 0);
    }
    private static int[] randomPosition(){

        int[] pos =  {0 + (int)(Math.random()*4), 0 + (int)(Math.random()*4)};
        return pos;
    }




    public static void setNames(){
        pointsModel.setValueAt("<html>Jugador 1</html>", 0, 0);
        pointsModel.setValueAt("<html>Jugador 2</html>", 1, 0);
    }
    /* Funcion inicial donde cargamos el hilo del UI e iniciamos jugadores */

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Generamos el tablero de 5x5
                tablero = new int[6][7];
                lista = new Lista();
                for(int i=1;i<=7;i++){
                    lista.insertar(i);
                }
                // Generamos la interfaz gr?fica
                crearUI();
                // Rellenamos aleatoriamente el tablero
                rellenar(tablero);
                // Mostramos en la tabla gr?fica el contenido
                remaining[0]=0;
                remaining[1]=0;
                updateRows();
                //Generamos los jugadores y sus posiciones iniciales
                int[] pos1={0,0};
                int[] pos2={0,0};
                // Nos aseguramos que sean diferentes
                while(pos1[0]==pos2[0] && pos1[1]==pos1[1]){
                    pos1=randomPosition();
                    pos2=randomPosition();
                }

                //Marcamos el turno
                pointsModel.setValueAt("<html><b>Jugador 1</b></html>", 0, 0);
                //Mostramos gráficamente el juego
                updateRows();


            }
        });
    }
}