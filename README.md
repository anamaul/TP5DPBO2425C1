<h1>🧾 Janji</h1>
Saya Muhammad Maulana Adrian dengan NIM 2408647 mengerjakan Tugas Praktikum 5
dalam mata kuliah Desain Pemrograman Berbasis Objek untuk keberkahanNya maka
saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin

<h2>🖥️ GUI Program</h2>
Tampilan antarmuka aplikasi dirancang menggunakan Java Swing dengan komponen-komponen yang sederhana namun fungsional. Berikut tampilan GUI-nya:
<img width="734" height="589" alt="image" src="https://github.com/user-attachments/assets/2576bf92-9d25-4a02-a382-0ea4b7ff3d97" />

<h2>🧩 Desain Program</h2>
Kelas ini merepresentasikan entitas produk dengan atribut utama sebagai berikut:
<img width="304" height="134" alt="image" src="https://github.com/user-attachments/assets/ddec89a9-90ef-4d39-a050-7027f976e434" />
Setiap atribut memiliki getter dan setter untuk menjaga prinsip enkapsulasi.

<h2>⚙️ Fitur Utama</h2>

* Insert
  Menambahkan data produk baru ke dalam database MySQL.
* Update
  Memperbarui data produk yang sudah ada berdasarkan ID.
* Delete
  Menghapus produk dari database secara permanen.
Setiap perubahan (insert, update, delete) akan langsung disimpan ke dalam tabel product di database, sehingga data selalu sinkron dan persisten.

Komponen yang digunakan:

<img width="488" height="127" alt="image" src="https://github.com/user-attachments/assets/4e3117fa-08d9-4ceb-8b15-57a83e68ce69" />

<h2>🔄 Perbedaan dari TP4</h2>

Pada TP5, sudah tidak menggunakan Arraylist, tetapi menggunakan Database MySQL untuk menyimpan datanya, data bersifat permanent(persistent), karena sebelumnya di TP4 menggunakan arraylist(hardcode) jadi ketika kita sudah menambahkan data, lalu kita keluar dari program, data yang sebelumnya hilang, Implementasi pattern DAO (Data Access Object)
Operasi CRUD langsung terintegrasi dengan database:
INSERT → Data langsung masuk ke tabel database
UPDATE → Data langsung diperbarui di database
DELETE → Data langsung terhapus dari database
SELECT → Data langsung diambil dari database

<h2>🧭 Penjelasan Alur Program</h2>

https://github.com/user-attachments/assets/5250e709-4a3d-4e83-a17e-bcd7914a09fb

