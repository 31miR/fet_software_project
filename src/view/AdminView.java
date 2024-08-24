package view;

import javax.swing.*;
import model.Administrator;
import java.awt.*;

public class AdminView extends JFrame {

    private static final long serialVersionUID = 1L;
    private Administrator admin;
    private JPanel contentPane;

    public AdminView(Administrator admin) {
        this.admin = admin;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 800); 
        setResizable(false);
        
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));
        setTitle("Admin View - " + admin.getUsername());

        // Prikaz slike
        ImageIcon imageIcon = new ImageIcon("resources/logo.png"); 
        Image image = imageIcon.getImage(); 
        Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH); 
        imageIcon = new ImageIcon(scaledImage); 
        
        JLabel label = new JLabel(imageIcon);
        label.setBounds(50, 200, 400, 300);
        contentPane.add(label);

        JLabel lblWelcome = new JLabel("Welcome Admin, " + admin.getUsername() + "!");
        lblWelcome.setForeground(new Color(51, 51, 51));
        lblWelcome.setFont(new Font("Chilanka", Font.ITALIC, 46));
        lblWelcome.setBounds(500, 12, 600, 95);
        contentPane.add(lblWelcome);

        // Button-i
        JButton requestButton = new JButton("Request users");
        requestButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        requestButton.setBounds(550, 200, 250, 50);
        requestButton.setForeground(new Color(51, 51, 51));
        requestButton.addActionListener(e -> {
            RequestUsers event = new RequestUsers();
            event.setVisible(true);
            dispose();
        });
        contentPane.add(requestButton); 
        
        JButton eventButton = new JButton("Request events");
        eventButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        eventButton.setBounds(550, 300, 250, 50);
        eventButton.setForeground(new Color(51, 51, 51));
        eventButton.addActionListener(e -> {
            RequestEvents event = new RequestEvents();
            event.setVisible(true);
            dispose();
        });
        contentPane.add(eventButton);
        
        JButton viewButton = new JButton("View events");
        viewButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        viewButton.setBounds(550, 400, 250, 50);
        viewButton.setForeground(new Color(51, 51, 51));
        viewButton.addActionListener(e -> {

            ViewEvent event = new ViewEvent(admin);

            event.setVisible(true);
            dispose();
        });
        contentPane.add(viewButton);
        
        JButton editButton = new JButton("Create Locations");
        editButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        editButton.setBounds(550, 500, 250, 50);  
        editButton.setForeground(new Color(51, 51, 51));
        editButton.addActionListener(e -> {
            CreateLocation event1 = new CreateLocation(admin);
            event1.setVisible(true);
            dispose();
        });
        contentPane.add(editButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Chilanka", Font.PLAIN, 26));  
        logoutButton.setBounds(550, 600, 250, 50);  
        logoutButton.setForeground(new Color(51, 51, 51));
        logoutButton.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
        contentPane.add(logoutButton);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }}
    