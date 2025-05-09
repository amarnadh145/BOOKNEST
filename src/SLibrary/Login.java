package SLibrary;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;
import javax.swing.*;

public class Login extends javax.swing.JFrame {

    private static String currentUsername = "";
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public Login() {
        initComponents();
        Connect();
    }

    private void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5777721", "sql5777721", "VSiGHrR7hT");
            System.out.println("Connected to database.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    private void initComponents() {
        // Main background panel
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(236, 240, 241));

        // Main form panel (rounded corners, green background)
        Color mainGreen = new Color(0, 102, 102);
        JPanel formPanel = new RoundedPanel(30, mainGreen);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(420, 500));
        formPanel.setBackground(mainGreen);

        // Big Title at the top
        JLabel bigTitle = new JLabel("BOOKNEST");
        bigTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        bigTitle.setForeground(Color.WHITE);
        bigTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Title
        JLabel title = new JLabel("LOGIN");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Username
        JLabel userLabel = new JLabel("EMAIL");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(280, 36));
        userField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userField.setBackground(Color.WHITE);
        userField.setForeground(mainGreen);
        userField.setCaretColor(mainGreen);
        userField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Password
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passLabel.setForeground(Color.WHITE);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(280, 36));
        passField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passField.setBackground(Color.WHITE);
        passField.setForeground(mainGreen);
        passField.setCaretColor(mainGreen);
        passField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Show password checkbox
        JCheckBox showPass = new JCheckBox("Show Password");
        showPass.setBackground(mainGreen);
        showPass.setFont(new Font("SansSerif", Font.PLAIN, 13));
        showPass.setForeground(Color.WHITE);
        showPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        showPass.addActionListener(e -> passField.setEchoChar(showPass.isSelected() ? (char)0 : '\u2022'));

        // Sign in button (green, white text)
        JButton signInBtn = new JButton("SIGN IN");
        signInBtn.setBackground(new Color(0, 153, 102));
        signInBtn.setForeground(Color.WHITE);
        signInBtn.setFocusPainted(false);
        signInBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        signInBtn.setPreferredSize(new Dimension(180, 38));
        signInBtn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        signInBtn.addActionListener(e -> loginAction(new ActionEvent(signInBtn, ActionEvent.ACTION_PERFORMED, null)));

        // Forgot password link
        JLabel forgotLabel = new JLabel("FORGOT PASSWORD?");
        forgotLabel.setForeground(Color.WHITE);
        forgotLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        forgotLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                forgotPasswordAction(new ActionEvent(forgotLabel, ActionEvent.ACTION_PERFORMED, null));
            }
        });

        // Register link (bold, underlined, more prominent)
        JLabel registerLabel = new JLabel("REGISTER");
        registerLabel.setForeground(Color.WHITE);
        registerLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        registerLabel.setText("<html><u>REGISTER</u></html>");
        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                AdminRegistration ar = new AdminRegistration();
                setVisible(false);
                ar.setVisible(true);
            }
        });

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(bigTitle, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(18, 20, 0, 20);
        formPanel.add(title, gbc);

        // Centered block for label+field
        gbc.gridy++;
        gbc.insets = new Insets(18, 0, 0, 0);
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel userBlock = new JPanel();
        userBlock.setLayout(new BoxLayout(userBlock, BoxLayout.Y_AXIS));
        userBlock.setOpaque(false);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userField.setAlignmentX(Component.LEFT_ALIGNMENT);
        userBlock.add(userLabel);
        userBlock.add(Box.createVerticalStrut(4));
        userBlock.add(userField);
        formPanel.add(userBlock, gbc);

        gbc.gridy++;
        JPanel passBlock = new JPanel();
        passBlock.setLayout(new BoxLayout(passBlock, BoxLayout.Y_AXIS));
        passBlock.setOpaque(false);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passBlock.add(passLabel);
        passBlock.add(Box.createVerticalStrut(4));
        passBlock.add(passField);
        formPanel.add(passBlock, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(8, 60, 0, 0); // left align with textfield
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(showPass, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(18, 20, 0, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(signInBtn, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(18, 20, 0, 20);
        formPanel.add(forgotLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(8, 20, 18, 20);
        formPanel.add(registerLabel, gbc);

        // Add formPanel to backgroundPanel
        backgroundPanel.add(formPanel);
        setContentPane(backgroundPanel);
        setTitle("Login");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(520, 600));
        pack();
        setLocationRelativeTo(null);

        // Assign fields for use in loginAction
        this.username = userField;
        this.password = passField;
        this.jButton1 = signInBtn;
        this.jButton3 = new JButton(); // dummy for registration
    }

    // Helper class for rounded panel
    static class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Color bgColor;
        public RoundedPanel(int radius, Color bgColor) {
            super();
            this.cornerRadius = radius;
            this.bgColor = bgColor;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
    }

    private void loginAction(ActionEvent evt) {
        String user = username.getText().trim();
        String pass = new String(password.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        try {
            pst = con.prepareStatement("SELECT * FROM admins WHERE email = ? AND BINARY password = ?");
            pst.setString(1, user);
            pst.setString(2, pass);
            rs = pst.executeQuery();

            if (rs.next()) {
                currentUsername = user;
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.dispose(); // Dispose of the login window first
                new Main().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
                username.setText("");
                password.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void forgotPasswordAction(ActionEvent evt) {
        // Get user's email
        String email = JOptionPane.showInputDialog(this, "Enter your email address:");
        if (email == null || email.trim().isEmpty()) return;
        
        // Get user's username
        String user = JOptionPane.showInputDialog(this, "Enter your username:");
        if (user == null || user.trim().isEmpty()) return;
        
        try {
            // Check if email and username combination exists
            pst = con.prepareStatement("SELECT * FROM admins WHERE email = ? AND username = ?");
            pst.setString(1, email);
            pst.setString(2, user);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                // Generate a random password
                String randomPassword = generateRandomPassword();
                
                // Update the password in the database
                pst = con.prepareStatement("UPDATE admins SET password = ? WHERE email = ? AND username = ?");
                pst.setString(1, randomPassword);
                pst.setString(2, email);
                pst.setString(3, user);
                int updated = pst.executeUpdate();
                
                if (updated > 0) {
                    // Show the random password to the user
                    JOptionPane.showMessageDialog(this, 
                        "Your password has been reset.\n" +
                        "Your temporary password is: " + randomPassword + 
                        "\nPlease login with this password and change it immediately.",
                        "Password Reset", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to reset password.");
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No account found with that email and username combination.", 
                    "Account Not Found", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    // Method to generate a random password
    private String generateRandomPassword() {
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?";
        String allChars = upperCase + lowerCase + numbers + specialChars;
        
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        
        // Add at least one character from each character set
        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        
        // Add more random characters to reach desired length (e.g., 10 characters total)
        for (int i = 4; i < 10; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        
        // Shuffle the password to make the pattern less predictable
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int j = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }
        
        return new String(passwordArray);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
    
    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }

    // Components
    private JPanel jPanel1;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4;
    private JTextField username;
    private JPasswordField password;
    private JButton jButton1, jButton2, jButton3, jButton4;
}