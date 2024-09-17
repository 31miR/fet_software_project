package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.LokacijaDAO;

//This is for each individual dogadjaj OK
class GuestDogadjajPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Dogadjaj dogadjaj;
	Guest parent;

	public GuestDogadjajPanel(Guest parent, Dogadjaj dogadjaj) {
		this.dogadjaj = dogadjaj;
		this.parent = parent;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 200));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		// DODAVANJE SLIKE UZAS
		JPanel slikaPanel = new JPanel();
		slikaPanel.setPreferredSize(new Dimension(200, 200));
		slikaPanel.setBackground(Color.PINK);
		ImageIcon imageIcon = new ImageIcon(dogadjaj.getSlika());
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		JLabel slikaLabel = new JLabel(new ImageIcon(scaledImage));
		slikaPanel.add(slikaLabel);
		add(slikaPanel, BorderLayout.WEST);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		JLabel nazivLabel = new JLabel(dogadjaj.getNaziv());
		nazivLabel.setFont(new Font("Arial", Font.BOLD, 18));
		infoPanel.add(nazivLabel);

		infoPanel.add(new JLabel(dogadjaj.getOpis()));
		infoPanel.add(new JLabel(dogadjaj.getDatum().toString()));
		infoPanel.add(new JLabel(dogadjaj.getVrsta()));
		infoPanel.add(new JLabel(dogadjaj.getPodvrsta()));
		infoPanel.add(new JLabel(dogadjaj.isZavrsio() ? "DOGADJAJ JE ZAVRSEN!" : ""));
		infoPanel.add(new JLabel(dogadjaj.getLokacija().getGrad()));

		// Panel za dugmad
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton detaljiButton = new JButton("Detaljnije informacije");
		detaljiButton.addActionListener((e) -> {
			detaljiButtonPressed();
		});

		buttonPanel.add(detaljiButton);

		// Dodaj panele za informacije i dugmad u glavni panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(infoPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);
	}
	private void detaljiButtonPressed() {
		EventDetailsDialogBox view = new EventDetailsDialogBox(dogadjaj);
		view.setVisible(true);
	}
}


class GuestDogadjajListViewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	List<Dogadjaj> dogadjajList;
	Guest parent;
	GuestDogadjajListViewPanel(List<Dogadjaj> dogadjajList, Guest parent) {
		this.dogadjajList = dogadjajList;
		this.parent = parent;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		refreshLayout();
	}
	public void refreshLayout() {
		removeAll();
		for (Dogadjaj i : dogadjajList) {
			GuestDogadjajPanel dp = new GuestDogadjajPanel(parent, i);
			add(dp);
		}
		revalidate();
		repaint();
	}
}

public class Guest extends JFrame {
    private static final long serialVersionUID = 1L;
    private LokacijaDAO lokacijaDAO = new LokacijaDAO();
    private DogadjajDAO dogadjajDAO = new DogadjajDAO();
    private int dogadjajOffset = 0;
    private final int PER_PAGE = 5;
    private Map<String, List<String>> typeToSubType;
    JPanel topPanel;
    private JComboBox<String> cityJCB;
    private JComboBox<String> typeJCB;
    private JComboBox<String> subTypeJCB;
    private JCheckBox showFinishedCheckBox;
    private JTextField searchBarTF;
    private JButton prevPageButton;
    private JButton nextPageButton;
    private List<Dogadjaj> dogadjajList = new ArrayList<Dogadjaj>();
    GuestDogadjajListViewPanel mainContentPanel;
    
    //values which are only allowed to be changed in the searchButtonPressed function! NOWHERE ELSE!!!!
    List<String> filters;
    String searchString;
    
    public Guest() {
        setTitle("Karta - korisnicki panel");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Vrh opcije
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        updateTopPanel();

        // Opcije za pretragu dogadjaja
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        updateCityJCB();
        updateTypeJCB();
        updateSubTypeJCB();
        typeJCB.addActionListener((e) -> {
        	updateSubTypeJCB();
        });
        searchPanel.add(cityJCB);
        searchPanel.add(typeJCB);
        searchPanel.add(subTypeJCB);
        searchBarTF = new JTextField("search bar", 15);
        addPlaceholderEffect(searchBarTF, "search bar");
        searchPanel.add(searchBarTF);
        showFinishedCheckBox = new JCheckBox("prikazi zavrsene dogadjaje");
        searchPanel.add(showFinishedCheckBox);
        JButton searchButton = new JButton("search");
        searchButton.addActionListener((e) -> {
        	searchButtonPressed();
        });
        searchPanel.add(searchButton);
        
        mainContentPanel = new GuestDogadjajListViewPanel(dogadjajList, this);
        mainContentPanel.setPreferredSize(new Dimension(600, 1000));
        
        
        // Za promjenu stranice
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        prevPageButton = new JButton("prev page");
        prevPageButton.setEnabled(false);
        prevPageButton.addActionListener((e) -> {
        	prevPagePressed();
        });
        bottomPanel.add(prevPageButton);
        nextPageButton = new JButton("next page");
        nextPageButton.setEnabled(false);
        nextPageButton.addActionListener((e) -> {
        	nextPagePressed();
        });
        bottomPanel.add(nextPageButton);

        // uvezivanje YAY
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(mainContentPanel, BorderLayout.CENTER);
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // I kao, da mozes skrolat
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
    private void updateCityJCB() {
		java.util.List<String> cities = lokacijaDAO.getAllCities();
		cityJCB = new JComboBox<String>();
		cityJCB.addItem("");
		for (String i : cities) {
			cityJCB.addItem(i);
		}
	}
	private void updateSubTypeJCB() {
		if (subTypeJCB == null) {
			subTypeJCB = new JComboBox<String>(typeToSubType.get(typeJCB.getSelectedItem()).toArray(new String[0]));
		}
		else {
			subTypeJCB.removeAllItems();
			for (String i : typeToSubType.get(typeJCB.getSelectedItem())) {
				subTypeJCB.addItem(i);
			}
		}
	}
	private void updateTypeJCB() {
		typeToSubType = dogadjajDAO.getTypeToSubTypeMapping();
		typeJCB = new JComboBox<String>(typeToSubType.keySet().toArray(new String[0]));
	}
	private void nextPagePressed() {
		dogadjajOffset += PER_PAGE;
		dogadjajList.removeAll(dogadjajList);
		List<Dogadjaj> dummy = dogadjajDAO.getFiltered(filters, searchString, "datum", false, dogadjajOffset, PER_PAGE);
		dogadjajList.addAll(dummy);
		if (dogadjajDAO.getFiltered(filters, searchString, "datum", false, dogadjajOffset + PER_PAGE, PER_PAGE).size() != 0) {
			nextPageButton.setEnabled(true);
		}
		else {
			nextPageButton.setEnabled(false);
		}
		prevPageButton.setEnabled(true);
		mainContentPanel.refreshLayout();
	}
	private void prevPagePressed() {
		dogadjajOffset -= PER_PAGE;
		dogadjajList.removeAll(dogadjajList);
		List<Dogadjaj> dummy = dogadjajDAO.getFiltered(filters, searchString, "datum", false, dogadjajOffset, PER_PAGE);
		dogadjajList.addAll(dummy);
		if (dogadjajDAO.getFiltered(filters, searchString, "datum", false, dogadjajOffset + PER_PAGE, PER_PAGE).size() != 0) {
			nextPageButton.setEnabled(true);
		}
		else {
			nextPageButton.setEnabled(false);
		}
		if (dogadjajOffset == 0) {
			prevPageButton.setEnabled(false);
		}
		else {
			prevPageButton.setEnabled(true);
		}
		mainContentPanel.refreshLayout();
	}
	private void searchButtonPressed() {
		dogadjajOffset = 0;
		String grad = (String) cityJCB.getSelectedItem();
		String vrsta = (String) typeJCB.getSelectedItem();
		String podvrsta = (String) subTypeJCB.getSelectedItem();
		if (searchBarTF.getText().equals("search bar")) {
			searchString = "";
		}
		else {
			searchString = searchBarTF.getText();
		}
		filters = new ArrayList<String>();
		if (!grad.equals("")) {
			filters.add("grad");
			filters.add(grad);
		}
		if (!vrsta.equals("")) {
			filters.add("vrsta");
			filters.add(vrsta);
		}
		if (!podvrsta.equals("")) {
			filters.add("podvrsta");
			filters.add(podvrsta);
		}
		if (!showFinishedCheckBox.isSelected()) {
			filters.add("zavrsio");
			filters.add("false");
		}
		dogadjajList.removeAll(dogadjajList);
		List<Dogadjaj> dummy = dogadjajDAO.getFiltered(filters, searchString, "datum", false, dogadjajOffset, PER_PAGE);
		dogadjajList.addAll(dummy);
		if (dogadjajDAO.getFiltered(filters, searchString, "datum", false, dogadjajOffset + PER_PAGE, PER_PAGE).size() != 0) {
			nextPageButton.setEnabled(true);
		}
		else {
			nextPageButton.setEnabled(false);
		}
		prevPageButton.setEnabled(false);
		mainContentPanel.refreshLayout();
	}
	private void addPlaceholderEffect(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }
	private void logoutButtonPressed() {
		Login view = new Login();
		dispose();
		view.setVisible(true);
	}
	public void updateTopPanel() {
		topPanel.removeAll();
		topPanel.add(new JLabel("Guest"));
        JButton logoutButton = new JButton("leave guest view");
        logoutButton.addActionListener((e) -> {
        	logoutButtonPressed();
        });
        topPanel.add(logoutButton);
        topPanel.revalidate();
        topPanel.repaint();
	}
}
