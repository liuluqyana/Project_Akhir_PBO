-- Aplikasi Pemesanan Tiket Pesawat
-- DIPERBAIKI: tambah kolom 'role' di tabel users, lengkapi tabel pemesanan

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
/*!40101 SET NAMES utf8mb4 */;

-- --------------------------------------------------------
-- Tabel kota
-- --------------------------------------------------------
CREATE TABLE `kota` (
  `id_kota` int(11) NOT NULL AUTO_INCREMENT,
  `kodeKota` varchar(10) NOT NULL,
  `kota` varchar(50) NOT NULL,
  PRIMARY KEY (`id_kota`),
  UNIQUE KEY `kodeKota` (`kodeKota`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `kota` (`kodeKota`, `kota`) VALUES
('KT01', 'Jogja'),
('KT02', 'Semarang'),
('KT03', 'Jakarta'),
('KT04', 'Surabaya');

-- --------------------------------------------------------
-- Tabel pesawat
-- --------------------------------------------------------
CREATE TABLE `pesawat` (
  `id_pesawat` int(11) NOT NULL AUTO_INCREMENT,
  `kodePesawat` varchar(10) NOT NULL,
  `pesawat` varchar(50) NOT NULL,
  PRIMARY KEY (`id_pesawat`),
  UNIQUE KEY `kodePesawat` (`kodePesawat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `pesawat` (`kodePesawat`, `pesawat`) VALUES
('PW01', 'Garuda Indonesia'),
('PW02', 'Batik Air'),
('PW03', 'Lion Air');

-- --------------------------------------------------------
-- Tabel jadwal
-- --------------------------------------------------------
CREATE TABLE `jadwal` (
  `id_jadwal` int(11) NOT NULL AUTO_INCREMENT,
  `kodeJadwal` varchar(10) NOT NULL,
  `jamKeberangkatan` varchar(15) NOT NULL,
  `jamKedatangan` varchar(15) NOT NULL,
  `harga` varchar(20) NOT NULL,
  `kodePesawat` varchar(10) NOT NULL,
  `kodeKotaAwal` varchar(10) NOT NULL,
  `kodeKotaTujuan` varchar(10) NOT NULL,
  `kursiDiambil` int(11) DEFAULT 0,
  `kursiTersedia` int(11) DEFAULT 0,
  `statusKuota` varchar(10) GENERATED ALWAYS AS (
    CASE WHEN `kursiDiambil` >= `kursiTersedia` THEN 'penuh' ELSE 'tersedia' END
  ) STORED,
  PRIMARY KEY (`id_jadwal`),
  UNIQUE KEY `kodeJadwal` (`kodeJadwal`),
  FOREIGN KEY (`kodePesawat`)   REFERENCES `pesawat`(`kodePesawat`),
  FOREIGN KEY (`kodeKotaAwal`)  REFERENCES `kota`(`kodeKota`),
  FOREIGN KEY (`kodeKotaTujuan`) REFERENCES `kota`(`kodeKota`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `jadwal` (`kodeJadwal`, `jamKeberangkatan`, `jamKedatangan`, `harga`, `kodePesawat`, `kodeKotaAwal`, `kodeKotaTujuan`, `kursiDiambil`, `kursiTersedia`) VALUES
('JD01', '07:00', '08:00', '500000',  'PW01', 'KT01', 'KT03', 0, 100),
('JD02', '10:00', '11:00', '300000',  'PW02', 'KT01', 'KT03', 0, 150),
('JD03', '14:00', '15:30', '250000',  'PW03', 'KT01', 'KT02', 0, 120),
('JD04', '09:00', '10:30', '450000',  'PW01', 'KT03', 'KT01', 0, 100);

-- --------------------------------------------------------
-- Tabel pemesanan — DIPERBAIKI: tambah kolom jumlahKursi & totalHarga
-- --------------------------------------------------------
CREATE TABLE `pemesanan` (
  `id_pesan` int(11) NOT NULL AUTO_INCREMENT,
  `nik` varchar(25) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `noHp` varchar(20) NOT NULL,
  `kodeJadwal` varchar(10) NOT NULL,
  `jumlahKursi` int(11) NOT NULL DEFAULT 1,
  `totalHarga` varchar(20) NOT NULL,
  `waktuPesan` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_pesan`),
  FOREIGN KEY (`kodeJadwal`) REFERENCES `jadwal`(`kodeJadwal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Tabel users — DIPERBAIKI: tambah kolom role
-- --------------------------------------------------------
CREATE TABLE `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'penumpang',
  -- role: 'admin' -> akses dashboard admin
  -- role: 'penumpang' -> akses pesan tiket
  -- Admin HANYA dibuat manual di sini (tidak bisa daftar lewat aplikasi)
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `users` (`nama`, `email`, `username`, `password`, `role`) VALUES
('Administrator', 'admin@tiket.com',   'admin',  'admin123', 'admin'),
('Wijdan',        'wijdan@gmail.com',  'wijdan', '321',      'penumpang'),
('Akbar',         'akbar@gmail.com',   'akbar',  '123',      'penumpang');

COMMIT;
