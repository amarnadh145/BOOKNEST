package SLibrary;

import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AdminRegistration extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    
    public AdminRegistration() {
        initComponents();
        Connect();
    }
    
    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5777721", "sql5777721", "VSiGHrR7hT");
            System.out.println("Connected to database.");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    // Registration logic
    private void registerAdmin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String fullname = txtFullname.getText().trim();
        String email = txtEmail.getText().trim();

        // Validation
        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return;
        }
        if (!fullname.matches("^[a-zA-Z\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Full name must contain only letters and spaces");
            txtFullname.requestFocus();
            return;
        }
        if (!username.matches("[a-zA-Z0-9]+")) {
            JOptionPane.showMessageDialog(this, "Username must contain only letters and numbers");
            txtUsername.requestFocus();
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
        try {
            // Check if username already exists
            PreparedStatement checkUser = con.prepareStatement("SELECT * FROM admins WHERE username = ?");
            checkUser.setString(1, username);
            ResultSet rs = checkUser.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose another username.");
                txtUsername.requestFocus();
                return;
            }
            // Check if email already exists
            PreparedStatement checkEmail = con.prepareStatement("SELECT * FROM admins WHERE email = ?");
            checkEmail.setString(1, email);
            rs = checkEmail.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Email already exists. Please use another email address.");
                txtEmail.requestFocus();
                return;
            }
            // Insert new admin
            PreparedStatement pst = con.prepareStatement("INSERT INTO admins (username, password, fullname, email) VALUES (?, ?, ?, ?)");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, fullname);
            pst.setString(4, email);
            int k = pst.executeUpdate();
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Admin registered successfully");
                txtUsername.setText("");
                txtPassword.setText("");
                txtFullname.setText("");
                txtEmail.setText("");
                txtUsername.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // Initialize all UI components first
        jButton1 = new JButton("REGISTER");
        jButton2 = new JButton("LOGIN");
        jButton3 = new JButton("CANCEL");
        txtEmail = new JTextField();
        txtFullname = new JTextField();
        txtPassword = new JPasswordField();
        txtUsername = new JTextField();

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
        JLabel sectionLabel = new JLabel("ADMIN REGISTRATION");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionLabel.setForeground(Color.WHITE);
        sectionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Labels and Fields
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        txtUsername.setPreferredSize(new Dimension(280, 36));
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtUsername.setBackground(Color.WHITE);
        txtUsername.setForeground(mainGreen);
        txtUsername.setCaretColor(mainGreen);
        txtUsername.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        passLabel.setForeground(Color.WHITE);
        txtPassword.setPreferredSize(new Dimension(280, 36));
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setForeground(mainGreen);
        txtPassword.setCaretColor(mainGreen);
        txtPassword.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

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
        jButton1.setBackground(new Color(0, 153, 102));
        jButton1.setForeground(Color.WHITE);
        jButton1.setFocusPainted(false);
        jButton1.setFont(new Font("SansSerif", Font.BOLD, 16));
        jButton1.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        jButton2.setBackground(Color.WHITE);
        jButton2.setForeground(new Color(0, 153, 102));
        jButton2.setFocusPainted(false);
        jButton2.setFont(new Font("SansSerif", Font.BOLD, 16));
        jButton2.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        jButton3.setBackground(new Color(220, 53, 69));
        jButton3.setForeground(Color.WHITE);
        jButton3.setFocusPainted(false);
        jButton3.setFont(new Font("SansSerif", Font.BOLD, 16));
        jButton3.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        JPanel btnRow = new JPanel(new GridLayout(1, 3, 18, 0));
        btnRow.setOpaque(false);
        btnRow.add(jButton1);
        btnRow.add(jButton2);
        btnRow.add(jButton3);

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
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtUsername, lgbc);
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
        setTitle("Admin Registration");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);

        // Button actions
        jButton1.addActionListener(e -> registerAdmin());
        jButton2.addActionListener(e -> {
            this.setVisible(false);
            new Login().setVisible(true);
        });
        jButton3.addActionListener(e -> this.setVisible(false));
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminRegistration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    // Helper class for rounded panel (if not present)
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
}