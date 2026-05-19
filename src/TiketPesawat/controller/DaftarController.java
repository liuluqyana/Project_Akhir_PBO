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

    DaftarView view;
    MasukController mc;

    public DaftarController() {
        view = new DaftarView(this);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void tampilPage() { view.setVisible(true); }
    public void hilangPage() { view.setVisible(false); }

    public void daftarUser(String nama, String email, String username, String password) {
        if (nama == null || nama.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data harus diisi terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DBAuth helper = new DBAuth();
        // daftarUser() otomatis set role = 'penumpang' di query SQL
        if (helper.daftarUser(nama, email, username, password)) {
            JOptionPane.showMessageDialog(null, "Akun berhasil didaftarkan!\nSilakan login sebagai Penumpang.");
            keMasuk();
        } else {
            JOptionPane.showMessageDialog(null, "Username sudah dipake!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void keMasuk() {
        mc = new MasukController();
        mc.tampilPage();
        view.setVisible(false);
    }
}
