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
        eventSubtypesMap.put("Kultura", new String[]{"Izlozba", "Predstava", "Plesna izvedba"});
        eventSubtypesMap.put("Zabava", new String[]{"Zurka", "Stand-up", "Ostalo"});
        eventSubtypesMap.put("Sport", new String[]{"Utakmica", "Mec", "Turnir"});
        eventSubtypesMap.put("Festival", new String[]{"Muzika", "Film", "Hrana i pice"});
        eventSubtypesMap.put("Konferencija", new String[]{"Poslovna", "Edukativna", "Ostalo"});
        eventSubtypesMap.put("Workshop", new String[]{"Tehnicki", "Kreativni", "Ostalo"});
        eventSubtypesMap.put("Ostalo", new String[]{"Drugo"});

        // Event Name Label and Field
        JLabel lblEventName = new JLabel("Event Name:");
        lblEventName.setForeground(Color.BLACK);
        lblEventName.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventName.setBounds(30, 30, 150, 30);
        contentPane.add(lblEventName);

        eventNameField = new JTextField(event.getNaziv());
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
        SpinnerDateModel model = new SpinnerDateModel(event.getDatum(), null, null, Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd-MM-yyyy");
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

        eventDescriptionArea = new JTextArea(event.getOpis());
        eventDescriptionArea.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventDescriptionArea.setBounds(200, 80, 350, 70);
        contentPane.add(eventDescriptionArea);

        // Event Location Label and Field
        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setForeground(Color.BLACK);
        lblLocation.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblLocation.setBounds(30, 170, 150, 30);
        contentPane.add(lblLocation);

        LokacijaDAO lokacijaDAO = new LokacijaDAO();
        java.util.List<Lokacija> locations = lokacijaDAO.getAllLocations();

        locationComboBox = new JComboBox<>(locations.toArray(new Lokacija[0]));
        locationComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        locationComboBox.setBounds(200, 170, 350, 30);
        locationComboBox.setSelectedItem(event.getLokacija());
        contentPane.add(locationComboBox);

        // Event Type Label and ComboBox
        JLabel lblEventType = new JLabel("Event Type:");
        lblEventType.setForeground(Color.BLACK);
        lblEventType.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventType.setBounds(30, 270, 150, 30);
        contentPane.add(lblEventType);

        eventTypeComboBox = new JComboBox<>(new String[]{"Muzika","Kultura","Zabava","Sport", "Festival", "Konferencija", "Workshop","Ostalo"});
        eventTypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventTypeComboBox.setBounds(200, 270, 350, 30);
        eventTypeComboBox.setSelectedItem(event.getVrsta());
        contentPane.add(eventTypeComboBox);

        // Event Subtype Label and ComboBox
        JLabel lblEventSubtype = new JLabel("Event Subtype:");
        lblEventSubtype.setForeground(Color.BLACK);
        lblEventSubtype.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventSubtype.setBounds(30, 320, 150, 30);
        contentPane.add(lblEventSubtype);

        eventSubtypeComboBox = new JComboBox<>(getSubtypesForType(event.getVrsta()));
        eventSubtypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventSubtypeComboBox.setBounds(200, 320, 350, 30);
        eventSubtypeComboBox.setSelectedItem(event.getPodvrsta());
        contentPane.add(eventSubtypeComboBox);

        // Add ActionListener to eventTypeComboBox to update eventSubtypeComboBox
        eventTypeComboBox.addActionListener(e -> {
            String selectedType = (String) eventTypeComboBox.getSelectedItem();
            eventSubtypeComboBox.removeAllItems();
            for (String subtype : eventSubtypesMap.getOrDefault(selectedType, new String[0])) {
                eventSubtypeComboBox.addItem(subtype);
            }
        });

        // Event Image Label and Field
        JLabel lblEventImage = new JLabel("Image Location:");
        lblEventImage.setForeground(Color.BLACK);
        lblEventImage.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventImage.setBounds(30, 370, 150, 30);
        contentPane.add(lblEventImage);

        eventImageField = new JTextField(event.getSlika());
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
        browseButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif"));
            int returnValue = fileChooser.showOpenDialog(EditEvent.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String destDir = "resources/event_images/";
                new File(destDir).mkdirs();
                String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
                String newFileName = "event_" + event.getDogadjaj_id() + "." + fileExtension;
                File destinationFile = new File(destDir + newFileName);
                try {
                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    eventImageField.setText(newFileName);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        contentPane.add(browseButton);

        // Maximum Tickets Label and Field
        JLabel lblMaxTickets = new JLabel("Max Tickets:");
        lblMaxTickets.setForeground(Color.BLACK);
        lblMaxTickets.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblMaxTickets.setBounds(30, 420, 150, 30);
        contentPane.add(lblMaxTickets);

        maxTicketsField = new JTextField(String.valueOf(event.getMaxKartiPoKorisniku()));
        maxTicketsField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        maxTicketsField.setBounds(200, 420, 150, 30);
        contentPane.add(maxTicketsField);

        // Payment On Registration CheckBox
        paymentOnRegistrationCheckBox = new JCheckBox("Payment on Registration");
        paymentOnRegistrationCheckBox.setForeground(Color.BLACK);
        paymentOnRegistrationCheckBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        paymentOnRegistrationCheckBox.setBounds(200, 460, 250, 30);
        paymentOnRegistrationCheckBox.setSelected(event.isNaplataPriRezervaciji());
        contentPane.add(paymentOnRegistrationCheckBox);

        // Event Finished Label and ComboBox
        JLabel lblEventFinished = new JLabel("Event Finished:");
        lblEventFinished.setForeground(Color.BLACK);
        lblEventFinished.setFont(new Font("Chilanka", Font.PLAIN, 18));
        lblEventFinished.setBounds(30, 510, 150, 30);
        contentPane.add(lblEventFinished);

        eventFinishedComboBox = new JComboBox<>(new String[]{"Yes", "No"});
        eventFinishedComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventFinishedComboBox.setBounds(200, 510, 150, 30);
        eventFinishedComboBox.setSelectedItem(event.isZavrsio() ? "Yes" : "No");
        contentPane.add(eventFinishedComboBox);

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        saveButton.setBounds(450, 600, 100, 40);
        saveButton.setForeground(new Color(51, 51, 51));
        saveButton.setBackground(Color.decode("#f3f7f8"));
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateEvent();
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EditEvent.this, "Error saving event.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPane.add(saveButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Chilanka", Font.PLAIN, 18));
        cancelButton.setBounds(330, 600, 100, 40);
        cancelButton.setForeground(new Color(51, 51, 51));
        cancelButton.setBackground(Color.decode("#f3f7f8"));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e2e2e2")));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(cancelButton);
    }

    private void updateEvent() {
        String naziv = eventNameField.getText();
        String opis = eventDescriptionArea.getText();
        Date datum = ((SpinnerDateModel) dateSpinner.getModel()).getDate();
        Lokacija lokacija = (Lokacija) locationComboBox.getSelectedItem();
        String vrsta = (String) eventTypeComboBox.getSelectedItem();
        String podvrsta = (String) eventSubtypeComboBox.getSelectedItem();
        String slika = eventImageField.getText();
        int brojKarata = Integer.parseInt(maxTicketsField.getText());
        boolean placanjeNaRegistraciji = paymentOnRegistrationCheckBox.isSelected();
        boolean zavrseno = "Yes".equals(eventFinishedComboBox.getSelectedItem());

        event.setNaziv(naziv);
        event.setOpis(opis);
        event.setDatum(datum);
        event.setLokacija(lokacija);
        event.setVrsta(vrsta);
        event.setPodvrsta(podvrsta);
        event.setSlika(slika);
        event.setMaxKartiPoKorisniku(brojKarata);
        event.setNaplataPriRezervaciji(placanjeNaRegistraciji);
        event.setZavrsio(zavrseno);

        DogadjajDAO dogadjajDAO = new DogadjajDAO();
        dogadjajDAO.updateDogadjaj(event);
    }

    private String[] getSubtypesForType(String eventType) {
        return eventSubtypesMap.getOrDefault(eventType, new String[0]);
    }
}
