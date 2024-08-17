package view;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;


import model.Dogadjaj;
import model.DogadjajDAO;

public class RequestEvents extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;

    private DogadjajDAO dogadjajDAO;
    
    public RequestEvents() {
        dogadjajDAO = new DogadjajDAO(); // Inicijalizuj DAO

        setTitle("Event Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 800, 600);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Kreiraj tabelu
        String[] columnNames = {"ID", "Event Name", "Organizer", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Učitaj podatke u tabelu
        loadEventRequests(model);

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
    
  


    private void loadEventRequests(DefaultTableModel model) {
    	
        List<Dogadjaj> eventRequests = dogadjajDAO.getLimitedPending(0, 30); // Dohvati zahtjeve iz baze

        for (Dogadjaj dogadjaj : eventRequests) {
            Object[] rowData = {
                dogadjaj.getDogadjaj_id(),
                dogadjaj.getNaziv(),
                dogadjaj.isDogadjajApproved()
            };
            model.addRow(rowData); // Dodaj red u tabelu
        }
    }
}