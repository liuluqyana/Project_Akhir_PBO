package TiketPesawat.controller;

import TiketPesawat.helper.DBAuth;
import TiketPesawat.views.*;
import javax.swing.JOptionPane;

/**
 * Controller Registrasi.
 * Akun baru otomatis mendapat role 'penumpang'.
 * Admin hanya bisa dibuat manual langsung di database.
 */
public class DaftarController {

    // Deklarasi objek View & Controller untuk navigasi halaman
    // [OOP: Encapsulation] Pembungkusan data/state dalam satu class
    DaftarView view;
    MasukController mc;

    // Constructor: Inisialisasi awal & menampilkan GUI registrasi
    public DaftarController() {
        view = new DaftarView(this);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    // [OOP: Abstraction] Menyembunyikan detail logika GUI di balik method simpel
    public void tampilPage() { view.setVisible(true); }
    public void hilangPage() { view.setVisible(false); }

    // Proses registrasi user baru dengan validasi form kosong
    public void daftarUser(String nama, String email, String username, String password) {
        if (nama == null || nama.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data harus diisi terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Instansiasi helper database untuk proses simpan data
        DBAuth helper = new DBAuth();
        // daftarUser() otomatis set role = 'penumpang' di query SQL
        if (helper.daftarUser(nama, email, username, password)) {
            JOptionPane.showMessageDialog(null, "Akun berhasil didaftarkan!\nSilakan login sebagai Penumpang.");
            keMasuk();
        } else {
            JOptionPane.showMessageDialog(null, "Username sudah dipake!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Pindah halaman ke menu Login (Buka MasukController, sembunyikan DaftarView)
    public void keMasuk() {
        mc = new MasukController();
        mc.tampilPage();
        view.setVisible(false);
    }
}