/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package SLibrary;

import java.sql.ResultSetMetaData;

import SLibrary.Book.categoryItem;
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
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;


/**
 *
 * @author DAKSHAYANI
 */
public class Category extends javax.swing.JFrame {

    /**
     * Creates new form Category
     */
    public Category() {
        initComponents();
        Connect(); 
        Category_load();
    }
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    
    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Updated driver for MySQL 8+
            con = DriverManager.getConnection("jdbc:mysql://sql5.freesqldatabase.com:3306/sql5777721", "sql5777721", "VSiGHrR7hT");
            System.out.println("Connected to database.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Hidden ID column for internal use
    private int selectedId = -1;
    
    public void Category_load() {
        try {
            pst = con.prepareStatement("SELECT * FROM category");
            rs = pst.executeQuery();
            
            DefaultTableModel d = (DefaultTableModel) jTable1.getModel();
            d.setRowCount(0); // clear existing rows
    
            while (rs.next()) {
                Vector<String> v2 = new Vector<>();
                // Don't include the ID field in the visible table
                v2.add(rs.getString("catname"));
                v2.add(rs.getString("status"));
                d.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
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
        // Modern split layout: left=form+buttons, right=table
        jPanel1 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new java.awt.Color(0, 102, 102));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtcategoryname = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtstatus = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 30));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Category");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Category Name");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Status");

        txtcategoryname.setFont(new java.awt.Font("SansSerif", 0, 16));
        txtcategoryname.setBackground(java.awt.Color.white);
        txtcategoryname.setForeground(new java.awt.Color(0, 102, 102));
        txtcategoryname.setCaretColor(new java.awt.Color(0, 102, 102));
        txtcategoryname.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white, 1));
        txtcategoryname.setPreferredSize(new Dimension(250, 36));
        txtcategoryname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcategorynameActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("SansSerif", 1, 16));
        jButton1.setText("ADD");
        jButton1.setBackground(new java.awt.Color(0, 153, 102));
        jButton1.setForeground(java.awt.Color.white);
        jButton1.setFocusPainted(false);
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("SansSerif", 1, 16));
        jButton2.setText("UPDATE");
        jButton2.setBackground(java.awt.Color.white);
        jButton2.setForeground(new java.awt.Color(0, 153, 102));
        jButton2.setFocusPainted(false);
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("SansSerif", 1, 16));
        jButton3.setText("DELETE");
        jButton3.setBackground(new java.awt.Color(220, 53, 69));
        jButton3.setForeground(java.awt.Color.white);
        jButton3.setFocusPainted(false);
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("SansSerif", 1, 16));
        jButton4.setText("CANCEL");
        jButton4.setBackground(java.awt.Color.white);
        jButton4.setForeground(new java.awt.Color(0, 153, 102));
        jButton4.setFocusPainted(false);
        jButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        // IMPORTANT CHANGE: Removed "ID" column from the table model
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Category Name", "Status" }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jTable1.setFont(new java.awt.Font("SansSerif", 0, 15));
        jTable1.setRowHeight(28);
        jTable1.getTableHeader().setFont(new java.awt.Font("SansSerif", 1, 15));
        jTable1.getTableHeader().setBackground(new java.awt.Color(0, 102, 102));
        jTable1.getTableHeader().setForeground(java.awt.Color.white);
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white, 2));

        txtstatus.setFont(new java.awt.Font("SansSerif", 0, 16));
        txtstatus.setBackground(java.awt.Color.white);
        txtstatus.setForeground(new java.awt.Color(0, 102, 102));
        txtstatus.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white, 1));
        txtstatus.setPreferredSize(new Dimension(250, 36));
        txtstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Deactive" }));

        // --- BUTTONS IN SINGLE ROW ---
        javax.swing.JPanel buttonRow = new javax.swing.JPanel();
        buttonRow.setOpaque(false);
        buttonRow.setLayout(new java.awt.GridLayout(1, 4, 25, 0)); // Increased spacing between buttons
        buttonRow.add(jButton1);
        buttonRow.add(jButton2);
        buttonRow.add(jButton3);
        buttonRow.add(jButton4);

        // --- LEFT PANEL (form + buttons) ---
        javax.swing.JPanel leftPanel = new javax.swing.JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints lgbc = new java.awt.GridBagConstraints();
        lgbc.insets = new java.awt.Insets(15, 15, 15, 15); // Increased padding
        lgbc.gridx = 0; lgbc.gridy = 0; lgbc.gridwidth = 2;
        lgbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        leftPanel.add(jLabel1, lgbc);

        // Add more vertical space after title
        lgbc.gridy++;
        lgbc.insets = new java.awt.Insets(25, 15, 15, 15);
        lgbc.gridwidth = 1;
        lgbc.anchor = java.awt.GridBagConstraints.WEST;
        leftPanel.add(jLabel2, lgbc);
        lgbc.gridx = 1; lgbc.anchor = java.awt.GridBagConstraints.EAST;
        leftPanel.add(txtcategoryname, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.anchor = java.awt.GridBagConstraints.WEST;
        lgbc.insets = new java.awt.Insets(15, 15, 15, 15);
        leftPanel.add(jLabel3, lgbc);
        lgbc.gridx = 1; lgbc.anchor = java.awt.GridBagConstraints.EAST;
        leftPanel.add(txtstatus, lgbc);
        lgbc.gridx = 0; lgbc.gridy++; lgbc.gridwidth = 2;
        lgbc.anchor = java.awt.GridBagConstraints.CENTER;
        lgbc.insets = new java.awt.Insets(30, 15, 15, 15); // More space before buttons
        leftPanel.add(buttonRow, lgbc);

        // --- MAIN SPLIT LAYOUT: left=form, right=table ---
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtcategorynameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcategorynameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcategorynameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String name = txtcategoryname.getText().trim();
        
        // Validate all fields
        String nameError = ValidationUtils.validateName(name);
        
        // Check for any validation errors
        if (nameError != null) {
            JOptionPane.showMessageDialog(this, nameError, "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            pst = con.prepareStatement("INSERT INTO category (catname, status) VALUES (?, 'Active')");
            pst.setString(1, name);
            
            int k = pst.executeUpdate();
            
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Category Added Successfully");
                clearFields();
                Category_load();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add category");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        txtcategoryname.setText("");
        txtcategoryname.requestFocus();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // UPDATE button logic
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to update");
            return;
        }
        
        String categoryname = txtcategoryname.getText().trim();
        String status = txtstatus.getSelectedItem().toString();
        
        // Validation
        if (categoryname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category name is required.");
            txtcategoryname.requestFocus();
            return;
        }
        
        try {
            pst = con.prepareStatement("UPDATE category SET catname = ?, status = ? WHERE id = ?");
            pst.setString(1, categoryname);
            pst.setString(2, status);
            pst.setInt(3, selectedId);
    
            int k = pst.executeUpdate();
    
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Category Updated");
                txtcategoryname.setText("");
                txtstatus.setSelectedIndex(0);
                txtcategoryname.requestFocus();
                selectedId = -1; // Reset selected ID
                Category_load();
                jButton1.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Category update failed");
            }
    
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // DELETE button logic
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to delete");
            return;
        }
        
        String categoryName = txtcategoryname.getText();
        
        try {
            // Check if category is in use in book table
            pst = con.prepareStatement("SELECT COUNT(*) FROM book WHERE category = ?");
            pst.setInt(1, selectedId);
            rs = pst.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                int bookCount = rs.getInt(1);
                JOptionPane.showMessageDialog(this, 
                    "Cannot delete category '" + categoryName + "' because it is assigned to " + 
                    bookCount + " book(s).\nPlease reassign these books to another category first.",
                    "Category In Use", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // If we get here, no books are using this category, so proceed with deletion
            pst = con.prepareStatement("DELETE FROM category WHERE id = ?");
            pst.setInt(1, selectedId);
            int k = pst.executeUpdate();
    
            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Category '" + categoryName + "' deleted successfully");
                txtcategoryname.setText("");
                txtstatus.setSelectedIndex(0);
                txtcategoryname.requestFocus();
                selectedId = -1; // Reset selected ID
                Category_load();
                jButton1.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete category");
            }
    
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // CANCEL button logic
        this.setVisible(false);
        new Main().setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // Table row click logic - FIXED to get ID from database
        DefaultTableModel d1 = (DefaultTableModel) jTable1.getModel();
        int selectIndex = jTable1.getSelectedRow();
        
        if (selectIndex != -1) {
            String categoryName = d1.getValueAt(selectIndex, 0).toString();
            String status = d1.getValueAt(selectIndex, 1).toString();
            
            try {
                // Get the ID from the database based on category name
                pst = con.prepareStatement("SELECT id FROM category WHERE catname = ?");
                pst.setString(1, categoryName);
                rs = pst.executeQuery();
                
                if (rs.next()) {
                    selectedId = rs.getInt("id");
                    txtcategoryname.setText(categoryName);
                    txtstatus.setSelectedItem(status);
                    jButton1.setEnabled(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Category().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtcategoryname;
    private javax.swing.JComboBox<String> txtstatus;
    // End of variables declaration//GEN-END:variables

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
}