package TiketPesawat.model;

public class PesanPodel {

    // [OOP: Encapsulation] Seluruh properti dideklarasikan sebagai private (Data Hiding)
    // tujuannya agar variabel tidak dapat dimanipulasi secara ilegal dari luar class ini
    private String nik = "";
    private String namam = "";
    private String noHp = "";
    private String jumlahKursi = "";
    private String kotaAwal = "";
    private String kotaTujuan = "";
    private String kursiDiambil = "";
    private String kursiTersedia = "";
    private String statusKuota = "";

    // ---- Getter & Setter (Encapsulation) ----

    // [OOP: Encapsulation] Method Getter sebagai akses kontrol untuk membaca data variabel kursiDiambil
    public String getKursiDiambil() {
        return kursiDiambil;
    }

    // [OOP: Encapsulation] Method Setter untuk mengubah data kursiDiambil. Kata kunci 'this' merujuk pada atribut milik class sendiri
    public void setKursiDiambil(String kursiDiambil) {
        this.kursiDiambil = kursiDiambil;
    }

    public String getKursiTersedia() {
        return kursiTersedia;
    }

    public void setKursiTersedia(String kursiTersedia) {
        this.kursiTersedia = kursiTersedia;
    }

    public String getStatusKuota() {
        return statusKuota;
    }

    public void setStatusKuota(String statusKuota) {
        this.statusKuota = statusKuota;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamam() {
        return namam;
    }

    public void setNamam(String namam) {
        this.namam = namam;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getJumlahKursi() {
        return jumlahKursi;
    }

    public void setJumlahKursi(String jumlahKursi) {
        this.jumlahKursi = jumlahKursi;
    }

    public String getKotaAwal() {
        return kotaAwal;
    }

    public void setKotaAwal(String kotaAwal) {
        this.kotaAwal = kotaAwal;
    }

    public String getKotaTujuan() {
        return kotaTujuan;
    }

    public void setKotaTujuan(String kotaTujuan) {
        this.kotaTujuan = kotaTujuan;
    }

}