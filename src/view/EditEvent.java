package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.Lokacija;
import model.Sektor;
import model.LokacijaDAO;
import model.SektorDAO;

public class EditEvent extends JFrame {
    private JTextField nazivField;
    private JTextField gradField;
    private JTextField adresaField;
    private JTextField kapacitetField;
    private JTextField slikaField;
    private JButton browseButton;
    private JButton addSectorButton;
    private JButton saveButton;
    private JButton cancelButton;

    private LokacijaDAO lokacijaDAO;
    private SektorDAO sektorDAO;
    private List<Sektor> sektori;

    public EditEvent() {
        // Postavke prozora
        setTitle("Create Location");
        setBounds(450, 190, 1014, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));

        // Inicijalizacija DAO klasa
        lokacijaDAO = new LokacijaDAO();
        sektorDAO = new SektorDAO();

        // Inicijalizacija polja i dugmadi
        nazivField = new JTextField();
        gradField = new JTextField();
        adresaField = new JTextField();
        kapacitetField = new JTextField();
        slikaField = new JTextField();
        browseButton = new JButton("Browse...");
        addSectorButton = new JButton("Add Sectors");
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Postavljanje pozicija i veličina komponenti
        nazivField.setBounds(200, 50, 300, 30);
        gradField.setBounds(200, 100, 300, 30);
        adresaField.setBounds(200, 150, 300, 30);
        kapacitetField.setBounds(200, 200, 300, 30);
        slikaField.setBounds(200, 250, 200, 30);
        browseButton.setBounds(420, 250, 100, 30);
        addSectorButton.setBounds(200, 300, 200, 30);
        saveButton.setBounds(200, 350, 100, 30);
        cancelButton.setBounds(320, 350, 100, 30);

        // Dodavanje komponenti na panel
        contentPane.add(new JLabel("Naziv lokacije:")).setBounds(50, 50, 150, 30);
        contentPane.add(nazivField);
        contentPane.add(new JLabel("Grad:")).setBounds(50, 100, 150, 30);
        contentPane.add(gradField);
        contentPane.add(new JLabel("Adresa:")).setBounds(50, 150, 150, 30);
        contentPane.add(adresaField);
        contentPane.add(new JLabel("Kapacitet:")).setBounds(50, 200, 150, 30);
        contentPane.add(kapacitetField);
        contentPane.add(new JLabel("Slika:")).setBounds(50, 250, 150, 30);
        contentPane.add(slikaField);
        contentPane.add(browseButton);
        contentPane.add(addSectorButton);
        contentPane.add(saveButton);
        contentPane.add(cancelButton);

        // Postavljanje akcija za dugmadi
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg", "gif"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    slikaField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        addSectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Unos broja sektora
                int brojSektora = Integer.parseInt(JOptionPane.showInputDialog("Koliko sektora želite dodati za ovu lokaciju?"));
                sektori = new ArrayList<>();
                
                for (int i = 0; i < brojSektora; i++) {
                    String nazivSektora = JOptionPane.showInputDialog("Unesite naziv sektora " + (i + 1) + ":");
                    int kapacitetSektora = Integer.parseInt(JOptionPane.showInputDialog("Unesite kapacitet sektora " + nazivSektora + ":"));
                    
                    // Kreiraj novi sektor
                    Sektor sektor = new Sektor();
                    sektor.setNaziv(nazivSektora);
                    sektor.setKapacitet(kapacitetSektora);
                    sektor.setLokacija(new Lokacija()); // Placeholder, će se ažurirati kasnije
                    
                    sektori.add(sektor);
                }
                
                JOptionPane.showMessageDialog(null, "Sektori su uspješno dodani. Kliknite na 'Save' za spremanje.");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Prikupljanje podataka iz polja
                    String naziv = nazivField.getText();
                    String grad = gradField.getText();
                    String adresa = adresaField.getText();
                    int kapacitet = Integer.parseInt(kapacitetField.getText());
                    String slika = slikaField.getText();
                    
                    // Kreiraj instancu Lokacija
                    Lokacija lokacija = new Lokacija();
                    lokacija.setNaziv(naziv);
                    lokacija.setGrad(grad);
                    lokacija.setAdresa(adresa);
                    lokacija.setKapacitet(kapacitet);
                    lokacija.setSlika(slika);
                    
                    // Spremi lokaciju u bazu podataka
                    lokacijaDAO.addLocation(lokacija);

                    // Ažuriraj lokaciju u sektorima
                    for (Sektor sektor : sektori) {
                        sektor.setLokacija(lokacija);
                        sektorDAO.addSektor(sektor);
                    }
                    
                    JOptionPane.showMessageDialog(null, "Lokacija i sektori su uspješno dodani u bazu podataka.");
                    AdminView view = new AdminView();
                    view.setVisible(true);
                    dispose();
                    ;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Unesite validne brojeve za kapacitet.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Došlo je do greške: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminView view = new AdminView();
                view.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditEvent frame = new EditEvent();
            frame.setVisible(true);
        });
    }
}