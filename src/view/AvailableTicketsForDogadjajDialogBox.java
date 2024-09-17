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
		
		rezervisiButton = new JButton("Rezervisi kartu");
		rezervisiButton.addActionListener((e) -> {
			rezervisiButtonPressed();
		});
		buttonPanel.add(rezervisiButton);
		kupiButton = new JButton("Kupi kartu");
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
		            "Vec ste kupili/rezervisali maksimalan broj karti za dati dogadaj. Maksimalan broj karti je: "
		            	+String.valueOf(karta.getDogadjaj().getMaxKartiPoKorisniku()),
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		if (korisnik.getWalletBalance() < calculatedPrice) {
			JOptionPane.showMessageDialog(
		            parentDialog,
		            "Nemate dovoljno novca da bi kupili kartu. Cijena karte sa eventualnim popustom je: " +
            				String.valueOf(calculatedPrice / 100) + "." + String.valueOf(calculatedPrice % 100),
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		int response = JOptionPane.showOptionDialog(
				parentDialog,
				"Cijena karte sa eventualnim popustom je: " +
						String.valueOf(calculatedPrice / 100) + "." + String.valueOf(calculatedPrice % 100),
				"Zelite li kupiti kartu? ",
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
		            "Vec ste kupili/rezervisali maksimalan broj karti za dati dogadaj. Maksimalan broj karti je: "
		            	+String.valueOf(karta.getDogadjaj().getMaxKartiPoKorisniku()),
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		if (!karta.getDogadjaj().isNaplataPriRezervaciji() && korisnik.getWalletBalance() < 0) {
			JOptionPane.showMessageDialog(
		            parentDialog,
		            "Vase stanje racuna je u minusu i ne mozete izvrsiti nikakvu kupnju ili rezervaciju zbog toga!",
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		if (karta.getDogadjaj().isNaplataPriRezervaciji() && korisnik.getWalletBalance() < karta.getCijenaRezervacije()) {
			JOptionPane.showMessageDialog(
		            parentDialog,
		            "Nemate dovoljno novca da izvrsite rezervaciju!",
		            "Oopsie :(",
		            JOptionPane.INFORMATION_MESSAGE
		        );
			return;
		}
		if (karta.getDogadjaj().isNaplataPriRezervaciji()) {
			int response = JOptionPane.showOptionDialog(
					parentDialog,
					"Rezervacija se naplaćuje unaprijed, cijena naplate je: " +
							String.valueOf(karta.getCijenaRezervacije() / 100) + "."
							+ String.valueOf(karta.getCijenaRezervacije() % 100),
					"Zelite li rezervisati kartu? ",
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
        walletBalanceLabel.setText("Stanje racuna: "
            + String.valueOf(parent.korisnik.getWalletBalance() / 100)
            + "." 
            + String.valueOf(parent.korisnik.getWalletBalance() % 100));
    }
}
