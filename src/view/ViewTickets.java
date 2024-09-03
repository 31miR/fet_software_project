package view;

import model.Dogadjaj;
<<<<<<< HEAD

import model.DogadjajDAO;
import model.Korisnik;
import model.Organizator;
import view.BuyTickets;
import model.Karta;
import model.KartaDAO;
=======
import model.DogadjajDAO;
import model.Korisnik;
import model.Organizator;
>>>>>>> f0cc8f2 (modified event for organizator)

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewTickets extends JFrame {
    private Korisnik korisnik;
    private DogadjajDAO dogadjajDAO;
    private JPanel eventsPanel;
    private JButton seeMoreButton;
    private int start = 0;
    private final int limit = 5;

    public ViewTickets(Korisnik korisnik) {
        this.korisnik = korisnik;
        this.dogadjajDAO = new DogadjajDAO();
        setTitle("Karta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("Karta");
        headerLabel.setFont(new Font("Chilanka", Font.BOLD, 24));
        headerLabel.setForeground(Color.decode("333333"));
        headerLabel.setBackground(Color.decode("#14bea6"));// Green color
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Events Panel
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // See More Button
        seeMoreButton = new JButton("See More");
        seeMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start += limit;
                loadEvents();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(seeMoreButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
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

    // Method to load events
 // Method to load events
    private void loadEvents() {
        // Prepare the list of filters
        List<String> filters = new ArrayList<>();

        // Load events with filters
        List<Dogadjaj> events = dogadjajDAO.getFiltered(new ArrayList<>(), "datum", true, start, limit);

        if (events.isEmpty() && start > 0) {
            seeMoreButton.setEnabled(false);
        }

        if (start == 0) {
            eventsPanel.removeAll();
        }

        for (Dogadjaj event : events) {
            JPanel eventPanel = new JPanel();
            eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
            eventPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Line between events
            eventPanel.setPreferredSize(new Dimension(getWidth(), 150)); // Set a preferred height for the panel

            // Event Name and Date
            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
            headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel eventNameLabel = new JLabel("<html><b>Event Name:</b> " + event.getNaziv() + "</html>");
            eventNameLabel.setFont(new Font("Chilanka", Font.BOLD, 18));
            JLabel eventDateLabel = new JLabel("<html><b>Date:</b> " + event.getDatum().toString() + "</html>");
            eventDateLabel.setFont(new Font("Chilanka", Font.BOLD, 18));

            headerPanel.add(eventNameLabel);
            headerPanel.add(eventDateLabel);

            // Event Description
            JLabel eventDescriptionLabel = new JLabel("<html><div style='text-align: left;'>Description: " + event.getOpis() + "</div></html>");
            eventDescriptionLabel.setFont(new Font("Chilanka", Font.PLAIN, 16));

            eventPanel.add(headerPanel);
            eventPanel.add(eventDescriptionLabel);

            // Panel for buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Buttons side by side

            // Reserve Ticket Button
            JButton reserveButton = new JButton("Reserve Ticket");
            reserveButton.setBackground(Color.decode("#e2e2e2")); // Background color
            reserveButton.setForeground(Color.decode("#333333")); // Text color
            reserveButton.addActionListener(e -> {
                // Handle Reserve Ticket action
            });
            JButton buyButton = new JButton("Buy Ticket");
            buyButton.setBackground(Color.decode("#e2e2e2"));
            buyButton.setForeground(Color.decode("#333333")); // Text color
            buyButton.addActionListener(e -> {
                List<Karta> karte = event.getKarta(); // Get available tickets for the event
                if (karte.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No tickets available for this event.");
                    return;
                }
                // Kreiraj instancu BuyTickets sa potrebnim parametrima
                BuyTickets buyTicketsView = new BuyTickets(korisnik, karte); // Pass the list of tickets
                buyTicketsView.setVisible(true); // Postavi prozor vidljivim
                dispose(); // Zatvori trenutni prozor
            });

            // Add buttons to the button panel
            buttonPanel.add(reserveButton);
            buttonPanel.add(buyButton);

            // Add the button panel to the event panel
            eventPanel.add(buttonPanel);

            // Add the event panel to the events panel
            eventsPanel.add(eventPanel);
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }
}