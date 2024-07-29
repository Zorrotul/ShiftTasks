package cft;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ChatField extends JFrame {

    private JTextField messageField;
    private JTextArea chatArea;
    private DefaultListModel<String> participantListModel;
    private final ClientConnection connection;

    public ChatField(ClientConnection connection) {
        this.connection = connection;
        createChatField();
    }

    public void showChat() {
        setVisible(true);
    }

    public void hideChat() {
        setVisible(false);
    }


    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                messageField.setText("");
                connection.sendChatMessage(message);
            }
        }
    }

    public void printChatMessage(@NotNull Message message) {
        chatArea.append(message.toString() + "\n");
        printParticipantList(message.getParticipants());

    }

    public void printParticipantList(List<String> connectedNicks) {
        participantListModel.clear();
        for (String p : connectedNicks) {
            participantListModel.addElement(p);
        }
    }

    public void createChatField() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        messageField = new JTextField(20);
        inputPanel.add(messageField);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        inputPanel.add(sendButton);
        add(inputPanel, BorderLayout.SOUTH);

        chatArea = new JTextArea(20, 40);
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        this.participantListModel = new DefaultListModel<>();
        JList<String> participantList = new JList<>(participantListModel);
        participantList.setPreferredSize(new Dimension(200, 300));
        add(new JScrollPane(participantList), BorderLayout.EAST);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        settingsPanel.setPreferredSize(new Dimension(800, 60));
        add(settingsPanel, BorderLayout.NORTH);

        setSize(960, 540);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connection.onDisconnected();
                dispose();
            }
        });

    }

    public void setNickname(String nickname) {
        setTitle("Your nickname: " + nickname);
    }
}





