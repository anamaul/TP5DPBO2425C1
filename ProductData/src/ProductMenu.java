//import library yang dibutuhkan

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductMenu extends JFrame {//mewarisi kelas JFrame untuk membuat GUI

    public static void main(String[] args) {
        // Buat object window
        ProductMenu menu = new ProductMenu();
        // Atur ukuran window
        menu.setSize(700, 600);
        // Letakkan window di tengah layar
        menu.setLocationRelativeTo(null);
        // Isi window
        menu.setContentPane(menu.mainPanel);
        // Ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);
        // Tampilkan window
        menu.setVisible(true);
        // Agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Index baris yang diklik
    private int selectedIndex = -1;
// Object database
    private Database database;
    // Komponen GUI
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private javax.swing.JSpinner jumlahSpinner;

    // Constructor
    public ProductMenu() {
        // Buat objek database
        database = new Database();

        // Isi tabel produk
        productTable.setModel(setTable());

        // Ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // Atur isi combo box
        String[] kategoriData = {"Pilih Kategori", "Elektronik", "Makanan", "Minuman", "Pakaian", "Alat Tulis"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // Atur spinner jumlah dari 1 sampai 100
        SpinnerNumberModel jumlahModel = new SpinnerNumberModel(1, 1, 100, 1);
        jumlahSpinner.setModel(jumlahModel);

        // Sembunyikan button delete
        deleteButton.setVisible(false);

        // Tambahkan window listener untuk menutup database
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                database.close();
            }
        });

        // Saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertProduct();
                } else {
                    updateProduct();
                }
            }
        });

        // Saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        // Saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Saat salah satu baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // Simpan value dari tabel (index dimulai dari 0)
                String curId = productTable.getModel().getValueAt(selectedIndex, 0).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                int curJumlah = Integer.parseInt(productTable.getModel().getValueAt(selectedIndex, 4).toString());

                // Ubah isi textfield dan combo box
                idField.setText(curId);
                idField.setEditable(false); // ID tidak bisa diubah saat update
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);
                jumlahSpinner.setValue(curJumlah);

                // Ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // Tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        Object[] cols = {"ID Produk", "Nama", "Harga", "Kategori", "Jumlah"};
        DefaultTableModel tmp = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabel tidak bisa diedit langsung
            }
        };

        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            stmt = database.createStatement();//membuat Statement untuk menjalankan pernyataan SQL
            resultSet = stmt.executeQuery("SELECT * FROM product");//menjalankan query untuk mendapatkan semua data dari tabel product

            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = resultSet.getString("id");
                row[1] = resultSet.getString("nama");
                row[2] = resultSet.getDouble("harga");
                row[3] = resultSet.getString("kategori");
                row[4] = resultSet.getInt("jumlah");
                tmp.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error membaca data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {//menutup resource yang digunakan
                if (resultSet != null) {
                    resultSet.close();//menutup ResultSet

                }
                if (stmt != null) {
                    stmt.close();//menutup Statement

                }
            } catch (SQLException e) {
                System.err.println("Error menutup resource: " + e.getMessage());
            }
        }

        return tmp;//mengembalikan model tabel yang telah diisi dengan data dari database
    }

    public void insertProduct() {//fungsi untuk menambahkan produk baru ke database
        // Validasi input
        if (!validateInput()) {
            return;
        }

        try {
            String id = idField.getText().trim();//mengambil nilai dari field ID dan menghapus spasi di awal dan akhir
            String nama = namaField.getText().trim();//mengambil nilai dari field Nama dan menghapus spasi di awal dan akhir
            double harga = Double.parseDouble(hargaField.getText().trim());//mengambil nilai dari field Harga, menghapus spasi di awal dan akhir, lalu mengubahnya menjadi tipe double
            String kategori = kategoriComboBox.getSelectedItem().toString();//mengambil nilai yang dipilih dari combo box Kategori dan mengubahnya menjadi string
            int jumlah = (int) jumlahSpinner.getValue();//mengambil nilai dari spinner Jumlah dan mengubahnya menjadi tipe int
            // Cek apakah ID sudah ada
            if (database.isIdExists(id)) {
                JOptionPane.showMessageDialog(null, "ID produk sudah ada! Gunakan ID yang berbeda.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            database.insertProduct(id, nama, harga, kategori, jumlah);//memanggil metode insertProduct pada objek database untuk menambahkan produk baru ke database
            productTable.setModel(setTable());//mengatur model tabel produk dengan data terbaru dari database
            clearForm();//memanggil metode clearForm untuk mengosongkan form input
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka yang valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateProduct() {//fungsi untuk memperbarui data produk yang sudah ada di database
        // Validasi input
        if (!validateInput()) {
            return;
        }

        try {
            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            double harga = Double.parseDouble(hargaField.getText().trim());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            int jumlah = (int) jumlahSpinner.getValue();

            database.updateProduct(id, nama, harga, kategori, jumlah);

            productTable.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka yang valid", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteProduct() {//fungsi untuk menghapus produk dari database
        int confirm = JOptionPane.showConfirmDialog(//konfirmasi penghapusan data
                null,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {//jika pengguna mengkonfirmasi penghapusan
            try {
                String id = idField.getText().trim();//mengambil nilai dari field ID dan menghapus spasi di awal dan akhir
                database.deleteProduct(id);//memanggil metode deleteProduct pada objek database untuk menghapus produk dari database berdasarkan ID

                productTable.setModel(setTable());
                clearForm();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateInput() {//fungsi untuk memvalidasi input dari user
        // Validasi ID
        if (idField.getText().trim().isEmpty()) {//memeriksa apakah field ID kosong setelah menghapus spasi di awal dan akhir
            JOptionPane.showMessageDialog(null, "ID tidak boleh kosong!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            idField.requestFocus();
            return false;
        }

        // Validasi Nama
        if (namaField.getText().trim().isEmpty()) {//memeriksa apakah field Nama kosong setelah menghapus spasi di awal dan akhir
            JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            namaField.requestFocus();//memfokuskan kursor pada field Nama
            return false;
        }

        // Validasi Harga
        if (hargaField.getText().trim().isEmpty()) {//memeriksa apakah field Harga kosong setelah menghapus spasi di awal dan akhir
            JOptionPane.showMessageDialog(null, "Harga tidak boleh kosong!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            hargaField.requestFocus();//memfokuskan kursor pada field Harga
            return false;
        }

        try {
            double harga = Double.parseDouble(hargaField.getText().trim());//mencoba mengubah nilai dari field Harga menjadi tipe double setelah menghapus spasi di awal dan akhir
            if (harga <= 0) {//memeriksa apakah harga kurang dari atau sama dengan 0
                JOptionPane.showMessageDialog(null, "Harga harus lebih besar dari 0!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                hargaField.requestFocus();//memfokuskan kursor pada field Harga
                return false;
            }
        } catch (NumberFormatException e) {//menangkap pengecualian jika nilai dari field Harga tidak dapat diubah menjadi tipe double
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka yang valid!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            hargaField.requestFocus();
            return false;
        }

        // Validasi Kategori
        if (kategoriComboBox.getSelectedIndex() == 0) {//memeriksa apakah pengguna belum memilih kategori (indeks 0 adalah "Pilih Kategori")
            JOptionPane.showMessageDialog(null, "Pilih kategori produk!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            kategoriComboBox.requestFocus();//memfokuskan kursor pada combo box Kategori
            return false;
        }

        return true;//jika semua validasi lolos, mengembalikan nilai true
    }

    public void clearForm() {//fungsi untuk mengosongkan form input
        // Kosongkan semua textfield dan combo box
        idField.setText("");
        idField.setEditable(true); // Aktifkan kembali ID field
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        jumlahSpinner.setValue(1); // Set ke 1 bukan 0

        // Ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // Sembunyikan button delete
        deleteButton.setVisible(false);

        // Ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }
}
