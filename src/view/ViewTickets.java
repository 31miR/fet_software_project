package view;

import model.Dogadjaj;
import model.DogadjajDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewTickets extends JFrame {
    private static final long serialVersionUID = 1L;

    public ViewTickets() {
        setTitle("Dostupni događaji");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 800, 600);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Tabela za prikaz događaja
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Naziv", "Opis", "Datum", "Vrsta", "Podvrsta"}, 0);
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Učitaj događaje iz baze
        loadEvents(model);
    }

    private void loadEvents(DefaultTableModel model) {
        DogadjajDAO dogadjajDAO = new DogadjajDAO();

        // Definiši filtere, sortiranje i granice broja rezultata
        List<String> filters = new ArrayList<>();
        filters.add("dogadjajApproved");
        filters.add("true"); // Prikazivanje samo odobrenih događaja

        String sortBy = "datum"; // Sortiraj po datumu
        boolean ascending = true; // Uzlazno sortiranje
        int start = 0;
        int ammount = 10;

        // Pozovi metodu getFiltered
        List<Dogadjaj> events = dogadjajDAO.getFiltered(filters, sortBy, ascending, start, ammount);

        for (Dogadjaj event : events) {
            model.addRow(new Object[]{
                event.getDogadjaj_id(),
                event.getNaziv(),
                event.getOpis(),
                event.getDatum(),
                event.getVrsta(),
                event.getPodvrsta()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ViewTickets frame = new ViewTickets();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}