package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.SektorDAO;
import model.Karta;
import model.KartaDAO;
import model.Korisnik;
import model.KorisnikDAO;
import model.LokacijaDAO;
import model.Sektor;


class ReservedTicket extends JPanel {
	private static final long serialVersionUID = 1L;
	ReservedTicketsForUserDialogBox parentDialog;
	Karta karta;
	public ReservedTicket(ReservedTicketsForUserDialogBox parentDialog, Karta karta) {
		this.parentDialog = parentDialog;
		this.karta = karta;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 100));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		infoPanel.add(new JLabel("Lokacija sjedista: " + karta.getSjediste()));
		infoPanel.add(new JLabel("Cijena karte: "
								+ String.valueOf(karta.getCijena() / 100)
								+ "."
								+String.valueOf(karta.getCijena() % 100)));
		infoPanel.add(new JLabel("Cijena rezervacije: "
				+ (karta.getCijenaRezervacije() == 0 ? "BESPLATNO" : String.valueOf(karta.getCijenaRezervacije() / 100)
																	+ "."
																	+ String.valueOf(karta.getCijenaRezervacije() % 100))));
		if (karta.getCijenaRezervacije() != 0 && (!karta.getDogadjaj().isNaplataPriRezervaciji())) {
			infoPanel.add(new JLabel("Rezervacija se naplacuje samo u slucaju da je rezervacija otkazana"));
		}
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton otkaziButton = new JButton("Otkazi rezervaciju");
		otkaziButton.addActionListener((e) -> {
			otkaziButtonPressed();
		});
		buttonPanel.add(otkaziButton);
		JButton kupiButton = new JButton("Kupi kartu");
		kupiButton.addActionListener((e) -> {
			kupiButtonPressed();
		});
		buttonPanel.add(kupiButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(infoPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);
	}
	private void kupiButtonPressed() {
		// TODO Auto-generated method stub
		
	}
	private void otkaziButtonPressed() {
		// TODO Auto-generated method stub
		
	}
}

public class ReservedTicketsForUserDialogBox extends JDialog {
	private static final long serialVersionUID = 1L;
	KorisnikDAO korisnikDAO = new KorisnikDAO();
	SektorDAO sektorDAO = new SektorDAO();
	KartaDAO kartaDAO = new KartaDAO();
	KorisnikAndDogadjajListView parent;
	JPanel topPanel;
	JPanel mainContentPanel;
	List<Karta> karte;
    public ReservedTicketsForUserDialogBox(KorisnikAndDogadjajListView parent) {
    	super(parent, "Add Wallet Balance", true);
    	this.parent = parent;
    	
    	//make sure finished events are either removed AND IF NEEDED force the payment of reservation
    	kartaDAO.forceUserReservedPayment(parent.korisnik);
    	kartaDAO.removeUserReservedAndPaid(parent.korisnik);
    	parent.korisnik = korisnikDAO.searchByUserName(parent.korisnik.getUsername());
    	
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Rezervisane karte - " + parent.korisnik.getName() + " " + parent.korisnik.getLastName());
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        topPanel.add(new JLabel("Stanje racuna: "
        			+ String.valueOf(parent.korisnik.getWalletBalance() / 100)
        			+ "."
        			+ String.valueOf(parent.korisnik.getWalletBalance() % 100)));
        
        mainContentPanel = new JPanel();
        mainContentPanel.setPreferredSize(new Dimension(600, 1000));
        
        updateTicketList();

        // uvezivanje YAY
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(mainContentPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // I kao, da mozes skrolat
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }
    public void updateUserBalance(int newBalance) {
    	Korisnik korisnik = korisnikDAO.searchByUserName(parent.korisnik.getName());
    	korisnik.setWalletBalance(newBalance);
    	korisnikDAO.updateKorisnik(korisnik);
    	parent.korisnik = korisnik;
    }
    public void updateTicketList() {
    	mainContentPanel.removeAll();
    	karte = kartaDAO.getReservedTicketsForUser(parent.korisnik);
    	for (Karta i : karte) {
    		mainContentPanel.add(new ReservedTicket(this, i));
    	}
    	mainContentPanel.revalidate();
    	mainContentPanel.repaint();
    }
}
