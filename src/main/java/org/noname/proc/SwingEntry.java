package org.noname.proc;

import org.noname.proc.data.Input;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class SwingEntry {

    public static void main(String[] args) {
        JFrame frame = new JFrame("HelloWordNet");

        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton button = new JButton();
        button.setText("Press me");

        final JTextArea inputEditor = new JTextArea();
        final JTextArea outputEditor = new JTextArea();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String input = inputEditor.getText();

                WordProcessor.preConfigureWordNet();
                List<String> words = new WordProcessor(input).obtainUniqueWords();

                outputEditor.setText(joinStrings(words));

                panel.revalidate();
                panel.repaint();
            }
        });

        inputEditor.setText(Input.VALUE);
        outputEditor.setEditable(false);

        JScrollPane inputScroll = new JScrollPane();
        JScrollPane oututScroll = new JScrollPane();

        inputScroll.setViewportView(inputEditor);
        oututScroll.setViewportView(outputEditor);

        panel.add(button, BorderLayout.PAGE_START);
        panel.add(inputScroll, BorderLayout.CENTER);
        panel.add(oututScroll, BorderLayout.LINE_END);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static String joinStrings(List<String> strings) {
        final StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string).append("\n");
        }
        return sb.toString();
    }
}
