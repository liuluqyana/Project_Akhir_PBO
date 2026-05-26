package TiketPesawat.model;

public class KotaModel {
    // [OOP: Encapsulation] Variabel dideklarasikan sebagai private untuk mencegah modifikasi langsung dari luar class (Data Hiding)
    private String kodeKota = "";
    private String kota = "";

    // [OOP: Encapsulation] Method Getter sebagai akses kontrol untuk membaca data variabel kodeKota
    public String getKodeKota() {
        return kodeKota;
    }

    // [OOP: Encapsulation] Method Setter untuk mengubah data kodeKota. Penggunaan keyword 'this' merujuk pada atribut milik class ini sendiri
    public void setKodeKota(String kodeKota) {
        this.kodeKota = kodeKota;
    }

    // [OOP: Encapsulation] Method Getter sebagai akses kontrol untuk membaca data variabel kota
    public String getKota() {
        return kota;
    }

    // [OOP: Encapsulation] Method Setter untuk mengubah data kota
    public void setKota(String kota) {
        this.kota = kota;
    }
    
}