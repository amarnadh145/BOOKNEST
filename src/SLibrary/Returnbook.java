package SLibrary;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Returnbook extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    // Book item for combobox
    class BookItem {
        String id;
        String name;
        String issueDate;
        String returnDate;
        int elapsedDays;
        int fine;
        
        public BookItem(String id, String name, String issueDate, String returnDate, int elapsedDays, int fine) {
            this.id = id;
            this.name = name;
            this.issueDate = issueDate;
            this.returnDate = returnDate;
            this.elapsedDays = elapsedDays;
            this.fine = fine;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    public Returnbook() throws SQLException {
        initComponents();
        Connect();
        Returnbook_load();
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
    
    public void Returnbook_load() {
        try {
            pst = con.prepareStatement(
                "SELECT member_id, membername, bookname, returndate, elap, fine FROM returnbook"
            );
            rs = pst.executeQuery();
            
            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0); // clear existing rows
            
            while (rs.next()) {
                Vector<Object> v2 = new Vector<>();
                v2.add(rs.getString("member_id"));
                v2.add(rs.getString("membername"));
                v2.add(rs.getString("bookname"));
                v2.add(rs.getString("returndate"));
                v2.add(rs.getString("elap"));
                v2.add(rs.getString("fine"));
                d.addRow(v2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Returnbook.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error loading return records: " + ex.getMessage());
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
        txtMemberId = new JTextField();
        txtDaysElapsed = new JTextField();
        txtFine = new JTextField();
        returnBtn = new JButton("RETURN");
        JButton cancelBtn = new JButton("CANCEL");
        
        bookComboBox = new JComboBox<>();
        memberNameLabel = new JLabel();
        returnDateLabel = new JLabel();
        
        // Create the main background panel
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(236, 240, 241));
        
        // Main form panel with rounded corners
        Color mainGreen = new Color(0, 102, 102);
        JPanel formPanel = new RoundedPanel(30, mainGreen);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(1300, 540));
        formPanel.setBackground(mainGreen);
        
        // Title above 'RETURN BOOK'
        JLabel bigTitle = new JLabel("BOOKNEST");
        bigTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        bigTitle.setForeground(Color.WHITE);
        bigTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        // 'RETURN BOOK' label, centered
        JLabel sectionLabel = new JLabel("RETURN BOOK");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionLabel.setForeground(Color.WHITE);
        sectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Labels
        JLabel memberIdLabel = new JLabel("MEMBER ID");
        memberIdLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        memberIdLabel.setForeground(Color.WHITE);
        JLabel memberNameTextLabel = new JLabel("MEMBER NAME");
        memberNameTextLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        memberNameTextLabel.setForeground(Color.WHITE);
        JLabel bookLabel = new JLabel("BOOK");
        bookLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        bookLabel.setForeground(Color.WHITE);
        JLabel returnDateTextLabel = new JLabel("RETURN DATE");
        returnDateTextLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        returnDateTextLabel.setForeground(Color.WHITE);
        JLabel daysElapsedLabel = new JLabel("DAYS ELAPSED");
        daysElapsedLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        daysElapsedLabel.setForeground(Color.WHITE);
        JLabel fineLabel = new JLabel("FINE");
        fineLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        fineLabel.setForeground(Color.WHITE);
        
        // Fields styling
        Dimension fieldSize = new Dimension(280, 36);
        txtMemberId.setPreferredSize(fieldSize);
        txtMemberId.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtMemberId.setBackground(Color.WHITE);
        txtMemberId.setForeground(mainGreen);
        txtMemberId.setCaretColor(mainGreen);
        txtMemberId.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        memberNameLabel.setPreferredSize(fieldSize);
        memberNameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        memberNameLabel.setForeground(Color.WHITE);
        
        bookComboBox.setPreferredSize(fieldSize);
        bookComboBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
        bookComboBox.setBackground(Color.WHITE);
        bookComboBox.setForeground(mainGreen);
        bookComboBox.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        returnDateLabel.setPreferredSize(fieldSize);
        returnDateLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        returnDateLabel.setForeground(Color.WHITE);
        
        txtDaysElapsed.setPreferredSize(fieldSize);
        txtDaysElapsed.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtDaysElapsed.setEditable(false);
        txtDaysElapsed.setBackground(Color.WHITE);
        txtDaysElapsed.setForeground(mainGreen);
        txtDaysElapsed.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtFine.setPreferredSize(fieldSize);
        txtFine.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtFine.setEditable(false);
        txtFine.setBackground(Color.WHITE);
        txtFine.setForeground(mainGreen);
        txtFine.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        // Buttons (styled)
        returnBtn.setBackground(new Color(0, 153, 102));
        returnBtn.setForeground(Color.WHITE);
        returnBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        returnBtn.setFocusPainted(false);
        returnBtn.setEnabled(false);
        
        cancelBtn.setBackground(new Color(220, 53, 69));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        cancelBtn.setFocusPainted(false);
        
        JPanel btnRow = new JPanel(new GridLayout(1, 2, 18, 0));
        btnRow.setOpaque(false);
        btnRow.add(returnBtn);
        btnRow.add(cancelBtn);
        
        // Table on the right side
        jTable1 = new JTable(new DefaultTableModel(
            new Object [][] {},
            new String [] { "Member ID", "Member Name", "Book", "Return Date", "Days Elapsed", "Fine" }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jTable1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        jTable1.setRowHeight(30);
        jTable1.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        jTable1.getTableHeader().setBackground(mainGreen);
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
        JScrollPane tableScrollPane = new JScrollPane(jTable1);
        tableScrollPane.setPreferredSize(new Dimension(720, 420));
        tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
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
        leftPanel.add(memberIdLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtMemberId, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(memberNameTextLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(memberNameLabel, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(bookLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(bookComboBox, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(returnDateTextLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(returnDateLabel, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(daysElapsedLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtDaysElapsed, lgbc);
        
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(fineLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtFine, lgbc);
        
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
        formPanel.add(tableScrollPane, fgbc);
        
        // Add formPanel to backgroundPanel with constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(formPanel, gbc);
        
        setContentPane(backgroundPanel);
        setTitle("Return Book");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1400, 700));
        pack();
        setLocationRelativeTo(null);
        
        // Add event handlers
        txtMemberId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    loadMemberBooks();
                }
            }
        });
        
        bookComboBox.addActionListener(e -> bookSelectionChanged());
        returnBtn.addActionListener(e -> returnBookAction());
        cancelBtn.addActionListener(e -> {
            this.dispose();
            new Main().setVisible(true);
        });
    }
    
    private void loadMemberBooks() {
        String memberId = txtMemberId.getText().trim();
        if (memberId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID");
            return;
        }
        
        try {
            // Get member name
            pst = con.prepareStatement("SELECT name FROM member WHERE id = ?");
            pst.setString(1, memberId);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                // Member found
                String memberName = rs.getString("name");
                memberNameLabel.setText(memberName);
                
                // Get books issued to this member
                pst = con.prepareStatement(
                    "SELECT l.id, b.id as bookid, b.name, l.issuedate, l.returndate, " +
                    "DATEDIFF(NOW(), l.returndate) AS elap " +
                    "FROM lendbook l " +
                    "JOIN book b ON l.bookid = b.id " +
                    "WHERE l.memberid = ?");
                pst.setString(1, memberId);
                rs = pst.executeQuery();
                
                // Clear previous books
                bookComboBox.removeAllItems();
                
                boolean hasBooks = false;
                
                while (rs.next()) {
                    hasBooks = true;
                    String issueId = rs.getString("id");
                    String bookName = rs.getString("name");
                    String issueDate = rs.getString("issuedate");
                    String returnDate = rs.getString("returndate");
                    int elapsedDays = rs.getInt("elap");
                    
                    // Calculate fine
                    int fine = 0;
                    if (elapsedDays > 0) {
                        fine = elapsedDays * 5; // $5 per day
                    }
                    
                    BookItem book = new BookItem(issueId, bookName, issueDate, returnDate, elapsedDays, fine);
                    bookComboBox.addItem(book);
                }
                
                if (!hasBooks) {
                    JOptionPane.showMessageDialog(this, "No books issued to this member");
                    clearFields();
                } else {
                    // Select first book
                    bookComboBox.setSelectedIndex(0);
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Member not found");
                clearFields();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Returnbook.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    private void bookSelectionChanged() {
        if (bookComboBox.getSelectedItem() != null) {
            BookItem book = (BookItem) bookComboBox.getSelectedItem();
            
            // Update fields
            returnDateLabel.setText(book.returnDate);
            txtDaysElapsed.setText(String.valueOf(book.elapsedDays));
            txtFine.setText(String.valueOf(book.fine));
            
            // Enable return button
            returnBtn.setEnabled(true);
        }
    }
    
    private void returnBookAction() {
        if (bookComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a book to return");
            return;
        }
        
        String memberId = txtMemberId.getText().trim();
        BookItem selectedBook = (BookItem) bookComboBox.getSelectedItem();
        
        try {
            // Start transaction
            con.setAutoCommit(false);
        
        try {
            // Insert into returnbook table
            pst = con.prepareStatement("INSERT INTO returnbook (member_id, membername, bookname, returndate, elap, fine) VALUES (?, ?, ?, ?, ?, ?)");
            pst.setString(1, memberId);
            pst.setString(2, memberNameLabel.getText());
            pst.setString(3, selectedBook.name);
            pst.setString(4, selectedBook.returnDate);
            pst.setString(5, String.valueOf(selectedBook.elapsedDays));
            pst.setString(6, String.valueOf(selectedBook.fine));

            int k = pst.executeUpdate();
            
            if (k == 1) {
                // Delete from lendbook
                pst = con.prepareStatement("DELETE FROM lendbook WHERE id = ?");
                pst.setString(1, selectedBook.id);
                pst.executeUpdate();
                    
                    // Commit transaction
                    con.commit();
                
                JOptionPane.showMessageDialog(this, "Book returned successfully");
                
                // Remove book from dropdown
                bookComboBox.removeItem(selectedBook);
                
                // If no more books, clear form
                if (bookComboBox.getItemCount() == 0) {
                    clearFields();
                    returnBtn.setEnabled(false);
                } else {
                    // Select first remaining book
                    bookComboBox.setSelectedIndex(0);
                }
                
                // Refresh table
                Returnbook_load();
                }
            } catch (SQLException ex) {
                // Rollback transaction on error
                con.rollback();
                ex.printStackTrace(); // Debug print for exception
                throw ex;
            } finally {
                // Reset auto-commit
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Returnbook.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    private void clearFields() {
        txtMemberId.setText("");
        memberNameLabel.setText("");
        bookComboBox.removeAllItems();
        returnDateLabel.setText("");
        txtDaysElapsed.setText("");
        txtFine.setText("");
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
            java.util.logging.Logger.getLogger(Returnbook.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Returnbook().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Returnbook.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration
    private JTextField txtMemberId;
    private JTextField txtDaysElapsed;
    private JTextField txtFine;
    private JButton returnBtn;
    private JTable jTable1;
    private JComboBox<BookItem> bookComboBox;
    private JLabel memberNameLabel;
    private JLabel returnDateLabel;
}