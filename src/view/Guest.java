package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Guest extends JFrame {
    private static final long serialVersionUID = 1L;

    public Guest() {
        // Inicijalizacija gostujućeg pogleda
        setTitle("Karta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));

        // Logo kao slika
        ImageIcon imageIcon = new ImageIcon("resources/logo.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        JLabel label = new JLabel(imageIcon);
        label.setBounds(15, 140, 300, 300);
        contentPane.add(label);

        // Dugme za pregled događaja
        JButton viewTicketsButton = new JButton("View Tickets");
        viewTicketsButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        viewTicketsButton.setBounds(500, 220, 200, 50);
        viewTicketsButton.setForeground(new Color(51, 51, 51));
        viewTicketsButton.setBackground(Color.decode("#f3f7f8"));
        viewTicketsButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                otvoriPregledDogadjaja();
            }
        });
        contentPane.add(viewTicketsButton);

        // Dugme "Nazad"
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        backButton.setBounds(500, 320, 200, 50);
        backButton.setForeground(new Color(51, 51, 51));
        backButton.setBackground(Color.decode("#f3f7f8"));
        backButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Vratite se na Login ekran
                Login loginView = new Login();
                loginView.setVisible(true);
                dispose(); // Zatvori trenutni prozor
            }
        });
        contentPane.add(backButton);
    }

    private void otvoriPregledDogadjaja() {
      //  ViewTickets viewTickets = new ViewTickets(null); // Null za scenarij gosta
        //viewTickets.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Guest frame = new Guest();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
