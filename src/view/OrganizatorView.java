package view;

import javax.swing.*;
import model.Organizator;
import model.Dogadjaj;

import java.awt.*;
import java.util.List;

public class OrganizatorView extends JFrame {

    private static final long serialVersionUID = 1L;
    private Organizator organizator;
    private JPanel contentPane;

    public OrganizatorView(Organizator organizator) {
        this.organizator = organizator;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));
        setTitle("Organizator View - " + organizator.getUsername());

        // Image Label
        ImageIcon imageIcon = new ImageIcon("resources/logo.png"); 
        Image image = imageIcon.getImage(); 
        Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH); 
        imageIcon = new ImageIcon(scaledImage); 
        JLabel label = new JLabel(imageIcon);
        label.setBounds(15, 140, 400, 300);
        contentPane.add(label);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Welcome Organizator, " + organizator.getName() + "!");
        lblWelcome.setForeground(new Color(95, 95, 95)); 
        lblWelcome.setFont(new Font("Chilanka", Font.ITALIC, 46));
        lblWelcome.setBounds(250, 12, 1000, 95);
        contentPane.add(lblWelcome);

        // Create Event Button
        JButton createEventButton = new JButton("Create Event");
        createEventButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        createEventButton.setBounds(500, 120, 200, 50);
        createEventButton.setForeground(new Color(51, 51, 51));
        createEventButton.setBackground(Color.decode("#f3f7f8"));
        createEventButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        createEventButton.addActionListener(e -> {
            CreateEvent createEvent = new CreateEvent();
            createEvent.setVisible(true);
        });
        contentPane.add(createEventButton);

        // View Events Button
        JButton viewEventsButton = new JButton("View Events");
        viewEventsButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        viewEventsButton.setBounds(500, 220, 200, 50);
        viewEventsButton.setForeground(new Color(51, 51, 51));
        viewEventsButton.setBackground(Color.decode("#f3f7f8"));
        viewEventsButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        viewEventsButton.addActionListener(e -> {

            List<Dogadjaj> events = organizator.getDogadjaj();  // Retrieve the list of events associated with the organizer
            ViewEventOrganizator selectEventWindow = new ViewEventOrganizator(organizator);
            selectEventWindow.setVisible(true);

        });
        contentPane.add(viewEventsButton);

        // Edit Event Button
        JButton editEventButton = new JButton("Edit Event");
        editEventButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        editEventButton.setBounds(500, 320, 200, 50);
        editEventButton.setForeground(new Color(51, 51, 51));
        editEventButton.setBackground(Color.decode("#f3f7f8"));
        editEventButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        editEventButton.addActionListener(e -> {
            List<Dogadjaj> events = organizator.getDogadjaj();  // Retrieve the list of events associated with the organizer
            SelectEventWindow selectEventWindow = new SelectEventWindow(organizator);
            selectEventWindow.setVisible(true);
        });
        contentPane.add(editEventButton);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        logoutButton.setBounds(500, 420, 200, 50);
        logoutButton.setForeground(new Color(51, 51, 51));
        logoutButton.setBackground(Color.decode("#f3f7f8"));
        logoutButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        logoutButton.addActionListener(e -> {
            Login loginView = new Login();
            loginView.setVisible(true);
            dispose();
        });
        contentPane.add(logoutButton);
    }
}
