package view;

import javax.swing.*;

import view.RequestUsers;


import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.KorisnikDAO;
import model.OrganizatorDAO;
import model.AdministratorDAO;
import model.Korisnik;
import view.RequestEvents;

import javax.swing.border.LineBorder;


public class AdminView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel label;
    private JPanel contentPane;

    private KorisnikDAO userController;
    private AdministratorDAO adminController;
    private OrganizatorDAO orgController;
  
    private JButton requestButton;
    private JButton eventButton;
    private JButton editButton;
    private JButton ViewButton;
    private JButton LogoutButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AdminView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 800); 
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
        label.setBounds(50, 200, 400, 300);
        contentPane.add(label);
        

        JLabel lblNewLabel = new JLabel("Welcome Admin!");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Chilanka", Font.ITALIC, 46));
        lblNewLabel.setBounds(500, 12, 600, 95);
        lblNewLabel.setForeground(new Color(51, 51, 51));
        contentPane.add(lblNewLabel);

  


        // Button-i
        
        requestButton = new JButton("Request users");
        requestButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        requestButton.setBounds(550, 200, 250, 50);
        requestButton.setForeground(new Color(51, 51, 51));
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestUsers event = new RequestUsers();
                event.setVisible(true);
                dispose();
            }
        });
        
        
        
        contentPane.add(requestButton); 
        
        eventButton = new JButton("Request events");
        eventButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        eventButton.setBounds(550, 300, 250, 50);
        eventButton.setForeground(new Color(51, 51, 51));
        eventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestEvents event = new RequestEvents();
                event.setVisible(true);
                dispose();
            }
        });
        
        
        
        contentPane.add(eventButton);
        
        ViewButton = new JButton("View events");
        ViewButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        ViewButton.setBounds(550, 400, 250, 50);
        ViewButton.setForeground(new Color(51, 51, 51));
        
        ViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewEvent event = new ViewEvent();
                event.setVisible(true);
                dispose();
            }
        });
        
        
        contentPane.add(ViewButton);
        
        editButton = new JButton("Create Locations");
        editButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        editButton.setBounds(550, 500, 250, 50);  
        editButton.setForeground(new Color(51, 51, 51));
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateLocation event1 = new CreateLocation();
                event1.setVisible(true);
                dispose();
            }
        });

        contentPane.add(editButton);

        LogoutButton = new JButton("Logout");
        LogoutButton.setFont(new Font("Chilanka", Font.PLAIN, 26));  
        LogoutButton.setBounds(550, 600, 250, 50);  
        LogoutButton.setForeground(new Color(51, 51, 51));
        
        LogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });
        
    
        contentPane.add(LogoutButton);
        
        
        
    }
}
