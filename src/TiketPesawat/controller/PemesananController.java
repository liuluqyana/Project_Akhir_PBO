package TiketPesawat.controller;

import TiketPesawat.helper.DBPemesanan;
import TiketPesawat.model.PemesananModel;
import TiketPesawat.views.PemesananView;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * Controller untuk halaman Lihat Pemesanan (khusus Admin).
 * Admin dapat melihat semua data pemesanan yang masuk dari penumpang.
 */
public class PemesananController {

    PemesananView view;
    AdminController ac;
    private DefaultTableModel model;

    public PemesananController() {
        String[] kolom = {"ID", "NIK", "Nama", "No HP", "Kode Jadwal",
            "Kota Asal", "Kota Tujuan", "Maskapai", "Jam Berangkat",
            "Jml Kursi", "Total Harga"};
        model = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        view = new PemesananView(this);
        view.getTabelPemesanan().setModel(model);
        refreshTable();
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void tampilPage() { view.setVisible(true); }
    public void hilangPage() { view.setVisible(false); }

    public void refreshTable() {
        model.setRowCount(0);
        DBPemesanan helper = new DBPemesanan();
        List<PemesananModel> data = helper.getAllPemesanan();
        for (PemesananModel m : data) {
            model.addRow(new Object[]{
                m.getIdPesan(), m.getNik(), m.getNama(), m.getNoHp(),
                m.getKodeJadwal(), m.getKotaAwal(), m.getKotaTujuan(),
                m.getMaskapai(), m.getJamKeberangkatan(),
                m.getJumlahKursi(), m.getTotalHarga()
            });
        }
    }

    public void keAdmin() {
        ac = new AdminController();
        ac.tampilPage();
        view.setVisible(false);
    }
}
