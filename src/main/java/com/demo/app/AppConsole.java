package com.demo.app;

import javax.swing.*;
import java.awt.*;

public class AppConsole extends JFrame {
    private JTextArea consoleTextArea;

    public AppConsole() {
        super("Console");
        setPreferredSize(new Dimension(640, 500));
        setLayout(new GridLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Création de la zone de texte pour la console
        consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false); // Pour empêcher l'édition du texte
        JScrollPane scrollPane = new JScrollPane(consoleTextArea);

        // Ajout de la zone de texte à la fenêtre
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public void println(String text) {
        consoleTextArea.append(text + "\n"); // Ajoute du texte à la fin de la console
        consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength()); // Fait défiler jusqu'au bas
    }

    public void printf(String text, Object... args) {
        consoleTextArea.append(String.format(text + "\n", args)); // Ajoute du texte à la fin de la console
        consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength()); // Fait défiler jusqu'au bas
    }
}
