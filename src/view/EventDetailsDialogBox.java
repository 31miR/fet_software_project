package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Dogadjaj;
import model.Lokacija;
import model.Organizator;

class DogadjajDetailsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public DogadjajDetailsPanel(Dogadjaj dogadjaj) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel nazivDogadjaja = new JLabel(dogadjaj.getNaziv());
        nazivDogadjaja.setFont(new Font("Arial", Font.BOLD, 14));
        nazivDogadjaja.setAlignmentX(LEFT_ALIGNMENT);
        add(nazivDogadjaja);

        JTextArea opisTextBox = new JTextArea(dogadjaj.getOpis());
        opisTextBox.setEditable(false);
        opisTextBox.setLineWrap(true);
        opisTextBox.setWrapStyleWord(true);
        opisTextBox.setBackground(getBackground());
        opisTextBox.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        opisTextBox.setAlignmentX(LEFT_ALIGNMENT);
        
        opisTextBox.setPreferredSize(new Dimension(300, 60));
        opisTextBox.setMaximumSize(new Dimension(600, 60));
        add(opisTextBox);

        JLabel datumVrijeme = new JLabel(dogadjaj.getDatum().toString());
        datumVrijeme.setFont(new Font("Arial", Font.PLAIN, 12));
        datumVrijeme.setAlignmentX(LEFT_ALIGNMENT);
        add(datumVrijeme);

        JLabel vrstaDogadjaja = new JLabel(dogadjaj.getVrsta());
        vrstaDogadjaja.setFont(new Font("Arial", Font.PLAIN, 12));
        vrstaDogadjaja.setAlignmentX(LEFT_ALIGNMENT);
        add(vrstaDogadjaja);

        JLabel podvrstaDogadjaja = new JLabel(dogadjaj.getPodvrsta());
        podvrstaDogadjaja.setFont(new Font("Arial", Font.PLAIN, 12));
        podvrstaDogadjaja.setAlignmentX(LEFT_ALIGNMENT);
        add(podvrstaDogadjaja);

        JLabel dogadjajZavrsio = new JLabel(dogadjaj.isZavrsio() ? "Dogadjaj je zavrsio!" : "");
        dogadjajZavrsio.setForeground(Color.RED);
        dogadjajZavrsio.setFont(new Font("Arial", Font.BOLD, 12));
        dogadjajZavrsio.setAlignmentX(LEFT_ALIGNMENT);
        add(dogadjajZavrsio);

        JLabel rezervacijeNaplacuju = new JLabel(dogadjaj.isNaplataPriRezervaciji() ? "Rezervacija se naplacuje" : "");
        rezervacijeNaplacuju.setForeground(Color.GREEN);
        rezervacijeNaplacuju.setFont(new Font("Arial", Font.BOLD, 12));
        rezervacijeNaplacuju.setAlignmentX(LEFT_ALIGNMENT);
        add(rezervacijeNaplacuju);
        
        add(Box.createVerticalGlue());
    }
}


class LokacijaDetailsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public LokacijaDetailsPanel(Lokacija lokacija) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel nazivLokacije = new JLabel(lokacija.getNaziv());
        nazivLokacije.setFont(new Font("Arial", Font.BOLD, 14));
        nazivLokacije.setAlignmentX(LEFT_ALIGNMENT);
        add(nazivLokacije);

        JLabel ukupanKapacitet = new JLabel("Ukupan kapacitet: " + String.valueOf(lokacija.getKapacitet()));
        ukupanKapacitet.setFont(new Font("Arial", Font.PLAIN, 12));
        ukupanKapacitet.setAlignmentX(LEFT_ALIGNMENT);
        add(ukupanKapacitet);

        JLabel grad = new JLabel(lokacija.getGrad());
        grad.setFont(new Font("Arial", Font.PLAIN, 12));
        grad.setAlignmentX(LEFT_ALIGNMENT);
        add(grad);

        JLabel adresa = new JLabel(lokacija.getAdresa());
        adresa.setFont(new Font("Arial", Font.PLAIN, 12));
        adresa.setAlignmentX(LEFT_ALIGNMENT);
        add(adresa);
        
        JPanel slikaPanel = new JPanel();
        slikaPanel.setPreferredSize(new Dimension(200, 200));
        slikaPanel.setMaximumSize(new Dimension(200, 200));
        slikaPanel.setBackground(Color.PINK);
        slikaPanel.setAlignmentX(LEFT_ALIGNMENT);

        ImageIcon imageIcon = new ImageIcon(lokacija.getSlika());
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel slikaLabel = new JLabel(new ImageIcon(scaledImage));
        slikaPanel.add(slikaLabel);
		add(slikaPanel);

        add(Box.createVerticalGlue());
    }
}


class OrganizatorDetailsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public OrganizatorDetailsPanel(Organizator organizator) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel imePrezime = new JLabel(organizator.getName() + " " + organizator.getLastName());
        imePrezime.setFont(new Font("Arial", Font.BOLD, 14));
        imePrezime.setAlignmentX(LEFT_ALIGNMENT);
        add(imePrezime);

        JLabel email = new JLabel(organizator.getEmail());
        email.setFont(new Font("Arial", Font.PLAIN, 12));
        email.setAlignmentX(LEFT_ALIGNMENT);
        add(email);
        
        JLabel phone = new JLabel(organizator.getPhone());
        phone.setFont(new Font("Arial", Font.PLAIN, 12));
        phone.setAlignmentX(LEFT_ALIGNMENT);
        add(phone);

        JLabel adresa = new JLabel(organizator.getAddress());
        adresa.setFont(new Font("Arial", Font.PLAIN, 12));
        adresa.setAlignmentX(LEFT_ALIGNMENT);
        add(adresa);

        add(Box.createVerticalGlue());
    }
}




public class EventDetailsDialogBox extends JDialog {
	private static final long serialVersionUID = 1L;
	private Dogadjaj dogadjaj;
	private Lokacija lokacija;
	private Organizator organizator;
	private JPanel dataPanel;
	public EventDetailsDialogBox(Dogadjaj dogadjaj) {
		super((JFrame)null, "Detalji dogadjaja", true);
		this.setSize(new Dimension(500,500));
        this.dogadjaj = dogadjaj;
        this.lokacija = dogadjaj.getLokacija();
        this.organizator = dogadjaj.getOrganizator();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        
        JButton dogadjajButton = new JButton("detalji dogadjaja");
        dogadjajButton.addActionListener((e) -> {
        	dogadjajButtonPressed();
        });
        topPanel.add(dogadjajButton);
        JButton lokacijaButton = new JButton("detalji lokacije");
        lokacijaButton.addActionListener((e) -> {
        	lokacijaButtonPressed();
        });
        topPanel.add(lokacijaButton);
        JButton organizatorButton = new JButton("detalji o organizatoru");
        organizatorButton.addActionListener((e) -> {
        	organizatorButtonPressed();
        });
        topPanel.add(organizatorButton);
        
        dataPanel = new DogadjajDetailsPanel(dogadjaj);
        
        this.add(topPanel, BorderLayout.NORTH);
        this.add(dataPanel, BorderLayout.CENTER);
	}
	private void organizatorButtonPressed() {
		this.remove(dataPanel);
		dataPanel = new OrganizatorDetailsPanel(organizator);
		this.add(dataPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	private void lokacijaButtonPressed() {
		this.remove(dataPanel);
		dataPanel = new LokacijaDetailsPanel(lokacija);
		this.add(dataPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	private void dogadjajButtonPressed() {
		this.remove(dataPanel);
		dataPanel = new DogadjajDetailsPanel(dogadjaj);
		this.add(dataPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
