package view;

import model.DogadjajDAO;
import model.Organizator;
import model.Dogadjaj;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class FinishedEventOrganizator extends JDialog {

    private DogadjajDAO dogadjajDAO;
    private JPanel eventsPanel;
    private JButton seeMoreButton;
    private int start = 0;
    private final int limit = 5;
    private Organizator organizator;

    public FinishedEventOrganizator(Frame owner,Organizator organizator) {
    	super(owner, "View Finished Events", true);  // Modal dialog with title
        this.organizator = organizator;
        dogadjajDAO = new DogadjajDAO();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
       
        

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("View Finished Events");
        headerLabel.setFont(new Font("Chilanka", Font.BOLD, 24));
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
        seeMoreButton.addActionListener(e -> {
            start += limit;
            loadEvents();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(seeMoreButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load initial events before showing dialog
        loadEvents();

        pack();
        setLocationRelativeTo(owner);  // Center relative to owner window
        setVisible(true);  //
    }

    private void loadEvents() {
        List<Dogadjaj> allEvents = organizator.getDogadjaj();

        // Filter out only finished events
        List<Dogadjaj> finishedEvents = allEvents.stream()
            .filter(Dogadjaj::isZavrsio)
            .collect(Collectors.toList());

        if (start == 0) {
            eventsPanel.removeAll(); // Clear previous events if starting from the beginning
        }

        // Determine end index for pagination
        int end = Math.min(start + limit, finishedEvents.size());

        for (int i = start; i < end; i++) {
            Dogadjaj event = finishedEvents.get(i);

            JPanel eventPanel = new JPanel(new GridBagLayout());
            eventPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            eventPanel.setBackground(new Color(240, 240, 240));
            eventPanel.setMaximumSize(new Dimension(780, 400)); // Ensure panel width fits within the window

            GridBagConstraints eventGbc = new GridBagConstraints();
            eventGbc.insets = new Insets(5, 5, 5, 5);

            // Event Name - Left side
            JTextArea nameTextArea = new JTextArea("Event Name: " + event.getNaziv());
            nameTextArea.setFont(new Font("Arial", Font.BOLD, 14));
            nameTextArea.setLineWrap(true);
            nameTextArea.setWrapStyleWord(true);
            nameTextArea.setEditable(false);
            nameTextArea.setBackground(new Color(240, 240, 240));
            nameTextArea.setPreferredSize(new Dimension(350, 30));
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
            descTextArea.setPreferredSize(new Dimension(350, 50));
            descTextArea.setBorder(null);
            eventGbc.gridy = 1;
            eventPanel.add(descTextArea, eventGbc);

            // Event Date - Right side
            JLabel dateLabel = new JLabel("Date: " + event.getDatum().toString());
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            dateLabel.setPreferredSize(new Dimension(350, 25));
            eventGbc.gridx = 1;
            eventGbc.gridy = 0;
            eventGbc.anchor = GridBagConstraints.EAST;
            eventGbc.weightx = 0.5;
            eventGbc.insets = new Insets(5, 5, 5, 15);
            eventPanel.add(dateLabel, eventGbc);

            ImageIcon imageIcon = new ImageIcon(event.getSlika());
    		Image image = imageIcon.getImage();
    		Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setPreferredSize(new Dimension(150, 150)); // Fixed size for the image
            eventGbc.gridy = 1;
            eventGbc.insets = new Insets(5, 5, 5, 15);
            eventPanel.add(imageLabel, eventGbc);

            eventsPanel.add(eventPanel);
            eventsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        // Update the start position for the next batch of events
        if (end >= finishedEvents.size()) {
            seeMoreButton.setEnabled(false);
        } else {
            seeMoreButton.setEnabled(true);
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

}
