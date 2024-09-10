package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.Lokacija;
import model.LokacijaDAO;

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
import java.util.List;

public class CreateEvent extends JFrame {
    private JPanel contentPane;
    private JComboBox<Lokacija> locationComboBox;
    private Lokacija selectedLocation;
    private JTextField eventNameField;
    private JTextArea eventDescriptionArea;
    private JSpinner dateSpinner;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton ticketDataButton;
    private JFileChooser fileChooser;
    private Map<String, String[]> eventSubtypesMap;
    private DogadjajDAO dogadjajDAO;
    private Dogadjaj dogadjaj; // Dogadjaj object to be used later
	private Container maxTicketsField;
	private JTextComponent eventImageField;

    public CreateEvent() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 210, 620, 750);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));
        setTitle("Create Event");
        
        eventSubtypesMap = new HashMap<>();
        eventSubtypesMap.put("Muzika", new String[]{"Koncert", "Festival", "Ostalo"});
        eventSubtypesMap.put("Kultura", new String[]{"Izlozba", "Predstava", "Plesna izvedba"});
        eventSubtypesMap.put("Zabava", new String[]{"Zurka", "Stand-up", "Ostalo"});
        eventSubtypesMap.put("Sport", new String[]{"Utakmica", "Mec", "Turnir"});
        eventSubtypesMap.put("Festival", new String[]{"Muzika", "Film", "Hrana i pice"});
        eventSubtypesMap.put("Konferencija", new String[]{"Poslovna", "Edukativna", "Ostalo"});
        eventSubtypesMap.put("Workshop", new String[]{"Tehnicki", "Kreativni", "Ostalo"});
        eventSubtypesMap.put("Film",new String []{"Romantika", "Komedija", "Horor","Akcija","Fantazija"});
        eventSubtypesMap.put("Ostalo", new String[]{"Drugo"});

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

        // Event Description Label and Field
        JLabel lblEventDescription = new JLabel("Description:");
        lblEventDescription.setForeground(Color.BLACK);
        lblEventDescription.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventDescription.setBounds(30, 80, 150, 30);
        contentPane.add(lblEventDescription);

        JTextArea eventDescriptionArea = new JTextArea();
        eventDescriptionArea.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventDescriptionArea.setWrapStyleWord(true); 
        eventDescriptionArea.setLineWrap(true); 

        JScrollPane scrollPane = new JScrollPane(eventDescriptionArea);
        scrollPane.setBounds(200, 80, 350, 70); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        contentPane.add(scrollPane);
        
        
        
        // Date Label and Spinner
        JLabel lblEventDate = new JLabel("Event Date:");
        lblEventDate.setForeground(Color.BLACK);
        lblEventDate.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventDate.setBounds(30, 220, 150, 30);
        contentPane.add(lblEventDate);

        Calendar calendar = Calendar.getInstance();
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-mm-dd hh:mm:ss");
        dateSpinner.setEditor(editor);
        dateSpinner.setFont(new Font("Chilanka", Font.PLAIN, 18));
        dateSpinner.setBounds(200, 220, 210, 30);
        contentPane.add(dateSpinner);

        // Location Label and ComboBox
        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setForeground(Color.BLACK);
        lblLocation.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblLocation.setBounds(30, 170, 150, 30);
        contentPane.add(lblLocation);

        LokacijaDAO lokacijaDAO = new LokacijaDAO();
        List<Lokacija> locations = lokacijaDAO.getAllLocations();
        locationComboBox = new JComboBox<>(locations.toArray(new Lokacija[0]));
        locationComboBox.setRenderer(new LocationRenderer());
        locationComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        locationComboBox.setBounds(200, 170, 350, 30);
        contentPane.add(locationComboBox);

        locationComboBox.addActionListener(e -> selectedLocation = (Lokacija) locationComboBox.getSelectedItem());
        fileChooser = new JFileChooser();
        // Event Type and Subtype
        JLabel lblEventType = new JLabel("Event Type:");
        lblEventType.setForeground(Color.BLACK);
        lblEventType.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventType.setBounds(30, 270, 150, 30);
        contentPane.add(lblEventType);

        JComboBox<String> eventTypeComboBox = new JComboBox<>(new String[]{"Muzika","Kultura","Zabava","Sport", "Festival", "Konferencija", "Workshop","Film","Ostalo"});
        eventTypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventTypeComboBox.setBounds(200, 270, 350, 30);
        contentPane.add(eventTypeComboBox);

        JLabel lblEventSubtype = new JLabel("Event Subtype:");
        lblEventSubtype.setForeground(Color.BLACK);
        lblEventSubtype.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventSubtype.setBounds(30, 320, 150, 30);
        contentPane.add(lblEventSubtype);

        JComboBox<String> eventSubtypeComboBox = new JComboBox<>(new String[]{"Koncert", "Predstava", "Izlozba", "Plesna izvedba","Utakmica","Mec","Drugo"});
        eventSubtypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventSubtypeComboBox.setBounds(200, 320, 350, 30);
        contentPane.add(eventSubtypeComboBox);

        eventTypeComboBox.addActionListener(e -> {
            String selectedType = (String) eventTypeComboBox.getSelectedItem();
            String[] subtypes = eventSubtypesMap.get(selectedType);

            eventSubtypeComboBox.removeAllItems();
            if (subtypes != null) {
                for (String subtype : subtypes) {
                    eventSubtypeComboBox.addItem(subtype);
                }
            }
        });
        
        // Image Field and Browse Button
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
        browseButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif"));
            int returnValue = fileChooser.showOpenDialog(CreateEvent.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                eventImageField.setText(selectedFile.getAbsolutePath());
            }
        });
        
        JLabel lblMaxTickets = new JLabel("Max Tickets:");
        lblMaxTickets.setForeground(Color.BLACK);
        lblMaxTickets.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblMaxTickets.setBounds(30, 460, 150, 30);
        contentPane.add(lblMaxTickets);

        maxTicketsField = new JTextField();
        maxTicketsField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        maxTicketsField.setBounds(200, 460, 350, 30);
        contentPane.add(maxTicketsField);
    
        contentPane.add(browseButton);

        ticketDataButton = new JButton("Add Tickets");
        ticketDataButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        ticketDataButton.setBounds(220, 550, 200, 35);
        ticketDataButton.addActionListener(e -> {
            if (selectedLocation != null) {
                dogadjaj = new Dogadjaj();
                dogadjaj.setNaziv(eventNameField.getText());
                dogadjaj.setOpis(eventDescriptionArea.getText());
                dogadjaj.setDatum((Date) dateSpinner.getValue());
                dogadjaj.setLokacija(selectedLocation);
                try {
                    dogadjaj.setSlika(saveEventImage());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(this, "Error saving image: " + e1.getMessage());
                }

                AddTickets dialog = new AddTickets(CreateEvent.this, dogadjaj, selectedLocation);
                dialog.setVisible(true);
                if (dialog.isTicketsAdded()) {
                    saveButton.setEnabled(true); // Enable save button if tickets are added
                }
            } else {
                JOptionPane.showMessageDialog(CreateEvent.this, "Please select a location first.");
            }
        });
        contentPane.add(ticketDataButton);
        

        saveButton = new JButton("Save");
        saveButton.setBounds(220, 650, 120, 35);
        saveButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        saveButton.setEnabled(false); // Initially disabled
        saveButton.addActionListener(e -> saveEvent());
        contentPane.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        cancelButton.setBounds(350, 650, 120, 35);
        cancelButton.addActionListener(e -> dispose());
        contentPane.add(cancelButton);

        // Initialize DogadjajDAO
        dogadjajDAO = new DogadjajDAO();
    }

    private void saveEvent() {
        if (dogadjaj == null) {
            JOptionPane.showMessageDialog(this, "No event data to save.");
            return;
        }

        try {
            // Save the Dogadjaj object
            dogadjajDAO.addDogadjaj(dogadjaj);
            JOptionPane.showMessageDialog(this, "Event created successfully!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving event: " + e.getMessage());
        }
    }

    private String saveEventImage() throws IOException {
        File source = fileChooser.getSelectedFile();
        String fileName = "event_" + selectedLocation.getLokacija_id() + "_" + source.getName();
        File destination = new File("event_images/" + fileName);
        Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    private class LocationRenderer extends JLabel implements ListCellRenderer<Lokacija> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Lokacija> list, Lokacija value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.getNaziv());
            return this;
        }
    }
}
