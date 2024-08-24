package view;

import model.IzmjeneDAO;
import model.Korisnik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfile extends JFrame {
    private static final long serialVersionUID = 1L;
    private Korisnik korisnik;
    private IzmjeneDAO izmjeneDAO = new IzmjeneDAO();

    // Konstruktor
    public EditProfile(Korisnik korisnik) {
        this.korisnik = korisnik;
        initialize();
    }

    private void initialize() {
    	
  
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
        

        // Tekstualni okviri za unos novih podataka
        JLabel lblName = new JLabel("Name:");
        lblName.setForeground(new Color(51, 51, 51));
        lblName.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblName.setBounds(450, 100, 281, 50);
        contentPane.add(lblName);

        JTextField nameField = new JTextField(korisnik.getName());
        nameField.setForeground(new Color(51, 51, 51));
        nameField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        nameField.setBounds(650, 100, 281, 50);
        contentPane.add(nameField);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setForeground(new Color(51, 51, 51));
        lblLastName.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblLastName.setBounds(450, 170, 200, 50);
        contentPane.add(lblLastName);

        JTextField lastNameField = new JTextField(korisnik.getLastName());
        lastNameField.setForeground(new Color(51, 51, 51));
        lastNameField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        lastNameField.setBounds(650, 170, 281, 50);
        contentPane.add(lastNameField);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(new Color(51, 51, 51));
        lblEmail.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblEmail.setBounds(450, 240, 150, 52);
        contentPane.add(lblEmail);

        JTextField emailField = new JTextField(korisnik.getEmail());
        emailField.setForeground(new Color(51, 51, 51));
        emailField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        emailField.setBounds(650, 240, 281, 50);
        contentPane.add(emailField);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setForeground(new Color(51, 51, 51));
        lblPhone.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblPhone.setBounds(450, 310, 150, 52);
        contentPane.add(lblPhone);

        JTextField phoneField = new JTextField(korisnik.getPhone());
        phoneField.setForeground(new Color(51, 51, 51));
        phoneField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        phoneField.setBounds(650, 310, 281, 50);
        contentPane.add(phoneField);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setForeground(new Color(51, 51, 51));
        lblAddress.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblAddress.setBounds(450, 380, 150, 52);
        contentPane.add(lblAddress);

        JTextField addressField = new JTextField(korisnik.getAddress());
        addressField.setForeground(new Color(51, 51, 51));
        addressField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        addressField.setBounds(650, 380, 281, 50);
        contentPane.add(addressField);

        // Dugme za čuvanje promjena
        JButton saveButton = new JButton("Request Changes");
        saveButton.setBounds(500, 470, 200, 50);
        saveButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        saveButton.setForeground(new Color(51, 51, 51));
        saveButton.setBackground(Color.decode("#f3f7f8"));
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().length() != 0) {
                	izmjeneDAO.addChange("Korisnik", korisnik.getUsername(), "name", nameField.getText());
                }
                if (lastNameField.getText().length() != 0) {
                	izmjeneDAO.addChange("Korisnik", korisnik.getUsername(), "lastName", lastNameField.getText());
                }
                if (emailField.getText().length() != 0) {
                	izmjeneDAO.addChange("Korisnik", korisnik.getUsername(), "email", emailField.getText());
                }
                if (phoneField.getText().length() != 0) {
                	izmjeneDAO.addChange("Korisnik", korisnik.getUsername(), "phone", phoneField.getText());
                }
                if (addressField.getText().length() != 0) {
                	izmjeneDAO.addChange("Korisnik", korisnik.getUsername(), "address", addressField.getText());
                }
                dispose();
            }
        });
        contentPane.add(saveButton);
        
        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        backButton.setBounds(750, 470, 200, 50);
        backButton.setForeground(new Color(51, 51, 51));
        backButton.setBackground(Color.decode("#f3f7f8"));
        backButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        backButton.addActionListener(e -> {
            KorisnikView back = new KorisnikView(korisnik);
            back.setVisible(true);
            dispose();
        });
        contentPane.add(backButton);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Korisnik korisnik = new Korisnik(); // Zamijeni sa stvarnim Korisnik objektom
                    EditProfile frame = new EditProfile(korisnik);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}