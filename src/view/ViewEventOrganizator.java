package view;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.Organizator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewEventOrganizator extends JFrame {
    private DogadjajDAO dogadjajDAO;
    private JPanel eventsPanel;
    private JButton seeMoreButton;
    private int start = 0;
    private final int limit = 5;
    private Organizator organizator;

    // Constructor that takes Organizator object
    public ViewEventOrganizator(Organizator org) {
        this.organizator = org;
        dogadjajDAO = new DogadjajDAO();
        setTitle("View Events");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 210, 800, 600);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("View Events");
        headerLabel.setFont(new Font("Chilanka", Font.BOLD, 24));
        headerLabel.setForeground(Color.decode("#29be9a")); // Green color
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
        add(buttonPanel, BorderLayout.SOUTH);

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
    private void loadEvents() {
        List<Dogadjaj> events = dogadjajDAO.getFiltered(new ArrayList<>(), "datum", true, start, limit);
        if (events.isEmpty() && start > 0) {
            seeMoreButton.setEnabled(false); // Disable button if no more events
        }

        if (start == 0) {
            eventsPanel.removeAll(); // Clear previous events if starting fresh
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

            eventsPanel.add(eventPanel);
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    public static void main(String[] args) {
        // Example usage with dummy Organizator object
        Organizator org = new Organizator(); // Assumes Organizator has a default constructor
        new ViewEventOrganizator(org);
    }
}
