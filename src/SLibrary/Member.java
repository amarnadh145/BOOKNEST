package SLibrary;

import java.awt.*;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Member extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    // Variable to track selected member ID
    private String selectedId = "";

    public Member() {
        initComponents();
        Connect();
        Member_Load();
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5777721", "sql5777721", "VSiGHrR7hT");
            System.out.println("Connected to database.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void Member_Load() {
        try {
            pst = con.prepareStatement("SELECT * FROM member");
            rs = pst.executeQuery();
            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0);

            while (rs.next()) {
                Vector<String> v2 = new Vector<>();
                v2.add(rs.getString("id"));
                v2.add(rs.getString("name"));
                v2.add(rs.getString("email"));
                v2.add(rs.getString("phone"));
                v2.add(rs.getString("address"));
                d.addRow(v2);
            }

        } catch (Exception e) {
            e.printStackTrace();
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

    private void initComponents() {
        // Initialize components
        txtName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtAddress = new JTextArea(5, 20); // Using JTextArea for address
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        JScrollPane addressScrollPane = new JScrollPane(txtAddress);
        JButton btnAdd = new JButton();
        JButton btnUpdate = new JButton();
        JButton btnDelete = new JButton();
        JButton btnCancel = new JButton();
        
        // Initialize table with all member details
        jTable1 = new JTable(new DefaultTableModel(
            new Object [][] {},
            new String [] { "ID", "Name", "Email", "Phone", "Address" }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        JScrollPane jScrollPane1 = new JScrollPane(jTable1);

        // Main background panel
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(236, 240, 241));

        // Main form panel (rounded corners, green background)
        Color mainGreen = new Color(0, 102, 102);
        JPanel formPanel = new RoundedPanel(30, mainGreen);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(1300, 540));
        formPanel.setBackground(mainGreen);

        // Title above 'MEMBER'
        JLabel bigTitle = new JLabel("BOOKNEST");
        bigTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        bigTitle.setForeground(Color.WHITE);
        bigTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // 'MEMBER' label, centered
        JLabel sectionLabel = new JLabel("MEMBER");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionLabel.setForeground(Color.WHITE);
        sectionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Labels
        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        JLabel emailLabel = new JLabel("EMAIL");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        emailLabel.setForeground(Color.WHITE);
        JLabel phoneLabel = new JLabel("PHONE");
        phoneLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        phoneLabel.setForeground(Color.WHITE);
        JLabel addressLabel = new JLabel("ADDRESS");
        addressLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        addressLabel.setForeground(Color.WHITE);

        // Fields styling
        Dimension fieldSize = new Dimension(280, 36);
        txtName.setPreferredSize(fieldSize);
        txtName.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtName.setBackground(Color.WHITE);
        txtName.setForeground(mainGreen);
        txtName.setCaretColor(mainGreen);
        txtName.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtEmail.setPreferredSize(fieldSize);
        txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setForeground(mainGreen);
        txtEmail.setCaretColor(mainGreen);
        txtEmail.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtPhone.setPreferredSize(fieldSize);
        txtPhone.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtPhone.setBackground(Color.WHITE);
        txtPhone.setForeground(mainGreen);
        txtPhone.setCaretColor(mainGreen);
        txtPhone.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        // Text area for address
        txtAddress.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtAddress.setBackground(Color.WHITE);
        txtAddress.setForeground(mainGreen);
        txtAddress.setCaretColor(mainGreen);
        txtAddress.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        addressScrollPane.setPreferredSize(new Dimension(280, 80)); // Taller to show multiple lines
        addressScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Buttons (styled, single row)
        btnAdd.setText("ADD");
        btnAdd.setBackground(new Color(0, 153, 102));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnAdd.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        btnUpdate.setText("UPDATE");
        btnUpdate.setBackground(Color.WHITE);
        btnUpdate.setForeground(new Color(0, 153, 102));
        btnUpdate.setFocusPainted(false);
        btnUpdate.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnUpdate.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        btnDelete.setText("DELETE");
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnDelete.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        btnCancel.setText("CANCEL");
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setForeground(new Color(0, 153, 102));
        btnCancel.setFocusPainted(false);
        btnCancel.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCancel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        JPanel btnRow = new JPanel(new GridLayout(1, 4, 18, 0));
        btnRow.setOpaque(false);
        btnRow.add(btnAdd);
        btnRow.add(btnUpdate);
        btnRow.add(btnDelete);
        btnRow.add(btnCancel);

        // Table styling
        jTable1.setFont(new Font("SansSerif", Font.PLAIN, 15));
        jTable1.setRowHeight(28);
        jTable1.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        jTable1.getTableHeader().setBackground(new Color(0, 102, 102));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        jScrollPane1.setPreferredSize(new Dimension(400, 420));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Layout for form fields/buttons (left half)
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
        leftPanel.add(nameLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtName, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(emailLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtEmail, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(phoneLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtPhone, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(addressLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(addressScrollPane, lgbc); // Using JScrollPane for address
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.gridwidth = 2;
        lgbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(Box.createVerticalStrut(18), lgbc); // vertical space before buttons
        
        lgbc.gridy++;
        leftPanel.add(btnRow, lgbc);

        // Main formPanel layout: left (form), right (table)
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(10, 10, 10, 10);
        fgbc.gridx = 0; fgbc.gridy = 0;
        fgbc.anchor = GridBagConstraints.NORTHWEST;
        fgbc.weightx = 0.5; fgbc.weighty = 1.0;
        fgbc.fill = GridBagConstraints.BOTH;
        formPanel.add(leftPanel, fgbc);
        
        fgbc.gridx = 1;
        fgbc.weightx = 0.5;
        formPanel.add(jScrollPane1, fgbc);

        // Add formPanel to backgroundPanel with constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(formPanel, gbc);

        setContentPane(backgroundPanel);
        setTitle("Member");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 700));
        pack();
        setLocationRelativeTo(null);

        // Store button references as class fields
        this.btnAdd = btnAdd;
        this.btnUpdate = btnUpdate;
        this.btnDelete = btnDelete;
        
        // Initially disable update and delete buttons
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    
        // Button action listeners
        btnAdd.addActionListener(e -> addMember());
        btnUpdate.addActionListener(e -> updateMember());
        btnDelete.addActionListener(e -> deleteMember());
        btnCancel.addActionListener(e -> {
            this.setVisible(false);
            new Main().setVisible(true);
        });

        // Table row click: populate fields and enable update/delete
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.getSelectedRow();
                if (row != -1) {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    selectedId = model.getValueAt(row, 0).toString();
                    txtName.setText(model.getValueAt(row, 1).toString());
                    txtEmail.setText(model.getValueAt(row, 2).toString());
                    txtPhone.setText(model.getValueAt(row, 3).toString());
                    txtAddress.setText(model.getValueAt(row, 4).toString());
                    btnUpdate.setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }
        });
    }

    private void addMember() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            // Generate member ID: first two letters of name + random 4 digits
            String namePrefix = name.substring(0, Math.min(2, name.length())).toUpperCase();
            int randomNum = (int)(1000 + Math.random() * 9000);
            String memberId = namePrefix + randomNum;
            
            // Check if the generated ID already exists
            while (true) {
                pst = con.prepareStatement("SELECT id FROM member WHERE id = ?");
                pst.setString(1, memberId);
                rs = pst.executeQuery();
                
                if (!rs.next()) {
                    break; // ID is unique, we can use it
                }
                
                // If ID exists, generate a new one
                randomNum = (int)(1000 + Math.random() * 9000);
                memberId = namePrefix + randomNum;
            }
            
            pst = con.prepareStatement("INSERT INTO member (id, name, email, phone, address) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, memberId);
            pst.setString(2, name);
            pst.setString(3, email);
            pst.setString(4, phone);
            pst.setString(5, address);
            
            int k = pst.executeUpdate();
            
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Member Added Successfully");
                clearFields();
                Member_Load();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add member");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void updateMember() {
        if (selectedId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a member to update.");
            return;
        }
        try {
            pst = con.prepareStatement("UPDATE member SET name=?, email=?, phone=?, address=? WHERE id=?");
            pst.setString(1, txtName.getText().trim());
            pst.setString(2, txtEmail.getText().trim());
            pst.setString(3, txtPhone.getText().trim());
            pst.setString(4, txtAddress.getText().trim());
            pst.setString(5, selectedId);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Member updated successfully");
            Member_Load();
            clearFields();
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
            selectedId = "";
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating member: " + e.getMessage());
        }
    }

    private void deleteMember() {
        if (selectedId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.");
            return;
        }
        try {
            // Check for issued books
            pst = con.prepareStatement("SELECT COUNT(*) FROM lendbook WHERE memberid = ?");
            pst.setString(1, selectedId);
            rs = pst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Cannot delete this member because they have issued books.");
                return;
            }
            // Delete from returnbook first
            pst = con.prepareStatement("DELETE FROM returnbook WHERE member_id = ?");
            pst.setString(1, selectedId);
            pst.executeUpdate();
            // Now delete the member
            pst = con.prepareStatement("DELETE FROM member WHERE id=?");
            pst.setString(1, selectedId);
            int k = pst.executeUpdate();
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Member deleted successfully");
                Member_Load();
                clearFields();
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                selectedId = "";
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete member.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting member: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtName.requestFocus();
        
        // Re-enable Add button when the form is cleared
        btnAdd.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Member().setVisible(true));
    }

    // Variables declaration
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextArea txtAddress;
    private JTable jTable1;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
}