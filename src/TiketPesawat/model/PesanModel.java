package TiketPesawat.model;

public class PesanModel {
    // [OOP: Encapsulation] Menggunakan hak akses private (Data Hiding) agar variabel tidak bisa diubah langsung dari luar class
    private String kodeKota = "";
    private String kota = "";

    // [OOP: Encapsulation] Method Getter sebagai akses kontrol untuk membaca data dari variabel kodeKota
    public String getKodeKota() {
        return kodeKota;
    }

    // [OOP: Encapsulation] Method Setter untuk mengubah data kodeKota dengan aman. Kata kunci 'this' merujuk pada variabel instans milik class ini
    public void setKodeKota(String kodeKota) {
        this.kodeKota = kodeKota;
    }

    // [OOP: Encapsulation] Method Getter sebagai akses kontrol untuk membaca data dari variabel kota
    public String getKota() {
        return kota;
    }

    // [OOP: Encapsulation] Method Setter untuk mengubah data kota secara terkontrol
    public void setKota(String kota) {
        this.kota = kota;
    }
}