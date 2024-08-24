package view;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.Lokacija;
import model.LokacijaDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditEvent extends JFrame {
    private static final long serialVersionUID = 1L;
    private Dogadjaj event;
    private JPanel contentPane;
    private JTextField eventNameField;
    private JTextArea eventDescriptionArea;
    private JSpinner dateSpinner;
    private JComboBox<String> eventTypeComboBox;
    private JComboBox<String> eventSubtypeComboBox;
    private JTextField eventImageField;
    private JFileChooser fileChooser;
    private JComboBox<Lokacija> locationComboBox;
    private JTextField maxTicketsField;
    private JCheckBox paymentOnRegistrationCheckBox;
    private JComboBox<String> eventFinishedComboBox;
    private Map<String, String[]> eventSubtypesMap;

    public EditEvent(Dogadjaj event) {
        this.event = event;
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 210, 620, 750);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));
        setTitle("Edit Event");

        // Initialize subtype map
        eventSubtypesMap = new HashMap<>();
        eventSubtypesMap.put("Muzika", new String[]{"Koncert", "Festival", "Ostalo"});
        eventSubtypesMap.put("Kultura", new String[]{"Izložba", "Pozorište", "Ostalo"});
        eventSubtypesMap.put("Sport", new String[]{"Utakmica", "Trka", "Ostalo"});

        // Event Name
        JLabel lblEventName = new JLabel("Event Name:");
        lblEventName.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventName.setBounds(20, 20, 150, 30);
        contentPane.add(lblEventName);

        eventNameField = new JTextField(event.getNaziv());
        eventNameField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventNameField.setBounds(180, 20, 400, 30);
        contentPane.add(eventNameField);

        // Event Description
        JLabel lblEventDescription = new JLabel("Event Description:");
        lblEventDescription.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventDescription.setBounds(20, 60, 200, 30);
        contentPane.add(lblEventDescription);

        eventDescriptionArea = new JTextArea(event.getOpis());
        eventDescriptionArea.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventDescriptionArea.setLineWrap(true);
        eventDescriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(eventDescriptionArea);
        descriptionScrollPane.setBounds(20, 100, 560, 100);
        contentPane.add(descriptionScrollPane);

        // Event Date
        JLabel lblEventDate = new JLabel("Event Date:");
        lblEventDate.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventDate.setBounds(20, 220, 150, 30);
        contentPane.add(lblEventDate);

        dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        dateSpinner.setFont(new Font("Chilanka", Font.PLAIN, 18));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy HH:mm");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setBounds(180, 220, 200, 30);
        contentPane.add(dateSpinner);

        // Event Type
        JLabel lblEventType = new JLabel("Event Type:");
        lblEventType.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventType.setBounds(20, 260, 150, 30);
        contentPane.add(lblEventType);

        eventTypeComboBox = new JComboBox<>(new String[]{"Muzika", "Kultura", "Sport"});
        eventTypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventTypeComboBox.setBounds(180, 260, 200, 30);
        eventTypeComboBox.addActionListener(e -> updateEventSubtypes());
        contentPane.add(eventTypeComboBox);

        // Event Subtype
        JLabel lblEventSubtype = new JLabel("Event Subtype:");
        lblEventSubtype.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventSubtype.setBounds(20, 300, 150, 30);
        contentPane.add(lblEventSubtype);

        eventSubtypeComboBox = new JComboBox<>();
        eventSubtypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventSubtypeComboBox.setBounds(180, 300, 200, 30);
        contentPane.add(eventSubtypeComboBox);

        // Event Image
        JLabel lblEventImage = new JLabel("Event Image:");
        lblEventImage.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventImage.setBounds(20, 340, 150, 30);
        contentPane.add(lblEventImage);

        eventImageField = new JTextField();
        eventImageField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventImageField.setBounds(180, 340, 400, 30);
        contentPane.add(eventImageField);

        JButton browseButton = new JButton("Browse");
        browseButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        browseButton.setBounds(500, 380, 80, 30);
        browseButton.addActionListener(e -> chooseImageFile());
        contentPane.add(browseButton);

        // Location
        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblLocation.setBounds(20, 380, 150, 30);
        contentPane.add(lblLocation);

        LokacijaDAO lokacijaDAO = new LokacijaDAO();
        java.util.List<Lokacija> locations = lokacijaDAO.getAllLocations();
        locationComboBox = new JComboBox<>(locations.toArray(new Lokacija[0]));
        locationComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        locationComboBox.setBounds(180, 380, 300, 30);
        contentPane.add(locationComboBox);

        // Max Tickets
        JLabel lblMaxTickets = new JLabel("Max Tickets:");
        lblMaxTickets.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblMaxTickets.setBounds(20, 420, 150, 30);
        contentPane.add(lblMaxTickets);

        maxTicketsField = new JTextField(String.valueOf(event.getMaxKartiPoKorisniku()));
        maxTicketsField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        maxTicketsField.setBounds(180, 420, 100, 30);
        contentPane.add(maxTicketsField);

        // Payment on Registration
        paymentOnRegistrationCheckBox = new JCheckBox("Payment on Registration");
        paymentOnRegistrationCheckBox.setFont(new Font("Chilanka", Font.PLAIN, 20));
        paymentOnRegistrationCheckBox.setBounds(20, 460, 300, 30);
        paymentOnRegistrationCheckBox.setSelected(event.isNaplataPriRezervaciji());
        contentPane.add(paymentOnRegistrationCheckBox);

        // Event Finished
        JLabel lblEventFinished = new JLabel("Event Finished:");
        lblEventFinished.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventFinished.setBounds(20, 500, 150, 30);
        contentPane.add(lblEventFinished);

        eventFinishedComboBox = new JComboBox<>(new String[]{"Yes", "No"});
        eventFinishedComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventFinishedComboBox.setBounds(180, 500, 100, 30);
        eventFinishedComboBox.setSelectedItem(event.isZavrsio() ? "Yes" : "No");
        contentPane.add(eventFinishedComboBox);

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Chilanka", Font.PLAIN, 20));
        saveButton.setBounds(420, 620, 150, 50);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEvent();
            }
        });
        contentPane.add(saveButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Chilanka", Font.PLAIN, 20));
        cancelButton.setBounds(250, 620, 150, 50);
        cancelButton.addActionListener(e -> dispose());
        contentPane.add(cancelButton);
    }

    private void updateEventSubtypes() {
        String selectedType = (String) eventTypeComboBox.getSelectedItem();
        String[] subtypes = eventSubtypesMap.get(selectedType);
        eventSubtypeComboBox.setModel(new DefaultComboBoxModel<>(subtypes));
    }

    private void chooseImageFile() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));
        }
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            eventImageField.setText(selectedFile.getAbsolutePath());
            // Optionally copy or move the file to a permanent location
        }
    }

    private void saveEvent() {
        String name = eventNameField.getText();
        String description = eventDescriptionArea.getText();
        Date date = (Date) dateSpinner.getValue();
        String type = (String) eventTypeComboBox.getSelectedItem();
        String subtype = (String) eventSubtypeComboBox.getSelectedItem();
        String imagePath = eventImageField.getText();
        Lokacija location = (Lokacija) locationComboBox.getSelectedItem();
        int maxTickets = Integer.parseInt(maxTicketsField.getText());
        boolean paymentOnRegistration = paymentOnRegistrationCheckBox.isSelected();
        boolean isFinished = "Yes".equals(eventFinishedComboBox.getSelectedItem());

        event.setNaziv(name);
        event.setOpis(description);
        event.setDatum(date);
        event.setVrsta(type);
        event.setPodvrsta(subtype);
        event.setSlika(imagePath);
        event.setLokacija(location);
        event.setMaxKartiPoKorisniku(maxTickets);
        event.setNaplataPriRezervaciji(paymentOnRegistration);
        event.setZavrsio(isFinished);

        // Save changes to database
        DogadjajDAO dogadjajDAO = new DogadjajDAO();
        dogadjajDAO.updateDogadjaj(event);

        JOptionPane.showMessageDialog(this, "Event updated successfully!");
        dispose();
    }
}
