package view;

import model.DogadjajDAO;
import model.Organizator;
import model.Dogadjaj;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;


//View za prikaz aktivnih dogadjaja organizatora
public class ViewEventOrganizator extends JFrame {
    private DogadjajDAO dogadjajDAO;
    private JPanel eventsPanel;
    private JButton seeMoreButton;
    private int start = 0;
    private final int limit = 5;
    private Organizator organizator;

    public ViewEventOrganizator(Organizator organizator) {
        this.organizator = organizator;  // Set the organizer
        dogadjajDAO = new DogadjajDAO();
        setTitle("Karta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 210, 800, 600);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("View Active Events");
        headerLabel.setFont(new Font("Chilanka", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Events Panel
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrolling
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
        backButton.addActionListener(e -> dispose());
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Load initial events
        loadEvents();
    }

    private void loadEvents() {
        List<Dogadjaj> allEvents = organizator.getDogadjaj();

        // Filter out only active events
        List<Dogadjaj> activeEvents = allEvents.stream()
            .filter(event -> !event.isZavrsio())
            .collect(Collectors.toList());

        if (activeEvents.isEmpty() && start > 0) {
            seeMoreButton.setEnabled(false); // Disable button if no more events
        }

        if (start == 0) {
            eventsPanel.removeAll(); // Clear previous events if starting from the beginning
        }

        // Determine end index for pagination
        int end = Math.min(start + limit, activeEvents.size());

        for (int i = start; i < end; i++) {
            Dogadjaj event = activeEvents.get(i);

            JPanel eventPanel = new JPanel(new GridBagLayout());
            eventPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            eventPanel.setBackground(new Color(240, 240, 240));
            eventPanel.setMaximumSize(new Dimension(780, 120)); // Ensure panel width fits within the window

            GridBagConstraints eventGbc = new GridBagConstraints();
            eventGbc.insets = new Insets(5, 5, 5, 5);

            // Event Name - Left side
            JTextArea nameTextArea = new JTextArea("Event Name: " + event.getNaziv());
            nameTextArea.setFont(new Font("Arial", Font.BOLD, 14));
            nameTextArea.setLineWrap(true);
            nameTextArea.setWrapStyleWord(true);
            nameTextArea.setEditable(false);
            nameTextArea.setBackground(new Color(240, 240, 240));
            nameTextArea.setPreferredSize(new Dimension(350, 30)); // Adjust height if needed
            nameTextArea.setBorder(null);
            eventGbc.gridx = 0;
            eventGbc.gridy = 0;
            eventGbc.gridwidth = 1;
            eventGbc.anchor = GridBagConstraints.WEST;
            eventGbc.weightx = 0.5;
            eventPanel.add(nameTextArea, eventGbc);

            // Event Description - Left side
            JTextArea descTextArea = new JTextArea("Description: " + event.getOpis());
            descTextArea.setFont(new Font("Arial", Font.PLAIN, 13));
            descTextArea.setLineWrap(true);
            descTextArea.setWrapStyleWord(true);
            descTextArea.setEditable(false);
            descTextArea.setBackground(new Color(240, 240, 240));
            descTextArea.setPreferredSize(new Dimension(350, 50)); // Adjust height if needed
            descTextArea.setBorder(null);
            eventGbc.gridy = 1;
            eventPanel.add(descTextArea, eventGbc);

            // Event Date - Right side
            JLabel dateLabel = new JLabel("Date: " + event.getDatum().toString());
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            dateLabel.setPreferredSize(new Dimension(350, 25)); // Fixed width
            eventGbc.gridx = 1;
            eventGbc.gridy = 0;
            eventGbc.anchor = GridBagConstraints.EAST;
            eventGbc.weightx = 0.5;
            eventGbc.insets = new Insets(5, 5, 5, 15); // Add extra space to the right
            eventPanel.add(dateLabel, eventGbc);

            // Location Image - Right side
            JLabel imageLabel = new JLabel(new ImageIcon(event.getSlika()));
            imageLabel.setPreferredSize(new Dimension(350, 50)); // Fixed size for the image
            eventGbc.gridy = 1;
            eventGbc.insets = new Insets(5, 5, 5, 15); // Add extra space to the right
            eventPanel.add(imageLabel, eventGbc);

            eventsPanel.add(eventPanel);
            eventsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        // Update the start position for the next batch of events
        if (end >= activeEvents.size()) {
            seeMoreButton.setEnabled(false); // Disable button if no more events
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }
}
