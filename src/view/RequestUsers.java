package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.border.EmptyBorder;

import model.Korisnik;
import model.KorisnikDAO;

public class RequestUsers extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private KorisnikDAO korisnikDAO;

    public RequestUsers() {
        korisnikDAO = new KorisnikDAO(); // Inicijalizuj DAO

        setTitle("User Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 800, 600);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Kreiraj tabelu
        String[] columnNames = {"Username", "Name", "LastName", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        loadUserRequests(model);

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private void loadUserRequests(DefaultTableModel model) {
        List<Korisnik> userRequests = korisnikDAO.getLimitedPending(0, 30); // Dohvati zahtjeve iz baze

        for (Korisnik korisnik : userRequests) {
            Object[] rowData = {
                korisnik.getUsername(),
                korisnik.getName(),
                korisnik.getLastName()
            };
            model.addRow(rowData); // Dodaj red u tabelu

            // Dodaj dugmad u svaki red
            JButton acceptButton = new JButton("Accept");
            acceptButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int row = table.convertRowIndexToModel(table.getSelectedRow());
                    Korisnik selectedUser = userRequests.get(row);
                    selectedUser.setProfileApproved(true);
                    korisnikDAO.updateKorisnik(selectedUser);
                    model.setValueAt(true, row, 3); // Update status in table
                }
            });

            JButton rejectButton = new JButton("Reject");
            rejectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int row = table.convertRowIndexToModel(table.getSelectedRow());
                    Korisnik selectedUser = userRequests.get(row);
                    selectedUser.setProfileApproved(false);
                    korisnikDAO.updateKorisnik(selectedUser);
                    model.setValueAt(false, row, 3); // Update status in table
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(acceptButton);
            buttonPanel.add(rejectButton);
            model.setValueAt(buttonPanel, model.getRowCount() - 1, 3); // Add buttons to table
        }
    }}