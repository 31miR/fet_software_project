package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.Korisnik;
import model.KorisnikDAO;

public class WalletBalanceView extends JFrame {
    private static final long serialVersionUID = 1L;

    // Inicijalizirani podaci
    private String initializedID = "123456789";
    private int initializedYear = 2025;
    private int cvc =123;
    private Korisnik korisnik;

    // UI komponente
    private JTextField idCardField;
    private JTextField expirationYearField;
    private JTextField walletBalanceField;
    private JTextField cvcField;

    public WalletBalanceView(Korisnik korisnik) {
        this.korisnik = korisnik;
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	dispose();
            	KorisnikAndDogadjajListView view = new KorisnikAndDogadjajListView(korisnik);
            	view.setVisible(true);
            }
        });
        setBounds(450, 190, 450, 400);
        setResizable(false);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));

        setTitle("Add Wallet Balance");

        JLabel idCardLabel = new JLabel("ID Card:");
        idCardLabel.setBounds(50, 50, 150, 30);
        contentPane.add(idCardLabel);

        idCardField = new JTextField();
        idCardField.setBounds(200, 50, 200, 30);
        contentPane.add(idCardField);

        JLabel expirationYearLabel = new JLabel("Expiration Year:");
        expirationYearLabel.setBounds(50, 100, 150, 30);
        contentPane.add(expirationYearLabel);

        expirationYearField = new JTextField();
        expirationYearField.setBounds(200, 100, 200, 30);
        contentPane.add(expirationYearField);
        
        JLabel cvcLabel = new JLabel("CVC number:");
        cvcLabel.setBounds(50, 150, 150, 30);
        contentPane.add(cvcLabel);

        cvcField = new JTextField();
        cvcField.setBounds(200, 150, 200, 30);
        contentPane.add(cvcField);

        JLabel walletBalanceLabel = new JLabel("Wallet Balance:");
        walletBalanceLabel.setBounds(50, 200, 150, 30);
        contentPane.add(walletBalanceLabel);

        walletBalanceField = new JTextField();
        walletBalanceField.setBounds(200, 200, 200, 30);
        contentPane.add(walletBalanceField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 250, 200, 40);
        contentPane.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String enteredID = idCardField.getText();
                    int enteredYear = Integer.parseInt(expirationYearField.getText());
                    double balanceToAdd = Double.parseDouble(walletBalanceField.getText());
                    int cvcnum = Integer.parseInt(cvcField.getText());

                    // Provjeri ID i godinu
                    if (enteredID.equals(initializedID) && enteredYear == initializedYear && cvcnum==cvc ) {
                        korisnik.setWalletBalance(korisnik.getWalletBalance() + (int)(balanceToAdd * 100)); 
                        JOptionPane.showMessageDialog(null, "Wallet balance updated!");
                        KorisnikAndDogadjajListView view = new KorisnikAndDogadjajListView(korisnik);
                        view.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid ID card or expiration year. Please try again.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for wallet balance.");
                }
            }
        });
    }
}