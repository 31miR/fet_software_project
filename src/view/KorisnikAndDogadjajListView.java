package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import model.Korisnik;

public class KorisnikAndDogadjajListView extends JFrame {
    private static final long serialVersionUID = 1L;
    Korisnik korisnik;
    public KorisnikAndDogadjajListView(Korisnik k) {
    	this.korisnik = k;
        setTitle("Karta - korisnicki panel");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Vrh opcije
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(new JLabel(korisnik.getName() + " " + korisnik.getLastName()));
        topPanel.add(new JLabel(String.valueOf(korisnik.getWalletBalance()/100) + "." + 
        						String.valueOf(korisnik.getWalletBalance()%100) + "$"));
        JButton addMoneyButton = new JButton("add money");
        addMoneyButton.addActionListener((e) -> {
        	addMoneyButtonPressed();
        });
        topPanel.add(addMoneyButton);
        JButton profileSettingsButton = new JButton("profile settings");
        profileSettingsButton.addActionListener((e) -> {
        	profileSettingsButtonPressed();
        });
        topPanel.add(profileSettingsButton);
        JButton boughtTicketsButton = new JButton("bought tickets");
        boughtTicketsButton.addActionListener((e) -> {
        	boughtTicketsButtonPressed();
        });
        topPanel.add(boughtTicketsButton);
        JButton reservedTicketsButton = new JButton("reserved tickets");
        reservedTicketsButton.addActionListener((e) -> {
        	reservedTicketsButtonPressed();
        });
        topPanel.add(reservedTicketsButton);
        JButton logoutButton = new JButton("log out");
        logoutButton.addActionListener((e) -> {
        	logoutButtonPressed();
        });
        topPanel.add(logoutButton);

        // Opcije za pretragu dogadjaja
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        searchPanel.add(new JComboBox<>(new String[]{"City1", "City2"}));
        searchPanel.add(new JComboBox<>(new String[]{"Type1", "Type2"}));
        searchPanel.add(new JComboBox<>(new String[]{"Subtype1", "Subtype2"}));
        JTextField maxPriceTF = new JTextField("Max Price", 8);
        addPlaceholderEffect(maxPriceTF, "Max Price");
        searchPanel.add(maxPriceTF);
        JTextField minPriceTF = new JTextField("Min Price", 8);
        addPlaceholderEffect(minPriceTF, "Min Price");
        searchPanel.add(minPriceTF);
        JTextField searchBarTF = new JTextField("search bar", 15);
        addPlaceholderEffect(searchBarTF, "search bar");
        searchPanel.add(searchBarTF);
        JButton searchButton = new JButton("search");
        searchButton.addActionListener((e) -> {
        	searchButtonPressed();
        });
        searchPanel.add(searchButton);

        // Panel za dogadjaje
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.BLUE);
        mainContentPanel.setPreferredSize(new Dimension(600, 300)); // size can be adjusted
        // TODO: implement JPanel for dogadjaji, sad-zasad

        // Za promjenu stranice
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton prevPageButton = new JButton("prev page");
        prevPageButton.addActionListener((e) -> {
        	prevPagePressed();
        });
        bottomPanel.add(prevPageButton);
        JButton nextPageButton = new JButton("next page");
        nextPageButton.addActionListener((e) -> {
        	nextPagePressed();
        });
        bottomPanel.add(nextPageButton);

        // uvezivanje YAY
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(mainContentPanel, BorderLayout.CENTER);
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // I kao, da mozes skrolat
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
    private void nextPagePressed() {
		// TODO Auto-generated method stub
		
	}
	private void prevPagePressed() {
		// TODO Auto-generated method stub
		
	}
	private void searchButtonPressed() {
		// TODO Auto-generated method stub
		
	}
	private void addPlaceholderEffect(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }
	private void logoutButtonPressed() {
		Login view = new Login();
		dispose();
		view.setVisible(true);
	}
	private void reservedTicketsButtonPressed() {
		// TODO Auto-generated method stub
		
	}
	private void boughtTicketsButtonPressed() {
		// TODO Auto-generated method stub
		
	}
	private void profileSettingsButtonPressed() {
		// TODO Auto-generated method stub
		
	}
	private void addMoneyButtonPressed() {
		WalletBalanceView view = new WalletBalanceView(korisnik);
		dispose();
		view.setVisible(true);
	}
}
