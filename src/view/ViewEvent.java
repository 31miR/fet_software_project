package view;

import model.Dogadjaj;
import model.DogadjajDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewEvent extends JFrame {
    private DogadjajDAO dogadjajDAO;
    private JPanel eventsPanel;
    private JButton seeMoreButton;
    private int start = 0;
    private final int limit = 5;

    public ViewEvent() {
        dogadjajDAO = new DogadjajDAO();
        setTitle("View Events");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 210, 620, 750);
        setResizable(false);
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

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Load initial events
        loadEvents();
    }

    private void loadEvents() {
        List<Dogadjaj> events = dogadjajDAO.getFiltered(
            null,     // No filters applied
            "datum",  // Replace with your preferred sorting field
            true,     // Sorting direction
            start,
            limit
        );

        if (events.isEmpty() && start > 0) {
            seeMoreButton.setEnabled(false); // Disable button if no more events
        }

        if (start == 0) {
            eventsPanel.removeAll(); // Clear previous events if starting fresh
        }

        for (Dogadjaj event : events) {
            JPanel eventPanel = new JPanel(new GridLayout(1, 2));
            eventPanel.add(new JLabel("Event Name: " + event.getNaziv()));
            eventPanel.add(new JLabel("Date: " + event.getDatum().toString()));
       
            eventsPanel.add(eventPanel);
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    public static void main(String[] args) {
        new ViewEvent();
    }
}
