package cft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class NewSessionWindow extends JDialog {

    private JTextField portField;
    private JTextField nicknameField;
    private final ClientConnection connection;
    private static final Logger logger = LoggerFactory.getLogger(NewSessionWindow.class);


    public NewSessionWindow(ClientConnection connection) {
        super(new Frame(), "Log in", true);
        this.connection = connection;

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);


        contentPane.add(createConnectButton(layout));
        contentPane.add(createPortAndNicknameTextField(layout));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connection.onDisconnected();
                dispose();
            }
        });

        setPreferredSize(new Dimension(400, 200));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public void showWindow() {
        this.setVisible(true);
    }

    private JPanel createPortAndNicknameTextField(GridBagLayout layout) {
        JPanel serverPanel = new JPanel();
        serverPanel.setLayout(new GridBagLayout());

        JLabel serverLabel = new JLabel("Server address:");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 10);
        serverPanel.add(serverLabel, gbc);

        this.portField = new JTextField(10);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        serverPanel.add(this.portField, gbc);

        JLabel nicknameLabel = new JLabel("Nickname:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 10);
        serverPanel.add(nicknameLabel, gbc);

        this.nicknameField = new JTextField(10);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        serverPanel.add(this.nicknameField, gbc);

        serverPanel.setPreferredSize(new Dimension(800, 80));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(serverPanel, gbc);

        return serverPanel;
    }

    private JButton createConnectButton(GridBagLayout layout) {
        JButton newGameButton = new JButton("Connect");
        newGameButton.setPreferredSize(new Dimension(100, 25));

        newGameButton.addActionListener(new ConnectButtonListener());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(newGameButton, gbc);

        return newGameButton;
    }

    private String getNicknameFromField() {
        return nicknameField.getText();
    }

    public Integer getPort() {
        int port = 0;
        try {
            port = Integer.parseInt(portField.getText());
        } catch (NumberFormatException e) {
            logger.error("Wrong port");
        }
        return port;
    }


    private class ConnectButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nickname = getNicknameFromField();
            if ((!Objects.equals(nickname, "")) && (connection.tryToConnect(getPort(), nickname))) {
                logger.info("Client {} connected", nickname);
                connection.onConnected(nickname);
                dispose();
            }
        }
    }

}


