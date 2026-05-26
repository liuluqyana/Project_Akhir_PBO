package TiketPesawat.views;

import TiketPesawat.controller.PesawatController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static TiketPesawat.views.ThemeHelper.*;

/**
 * PesawatView — Antarmuka (View) untuk mengelola data armada pesawat (CRUD).
 * Menyediakan form entri data maskapai serta tabel pemantau daftar registrasi pesawat.
 * * * [OOP: Inheritance] Diturunkan dari class JFrame untuk mengambil alih fungsi kontainer window utama desktop.
 * * [OOP: Encapsulation] Mengunci komponen-komponen input text field secara private, 
 * tetapi menyediakan gerbang interaksi terbatas melalui metode Getter dan Setter terpilih.
 */
public class PesawatView extends JFrame {

    // Dependency Injection: Menyimpan objek controller penanggung jawab operasi CRUD armada
    PesawatController pc;

    // [OOP: Encapsulation] Mengamankan elemen kontrol data masukan dari modifikasi eksternal liar
    private JTextField kodePesawat, pesawat;
    private JTable     pesawatTabel;
    private JButton    tambahBtn, ubahBtn, hapusBtn, clearBtn, kembaliBtn;

    // Constructor: Registrasi dependensi controller utama serta memicu proses kompilasi visual
    public PesawatView(PesawatController c) {
        this.pc = c;
        initComponents();
    }

    // Bagian Penting: Mengonstruksi struktur tata letak elemen GUI kelola armada pesawat
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Kelola Pesawat");
        setResizable(false);

        // Menggunakan BorderLayout sebagai pondasi pembagian wilayah atas (North) dan tengah (Center)
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(LINEN);

        // Abstraction: Memanggil fungsi instansiasi bar judul terpadu melalui static helper ThemeHelper
        JPanel header = createHeaderPanel("✈  Halaman Pesawat", 620);
        root.add(header, BorderLayout.NORTH);

        // Panel Konten Utama Menggunakan Absolute Layouting (null) untuk penataan koordinat presisi
        JPanel content = new JPanel(null);
        content.setBackground(LINEN);
        content.setPreferredSize(new Dimension(620, 380));

        // ── KARTU ENTRI FORM DATA PESAWAT ──
        JPanel formCard = new JPanel(null);
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(new LineBorder(KHAKI, 1, true));
        formCard.setBounds(16, 12, 210, 150);
        content.add(formCard);

        JLabel titleForm = new JLabel("Data Pesawat");
        titleForm.setFont(new Font("Georgia", Font.BOLD, 14));
        titleForm.setForeground(ESPRESSO);
        titleForm.setBounds(10, 8, 190, 22);
        formCard.add(titleForm);

        JSeparator sep = new JSeparator();
        sep.setForeground(KHAKI);
        sep.setBounds(10, 30, 190, 2);
        formCard.add(sep);

        JLabel kodeLabel = new JLabel("Kode Pesawat");
        styleLabel(kodeLabel);
        kodeLabel.setBounds(10, 38, 190, 16);
        formCard.add(kodeLabel);

        kodePesawat = new JTextField();
        styleInputField(kodePesawat);
        kodePesawat.setBounds(10, 56, 190, 28);
        formCard.add(kodePesawat);

        JLabel maskapaiLabel = new JLabel("Nama Maskapai");
        styleLabel(maskapaiLabel);
        maskapaiLabel.setBounds(10, 90, 190, 16);
        formCard.add(maskapaiLabel);

        pesawat = new JTextField();
        styleInputField(pesawat);
        pesawat.setBounds(10, 108, 190, 28);
        formCard.add(pesawat);

        // ── BLOK TOMBOL AKSI OPERASIONAL ──
        tambahBtn = primaryButton("Tambah");
        tambahBtn.setBounds(16, 172, 100, 32);
        content.add(tambahBtn);

        ubahBtn = primaryButton("Ubah");
        ubahBtn.setBounds(124, 172, 100, 32);
        content.add(ubahBtn);

        hapusBtn = dangerButton("Hapus");
        hapusBtn.setBounds(16, 212, 100, 32);
        content.add(hapusBtn);

        clearBtn = secondaryButton("Clear");
        clearBtn.setBounds(124, 212, 100, 32);
        content.add(clearBtn);

        kembaliBtn = secondaryButton("⬅ Kembali");
        kembaliBtn.setBounds(16, 340, 210, 32);
        content.add(kembaliBtn);

        // ── BLOK TABEL MANIFEST DATA ──
        pesawatTabel = new JTable();
        styleTable(pesawatTabel);
        JScrollPane scroll = new JScrollPane(pesawatTabel);
        styleScrollPane(scroll);
        scroll.setBounds(240, 12, 366, 356);
        content.add(scroll);

        root.add(content, BorderLayout.CENTER);

        // ── EVENT LISTENERS (Delegasi Operasi Bisnis Menuju Controller) ──

        // Operasi Tambah Data Baru
        tambahBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                pc.addData(kodePesawat.getText(), pesawat.getText());
            }
        });

        // Operasi Ubah Data Terpilih
        ubahBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = pesawatTabel.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null,
                        "Pilih baris di tabel terlebih dahulu!", 
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                pc.updateData(row, kodePesawat.getText(), pesawat.getText());
            }
        });

        // Operasi Hapus Record Data
        hapusBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = pesawatTabel.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null,
                        "Pilih baris di tabel terlebih dahulu!", 
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                pc.hapusData(row);
            }
        });

        // Operasi Reset Form Isian (Clear)
        clearBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                clearFields();
                pesawatTabel.clearSelection();
                kodePesawat.setEnabled(true); // Membuka kembali kunci input untuk data baru
            }
        });

        // Navigasi Kembali ke Dashboard Utama Admin
        kembaliBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                pc.keAdmin();
            }
        });

        // Event Klik Baris Tabel: Memetakan baris terpilih ke dalam kolom isian form (Data Binding)
        pesawatTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = pesawatTabel.getSelectedRow();
                if (row != -1) {
                    kodePesawat.setText(pesawatTabel.getValueAt(row, 0).toString());
                    pesawat.setText(pesawatTabel.getValueAt(row, 1).toString());
                    // [Business Rule] Kode Pesawat bertindak sebagai Primary Key unik, dilarang diubah saat update!
                    kodePesawat.setEnabled(false); 
                }
            }
        });

        setContentPane(root);
        pack();
        setLocationRelativeTo(null); // Menampilkan window persis di tengah monitor layar desktop
    }

    // ── [OOP: Encapsulation] AKSESOR GETTER & SETTER KONTROL DATA ──

    public JTextField getKodePesawat() { return kodePesawat; }
    public JTextField getPesawat()     { return pesawat; }
    public JTable      getPesawatTabel(){ return pesawatTabel; }
    
    // Mutator khusus pembaruan data secara parsial
    public void setKode(String v)      { kodePesawat.setText(v); }
    public void setNama(String v)      { pesawat.setText(v); }
    
    // Helper internal untuk mengosongkan teks field formulir secara simultan
    public void clearFields()          { kodePesawat.setText(""); pesawat.setText(""); }
}