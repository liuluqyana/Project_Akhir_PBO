package TiketPesawat.model;

/**
 * Abstract class User — parent dari Admin dan Penumpang.
 * ABSTRACTION: mendefinisikan kontrak yang wajib diimplementasi child class.
 * INHERITANCE: Admin dan Penumpang mewarisi class ini.
 */
public abstract class User {
    private int    idUser;
    private String nama;
    private String email;
    private String username;
    private String password;
    private String role;

    public User(int idUser, String nama, String email, String username, String password, String role) {
        this.idUser   = idUser;
        this.nama     = nama;
        this.email    = email;
        this.username = username;
        this.password = password;
        this.role     = role;
    }

    // Method abstract — wajib diimplementasi child class (Abstraction)
    public abstract String getHakAkses();
    public abstract String getWelcomeMessage();

    // Polymorphism: bisa di-override child class
    public String getInfo() {
        return "User [" + role + "]: " + nama;
    }

    // Getter & Setter (Encapsulation)
    public int    getIdUser()   { return idUser; }
    public String getNama()     { return nama; }
    public String getEmail()    { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }
}
