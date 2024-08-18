package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.border.EmptyBorder;

import model.Korisnik;
import model.KorisnikDAO;

public class RequestUsers extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private KorisnikDAO korisnikDAO;

    public RequestUsers() {
        korisnikDAO = new KorisnikDAO(); // Inicijalizuj DAO

        setTitle("User Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 800, 600);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Kreiraj tabelu
        String[] columnNames = {"Username", "Name", "LastName", "Actions"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only Actions column is editable (for buttons)
                return column == 3;
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                // Render the "Actions" column with buttons
                if (column == 3) {
                    return new ButtonRenderer();
                } else {
                    return new CustomTableCellRenderer();
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 3) {
                    return new ButtonEditor(); // Custom editor for buttons
                }
                return super.getCellEditor(row, column);
            }
        };

        // Povećaj visinu redova
        table.setRowHeight(40); // Postavlja visinu svakog reda na 40 piksela

        loadUserRequests();

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private void loadUserRequests() {
        List<Korisnik> userRequests = korisnikDAO.getLimitedPending(0, 30); // Dohvati zahtjeve iz baze

        // Clear the existing data
        model.setRowCount(0);

        for (Korisnik korisnik : userRequests) {
            Object[] rowData = {
                korisnik.getUsername(),
                korisnik.getName(),
                korisnik.getLastName(),
                "Buttons" // Placeholder for buttons, will be rendered by the custom renderer
            };
            model.addRow(rowData); // Dodaj red u tabelu
        }
    }

    // Custom renderer for other columns
    private class CustomTableCellRenderer extends JLabel implements TableCellRenderer {

        public CustomTableCellRenderer() {
            setOpaque(true); // Make the background visible
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText(value != null ? value.toString() : "");

            // Bojenje pozadine redova u zeleno
            setBackground(new Color(20,190, 166));


            // Ako je red selektovan, možeš postaviti drugu boju
            if (isSelected) {
                setBackground(Color.YELLOW); // Na primjer, žuta boja za selekciju
            }

            // Poravnanje teksta
            setHorizontalAlignment(CENTER);

            return this;
        }
    }

    // Custom renderer for buttons
    private class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton acceptButton;
        private JButton rejectButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout());

            acceptButton = new JButton("Accept");
            rejectButton = new JButton("Reject");

            // Obojimo dugmad u sivo
            acceptButton.setBackground(new Color(95, 95, 95));
            rejectButton.setBackground(new Color(95, 95, 95));
            // Postavimo boju teksta na dugmadima
            acceptButton.setForeground(Color.WHITE);
            rejectButton.setForeground(Color.WHITE);

            add(acceptButton);
            add(rejectButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    // Custom editor for buttons
    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton acceptButton;
        private JButton rejectButton;

        public ButtonEditor() {
            panel = new JPanel();
            panel.setLayout(new FlowLayout());

            acceptButton = new JButton("Accept");
            rejectButton = new JButton("Reject");

            // Obojimo dugmad u sivo
            acceptButton.setBackground(new Color(95, 95, 95));
            rejectButton.setBackground(new Color(95, 95, 95));

            // Postavimo boju teksta na dugmadima
            acceptButton.setForeground(Color.WHITE);
            rejectButton.setForeground(Color.WHITE);

            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table.getSelectedRow();
                    Korisnik selectedUser = korisnikDAO.getLimitedPending(0, 30).get(row);
                    selectedUser.setProfileApproved(true);
                    korisnikDAO.updateKorisnik(selectedUser);

                    // Remove the row from the table model
                    model.removeRow(row);

                    fireEditingStopped(); // Zaustavi uređivanje nakon klika
                }
            });

            rejectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table.getSelectedRow();
                    Korisnik selectedUser = korisnikDAO.getLimitedPending(0, 30).get(row);
                    selectedUser.setProfileApproved(false);
                    korisnikDAO.updateKorisnik(selectedUser);

                    // Remove the row from the table model
                    model.removeRow(row);

                    fireEditingStopped(); // Zaustavi uređivanje nakon klika
                }
            });

            panel.add(acceptButton);
            panel.add(rejectButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
}