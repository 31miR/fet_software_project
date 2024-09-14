package view;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.KorisnikDAO;
import model.OrganizatorDAO;
import model.Administrator;
import model.AdministratorDAO;
import model.Korisnik;
import model.Organizator;

import javax.swing.border.LineBorder;


public class RegisterOrg extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel label;
    private JPanel contentPane;

    private KorisnikDAO userController = new KorisnikDAO();
    private AdministratorDAO adminController = new AdministratorDAO();
    private OrganizatorDAO orgController = new OrganizatorDAO();
    private JTextField textField;
    private JPasswordField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JButton backButton;
    private JButton registerButton;
    

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

    public RegisterOrg() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 800); // Povecaj visinu da stane registracija
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
        

        JLabel lblNewLabel = new JLabel("Dobrodosli u Kartu!");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Chilanka", Font.ITALIC, 46));
        lblNewLabel.setBounds(500, 12, 600, 95);
        lblNewLabel.setForeground(new Color(51, 51, 51));
        contentPane.add(lblNewLabel);

  


        // Registracijska polja
        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setForeground(new Color(51, 51, 51));
        lblFirstName.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblFirstName.setBounds(450, 150, 281, 50);
        contentPane.add(lblFirstName);

        firstNameField = new JTextField();
        firstNameField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        firstNameField.setBounds(650, 150, 281, 50);
        contentPane.add(firstNameField);

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setForeground(new Color(51, 51, 51));
        lblLastName.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblLastName.setBounds(450, 230, 200, 50);
        contentPane.add(lblLastName);

        lastNameField = new JTextField();
        lastNameField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        lastNameField.setBounds(650, 230, 281, 50);
        contentPane.add(lastNameField);
        
        JLabel lblusername = new JLabel("Username");
        lblusername.setForeground(new Color(51, 51, 51));
        lblusername.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblusername.setBounds(450, 300, 150, 52);
        contentPane.add(lblusername);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        usernameField.setBounds(650, 300, 281, 50);
        contentPane.add(usernameField);
        
        JLabel lblpassword= new JLabel("Password");
        lblpassword.setForeground(new Color(51, 51, 51));
        lblpassword.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblpassword.setBounds(450, 370, 150, 52);
        contentPane.add(lblpassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        passwordField.setBounds(650, 370, 281, 50);
        contentPane.add(passwordField);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setForeground(new Color(51, 51, 51));
        lblEmail.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblEmail.setBounds(450, 440, 150, 52);
        contentPane.add(lblEmail);

        emailField = new JTextField();
        emailField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        emailField.setBounds(650, 440, 281, 50);
        contentPane.add(emailField);
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setForeground(new Color(51, 51, 51));
        lbladdress.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lbladdress.setBounds(450, 510, 150, 52);
        contentPane.add(lbladdress);

        addressField = new JTextField();
        addressField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        addressField.setBounds(650, 510, 281, 50);
        contentPane.add(addressField);
        
        
        JLabel lblphone = new JLabel("Phone");
        lblphone.setForeground(new Color(51, 51, 51));
        lblphone.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblphone.setBounds(450, 590, 150, 52);
        contentPane.add(lblphone);

        phoneField = new JTextField();
        phoneField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        phoneField.setBounds(650, 590, 281, 50);
        contentPane.add(phoneField);
        
       

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        registerButton.setBounds(650, 670, 162, 50);
        registerButton.setForeground(new Color(51, 51, 51));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                String username = usernameField.getText();
                String phone = phoneField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    // Proveri da li su polja ispravno popunjena
                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || username.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                        JOptionPane.showMessageDialog(RegisterOrg.this, "All fields must be filled.");
                        return;
                    }

                    // Provjera da li je telefon broj
                    if (!phone.matches("\\d+")) {
                        JOptionPane.showMessageDialog(RegisterOrg.this, "Phone number can only contain numbers.");
                        return;
                    }

                    // Provjera email formata
                    if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                        JOptionPane.showMessageDialog(RegisterOrg.this, "Invalid email format.");
                        return;
                    }
                    
                    Administrator check_admin = adminController.searchByUserName(username);
                    if (check_admin != null) {
                    	JOptionPane.showMessageDialog(RegisterOrg.this, "Profile with that username already exists!");
                        return;
                    }
                    Organizator check_org = orgController.searchByUserName(username);
                    if (check_org != null) {
                    	JOptionPane.showMessageDialog(RegisterOrg.this, "Profile with that username already exists!");
                        return;
                    }
                    Korisnik check_kor = userController.searchByUserName(username);
                    if (check_kor != null) {
                    	JOptionPane.showMessageDialog(RegisterOrg.this, "Profile with that username already exists!");
                        return;
                    }

                    // Registruj korisnika
                    Organizator organizator = new Organizator();
                    organizator.setUsername(username);
                    organizator.setPassword(password);
                    organizator.setEmail(email);
                    organizator.setName(firstName);
                    organizator.setLastName(lastName);
                    organizator.setAddress(address);
                    organizator.setPhone(phone);

                    // Dodavanje korisnika u bazu
                    OrganizatorDAO organizatorDAO = new OrganizatorDAO();
                    organizatorDAO.addOrganizator(organizator);
                    JOptionPane.showMessageDialog(RegisterOrg.this, "Registration successful!");

                    // Očisti polja nakon uspešne registracije
                    firstNameField.setText("");
                    lastNameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    usernameField.setText("");
                    addressField.setText("");
                    phoneField.setText("");

                } catch (Exception err) {
                	err.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterOrg.this, "An error occurred during registration.");
                }
            }
        });
        
        contentPane.add(registerButton);
        
        // Back dugme
        backButton = new JButton("Back");
        backButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        backButton.setBounds(830, 670, 162, 50);
        backButton.setForeground(new Color(51, 51, 51));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Zatvori trenutni prozor
                Login login = new Login();
                login.setVisible(true); // Vrati se na Login ekran
            }
        });

        contentPane.add(backButton);
        
        contentPane.setVisible(true);
    }
}