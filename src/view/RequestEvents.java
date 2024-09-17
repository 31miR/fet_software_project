package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.KartaDAO;
import model.KorisnikDAO;
import model.SektorDAO;


class DogadjajCreationRequestPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	RequestEvents parentDialog;
	KorisnikDAO korisnikDAO = new KorisnikDAO();
	KartaDAO kartaDAO = new KartaDAO();
	DogadjajDAO dogadjajDAO = new DogadjajDAO();
	JButton odbijButton;
	JButton prihvatiButton;
	Dogadjaj dogadjaj;
	public DogadjajCreationRequestPanel(RequestEvents parentDialog, Dogadjaj dogadjaj) {
		this.parentDialog = parentDialog;
		this.dogadjaj = dogadjaj;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 100));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		infoPanel.add(new JLabel("name: " + dogadjaj.getNaziv()));
		infoPanel.add(new JLabel("Location: " + dogadjaj.getLokacija().getNaziv()));
		infoPanel.add(new JLabel("City: " + dogadjaj.getLokacija().getGrad()));
		infoPanel.add(new JLabel("Event creator: " + dogadjaj.getOrganizator().getName() + " " + dogadjaj.getOrganizator().getLastName()));
		infoPanel.add(new JLabel("Date: " + dogadjaj.getDatum()));
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		odbijButton = new JButton("decline event");
		odbijButton.addActionListener((e) -> {
			odbijButtonPressed();
		});
		buttonPanel.add(odbijButton);
		prihvatiButton = new JButton("accept event");
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
		dogadjaj.setDogadjajApproved(true);;
		dogadjajDAO.updateDogadjaj(dogadjaj);;
		prihvatiButton.setEnabled(false);
		odbijButton.setEnabled(false);
	}
	private void odbijButtonPressed() {
		dogadjajDAO.deleteDogadjaj(dogadjaj);
		prihvatiButton.setEnabled(false);
		odbijButton.setEnabled(false);
	}
}

public class RequestEvents extends JDialog {
	private static final long serialVersionUID = 1L;
	SektorDAO sektorDAO = new SektorDAO();
	KartaDAO kartaDAO = new KartaDAO();
	DogadjajDAO dogadjajDAO = new DogadjajDAO();
	AdminView parent;
	JPanel topPanel;
	JLabel walletBalanceLabel;
	JPanel mainContentPanel;
    public RequestEvents(AdminView parent) {
    	super(parent, "Event requests", true);
    	this.parent = parent;
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Zahtjevi za profil:");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
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
    	
    	List<Dogadjaj> list = dogadjajDAO.getPending();
    	for (Dogadjaj i : list) {
    		mainContentPanel.add(new DogadjajCreationRequestPanel(this, i));
    	}
    	
    	mainContentPanel.revalidate();
    	mainContentPanel.repaint();
    }
}
