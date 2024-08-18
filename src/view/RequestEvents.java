package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        String[] columnNames = {"ID", "Event Name", "Status", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

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

            // Dodaj dugmad u svaki red
            JButton approveButton = new JButton("Approve");
            approveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int row = table.convertRowIndexToModel(table.getSelectedRow());
                    Dogadjaj selectedEvent = eventRequests.get(row);
                    selectedEvent.setDogadjajApproved(true);
                    dogadjajDAO.updateDogadjaj(selectedEvent);
                    model.setValueAt(true, row, 2); // Update status in table
                }
            });

            JButton rejectButton = new JButton("Reject");
            rejectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int row = table.convertRowIndexToModel(table.getSelectedRow());
                    Dogadjaj selectedEvent = eventRequests.get(row);
                    selectedEvent.setDogadjajApproved(false);
                    dogadjajDAO.updateDogadjaj(selectedEvent);
                    model.setValueAt(false, row, 2); // Update status in table
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(approveButton);
            buttonPanel.add(rejectButton);
            model.setValueAt(buttonPanel, model.getRowCount() - 1, 3); // Add buttons to table
        }
    }
}