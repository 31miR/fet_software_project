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

import model.Administrator;
import model.AdministratorDAO;
import model.Dogadjaj;
import model.DogadjajDAO;

public class RequestEvents extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private DogadjajDAO dogadjajDAO;
    private JButton loadMoreButton;
    private JButton backButton;
    private Administrator administrator; // Currently logged-in administrator
    private int offset = 0; // Variable to track the position in the database


    public RequestEvents(Administrator administrator) { // Constructor now accepts Administrator
        this.administrator = administrator; // Set the administrator
        dogadjajDAO = new DogadjajDAO(); // Initialize DAO

        setTitle("Event Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 800, 600);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Create table
        String[] columnNames = {"ID", "Event Name", "Status", "Actions"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only Actions column is editable (for buttons)
                return column == 3;
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 3) {
                    return new ButtonRenderer(); // Custom renderer for buttons
                } else {
                    return new CustomTableCellRenderer(); // Custom renderer for other columns
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

        // Increase row height
        table.setRowHeight(40);

        loadEventRequests();

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Create button panel at the bottom
        JPanel buttonPanel = new JPanel();
        loadMoreButton = new JButton("Load More");
        buttonPanel.add(loadMoreButton);
       
        

        backButton = new JButton("Back");
        buttonPanel.add(backButton);

        // Add buttonPanel to the bottom of the window
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Action for the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                AdminView adminView = new AdminView(administrator); // Return to AdminView with the logged-in administrator
                adminView.setVisible(true);
            }
        });

        // Action for the Load More button
        loadMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offset += 30; // Increase offset to load the next set of requests
                loadEventRequests();
            }
        });
    }

    private void loadEventRequests() {
        List<Dogadjaj> eventRequests = dogadjajDAO.getLimitedPending(offset, 30);
        for (Dogadjaj dogadjaj : eventRequests) {
            // Process and add each event to the table
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
            setBackground(new Color(20, 190, 166));

            if (isSelected) {
                setBackground(Color.YELLOW);
            }

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
            acceptButton = new JButton("Approve");
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
            acceptButton = new JButton("Approve");
            rejectButton = new JButton("Reject");
            acceptButton.setBackground(new Color(95, 95, 95));
            rejectButton.setBackground(new Color(95, 95, 95));
            acceptButton.setForeground(Color.WHITE);
            rejectButton.setForeground(Color.WHITE);

            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table.convertRowIndexToModel(table.getSelectedRow());
                    Dogadjaj selectedEvent = dogadjajDAO.getLimitedPending(0, 30).get(row);
                    selectedEvent.setDogadjajApproved(true);
                    dogadjajDAO.updateDogadjaj(selectedEvent);
                    model.removeRow(row);
                    fireEditingStopped(); // Stop editing after button click
                }
            });

            rejectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table.convertRowIndexToModel(table.getSelectedRow());
                    Dogadjaj selectedEvent = dogadjajDAO.getLimitedPending(0, 30).get(row);
                    selectedEvent.setDogadjajApproved(false);
                    dogadjajDAO.updateDogadjaj(selectedEvent);
                    model.removeRow(row);
                    fireEditingStopped(); // Stop editing after button click
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
