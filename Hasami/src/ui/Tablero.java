package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
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

	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] boardSquares = new JButton[9][9];
    private JPanel board;
    private final JLabel message = new JLabel(
            "Que gane el mejor!");
    private static final String COLS = "ABCDEFGHI";

    Tablero() {
        initializeGui();
    }

    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(30, 30, 30, 30));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("Nueva partida")); // TODO - add functionality!
        tools.add(new JButton("Guardar")); // TODO - add functionality!
        //tools.add(new JButton("Restore")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(new JButton("Salir")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(message);

        //gui.add(new JLabel("?"), BorderLayout.LINE_START);

        board = new JPanel(new GridLayout(0, 10));
        board.setBorder((Border) new LineBorder(Color.BLACK));
        gui.add(board);

        // create the board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < boardSquares.length; ii++) {
            for (int jj = 0; jj < boardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = null;
                if(ii==0 ||ii==1){
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
                	JButton pieceToMove = null;
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton button = (JButton)e.getSource();
						if(pieceToMove == null){
							pieceToMove = button;
						}else{
							button.setIcon(pieceToMove.getIcon());
							pieceToMove = null;
						}
					}
                	
                });
                boardSquares[jj][ii] = b;
            }
        }

        //fill the chess board
        board.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 9; ii++) {
            board.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
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

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
