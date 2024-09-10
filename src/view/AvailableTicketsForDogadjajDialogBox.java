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


class AvailableTicket extends JPanel {
	private static final long serialVersionUID = 1L;
	AvailableTicketsForDogadjajDialogBox parentDialog;
	Karta karta;
	public AvailableTicket(AvailableTicketsForDogadjajDialogBox parentDialog, Karta karta) {
		this.parentDialog = parentDialog;
		this.karta = karta;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 100));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		infoPanel.add(new JLabel("Lokacija sjedista: " + karta.getSjediste()));
		infoPanel.add(new JLabel("Cijena karte: " + karta.getCijena()));
		infoPanel.add(new JLabel("Cijena rezervacije: "
				+ (karta.getCijenaRezervacije() == 0 ? "BESPLATNO" : String.valueOf(karta.getCijenaRezervacije()))));
		if (karta.getCijenaRezervacije() != 0 && (!karta.getDogadjaj().isNaplataPriRezervaciji())) {
			infoPanel.add(new JLabel("Rezervacija se naplacuje samo u slucaju da je rezervacija otkazana"));
		}
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton rezervisiButton = new JButton("Rezervisi kartu");
		rezervisiButton.addActionListener((e) -> {
			rezervisiButtonPressed();
		});
		buttonPanel.add(rezervisiButton);
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
	private void rezervisiButtonPressed() {
		// TODO Auto-generated method stub
		
	}
}

public class AvailableTicketsForDogadjajDialogBox extends JDialog {
	private static final long serialVersionUID = 1L;
	KorisnikDAO korisnikDAO = new KorisnikDAO();
	SektorDAO sektorDAO = new SektorDAO();
	KartaDAO kartaDAO = new KartaDAO();
	KorisnikAndDogadjajListView parent;
	Dogadjaj dogadjaj;
	JPanel topPanel;
	JPanel mainContentPanel;
	JComboBox<String> sectorSelector;
	List<Sektor> sektori;
	List<Karta> karte;
    public AvailableTicketsForDogadjajDialogBox(KorisnikAndDogadjajListView parent, Dogadjaj dogadjaj) {
    	super(parent, "Add Wallet Balance", true);
    	this.parent = parent;
    	this.dogadjaj = dogadjaj;
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Slobodne karte za " + dogadjaj.getNaziv());
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        topPanel.add(new JLabel("Stanje racuna: "
        			+ String.valueOf(parent.korisnik.getWalletBalance() / 100)
        			+ "."
        			+ String.valueOf(parent.korisnik.getWalletBalance() % 100)));
        
        sektori = sektorDAO.getAllSectorsInLokacija(dogadjaj.getLokacija());
        sectorSelector = new JComboBox<>();
        for (Sektor i : sektori) {
        	sectorSelector.addItem(i.getNaziv());
        }
        sectorSelector.addActionListener((e) -> {
        	updateTicketList();
        });
        topPanel.add(sectorSelector);
        
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
    	String sectorString = (String)sectorSelector.getSelectedItem();
    	Sektor sector = sektori.get(0);
    	for (Sektor i : sektori) {
    		if (i.getNaziv().equals(sectorString)) {
    			sector = i;
    		}
    	}
    	karte = kartaDAO.getFreeTickets(dogadjaj, sector);
    	for (Karta i : karte) {
    		mainContentPanel.add(new AvailableTicket(this, i));
    	}
    	mainContentPanel.revalidate();
    	mainContentPanel.repaint();
    }
}
