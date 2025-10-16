import java.sql.*;// Impor semua kelas dalam paket java.sql

public class Database {// Kelas untuk mengelola koneksi dan operasi database
    private Connection connection;// Objek koneksi database

    // Constructor
    public Database() {
        try {// Inisialisasi koneksi database
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_product",
                    "root",
                    ""
            );
        } catch (SQLException e) {// Tangani kesalahan koneksi
            throw new RuntimeException("Koneksi database gagal: " + e.getMessage());// Lempar RuntimeException jika koneksi gagal
        }
    }

    // SELECT - Mengembalikan Statement yang harus ditutup oleh pemanggil
    public Statement createStatement() throws SQLException {// Buat dan kembalikan objek Statement
        return connection.createStatement();// Pemanggil bertanggung jawab untuk menutup Statement
    }

    // INSERT
    public int insertProduct(String id, String nama, double harga, String kategori, int jumlah) {//fungsi untuk menambahkan produk baru
        String sql = "INSERT INTO product (id, nama, harga, kategori, jumlah) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);//mengisi parameter pertama dengan id
            ps.setString(2, nama);//mengisi parameter kedua dengan nama
            ps.setDouble(3, harga);//mengisi parameter ketiga dengan harga
            ps.setString(4, kategori);//mengisi parameter keempat dengan kategori
            ps.setInt(5, jumlah);//mengisi parameter kelima dengan jumlah
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saat INSERT: " + e.getMessage());
        }
    }

    // UPDATE
    public int updateProduct(String id, String nama, double harga, String kategori, int jumlah) {//fungsi untuk memperbarui data produk yang sudah ada
        String sql = "UPDATE product SET nama=?, harga=?, kategori=?, jumlah=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {//membuat PreparedStatement untuk menjalankan pernyataan SQL
            ps.setString(1, nama);
            ps.setDouble(2, harga);
            ps.setString(3, kategori);
            ps.setInt(4, jumlah);
            ps.setString(5, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saat UPDATE: " + e.getMessage());
        }
    }

    // DELETE
    public int deleteProduct(String id) {//fungsi untuk menghapus produk berdasarkan id
        String sql = "DELETE FROM product WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);//mengisi parameter pertama dengan id
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saat DELETE: " + e.getMessage());
        }
    }

    // Cek apakah ID sudah ada
    public boolean isIdExists(String id) {//fungsi untuk memeriksa apakah id produk sudah ada di database
        String sql = "SELECT COUNT(*) FROM product WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);//mengisi parameter pertama dengan id
            try (ResultSet rs = ps.executeQuery()) {//menjalankan query dan mendapatkan hasilnya
                if (rs.next()) {//memeriksa apakah ada hasil dari query
                    return rs.getInt(1) > 0;//mengembalikan true jika ada produk dengan id tersebut
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saat cek ID: " + e.getMessage());
        }
        return false;
    }

    // Tutup koneksi
    public void close() {//fungsi untuk menutup koneksi database
        try {
            if (connection != null && !connection.isClosed()) {//memeriksa apakah koneksi tidak null dan belum ditutup
                connection.close();//menutup koneksi
            }
        } catch (SQLException e) {
            System.err.println("Error saat menutup koneksi: " + e.getMessage());
        }
    }
}