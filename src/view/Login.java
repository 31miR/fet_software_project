package view;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Administrator;
import model.Organizator;
import model.Korisnik;
import model.AdministratorDAO;
import model.OrganizatorDAO;
import model.KorisnikDAO;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton RegisterButton;
    private JButton guestButton;

    private KorisnikDAO userController;
    private AdministratorDAO adminController;
    private OrganizatorDAO orgController;

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));
     
        // Prikaz slike
        ImageIcon imageIcon = new ImageIcon("resources/logo.png"); // Postavi putanju do svoje slike
        Image image = imageIcon.getImage(); // Transformacija slike
        Image scaledImage = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH); // Promijeni veličinu slike
        imageIcon = new ImageIcon(scaledImage); // Ponovo kreiraj ImageIcon sa promijenjenom slikom
        
        JLabel label = new JLabel(imageIcon);
        label.setBounds(50, 100, 400, 300);
        contentPane.add(label);
        ;

        JLabel lblNewLabel = new JLabel("Dobrodosli u Kartu!");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Chilanka", Font.ITALIC, 46));
        lblNewLabel.setBounds(500, 12, 600, 95);
        lblNewLabel.setForeground(new Color(95, 95, 95)); 
        contentPane.add(lblNewLabel);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Chilanka", Font.PLAIN, 26));
        lblUsername.setBounds(450, 166, 193, 52);
        contentPane.add(lblUsername);

        textField = new JTextField();
        textField.setFont(new Font("Chilanka", Font.PLAIN, 26));
        textField.setBounds(650, 170, 281, 50);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Chilanka", Font.PLAIN, 26));
        lblPassword.setBounds(450, 286, 193, 52);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Chilanka", Font.PLAIN, 26));
        passwordField.setBounds(650, 286, 281, 50);
        contentPane.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        loginButton.setBounds(545, 392, 162, 50);
        loginButton.setBackground(Color.decode("#f3f7f8"));
        loginButton.setForeground(new Color(51, 51, 51));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        contentPane.add(loginButton);

        RegisterButton = new JButton("Register as User");
        RegisterButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        RegisterButton.setBounds(800, 392, 162, 50);
        RegisterButton.setBackground(Color.decode("#f3f7f8"));
        RegisterButton.setForeground(new Color(51, 51, 51));
        RegisterButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        contentPane.add(RegisterButton);
        
        RegisterButton = new JButton("Register as User");
        RegisterButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        RegisterButton.setBounds(800, 392, 162, 50);
        RegisterButton.setBackground(Color.decode("#f3f7f8"));
        RegisterButton.setForeground(new Color(51, 51, 51));
        RegisterButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        contentPane.add(RegisterButton);

        guestButton = new JButton("Continue as Guest");
        guestButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        guestButton.setBounds(600, 450, 300, 50);
        guestButton.setBackground(Color.decode("#f3f7f8"));
        guestButton.setForeground(new Color(51, 51, 51));
        guestButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        contentPane.add(guestButton);

        // Initialize DAOs
        userController = new KorisnikDAO();
        adminController = new AdministratorDAO();
        orgController = new OrganizatorDAO();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    // Pokušaj prijave kao admin
                    Administrator admin = adminController.searchByUserName(username);
                    if (admin != null && admin.getPassword().equals(password)) {
                        AdminView adminView = new AdminView(admin);
                        adminView.setVisible(true);
                        dispose();
                        return;
                    }

                 // Pokušaj prijave kao korisnik
                    Organizator organizator = orgController.searchByUserName(username);
                    
                    if (organizator == null ) {

                        
                    }
                    else if (!organizator.getPassword().equals(password)){
                    	
                    	 JOptionPane.showMessageDialog(null, "Password is incorrect.");
                    }
                    else if (!organizator.isProfileApproved()) {
                    	
                    	 JOptionPane.showMessageDialog(null, "Profile is pending.");
                    } else {
                    	
                        OrganizatorView orgView = new OrganizatorView(organizator);
                        orgView.setVisible(true);
                        dispose();
                        return;
                    }


                    // Pokušaj prijave kao korisnik
                    Korisnik korisnik = userController.searchByUserName(username);
                    
                    if (korisnik == null ) {

                        // Ako nijedna prijava nije uspješna
                        JOptionPane.showMessageDialog(null, "Profile doesn't exist.");
                    }
                    else if (!korisnik.getPassword().equals(password)){
                    	
                    	 JOptionPane.showMessageDialog(null, "Password is incorrect.");
                    }
                    else if (!korisnik.isProfileApproved()) {
                    	
                    	 JOptionPane.showMessageDialog(null, "Profile is pending.");
                    } else {
                    	
                        KorisnikView korisnikView = new KorisnikView(korisnik);
                        korisnikView.setVisible(true);
                        dispose();
                        return;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while logging in.");
                }
            }
        });

        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                register.setVisible(true);
                dispose();
            }
        });

        guestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //GuestView guestView = new GuestView();
                //guestView.setVisible(true);
                //dispose();
            }
        });
    }
}