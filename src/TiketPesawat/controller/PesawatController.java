package TiketPesawat.controller;

import TiketPesawat.helper.*;
import TiketPesawat.model.PesawatModel;
import TiketPesawat.views.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PesawatController {

    // Deklarasi objek View, Controller pendukung, dan model tabel
    // [OOP: Encapsulation] Pembungkusan state komponen UI secara aman
    PesawatView view;
    AdminController ac;
    private DefaultTableModel model;

    // Constructor: Inisialisasi awal struktur tabel, load data, dan menampilkan GUI Pesawat
    public PesawatController() {
        String[] kolom = {"Kode", "Maskapai"};
        model = new DefaultTableModel(kolom, 0);
        refreshTable();

        view = new PesawatView(this);
        view.getPesawatTabel().setModel(model);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    // [OOP: Abstraction] Menyembunyikan kerumitan detail set visibilitas GUI
    public void tampilPage() {
        view.setVisible(true);
    }

    public void hilangPage() {
        view.setVisible(false);
    }

    // Proses validasi dan penambahan data maskapai/pesawat baru ke database
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

    // Proses modifikasi data nama/kode maskapai berdasarkan baris terpilih
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

    // Proses penghapusan record data pesawat terpilih dari database
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

    // Sinkronisasi data tabel GUI dengan record data pesawat dari database
    private void refreshTable() {
        model.setRowCount(0);
        DBPesawat helper = new DBPesawat();
        List<PesawatModel> data = helper.getAllPesawat();
        for (PesawatModel m : data) {
            // nambah baris - dalamnya array
            model.addRow(new Object[]{m.getKodePesawat(), m.getPesawat()});
        }
    }

    // Kembali navigasi ke dashboard utama Admin
    public void keAdmin() {
        ac = new AdminController();
        ac.tampilPage();
        view.setVisible(false);
    }
}