package com.sovaowlsova;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Game implements KeyListener {
    public static final int MAX_FIELD_SIZE = 1000;
    public static final Character ALIVE_CELL = 'o';
    public static final Character DEAD_CELL = ' ';
    private static int speed = 500;
    private static boolean play = true;

    public static void run(Field field) {
        try {
            JFrame frame = new JFrame("Game Of Life");
            frame.setSize(800, 800);
            frame.getContentPane().setBackground(Color.BLACK);

            JLabel label = new JLabel("", SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setText(field.toHTML());

            frame.add(label);
            frame.setVisible(true);

            while (play) {
                field.tick();
                label.setText(field.toHTML());
                Thread.sleep(speed);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Pressed");
        if (e.getKeyChar() == 'q') {
            play = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
