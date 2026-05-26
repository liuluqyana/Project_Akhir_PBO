package TiketPesawat.controller;

import TiketPesawat.helper.*;
import TiketPesawat.model.KotaModel;
import TiketPesawat.views.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;

public class KotaController {

    // Deklarasi objek View, Controller pendukung, dan model tabel
    // [OOP: Encapsulation] Pembungkusan state komponen UI secara aman
    KotaView view;
    AdminController ac;
    private DefaultTableModel model;

    // Constructor: Inisialisasi struktur tabel, memuat data awal, dan menampilkan GUI Kota
    public KotaController() {
        String[] kolom = {"Kode", "Kota"};
        model = new DefaultTableModel(kolom, 0);
        refreshTable();

        view = new KotaView(this);
        view.getKotaTabel().setModel(model);
        view.setupColumnWidths(); // atur lebar kolom setelah model dipasang
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

    // Proses validasi dan penambahan data kota baru ke database
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

    // Proses modifikasi data kota berdasarkan baris terpilih dan kode kunci lama
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

    // Proses penghapusan record data kota terpilih dari database
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

    // Sinkronisasi data tabel GUI dengan record entitas dari database
    private void refreshTable() {
        model.setRowCount(0);
        DBKota helper = new DBKota();
        List<KotaModel> data = helper.getAllKota();
        for (KotaModel m : data) {
            // nambah baris - dalamnya array
            model.addRow(new Object[]{m.getKodeKota(), m.getKota()});
        }
    }

    // Kembali navigasi ke dashboard utama Admin
    public void keAdmin() {
        ac = new AdminController();
        ac.tampilPage();
        view.setVisible(false);
    }
}