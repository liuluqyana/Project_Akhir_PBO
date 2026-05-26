package TiketPesawat.controller;

import TiketPesawat.helper.DBAuth;
import TiketPesawat.model.*;
import TiketPesawat.views.*;
import javax.swing.JOptionPane;

/**
 * Controller Login.
 * Setelah login berhasil, cek role lalu routing ke halaman sesuai.
 * POLYMORPHISM: variabel User bisa berisi Admin atau Penumpang.
 */
public class MasukController {

    // Deklarasi objek View & Controller pendukung untuk routing halaman
    // [OOP: Encapsulation] Pembungkusan state komponen UI secara aman
    MasukView view;
    DaftarController dc;
    AdminController  ac;
    PesanController  pc;

    // Constructor: Inisialisasi awal & menampilkan GUI halaman login
    public MasukController() {
        view = new MasukView(this);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    // [OOP: Abstraction] Menyembunyikan detail pengaturan visibilitas GUI
    public void tampilPage() { view.setVisible(true); }
    public void hilangPage() { view.setVisible(false); }

    // Proses autentikasi user dan penentuan hak akses (routing) halaman
    public void cekLogin(String username, String pass) {
        if (username.trim().isEmpty() || pass.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username dan password harus diisi!");
            return;
        }
        DBAuth helper = new DBAuth();
        if (helper.cekLogin(username, pass)) {
            
            // [OOP: Polymorphism] Polimorfisme runtime, objek superclass 'User' menampung subclass 'Admin'/'Penumpang'
            User user = helper.ambilDataUser(username, pass);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Gagal mengambil data user!");
                return;
            }
            
            // [OOP: Polymorphism] Dynamic method dispatch, memanggil 'getWelcomeMessage' spesifik milik subclass
            JOptionPane.showMessageDialog(null, user.getWelcomeMessage());
            view.setVisible(false);

            // [OOP: Inheritance / Polymorphism] Pengecekan tipe objek spesifik subclass dengan operator 'instanceof'
            if (user instanceof Admin) {
                ac = new AdminController();
                ac.tampilPage();
                ac.ambilNama(user.getNama());
            } else if (user instanceof Penumpang) {
                pc = new PesanController();
                pc.tampilPage();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Username atau Password salah euy!");
        }
    }

    // Pindah halaman ke menu Registrasi (Buka DaftarController, sembunyikan MasukView)
    public void keDaftar() {
        dc = new DaftarController();
        dc.tampilPage();
        view.setVisible(false);
    }
}