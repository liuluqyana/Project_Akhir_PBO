package TiketPesawat.controller;

import TiketPesawat.helper.*;
import TiketPesawat.model.PesawatModel;
import TiketPesawat.views.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PesawatController {

    PesawatView view;

//    Dekler Controller
    AdminController ac;

    //  buat tabel
    private DefaultTableModel model;

    public PesawatController() {
        String[] kolom = {"Kode", "Maskapai"};
        model = new DefaultTableModel(kolom, 0);
        refreshTable();

        view = new PesawatView(this);
        view.getPesawatTabel().setModel(model);
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
    public void addData(String kode, String pesawat) {
        if (kode == null || kode.trim().isEmpty()
                || pesawat == null || pesawat.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data harus diisi terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            DBPesawat helper = new DBPesawat();
            if (helper.insertDataPesawat(kode, pesawat)) {
                JOptionPane.showMessageDialog(null, "Data berhasil!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, "Kode gak boleh sama!");
            }
        }
    }

    public void updateData(int row, String kode, String pesawat) {
    if (kode == null || kode.trim().isEmpty()
            || pesawat == null || pesawat.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Data harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String kodeLama = model.getValueAt(row, 0).toString();
    DBPesawat helper = new DBPesawat();
    boolean result = helper.updateDataPesawat(kodeLama, kode, pesawat);
    if (result) {
        JOptionPane.showMessageDialog(null, "Data Diubah!");
        refreshTable();
    } else {
        JOptionPane.showMessageDialog(null, "Gagal mengubah data!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    public void hapusData(int row) {
        String kode = model.getValueAt(row, 0).toString();
        if (row != -1) {
            DBPesawat helper = new DBPesawat();
            if (helper.deleteDataPesawat(kode)) {
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
        DBPesawat helper = new DBPesawat();
        List<PesawatModel> data = helper.getAllPesawat();
        for (PesawatModel m : data) {
            // nambah baris - dalamnya array
            model.addRow(new Object[]{m.getKodePesawat(), m.getPesawat()});
        }
    }

//    metode buat pindah halaman
    public void keAdmin() {
        ac = new AdminController();
        ac.tampilPage();
        view.setVisible(false);
    }

}
