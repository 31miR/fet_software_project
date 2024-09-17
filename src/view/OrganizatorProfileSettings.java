package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.IzmjeneDAO;
import model.Organizator;

public class OrganizatorProfileSettings extends JDialog {

    private static final long serialVersionUID = 1L;
    private Organizator organizator;
    private IzmjeneDAO izmjeneDAO = new IzmjeneDAO();

    // Konstruktor
    public OrganizatorProfileSettings(Organizator organizator) {
        this.organizator = organizator;
        initialize();
    }

    private void initialize() {
        setTitle("Karta");
        setModal(true); // Ensures that it acts as a modal dialog
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));

        // Prikaz slike
        ImageIcon imageIcon = new ImageIcon("resources/logo.png"); // Postavi putanju do slike
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

        JTextField nameField = new JTextField(organizator.getName());
        nameField.setForeground(new Color(51, 51, 51));
        nameField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        nameField.setBounds(650, 100, 281, 50);
        contentPane.add(nameField);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setForeground(new Color(51, 51, 51));
        lblLastName.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblLastName.setBounds(450, 170, 200, 50);
        contentPane.add(lblLastName);

        JTextField lastNameField = new JTextField(organizator.getLastName());
        lastNameField.setForeground(new Color(51, 51, 51));
        lastNameField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        lastNameField.setBounds(650, 170, 281, 50);
        contentPane.add(lastNameField);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(new Color(51, 51, 51));
        lblEmail.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblEmail.setBounds(450, 240, 150, 52);
        contentPane.add(lblEmail);

        JTextField emailField = new JTextField(organizator.getEmail());
        emailField.setForeground(new Color(51, 51, 51));
        emailField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        emailField.setBounds(650, 240, 281, 50);
        contentPane.add(emailField);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setForeground(new Color(51, 51, 51));
        lblPhone.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblPhone.setBounds(450, 310, 150, 52);
        contentPane.add(lblPhone);

        JTextField phoneField = new JTextField(organizator.getPhone());
        phoneField.setForeground(new Color(51, 51, 51));
        phoneField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        phoneField.setBounds(650, 310, 281, 50);
        contentPane.add(phoneField);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setForeground(new Color(51, 51, 51));
        lblAddress.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblAddress.setBounds(450, 380, 150, 52);
        contentPane.add(lblAddress);

        JTextField addressField = new JTextField(organizator.getAddress());
        addressField.setForeground(new Color(51, 51, 51));
        addressField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        addressField.setBounds(650, 380, 281, 50);
        contentPane.add(addressField);

        // Dugme za čuvanje promjena
        JButton saveButton = new JButton("Request Changes");
        saveButton.setBounds(500, 470, 250, 50);
        saveButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        saveButton.setForeground(new Color(51, 51, 51));
        saveButton.setBackground(Color.decode("#f3f7f8"));
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Provera i slanje promena samo ako su se vrednosti promenile
                if (!nameField.getText().equals(organizator.getName()) && !nameField.getText().isEmpty()) {
                    izmjeneDAO.addChange("Organizator", organizator.getUsername(), "name", nameField.getText());
                }
                if (!lastNameField.getText().equals(organizator.getLastName()) && !lastNameField.getText().isEmpty()) {
                    izmjeneDAO.addChange("Organizator", organizator.getUsername(), "lastName", lastNameField.getText());
                }
                if (!emailField.getText().equals(organizator.getEmail()) && !emailField.getText().isEmpty()) {
                    izmjeneDAO.addChange("Organizator", organizator.getUsername(), "email", emailField.getText());
                }
                if (!phoneField.getText().equals(organizator.getPhone()) && !phoneField.getText().isEmpty()) {
                    izmjeneDAO.addChange("Organizator", organizator.getUsername(), "phone", phoneField.getText());
                }
                if (!addressField.getText().equals(organizator.getAddress()) && !addressField.getText().isEmpty()) {
                    izmjeneDAO.addChange("Organizator", organizator.getUsername(), "address", addressField.getText());
                }
                dispose(); // Zatvaranje prozora nakon slanja promjena
            }
        });
        contentPane.add(saveButton);
    }
}
