package TiketPesawat.model;

/**
 * Model untuk data Pemesanan tiket.
 * Digunakan Admin untuk melihat semua pemesanan yang masuk.
 */
public class PemesananModel {
    private String idPesan;
    private String nik;
    private String nama;
    private String noHp;
    private String kodeJadwal;
    private String kotaAwal;
    private String kotaTujuan;
    private String maskapai;
    private String jamKeberangkatan;
    private String jumlahKursi;
    private String totalHarga;

    public String getIdPesan()          { return idPesan; }
    public void   setIdPesan(String v)  { this.idPesan = v; }

    public String getNik()              { return nik; }
    public void   setNik(String v)      { this.nik = v; }

    public String getNama()             { return nama; }
    public void   setNama(String v)     { this.nama = v; }

    public String getNoHp()             { return noHp; }
    public void   setNoHp(String v)     { this.noHp = v; }

    public String getKodeJadwal()               { return kodeJadwal; }
    public void   setKodeJadwal(String v)       { this.kodeJadwal = v; }

    public String getKotaAwal()                 { return kotaAwal; }
    public void   setKotaAwal(String v)         { this.kotaAwal = v; }

    public String getKotaTujuan()               { return kotaTujuan; }
    public void   setKotaTujuan(String v)       { this.kotaTujuan = v; }

    public String getMaskapai()                 { return maskapai; }
    public void   setMaskapai(String v)         { this.maskapai = v; }

    public String getJamKeberangkatan()         { return jamKeberangkatan; }
    public void   setJamKeberangkatan(String v) { this.jamKeberangkatan = v; }

    public String getJumlahKursi()              { return jumlahKursi; }
    public void   setJumlahKursi(String v)      { this.jumlahKursi = v; }

    public String getTotalHarga()               { return totalHarga; }
    public void   setTotalHarga(String v)       { this.totalHarga = v; }
}
