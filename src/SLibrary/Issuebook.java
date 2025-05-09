package SLibrary;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.ResultSetMetaData;

public class Issuebook extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    // Member name display
    private JLabel memberNameDisplay;
    
    public Issuebook() throws SQLException {
        initComponents();
        Connect();
        Book();
        issuebook_load();
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
    
    public class BookItem {
        int id;
        String name;
        
        public BookItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
    
    public void Book() throws SQLException {
        pst = con.prepareStatement("SELECT * FROM book");
        rs = pst.executeQuery();
        txtbook.removeAllItems();
        
        // Add a blank initial item
        txtbook.addItem(new BookItem(0, "-- Select Book --"));
        
        while (rs.next()) {
            txtbook.addItem(new BookItem(rs.getInt("id"), rs.getString("name")));
        }
    }
    
    public void issuebook_load() {
        try {
            pst = con.prepareStatement("SELECT i.id, i.memberid, m.name, b.name, i.issuedate, i.returndate FROM lendbook i JOIN member m ON i.memberid = m.id JOIN book b ON i.bookid = b.id");
            rs = pst.executeQuery();
            ResultSetMetaData rsd = (ResultSetMetaData) rs.getMetaData();
            
            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0); // clear existing rows
            
            while (rs.next()) {
                Vector<Object> v2 = new Vector<>();
                v2.add(rs.getInt("i.id")); // Internal lendbook ID
                v2.add(rs.getString("i.memberid"));
                v2.add(rs.getString("m.name"));
                v2.add(rs.getString("b.name"));
                v2.add(rs.getString("i.issuedate"));
                v2.add(rs.getString("i.returndate"));
                d.addRow(v2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Issuebook.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error loading issue records: " + ex.getMessage());
        }
        
        // Hide the Lendbook ID column from the user
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);
    }
    
    private void initComponents() {
        // Initialize components
        txtid = new JTextField();
        txtbook = new JComboBox();
        txtidate = new com.toedter.calendar.JDateChooser();
        txtrdate = new com.toedter.calendar.JDateChooser();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton4 = new JButton();
        jTable1 = new JTable(new DefaultTableModel(
            new Object [][] {},
            new String [] { "Lendbook ID", "Member ID", "Member Name", "Book", "Issue Date", "Return Date" }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane1 = new JScrollPane(jTable1);

        // Main background panel
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(236, 240, 241));

        // Main form panel (rounded corners, green background)
        Color mainGreen = new Color(0, 102, 102);
        JPanel formPanel = new RoundedPanel(30, mainGreen);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(1300, 540));
        formPanel.setBackground(mainGreen);

        // Title above 'ISSUE BOOK'
        JLabel bigTitle = new JLabel("BOOKNEST");
        bigTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        bigTitle.setForeground(Color.WHITE);
        bigTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // 'ISSUE BOOK' label, centered
        JLabel sectionLabel = new JLabel("ISSUE BOOK");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionLabel.setForeground(Color.WHITE);
        sectionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Labels
        JLabel idLabel = new JLabel("MEMBER ID");
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        idLabel.setForeground(Color.WHITE);
        
        JLabel nameLabel = new JLabel("MEMBER NAME");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        
        // Member name display (read-only)
        memberNameDisplay = new JLabel("");
        memberNameDisplay.setFont(new Font("SansSerif", Font.BOLD, 16));
        memberNameDisplay.setForeground(Color.WHITE);
        memberNameDisplay.setPreferredSize(new Dimension(280, 36));
        
        JLabel bookLabel = new JLabel("BOOK");
        bookLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        bookLabel.setForeground(Color.WHITE);
        
        JLabel issueDateLabel = new JLabel("ISSUE DATE");
        issueDateLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        issueDateLabel.setForeground(Color.WHITE);
        
        JLabel returnDateLabel = new JLabel("RETURN DATE");
        returnDateLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        returnDateLabel.setForeground(Color.WHITE);

        // Fields (modern size)
        Dimension fieldSize = new Dimension(280, 36);
        txtid.setPreferredSize(fieldSize);
        txtid.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtid.setBackground(Color.WHITE);
        txtid.setForeground(mainGreen);
        txtid.setCaretColor(mainGreen);
        txtid.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtbook.setPreferredSize(fieldSize);
        txtbook.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtbook.setBackground(Color.WHITE);
        txtbook.setForeground(mainGreen);
        txtbook.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtidate.setPreferredSize(fieldSize);
        txtidate.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtidate.setBackground(Color.WHITE);
        txtidate.setForeground(mainGreen);
        txtidate.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtrdate.setPreferredSize(fieldSize);
        txtrdate.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtrdate.setBackground(Color.WHITE);
        txtrdate.setForeground(mainGreen);
        txtrdate.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Buttons (styled)
        JButton issueBtn = jButton1;
        issueBtn.setText("ISSUE");
        issueBtn.setBackground(new Color(0, 153, 102));
        issueBtn.setForeground(Color.WHITE);
        issueBtn.setFocusPainted(false);
        issueBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        issueBtn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        JButton updateBtn = jButton2;
        updateBtn.setText("UPDATE");
        updateBtn.setBackground(Color.WHITE);
        updateBtn.setForeground(new Color(0, 153, 102));
        updateBtn.setFocusPainted(false);
        updateBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        updateBtn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        JButton cancelBtn = jButton4;
        cancelBtn.setText("CANCEL");
        cancelBtn.setBackground(new Color(220, 53, 69));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        JPanel btnRow = new JPanel(new GridLayout(1, 3, 18, 0));
        btnRow.setOpaque(false);
        btnRow.add(issueBtn);
        btnRow.add(updateBtn);
        btnRow.add(cancelBtn);

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
        
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(0).setMinWidth(80);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(200);
        
        jScrollPane1.setPreferredSize(new Dimension(720, 420));
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
        leftPanel.add(idLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtid, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(nameLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(memberNameDisplay, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(bookLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtbook, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(issueDateLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtidate, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(returnDateLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtrdate, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.gridwidth = 2;
        lgbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(Box.createVerticalStrut(18), lgbc); // vertical space before buttons
        
        lgbc.gridy++;
        leftPanel.add(btnRow, lgbc);

        // Main formPanel layout: left (form), right (table)
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(20, 20, 20, 20);
        fgbc.gridx = 0; fgbc.gridy = 0;
        fgbc.anchor = GridBagConstraints.NORTHWEST;
        fgbc.weightx = 0.5; fgbc.weighty = 1.0;
        fgbc.fill = GridBagConstraints.BOTH;
        formPanel.add(leftPanel, fgbc);
        
        fgbc.gridx = 1;
        fgbc.weightx = 0.5;
        formPanel.add(jScrollPane1, fgbc);

        // Add formPanel to backgroundPanel
        backgroundPanel.add(formPanel);
        setContentPane(backgroundPanel);
        setTitle("Issue Book");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1400, 700));
        pack();
        setLocationRelativeTo(null);
        
        // Add event listeners
        txtid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtidKeyPressed(evt);
            }
        });
        
        jButton1.addActionListener(e -> issueBookAction());
        jButton2.addActionListener(e -> updateBookAction());
        jButton4.addActionListener(e -> this.setVisible(false));
        
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRowClicked();
            }
        });
        
        // Disable update button initially
        jButton2.setEnabled(false);
    }
    
    private void txtidKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String mid = txtid.getText().trim();
            if (mid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Member ID");
                return;
            }
            
            try {
                pst = con.prepareStatement("SELECT * FROM member WHERE id = ?");
                pst.setString(1, mid);
                rs = pst.executeQuery();
                
                if (rs.next()) {
                    String memberName = rs.getString("name");
                    memberNameDisplay.setText(memberName);
                } else {
                    JOptionPane.showMessageDialog(this, "Member ID not found");
                    memberNameDisplay.setText("");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Issuebook.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error fetching member: " + ex.getMessage());
            }
        }
    }
    
    private void issueBookAction() {
        // Validate inputs
        String mid = txtid.getText().trim();
        if (mid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID");
            txtid.requestFocus();
            return;
        }
        
        if (memberNameDisplay.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member not found. Please enter a valid Member ID");
            txtid.requestFocus();
            return;
        }
        
        if (txtbook.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a Book");
            txtbook.requestFocus();
            return;
        }
        
        if (txtidate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select Issue Date");
            txtidate.requestFocus();
            return;
        }
        
        if (txtrdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select Return Date");
            txtrdate.requestFocus();
            return;
        }
        
        // Get values for database insert
        BookItem bitem = (BookItem) txtbook.getSelectedItem();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String issueDate = dateFormat.format(txtidate.getDate());
        String returnDate = dateFormat.format(txtrdate.getDate());
        
        try {
            // Check if the user is already issued the same book
            pst = con.prepareStatement("SELECT COUNT(*) FROM lendbook WHERE memberid = ? AND bookid = ?");
            pst.setString(1, mid);
            pst.setInt(2, bitem.id);
            rs = pst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "This book is already issued to the user. Please return it first.");
                return;
            }
            
            pst = con.prepareStatement("INSERT INTO lendbook (memberid, bookid, issuedate, returndate) VALUES (?, ?, ?, ?)");
            pst.setString(1, mid);
            pst.setInt(2, bitem.id);
            pst.setString(3, issueDate);
            pst.setString(4, returnDate);
            
            int k = pst.executeUpdate();
            
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Book Issued Successfully");
                clearFields();
                issuebook_load();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to issue book");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Issuebook.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
    
    private void updateBookAction() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to update");
            return;
        }
        
        // Validate inputs
        if (txtbook.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a Book");
            txtbook.requestFocus();
            return;
        }
        
        if (txtidate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select Issue Date");
            txtidate.requestFocus();
            return;
        }
        
        if (txtrdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select Return Date");
            txtrdate.requestFocus();
            return;
        }
        
        // Get values for database update
        int id = Integer.parseInt(jTable1.getValueAt(row, 0).toString()); // Now this is the correct lendbook.id
        String mid = txtid.getText().trim();
        BookItem bitem = (BookItem) txtbook.getSelectedItem();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String issueDate = dateFormat.format(txtidate.getDate());
        String returnDate = dateFormat.format(txtrdate.getDate());
        
        try {
            pst = con.prepareStatement("UPDATE lendbook SET bookid = ?, issuedate = ?, returndate = ? WHERE id = ?");
            pst.setInt(1, bitem.id);
            pst.setString(2, issueDate);
            pst.setString(3, returnDate);
            pst.setInt(4, id);
            
            int k = pst.executeUpdate();
            
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Record Updated Successfully");
                clearFields();
                issuebook_load();
                jButton1.setEnabled(true);
                jButton2.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update record");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Issuebook.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
    
    private void tableRowClicked() {
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            try {
                // Get selected record ID
                int id = Integer.parseInt(jTable1.getValueAt(row, 0).toString());
                
                // Get complete record from database
                pst = con.prepareStatement("SELECT l.*, m.name AS membername, b.name AS bookname FROM lendbook l " +
                                          "JOIN member m ON l.memberid = m.id " +
                                          "JOIN book b ON l.bookid = b.id " +
                                          "WHERE l.id = ?");
                pst.setInt(1, id);
                rs = pst.executeQuery();
                
                if (rs.next()) {
                    // Set member info (readonly)
                    String memberId = rs.getString("memberid");
                    String memberName = rs.getString("membername");
                    txtid.setText(memberId);
                    memberNameDisplay.setText(memberName);
                    txtid.setEnabled(false);
                    
                    // Set book selection
                    String bookName = rs.getString("bookname");
                    for (int i = 0; i < txtbook.getItemCount(); i++) {
                        BookItem item = (BookItem) txtbook.getItemAt(i);
                        if (item.name.equals(bookName)) {
                            txtbook.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    // Set dates
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    txtidate.setDate(sdf.parse(rs.getString("issuedate")));
                    txtrdate.setDate(sdf.parse(rs.getString("returndate")));
                    
                    // Enable update, disable issue
                    jButton1.setEnabled(false);
                    jButton2.setEnabled(true);
                }
            } catch (Exception ex) {
                Logger.getLogger(Issuebook.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error loading record: " + ex.getMessage());
            }
        }
    }
    
    private void clearFields() {
        txtid.setText("");
        txtid.setEnabled(true);
        memberNameDisplay.setText("");
        txtbook.setSelectedIndex(0);
        txtidate.setDate(null);
        txtrdate.setDate(null);
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
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
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Issuebook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Issuebook().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Issuebook.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox txtbook;
    private javax.swing.JTextField txtid;
    private com.toedter.calendar.JDateChooser txtidate;
    private com.toedter.calendar.JDateChooser txtrdate;
}