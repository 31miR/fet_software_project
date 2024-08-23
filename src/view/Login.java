package view;


import java.awt.Color;

import javax.swing.border.BevelBorder;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


import model.Administrator;
import model.Korisnik;
import model.Organizator;
import model.AdministratorDAO;
import model.OrganizatorDAO;
import model.KorisnikDAO;
import view.AdminView;;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel label;
    private JPanel contentPane;
    
    private KorisnikDAO userController;
    private AdministratorDAO adminController;
    private OrganizatorDAO orgController;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton RegisterButton;
    private JButton guestButton;
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(29, 190, 166)); 
        setTitle("Karta");
        ImageIcon icon = new ImageIcon("resources/logo.png");
        setIconImage(icon.getImage());
       
        
        
        ImageIcon imageIcon = new ImageIcon("resources/logo.png"); // Postavi putanju do svoje slike
        Image image = imageIcon.getImage(); // Transformacija slike
        Image scaledImage = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH); // Promijeni veličinu slike
        imageIcon = new ImageIcon(scaledImage); // Ponovo kreiraj ImageIcon sa promijenjenom slikom
        
        JLabel label = new JLabel(imageIcon);
        label.setBounds(50, 100, 400, 300);
        contentPane.add(label);
        
        contentPane.setBorder(new LineBorder(new Color(95,95,95), 7));
        

        JLabel lblNewLabel = new JLabel("Dobrodosli u Kartu!");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Chilanka", Font.ITALIC, 46));
        lblNewLabel.setBounds(500, 12, 600, 95);
        lblNewLabel.setForeground(new Color(95, 95, 95)); 
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        textField.setBounds(650, 170, 281, 50);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Chilanka", Font.PLAIN, 32));
        passwordField.setBounds(650, 286, 281, 50);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(new Color(51, 51, 51)); 
        lblUsername.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblUsername.setBounds(450, 166, 193, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(new Color(51, 51, 51)); 
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Chilanka", Font.PLAIN, 31));
        lblPassword.setBounds(450, 286, 193, 52);
        contentPane.add(lblPassword);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
        loginButton.setBounds(545, 392, 162, 50);
        loginButton.setForeground(new Color(51, 51, 51)); 
        loginButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        loginButton.addActionListener(new ActionListener() {
            	 @Override
                 public void actionPerformed(ActionEvent e) {
                     String username = textField.getText();
                     String password = new String(passwordField.getPassword());
                    
                     
                     try {
                     	Administrator admin;
                         // Pokušaj prijave kao admin
                         admin = adminController.searchByUserName(username);
                         if (admin != null) {
                        	 if(admin.getPassword().equals(password)) {                        	 
                             System.out.println("Prijava uspješna!");
                             AdminView view = new AdminView();
                             view.setVisible(true);
                             dispose();
                             
                         }
                         }
                         // Pokušaj prijave kao organizator
                         Organizator organizator;
                         organizator = orgController.searchByUserName(username);
                         if (organizator != null) {
                        	 if(organizator.getPassword().equals(password)) { 
                        		 if (organizator.isProfileApproved()) {
                        			 System.out.println("Prijava uspješna!");
                                     OrganizatorView view = new OrganizatorView();
                                     view.setVisible(true);
                                     dispose();
                        			    
                        			}
                        		}
                         }

                         // Pokušaj prijave kao obični korisnik
                         Korisnik korisnik = userController.searchByUserName(username);
                         if (korisnik != null) {
                         	System.out.println("Prijava uspješna!");
                             //TODO: switch to KorisnikView
                             return; // Prekida izvršavanje metode ako je prijava uspješna
                         }

                         // Ako prijava nije uspješna
                         JOptionPane.showMessageDialog(Login.this, "Invalid username or password.");
                     } catch (Exception err) {
                         JOptionPane.showMessageDialog(Login.this, "An error occurred during login.");
                     }
                 }
             });

        contentPane.add(loginButton);

        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPane.add(label);
    
    
    RegisterButton= new JButton("Register");
    RegisterButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
    RegisterButton.setBounds(800, 392, 162, 50);
    RegisterButton.setForeground(new Color(51, 51, 51)); 
    
    RegisterButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Register registerView = new Register();
            registerView.setVisible(true);
            dispose();
        }
    });
    
    contentPane.add(RegisterButton);
    
    
    guestButton= new JButton("Login as guest");
    guestButton.setFont(new Font("Chilanka", Font.PLAIN, 26));
    guestButton.setBounds(600, 450, 300, 50);
   
    guestButton.setForeground(new Color(51, 51, 51)); 
    
    guestButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	 AdminView  adminView1 = new AdminView();
        	    adminView1.setVisible(true);
        	    dispose();
           
        }
    });
    
   
  
    contentPane.add(guestButton);
    
   
}}