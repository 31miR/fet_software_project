package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.KartaDAO;
import model.Lokacija;
import model.LokacijaDAO;
import model.Organizator;

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
import java.util.UUID;
import java.util.List;

public class CreateEvent extends JDialog {
    private JPanel contentPane;
    private JComboBox<Lokacija> locationComboBox;
    private Lokacija selectedLocation;
    private JTextField eventNameField;
    private JTextArea eventDescriptionArea;
    private JSpinner dateSpinner;
    private JButton saveButton;
    private JButton cancelButton;
    private JFileChooser fileChooser;
    private Map<String, String[]> eventSubtypesMap;
    private DogadjajDAO dogadjajDAO = new DogadjajDAO();
    private KartaDAO kartaDAO = new KartaDAO();
    private Dogadjaj dogadjaj; // Dogadjaj object to be used later
	private JTextField maxTicketsField;
	private JTextComponent eventImageField;
	private JCheckBox paymentOnRegistrationCheckBox;
	private Organizator organizator;
	

	    public CreateEvent(Organizator organizator) {
	    	this.organizator = organizator;
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
	        eventSubtypesMap.put("Film", new String[]{"Romantika", "Komedija", "Horor", "Akcija", "Fantazija"});
	        eventSubtypesMap.put("Ostalo", new String[]{"Drugo"});

	        // Event Name Label and Field
	        JLabel lblEventName = new JLabel("Event Name:");
	        lblEventName.setForeground(Color.BLACK);
	        lblEventName.setFont(new Font("Chilanka", Font.PLAIN, 18));
	        lblEventName.setBounds(30, 30, 150, 30);
	        contentPane.add(lblEventName);

	        eventNameField = new JTextField();
	        eventNameField.setFont(new Font("Chilanka", Font.PLAIN, 18));
	        eventNameField.setBounds(200, 30, 350, 30);
	        contentPane.add(eventNameField);

	        // Event Description Label and Field
	        JLabel lblEventDescription = new JLabel("Description:");
	        lblEventDescription.setForeground(Color.BLACK);
	        lblEventDescription.setFont(new Font("Chilanka", Font.PLAIN, 18));
	        lblEventDescription.setBounds(30, 80, 150, 30);
	        contentPane.add(lblEventDescription);

	        eventDescriptionArea = new JTextArea();
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
	        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd hh:mm:ss");
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

	        JComboBox<String> eventTypeComboBox = new JComboBox<>(new String[]{"Muzika", "Kultura", "Zabava", "Sport", "Festival", "Konferencija", "Workshop", "Film", "Ostalo"});
	        eventTypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
	        eventTypeComboBox.setBounds(200, 270, 350, 30);
	        contentPane.add(eventTypeComboBox);

	        JLabel lblEventSubtype = new JLabel("Event Subtype:");
	        lblEventSubtype.setForeground(Color.BLACK);
	        lblEventSubtype.setFont(new Font("Chilanka", Font.PLAIN, 18));
	        lblEventSubtype.setBounds(30, 320, 150, 30);
	        contentPane.add(lblEventSubtype);

	        JComboBox<String> eventSubtypeComboBox = new JComboBox<>(new String[]{"Koncert", "Predstava", "Izlozba", "Plesna izvedba", "Utakmica", "Mec", "Drugo"});
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

	        // Payment on Registration
	        paymentOnRegistrationCheckBox = new JCheckBox("Payment on Registration");
	        paymentOnRegistrationCheckBox.setFont(new Font("Chilanka", Font.PLAIN, 20));
	        paymentOnRegistrationCheckBox.setBounds(30, 430, 300, 30);
	        contentPane.add(paymentOnRegistrationCheckBox);

	        // Image Field and Browse Button
	        JLabel lblEventImage = new JLabel("Event Image:");
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
	            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
	            int result = fileChooser.showOpenDialog(CreateEvent.this);
	            if (result == JFileChooser.APPROVE_OPTION) {
	                File file = fileChooser.getSelectedFile();
	                eventImageField.setText(file.getAbsolutePath());
	            }
	        });
	        contentPane.add(browseButton);

	        // Max Tickets
	        JLabel lblMaxTickets = new JLabel("Max Tickets per User:");
	        lblMaxTickets.setForeground(Color.BLACK);
	        lblMaxTickets.setFont(new Font("Chilanka", Font.PLAIN, 18));
	        lblMaxTickets.setBounds(30, 480, 200, 30);
	        contentPane.add(lblMaxTickets);

	        maxTicketsField = new JTextField();
	        maxTicketsField.setFont(new Font("Chilanka", Font.PLAIN, 18));
	        maxTicketsField.setBounds(250, 480, 100, 30);
	        contentPane.add(maxTicketsField);

	        // Buttons
	        saveButton = new JButton("Save Event");
	        saveButton.setFont(new Font("Chilanka", Font.PLAIN, 20));
	        saveButton.setBounds(60, 600, 200, 40);
	        contentPane.add(saveButton);

	        cancelButton = new JButton("Cancel");
	        cancelButton.setFont(new Font("Chilanka", Font.PLAIN, 20));
	        cancelButton.setBounds(300, 600, 150, 40);
	        contentPane.add(cancelButton);

	        // Button Action Listeners
	        cancelButton.addActionListener(e -> dispose());

	        saveButton.addActionListener(e -> {
	            if (validateFields()) {
	                try {
	                    String imagePath = saveEventImage();
	                    dogadjaj = new Dogadjaj();
	                    dogadjaj.setDatum((Date) dateSpinner.getValue());
	                    dogadjaj.setOpis(eventDescriptionArea.getText().trim());
	                    dogadjaj.setLokacija((Lokacija) locationComboBox.getSelectedItem());
	                    dogadjaj.setMaxKartiPoKorisniku(Integer.parseInt(maxTicketsField.getText().trim()));
	                    dogadjaj.setNaplataPriRezervaciji(paymentOnRegistrationCheckBox.isSelected());
	                    dogadjaj.setVrsta((String) eventTypeComboBox.getSelectedItem());
	                    dogadjaj.setPodvrsta((String) eventSubtypeComboBox.getSelectedItem());
	                    dogadjaj.setNaziv(eventNameField.getText().trim());
	                    dogadjaj.setOrganizator(organizator);

	                    if (imagePath != null) {
	                        dogadjaj.setSlika(imagePath);
	                    }

	                    // Save the event using DogadjajDAO
	                    dogadjajDAO.addDogadjaj(dogadjaj);
	                    
	                    //THIS IS EXPERIMENTAL
	                    AddTickets dialog = new AddTickets( dogadjaj, selectedLocation);
	                    dialog.setVisible(true);
	                    //THIS IS THE END
	                    
	                    JOptionPane.showMessageDialog(CreateEvent.this, "Event saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	                    dispose();
	                } catch (IOException e1) {
	                	e1.printStackTrace();
	                    JOptionPane.showMessageDialog(CreateEvent.this, "Error saving image: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                    dogadjajDAO.updateDogadjaj(dogadjaj);
	                    dogadjajDAO.deleteDogadjaj(dogadjaj);
	                    kartaDAO.deleteFreeTicketsForDogadjaj(dogadjaj);
	                }
	            }
	        });
	    }

	    private boolean validateFields() {
	        String newName = eventNameField.getText().trim();
	        String newDescription = eventDescriptionArea.getText().trim();
	        Date newDate = (Date) dateSpinner.getValue();
	        Lokacija newLocation = (Lokacija) locationComboBox.getSelectedItem();
	        int newMaxTickets;
	        
	        // Check if all required fields are filled
	        if (newName.isEmpty() || newDescription.isEmpty()) {
	            JOptionPane.showMessageDialog(CreateEvent.this, "Please fill out the event name and description.", "Warning", JOptionPane.WARNING_MESSAGE);
	            return false;
	        }

	        // Check if location is selected
	        if (newLocation == null) {
	            JOptionPane.showMessageDialog(CreateEvent.this, "Please select a location.", "Warning", JOptionPane.WARNING_MESSAGE);
	            return false;
	        }

	        // Check if a valid number of maximum tickets is provided
	        try {
	            newMaxTickets = Integer.parseInt(maxTicketsField.getText().trim());
	            if (newMaxTickets <= 0) {
	                throw new NumberFormatException();
	            }
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(CreateEvent.this, "Please enter a valid positive number for the maximum tickets per user.", "Warning", JOptionPane.WARNING_MESSAGE);
	            return false;
	        }

	        return true;
	    }

	    private String saveEventImage() throws IOException {
	        if (eventImageField.getText().trim().isEmpty()) {
	            return null;
	        }
	        File file = new File(eventImageField.getText().trim());
	        String newFileName = "./resources/event_images/" + file.getName();
	        File newFile = new File(newFileName);
	        Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	        return newFileName;
	    }
	}

    class LocationRenderer extends JLabel implements ListCellRenderer<Lokacija> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Lokacija> list, Lokacija value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.getNaziv());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setOpaque(true);
            return this;
        }
    }

