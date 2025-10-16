-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 16, 2025 at 05:02 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_product`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `harga` double NOT NULL,
  `kategori` varchar(255) NOT NULL,
  `jumlah` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `nama`, `harga`, `kategori`, `jumlah`) VALUES
('PRD001', 'Laptop ASUS', 8500000, 'Elektronik', 10),
('PRD002', 'Mouse Wireless', 150000, 'Elektronik', 25),
('PRD003', 'Keyboard Gaming', 450000, 'Elektronik', 15),
('PRD004', 'Monitor 24 inch', 2200000, 'Elektronik', 8),
('PRD005', 'Headset Gaming', 350000, 'Elektronik', 12),
('PRD006', 'Smartphone Samsung', 4500000, 'Elektronik', 9),
('PRD007', 'Charger USB-C', 85000, 'Aksesoris', 30),
('PRD008', 'Power Bank', 250000, 'Aksesoris', 18),
('PRD009', 'Webcam HD', 180000, 'Elektronik', 14),
('PRD010', 'Speaker Bluetooth', 320000, 'Elektronik', 11),
('PRD011', 'Tablet Android', 2800000, 'Elektronik', 7),
('PRD012', 'Smartwatch', 1200000, 'Aksesoris', 13),
('PRD013', 'Flash Drive 32GB', 65000, 'Penyimpanan', 35),
('PRD014', 'Hard Disk 1TB', 750000, 'Penyimpanan', 10),
('PRD015', 'Router WiFi', 420000, 'Jaringan', 16),
('PRD016', 'Kabel HDMI', 45000, 'Aksesoris', 40),
('PRD017', 'Printer Inkjet', 850000, 'Perangkat Kantor', 6),
('PRD018', 'Scanner Document', 650000, 'Perangkat Kantor', 5),
('PRD019', 'Cooling Pad', 120000, 'Aksesoris', 20),
('PRD020', 'Gaming Chair', 1800000, 'Furniture', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
