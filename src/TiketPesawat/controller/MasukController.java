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

    MasukView view;
    DaftarController dc;
    AdminController  ac;
    PesanController  pc;

    public MasukController() {
        view = new MasukView(this);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void tampilPage() { view.setVisible(true); }
    public void hilangPage() { view.setVisible(false); }

    public void cekLogin(String username, String pass) {
        if (username.trim().isEmpty() || pass.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username dan password harus diisi!");
            return;
        }
        DBAuth helper = new DBAuth();
        if (helper.cekLogin(username, pass)) {
            // Polymorphism: User bisa berisi Admin atau Penumpang
            User user = helper.ambilDataUser(username, pass);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Gagal mengambil data user!");
                return;
            }
            // getWelcomeMessage() berbeda untuk Admin vs Penumpang (Polymorphism)
            JOptionPane.showMessageDialog(null, user.getWelcomeMessage());
            view.setVisible(false);

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

    public void keDaftar() {
        dc = new DaftarController();
        dc.tampilPage();
        view.setVisible(false);
    }
}
