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
        setBounds(450, 190, 1014, 650);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));
        setTitle("Karta");

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
        createEventButton.setBounds(500, 120, 270, 50);
        createEventButton.setForeground(new Color(51, 51, 51));
        createEventButton.setBackground(Color.decode("#f3f7f8"));
        createEventButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        createEventButton.addActionListener(e -> {
            CreateEvent createEvent = new CreateEvent();
            createEvent.setVisible(true);
        });
        contentPane.add(createEventButton);

        // View Active Events Button (previously "View Events")
        JButton viewActiveEventsButton = new JButton("View Active Events");
        viewActiveEventsButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        viewActiveEventsButton.setBounds(500, 220, 270, 50);
        viewActiveEventsButton.setForeground(new Color(51, 51, 51));
        viewActiveEventsButton.setBackground(Color.decode("#f3f7f8"));
        viewActiveEventsButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        viewActiveEventsButton.addActionListener(e -> {
            List<Dogadjaj> events = organizator.getDogadjaj();  // Retrieve the list of events associated with the organizer
            ViewEventOrganizator selectEventWindow = new ViewEventOrganizator(organizator);
            selectEventWindow.setVisible(true);
        });
        contentPane.add(viewActiveEventsButton);
       
        // View Finished Events Button
        JButton viewFinishedEventsButton = new JButton("View Finished Events");
        viewFinishedEventsButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        viewFinishedEventsButton.setBounds(500, 320, 270, 50);
        viewFinishedEventsButton.setForeground(new Color(51, 51, 51));
        viewFinishedEventsButton.setBackground(Color.decode("#f3f7f8"));
        viewFinishedEventsButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        viewFinishedEventsButton.addActionListener(e -> {
            // Code to display finished events goes here
            List<Dogadjaj> finishedEvents = organizator.getDogadjaj();  // You may need to filter this list to show only finished events
           FinishedEventOrganizator viewFinishedEventsWindow = new FinishedEventOrganizator(organizator);
              viewFinishedEventsWindow.setVisible(true);
        });
        contentPane.add(viewFinishedEventsButton);

        // Edit Event Button
        JButton editEventButton = new JButton("Edit Event");
        editEventButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        editEventButton.setBounds(500, 420, 270, 50);
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
        logoutButton.setBounds(500, 520, 270, 50);
        logoutButton.setForeground(new Color(51, 51, 51));
        logoutButton.setBackground(Color.decode("#f3f7f8"));
        logoutButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        logoutButton.addActionListener(e -> {
            Login loginView = new Login();
            loginView.setVisible(true);
            dispose();
        });
        contentPane.add(logoutButton);
        
        //Profile settings
        JButton profileButton = new JButton("Profile settings");
        profileButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        profileButton.setBounds(780, 120, 200, 50);
        profileButton.setForeground(new Color(51, 51, 51));
        profileButton.setBackground(Color.decode("#f3f7f8"));
        profileButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        profileButton.addActionListener(e -> {
         OrganizatorProfileSettings settings = new OrganizatorProfileSettings(organizator);
         settings.setVisible(true);
         });
        contentPane.add(profileButton);
    }
}
