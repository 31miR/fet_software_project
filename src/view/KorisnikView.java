package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Korisnik; // Provjeri da li si uvezao svoj Korisnik model

public class KorisnikView extends JFrame {
    private static final long serialVersionUID = 1L;
    private Korisnik korisnik;

    // Konstruktor koji prihvata Korisnik objekat
    public KorisnikView(Korisnik korisnik) {
        this.korisnik = korisnik;
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(450, 190, 900, 597);

        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));
        setTitle("Korisnik View");



        // Prikaz slike
        ImageIcon imageIcon = new ImageIcon("resources/logo.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        JLabel label = new JLabel(imageIcon);

        label.setBounds(50, 140, 300, 300);

        contentPane.add(label);

        // Pozdravna oznaka
        JLabel lblWelcome = new JLabel("Welcome, " + korisnik.getUsername() + "!");
        lblWelcome.setForeground(Color.BLACK);
        lblWelcome.setFont(new Font("Chilanka", Font.ITALIC, 46));
        lblWelcome.setBounds(300, 12, 600, 95);
        lblWelcome.setForeground(new Color(95, 95, 95));
        contentPane.add(lblWelcome);

        // Dugme za prikaz profila
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        viewProfileButton.setBounds(500, 120, 200, 50);
        viewProfileButton.setForeground(new Color(51, 51, 51));
        viewProfileButton.setBackground(Color.decode("#f3f7f8"));
        viewProfileButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewProfile profileView = new ViewProfile(korisnik);
                profileView.setVisible(true);
            }
        });
        contentPane.add(viewProfileButton);

        
        JButton EditProfileButton = new JButton("Edit Profile");
        EditProfileButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        EditProfileButton.setBounds(500, 200, 200, 50);
        EditProfileButton.setForeground(new Color(51, 51, 51));
        EditProfileButton.setBackground(Color.decode("#f3f7f8"));
        EditProfileButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        EditProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditProfile profileView = new EditProfile(korisnik);
                profileView.setVisible(true);
            }
        });
        contentPane.add(EditProfileButton);
 



        // Dugme za prikaz karata
        JButton viewTicketsButton = new JButton("View Tickets");
        viewTicketsButton.setFont(new Font("Chilanka", Font.PLAIN, 26));

        viewTicketsButton.setBounds(500, 300, 200, 50);

        viewTicketsButton.setForeground(new Color(51, 51, 51));
        viewTicketsButton.setBackground(Color.decode("#f3f7f8"));
        viewTicketsButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // ViewTickets viewTickets = new ViewTickets();
               // viewTickets.setVisible(true);
            }
        });
        contentPane.add(viewTicketsButton);

        // Saldo na novčaniku
        JLabel lblWalletBalance = new JLabel("Wallet Balance: $" + korisnik.getWalletBalance());
        lblWalletBalance.setFont(new Font("Chilanka", Font.PLAIN, 20));

        lblWalletBalance.setBounds(500, 400, 300, 30);

        lblWalletBalance.setForeground(new Color(51, 51, 51));
        contentPane.add(lblWalletBalance);
        
        // Dugme za odjavu
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Chilanka", Font.PLAIN, 26));

        logoutButton.setBounds(500, 450, 200, 50);

        logoutButton.setForeground(new Color(51, 51, 51));
        logoutButton.setBackground(Color.decode("#f3f7f8"));
        logoutButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simulacija odjave
                JOptionPane.showMessageDialog(null, "Logout functionality not implemented.");
                Login loginView = new Login(); // Provjeri da li postoji Login klasa
                loginView.setVisible(true);
                dispose();
            }
        });
        contentPane.add(logoutButton);
        
     
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    KorisnikView frame = new KorisnikView(new Korisnik()); // Zamijeni sa stvarnim Korisnik objektom
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
