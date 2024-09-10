package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.border.EmptyBorder;
import model.Korisnik;
import model.KorisnikDAO;
import model.Organizator;
import model.OrganizatorDAO;
import model.Administrator;

public class RequestUsers extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private KorisnikDAO korisnikDAO;
    private OrganizatorDAO organizatorDAO;
    private JButton loadMoreButton;
    private JButton backButton;
    private int offset = 0;

    public RequestUsers(Administrator admin) {
        korisnikDAO = new KorisnikDAO();
        organizatorDAO = new OrganizatorDAO();

        setTitle("User and Organizer Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 1000, 800);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Kreiraj tabelu
        String[] columnNames = {"Type", "Username", "Name", "LastName", "Accept", "Reject"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5;
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 4 || column == 5) {
                    return new ButtonRenderer();
                } else {
                    return new CustomTableCellRenderer();
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 4 || column == 5) {
                    return new ButtonEditor();
                }
                return super.getCellEditor(row, column);
            }
        };

        table.setRowHeight(40);

        loadRequests();

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        loadMoreButton = new JButton("Load More");
        buttonPanel.add(loadMoreButton);
        
        backButton = new JButton ("Back");
        buttonPanel.add(backButton);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        loadMoreButton.addActionListener(e -> {
            offset += 30;
            loadRequests();
        });
        
        backButton.addActionListener(e -> {
            AdminView back = new AdminView(admin);
            back.setVisible(true);
            dispose();
        });
    }
    
    private void loadRequests() {
        List<Korisnik> userRequests = korisnikDAO.getLimitedPending(offset, 30);
        List<Organizator> organizatorRequests = organizatorDAO.getLimitedPending(offset, 30);

        model.setRowCount(0); // Clear existing rows

        for (Korisnik korisnik : userRequests) {
            model.addRow(new Object[]{
                "Korisnik",
                korisnik.getUsername(),
                korisnik.getName(),
                korisnik.getLastName(),
                korisnik, // Korisnik objekat
                null // Organizator objekat
            });
        }

        for (Organizator organizator : organizatorRequests) {
            model.addRow(new Object[]{
                "Organizator",
                organizator.getUsername(),
                organizator.getName(),
                organizator.getLastName(),
                null, // Korisnik objekat
                organizator // Organizator objekat
            });
        }
    }

    private class CustomTableCellRenderer extends JLabel implements TableCellRenderer {
        public CustomTableCellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText(value != null ? value.toString() : "");
            setBackground(new Color(20, 190, 166));
            if (isSelected) {
                setBackground(Color.YELLOW);
            }
            setHorizontalAlignment(CENTER);
            return this;
        }
    }

    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton acceptButton;
        private JButton rejectButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            acceptButton = new JButton("Accept");
            rejectButton = new JButton("Reject");

            acceptButton.setBackground(new Color(95, 95, 95));
            rejectButton.setBackground(new Color(95, 95, 95));
            acceptButton.setForeground(Color.WHITE);
            rejectButton.setForeground(Color.WHITE);

            add(acceptButton);
            add(rejectButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            acceptButton.setVisible(column == 4);
            rejectButton.setVisible(column == 5);
            return this;
        }
    }

    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton acceptButton;
        private JButton rejectButton;
        private JTable table;
        private int editingRow;

        public ButtonEditor() {
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

            acceptButton = new JButton("Accept");
            rejectButton = new JButton("Reject");

            acceptButton.setBackground(new Color(95, 95, 95));
            rejectButton.setBackground(new Color(95, 95, 95));

            acceptButton.setForeground(Color.WHITE);
            rejectButton.setForeground(Color.WHITE);

            acceptButton.addActionListener(e -> {
                if (table != null) {
                    int row = table.convertRowIndexToModel(editingRow);
                    if (row >= 0 && row < model.getRowCount()) {
                        String userType = (String) model.getValueAt(row, 0);
                        if ("Korisnik".equals(userType)) {
                            Korisnik selectedUser = (Korisnik) model.getValueAt(row, 4);
                            selectedUser.setProfileApproved(true);
                            korisnikDAO.updateKorisnik(selectedUser);
                        } else {
                            Organizator selectedOrg = (Organizator) model.getValueAt(row, 5);
                            selectedOrg.setProfileApproved(true);
                            organizatorDAO.updateOrganizator(selectedOrg);
                        }
                        model.removeRow(row);
                    }
                    stopCellEditing();
                }
            });

            rejectButton.addActionListener(e -> {
                if (table != null) {
                    int row = table.convertRowIndexToModel(editingRow);
                    if (row >= 0 && row < model.getRowCount()) {
                        String userType = (String) model.getValueAt(row, 0);
                        if ("Korisnik".equals(userType)) {
                            Korisnik selectedUser = (Korisnik) model.getValueAt(row, 4);
                            selectedUser.setProfileApproved(false);
                            korisnikDAO.updateKorisnik(selectedUser);
                        } else {
                            Organizator selectedOrg = (Organizator) model.getValueAt(row, 5);
                            selectedOrg.setProfileApproved(false);
                            organizatorDAO.updateOrganizator(selectedOrg);
                        }
                        model.removeRow(row);
                    }
                    stopCellEditing();
                }
            });

            panel.add(acceptButton);
            panel.add(rejectButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.editingRow = row;
            return panel;
        }
       


        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
}
