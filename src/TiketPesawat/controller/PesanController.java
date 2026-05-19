package TiketPesawat.controller;

import TiketPesawat.helper.DBPesan;
import TiketPesawat.helper.DBPemesanan;
import TiketPesawat.model.*;
import TiketPesawat.views.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controller Pemesanan Tiket — hanya untuk Penumpang.
 */
public class PesanController {

    PesanView view;
    MasukController mc;
    private DefaultTableModel model;

    public PesanController() {
        String[] kolom = {"kode Jadwal", "Kode Maskapai", "Kota Awal", "Kota Tujuan",
            "Jam Keberangkatan", "Jam Kedatangan", "Harga",
            "Kursi Tersedia", "Kursi Terambil", "Status Kuota"};
        model = new DefaultTableModel(kolom, 0);

        view = new PesanView(this);
        view.getPesananTabel().setModel(model);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void tampilPage() { view.setVisible(true); }
    public void hilangPage() { view.setVisible(false); }

    /** Logout — kembali ke halaman Login */
    public void keMasuk() {
        int pilih = JOptionPane.showConfirmDialog(null,
                "Yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            mc = new MasukController();
            mc.tampilPage();
            view.setVisible(false);
            view.dispose();
        }
    }

    public List loadDataKota() {
        DBPesan helper = new DBPesan();
        return helper.getDataKota();
    }

    public void cekJadwal(String kotaAwal, String kotaAkhir) {
        String kodeKotaAwal   = kotaAwal.substring(0, 4);
        String kodeKotaTujuan = kotaAkhir.substring(0, 4);
        DBPesan helper = new DBPesan();

        if (kodeKotaAwal.equals(kodeKotaTujuan)) {
            JOptionPane.showMessageDialog(null, "Tempat asal dan tujuan tidak boleh sama!");
            return;
        }
        if (!helper.cekDataJadwal(kodeKotaAwal, kodeKotaTujuan)) {
            JOptionPane.showMessageDialog(null, "Tidak ada jadwal untuk rute ini!");
            return;
        }
        String kodeJadwal = helper.ambilKodeJadwal(kodeKotaAwal, kodeKotaTujuan);
        if (helper.isKuotaPenuh(kodeJadwal)) {
            JOptionPane.showMessageDialog(null, "Maaf, kuota untuk rute ini sudah penuh.");
            return;
        }
        refreshTable(kodeKotaAwal, kodeKotaTujuan);
    }

    public void refreshTableCetak(String kotaawal, String kotatujuan) {
        refreshTable(kotaawal.substring(0, 4), kotatujuan.substring(0, 4));
    }

    private void refreshTable(String kodeKotaAwal, String kodeKotaTujuan) {
        model.setRowCount(0);
        DBPesan helper = new DBPesan();
        List<JadwalModel> data = helper.getJadwal(kodeKotaAwal, kodeKotaTujuan);
        for (JadwalModel m : data) {
            model.addRow(new Object[]{
                m.getKodeJadwal(), m.getKodePesawat(),
                m.getKodeKotaAwal(), m.getKodeKotaTujuan(),
                m.getJamKeberangkatan(), m.getJamKedatangan(),
                m.getHarga(), m.getKursiTersedia(),
                m.getKursiDiambil(), m.getStatusKuota()
            });
        }
    }

    public String hitungHarga(String jmlKrs, String hrgTkt) {
        try {
            return String.valueOf(Integer.parseInt(jmlKrs) * Integer.parseInt(hrgTkt));
        } catch (NumberFormatException e) { return "0"; }
    }

    public void pembayaranDanCetak(Orang orang) {
        if (orang.getNama().isEmpty() || orang.getTotalHarga().isEmpty()
                || orang.getUang().isEmpty() || orang.getKursi().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!");
            return;
        }
        try {
            int totalHarga  = Integer.parseInt(orang.getTotalHarga());
            int uang        = Integer.parseInt(orang.getUang());
            int jumlahKursi = Integer.parseInt(orang.getKursi());

            DBPesan helper = new DBPesan();
            if (helper.isKuotaPenuh(orang.getKodeJadwal())) {
                JOptionPane.showMessageDialog(null, "Kuota penuh!");
                return;
            }
            if (uang < totalHarga) {
                JOptionPane.showMessageDialog(null, "Uangnya kurang pak!");
                return;
            }
            if (helper.updateKursiDiambil(orang.getKodeJadwal(), jumlahKursi)) {
                // Simpan ke tabel pemesanan agar Admin bisa melihat
                DBPemesanan dbPesan = new DBPemesanan();
                dbPesan.simpanPemesanan(orang.getNik(), orang.getNama(), orang.getNoHp(),
                        orang.getKodeJadwal(), orang.getKursi(), orang.getTotalHarga());
                printTiket(orang);
            } else {
                JOptionPane.showMessageDialog(null, "Gagal mengupdate jumlah kursi.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Tipe Uang harus berupa angka Manis!");
        }
    }

    public void printTiket(Orang orang) {
        DBPesan helper = new DBPesan();
        List<JadwalModel> data = helper.getJadwalKodeJadwal(orang.getKodeJadwal());
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data jadwal tidak ditemukan!");
            return;
        }
        // Simpan di folder 'tiket' relatif terhadap working directory
        String folderPath = System.getProperty("user.dir") + File.separator + "tiket";
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();

        String filePath = folderPath + File.separator
                + "tiket-" + orang.getNama() + "--" + orang.getNik() + ".txt";

        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("==========================================");
            writer.println("          TIKET PESAWAT                   ");
            writer.println("==========================================");
            writer.println("--- Data Penumpang ---");
            writer.println("NIK          : " + orang.getNik());
            writer.println("Nama         : " + orang.getNama());
            writer.println("No. HP       : " + orang.getNoHp());
            writer.println("Jumlah Kursi : " + orang.getKursi());
            writer.println("------------------------------------------");
            writer.println("--- Data Penerbangan ---");
            for (JadwalModel m : data) {
                writer.println("Kode Jadwal       : " + m.getKodeJadwal());
                writer.println("Maskapai          : " + m.getPesawat());
                writer.println("Kota Asal         : " + m.getKotaAwal());
                writer.println("Kota Tujuan       : " + m.getKotaTujuan());
                writer.println("Jam Keberangkatan : " + m.getJamKeberangkatan());
                writer.println("Jam Kedatangan    : " + m.getJamKedatangan());
                writer.println("Harga/Kursi       : Rp " + m.getHarga());
            }
            writer.println("------------------------------------------");
            writer.println("Total Harga  : Rp " + orang.getTotalHarga());
            writer.println("==========================================");
            writer.println("     SELAMAT TERBANG, SEMOGA SELAMAT!     ");
            writer.println("==========================================");
            JOptionPane.showMessageDialog(null, "Tiket berhasil dicetak!\nDisimpan di: " + filePath);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan tiket: " + e.getMessage());
        }
    }
}
