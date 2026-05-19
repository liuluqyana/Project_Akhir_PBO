package TiketPesawat.controller;

import TiketPesawat.helper.*;
import TiketPesawat.model.KotaModel;
import TiketPesawat.views.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;

public class KotaController {

    KotaView view;

    // Deklar Controller
    AdminController ac;

//  buat tabel
    private DefaultTableModel model;

    public KotaController() {
        String[] kolom = {"Kode", "Kota"};
        model = new DefaultTableModel(kolom, 0);
        refreshTable();

        view = new KotaView(this);
        view.getKotaTabel().setModel(model);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void tampilPage() {
        view.setVisible(true);
    }

    public void hilangPage() {
        view.setVisible(false);
    }

//  Metohde buat yang CRUD -- inti feature
    public void addData(String kode, String kota) {
        if (kode == null || kode.trim().isEmpty()
                || kota == null || kota.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data harus diisi terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            DBKota helper = new DBKota();
            if (helper.insertDataKota(kode, kota)) {
                JOptionPane.showMessageDialog(null, "Data berhasil!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, "Kode gak boleh sama!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updateData(int row, String kode, String kota) {
    if (kode == null || kode.trim().isEmpty()
            || kota == null || kota.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Data harus diisi terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String kodeLama = model.getValueAt(row, 0).toString();
    DBKota helper = new DBKota();
    if (helper.updateDataKota(kodeLama, kode, kota)) {
        JOptionPane.showMessageDialog(null, "Data Diubah!");
        refreshTable();
    } else {
        JOptionPane.showMessageDialog(null, "Gagal mengubah data!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    public void hapusData(int row) {
        String kode = model.getValueAt(row, 0).toString();
        if (row != -1) {
            DBKota helper = new DBKota();
            if (helper.deleteDataKota(kode)) {
                JOptionPane.showMessageDialog(null, "Data Dihapus!");
                refreshTable();
            }
        } else {
            System.out.println("Tidak ada data");
        }
    }

//  Methode buat refresh tablenya
    private void refreshTable() {
        model.setRowCount(0);
        DBKota helper = new DBKota();
        List<KotaModel> data = helper.getAllKota();
        for (KotaModel m : data) {
            // nambah baris - dalamnya array
            model.addRow(new Object[]{m.getKodeKota(), m.getKota()});
        }
    }

//  methode buat pindah halaman
    public void keAdmin() {
        ac = new AdminController();
        ac.tampilPage();
        view.setVisible(false);
    }
}
