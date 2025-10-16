
public class Product {

    // atribut
    // id, nama, harga, kategori, jumlah
    private String id;
    private String nama;
    private double harga;
    private String kategori;
    private int jumlah;

    // private LocalDateTime tanggal;
    public Product(String id, String nama, double harga, String kategori, int jumlah) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.jumlah = jumlah;
    }

    //Getters dan Setters
    // untuk mengakses dan mengubah nilai atribut
    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public double getHarga() {
        return this.harga;
    }

    public String getKategori() {
        return this.kategori;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

}
