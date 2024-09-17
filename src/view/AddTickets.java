package view;

import javax.swing.*;
import model.Dogadjaj;
import model.Karta;
import model.KartaDAO;
import model.Lokacija;
import model.Sektor;
import model.SektorDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddTickets extends JDialog {
    private Dogadjaj dogadjaj;
    private KartaDAO kartaDAO;
    private SektorDAO sektorDAO = new SektorDAO();
    private Lokacija selectedLokacija;
    private boolean ticketsAdded = false;
    private List<Karta> createdTickets = new ArrayList<>(); // List to keep track of created tickets

    public AddTickets(Dogadjaj dogadjaj, Lokacija selectedLokacija) {
    	super((JFrame)null, "Add tickets", true);
        this.dogadjaj = dogadjaj;
        this.kartaDAO = new KartaDAO();
        this.selectedLokacija = selectedLokacija;

        if (selectedLokacija == null) {
            JOptionPane.showMessageDialog(this, "Selected location cannot be null", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle("Add Tickets");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(450, 210, 400, 400);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(29, 190, 166));
        add(panel);

        List<Sektor> sektori = sektorDAO.getAllSectorsInLokacija(selectedLokacija);

        for (Sektor sektor : sektori) {
            JPanel sectorPanel = new JPanel(new GridLayout(0, 1));
            sectorPanel.setBorder(BorderFactory.createTitledBorder("Sector: " + sektor.getNaziv()));
            sectorPanel.setBackground(new Color(29, 190, 166));

            JLabel lblAmount = new JLabel("Amount:");
            JTextField txtAmount = new JTextField(5);

            JLabel lblPrice = new JLabel("Price:");
            JTextField txtPrice = new JTextField(5);

            JLabel lblResPrice = new JLabel("Reservation Price:");
            JTextField txtResPrice = new JTextField(5);

            JButton btnGenerate = new JButton("Generate Tickets");

            sectorPanel.add(lblAmount);
            sectorPanel.add(txtAmount);
            sectorPanel.add(lblPrice);
            sectorPanel.add(txtPrice);
            sectorPanel.add(lblResPrice);
            sectorPanel.add(txtResPrice);
            sectorPanel.add(btnGenerate);
            panel.add(sectorPanel);

            btnGenerate.addActionListener(e -> {
                try {
                    int amount = Integer.parseInt(txtAmount.getText());
                    int price = (int)(100*Double.parseDouble(txtPrice.getText()));
                    int resPrice = (int)(100*Double.parseDouble(txtResPrice.getText()));

                    if (amount > sektor.getKapacitet()) {
                        JOptionPane.showMessageDialog(AddTickets.this,
                                "Cannot generate tickets. The amount exceeds the sector's capacity.");
                    }
                    else if (amount < 0) {
                    	JOptionPane.showMessageDialog(AddTickets.this,
                                "Ammount of tickets cannot be negative.");
                    }
                    else if (price < 0 || resPrice < 0) {
                    	JOptionPane.showMessageDialog(AddTickets.this,
                                "Prices cannot be negative!");
                    }
                    else {
                       // List<Karta> tickets = 
                        kartaDAO.generateTickets(dogadjaj, sektor, amount, price, resPrice);
                      //  createdTickets.addAll(tickets); // Add generated tickets to the list
                        JOptionPane.showMessageDialog(AddTickets.this, "Tickets generated successfully.");
                        ticketsAdded = true; // Mark tickets as added
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddTickets.this, "Please enter valid numbers.");
                }
            });
        }

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            if (ticketsAdded) {
                dispose(); // Close dialog if tickets were added
            } else {
                JOptionPane.showMessageDialog(AddTickets.this, "No tickets were added.");
                dispose();
            }
        });
        panel.add(btnClose);

        pack(); // Adjust the size of the dialog
    }

    public boolean isTicketsAdded() {
        return ticketsAdded;
    }

    public List<Karta> getCreatedTickets() {
        return createdTickets;
    }
}
