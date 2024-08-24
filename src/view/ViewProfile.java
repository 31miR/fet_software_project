package view;

import model.Korisnik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewProfile extends JFrame {
    private static final long serialVersionUID = 1L;
    private Korisnik korisnik;
   

    // Constructor with parameters
    public ViewProfile(Korisnik korisnik) {
        this.korisnik = korisnik;
        initComponents();
    }

  

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));
        setTitle("Profile - " + korisnik.getUsername());

        // Profile Details
        JLabel lblName = new JLabel("Name: " + korisnik.getName() + " " + korisnik.getLastName());
        lblName.setFont(new Font("Chilanka", Font.PLAIN, 26));
        lblName.setBounds(50, 50, 400, 30);
        contentPane.add(lblName);

        JLabel lblEmail = new JLabel("Email: " + korisnik.getEmail());
        lblEmail.setFont(new Font("Chilanka", Font.PLAIN, 26));
        lblEmail.setBounds(50, 100, 400, 30);
        contentPane.add(lblEmail);

        JLabel lblPhone = new JLabel("Phone: " + korisnik.getPhone());
        lblPhone.setFont(new Font("Chilanka", Font.PLAIN, 26));
        lblPhone.setBounds(50, 150, 400, 30);
        contentPane.add(lblPhone);

        JLabel lblAddress = new JLabel("Address: " + korisnik.getAddress());
        lblAddress.setFont(new Font("Chilanka", Font.PLAIN, 26));
        lblAddress.setBounds(50, 200, 400, 30);
        contentPane.add(lblAddress);

        JLabel lblWalletBalance = new JLabel("Wallet Balance: $" + korisnik.getWalletBalance());
        lblWalletBalance.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblWalletBalance.setBounds(500, 320, 300, 30);
        lblWalletBalance.setForeground(new Color(51, 51, 51));
        contentPane.add(lblWalletBalance);

        JLabel lblTicketsBought = new JLabel("Tickets Bought: " + korisnik.getBrojKupljenihKarti());
        lblTicketsBought.setFont(new Font("Chilanka", Font.PLAIN, 26));
        lblTicketsBought.setBounds(50, 300, 400, 30);
        contentPane.add(lblTicketsBought);

        // Back Button

        JButton backButton = new JButton("Back");

        backButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        backButton.setBounds(500, 420, 200, 50);
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
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }}
