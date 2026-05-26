package TiketPesawat.views;

import TiketPesawat.controller.PemesananController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static TiketPesawat.views.ThemeHelper.*;

/**
 * PemesananView — Komponen antarmuka (View) untuk menampilkan rekam data transaksi pemesanan tiket.
 * Berbeda dengan form logis sebelumnya, view ini berfokus pada visualisasi data tabular (Read-Only/Daftar).
 * * [OOP: Inheritance] Mewarisi kerangka windowing desktop visual dengan menurunkan class JFrame.
 * [OOP: Abstraction] Menyederhanakan konsistensi visual lewat 'ThemeHelper.*' untuk pewarnaan dan dekorasi tabel.
 */
public class PemesananView extends JFrame {

    // Dependency Injection: Menyimpan referensi PemesananController sebagai otak pengendali aksi
    PemesananController pc;
    
    // [OOP: Encapsulation] Menyembunyikan komponen visual tabel dan tombol dari akses langsung di luar package/class
    private JTable  tabelPemesanan;
    private JButton refreshBtn, kembaliBtn;

    // Constructor: Memetakan objek controller pendamping dan menyusun struktur layouting grafis
    public PemesananView(PemesananController c) {
        this.pc = c;
        initComponents();
    }

    // Bagian Penting: Konstruksi tata letak panel penampung tabel riwayat pemesanan tiket pesawat
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Data Pemesanan Tiket");
        setResizable(false);

        // Menggunakan BorderLayout pada root panel demi fleksibilitas pembagian area Header dan Content
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(LINEN);

        // Abstraction: Memanggil fungsi pembuat panel judul atas dari utilitas eksternal ThemeHelper
        JPanel header = createHeaderPanel("📋  Daftar Pemesanan Tiket", 800);
        root.add(header, BorderLayout.NORTH);

        // Content Panel: Menggunakan BorderLayout dengan celah vertikal (Vgap) sebesar 12 piksel
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(LINEN);
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16)); // Padding sekeliling konten

        // Komponen Tabel Data Transaksi Pemesanan
        tabelPemesanan = new JTable();
        tabelPemesanan.setAutoCreateRowSorter(true); // Fitur pengurutan data otomatis (sorting) saat header kolom diklik
        styleTable(tabelPemesanan);
        
        // JScrollPane bertindak sebagai wrapper pembungkus agar tabel memiliki scrollbar otomatis saat data meluap
        JScrollPane scroll = new JScrollPane(tabelPemesanan);
        styleScrollPane(scroll);
        content.add(scroll, BorderLayout.CENTER);

        // Kontainer tombol navigasi bawah (Merapat ke sisi kanan / FlowLayout.RIGHT)
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(LINEN);

        // Fabrikasi elemen tombol aksi lewat fungsi utilitas tema
        refreshBtn  = primaryButton("🔄 Refresh");
        kembaliBtn  = dangerButton("⬅ Kembali");

        // [OOP: Polymorphism / Lambda Expression] Penerapan fungsionalitas Event Listener yang ringkas menggantikan MouseAdapter anonim
        refreshBtn.addActionListener(e -> pc.refreshTable()); // Meminta controller memuat ulang isi dataset dari DB
        kembaliBtn.addActionListener(e -> pc.keAdmin());      // Navigasi mematikan view saat ini dan berpindah ke dashboard admin

        btnPanel.add(refreshBtn);
        btnPanel.add(kembaliBtn);
        content.add(btnPanel, BorderLayout.SOUTH);

        root.add(content, BorderLayout.CENTER);

        setContentPane(root);
        setSize(800, 480);
        setMinimumSize(new Dimension(800, 450));
        setLocationRelativeTo(null); // Memastikan posisi aplikasi muncul tepat di tengah-tengah monitor pengguna
    }

    // [OOP: Encapsulation] Menyediakan gerbang akses terbatas (Getter) agar Controller dapat memanipulasi DefaultTableModel dari luar
    public JTable getTabelPemesanan() { return tabelPemesanan; }
}