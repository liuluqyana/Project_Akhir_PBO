package TiketPesawat.views;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * ThemeHelper — Utilitas global penyedia aset tema komponen antarmuka grafis (UI Helper).
 * Kelas ini memusatkan seluruh konfigurasi estetika palet warna cokelat-krim (Warm Earth Tone).
 * * [OOP: Abstraction] Mengabstraksi dan merangkum detail dekorasi layout (Font, Warna, Border) 
 * agar komponen View lain cukup memanggil fungsinya tanpa perlu menulis ulang baris kode CSS-like Java.
 */
public class ThemeHelper {

    // ── Global Color Palette Constants ──────────────────────────────────────────
    // Hak akses public static final membolehkan seluruh kelas dalam proyek memakai konstanta ini tanpa instansiasi objek
    public static final Color LINEN    = new Color(0xF5, 0xF1, 0xEA);
    public static final Color KHAKI    = new Color(0xD7, 0xC9, 0xB8);
    public static final Color CAMEL    = new Color(0xB2, 0x96, 0x7D);
    public static final Color COCOA    = new Color(0x7D, 0x5A, 0x44);
    public static final Color ESPRESSO = new Color(0x4A, 0x34, 0x2A);

    /** * Mendekorasi komponen JTable agar memiliki keselarasan visual bertema premium modern.
     * Mengatur tinggi baris, pewarnaan sel aktif (selection), serta menyembunyikan grid vertikal.
     */
    public static void styleTable(JTable table) {
        table.setBackground(Color.WHITE);
        table.setForeground(ESPRESSO);
        table.setSelectionBackground(CAMEL);
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(28); // Memberikan ruang padding vertikal yang lega pada tiap record data
        table.setGridColor(KHAKI);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false); // Clean Design: Menghilangkan pembatas tegak lurus kolom
        table.setIntercellSpacing(new Dimension(0, 0));

        // Melakukan kustomisasi visual khusus pada baris judul teratas tabel (Header)
        JTableHeader header = table.getTableHeader();
        header.setBackground(ESPRESSO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(0, 32));
        header.setBorder(BorderFactory.createEmptyBorder());
    }

    /** * Mendekorasi panel container scrollbar (JScrollPane) dengan sudut border yang melengkung halus.
     */
    public static void styleScrollPane(JScrollPane sp) {
        sp.setBorder(new LineBorder(KHAKI, 1, true));
        sp.getViewport().setBackground(Color.WHITE);
    }

    /** * Menyeragamkan tampilan text field input maupun combo box komponen formulir secara in-place.
     * Menggunakan CompoundBorder untuk memadukan garis tepi (LineBorder) dengan padding teks internal (EmptyBorder).
     */
    public static void styleInputField(JComponent c) {
        c.setBackground(LINEN);
        c.setForeground(ESPRESSO);
        c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        // Menggabungkan pembatas luar (garis Khaki melengkung) dengan jarak ketikan teks (padding 4px 8px)
        c.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(KHAKI, 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        c.setPreferredSize(new Dimension(c.getPreferredSize().width, 30));
    }

    /** * Menyeragamkan fontasi label penanda di atas kolom input data.
     */
    public static void styleLabel(JLabel lbl) {
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(ESPRESSO);
    }

    /** * Pabrikasi Tombol Utama (Primary Button) dengan gaya rounded melengkung bernuansa Cocoa.
     * [OOP: Polymorphism / Inner Class] Memanfaatkan teknik pewarnaan dinamis di dalam paintComponent.
     */
    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Interaksi Hover-State: Mengubah warna tombol berdasarkan deteksi kursor mouse
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
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Mengubah cursor menjadi ikon tangan
        btn.setPreferredSize(new Dimension(120, 32));
        return btn;
    }

    /** * Pabrikasi Tombol Bahaya / Pembatalan (Danger / Back Button) dengan aksen warna Merah Marun Red-Crimson.
     */
    public static JButton dangerButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = new Color(0x8B, 0x2E, 0x2E); // Warna dasar merah marun
                // Menghitung transisi warna redup saat ditekan (Pressed) dan cerah saat disorot (Rollover)
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

    /** * Pabrikasi Tombol Sekunder (Outline Button) berlatar transparan dengan garis tepi tipis warna Camel.
     */
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

    /** * Fabrikasi Panel Spanduk Atas (Header Banner Bar) yang menghiasi setiap halaman administrasi.
     * Menerapkan sapuan gradasi warna horizontal dari Espresso menuju Cocoa secara dinamis sesuai lebar parameter window.
     */
    public static JPanel createHeaderPanel(String titleText, int width) {
        JPanel header = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                // Menggambar visual transisi gradasi warna horizontal (Kiri ke Kanan)
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