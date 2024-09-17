package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Dogadjaj;
import model.DogadjajDAO;
import model.Izmjene;
import model.IzmjeneDAO;
import model.KartaDAO;
import model.SektorDAO;


class ChangesRequestPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	RequestChanges parentDialog;
	IzmjeneDAO izmjeneDAO = new IzmjeneDAO();
	DogadjajDAO dogadjajDAO = new DogadjajDAO();
	JButton odbijButton;
	JButton prihvatiButton;
	Izmjene izmjene;
	public ChangesRequestPanel(RequestChanges parentDialog, Izmjene izmjene) {
		this.parentDialog = parentDialog;
		this.izmjene = izmjene;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 100));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		infoPanel.add(new JLabel("Table name: " + izmjene.getImeTabele()));
		infoPanel.add(new JLabel("Entity id: " + izmjene.getStringIdEntitija()));
		if (izmjene.getImeTabele().equals("Dogadjaj")) {
			Dogadjaj dogadjaj = dogadjajDAO.searchById(Integer.parseInt(izmjene.getStringIdEntitija()));
			infoPanel.add(new JLabel("Event name is: " + dogadjaj.getNaziv()));
		}
		infoPanel.add(new JLabel("Column name: " + izmjene.getImeKolone()));
		infoPanel.add(new JLabel("New value: " + izmjene.getNovaVrijednost()));
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		odbijButton = new JButton("decline event");
		odbijButton.addActionListener((e) -> {
			odbijButtonPressed();
		});
		buttonPanel.add(odbijButton);
		prihvatiButton = new JButton("accept event");
		prihvatiButton.addActionListener((e) -> {
			prihvatiButtonPressed();
		});
		buttonPanel.add(prihvatiButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(infoPanel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);
	}
	private void prihvatiButtonPressed() {
		izmjeneDAO.acceptChange(izmjene);
		prihvatiButton.setEnabled(false);
		odbijButton.setEnabled(false);
	}
	private void odbijButtonPressed() {
		izmjeneDAO.deleteChange(izmjene);
		prihvatiButton.setEnabled(false);
		odbijButton.setEnabled(false);
	}
}

public class RequestChanges extends JDialog {
	private static final long serialVersionUID = 1L;
	SektorDAO sektorDAO = new SektorDAO();
	KartaDAO kartaDAO = new KartaDAO();
	DogadjajDAO dogadjajDAO = new DogadjajDAO();
	IzmjeneDAO izmjeneDAO = new IzmjeneDAO();
	AdminView parent;
	JPanel topPanel;
	JLabel walletBalanceLabel;
	JPanel mainContentPanel;
    public RequestChanges(AdminView parent) {
    	super(parent, "Event requests", true);
    	this.parent = parent;
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Edit requests:");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setPreferredSize(new Dimension(600, 1000));
        
        updateRequestList();

        add(topPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);

        // I kao, da mozes skrolat
        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }
    public void updateRequestList() {
    	mainContentPanel.removeAll();
    	
    	List<Izmjene> list = izmjeneDAO.getAllChanges();
    	for (Izmjene i : list) {
    		mainContentPanel.add(new ChangesRequestPanel(this, i));
    	}
    	
    	mainContentPanel.revalidate();
    	mainContentPanel.repaint();
    }
}
