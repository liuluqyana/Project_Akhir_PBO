package TiketPesawat.controller;

import TiketPesawat.helper.*;
import TiketPesawat.model.JadwalModel;
import TiketPesawat.views.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JadwalController {

    // Deklarasi atribut view, controller pendukung, dan model tabel
    // [OOP: Encapsulation] Menyimpan state komponen UI secara terproteksi
    JadwalView view;
    AdminController ac;
    private DefaultTableModel model;

    // Constructor: Setup struktur tabel, binding data awal, dan load GUI
    public JadwalController() {
        String[] kolom = {"kode Jadwal", "Kode Maskapai", "Kota Awal", "Kota Tujuan", "Jam Keberangkatan", "Jam Kedatangan", "Harga", "Kursi Tersedia", "Kursi Terambil", "Status Kuota"};
        model = new DefaultTableModel(kolom, 0);
        refreshTable();

        view = new JadwalView(this);
        view.getJadwalTabel().setModel(model);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    // [OOP: Abstraction] Abstraksi fungsi visibilitas frame GUI
    public void tampilPage() {
        view.setVisible(true);
    }

    public void hilangPage() {
        view.setVisible(false);
    }

    // Proses validasi input dan penambahan data jadwal baru ke database
    public void addData(String kode, String pesawat, String kotaAwal, String kotaTujuan,
                        String jamKeb, String jamKed, String harga, String kursiTersedia, String kursiDiambil) {

        // 1. Cek semua field tidak kosong
        if (kode.isEmpty() || pesawat.isEmpty() || kotaAwal.isEmpty() || kotaTujuan.isEmpty()
                || jamKeb.isEmpty() || jamKed.isEmpty() || harga.isEmpty()
                || kursiTersedia.isEmpty() || kursiDiambil.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap mengisi semua inputan yang tersedia!!",
                    "Input Kosong", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validasi rute (kota asal dan tujuan tidak boleh sama)
        String kodeKotaAwal   = kotaAwal.substring(0, 4);
        String kodeKotaTujuan = kotaTujuan.substring(0, 4);
        if (kodeKotaAwal.equals(kodeKotaTujuan)) {
            JOptionPane.showMessageDialog(null, "Tempat asal dan tujuan tidak boleh sama!",
                    "Kesalahan Kota", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Validasi aturan logika jam (kedatangan harus setelah keberangkatan)
        String errJam = validasiJam(jamKeb, jamKed);
        if (errJam != null) {
            JOptionPane.showMessageDialog(null, errJam, "Kesalahan Jam", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 4. Cek redundansi Primary Key (kode jadwal)
        DBJadwal helper = new DBJadwal();
        if (helper.isKodeJadwalExists(kode)) {
            JOptionPane.showMessageDialog(null, "Kode jadwal sudah ada!",
                    "Kode Duplikat", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 5. Eksekusi query INSERT via helper database
        String kodePesawat = pesawat.substring(0, 4);
        boolean isDataInserted = helper.insertDataJadwal(
                kode, kodePesawat, kodeKotaAwal, kodeKotaTujuan,
                jamKeb, jamKed, harga, kursiTersedia, kursiDiambil);

        if (isDataInserted) {
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Proses modifikasi data jadwal berdasarkan baris tabel yang dipilih
    public void updateData(int row, String kode, String pesawat, String kotaAwal, String kotaTujuan,
                           String jamKeb, String jamKed, String harga, String kursiTersedia, String kursiDiambil) {

        // 1. Pastikan ada baris tabel yang diklik/dipilih
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin diubah terlebih dahulu!",
                    "Tidak Ada Pilihan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validasi rute kota
        String kodeKotaAwal   = kotaAwal.substring(0, 4);
        String kodeKotaTujuan = kotaTujuan.substring(0, 4);
        if (kodeKotaAwal.equals(kodeKotaTujuan)) {
            JOptionPane.showMessageDialog(null, "Tempat asal dan tujuan tidak boleh sama!",
                    "Kesalahan Kota", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Validasi logika jam
        String errJam = validasiJam(jamKeb, jamKed);
        if (errJam != null) {
            JOptionPane.showMessageDialog(null, errJam, "Kesalahan Jam", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 4. Eksekusi query UPDATE via helper database
        String kodePesawat = pesawat.substring(0, 4);
        DBJadwal helper = new DBJadwal();
        boolean isDataUpdated = helper.updateDataJadwal(
                kode, kodePesawat, kodeKotaAwal, kodeKotaTujuan,
                jamKeb, jamKed, harga, kursiTersedia, kursiDiambil);

        if (isDataUpdated) {
            JOptionPane.showMessageDialog(null, "Data berhasil diubah!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Proses penghapusan data jadwal dengan konfirmasi pop-up dialog
    public void hapusData(int row) {
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus terlebih dahulu!",
                    "Tidak Ada Pilihan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String kode = model.getValueAt(row, 0).toString();
        int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Yakin ingin menghapus jadwal dengan kode: " + kode + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            DBJadwal helper = new DBJadwal();
            if (helper.deleteDataJadwal(kode)) {
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Sinkronisasi data tabel GUI dengan record terbaru dari database
    private void refreshTable() {
        model.setRowCount(0);
        DBJadwal helper = new DBJadwal();
        List<JadwalModel> data = helper.getAllJadwal();
        for (JadwalModel m : data) {
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
            });
        }
    }

    // Kembali ke halaman dashboard utama admin
    public void keAdmin() {
        ac = new AdminController();
        ac.tampilPage();
        view.setVisible(false);
    }

    // Mengambil opsi daftar pesawat dari database untuk combo box view
    public List loadDataPesawat() {
        DBJadwal helper = new DBJadwal();
        return helper.getDataPesawat();
    }

    // Mengambil opsi daftar kota dari database untuk combo box view
    public List loadDataKota() {
        DBJadwal helper = new DBJadwal();
        return helper.getDataKota();
    }

    // Validasi kronologi waktu: Kedatangan wajib lebih maju dari keberangkatan
    private String validasiJam(String jamKeb, String jamKed) {
        jamKeb = jamKeb.trim();
        jamKed = jamKed.trim();

        LocalTime tKeb = parseJam(jamKeb);
        if (tKeb == null) {
            return "Format jam keberangkatan tidak valid!\nGunakan format HH:mm (contoh: 07:30)";
        }

        LocalTime tKed = parseJam(jamKed);
        if (tKed == null) {
            return "Format jam kedatangan tidak valid!\nGunakan format HH:mm (contoh: 08:00)";
        }

        if (!tKeb.isBefore(tKed)) {
            return "Jam kedatangan (" + jamKed + ") harus setelah jam keberangkatan (" + jamKeb + ")!\n"
                 + "Contoh yang benar: Keberangkatan 07:00 → Kedatangan 08:30";
        }

        return null; // Valid
    }

    // Parsing String jam ke objek LocalTime dengan penanganan error multi-pattern
    private LocalTime parseJam(String jam) {
        String[] patterns = {"HH:mm", "H:mm"};
        for (String pattern : patterns) {
            try {
                return LocalTime.parse(jam, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException e) {
                // Lanjut ke iterasi pattern berikutnya jika format tidak sesuai
            }
        }
        return null;
    }
}