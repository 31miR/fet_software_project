package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Korisnik;
import model.KorisnikDAO;

public class WalletBalanceView extends JDialog {
    private static final long serialVersionUID = 1L;

    // Inicijalizirani podaci
    private String initializedID = "123456789";
    private int initializedYear = 2025;
    private int cvc =123;
    private Korisnik korisnik;
    private KorisnikDAO korisnikDAO = new KorisnikDAO();

    // UI komponente
    private JTextField idCardField;
    private JTextField expirationYearField;
    private JTextField walletBalanceField;
    private JTextField cvcField;

    public WalletBalanceView(JFrame parentFrame, Korisnik korisnik) {
    	super(parentFrame, "Add Wallet Balance", true);
        this.korisnik = korisnik;
        initialize();
        setLocationRelativeTo(parentFrame);
    }

    private void initialize() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 450, 400);
        setResizable(false);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));

        setTitle("Add Wallet Balance");

        JLabel idCardLabel = new JLabel("Card Number:");
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
                    if (enteredID.equals(initializedID) && enteredYear == initializedYear && cvcnum==cvc && balanceToAdd >=0) {
                        korisnik.setWalletBalance(korisnik.getWalletBalance() + (int)(balanceToAdd * 100));
                        korisnikDAO.updateKorisnik(korisnik);
                        JOptionPane.showMessageDialog(null, "Wallet balance updated!");
                        dispose();
                    }
                    else if (balanceToAdd < 0) {
                    	JOptionPane.showMessageDialog(null, "You can't remove money from your wallet.");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid ID card or expiration year. Please try again.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for wallet balance.");
                }
            }
        });
    }
}