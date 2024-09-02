package view;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.Administrator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ViewEvent extends JFrame {
    private DogadjajDAO dogadjajDAO;
    private JPanel eventsPanel;
    private JButton seeMoreButton;
    private int start = 0;
    private final int limit = 5;
    private Administrator admin; // Dodana varijabla za admina

    // Konstruktor koji prima admin objekt
    public ViewEvent(Administrator admin) {
        this.admin = admin; // Postavljanje admina
        dogadjajDAO = new DogadjajDAO();
        setTitle("View Events");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 210, 800, 600);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("View Events");
        headerLabel.setFont(new Font("Chilanka", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Events Panel
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // See More Button
        seeMoreButton = new JButton("See More");
        seeMoreButton.setBackground(Color.LIGHT_GRAY);
        seeMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start += limit;
                loadEvents();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(seeMoreButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.addActionListener(e -> {
            AdminView view = new AdminView(admin); // Korištenje proslijeđenog admina
            view.setVisible(true);
            dispose();
        });
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Load initial events
        loadEvents();
    }

    // Metoda za učitavanje događaja
    private void loadEvents() {
        // Kreiranje prazne liste filtara, ili dodajte filtere po potrebi
        List<String> filters = new ArrayList<>();
        
        // Koristite DAO metodu za preuzimanje događaja sa filtriranjem, sortiranjem i paginacijom
        List<Dogadjaj> events = dogadjajDAO.getFiltered(filters, "", "datum", true, start, limit);

        if (events.isEmpty() && start > 0) {
            seeMoreButton.setEnabled(false); // Onemogućavanje dugmeta ako više nema događaja
        }

        if (start == 0) {
            eventsPanel.removeAll(); // Očistite prethodne događaje ako počinjete iz početka
        }

        // Postavljanje BoxLayout za vertikalno slaganje događaja
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));

        for (Dogadjaj event : events) {
            JPanel eventPanel = new JPanel();
            eventPanel.setLayout(new GridBagLayout());
            eventPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margin oko panela
            eventPanel.setBackground(new Color(240, 240, 240)); // Svijetlosiva pozadina

            GridBagConstraints eventGbc = new GridBagConstraints();
            eventGbc.insets = new Insets(5, 5, 5, 5);
            eventGbc.anchor = GridBagConstraints.WEST; // Poravnanje lijevo

            // Dodavanje naziva događaja
            JLabel nameLabel = new JLabel("Event Name: " + event.getNaziv());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Podešavanje fonta
            eventGbc.gridx = 0;
            eventGbc.gridy = 0;
            eventGbc.weightx = 0.7;
            eventPanel.add(nameLabel, eventGbc);

            // Dodavanje datuma događaja
            JLabel dateLabel = new JLabel("Date: " + event.getDatum().toString());
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Podešavanje fonta
            eventGbc.gridx = 1;
            eventGbc.gridy = 0;
            eventGbc.weightx = 0.3;
            eventGbc.anchor = GridBagConstraints.EAST; // Poravnanje desno
            eventPanel.add(dateLabel, eventGbc);

            // Dodavanje panela događaja na glavni panel događaja
            eventsPanel.add(eventPanel);
            eventsPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Dodavanje praznog prostora između panela
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }



    public static void main(String[] args) {
        // Na primjer, možete proslijediti dummy admin objekt
        Administrator admin = new Administrator(); // Pretpostavlja se da Administrator ima default konstruktor
        new ViewEvent(admin);
    }
}
