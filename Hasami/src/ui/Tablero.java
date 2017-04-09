package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Tablero {

	//Variables de la clase
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] boardSquares = new JButton[9][9];
    private JPanel board;
    private final JLabel message = new JLabel(
            "Que gane el mejor!");
    private static final String COLS = "ABCDEFGHI";
    private static JButton pieceToMove = null;

	//Metodo para inicializar la clase
    Tablero() {
        initializeGui();
    }
	
	//*
	// Este metodo inicializa la parte grafica del juego
	//*
    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(30, 30, 30, 30));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("Nueva partida")); // boton nueva partida, por implementar
        tools.add(new JButton("Guardar")); // boton guardar partida, por implementar
        tools.addSeparator();
        tools.add(new JButton("Salir")); //dispose on close, por implementar
        tools.addSeparator();
        tools.add(message);

        board = new JPanel(new GridLayout(0, 10));
        board.setBorder((Border) new LineBorder(Color.BLACK));
        gui.add(board);

        // crear tablero como matriz de botones
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < boardSquares.length; ii++) {
            for (int jj = 0; jj < boardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = null;
                if(ii==0 ||ii==1){ // añadir las piezas
                	icon = new ImageIcon(getClass().getResource("p1.png"));
                }else if(ii==boardSquares.length-1 ||ii==boardSquares.length-2){
                	icon = new ImageIcon(getClass().getResource("p2.png"));
                }else{
                	icon = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));
                }
                Image img = icon.getImage();
            	Image newImage = img.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH);
            	icon = new ImageIcon(newImage);
				b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1) || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                b.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton button = (JButton)e.getSource();
						button.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
						if(pieceToMove == null){
							pieceToMove = button;
						}else{
							//if(esposible)
								
							button.setIcon(pieceToMove.getIcon());
							pieceToMove.setIcon(null);
							pieceToMove = null;
							emptyTheBorder();
							//come la ficha()
						}
					}
                	
                });
                boardSquares[jj][ii] = b;
            }
        }

        //Llena el tablero con fichas
        board.add(new JLabel(""));
        // Llena la fila de arriba
        for (int ii = 0; ii < 9; ii++) {
            board.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // 
        for (int ii = 0; ii < 9; ii++) {//aqui el actionListener(creo)
            for (int jj = 0; jj < 9; jj++) {
                switch (jj) {
                    case 0:
                        board.add(new JLabel("" + (ii + 1),
                                SwingConstants.CENTER));
                    default:
                        board.add(boardSquares[jj][ii]);
                }
            }
        }
    }
    
    private void emptyTheBorder(){
    	for (int i = 0; i < boardSquares.length; i++) {
			for (int j = 0; j < boardSquares[i].length; j++) {
				boardSquares[i][j].setBorder(null);
			}
		}
    }

    public final JComponent getChessBoard() {
        return board;
    }

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                Tablero cb =
                        new Tablero();

                JFrame f = new JFrame("Hasami Shogi");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // 
                // Ajusta los margenes
                f.pack();
                // 
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
