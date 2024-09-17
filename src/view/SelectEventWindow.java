package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Dogadjaj;
import model.IzmjeneDAO;
import model.Organizator;

public class SelectEventWindow extends JDialog {
    private Organizator organizator;
    private JPanel eventsPanel;
    private JButton seeMoreButton;
    private int start = 0;
    private final int limit = 5;
    private IzmjeneDAO izmjeneDAO = new IzmjeneDAO();
    

    public SelectEventWindow(Organizator organizator) {
        this.organizator = organizator;

        setTitle("Select Event to Edit");
        setModal(true);
        setBounds(450, 190, 1014, 597);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel("Select an Event to Edit");
        headerLabel.setFont(new Font("Chilanka", Font.BOLD, 24));
        headerLabel.setForeground(Color.decode("#29be9a"));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Events Panel
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(buttonPanel, BorderLayout.SOUTH);

        // See More Button
        seeMoreButton = new JButton("See More");
        seeMoreButton.addActionListener(e -> {
            start += limit;
            loadEvents();
        });
        buttonPanel.add(seeMoreButton);

        // Load initial events
        loadEvents();
    }

    private void loadEvents() {
        List<Dogadjaj> allEvents = organizator.getDogadjaj().stream()
                .filter(event -> !event.isZavrsio())
                .collect(Collectors.toList());

        // Disable "See More" if all events are loaded
        if (start >= allEvents.size()) {
            seeMoreButton.setEnabled(false);
            return;
        }

        int end = Math.min(start + limit, allEvents.size());

        // Remove previous events on first load
        if (start == 0) {
            eventsPanel.removeAll();
        }

        // Load and display events
        for (int i = start; i < end; i++) {
            Dogadjaj event = allEvents.get(i);
            JPanel eventPanel = new JPanel();
            eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
            eventPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

            JLabel eventNameLabel = new JLabel("<html><b>Event Name:</b> " + event.getNaziv() + "</html>");
            eventNameLabel.setFont(new Font("Chilanka", Font.BOLD, 18));
            JLabel eventDateLabel = new JLabel("<html><b>Date:</b> " + event.getDatum().toString() + "</html>");
            eventDateLabel.setFont(new Font("Chilanka", Font.BOLD, 18));

            eventPanel.add(eventNameLabel);
            eventPanel.add(eventDateLabel);

            JButton editButton = new JButton("Edit Event");
            editButton.addActionListener(e -> {
                EditEvent editEvent = new EditEvent(event);
                editEvent.setVisible(true);
            });
            eventPanel.add(editButton);
            
            JButton finishButton = new JButton("Finish Event");
            finishButton.addActionListener(e -> {
                int response = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to mark this event as finished?",
                        "Confirm Finish Event",
                        JOptionPane.YES_NO_OPTION
                );
                
                if (response == JOptionPane.YES_OPTION) {
                    // Mark the event as finished
                    izmjeneDAO.addChange("Dogadjaj", String.valueOf(event.getDogadjaj_id()), "zavrsio", "true");
                    JOptionPane.showMessageDialog(this, "Event has been marked as finished.");
                    dispose();
                }
            });
                eventPanel.add(finishButton);
            
            eventsPanel.add(eventPanel);
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();

        // If all events are loaded, disable "See More"
        if (end == allEvents.size()) {
            seeMoreButton.setEnabled(false);
        }
    }
}
