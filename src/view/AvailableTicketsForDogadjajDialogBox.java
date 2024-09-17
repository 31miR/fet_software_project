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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.Karta;
import model.KartaDAO;
import model.Korisnik;
import model.KorisnikDAO;
import model.Sektor;
import model.SektorDAO;


class AvailableTicket extends JPanel {
	private static final long serialVersionUID = 1L;
	AvailableTicketsForDogadjajDialogBox parentDialog;
	KorisnikDAO korisnikDAO = new KorisnikDAO();
	KartaDAO kartaDAO = new KartaDAO();
	DogadjajDAO dogadjajDAO = new DogadjajDAO();
	Karta karta;
	JButton rezervisiButton;
	JButton kupiButton;
	public AvailableTicket(AvailableTicketsForDogadjajDialogBox parentDialog, Karta karta) {
		this.parentDialog = parentDialog;
		this.karta = karta;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 100));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		infoPanel.add(new JLabel("Seat location: " + karta.getSjediste()));
		infoPanel.add(new JLabel("Ticket price: $"
								+ String.valueOf(karta.getCijena() / 100)
								+ "."
								+String.valueOf(karta.getCijena() % 100)));
		infoPanel.add(new JLabel("Reservation price: "
				+ (karta.getCijenaRezervacije() == 0 ? "FREE" : "$" + String.valueOf(karta.getCijenaRezervacije() / 100)
																	+ "."
																	+ String.valueOf(karta.getCijenaRezervacije() % 100))));
		if (karta.getCijenaRezervacije() != 0 && (!karta.getDogadjaj().isNaplataPriRezervaciji())) {
			infoPanel.add(new JLabel("You will pay for reservation only if the reservation is cancelled!"));
		}
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		rezervisiButton = new JButton("Reserve ticket");
		rezervisiButton.addActionListener((e) -> {
			rezervisiButtonPressed();
		});
		buttonPanel.add(rezervisiButton);
		kupiButton = new JButton("Buy ticket");
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
		Korisnik korisnik = parentDialog.parent.korisnik;
		double discountRate = Math.pow(0.9, kartaDAO.countBoughtTicketsForUser(korisnik) / 10); //every 10th card gives 10% discount
		int calculatedPrice = (int)(karta.getCijena() * discountRate);
		if (kartaDAO.countTakenTicketsForUserGivenDogadjaj(korisnik, karta.getDogadjaj()) >= karta.getDogadjaj().getMaxKartiPoKorisniku()) {
			JOptionPane.showMessageDialog(
		            parentDialog,
		            "You've already bought/reserved maximum ammount of tickets for this Event. Maximum ticket count is: "
		            	+String.valueOf(karta.getDogadjaj().getMaxKartiPoKorisniku()),
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		if (korisnik.getWalletBalance() < calculatedPrice) {
			JOptionPane.showMessageDialog(
		            parentDialog,
		            "You do not have enough money to buy this ticket. Ticket price with possible discount is: $" +
            				String.valueOf(calculatedPrice / 100) + "." + String.valueOf(calculatedPrice % 100),
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		int response = JOptionPane.showOptionDialog(
				parentDialog,
				"Ticket price with possible discount is: $" +
						String.valueOf(calculatedPrice / 100) + "." + String.valueOf(calculatedPrice % 100),
				"Would you like to buy this ticket?",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				new String[]{"Yes", "No"},
				"No"
				);
		if (response == 1) { //means he pressed no!
			return;
		}
		korisnik.setWalletBalance(korisnik.getWalletBalance() - calculatedPrice);
		karta.setKorKupio(korisnik);
		korisnikDAO.updateKorisnik(korisnik);
		kartaDAO.updateTicket(karta);
		kupiButton.setEnabled(false);
		rezervisiButton.setEnabled(false);
		parentDialog.updateWalletBalanceLabel();
	}
	private void rezervisiButtonPressed() {
		Korisnik korisnik = parentDialog.parent.korisnik;
		if (kartaDAO.countTakenTicketsForUserGivenDogadjaj(korisnik, karta.getDogadjaj()) >= karta.getDogadjaj().getMaxKartiPoKorisniku()) {
			JOptionPane.showMessageDialog(
		            parentDialog,
		            "You've already bought/reserved maximum ammount of tickets for this Event. Maximum ticket count is: "
		            	+String.valueOf(karta.getDogadjaj().getMaxKartiPoKorisniku()),
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		if (!karta.getDogadjaj().isNaplataPriRezervaciji() && korisnik.getWalletBalance() < 0) {
			JOptionPane.showMessageDialog(
		            parentDialog,
		            "Your wallet balance is negative and as such, you are unable to make any purchases!",
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		if (karta.getDogadjaj().isNaplataPriRezervaciji() && korisnik.getWalletBalance() < karta.getCijenaRezervacije()) {
			JOptionPane.showMessageDialog(
		            parentDialog,
		            "You do not have enough balance to make this reservation!",
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		if (karta.getDogadjaj().isNaplataPriRezervaciji()) {
			int response = JOptionPane.showOptionDialog(
					parentDialog,
					"Reservation is paid upfront. The price of reservation is: " +
							String.valueOf(karta.getCijenaRezervacije() / 100) + "."
							+ String.valueOf(karta.getCijenaRezervacije() % 100),
					"Would you like to make a reservation? ",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					new String[]{"Yes", "No"},
					"No"
					);
			if (response == 1) { //means he pressed no!
				return;
			}
			korisnik.setWalletBalance(korisnik.getWalletBalance() - karta.getCijenaRezervacije());
		}
		karta.setKorRezervisao(korisnik);
		korisnikDAO.updateKorisnik(korisnik);
		kartaDAO.updateTicket(karta);
		kupiButton.setEnabled(false);
		rezervisiButton.setEnabled(false);
		parentDialog.updateWalletBalanceLabel();
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
	JLabel walletBalanceLabel;
	JPanel mainContentPanel;
	JComboBox<String> sectorSelector;
	List<Sektor> sektori;
	List<Karta> karte;
    public AvailableTicketsForDogadjajDialogBox(KorisnikAndDogadjajListView parent, Dogadjaj dogadjaj) {
    	super(parent, "Available tickets", true);
    	this.parent = parent;
    	this.dogadjaj = dogadjaj;
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Available cards for: " + dogadjaj.getNaziv());
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        walletBalanceLabel = new JLabel();
        updateWalletBalanceLabel();
        topPanel.add(walletBalanceLabel);
        
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
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setPreferredSize(new Dimension(600, 1000));
        
        updateTicketList();

        add(topPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);

        // I kao, da mozes skrolat
        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
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
    public void updateWalletBalanceLabel() {
        walletBalanceLabel.setText("Wallet balance: "
            + String.valueOf(parent.korisnik.getWalletBalance() / 100)
            + "." 
            + String.valueOf(parent.korisnik.getWalletBalance() % 100));
    }
}
