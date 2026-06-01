-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 01, 2026 at 09:14 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pesawat`
--

-- --------------------------------------------------------

--
-- Table structure for table `jadwal`
--

CREATE TABLE `jadwal` (
  `id_jadwal` int(11) NOT NULL,
  `kodeJadwal` varchar(10) NOT NULL,
  `jamKeberangkatan` varchar(15) NOT NULL,
  `jamKedatangan` varchar(15) NOT NULL,
  `harga` varchar(20) NOT NULL,
  `kodePesawat` varchar(10) NOT NULL,
  `kodeKotaAwal` varchar(10) NOT NULL,
  `kodeKotaTujuan` varchar(10) NOT NULL,
  `kursiDiambil` int(11) DEFAULT 0,
  `kursiTersedia` int(11) DEFAULT 0,
  `statusKuota` varchar(10) GENERATED ALWAYS AS (case when `kursiDiambil` >= `kursiTersedia` then 'penuh' else 'tersedia' end) STORED
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `jadwal`
--

INSERT INTO `jadwal` (`id_jadwal`, `kodeJadwal`, `jamKeberangkatan`, `jamKedatangan`, `harga`, `kodePesawat`, `kodeKotaAwal`, `kodeKotaTujuan`, `kursiDiambil`, `kursiTersedia`) VALUES
(1, 'JD01', '07:00', '08:00', '500000', 'PW01', 'KT01', 'KT03', 3, 100),
(2, 'JD02', '10:00', '11:00', '300000', 'PW02', 'KT01', 'KT03', 0, 150),
(19, 'JD03', '07:00', '08:00', '700000', 'PW01', 'KT01', 'KT02', 2, 150);

-- --------------------------------------------------------

--
-- Table structure for table `kota`
--

CREATE TABLE `kota` (
  `id_kota` int(11) NOT NULL,
  `kodeKota` varchar(10) NOT NULL,
  `kota` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kota`
--

INSERT INTO `kota` (`id_kota`, `kodeKota`, `kota`) VALUES
(1, 'KT01', 'Jogja'),
(3, 'KT03', 'Jakarta'),
(21, 'KT02', 'Bali');

-- --------------------------------------------------------

--
-- Table structure for table `pemesanan`
--

CREATE TABLE `pemesanan` (
  `id_pesan` int(11) NOT NULL,
  `nik` varchar(25) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `noHp` varchar(20) NOT NULL,
  `kodeJadwal` varchar(10) NOT NULL,
  `jumlahKursi` int(11) NOT NULL DEFAULT 1,
  `totalHarga` varchar(20) NOT NULL,
  `waktuPesan` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pemesanan`
--

INSERT INTO `pemesanan` (`id_pesan`, `nik`, `nama`, `noHp`, `kodeJadwal`, `jumlahKursi`, `totalHarga`, `waktuPesan`) VALUES
(15, '8773495573964358', 'Liu', '0974954538', 'JD03', 2, '1400000', '2026-05-31 03:26:01');

-- --------------------------------------------------------

--
-- Table structure for table `pesawat`
--

CREATE TABLE `pesawat` (
  `id_pesawat` int(11) NOT NULL,
  `kodePesawat` varchar(10) NOT NULL,
  `pesawat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pesawat`
--

INSERT INTO `pesawat` (`id_pesawat`, `kodePesawat`, `pesawat`) VALUES
(1, 'PW01', 'Garuda Indonesia'),
(2, 'PW02', 'Batik Air'),
(20, 'PW03', 'Lion Air');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'penumpang'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `nama`, `email`, `username`, `password`, `role`) VALUES
(1, 'Administrator', 'admin@tiket.com', 'admin', 'admin123', 'admin'),
(7, 'Syahnita', 'syahnita@gmail.com', 'nita', 'nita123', 'penumpang'),
(8, 'Liu Luqyana', 'liuluqyana@gmail.com', 'liu', 'liu123', 'penumpang');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jadwal`
--
ALTER TABLE `jadwal`
  ADD PRIMARY KEY (`id_jadwal`),
  ADD UNIQUE KEY `kodeJadwal` (`kodeJadwal`),
  ADD KEY `kodePesawat` (`kodePesawat`),
  ADD KEY `kodeKotaAwal` (`kodeKotaAwal`),
  ADD KEY `kodeKotaTujuan` (`kodeKotaTujuan`);

--
-- Indexes for table `kota`
--
ALTER TABLE `kota`
  ADD PRIMARY KEY (`id_kota`),
  ADD UNIQUE KEY `kodeKota` (`kodeKota`);

--
-- Indexes for table `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD PRIMARY KEY (`id_pesan`),
  ADD KEY `kodeJadwal` (`kodeJadwal`);

--
-- Indexes for table `pesawat`
--
ALTER TABLE `pesawat`
  ADD PRIMARY KEY (`id_pesawat`),
  ADD UNIQUE KEY `kodePesawat` (`kodePesawat`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jadwal`
--
ALTER TABLE `jadwal`
  MODIFY `id_jadwal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `kota`
--
ALTER TABLE `kota`
  MODIFY `id_kota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `pemesanan`
--
ALTER TABLE `pemesanan`
  MODIFY `id_pesan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `pesawat`
--
ALTER TABLE `pesawat`
  MODIFY `id_pesawat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `jadwal`
--
ALTER TABLE `jadwal`
  ADD CONSTRAINT `jadwal_ibfk_1` FOREIGN KEY (`kodePesawat`) REFERENCES `pesawat` (`kodePesawat`),
  ADD CONSTRAINT `jadwal_ibfk_2` FOREIGN KEY (`kodeKotaAwal`) REFERENCES `kota` (`kodeKota`),
  ADD CONSTRAINT `jadwal_ibfk_3` FOREIGN KEY (`kodeKotaTujuan`) REFERENCES `kota` (`kodeKota`);

--
-- Constraints for table `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD CONSTRAINT `pemesanan_ibfk_1` FOREIGN KEY (`kodeJadwal`) REFERENCES `jadwal` (`kodeJadwal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
