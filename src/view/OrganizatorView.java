package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrganizatorView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton createEventButton;
    private JButton viewEventsButton;
    private JButton logoutButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    OrganizatorView frame = new OrganizatorView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public OrganizatorView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166)); 
        setTitle("Karta");

        // Prikaz slike
        ImageIcon imageIcon = new ImageIcon("resources/logo.png"); // Postavi putanju do svoje slike
        Image image = imageIcon.getImage(); // Transformacija slike
        Image scaledImage = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH); // Promijeni veličinu slike
        imageIcon = new ImageIcon(scaledImage); // Ponovo kreiraj ImageIcon sa promijenjenom slikom
        
        JLabel label = new JLabel(imageIcon);
        label.setBounds(15, 140, 400, 300);
        contentPane.add(label);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Welcome, Organizator!");
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setFont(new Font("Chilanka", Font.ITALIC, 46));
        lblWelcome.setBounds(300, 12, 600, 95);
        lblWelcome.setForeground(new Color(95, 95, 95)); 
        contentPane.add(lblWelcome);

        // Create Event Button
        createEventButton = new JButton("Create Event");
        createEventButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        createEventButton.setBounds(500, 200, 200, 50);
        createEventButton.setForeground(new Color(51, 51, 51));
        createEventButton.setBackground(Color.decode("#f3f7f8"));
        createEventButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        createEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otvaranje CreateEventView prozora
                CreateEvent createEvent = new CreateEvent();
                createEvent.setVisible(true);
            }
        });
        contentPane.add(createEventButton);

        // View Events Button
        viewEventsButton = new JButton("View Events");
        viewEventsButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        viewEventsButton.setBounds(500, 300, 200, 50);
        viewEventsButton.setForeground(new Color(51, 51, 51));
        viewEventsButton.setBackground(Color.decode("#f3f7f8"));
        viewEventsButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        viewEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otvaranje ViewEventView prozora
                ViewEvent viewEvent = new ViewEvent();
                viewEvent.setVisible(true);
            }
        });
        contentPane.add(viewEventsButton);

        // Logout Button
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        logoutButton.setBounds(500, 400, 200, 50);
        logoutButton.setForeground(new Color(51, 51, 51));
        logoutButton.setBackground(Color.decode("#f3f7f8"));
        logoutButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login loginView = new Login(); // Otvori Login prozor
                loginView.setVisible(true);
                dispose(); // Zatvori trenutni prozor
            }
        });
        contentPane.add(logoutButton);
    }
}
