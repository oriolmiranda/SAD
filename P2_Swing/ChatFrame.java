import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ChatFrame extends JFrame implements ActionListener {

  JButton sendButton;
  JTextField textField;

  ChatFrame(){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(500, 500);
    this.setLayout(new BorderLayout());
    this.setTitle("Chat");

    textField = new JTextField();
    textField.setPreferredSize(new Dimension(405,50));

    JLabel title = new JLabel("Chat");

    JPanel header = new JPanel();
    header.setPreferredSize(new Dimension(500,50));
    header.setLayout(new BorderLayout());
    header.add(title,BorderLayout.CENTER);
    
    JPanel convers = new JPanel();
    convers.setBackground(new Color(204,236,239));
    convers.setPreferredSize(new Dimension(500,300));

    JPanel text = new JPanel();
    text.setPreferredSize(new Dimension(500,50));
    text.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
 
    sendButton = new JButton("Enviar");
    sendButton.addActionListener(this);
    sendButton.setPreferredSize(new Dimension(80,50));

    text.add(textField);
    text.add(sendButton);
    

    this.add(header, BorderLayout.NORTH);
    this.add(convers, BorderLayout.CENTER);
    this.add(text, BorderLayout.SOUTH);
    
    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == sendButton){
      System.out.println(textField.getText());
      textField.setText("");
    }    
  }
}