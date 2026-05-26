package TiketPesawat.views;

import TiketPesawat.controller.PesanController;
import TiketPesawat.model.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static TiketPesawat.views.ThemeHelper.*;

/**
 * PesanView — Komponen antarmuka (View) untuk melayani transaksi pemesanan dan cetak tiket oleh penumpang.
 * Mengintegrasikan form input identitas, seleksi rute, kalkulasi otomatis, hingga tabel manifest jadwal.
 * * [OOP: Inheritance] Mewarisi seluruh fungsionalitas container windows grafis dari class JFrame.
 * [OOP: Encapsulation] Mengisolasi seluruh field masukan data agar terlindung dari manipulasi luar secara langsung.
 */
public class PesanView extends JFrame {

    // Dependency Injection: Menyimpan referensi objek controller untuk delegasi aksi transaksional
    PesanController pc;

    // [OOP: Encapsulation] Deklarasi komponen grafis menggunakan hak akses private (Data Hiding)
    private JTextField      nik, nama, noHp, jumlah, kodeJadwal, totalHarga, bayar;
    private JComboBox<String> kotaAwal, kotaTujuan;
    private JTable            pesananTabel;
    private JButton           cekBtn, cetakBtn, adminBtn;

    // Constructor: Menghubungkan controller, mengonstruksi UI, serta memuat daftar rute kota dari database
    public PesanView(PesanController c) {
        this.pc = c;
        initComponents();
        
        // Membersihkan item drop-down sebelum melakukan sinkronisasi data dari DB
        kotaAwal.removeAllItems();
        kotaTujuan.removeAllItems();
        loadDataKota();
    }

    // Bagian Penting: Konstruksi arsitektur tata letak antarmuka panel pemesanan tiket penerbangan
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pesan Tiket Pesawat");
        setResizable(false);

        // Menggunakan BorderLayout sebagai penata letak dasar container window utama
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(LINEN);

        // ── Header (Polimorfisme Kustom Mewarnai Gradasi Linear) ────────────────────
        // [OOP: Polymorphism] Overriding method paintComponent secara dinamis lewat Anonymous Inner Class
        JPanel header = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                // Menggambar visual gradasi warna mewah dari Espresso ke Cocoa
                g2.setPaint(new GradientPaint(0, 0, ESPRESSO, getWidth(), 0, COCOA));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(760, 56));

        JLabel titleLbl = new JLabel("✈  Pesan Tiket Penerbangan");
        titleLbl.setFont(new Font("Georgia", Font.BOLD, 18));
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setBounds(20, 14, 500, 28);
        header.add(titleLbl);

        // Tombol navigasi cepat untuk beralih ke halaman autentikasi Admin
        adminBtn = new JButton("👤 Admin");
        adminBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        adminBtn.setForeground(Color.WHITE);
        adminBtn.setBackground(new Color(255, 255, 255, 50));
        adminBtn.setBorder(new LineBorder(new Color(255, 255, 255, 100), 1, true));
        adminBtn.setFocusPainted(false);
        adminBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        adminBtn.setBounds(670, 14, 76, 28);
        header.add(adminBtn);
        
        adminBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { pc.keMasuk(); }
        });

        root.add(header, BorderLayout.NORTH);

        // ── Main Content (Absolute Layouting) ───────────────────────────────────────
        JPanel content = new JPanel(null);
        content.setBackground(LINEN);
        content.setPreferredSize(new Dimension(760, 520));

        // AREA KIRI (1): Kartu Identitas Penumpang
        JPanel penumpangCard = makeCard("Data Penumpang", 10, 10, 330, 200);
        content.add(penumpangCard);

        nik    = addFieldToCard(penumpangCard, "NIK",        10, 36);
        nama   = addFieldToCard(penumpangCard, "Nama",       10, 96);
        noHp   = addFieldToCard(penumpangCard, "No. HP",     170, 36);
        jumlah = addFieldToCard(penumpangCard, "Jml. Kursi", 170, 96);

        // AREA KIRI (2): Kartu Filter Penelusuran Rute
        JPanel ruteCard = makeCard("Pilih Rute Penerbangan", 10, 220, 330, 175);
        content.add(ruteCard);

        JLabel kotaAwalLbl = new JLabel("Kota Asal");
        styleLabel(kotaAwalLbl);
        kotaAwalLbl.setBounds(10, 36, 145, 16);
        ruteCard.add(kotaAwalLbl);

        kotaAwal = new JComboBox<>();
        styleCombo(kotaAwal, 10, 54, 145);
        ruteCard.add(kotaAwal);

        JLabel kotaTujuanLbl = new JLabel("Kota Tujuan");
        styleLabel(kotaTujuanLbl);
        kotaTujuanLbl.setBounds(165, 36, 145, 16);
        ruteCard.add(kotaTujuanLbl);

        kotaTujuan = new JComboBox<>();
        styleCombo(kotaTujuan, 165, 54, 145);
        ruteCard.add(kotaTujuan);

        // Aksi Trigger: Menyaring ketersediaan data tabel penerbangan sesuai rute drop-down
        cekBtn = primaryButton("🔍 Cek Jadwal Penerbangan");
        cekBtn.setBounds(10, 108, 300, 36);
        ruteCard.add(cekBtn);
        cekBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                pc.cekJadwal(
                    String.valueOf(kotaAwal.getSelectedItem()),
                    String.valueOf(kotaTujuan.getSelectedItem())
                );
            }
        });

        // AREA KIRI (3): Kartu Kunci Informasi Ringkasan Pembayaran (Read-Only)
        JPanel bayarCard = makeCard("Pembayaran", 10, 404, 330, 110);
        content.add(bayarCard);

        JLabel kodeJdwLbl = new JLabel("Kode Jadwal");
        styleLabel(kodeJdwLbl);
        kodeJdwLbl.setBounds(10, 34, 130, 16);
        bayarCard.add(kodeJdwLbl);

        kodeJadwal = new JTextField();
        kodeJadwal.setEditable(false); // Terkunci otomatis dari pilihan klik baris tabel
        styleInputField(kodeJadwal);
        kodeJadwal.setBackground(KHAKI);
        kodeJadwal.setBounds(140, 32, 178, 28);
        bayarCard.add(kodeJadwal);

        JLabel totalLbl = new JLabel("Total Harga");
        styleLabel(totalLbl);
        totalLbl.setBounds(10, 68, 130, 16);
        bayarCard.add(totalLbl);

        totalHarga = new JTextField();
        totalHarga.setEditable(false); // Terkunci (Hasil kalkulasi sistem)
        styleInputField(totalHarga);
        totalHarga.setBackground(KHAKI);
        totalHarga.setBounds(140, 66, 178, 28);
        bayarCard.add(totalHarga);

        // AREA KANAN (1): Kartu Komponen Tabel Jadwal Maskapai
        JPanel jadwalCard = makeCard("Jadwal Tersedia", 350, 10, 400, 370);
        content.add(jadwalCard);

        pesananTabel = new JTable();
        styleTable(pesananTabel);
        JScrollPane scroll = new JScrollPane(pesananTabel);
        styleScrollPane(scroll);
        scroll.setBounds(10, 34, 378, 324);
        jadwalCard.add(scroll);

        // Event Table Mouse Click: Validasi kuantitas kursi dan pemetaan hitung harga otomatis
        pesananTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (jumlah.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Isi jumlah kursi terlebih dahulu!");
                    jumlah.setText("0");
                }
                int row = pesananTabel.getSelectedRow();
                // Mengambil nilai primary key 'Kode Jadwal' dan 'Harga Satuan' dari kolom tabel
                kodeJadwal.setText(pesananTabel.getModel().getValueAt(row, 0).toString());
                String hargaTiket = pesananTabel.getModel().getValueAt(row, 6).toString();
                // Mendelegasikan logika perkalian harga total ke controller luar
                totalHarga.setText(pc.hitungHarga(jumlah.getText(), hargaTiket));
            }
        });

        // AREA KANAN (2): Panel Validasi nominal uang pembayaran tunai/debit
        JPanel bayarRightCard = makeCard("Masukkan Pembayaran", 350, 388, 400, 126);
        content.add(bayarRightCard);

        JLabel bayarLbl = new JLabel("Bayar (Rp)");
        styleLabel(bayarLbl);
        bayarLbl.setBounds(10, 36, 130, 16);
        bayarRightCard.add(bayarLbl);

        bayar = new JTextField();
        styleInputField(bayar);
        bayar.setBounds(140, 34, 248, 28);
        bayarRightCard.add(bayar);

        // Aksi Cetak: Mengemas data form ke dalam sebuah objek entitas tunggal (Model) sebelum dikirim ke database
        cetakBtn = primaryButton("🖨  Cetak Tiket Penerbangan");
        cetakBtn.setBounds(10, 72, 378, 38);
        bayarRightCard.add(cetakBtn);
        
        cetakBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // [OOP: Instantiation] Membungkus parameter masukan menjadi objek model entitas 'Orang'
                Orang orang = new Orang(
                    nik.getText(), nama.getText(), noHp.getText(),
                    jumlah.getText(), totalHarga.getText(), bayar.getText(), kodeJadwal.getText()
                );
                
                // Menjalankan query simpan transaksi & pencetakan invoice nota fisik via controller
                pc.pembayaranDanCetak(orang);
                
                // Menyegarkan isi tabel manifest untuk memperbarui kuota sisa kursi pesawat terbaru
                pc.refreshTableCetak(
                    String.valueOf(kotaAwal.getSelectedItem()),
                    String.valueOf(kotaTujuan.getSelectedItem())
                );
            }
        });

        root.add(content, BorderLayout.CENTER);
        setContentPane(root);
        pack();
        setLocationRelativeTo(null); // Memosisikan window otomatis tepat di tengah layar komputer
    }

    // ── Helpers Method (Abstraksi Pembentukan Desain) ───────────────────────────
    
    // Helper Method: Membantu perakitan container panel bermodel kartu (White Card Concept)
    private JPanel makeCard(String title, int x, int y, int w, int h) {
        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(KHAKI, 1, true),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        p.setBounds(x, y, w, h);
        
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Georgia", Font.BOLD, 13));
        lbl.setForeground(ESPRESSO);
        lbl.setBounds(10, 8, w - 20, 20);
        p.add(lbl);
        
        JSeparator sep = new JSeparator();
        sep.setForeground(KHAKI);
        sep.setBounds(10, 28, w - 20, 2);
        p.add(sep);
        return p;
    }

    // Helper Method: Mengotomatiskan pembuatan pasangan label teks bersama text field di dalam kartu
    private JTextField addFieldToCard(JPanel card, String label, int x, int y) {
        JLabel lbl = new JLabel(label);
        styleLabel(lbl);
        lbl.setBounds(x, y, 145, 16);
        card.add(lbl);
        JTextField tf = new JTextField();
        styleInputField(tf);
        tf.setBounds(x, y + 18, 145, 28);
        card.add(tf);
        return tf;
    }

    // Helper Method: Mempercantik visualisasi pilihan drop-down rute bandara
    private void styleCombo(JComboBox<String> cb, int x, int y, int w) {
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        cb.setBackground(LINEN);
        cb.setForeground(ESPRESSO);
        cb.setBorder(new LineBorder(KHAKI, 1, true));
        cb.setBounds(x, y, w, 28);
    }

    // [OOP: Encapsulation] Menyediakan gerbang data read-only tabel pesanan bagi objek Controller luar
    public JTable getPesananTabel() { return pesananTabel; }

    // Memuat daftar nama kota bandara ke drop-down rute (Data Binding dari DB)
    private void loadDataKota() {
        java.util.List<PesanModel> dataKota = pc.loadDataKota();
        for (PesanModel m : dataKota) {
            kotaAwal.addItem(m.getKodeKota() + " - " + m.getKota());
            kotaTujuan.addItem(m.getKodeKota() + " - " + m.getKota());
        }
    }
}