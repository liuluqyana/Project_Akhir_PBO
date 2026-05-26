package TiketPesawat.controller;

import TiketPesawat.views.*;
import javax.swing.JOptionPane;

/**
 * Controller Dashboard Admin.
 * Hanya bisa diakses user dengan role 'admin'.
 * Menu: Kota, Maskapai, Jadwal, Lihat Pemesanan, Logout.
 */
public class AdminController {

    // Deklarasi objek View & Controller untuk navigasi halaman
    // [OOP: Encapsulation] Pembungkusan data/state dalam satu class
    AdminView view;
    KotaController      kc;
    PesawatController   pc;
    JadwalController    jc;
    PemesananController pmc;
    MasukController     mc;

    // Constructor: Inisialisasi awal & menampilkan GUI utama
    public AdminController() {
        view = new AdminView(this);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    // [OOP: Abstraction] Menyembunyikan detail logika GUI di balik method simpel
    public void tampilPage() { view.setVisible(true); }
    public void hilangPage() { view.setVisible(false); }

    // Update data nama admin ke tampilan GUI
    public void ambilNama(String nama) { view.ubahLabel(nama); }

    // Pindah halaman ke menu Kota (Buka KotaController, sembunyikan AdminView)
    public void keKota() {
        kc = new KotaController();
        kc.tampilPage();
        view.setVisible(false);
    }

    // Pindah halaman ke menu Pesawat/Maskapai
    public void kePesawat() {
        pc = new PesawatController();
        pc.tampilPage();
        view.setVisible(false);
    }

    // Pindah halaman ke menu Jadwal
    public void keJadwal() {
        jc = new JadwalController();
        jc.tampilPage();
        view.setVisible(false);
    }

    // Pindah halaman ke menu Lihat Pemesanan
    public void kePemesanan() {
        pmc = new PemesananController();
        pmc.tampilPage();
        view.setVisible(false);
    }

    // Konfirmasi keluar sistem, buka menu login, hancurkan session admin
    public void logout() {
        int pilih = JOptionPane.showConfirmDialog(null,
                "Yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            mc = new MasukController();
            mc.tampilPage();
            view.setVisible(false);
            view.dispose();
        }
    }
}