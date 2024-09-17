package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.DogadjajDAO;
import model.KartaDAO;
import model.Korisnik;
import model.KorisnikDAO;
import model.Organizator;
import model.OrganizatorDAO;
import model.SektorDAO;


class KorisnikCreationRequestPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	RequestUsers parentDialog;
	KorisnikDAO korisnikDAO = new KorisnikDAO();
	KartaDAO kartaDAO = new KartaDAO();
	DogadjajDAO dogadjajDAO = new DogadjajDAO();
	JButton odbijButton;
	JButton prihvatiButton;
	Korisnik korisnik;
	public KorisnikCreationRequestPanel(RequestUsers parentDialog, Korisnik korisnik) {
		this.parentDialog = parentDialog;
		this.korisnik = korisnik;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 100));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		infoPanel.add(new JLabel("username: " + korisnik.getUsername()));
		infoPanel.add(new JLabel("full name: " + korisnik.getName() + " " + korisnik.getLastName()));
		infoPanel.add(new JLabel("e-mail: " + korisnik.getEmail()));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		odbijButton = new JButton("decline profile");
		odbijButton.addActionListener((e) -> {
			odbijButtonPressed();
		});
		buttonPanel.add(odbijButton);
		prihvatiButton = new JButton("accept profile");
		prihvatiButton.addActionListener((e) -> {
			prihvatiButtonPressed();
		});
		buttonPanel.add(prihvatiButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(infoPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);
	}
	private void prihvatiButtonPressed() {
		korisnik.setProfileApproved(true);
		korisnikDAO.updateKorisnik(korisnik);
		prihvatiButton.setEnabled(false);
		odbijButton.setEnabled(false);
	}
	private void odbijButtonPressed() {
		korisnikDAO.deleteKorisnik(korisnik);
		prihvatiButton.setEnabled(false);
		odbijButton.setEnabled(false);
	}
}


class OrganizatorCreationRequestPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	RequestUsers parentDialog;
	OrganizatorDAO organizatorDAO = new OrganizatorDAO();
	KartaDAO kartaDAO = new KartaDAO();
	DogadjajDAO dogadjajDAO = new DogadjajDAO();
	JButton odbijButton;
	JButton prihvatiButton;
	Organizator organizator;
	public OrganizatorCreationRequestPanel(RequestUsers parentDialog, Organizator organizator) {
		this.parentDialog = parentDialog;
		this.organizator = organizator;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 100));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		infoPanel.add(new JLabel("username: " + organizator.getUsername()));
		infoPanel.add(new JLabel("full name: " + organizator.getName() + " " + organizator.getLastName()));
		infoPanel.add(new JLabel("e-mail: " + organizator.getEmail()));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		odbijButton = new JButton("decline profile");
		odbijButton.addActionListener((e) -> {
			odbijButtonPressed();
		});
		buttonPanel.add(odbijButton);
		prihvatiButton = new JButton("accept profile");
		prihvatiButton.addActionListener((e) -> {
			prihvatiButtonPressed();
		});
		buttonPanel.add(prihvatiButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(infoPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);
	}
	private void prihvatiButtonPressed() {
		organizator.setProfileApproved(true);
		organizatorDAO.updateOrganizator(organizator);
		prihvatiButton.setEnabled(false);
		odbijButton.setEnabled(false);
	}
	private void odbijButtonPressed() {
		organizatorDAO.deleteOrganizator(organizator);
		prihvatiButton.setEnabled(false);
		odbijButton.setEnabled(false);
	}
}


public class RequestUsers extends JDialog {
	private static final long serialVersionUID = 1L;
	KorisnikDAO korisnikDAO = new KorisnikDAO();
	SektorDAO sektorDAO = new SektorDAO();
	KartaDAO kartaDAO = new KartaDAO();
	OrganizatorDAO organizatorDAO = new OrganizatorDAO();
	AdminView parent;
	JPanel topPanel;
	JLabel walletBalanceLabel;
	JPanel mainContentPanel;
	JComboBox<String> choiceSelector;
    public RequestUsers(AdminView parent) {
    	super(parent, "Profile creation requests", true);
    	this.parent = parent;
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Zahtjevi za profil:");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        choiceSelector = new JComboBox<>(new String[] {"organizatori", "korisnici"});
        choiceSelector.addActionListener((e) -> {
        	updateRequestList();
        });
        topPanel.add(choiceSelector);
        
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setPreferredSize(new Dimension(600, 1000));
        
        updateRequestList();

        add(topPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);

        // I kao, da mozes skrolat
        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }
    public void updateRequestList() {
    	mainContentPanel.removeAll();
    	String choiceString = (String)choiceSelector.getSelectedItem();
    	
    	if (choiceString.equals("organizatori")) {
    		List<Organizator> list = organizatorDAO.getPending();
    		for (Organizator i : list) {
    			mainContentPanel.add(new OrganizatorCreationRequestPanel(this, i));
    		}
    	}
    	else {
    		List<Korisnik> list = korisnikDAO.getPending();
    		for (Korisnik i : list) {
    			mainContentPanel.add(new KorisnikCreationRequestPanel(this, i));
    		}
    	}
    	
    	mainContentPanel.revalidate();
    	mainContentPanel.repaint();
    }
}
