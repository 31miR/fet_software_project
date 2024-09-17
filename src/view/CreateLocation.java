package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Administrator;
import model.Lokacija;
import model.LokacijaDAO;
import model.Sektor;
import model.SektorDAO;

public class CreateLocation extends JFrame {
	private JTextField nazivField;
	private JTextField gradField;
	private JTextField adresaField;
	private JTextField kapacitetField;
	private JTextField slikaField;
	private JButton browseButton;
	private JButton saveButton;
	private JButton cancelButton;

	private LokacijaDAO lokacijaDAO;
	private SektorDAO sektorDAO;
	private List<Sektor> sektori;
	private int ukupniKapacitet;
	private Administrator admin;

	public CreateLocation(Administrator admin) {
		this.admin = admin;

		// Postavke prozora
		setTitle("Create Location");
		setBounds(450, 190, 1014, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel contentPane = new JPanel();
		Border emptyBorder = BorderFactory.createEmptyBorder(5, 10, 5, 5);
		contentPane.setBorder(emptyBorder);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(29, 190, 166));

		// Prikaz slike
		ImageIcon imageIcon = new ImageIcon("resources/logo.png");
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(scaledImage);

		JLabel label = new JLabel(imageIcon);
		label.setBounds(25, 200, 400, 300);
		contentPane.add(label);

		// Inicijalizacija DAO klasa
		lokacijaDAO = new LokacijaDAO();
		sektorDAO = new SektorDAO();

		// Inicijalizacija polja i dugmadi
		nazivField = new JTextField();
		gradField = new JTextField();
		adresaField = new JTextField();
		kapacitetField = new JTextField();
		slikaField = new JTextField();
		browseButton = new JButton("Browse..");
		saveButton = new JButton("Save");
		cancelButton = new JButton("Back");

		// Font za komponente
		Font font = new Font("Chilanka", Font.PLAIN, 26);

		// Postavljanje fonta na JLabel i JTextField
		JLabel nazivLabel = new JLabel("Name Loc:");
		nazivLabel.setFont(font);
		JLabel gradLabel = new JLabel("City:");
		gradLabel.setFont(font);
		JLabel adresaLabel = new JLabel("Address:");
		adresaLabel.setFont(font);
		JLabel kapacitetLabel = new JLabel("Capacity:");
		kapacitetLabel.setFont(font);
		JLabel slikaLabel = new JLabel("Picture:");
		slikaLabel.setFont(font);

		nazivField.setFont(font);
		gradField.setFont(font);
		adresaField.setFont(font);
		kapacitetField.setFont(font);
		slikaField.setFont(font);
		browseButton.setFont(font);
		saveButton.setFont(font);
		cancelButton.setFont(font);

		// Postavljanje pozicija i veličina komponenti
		nazivLabel.setBounds(400, 100, 150, 100);
		nazivField.setBounds(550, 100, 300, 50);
		gradLabel.setBounds(400, 200, 150, 50);
		gradField.setBounds(550, 200, 300, 50);
		adresaLabel.setBounds(400, 300, 150, 50);
		adresaField.setBounds(550, 300, 300, 50);
		kapacitetLabel.setBounds(400, 400, 150, 50);
		kapacitetField.setBounds(550, 400, 300, 50);
		slikaLabel.setBounds(400, 500, 150, 50);
		slikaField.setBounds(550, 500, 300, 50);
		browseButton.setBounds(860, 500, 160, 50);
		saveButton.setBounds(550, 650, 200, 50);
		cancelButton.setBounds(800, 650, 200, 50);

		// Dodavanje komponenti na panel
		contentPane.add(nazivLabel);
		contentPane.add(nazivField);
		contentPane.add(gradLabel);
		contentPane.add(gradField);
		contentPane.add(adresaLabel);
		contentPane.add(adresaField);
		contentPane.add(kapacitetLabel);
		contentPane.add(kapacitetField);
		contentPane.add(slikaLabel);
		contentPane.add(slikaField);
		contentPane.add(browseButton);
		contentPane.add(saveButton);
		contentPane.add(cancelButton);

		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg", "gif"));
				int returnValue = fileChooser.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();

					// Folder u koji ćemo kopirati sliku
					File destFolder = new File("resources");
					if (!destFolder.exists()) {
						destFolder.mkdirs(); // Kreiraj folder ako ne postoji
					}

					// Odabir novog imena slike
					String newFileName = JOptionPane
							.showInputDialog("Enter new name for the image (without extension):");
					if (newFileName == null || newFileName.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Invalid file name.");
						return;
					}

					// Preuzimanje ekstenzije originalne datoteke
					String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.'));
					File newFile = new File(destFolder, newFileName + fileExtension);

					try {
						// Kopiranje slike u "resources" folder
						Files.copy(selectedFile.toPath(), newFile.toPath());

						// Postavi putanju nove slike u tekstualno polje
						slikaField.setText(newFile.getAbsolutePath());

						JOptionPane.showMessageDialog(null, "Image successfully copied and renamed.");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Error while copying the file: " + ex.getMessage());
					}
				}
			}
		});

		// Postavljanje akcije za dugme "Save"
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					createSectors();
					// Prikupljanje podataka iz polja
					String naziv = nazivField.getText();
					String grad = gradField.getText();
					String adresa = adresaField.getText();
					String slika = slikaField.getText();

					// Kreiraj instancu Lokacija
					Lokacija lokacija = new Lokacija();
					lokacija.setNaziv(naziv);
					lokacija.setGrad(grad);
					lokacija.setAdresa(adresa);
					lokacija.setKapacitet(ukupniKapacitet);
					lokacija.setSlika(slika);

					// Spremi lokaciju u bazu podataka
					lokacijaDAO.addLocation(lokacija);

					// Ažuriraj lokaciju u sektorima
					for (Sektor sektor : sektori) {
						sektor.setLokacija(lokacija);
						sektorDAO.addSektor(sektor);
					}

					JOptionPane.showMessageDialog(null,
							"Location and sectors have been successfully added to the database.");
					AdminView view = new AdminView(admin);
					view.setVisible(true);
					dispose();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
				}
			}
		});

		// Postavljanje akcije za dugme "Cancel"
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminView view = new AdminView(admin);
				view.setVisible(true);
				dispose();
			}
		});

	}

	public void createSectors() {
		while (true) {
			try {
				ukupniKapacitet = Integer.parseInt(kapacitetField.getText());
				int brojSektora = Integer.parseInt(
						JOptionPane.showInputDialog("How many sectors would you like to add for this location?"));
				sektori = new ArrayList<>();
				int ukupniKapacitetSektora = 0;

				for (int i = 0; i < brojSektora; i++) {
					String nazivSektora = JOptionPane.showInputDialog("Enter the name of the sector " + (i + 1) + ":");
					int kapacitetSektora = Integer
							.parseInt(JOptionPane.showInputDialog("Enter sector capacity: " + nazivSektora + ":"));
					ukupniKapacitetSektora += kapacitetSektora;

					if (ukupniKapacitetSektora > ukupniKapacitet) {
						JOptionPane.showMessageDialog(null,
								"The total capacity of sectors must not exceed the location's capacity.");
						ukupniKapacitetSektora -= kapacitetSektora;
						i--;
						continue;
					}

					// Kreiraj novi sektor
					Sektor sektor = new Sektor();
					sektor.setNaziv(nazivSektora);
					sektor.setKapacitet(kapacitetSektora);
					sektor.setLokacija(new Lokacija()); // Placeholder, ažurira se kasnije

					sektori.add(sektor);
				}

				JOptionPane.showMessageDialog(null, "Sectors have been successfully added. Click 'Save' to save.");
				break;
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
			}
		}
	}

	public static void main(String[] args) {
		Administrator admin = new Administrator(); // Inicijalizuj Administrator objekat ovde
		CreateLocation createLocationFrame = new CreateLocation(admin);
		createLocationFrame.setVisible(true);
	}
}