package view;

import model.Dogadjaj;
import model.KartaDAO;
import model.Lokacija;
import model.Sektor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddTickets extends JFrame {
    private Dogadjaj dogadjaj;
    private KartaDAO kartaDAO;
    private Lokacija selectedLokacija;

    public AddTickets(Dogadjaj dogadjaj, Lokacija selectedLokacija) {
        this.dogadjaj = dogadjaj;
        this.kartaDAO = new KartaDAO();
        this.selectedLokacija = selectedLokacija;
        
        if (selectedLokacija == null) {
            JOptionPane.showMessageDialog(this, "Selected location cannot be null", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); // Close the window if no valid location is provided
            return;
        }

        setTitle("Add Tickets");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 210, 400, 400);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(29, 190, 166));
        add(panel);

        // Fetch sectors for the selected location
        List<Sektor> sektori = selectedLokacija.getSektor();

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
            JButton btnAddIndividually = new JButton("Add Tickets Individually");

            sectorPanel.add(lblAmount);
            sectorPanel.add(txtAmount);
            sectorPanel.add(lblPrice);
            sectorPanel.add(txtPrice);
            sectorPanel.add(lblResPrice);
            sectorPanel.add(txtResPrice);
            sectorPanel.add(btnGenerate);
            sectorPanel.add(btnAddIndividually);
            panel.add(sectorPanel);

            btnGenerate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int amount = Integer.parseInt(txtAmount.getText());
                        int price = Integer.parseInt(txtPrice.getText());
                        int resPrice = Integer.parseInt(txtResPrice.getText());

                        if (amount > sektor.getKapacitet()) {
                            JOptionPane.showMessageDialog(AddTickets.this,
                                    "Cannot generate tickets. The amount exceeds the sector's capacity.");
                        } else {
                            // Use the generateTickets method from KartaDAO
                            kartaDAO.generateTickets(dogadjaj, sektor, amount, price, resPrice);
                            JOptionPane.showMessageDialog(AddTickets.this, "Tickets generated successfully.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AddTickets.this, "Please enter valid numbers.");
                    }
                }
            });

            btnAddIndividually.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  AddIndividualTickets dialog = new AddIndividualTickets(dogadjaj, sektor, kartaDAO);
                   dialog.setVisible(true);
                }
            });
        }

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnClose);

        pack();
    }
}
