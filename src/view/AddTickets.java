package view;

import model.Dogadjaj;
import model.KartaDAO;
import model.Sektor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTickets extends JFrame {
    private Dogadjaj dogadjaj;
    private KartaDAO kartaDAO;
    //private SektorDAO sektorDAO;

    public AddTickets(Dogadjaj dogadjaj) {
        this.dogadjaj = dogadjaj;
        this.kartaDAO = new KartaDAO();
     //   this.sektorDAO = new SektorDAO();

        setTitle("Add Tickets");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 210, 400, 400);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(29, 190, 166));
        add(panel);

        JLabel lblSector = new JLabel("Sector:");
        lblSector.setBounds(30, 30, 100, 30);
        panel.add(lblSector);

        JTextField txtSector = new JTextField();
        txtSector.setBounds(160, 30, 200, 30);
        panel.add(txtSector);

        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(30, 80, 100, 30);
        panel.add(lblAmount);

        JTextField txtAmount = new JTextField();
        txtAmount.setBounds(160, 80, 200, 30);
        panel.add(txtAmount);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(30, 130, 100, 30);
        panel.add(lblPrice);

        JTextField txtPrice = new JTextField();
        txtPrice.setBounds(160, 130, 200, 30);
        panel.add(txtPrice);

        JLabel lblResPrice = new JLabel("Reservation Price:");
        lblResPrice.setBounds(30, 180, 150, 30);
        panel.add(lblResPrice);

        JTextField txtResPrice = new JTextField();
        txtResPrice.setBounds(160, 180, 200, 30);
        panel.add(txtResPrice);

        JButton btnAddTickets = new JButton("Add Tickets");
        btnAddTickets.setBounds(100, 250, 200, 40);
        panel.add(btnAddTickets);

        btnAddTickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sector = txtSector.getText();
                    int amount = Integer.parseInt(txtAmount.getText());
                    int price = Integer.parseInt(txtPrice.getText());
                    int resPrice = Integer.parseInt(txtResPrice.getText());

                    // Fetch the sector from the database
                //    Sektor sektor = sektorDAO.getSektorByName(sector);
                 //   if (sektor == null) {
                   //     JOptionPane.showMessageDialog(AddTickets.this, "Sector not found in the database.");
                     //   return;
                //    }
                    // Create a dummy sector object or fetch it from the DB
                    Sektor sektor = new Sektor(); // In a real scenario, fetch the actual sector from DB
                    sektor.setNaziv(sector); 
                    // Generate and persist tickets
                    kartaDAO.generateTickets(dogadjaj, sektor, amount, price, resPrice);

                    JOptionPane.showMessageDialog(AddTickets.this, "Tickets added successfully.");
                    dispose(); // Close the AddTickets window
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddTickets.this, "Please enter valid numbers.");
                }
            }
        });
    }
}
