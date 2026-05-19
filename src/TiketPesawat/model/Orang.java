package TiketPesawat.model;

/**
 * Model data penumpang.
 *
 * Encapsulation: semua field dibuat PRIVATE.
 * Akses hanya melalui getter dan setter (bukan langsung akses field).
 */
public class Orang {

    // Encapsulation: field private agar tidak bisa diakses langsung dari luar
    private String nik;
    private String nama;
    private String noHp;
    private String kursi;
    private String totalHarga;
    private String uang;
    private String kodeJadwal;

    public Orang(String nik, String nama, String noHp,
                 String kursi, String totalHarga, String uang, String kodeJadwal) {
        this.nik        = nik;
        this.nama       = nama;
        this.noHp       = noHp;
        this.kursi      = kursi;
        this.totalHarga = totalHarga;
        this.uang       = uang;
        this.kodeJadwal = kodeJadwal;
    }

    // ---- Getter & Setter (Encapsulation) ----

    public String getNik()        { return nik; }
    public void   setNik(String v) { this.nik = v; }

    public String getNama()        { return nama; }
    public void   setNama(String v) { this.nama = v; }

    public String getNoHp()        { return noHp; }
    public void   setNoHp(String v) { this.noHp = v; }

    public String getKursi()        { return kursi; }
    public void   setKursi(String v) { this.kursi = v; }

    public String getTotalHarga()        { return totalHarga; }
    public void   setTotalHarga(String v) { this.totalHarga = v; }

    public String getUang()        { return uang; }
    public void   setUang(String v) { this.uang = v; }

    public String getKodeJadwal()        { return kodeJadwal; }
    public void   setKodeJadwal(String v) { this.kodeJadwal = v; }
}
