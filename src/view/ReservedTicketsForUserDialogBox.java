package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Karta;
import model.KartaDAO;
import model.Korisnik;
import model.KorisnikDAO;
import model.SektorDAO;


class ReservedTicket extends JPanel {
	private static final long serialVersionUID = 1L;
	ReservedTicketsForUserDialogBox parentDialog;
	Karta karta;
	KorisnikDAO korisnikDAO = new KorisnikDAO();
	KartaDAO kartaDAO = new KartaDAO();
	JButton kupiButton;
	JButton otkaziButton;
	public ReservedTicket(ReservedTicketsForUserDialogBox parentDialog, Karta karta) {
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
		
		otkaziButton = new JButton("Cancel reservation");
		otkaziButton.addActionListener((e) -> {
			otkaziButtonPressed();
		});
		buttonPanel.add(otkaziButton);
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
		double discountRate = Math.pow(0.9, kartaDAO.countBoughtTicketsForUser(korisnik) / 10);
		//if naplata pri rezervaciji, return the money taken from user for the raservation back to him
		int calculatedPrice = (int)(karta.getCijena()*discountRate)
				- (karta.getDogadjaj().isNaplataPriRezervaciji() ? karta.getCijenaRezervacije() : 0);
		if (korisnik.getWalletBalance() < calculatedPrice && calculatedPrice > 0) {
			JOptionPane.showMessageDialog(
		            parentDialog,
		            "You do not have enough balance to buy the ticket. Needed ammount is: $"
		            	+String.valueOf(calculatedPrice / 100) + "." + String.valueOf(Math.abs(calculatedPrice) % 100),
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		int returnalAmmount = karta.getDogadjaj().isNaplataPriRezervaciji() ? karta.getCijenaRezervacije() : 0;
		int priceWithoutReturnal = calculatedPrice + returnalAmmount;
		int response = JOptionPane.showOptionDialog(
				parentDialog,
				"Ticket price with possible discount is: $"
						+ String.valueOf(priceWithoutReturnal / 100) + "." + String.valueOf(priceWithoutReturnal % 100)
						+ (returnalAmmount == 0 ? "" : ", Returned money (the ammount you've paid when you've made the reservation): $"
							+ String.valueOf(returnalAmmount / 100) + "." + String.valueOf(returnalAmmount % 100)
							+ ", Final price: " + String.valueOf(calculatedPrice / 100) + "."
							+ String.valueOf(Math.abs(calculatedPrice) % 100)),
				"Do you wish to buy the ticket? ",
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
		karta.setKorRezervisao(null);
		karta.setKorKupio(korisnik);
		korisnikDAO.updateKorisnik(korisnik);
		kartaDAO.updateTicket(karta);
		kupiButton.setEnabled(false);
		otkaziButton.setEnabled(false);
		parentDialog.updateWalletBalanceLabel();
	}
	private void otkaziButtonPressed() {
		Korisnik korisnik = parentDialog.parent.korisnik;
		if (karta.getDogadjaj().isNaplataPriRezervaciji()) {
			int response = JOptionPane.showOptionDialog(
					parentDialog,
					"Are you sure you want to cancel the reservation",
					"Cancel?",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					new String[]{"Yes", "No"},
					"No"
					);
			if (response == 1) { //means he pressed no!
				return;
			}
		}
		else {
			int response = JOptionPane.showOptionDialog(
					parentDialog,
					"Are you sure you want to cancel the reservation?" + (karta.getCijenaRezervacije() == 0 ? ""
							: " You will have to pay for the reservation if you decide to cancel. Reservation price: $"
								+ String.valueOf(karta.getCijenaRezervacije() / 100) + "."
								+ String.valueOf(karta.getCijenaRezervacije() % 100)),
					"Do you wish to cancel said reservation?",
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
		karta.setKorRezervisao(null);
		korisnikDAO.updateKorisnik(korisnik);
		kartaDAO.updateTicket(karta);
		kupiButton.setEnabled(false);
		otkaziButton.setEnabled(false);
		parentDialog.updateWalletBalanceLabel();
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
	JLabel walletBalanceLabel;
	List<Karta> karte;
    public ReservedTicketsForUserDialogBox(KorisnikAndDogadjajListView parent) {
    	super(parent, "Add Wallet Balance", true);
    	this.parent = parent;
    	
    	//make sure finished events are either removed AND IF NEEDED force the payment of reservation
    	kartaDAO.forceUserReservedPayment(parent.korisnik);
    	kartaDAO.removeUserReservedAndPaid(parent.korisnik);
    	parent.korisnik = korisnikDAO.searchByUserName(parent.korisnik.getUsername());
    	
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Reserved tickets - " + parent.korisnik.getName() + " " + parent.korisnik.getLastName());
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        walletBalanceLabel = new JLabel();
        updateWalletBalanceLabel();
        topPanel.add(walletBalanceLabel);
        
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
    	karte = kartaDAO.getReservedTicketsForUser(parent.korisnik);
    	for (Karta i : karte) {
    		mainContentPanel.add(new ReservedTicket(this, i));
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
