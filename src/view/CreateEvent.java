package view;

import model.Dogadjaj;
import model.DogadjajDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import javax.swing.filechooser.FileNameExtensionFilter;


public class CreateEvent extends JFrame {
    private static final long serialVersionUID = 1L;
    private DogadjajDAO dogadjajController;
    private JPanel contentPane;
    private JButton saveButton;
    private JButton cancelButton;
    private JTextField eventImageField;
    private JFileChooser fileChooser;
    private JSpinner dateSpinner;
    private JComboBox<String> eventFinishedComboBox;
    private JButton ticketDataButton;
    private int maxTickets = 0;

    public CreateEvent() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Zatvori samo trenutni prozor
        setBounds(450, 210, 620, 750);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166)); 
        setTitle("Create Event");

        // Event Name Label and Field
        JLabel lblEventName = new JLabel("Event Name:");
        lblEventName.setForeground(Color.BLACK);
        lblEventName.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventName.setBounds(30, 30, 150, 30);
        contentPane.add(lblEventName);

        JTextField eventNameField = new JTextField();
        eventNameField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventNameField.setBounds(200, 30, 350, 30);
        contentPane.add(eventNameField);
        // Date Label and Spinner
        JLabel lblEventDate = new JLabel("Event Date:");
        lblEventDate.setForeground(Color.BLACK);
        lblEventDate.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventDate.setBounds(30, 220, 150, 30);
        contentPane.add(lblEventDate);

        Calendar calendar = Calendar.getInstance();
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        dateSpinner.setFont(new Font("Chilanka", Font.PLAIN, 18));
        dateSpinner.setBounds(200, 220, 150, 30);
        contentPane.add(dateSpinner);



        // Event Description Label and Field
        JLabel lblEventDescription = new JLabel("Description:");
        lblEventDescription.setForeground(Color.BLACK);
        lblEventDescription.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventDescription.setBounds(30, 80, 150, 30);
        contentPane.add(lblEventDescription);

        JTextArea eventDescriptionArea = new JTextArea();
        eventDescriptionArea.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventDescriptionArea.setBounds(200, 80, 350, 70);
        contentPane.add(eventDescriptionArea);
        
        
        // Event Location Label and Field
        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setForeground(Color.BLACK);
        lblLocation.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblLocation.setBounds(30, 170, 150, 30);
        contentPane.add(lblLocation);

        JTextArea eventLocation = new JTextArea();
        eventLocation.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventLocation.setBounds(200, 170, 350, 30);
        contentPane.add(eventLocation);
        
        
        

        // Event Type Label and ComboBox
        JLabel lblEventType = new JLabel("Event Type:");
        lblEventType.setForeground(Color.BLACK);
        lblEventType.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventType.setBounds(30, 270, 150, 30);
        contentPane.add(lblEventType);

        JComboBox<String> eventTypeComboBox = new JComboBox<>(new String[]{"Muzika","Kultura","Zabava","Sport", "Festival", "Konferencija", "Workshop","Ostalo"});
        eventTypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventTypeComboBox.setBounds(200, 270, 350, 30);
        contentPane.add(eventTypeComboBox);

        // Event Subtype Label and ComboBox
        JLabel lblEventSubtype = new JLabel("Event Subtype:");
        lblEventSubtype.setForeground(Color.BLACK);
        lblEventSubtype.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventSubtype.setBounds(30, 320, 150, 30);
        contentPane.add(lblEventSubtype);

        JComboBox<String> eventSubtypeComboBox = new JComboBox<>(new String[]{"Koncert", "Predstava", "Izlozba", "Plesna izvedba","Utakmica","Mec","Drugo"});
        eventSubtypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventSubtypeComboBox.setBounds(200, 320, 350, 30);
        contentPane.add(eventSubtypeComboBox);

        // Event Image Label and Field
        JLabel lblEventImage = new JLabel("Image Location:");
        lblEventImage.setForeground(Color.BLACK);
        lblEventImage.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventImage.setBounds(30, 370, 150, 30);
        contentPane.add(lblEventImage);

        eventImageField = new JTextField();
        eventImageField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventImageField.setBounds(200, 370, 250, 30);
        eventImageField.setEditable(false);
        contentPane.add(eventImageField);

        JButton browseButton = new JButton("Browse");
        browseButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        browseButton.setBounds(470, 370, 80, 30);
        browseButton.setForeground(new Color(51, 51, 51));
        browseButton.setBackground(Color.decode("#f3f7f8"));
        browseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif"));
                int returnValue = fileChooser.showOpenDialog(CreateEvent.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    eventImageField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        contentPane.add(browseButton);

        // Max Tickets Per User Label and Field
        JLabel lblMaxTickets = new JLabel("Max Tickets:");
        lblMaxTickets.setForeground(Color.BLACK);
        lblMaxTickets.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblMaxTickets.setBounds(30, 420, 150, 30);
        contentPane.add(lblMaxTickets);

        JTextField maxTicketsField = new JTextField();
        maxTicketsField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        maxTicketsField.setBounds(200, 420, 350, 30);
        contentPane.add(maxTicketsField);

        // Payment on Registration Checkbox
        JCheckBox paymentOnRegistrationCheckBox = new JCheckBox("Payment on Registration");
        paymentOnRegistrationCheckBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        paymentOnRegistrationCheckBox.setBackground(new Color(29, 190, 166));
        paymentOnRegistrationCheckBox.setBounds(30, 520, 300, 30);
        contentPane.add(paymentOnRegistrationCheckBox);

        // Event Approved Checkbox
      //  JCheckBox eventApprovedCheckBox = new JCheckBox("Event Approved");
        //eventApprovedCheckBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        //eventApprovedCheckBox.setBackground(new Color(29, 190, 166));
        //eventApprovedCheckBox.setBounds(30, 570, 200, 30);
        //contentPane.add(eventApprovedCheckBox);
        
        // Event Finished ComboBox
        JLabel lblEventFinished = new JLabel("Event Finished:");
        lblEventFinished.setForeground(Color.BLACK);
        lblEventFinished.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventFinished.setBounds(30, 470, 200, 30);
        contentPane.add(lblEventFinished);

        String[] finishedOptions = {"Yes", "No"};
        eventFinishedComboBox = new JComboBox<>(finishedOptions);
        eventFinishedComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventFinishedComboBox.setBounds(250, 470, 100, 30);
        contentPane.add(eventFinishedComboBox);

        // Add Tickets Button
        ticketDataButton = new JButton("Add Tickets");
        ticketDataButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        ticketDataButton.setBounds(30, 570, 520, 40);
        ticketDataButton.setForeground(new Color(51, 51, 51));
        ticketDataButton.setBackground(Color.decode("#f3f7f8"));
        ticketDataButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        ticketDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddTickets(null).setVisible(true); // Replace null with the Dogadjaj instance if available
            }
        });
        contentPane.add(ticketDataButton);
        
        
        // Save Button
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        saveButton.setBounds(200, 650, 150, 40);
        saveButton.setForeground(new Color(51, 51, 51));
        saveButton.setBackground(Color.decode("#f3f7f8"));
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 if (eventNameField.getText().trim().isEmpty() || 
            	            eventDescriptionArea.getText().trim().isEmpty() || 
            	            maxTicketsField.getText().trim().isEmpty() || 
            	            eventTypeComboBox.getSelectedItem() == null || 
            	            eventSubtypeComboBox.getSelectedItem() == null) {
            	            
            	            JOptionPane.showMessageDialog(CreateEvent.this, 
            	                                          "All fields must be filled.", 
            	                                          "Validation Error", 
            	                                          JOptionPane.ERROR_MESSAGE);
            	            return; // Stop the save process
            	        }
            	
            	DogadjajDAO dao = new DogadjajDAO();
                Dogadjaj dogadjaj = new Dogadjaj();
                dogadjaj.setNaziv(eventNameField.getText());
                dogadjaj.setVrsta(eventTypeComboBox.getSelectedItem().toString());
                dogadjaj.setPodvrsta(eventSubtypeComboBox.getSelectedItem().toString());
                dogadjaj.setDatum(new java.sql.Date(((Date) dateSpinner.getValue()).getTime()));
                dogadjaj.setSlika(eventImageField.getText());
                dogadjaj.setNaplataPriRegistraciji(paymentOnRegistrationCheckBox.isSelected());
                dogadjaj.setMaxKartiPoKorisniku(Integer.parseInt(maxTicketsField.getText()));
                dogadjaj.setZavrsio(eventFinishedComboBox.getSelectedItem().toString().equals("Yes"));
              //  dogadjaj.setDogadjajApproved(eventApprovedCheckBox.isSelected());
             //   dogadjaj.setLokacija(eventLocation).getLokacija();
                dao.addDogadjaj(dogadjaj);
                JOptionPane.showMessageDialog(CreateEvent.this, "Event saved.");
                dispose(); // Close the window after saving
            }
        });
        contentPane.add(saveButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        cancelButton.setBounds(400, 650, 150, 40);
        cancelButton.setForeground(new Color(51, 51, 51));
        cancelButton.setBackground(Color.decode("#f3f7f8"));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window when Cancel is clicked
            }
        });
        contentPane.add(cancelButton);
    }

    public static void main(String[] args) {
        new CreateEvent().setVisible(true);
    }
}
