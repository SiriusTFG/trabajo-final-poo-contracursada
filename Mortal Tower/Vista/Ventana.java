package Vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ventana extends JFrame {

    private JPanel panel;
    private CardLayout cardLayout;
    private boolean isUndecorated = false; // Estado actual del frame

    public Ventana() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        panel = new JPanel(cardLayout);

        add(panel, BorderLayout.CENTER);

        // Key Binding para F11
        agregarKeyBindingF11();
    }

    private void agregarKeyBindingF11() {
        JPanel content = (JPanel) this.getContentPane();

        InputMap inputMap = content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = content.getActionMap();

        KeyStroke f11Key = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0);
        inputMap.put(f11Key, "toggleUndecorated");

        actionMap.put("toggleUndecorated", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleUndecorated();
            }
        });
    }

    private void toggleUndecorated() {
        // Ocultar el frame antes de cambiar undecorated
        setVisible(false);
        dispose(); // Necesario para cambiar undecorated

        isUndecorated = !isUndecorated; // Cambiamos el estado
        setUndecorated(isUndecorated);

        // Restauramos tamaño y posición
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel getPanel() { return panel; }
    public CardLayout getCardLayout() { return cardLayout; }

    
}