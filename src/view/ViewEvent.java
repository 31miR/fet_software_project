package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Administrator;
import model.Dogadjaj;
import model.DogadjajDAO;

public class ViewEvent extends JFrame {
	private static final long serialVersionUID = 1L;
	private DogadjajDAO dogadjajDAO;
    private JPanel eventsPanel;
    private JButton seeMoreButton;
    private int start = 0;
    private final int limit = 5;

    // Konstruktor koji prima admin objekt
    public ViewEvent(Administrator admin) {
        dogadjajDAO = new DogadjajDAO();
        setTitle("Karta");
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
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
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
            eventGbc.weightx = 0.5;
            eventPanel.add(nameLabel, eventGbc);
            
            // Dodavanje naziva lokacije
            JLabel dogadjajLabel = new JLabel("Location Name: " + event.getLokacija().getNaziv());
            dogadjajLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Podešavanje fonta
            eventGbc.gridx = 0;
            eventGbc.gridy = 1;
            eventPanel.add(dogadjajLabel, eventGbc);

            // Dodavanje datuma događaja
            JLabel dateLabel = new JLabel("Date: " + event.getDatum().toString());
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Podešavanje fonta
            eventGbc.gridx = 0;
            eventGbc.gridy = 2;
            eventGbc.anchor = GridBagConstraints.EAST; // Poravnanje desno
            eventPanel.add(dateLabel, eventGbc);

            // Dodavanje panela događaja na glavni panel događaja
            eventsPanel.add(eventPanel);
            eventsPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Dodavanje praznog prostora između panela
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }


}
