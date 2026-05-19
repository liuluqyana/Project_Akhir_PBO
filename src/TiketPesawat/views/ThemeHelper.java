package TiketPesawat.views;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * Shared UI theme utilities for the brown-cream color palette.
 */
public class ThemeHelper {

    public static final Color LINEN    = new Color(0xF5, 0xF1, 0xEA);
    public static final Color KHAKI    = new Color(0xD7, 0xC9, 0xB8);
    public static final Color CAMEL    = new Color(0xB2, 0x96, 0x7D);
    public static final Color COCOA    = new Color(0x7D, 0x5A, 0x44);
    public static final Color ESPRESSO = new Color(0x4A, 0x34, 0x2A);

    /** Apply theme to a JTable */
    public static void styleTable(JTable table) {
        table.setBackground(Color.WHITE);
        table.setForeground(ESPRESSO);
        table.setSelectionBackground(CAMEL);
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(28);
        table.setGridColor(KHAKI);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        header.setBackground(ESPRESSO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(0, 32));
        header.setBorder(BorderFactory.createEmptyBorder());
    }

    /** Style a JScrollPane */
    public static void styleScrollPane(JScrollPane sp) {
        sp.setBorder(new LineBorder(KHAKI, 1, true));
        sp.getViewport().setBackground(Color.WHITE);
    }

    /** Style a text field / combo in-place */
    public static void styleInputField(JComponent c) {
        c.setBackground(LINEN);
        c.setForeground(ESPRESSO);
        c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        c.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(KHAKI, 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        c.setPreferredSize(new Dimension(c.getPreferredSize().width, 30));
    }

    /** Style a JLabel as field label */
    public static void styleLabel(JLabel lbl) {
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(ESPRESSO);
    }

    /** Create a styled primary button */
    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? ESPRESSO : getModel().isRollover() ? CAMEL : COCOA);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 32));
        return btn;
    }

    /** Create a styled danger/back button */
    public static JButton dangerButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = new Color(0x8B, 0x2E, 0x2E);
                g2.setColor(getModel().isPressed() ? base.darker() : getModel().isRollover() ? new Color(0xAA,0x44,0x44) : base);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 32));
        return btn;
    }

    /** Create a styled secondary (outline) button */
    public static JButton secondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(COCOA);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CAMEL, 1, true),
            BorderFactory.createEmptyBorder(4, 12, 4, 12)));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 32));
        return btn;
    }

    /** Create a panel header bar */
    public static JPanel createHeaderPanel(String titleText, int width) {
        JPanel header = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, ESPRESSO, width, 0, COCOA));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(width, 56));
        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Georgia", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setBounds(20, 14, width - 40, 28);
        header.add(title);
        return header;
    }
}
