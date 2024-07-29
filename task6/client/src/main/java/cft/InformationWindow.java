package cft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InformationWindow extends JDialog {

    public InformationWindow(String information) {

        super(new Frame(), "Information window", true);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setPreferredSize(new Dimension(400, 200));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        showWrongNicknameWindow(information);

    }

    void showWrongNicknameWindow(String information) {

        JLabel infoLabel = new JLabel(information);
        JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> dispose());

        JPanel panel = new JPanel();
        panel.add(infoLabel);
        panel.add(okButton);

        add(panel);

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void showChat() {
        setVisible(true);
    }

}
