package TiketPesawat.controller;

import TiketPesawat.helper.*;
import TiketPesawat.model.JadwalModel;
import TiketPesawat.views.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JadwalController {

    JadwalView view;

    // Deklar Controller
    AdminController ac;

    //  buat tabel
    private DefaultTableModel model;

    public JadwalController() {
        String[] kolom = {"kode Jadwal", "Kode Maskapai", "Kota Awal", "Kota Tujuan", "Jam Keberangkatan", "Jam Kedatangan", "Harga", "Kursi Tersedia", "Kursi Terambil", "Status Kuota"};
        model = new DefaultTableModel(kolom, 0);
        refreshTable();

        view = new JadwalView(this);
        view.getJadwalTabel().setModel(model);
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
    public void addData(String kode, String pesawat, String kotaAwal, String kotaTujuan, String jamKeb, String jamKed, String harga, String kursiTersedia, String kursiDiambil) {
        // Pengecekan input tidak boleh kosong
        if (kode.isEmpty() || pesawat.isEmpty() || kotaAwal.isEmpty() || kotaTujuan.isEmpty() || jamKeb.isEmpty() || jamKed.isEmpty() || harga.isEmpty() || kursiTersedia.isEmpty() || kursiDiambil.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap mengisi semua inputan yang tersedia!!");
            return;
        }

        DBJadwal helper = new DBJadwal();
        String kodePesawat = pesawat.substring(0, 4);
        String kodeKotaAwal = kotaAwal.substring(0, 4);
        String kodeKotaTujuan = kotaTujuan.substring(0, 4);

        if (kodeKotaAwal.equals(kodeKotaTujuan)) {
            JOptionPane.showMessageDialog(null, "Tempat asal dan tujuan tidak boleh sama!");
            return;
        }

        // Cek apakah kode sudah ada
        if (helper.isKodeJadwalExists(kode)) {
            JOptionPane.showMessageDialog(null, "Kode jadwal sudah ada!");
            return;
        }

        boolean isDataInserted = helper.insertDataJadwal(
                kode, kodePesawat, kodeKotaAwal, kodeKotaTujuan, jamKeb, jamKed, harga, kursiTersedia, kursiDiambil
        );

        if (isDataInserted) {
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data!");
        }
    }

    public void updateData(int row, String kode, String pesawat, String kotaAwal, String kotaTujuan, String jamKeb, String jamKed, String harga, String kursiTersedia, String kursiDiambil) {
        // Ekstrak kode dari pesawat, kota awal, dan kota tujuan
        String kodePesawat = pesawat.substring(0, 4);
        String kodeKotaAwal = kotaAwal.substring(0, 4);
        String kodeKotaTujuan = kotaTujuan.substring(0, 4);

        // Pastikan row yang dipilih valid
        if (row != -1) {
            String id = model.getValueAt(row, 0).toString();
            DBJadwal helper = new DBJadwal();

            // Periksa apakah kota awal dan kota tujuan berbeda
            if (kodeKotaAwal.equals(kodeKotaTujuan)) {
                JOptionPane.showMessageDialog(null, "Tempat asal dan tujuan tidak boleh sama!");
            } else {
                // Perbarui data jadwal
                boolean isDataUpdated = helper.updateDataJadwal(
                        kode, kodePesawat, kodeKotaAwal, kodeKotaTujuan, jamKeb, jamKed, harga, kursiTersedia, kursiDiambil);

                if (isDataUpdated) {
                    JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mengubah data!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tidak ada baris yang dipilih!");
        }
    }

    public void hapusData(int row) {
        String kode = model.getValueAt(row, 0).toString();
        if (row != -1) {
            DBJadwal helper = new DBJadwal();
            if (helper.deleteDataJadwal(kode)) {
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
        DBJadwal helper = new DBJadwal();
        List<JadwalModel> data = helper.getAllJadwal();
        for (JadwalModel m : data) {
            // nambah baris - dalamnya array
            model.addRow(new Object[]{
                m.getKodeJadwal(),
                m.getKodePesawat(),
                m.getKodeKotaAwal(),
                m.getKodeKotaTujuan(),
                m.getJamKeberangkatan(),
                m.getJamKedatangan(),
                m.getHarga(),
                m.getKursiTersedia(),
                m.getKursiDiambil(),
                m.getStatusKuota()
            }
            );
        }
    }

    // methode tambahan
    public void keAdmin() {
        ac = new AdminController();
        ac.tampilPage();
        view.setVisible(false);
    }

    public List loadDataPesawat() {
        DBJadwal helper = new DBJadwal();
        List<JadwalModel> data = helper.getDataPesawat();
        return data;
    }

    public List loadDataKota() {
        DBJadwal helper = new DBJadwal();
        List<JadwalModel> data = helper.getDataKota();
        return data;
    }

}
