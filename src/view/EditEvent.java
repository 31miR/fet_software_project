package view;

import model.Dogadjaj;
import model.IzmjeneDAO;
import model.Lokacija;
import model.LokacijaDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

public class EditEvent extends JDialog {
    private static final long serialVersionUID = 1L;
    private Dogadjaj event;
    private JPanel contentPane;
    private JTextField eventNameField;
    private JTextArea eventDescriptionArea;
    private JSpinner dateSpinner;
    private JComboBox<String> eventTypeComboBox;
    private JComboBox<String> eventSubtypeComboBox;
    private JTextField eventImageField;
    private JTextField maxTicketsField;
    private JCheckBox paymentOnRegistrationCheckBox;
    private Map<String, String[]> eventSubtypesMap;
    private IzmjeneDAO izmjeneDAO;

    // Store original values
    private String originalName;
    private String originalDescription;
    private Date originalDate;
    private String originalType;
    private String originalSubtype;
    private String originalImagePath;
    private int originalMaxTickets;
    private boolean originalPaymentOnRegistration;
    

    public EditEvent(Dogadjaj event) {
        this.event = event;
        this.izmjeneDAO = new IzmjeneDAO();
        initialize();
    }

    private void initialize() {
        setBounds(450, 210, 620, 750);
        setResizable(false);
        setModal(true);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166));
        setTitle("Edit Event");

        // Initialize subtype map
        eventSubtypesMap = new HashMap<>();
        eventSubtypesMap.put("", new String[]{""});
        eventSubtypesMap.put("Muzika", new String[]{"", "Koncert", "Festival", "Ostalo"});
        eventSubtypesMap.put("Kultura", new String[]{"", "Izložba", "Pozorište", "Ostalo"});
        eventSubtypesMap.put("Sport", new String[]{"", "Utakmica", "Trka", "Ostalo"});

        // Store original values
        originalName = event.getNaziv();
        originalDescription = event.getOpis();
        originalDate = event.getDatum();
        originalType = event.getVrsta();
        originalSubtype = event.getPodvrsta();
        originalImagePath = event.getSlika();
        originalMaxTickets = event.getMaxKartiPoKorisniku();
        originalPaymentOnRegistration = event.isNaplataPriRezervaciji();
       

        // Initialize form fields
        initFormFields();
    }

    private void initFormFields() {
        // Event Name
        JLabel lblEventName = new JLabel("Event Name:");
        lblEventName.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventName.setBounds(20, 20, 150, 30);
        contentPane.add(lblEventName);

        eventNameField = new JTextField(originalName);
        eventNameField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventNameField.setBounds(180, 20, 400, 30);
        contentPane.add(eventNameField);

        // Event Description
        JLabel lblEventDescription = new JLabel("Event Description:");
        lblEventDescription.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventDescription.setBounds(20, 60, 200, 30);
        contentPane.add(lblEventDescription);

        eventDescriptionArea = new JTextArea(originalDescription);
        eventDescriptionArea.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventDescriptionArea.setLineWrap(true);
        eventDescriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(eventDescriptionArea);
        descriptionScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        descriptionScrollPane.setBounds(20, 100, 560, 100);
        contentPane.add(descriptionScrollPane);

        // Event Date
        JLabel lblEventDate = new JLabel("Event Date:");
        lblEventDate.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventDate.setBounds(20, 220, 150, 30);
        contentPane.add(lblEventDate);

        dateSpinner = new JSpinner(new SpinnerDateModel(originalDate, null, null, Calendar.DAY_OF_MONTH));
        dateSpinner.setFont(new Font("Chilanka", Font.PLAIN, 18));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd HH:mm:ss");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setBounds(180, 220, 200, 30);
        contentPane.add(dateSpinner);

        // Event Type
        JLabel lblEventType = new JLabel("Event Type:");
        lblEventType.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventType.setBounds(20, 260, 150, 30);
        contentPane.add(lblEventType);

        eventTypeComboBox = new JComboBox<>(new String[]{"", "Muzika", "Kultura", "Sport"});
        eventTypeComboBox.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventTypeComboBox.setBounds(180, 260, 200, 30);
        eventTypeComboBox.setSelectedItem(originalType);
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
        updateEventSubtypes();
        eventSubtypeComboBox.setSelectedItem(originalSubtype);
        contentPane.add(eventSubtypeComboBox);

        // Event Image
        JLabel lblEventImage = new JLabel("Event Image:");
        lblEventImage.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblEventImage.setBounds(20, 340, 150, 30);
        contentPane.add(lblEventImage);

        eventImageField = new JTextField(originalImagePath);
        eventImageField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        eventImageField.setBounds(180, 340, 400, 30);
        contentPane.add(eventImageField);
        
        // Max Tickets
        JLabel lblMaxTickets = new JLabel("Max Tickets:");
        lblMaxTickets.setFont(new Font("Chilanka", Font.PLAIN, 20));
        lblMaxTickets.setBounds(20, 420, 150, 30);
        contentPane.add(lblMaxTickets);

        maxTicketsField = new JTextField(String.valueOf(originalMaxTickets));
        maxTicketsField.setFont(new Font("Chilanka", Font.PLAIN, 18));
        maxTicketsField.setBounds(180, 420, 100, 30);
        contentPane.add(maxTicketsField);

        // Payment on Registration
        paymentOnRegistrationCheckBox = new JCheckBox("Payment on Registration");
        paymentOnRegistrationCheckBox.setFont(new Font("Chilanka", Font.PLAIN, 20));
        paymentOnRegistrationCheckBox.setBounds(20, 460, 300, 30);
        paymentOnRegistrationCheckBox.setSelected(originalPaymentOnRegistration);
        contentPane.add(paymentOnRegistrationCheckBox);

        // Save Button
        JButton btnSave = new JButton("Save Changes");
        btnSave.setFont(new Font("Chilanka", Font.PLAIN, 20));
        btnSave.setBounds(160, 550, 200, 40);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEvent();
            }
        });
        contentPane.add(btnSave);

        // No more back button
    }

    // Method to update the event subtypes based on the selected type
    private void updateEventSubtypes() {
        String selectedType = (String) eventTypeComboBox.getSelectedItem();
        String[] subtypes = eventSubtypesMap.get(selectedType);
        eventSubtypeComboBox.setModel(new DefaultComboBoxModel<>(subtypes));
    }

 

    // Method to save the event and track changes
 
    private void saveEvent() {
        // Fetching new values from form fields
        String newName = eventNameField.getText();
        String newDescription = eventDescriptionArea.getText();
        Date newDate = (Date) dateSpinner.getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDate = dateFormat.format(newDate);
        String newType = (String) eventTypeComboBox.getSelectedItem();
        String newSubtype = (String) eventSubtypeComboBox.getSelectedItem();
        String newImagePath = eventImageField.getText();
        int newMaxTickets = Integer.parseInt(maxTicketsField.getText());
        boolean newPaymentOnRegistration = paymentOnRegistrationCheckBox.isSelected();
        

        // Check and save only modified fields
        if (!newName.equals(originalName)) {
            izmjeneDAO.addChange("Dogadjaj", String.valueOf(event.getDogadjaj_id()), "naziv", newName);
        }
        if (!newDescription.equals(originalDescription)) {
            izmjeneDAO.addChange("Dogadjaj", String.valueOf(event.getDogadjaj_id()), "opis", newDescription);
        }
        if (!newDate.equals(originalDate)) {
            izmjeneDAO.addChange("Dogadjaj", String.valueOf(event.getDogadjaj_id()), "datum", stringDate);
        }
        if (!newType.equals(originalType) && !newType.equals("")) {
            izmjeneDAO.addChange("Dogadjaj", String.valueOf(event.getDogadjaj_id()), "vrsta", newType);
        }
        if (!newSubtype.equals(originalSubtype) && !newType.equals("")) {
            izmjeneDAO.addChange("Dogadjaj", String.valueOf(event.getDogadjaj_id()), "podvrsta", newSubtype);
        }
        if (!newImagePath.equals(originalImagePath)) {
            izmjeneDAO.addChange("Dogadjaj", String.valueOf(event.getDogadjaj_id()), "slika", newImagePath);
        }
        if (newMaxTickets != originalMaxTickets) {
            izmjeneDAO.addChange("Dogadjaj", String.valueOf(event.getDogadjaj_id()), "maxKartiPoKorisniku", String.valueOf(newMaxTickets));
        }
        if (newPaymentOnRegistration != originalPaymentOnRegistration) {
            izmjeneDAO.addChange("Dogadjaj", String.valueOf(event.getDogadjaj_id()), "naplataPriRezervaciji", String.valueOf(newPaymentOnRegistration));
        }
        

        JOptionPane.showMessageDialog(this, "Changes have been sent for approval.");
     
        dispose();
        
    }    
}
