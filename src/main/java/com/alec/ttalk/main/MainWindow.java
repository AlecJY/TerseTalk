package com.alec.ttalk.main;

import com.alec.ttalk.about.AboutWindow;
import com.alec.ttalk.common.FrameTitleBar;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by Alec on 2015/6/6.
 */
public class MainWindow extends JFrame{
    private JMenuBar menuBar = new JMenuBar();
    private ResourceBundle lang = ResourceBundle.getBundle("lang/tTalk"); // load lang

    public MainWindow() {
        setTitle("TerseTalk");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        menu();

        JPanel northPane = new JPanel(new GridLayout(2, 1));
        northPane.add(new FrameTitleBar(this, 2));
        northPane.add(menuBar);

        add(northPane, BorderLayout.NORTH);
        pack();
        setVisible(true);
    }

    private void menu() {
        JMenu file = new JMenu(lang.getString("menu.file"));
        JMenuItem preference = new JMenuItem(lang.getString("menu.file.preference"));
        JMenuItem exit = new JMenuItem(lang.getString("menu.file.exit"));
        file.add(preference);
        file.add(exit);


        exit.addActionListener(e -> System.exit(0));

        JMenu help = new JMenu(lang.getString("menu.help"));
        JMenuItem about = new JMenuItem(lang.getString("menu.help.about"));
        help.add(about);

        about.addActionListener(e -> new AboutWindow());

        menuBar.add(file);
        menuBar.add(help);
    }
}
