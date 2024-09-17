package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import model.Karta;
import model.KartaDAO;
import model.KorisnikDAO;
import model.SektorDAO;


class BoughtTicket extends JPanel {
	private static final long serialVersionUID = 1L;
	BoughtTicketsForUserDialogBox parentDialog;
	Karta karta;
	public BoughtTicket(BoughtTicketsForUserDialogBox parentDialog, Karta karta) {
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
		
		JButton generisiPDFButton = new JButton("generisi PDF");
		generisiPDFButton.addActionListener((e) -> {
			generisiPDFButtonPressed();
		});
		buttonPanel.add(generisiPDFButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(infoPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);
	}
	private void generisiPDFButtonPressed() {
		JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int result = fileChooser.showSaveDialog(null);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File selectedFolder = fileChooser.getSelectedFile();
	        String path = selectedFolder.getAbsolutePath();
	        
	        // Koristi novi API iz iText 7
	        try {
	            PdfWriter writer = new PdfWriter(path + "/ticket_" + String.valueOf(karta.getKarta_id()) + ".pdf");
	            PdfDocument pdf = new PdfDocument(writer);
	            Document document = new Document(pdf);
	            
	            // Dodaj podatke o karti
	            document.add(new Paragraph("Cijena karte: " + String.valueOf(karta.getCijena() / 100) + "."
	            		+ String.valueOf(karta.getCijena() % 100)));
	            document.add(new Paragraph("Sjediste: " + karta.getSjediste()));
	            document.add(new Paragraph("Sektor: " + karta.getSektor().getNaziv()));
	            document.add(new Paragraph("Dogadjaj: " + karta.getDogadjaj().getNaziv()));
	            document.add(new Paragraph("Datum: " + karta.getDogadjaj().getDatum()));
	            
	            document.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
}

public class BoughtTicketsForUserDialogBox extends JDialog {
	private static final long serialVersionUID = 1L;
	KorisnikDAO korisnikDAO = new KorisnikDAO();
	SektorDAO sektorDAO = new SektorDAO();
	KartaDAO kartaDAO = new KartaDAO();
	KorisnikAndDogadjajListView parent;
	JPanel topPanel;
	JPanel mainContentPanel;
	List<Karta> karte;
    public BoughtTicketsForUserDialogBox(KorisnikAndDogadjajListView parent) {
    	super(parent, "Add Wallet Balance", true);
    	this.parent = parent;
    	
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Kupljene karte - " + parent.korisnik.getName() + " " + parent.korisnik.getLastName());
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        topPanel.add(new JLabel("Lista svih kupljenih karti"));
        
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
    public void updateTicketList() {
    	mainContentPanel.removeAll();
    	karte = kartaDAO.getBoughtTicketsForUser(parent.korisnik);
    	for (Karta i : karte) {
    		mainContentPanel.add(new BoughtTicket(this, i));
    	}
    	mainContentPanel.revalidate();
    	mainContentPanel.repaint();
    }
}
