package view;

import model.Karta;
import model.Korisnik;
import model.KartaDAO;
import model.Sektor;
import model.SektorDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BuyTickets extends JFrame {
    private Korisnik korisnik;
    private List<Karta> karte;
    private JComboBox<Karta> kartaComboBox;

    private JTextField imeField;
    private JTextField prezimeField;
    private JTextField adresaField;
    private JTextField telefonField;
    private JLabel walletLabel;
    private JButton buyButton;
    private JButton cancelButton;

    public BuyTickets(Korisnik korisnik, List<Karta> karte) {
        this.korisnik = korisnik;
        this.karte = karte;

        setTitle("Kupovina karte");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        imeField = new JTextField(korisnik.getName());
        prezimeField = new JTextField(korisnik.getLastName());
        adresaField = new JTextField(korisnik.getAddress());
        telefonField = new JTextField(korisnik.getPhone());
        walletLabel = new JLabel("Balance: " + korisnik.getWalletBalance() + " KM");

        kartaComboBox = new JComboBox<>(karte.toArray(new Karta[0]));

        buyButton = new JButton("Kupi");
        cancelButton = new JButton("Odustani");

        add(new JLabel("Ime:"));
        add(imeField);
        add(new JLabel("Prezime:"));
        add(prezimeField);
        add(new JLabel("Adresa:"));
        add(adresaField);
        add(new JLabel("Telefon:"));
        add(telefonField);
        add(new JLabel("Karta:"));
        add(kartaComboBox);
        add(walletLabel);
        add(buyButton);
        add(cancelButton);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBuyTicket();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private double applyDiscount(double price, int brojKupljenihKarti) {
        double discount = 0;

        // Svaka 10-ta kupljena karta daje 10% popusta
        if (brojKupljenihKarti % 10 == 0) {
            discount += 0.10;
        }

        return price * (1 - discount);
    }
    private void handleBuyTicket() {
        if (imeField.getText().isEmpty() || prezimeField.getText().isEmpty() || 
            adresaField.getText().isEmpty() || telefonField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sva polja moraju biti popunjena!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Karta selectedKarta = (Karta) kartaComboBox.getSelectedItem();
        if (selectedKarta == null) {
            JOptionPane.showMessageDialog(this, "Nema dostupnih karata.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int brojKupljenihKarti = korisnik.getBrojKupljenihKarti();
        double finalPrice = applyDiscount(selectedKarta.getCijena(), brojKupljenihKarti);

        if (korisnik.getWalletBalance() >= finalPrice) {
        	// Prvo izračunajte novu vrijednost walletBalance
            double currentBalance = korisnik.getWalletBalance();
            double newBalance = currentBalance - finalPrice;

            // Konvertujte double u int
            int newBalanceAsInt = (int) newBalance;

            // Postavi korisnika kao kupca karte
            selectedKarta.setKorKupio(korisnik);

            // Ažuriraj sektor
            SektorDAO sektorDAO = new SektorDAO();
            Sektor sektor = sektorDAO.getSektorById(selectedKarta.getSektor().getSektor_id());

            if (sektor != null) {
                int slobodnaMjesta = sektor.getKapacitet() - 1; // Pretpostavimo da je kapacitet samo jedan manje
                sektor.setKapacitet(slobodnaMjesta);

                // Spremi ažurirani sektor koristeći novu metodu
                sektorDAO.updateSektor(sektor);
            }

            // Ažuriraj kartu
            KartaDAO kartaDAO = new KartaDAO();
            kartaDAO.addTicket(selectedKarta);

            korisnik.setBrojKupljenihKarti(brojKupljenihKarti + 1);

            // Generiši PDF (ako je potrebno)
            // generatePDF(selectedKarta);

            JOptionPane.showMessageDialog(this, "Karta uspješno kupljena!", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Nemate dovoljno novca na računu!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }




    /*private void generatePDF(Karta karta) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Karta_" + karta.getId() + ".pdf"));
            document.open();
            document.add(new Paragraph("Karta ID: " + karta.getId()));
            document.add(new Paragraph("Naziv događaja: " + karta.getNazivDogadjaja()));
            document.add(new Paragraph("Cijena: " + karta.getCijena() + " KM"));
            document.add(new Paragraph("Kupljena od korisnika: " + korisnik.getName() + " " + korisnik.getLastName()));
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Došlo je do greške pri generisanju PDF-a.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }*/
}
