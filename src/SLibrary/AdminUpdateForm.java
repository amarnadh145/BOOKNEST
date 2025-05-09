package SLibrary;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class AdminUpdateForm extends JFrame {
    private JLabel lblUsernameValue; // Changed from JTextField to JLabel
    private JPasswordField txtPassword;
    private JTextField txtFullname;
    private JTextField txtEmail;
    private JButton btnUpdate;
    private JButton btnCancel;

    private Connection con;
    private PreparedStatement pst;
    private String currentUsername = "";
    private String currentEmail = "";

    public AdminUpdateForm() {
        initComponents();
        Connect();
        loadCurrentUserData();
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

    private void loadCurrentUserData() {
        String loginEmail = Login.getCurrentUsername(); // This is actually storing the email
        
        if (loginEmail == null || loginEmail.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No logged-in user found. Please login again.");
            this.dispose();
            return;
        }
        
        try {
            // Look up user by email instead of username
            pst = con.prepareStatement("SELECT * FROM admins WHERE email = ?");
            pst.setString(1, loginEmail);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                currentUsername = rs.getString("username");
                currentEmail = rs.getString("email");
                
                lblUsernameValue.setText(rs.getString("username")); // Set the username as label text
                txtPassword.setText(rs.getString("password"));
                txtFullname.setText(rs.getString("fullname"));
                txtEmail.setText(rs.getString("email"));
            } else {
                JOptionPane.showMessageDialog(this, "User data not found!");
                this.dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading user data: " + ex.getMessage());
            this.dispose();
        }
    }

    private void initComponents() {
        // Initialize fields and buttons first
        lblUsernameValue = new JLabel(); // Changed to JLabel
        txtPassword = new JPasswordField();
        txtFullname = new JTextField();
        txtEmail = new JTextField();
        btnUpdate = new JButton("UPDATE");
        btnCancel = new JButton("CANCEL");

        // Main background panel
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(236, 240, 241));

        // Main form panel (rounded corners, green background)
        Color mainGreen = new Color(0, 102, 102);
        JPanel formPanel = new RoundedPanel(30, mainGreen);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(600, 540));
        formPanel.setBackground(mainGreen);

        // Big Title at the top
        JLabel bigTitle = new JLabel("BOOKNEST");
        bigTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        bigTitle.setForeground(Color.WHITE);
        bigTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Section Title
        JLabel sectionLabel = new JLabel("UPDATE ADMIN PROFILE");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionLabel.setForeground(Color.WHITE);
        sectionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Labels and Fields
        JLabel userLabel = new JLabel("USERNAME:");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        
        // Username value as bold white text
        lblUsernameValue.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblUsernameValue.setForeground(Color.WHITE);
        lblUsernameValue.setPreferredSize(new Dimension(280, 36));
        
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        passLabel.setForeground(Color.WHITE);
        txtPassword.setPreferredSize(new Dimension(280, 36));
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setForeground(mainGreen);
        txtPassword.setCaretColor(mainGreen);
        txtPassword.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        txtPassword.setEchoChar((char)0); // Make password visible by default

        JLabel nameLabel = new JLabel("FULL NAME");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        txtFullname.setPreferredSize(new Dimension(280, 36));
        txtFullname.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtFullname.setBackground(Color.WHITE);
        txtFullname.setForeground(mainGreen);
        txtFullname.setCaretColor(mainGreen);
        txtFullname.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        JLabel emailLabel = new JLabel("EMAIL");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        emailLabel.setForeground(Color.WHITE);
        txtEmail.setPreferredSize(new Dimension(280, 36));
        txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setForeground(mainGreen);
        txtEmail.setCaretColor(mainGreen);
        txtEmail.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Buttons (styled, single row)
        btnUpdate.setBackground(new Color(0, 153, 102));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnUpdate.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setForeground(new Color(0, 153, 102));
        btnCancel.setFocusPainted(false);
        btnCancel.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        JPanel btnRow = new JPanel(new GridLayout(1, 2, 18, 0));
        btnRow.setOpaque(false);
        btnRow.add(btnUpdate);
        btnRow.add(btnCancel);

        // Layout for form fields/buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints lgbc = new GridBagConstraints();
        lgbc.insets = new Insets(10, 10, 10, 10);
        lgbc.gridx = 0; lgbc.gridy = 0; lgbc.gridwidth = 2;
        lgbc.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(bigTitle, lgbc);
        lgbc.gridy++;
        leftPanel.add(sectionLabel, lgbc);
        lgbc.gridy++;
        lgbc.gridwidth = 1;
        lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(userLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.WEST; // Changed to WEST from EAST for better alignment
        leftPanel.add(lblUsernameValue, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(passLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtPassword, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(nameLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtFullname, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(emailLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtEmail, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.gridwidth = 2;
        lgbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(Box.createVerticalStrut(18), lgbc); // vertical space before buttons
        lgbc.gridy++;
        leftPanel.add(btnRow, lgbc);

        // Add leftPanel to formPanel
        formPanel.add(leftPanel);
        backgroundPanel.add(formPanel);
        setContentPane(backgroundPanel);
        setTitle("Update Admin Profile");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);

        // Button actions
        btnUpdate.addActionListener(e -> updateAdmin());
        btnCancel.addActionListener(e -> this.dispose());
    }

    private void updateAdmin() {
        String password = new String(txtPassword.getPassword());
        String fullname = txtFullname.getText().trim();
        String email = txtEmail.getText().trim();

        // Validation (same as registration)
        if (password.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return;
        }
        if (!fullname.matches("^[a-zA-Z\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Full name must contain only letters and spaces");
            txtFullname.requestFocus();
            return;
        }
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[@$!%*?&].*");
        boolean hasMinLength = password.length() >= 8;
        if (!(hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar && hasMinLength)) {
            StringBuilder errorMessage = new StringBuilder("Password must contain:\n");
            if (!hasUpperCase) errorMessage.append("- At least one uppercase letter (A-Z)\n");
            if (!hasLowerCase) errorMessage.append("- At least one lowercase letter (a-z)\n");
            if (!hasDigit) errorMessage.append("- At least one number (0-9)\n");
            if (!hasSpecialChar) errorMessage.append("- At least one special character (@$!%*?&)\n");
            if (!hasMinLength) errorMessage.append("- At least 8 characters long\n");
            JOptionPane.showMessageDialog(this, errorMessage.toString());
            txtPassword.requestFocus();
            return;
        }
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address\nExample: username@domain.com");
            txtEmail.requestFocus();
            return;
        }
        
        // Check if email changed and already exists
        if (!email.equals(currentEmail)) {
            try {
                PreparedStatement checkEmail = con.prepareStatement("SELECT * FROM admins WHERE email = ? AND username != ?");
                checkEmail.setString(1, email);
                checkEmail.setString(2, currentUsername);
                ResultSet rs = checkEmail.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Email already exists. Please use another email address.");
                    txtEmail.requestFocus();
                    return;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
                return;
            }
        }
        
        try {
            pst = con.prepareStatement("UPDATE admins SET password = ?, fullname = ?, email = ? WHERE username = ?");
            pst.setString(1, password);
            pst.setString(2, fullname);
            pst.setString(3, email);
            pst.setString(4, currentUsername);
            int k = pst.executeUpdate();
            if (k == 1) {
                // Update also the currentUsername in Login class if email changed
                if (!email.equals(currentEmail)) {
                    Login.setCurrentUsername(email);
                }
                JOptionPane.showMessageDialog(this, "Profile updated successfully");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Profile update failed");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminUpdateForm().setVisible(true));
    }
}