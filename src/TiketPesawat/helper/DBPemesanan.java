package TiketPesawat.helper;

import TiketPesawat.model.PemesananModel;
import java.sql.*;
import java.util.*;

/**
 * DBPemesanan — menyimpan dan mengambil data pemesanan tiket.
 * INHERITANCE: extends DBHelper.
 * POLYMORPHISM: override getNamaTable().
 * Digunakan Admin untuk melihat semua pemesanan yang masuk.
 */
public class DBPemesanan extends DBHelper {

    @Override
    public String getNamaTable() { return "pemesanan"; }

    /** Simpan data pemesanan setelah transaksi berhasil */
    public boolean simpanPemesanan(String nik, String nama, String noHp,
            String kodeJadwal, String jumlahKursi, String totalHarga) {
        String query = "INSERT INTO " + getNamaTable()
                + " (nik, nama, noHp, kodeJadwal, jumlahKursi, totalHarga) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nik);
            pstmt.setString(2, nama);
            pstmt.setString(3, noHp);
            pstmt.setString(4, kodeJadwal);
            pstmt.setString(5, jumlahKursi);
            pstmt.setString(6, totalHarga);
            boolean ok = pstmt.executeUpdate() > 0;
            pstmt.close();
            return ok;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    /** Ambil semua data pemesanan (untuk halaman Admin) */
    public List<PemesananModel> getAllPemesanan() {
        List<PemesananModel> list = new ArrayList<>();
        String query = "SELECT p.*, j.kodeKotaAwal, j.kodeKotaTujuan, "
                + "k1.kota AS kotaAwal, k2.kota AS kotaTujuan, "
                + "ps.pesawat, j.jamKeberangkatan "
                + "FROM " + getNamaTable() + " p "
                + "LEFT JOIN jadwal j ON j.kodeJadwal = p.kodeJadwal "
                + "LEFT JOIN kota k1 ON k1.kodeKota = j.kodeKotaAwal "
                + "LEFT JOIN kota k2 ON k2.kodeKota = j.kodeKotaTujuan "
                + "LEFT JOIN pesawat ps ON ps.kodePesawat = j.kodePesawat "
                + "ORDER BY p.id_pesan DESC";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                PemesananModel data = new PemesananModel();
                data.setIdPesan(String.valueOf(rs.getInt("id_pesan")));
                data.setNik(rs.getString("nik"));
                data.setNama(rs.getString("nama"));
                data.setNoHp(rs.getString("noHp"));
                data.setKodeJadwal(rs.getString("kodeJadwal"));
                data.setKotaAwal(rs.getString("kotaAwal"));
                data.setKotaTujuan(rs.getString("kotaTujuan"));
                data.setMaskapai(rs.getString("pesawat"));
                data.setJamKeberangkatan(rs.getString("jamKeberangkatan"));
                data.setJumlahKursi(rs.getString("jumlahKursi"));
                data.setTotalHarga(rs.getString("totalHarga"));
                list.add(data);
            }
            tutupResource();
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
