package TiketPesawat.views;

import TiketPesawat.controller.AdminController;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;

/**
 * AdminView — Representasi antar muka (GUI) untuk halaman dashboard admin.
 * * [OOP: Inheritance] Mewarisi sifat JComponent/JFrame untuk rendering window grafis desktop.
 * [OOP: Abstraction] Menyembunyikan kompleksitas kalkulasi piksel gambar lewat fungsi komponen Swing.
 */
public class AdminView extends JFrame {

    // Konstanta tema warna palet cokelat (Akses private menerapkan prinsip Encapsulation)
    private static final Color LINEN    = new Color(0xF5, 0xF1, 0xEA);
    private static final Color KHAKI    = new Color(0xD7, 0xC9, 0xB8);
    private static final Color CAMEL    = new Color(0xB2, 0x96, 0x7D);
    private static final Color COCOA    = new Color(0x7D, 0x5A, 0x44);
    private static final Color ESPRESSO = new Color(0x4A, 0x34, 0x2A);

    // [OOP: Encapsulation] Objek controller dibatasi default package-private untuk interaksi komponen MVC
    AdminController ac;
    private JLabel  sapaan;
    private JButton kotaBtn, pesawatBtn, jadwalBtn, pemesananBtn, keluarBtn;

    // Constructor: Menerima objek dependency injection dari Controller untuk mengontrol alur bisnis view
    public AdminView(AdminController c) {
        this.ac = c;
        initComponents();
    }

    // Bagian Penting: Konstruksi struktur layouting komponen grafis window utama
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dashboard Admin");
        setResizable(false);

        // [OOP: Polymorphism / Inner Class] Melakukan overriding method paintComponent secara langsung (Anonymous Class)
        JPanel root = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                // Menggambar background warna gradasi linear dari atas-kiri ke bawah-kanan
                g2.setPaint(new GradientPaint(0, 0, LINEN, getWidth(), getHeight(), KHAKI));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        root.setPreferredSize(new Dimension(440, 420));

        // ── Sidebar strip ──────────────────────────────────────────
        // [OOP: Polymorphism] Polimorfisme dinamis runtime untuk kustomisasi rendering background sidebar
        JPanel sidebar = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, ESPRESSO, 0, getHeight(), COCOA));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        sidebar.setBounds(0, 0, 140, 420);
        root.add(sidebar);

        // Pengaturan ikon maskapai penerbangan berbasis font emoji
        JLabel planeIcon = new JLabel("✈", SwingConstants.CENTER);
        planeIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        planeIcon.setForeground(new Color(255,255,255,200));
        planeIcon.setBounds(0, 24, 140, 46);
        sidebar.add(planeIcon);

        JLabel appName = new JLabel("Tiket Pesawat", SwingConstants.CENTER);
        appName.setFont(new Font("Georgia", Font.BOLD, 13));
        appName.setForeground(Color.WHITE);
        appName.setBounds(0, 68, 140, 20);
        sidebar.add(appName);

        JLabel adminIcon = new JLabel("👤", SwingConstants.CENTER);
        adminIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        adminIcon.setBounds(0, 120, 140, 40);
        sidebar.add(adminIcon);

        sapaan = new JLabel("Admin", SwingConstants.CENTER);
        sapaan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sapaan.setForeground(new Color(255,255,255,220));
        sapaan.setBounds(0, 160, 140, 20);
        sidebar.add(sapaan);

        JLabel roleLabel = new JLabel("Administrator", SwingConstants.CENTER);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        roleLabel.setForeground(CAMEL);
        roleLabel.setBounds(0, 180, 140, 18);
        sidebar.add(roleLabel);

        // ── Main content ───────────────────────────────────────────
        JLabel title = new JLabel("Halaman Admin");
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(ESPRESSO);
        title.setBounds(165, 28, 240, 28);
        root.add(title);

        JLabel subtitle = new JLabel("Pilih menu manajemen di bawah ini");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitle.setForeground(CAMEL);
        subtitle.setBounds(165, 56, 260, 18);
        root.add(subtitle);

        JSeparator sep = new JSeparator();
        sep.setForeground(KHAKI);
        sep.setBounds(160, 78, 262, 2);
        root.add(sep);

        // Membuat instances button navigasi menu utama admin
        int y = 96;
        kotaBtn      = createMenuButton("🏙  Kelola Kota",             COCOA);
        pesawatBtn   = createMenuButton("✈  Kelola Pesawat",          COCOA);
        jadwalBtn    = createMenuButton("📅  Jadwal Penerbangan",       COCOA);
        pemesananBtn = createMenuButton("📋  Lihat Pemesanan",          CAMEL);

        kotaBtn.setBounds(160, y, 262, 44); y += 52;
        root.add(kotaBtn);
        pesawatBtn.setBounds(160, y, 262, 44); y += 52;
        root.add(pesawatBtn);
        jadwalBtn.setBounds(160, y, 262, 44); y += 52;
        root.add(jadwalBtn);
        pemesananBtn.setBounds(160, y, 262, 44); y += 60;
        root.add(pemesananBtn);

        keluarBtn = createMenuButton("⬅  Logout", new Color(0x8B, 0x2E, 0x2E));
        keluarBtn.setBounds(160, y, 262, 40);
        root.add(keluarBtn);

        // Menghubungkan Event Listener (Lambda Expression) ke Controller untuk merespons klik user
        kotaBtn.addActionListener(e -> ac.keKota());
        pesawatBtn.addActionListener(e -> ac.kePesawat());
        jadwalBtn.addActionListener(e -> ac.keJadwal());
        pemesananBtn.addActionListener(e -> ac.kePemesanan());
        keluarBtn.addActionListener(e -> ac.logout());

        setContentPane(root);
        pack();
        setLocationRelativeTo(null); // Memosisikan window otomatis tepat di tengah layar komputer
    }

    // Helper Method: Membuat button kustom dengan sudut membulat (Rounded Button) secara terpusat
    private JButton createMenuButton(String text, Color bg) {
        // [OOP: Polymorphism] Overriding method paintComponent milik kelas JButton secara anonim
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = (Color) getClientProperty("bg");
                // Menentukan shading warna tombol berdasarkan state (Klik, Hover, atau Normal)
                g2.setColor(getModel().isPressed() ? base.darker() : getModel().isRollover() ? base.brighter() : base);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        // Konfigurasi style properti visual tombol agar menyatu dengan desain kustom
        btn.putClientProperty("bg", bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // [OOP: Encapsulation] Metode mutator luar untuk mengubah teks sapaan user secara tidak langsung
    public void ubahLabel(String nama) {
        sapaan.setText(nama);
    }
}