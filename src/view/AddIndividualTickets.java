package view;

	import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	import javax.swing.JDialog;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JTextField;
	import javax.swing.JButton;

	import model.Dogadjaj;
	import model.Karta;
	import model.KartaDAO;
	import model.Sektor;

	public class AddIndividualTickets extends JDialog {
	    private KartaDAO kartaDAO;
	    private Dogadjaj dogadjaj;
	    private Sektor sektor;
	    private int ticketsAdded = 0;

	    public AddIndividualTickets(Dogadjaj dogadjaj, Sektor sektor, KartaDAO kartaDAO) {
	        this.kartaDAO = kartaDAO;
	        this.dogadjaj = dogadjaj;
	        this.sektor = sektor;

	        setTitle("Add Tickets Individually");
	        setLayout(new GridLayout(0, 2));

	        JLabel lblPrice = new JLabel("Price:");
	        JTextField txtPrice = new JTextField(10);

	        JLabel lblResPrice = new JLabel("Reservation Price:");
	        JTextField txtResPrice = new JTextField(10);

	        JButton btnAdd = new JButton("Add Ticket");
	        JButton btnNext = new JButton("Next");
	        
	        btnAdd.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                addTicket();
	            }
	        });

	        btnNext.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                addTicket();
	                if (ticketsAdded >= sektor.getKapacitet()) {
	                    JOptionPane.showMessageDialog(AddIndividualTickets.this, "The sector is full.");
	                    btnNext.setEnabled(false);
	                } else {
	                    // Clear fields for the next ticket
	                    txtPrice.setText("");
	                    txtResPrice.setText("");
	                }
	            }
	        });

	        // Validate fields and enable/disable Next button
	        ActionListener fieldValidator = new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                btnNext.setEnabled(!txtPrice.getText().trim().isEmpty() && !txtResPrice.getText().trim().isEmpty());
	            }
	        };

	        txtPrice.addActionListener(fieldValidator);
	        txtResPrice.addActionListener(fieldValidator);

	        add(lblPrice);
	        add(txtPrice);
	        add(lblResPrice);
	        add(txtResPrice);
	        add(btnAdd);
	        add(btnNext);

	        pack();
	        setLocationRelativeTo(null);
	        btnNext.setEnabled(false); // Initial state
	    }

	    private void addTicket() {
	        try {
	            int price = Integer.parseInt(txtPrice.getText());
	            int resPrice = Integer.parseInt(txtResPrice.getText());

	            // Create a new Karta object
	            Karta karta = new Karta();
	            karta.setCijena(price);
	            karta.setCijenaRezervacije(resPrice);
	            karta.setDogadjaj(dogadjaj);
	            karta.setSektor(sektor);
	            karta.setSjediste("neodredjeno");

	            // Use KartaDAO to add the individual ticket
	            kartaDAO.addTicket(karta);
	            ticketsAdded++;
	            JOptionPane.showMessageDialog(AddIndividualTickets.this, "Ticket added successfully.");
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(AddIndividualTickets.this, "Please enter valid numbers.");
	        }
	    }
	}

}
