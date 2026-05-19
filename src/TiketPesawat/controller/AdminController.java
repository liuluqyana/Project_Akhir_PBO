package TiketPesawat.controller;

import TiketPesawat.views.*;
import javax.swing.JOptionPane;

/**
 * Controller Dashboard Admin.
 * Hanya bisa diakses user dengan role 'admin'.
 * Menu: Kota, Maskapai, Jadwal, Lihat Pemesanan, Logout.
 */
public class AdminController {

    AdminView view;
    KotaController      kc;
    PesawatController   pc;
    JadwalController    jc;
    PemesananController pmc;
    MasukController     mc;

    public AdminController() {
        view = new AdminView(this);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void tampilPage() { view.setVisible(true); }
    public void hilangPage() { view.setVisible(false); }

    public void ambilNama(String nama) { view.ubahLabel(nama); }

    public void keKota() {
        kc = new KotaController();
        kc.tampilPage();
        view.setVisible(false);
    }

    public void kePesawat() {
        pc = new PesawatController();
        pc.tampilPage();
        view.setVisible(false);
    }

    public void keJadwal() {
        jc = new JadwalController();
        jc.tampilPage();
        view.setVisible(false);
    }

    public void kePemesanan() {
        pmc = new PemesananController();
        pmc.tampilPage();
        view.setVisible(false);
    }

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
