package SLibrary;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.ResultSetMetaData;

public class Book extends javax.swing.JFrame {

    // Helper class for rounded panel - MOVED UP before use
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
    
    public class categoryItem {
        int id;
        String name;
        
        public categoryItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
    
    public class authorItem {
        int id;
        String name;
        
        public authorItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
    
    public class publisherItem {
        int id;
        String name;
        
        public publisherItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    // Variable to track selected book ID
    private int selectedId = -1;
    
    // Components
    private JTextField txtname;
    private JTextField txtcontents;
    private JTextField txtnofpages;
    private JTextField txtedition;
    private JComboBox txtcategory;
    private JComboBox txtauthor;
    private JComboBox txtpublisher;
    private JButton jButton1; // Add
    private JButton jButton2; // Update
    private JButton jButton4; // Cancel
    private JTable jTable1;
    private JScrollPane jScrollPane1;
    
    // Added method for clearing fields - MOVED UP to fix reference error
    private void clearFields() {
        txtname.setText("");
        txtcategory.setSelectedIndex(0);
        txtauthor.setSelectedIndex(0);
        txtpublisher.setSelectedIndex(0);
        txtcontents.setText("");
        txtnofpages.setText("");
        txtedition.setText("");
        txtname.requestFocus();
    }
    
    public Book() throws SQLException {
        initComponents();
        Connect();
        Category();
        Author();
        Publisher();
        Book_load();
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
    
    public void Category() throws SQLException {
        try {
            pst = con.prepareStatement("SELECT * FROM category WHERE status = 'Active'");
            rs = pst.executeQuery();
            txtcategory.removeAllItems();
            
            // Add a default selection item
            txtcategory.addItem(new categoryItem(0, "-- Select Category --"));
            
            while (rs.next()) {
                txtcategory.addItem(new categoryItem(rs.getInt("id"), rs.getString("catname")));
            }
            
            // Select default item
            if (txtcategory.getItemCount() > 0) {
                txtcategory.setSelectedIndex(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error loading categories: " + ex.getMessage());
        }
    }
    
    public void Author() throws SQLException {
        try {
            pst = con.prepareStatement("SELECT * FROM author");
            rs = pst.executeQuery();
            txtauthor.removeAllItems();
            
            // Add a default selection item
            txtauthor.addItem(new authorItem(0, "-- Select Author --"));
            
            while (rs.next()) {
                txtauthor.addItem(new authorItem(rs.getInt("id"), rs.getString("name")));
            }
            
            // Select default item
            if (txtauthor.getItemCount() > 0) {
                txtauthor.setSelectedIndex(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error loading authors: " + ex.getMessage());
        }
    }
    
    public void Publisher() throws SQLException {
        try {
            pst = con.prepareStatement("SELECT * FROM publisher");
            rs = pst.executeQuery();
            txtpublisher.removeAllItems();
            
            // Add a default selection item
            txtpublisher.addItem(new publisherItem(0, "-- Select Publisher --"));
            
            while (rs.next()) {
                txtpublisher.addItem(new publisherItem(rs.getInt("id"), rs.getString("name")));
            }
            
            // Select default item
            if (txtpublisher.getItemCount() > 0) {
                txtpublisher.setSelectedIndex(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error loading publishers: " + ex.getMessage());
        }
    }
    
    public void Book_load() {
        try {
            pst = con.prepareStatement("SELECT b.id, b.name, c.catname, a.name AS author_name, " +
                                     "p.name AS publisher_name, b.contents, b.pages, b.edition " +
                                     "FROM book b " +
                                     "JOIN category c ON b.category = c.id " +
                                     "JOIN author a ON b.author = a.id " +
                                     "JOIN publisher p ON b.publisher = p.id");
            rs = pst.executeQuery();
            
            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0); // Clear existing rows
            
            while (rs.next()) {
                Vector<Object> v2 = new Vector<>();
                // Store ID internally but don't add to visible columns
                int id = rs.getInt("b.id");
                
                // Add only visible columns
                v2.add(rs.getString("b.name"));
                v2.add(rs.getString("c.catname"));
                v2.add(rs.getString("author_name"));
                v2.add(rs.getString("publisher_name"));
                v2.add(rs.getString("b.contents"));
                v2.add(rs.getString("b.pages"));
                v2.add(rs.getString("b.edition"));
                d.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error loading books: " + ex.getMessage());
        }
    }
    
    private void initComponents() {
        // Initialize components
        txtname = new JTextField();
        txtcontents = new JTextField();
        txtnofpages = new JTextField();
        txtedition = new JTextField();
        txtcategory = new JComboBox();
        txtauthor = new JComboBox();
        txtpublisher = new JComboBox();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton4 = new JButton();
        
        // CHANGED: Remove ID column from table model
        jTable1 = new JTable(new DefaultTableModel(
            new Object [][] {},
            new String [] { "Name", "Category", "Author", "Publisher", "Contents", "Pages", "Edition" }
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
        formPanel.setPreferredSize(new Dimension(1500, 650));
        formPanel.setBackground(mainGreen);

        // Title above 'BOOK'
        JLabel bigTitle = new JLabel("BOOKNEST");
        bigTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        bigTitle.setForeground(Color.WHITE);
        bigTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // 'BOOK' label, centered
        JLabel sectionLabel = new JLabel("BOOK");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionLabel.setForeground(Color.WHITE);
        sectionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Labels
        JLabel nameLabel = new JLabel("NAME");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        JLabel categoryLabel = new JLabel("CATEGORY");
        categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        categoryLabel.setForeground(Color.WHITE);
        JLabel authorLabel = new JLabel("AUTHOR");
        authorLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        authorLabel.setForeground(Color.WHITE);
        JLabel publisherLabel = new JLabel("PUBLISHER");
        publisherLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        publisherLabel.setForeground(Color.WHITE);
        JLabel contentsLabel = new JLabel("CONTENTS");
        contentsLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        contentsLabel.setForeground(Color.WHITE);
        JLabel pagesLabel = new JLabel("PAGES");
        pagesLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        pagesLabel.setForeground(Color.WHITE);
        JLabel editionLabel = new JLabel("EDITION");
        editionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        editionLabel.setForeground(Color.WHITE);

        // Fields (modern size)
        Dimension fieldSize = new Dimension(280, 36);
        txtname.setPreferredSize(fieldSize);
        txtname.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtname.setBackground(Color.WHITE);
        txtname.setForeground(mainGreen);
        txtname.setCaretColor(mainGreen);
        txtname.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtcontents.setPreferredSize(fieldSize);
        txtcontents.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtcontents.setBackground(Color.WHITE);
        txtcontents.setForeground(mainGreen);
        txtcontents.setCaretColor(mainGreen);
        txtcontents.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtnofpages.setPreferredSize(fieldSize);
        txtnofpages.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtnofpages.setBackground(Color.WHITE);
        txtnofpages.setForeground(mainGreen);
        txtnofpages.setCaretColor(mainGreen);
        txtnofpages.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtedition.setPreferredSize(fieldSize);
        txtedition.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtedition.setBackground(Color.WHITE);
        txtedition.setForeground(mainGreen);
        txtedition.setCaretColor(mainGreen);
        txtedition.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtcategory.setPreferredSize(fieldSize);
        txtcategory.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtcategory.setBackground(Color.WHITE);
        txtcategory.setForeground(mainGreen);
        txtcategory.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtauthor.setPreferredSize(fieldSize);
        txtauthor.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtauthor.setBackground(Color.WHITE);
        txtauthor.setForeground(mainGreen);
        txtauthor.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        txtpublisher.setPreferredSize(fieldSize);
        txtpublisher.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtpublisher.setBackground(Color.WHITE);
        txtpublisher.setForeground(mainGreen);
        txtpublisher.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Buttons (styled, single row)
        JButton addBtn = jButton1;
        addBtn.setText("ADD");
        addBtn.setBackground(new Color(0, 153, 102));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        addBtn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        JButton updateBtn = jButton2;
        updateBtn.setText("UPDATE");
        updateBtn.setBackground(Color.WHITE);
        updateBtn.setForeground(new Color(0, 153, 102));
        updateBtn.setFocusPainted(false);
        updateBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        updateBtn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        // Add DELETE button
        JButton deleteBtn = new JButton();
        deleteBtn.setText("DELETE");
        deleteBtn.setBackground(new Color(220, 53, 69)); // red
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        deleteBtn.setEnabled(false);
        // Wire to deleteBook()
        deleteBtn.addActionListener(e -> deleteBook());
        
        JButton cancelBtn = jButton4;
        cancelBtn.setText("CANCEL");
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(new Color(0, 153, 102));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        JPanel btnRow = new JPanel(new GridLayout(1, 4, 18, 0));
        btnRow.setOpaque(false);
        btnRow.add(addBtn);
        btnRow.add(updateBtn);
        btnRow.add(deleteBtn);
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
        jScrollPane1.setPreferredSize(new Dimension(1000, 420));
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
        leftPanel.add(txtname, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(categoryLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtcategory, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(authorLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtauthor, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(publisherLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtpublisher, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(contentsLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtcontents, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(pagesLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtnofpages, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = GridBagConstraints.WEST;
        leftPanel.add(editionLabel, lgbc);
        lgbc.gridx = 1; lgbc.anchor = GridBagConstraints.EAST;
        leftPanel.add(txtedition, lgbc);
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

        // Add formPanel to backgroundPanel
        backgroundPanel.add(formPanel);
        setContentPane(backgroundPanel);
        setTitle("Book");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1600, 750));
        pack();
        setLocationRelativeTo(null);
        
        // Add action listeners (THIS IS THE KEY ADDITION)
        addBtn.addActionListener(e -> addBook());
        updateBtn.addActionListener(e -> updateBook());
        cancelBtn.addActionListener(e -> this.setVisible(false));
        
        // Add table selection listener
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRowClicked();
                deleteBtn.setEnabled(true); // Enable delete when a row is selected
            }
        });
        
        // Initially disable update and delete buttons until row is selected
        updateBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }

    private void addBook() {
        String name = txtname.getText().trim();
        String contents = txtcontents.getText().trim();
        String pages = txtnofpages.getText().trim();
        String edition = txtedition.getText().trim();
        
        // Get selected values from combo boxes
        categoryItem citem = (categoryItem)txtcategory.getSelectedItem();
        authorItem aitem = (authorItem)txtauthor.getSelectedItem();
        publisherItem pitem = (publisherItem)txtpublisher.getSelectedItem();
        
        // Validate all fields
        String nameError = ValidationUtils.validateRequiredField("Book name", name);
        String contentsError = ValidationUtils.validateRequiredField("Contents", contents);
        String pagesError = ValidationUtils.validateRequiredField("Pages", pages);
        String editionError = ValidationUtils.validateRequiredField("Edition", edition);
        
        // Check for any validation errors
        if (nameError != null || contentsError != null || pagesError != null || editionError != null) {
            StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");
            if (nameError != null) errorMessage.append("\n- ").append(nameError);
            if (contentsError != null) errorMessage.append("\n- ").append(contentsError);
            if (pagesError != null) errorMessage.append("\n- ").append(pagesError);
            if (editionError != null) errorMessage.append("\n- ").append(editionError);
            
            JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate combo box selections
        if (citem == null || citem.id == 0) {
            JOptionPane.showMessageDialog(this, "Please select a Category", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (aitem == null || aitem.id == 0) {
            JOptionPane.showMessageDialog(this, "Please select an Author", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (pitem == null || pitem.id == 0) {
            JOptionPane.showMessageDialog(this, "Please select a Publisher", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            pst = con.prepareStatement("INSERT INTO book (name, category, author, publisher, contents, pages, edition) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, name);
            pst.setInt(2, citem.id);
            pst.setInt(3, aitem.id);
            pst.setInt(4, pitem.id);
            pst.setString(5, contents);
            pst.setString(6, pages);
            pst.setString(7, edition);
            
            int k = pst.executeUpdate();
            
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Book Added Successfully");
                clearFields();
                Book_load();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add book");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
    
    private void updateBook() {
        // UPDATED: Now uses selectedId instead of getting from table
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to update");
            return;
        }
        
        // Validation
        if (txtname.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Book name is required");
            txtname.requestFocus();
            return;
        }
        
        if (txtcategory.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a category");
            txtcategory.requestFocus();
            return;
        }
        
        if (txtauthor.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select an author");
            txtauthor.requestFocus();
            return;
        }
        
        if (txtpublisher.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a publisher");
            txtpublisher.requestFocus();
            return;
        }
        
        // Get values
        String name = txtname.getText().trim();
        categoryItem citem = (categoryItem)txtcategory.getSelectedItem();
        authorItem aitem = (authorItem)txtauthor.getSelectedItem();
        publisherItem pitem = (publisherItem)txtpublisher.getSelectedItem();
        String contents = txtcontents.getText().trim();
        String pages = txtnofpages.getText().trim();
        String edition = txtedition.getText().trim();
        
        try {
            pst = con.prepareStatement("UPDATE book SET name=?, category=?, author=?, publisher=?, contents=?, pages=?, edition=? WHERE id=?");
            pst.setString(1, name);
            pst.setInt(2, citem.id);
            pst.setInt(3, aitem.id);
            pst.setInt(4, pitem.id);
            pst.setString(5, contents);
            pst.setString(6, pages);
            pst.setString(7, edition);
            pst.setInt(8, selectedId);

            int k = pst.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Book updated successfully");
                clearFields();
                selectedId = -1; // Reset selected ID
                Book_load();
                jButton1.setEnabled(true);
                jButton2.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update book");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
    
    private void tableRowClicked() {
        // UPDATED: Table row click handler to get ID from database
        try {
            DefaultTableModel d1 = (DefaultTableModel) jTable1.getModel();
            int selectIndex = jTable1.getSelectedRow();
            
            if (selectIndex >= 0) {
                String bookName = d1.getValueAt(selectIndex, 0).toString();
                String categoryName = d1.getValueAt(selectIndex, 1).toString();
                
                // Get book ID from database
                pst = con.prepareStatement("SELECT b.id FROM book b " +
                                         "JOIN category c ON b.category = c.id " +
                                         "WHERE b.name = ? AND c.catname = ?");
                pst.setString(1, bookName);
                pst.setString(2, categoryName);
                rs = pst.executeQuery();
                
                if (rs.next()) {
                    selectedId = rs.getInt("id");
                    
                    // Fill form fields with selected data
                    txtname.setText(bookName);
                    
                    // Category
                    for (int i = 0; i < txtcategory.getItemCount(); i++) {
                        categoryItem item = (categoryItem) txtcategory.getItemAt(i);
                        if (item.toString().equals(categoryName)) {
                            txtcategory.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    // Author
                    String authorName = d1.getValueAt(selectIndex, 2).toString();
                    for (int i = 0; i < txtauthor.getItemCount(); i++) {
                        authorItem item = (authorItem) txtauthor.getItemAt(i);
                        if (item.toString().equals(authorName)) {
                            txtauthor.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    // Publisher
                    String pubName = d1.getValueAt(selectIndex, 3).toString();
                    for (int i = 0; i < txtpublisher.getItemCount(); i++) {
                        publisherItem item = (publisherItem) txtpublisher.getItemAt(i);
                        if (item.toString().equals(pubName)) {
                            txtpublisher.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    txtcontents.setText(d1.getValueAt(selectIndex, 4).toString());
                    txtnofpages.setText(d1.getValueAt(selectIndex, 5).toString());
                    txtedition.setText(d1.getValueAt(selectIndex, 6).toString());
                    
                    // Enable update and delete buttons, disable add button
                    jButton1.setEnabled(false);
                    jButton2.setEnabled(true);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error selecting record: " + ex.getMessage());
        }
    }
    
    // Add this method for book deletion
    private void deleteBook() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            return;
        }
        try {
            // Check if book is currently issued
            pst = con.prepareStatement("SELECT COUNT(*) FROM lendbook WHERE bookid = ?");
            pst.setInt(1, selectedId);
            rs = pst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Cannot delete this book because it is currently issued.");
                return;
            }
            // Get book name for returnbook deletion
            String bookName = txtname.getText().trim();
            // Delete from returnbook first
            pst = con.prepareStatement("DELETE FROM returnbook WHERE bookname = ?");
            pst.setString(1, bookName);
            pst.executeUpdate();
            // Now delete the book
            pst = con.prepareStatement("DELETE FROM book WHERE id = ?");
            pst.setInt(1, selectedId);
            int k = pst.executeUpdate();
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully");
                clearFields();
                selectedId = -1;
                Book_load();
                jButton1.setEnabled(true);
                jButton2.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete book");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error deleting book: " + ex.getMessage());
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
            java.util.logging.Logger.getLogger(Book.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Book().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}