/*
 Navicat Premium Data Transfer

 Source Server         : localhost_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 120002
 Source Host           : localhost:5432
 Source Catalog        : Employees
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 120002
 File Encoding         : 65001

 Date: 13/07/2020 08:53:45
*/


-- ----------------------------
-- Sequence structure for agama_id_agama_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."agama_id_agama_seq";
CREATE SEQUENCE "public"."agama_id_agama_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for karyawan_id_karyawan_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."karyawan_id_karyawan_seq";
CREATE SEQUENCE "public"."karyawan_id_karyawan_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for kategori_kemampuan_id_kategori_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."kategori_kemampuan_id_kategori_seq";
CREATE SEQUENCE "public"."kategori_kemampuan_id_kategori_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for kemampuan_id_kemampuan_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."kemampuan_id_kemampuan_seq";
CREATE SEQUENCE "public"."kemampuan_id_kemampuan_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for lembur_bonus_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."lembur_bonus_seq";
CREATE SEQUENCE "public"."lembur_bonus_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for list_kemampuan_id_list_kemampuan_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."list_kemampuan_id_list_kemampuan_seq";
CREATE SEQUENCE "public"."list_kemampuan_id_list_kemampuan_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for parameter_id_param_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."parameter_id_param_seq";
CREATE SEQUENCE "public"."parameter_id_param_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for parameter_pajak_id_param_pajak_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."parameter_pajak_id_param_pajak_seq";
CREATE SEQUENCE "public"."parameter_pajak_id_param_pajak_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for pendapatan_id_pendapatan_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."pendapatan_id_pendapatan_seq";
CREATE SEQUENCE "public"."pendapatan_id_pendapatan_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for penempatan_id_penempatan_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."penempatan_id_penempatan_seq";
CREATE SEQUENCE "public"."penempatan_id_penempatan_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for posisi_id_posisi_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."posisi_id_posisi_seq";
CREATE SEQUENCE "public"."posisi_id_posisi_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 13
CACHE 1;

-- ----------------------------
-- Sequence structure for presentase_gaji_id_presentase_gaji_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."presentase_gaji_id_presentase_gaji_seq";
CREATE SEQUENCE "public"."presentase_gaji_id_presentase_gaji_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tingkatan_id_tingkatan_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tingkatan_id_tingkatan_seq";
CREATE SEQUENCE "public"."tingkatan_id_tingkatan_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tunjangan_pegawai_id_tunjangan_pegawai_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tunjangan_pegawai_id_tunjangan_pegawai_seq";
CREATE SEQUENCE "public"."tunjangan_pegawai_id_tunjangan_pegawai_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for user_id_user_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_id_user_seq";
CREATE SEQUENCE "public"."user_id_user_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 987654321
START 1
CACHE 1;

-- ----------------------------
-- Table structure for agama
-- ----------------------------
DROP TABLE IF EXISTS "public"."agama";
CREATE TABLE "public"."agama" (
  "id_agama" int4 NOT NULL,
  "nama_agama" varchar(128) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of agama
-- ----------------------------
INSERT INTO "public"."agama" VALUES (1, 'Islam');
INSERT INTO "public"."agama" VALUES (2, 'Kristen Protestan');
INSERT INTO "public"."agama" VALUES (3, 'Kristen Katolik');
INSERT INTO "public"."agama" VALUES (4, 'Hindu');
INSERT INTO "public"."agama" VALUES (5, 'Buddha');

-- ----------------------------
-- Table structure for karyawan
-- ----------------------------
DROP TABLE IF EXISTS "public"."karyawan";
CREATE TABLE "public"."karyawan" (
  "id_karyawan" int4 NOT NULL,
  "id_posisi" int4,
  "id_tingkatan" int4,
  "id_agama" int4,
  "id_penempatan" int4,
  "nama" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
  "no_ktp" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "alamat" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "tanggal_lahir" date NOT NULL,
  "masa_kerja" int4,
  "status_pernikahan" int2,
  "kontrak_awal" date NOT NULL,
  "kontrak_akhir" date NOT NULL,
  "jenis_kelamin" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "jumlah_anak" int4
)
;

-- ----------------------------
-- Records of karyawan
-- ----------------------------
INSERT INTO "public"."karyawan" VALUES (1, 1, 2, 2, 2, 'Mery', '7666736528372638', 'jl Baleendah No. 22 Kab. Bandung', '1999-06-03', 1, 0, '2018-02-11', '2020-02-11', 'P', 0);
INSERT INTO "public"."karyawan" VALUES (2, 3, 1, 3, 4, 'Jajang', '7666736590372638', 'jl Buahbatu No. 128 Bandung', '1980-02-12', 6, 1, '2013-02-11', '2020-02-11', 'L', 3);
INSERT INTO "public"."karyawan" VALUES (3, 4, 1, 2, 2, 'Rita', '7666736528373738', 'jl Paledang No. 02 Bandung', '1993-12-03', 1, 0, '2018-02-11', '2020-02-11', 'P', 0);
INSERT INTO "public"."karyawan" VALUES (4, 2, 1, 2, 2, 'Behuy', '7666736528370938', 'jl Geger Hilir No. 02 Bandung', '1997-01-03', 1, 0, '2018-02-11', '2020-02-11', 'P', 0);
INSERT INTO "public"."karyawan" VALUES (7, 4, 1, 1, 1, 'Sutan', '7690336528372638', 'jl Soreang No. 99 Kab. Bandung', '1998-06-03', 1, 0, '2018-02-11', '2020-02-11', 'L', 0);
INSERT INTO "public"."karyawan" VALUES (10, 2, 2, 1, 1, 'Sri Rahayu', '32112312020002', 'Garut', '1990-02-10', 5, 1, '2018-07-01', '2019-09-20', 'P', 1);
INSERT INTO "public"."karyawan" VALUES (11, 2, 2, 1, 3, 'Lilis Nurbayanti', '3211230807970002', 'Ciamis', '1998-03-11', 2, 0, '2017-07-01', '2019-09-20', 'P', NULL);
INSERT INTO "public"."karyawan" VALUES (14, 1, 1, 1, 1, 'Dewi Purnama Sari', '3211230110990002', 'Bogor', '1993-06-14', 4, 1, '2015-07-01', '2019-09-20', 'P', 3);
INSERT INTO "public"."karyawan" VALUES (15, 1, 1, 1, 1, 'Dodi Damara', '3211230101010001', 'Tasikmalaya', '1993-07-15', 2, 1, '2017-07-01', '2019-09-20', 'L', 1);
INSERT INTO "public"."karyawan" VALUES (16, 1, 1, 1, 1, 'Farhan Qinoy', '3211230230900003', 'Cimahi', '2000-08-16', 1, 1, '2018-07-01', '2019-09-20', 'L', 4);
INSERT INTO "public"."karyawan" VALUES (18, 4, 2, 1, 9, 'Guntur Solehudin', '3211234747380009', 'Bandung', '1996-11-18', 2, 0, '2017-07-01', '2019-09-20', 'L', NULL);
INSERT INTO "public"."karyawan" VALUES (19, 3, 1, 1, 1, 'Miranitha Fasha', '3211231234560001', 'Bandung', '1996-10-19', 2, 0, '2017-07-01', '2019-09-20', 'P', NULL);
INSERT INTO "public"."karyawan" VALUES (22, 1, 1, 1, 1, 'Deri Hermawan', '3211230908070030', 'Bandung', '1997-01-22', 1, 1, '2018-07-01', '2019-09-20', 'L', 3);
INSERT INTO "public"."karyawan" VALUES (23, 4, 2, 1, 9, 'Melissa Nurhasanah', '3211230304050007', 'Bandung', '1989-02-23', 3, 1, '2016-07-01', '2019-09-20', 'P', 2);
INSERT INTO "public"."karyawan" VALUES (25, 3, 2, 1, 3, 'Rifki Nursandi', '3211230102030004', 'Bandung', '1999-04-25', 3, 0, '2017-07-01', '2019-09-20', 'L', NULL);
INSERT INTO "public"."karyawan" VALUES (26, 1, 3, 2, 9, 'Muhammad Agung Gumilar', '3211704280496007', 'Bandung', '1989-04-28', 1, 0, '2018-07-01', '2019-09-20', 'L', NULL);
INSERT INTO "public"."karyawan" VALUES (27, 2, 2, 1, 3, 'Devi Wulan Sari', '3211230907070001', 'Bandung', '1996-06-27', 1, 1, '2018-07-01', '2019-09-20', 'P', 2);
INSERT INTO "public"."karyawan" VALUES (28, 2, 2, 1, 9, 'Yayoe Nurhasanah', '3211230102040005', 'Jakarta', '1995-07-28', 2, 1, '2017-07-01', '2019-09-20', 'P', 1);

-- ----------------------------
-- Table structure for kategori_kemampuan
-- ----------------------------
DROP TABLE IF EXISTS "public"."kategori_kemampuan";
CREATE TABLE "public"."kategori_kemampuan" (
  "id_kategori" int4 NOT NULL,
  "nama_kategori" varchar(128) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of kategori_kemampuan
-- ----------------------------
INSERT INTO "public"."kategori_kemampuan" VALUES (7, 'Ngoding');
INSERT INTO "public"."kategori_kemampuan" VALUES (8, 'Analisa');

-- ----------------------------
-- Table structure for kemampuan
-- ----------------------------
DROP TABLE IF EXISTS "public"."kemampuan";
CREATE TABLE "public"."kemampuan" (
  "id_kemampuan" int4 NOT NULL,
  "id_kategori" int4,
  "nama_kemampuan" varchar(128) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of kemampuan
-- ----------------------------
INSERT INTO "public"."kemampuan" VALUES (70, 8, 'front end');
INSERT INTO "public"."kemampuan" VALUES (72, 8, 'BE');
INSERT INTO "public"."kemampuan" VALUES (75, 8, 'penjaga');

-- ----------------------------
-- Table structure for lembur_bonus
-- ----------------------------
DROP TABLE IF EXISTS "public"."lembur_bonus";
CREATE TABLE "public"."lembur_bonus" (
  "id_lembur_bonus" int8 NOT NULL DEFAULT nextval('lembur_bonus_seq'::regclass),
  "id_karyawan" int8,
  "tanggal_lembur_bonus" date NOT NULL,
  "lama_lembur" int4 NOT NULL,
  "variable_bonus" int4 NOT NULL
)
;

-- ----------------------------
-- Records of lembur_bonus
-- ----------------------------
INSERT INTO "public"."lembur_bonus" VALUES (19, 1, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (20, 2, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (21, 3, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (22, 4, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (23, 7, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (24, 10, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (25, 11, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (26, 14, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (27, 15, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (28, 16, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (29, 18, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (30, 19, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (31, 22, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (32, 23, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (33, 25, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (34, 26, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (35, 27, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (36, 28, '2020-04-19', 0, 0);
INSERT INTO "public"."lembur_bonus" VALUES (1, 1, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (2, 2, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (3, 3, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (4, 4, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (5, 7, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (6, 10, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (7, 11, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (8, 14, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (9, 15, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (10, 16, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (11, 18, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (12, 19, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (13, 22, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (14, 23, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (15, 25, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (16, 26, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (17, 27, '2020-03-27', 4, 2);
INSERT INTO "public"."lembur_bonus" VALUES (18, 28, '2020-03-27', 4, 2);

-- ----------------------------
-- Table structure for list_kemampuan
-- ----------------------------
DROP TABLE IF EXISTS "public"."list_kemampuan";
CREATE TABLE "public"."list_kemampuan" (
  "nilai_kemampuan" int4,
  "id_list_kemampuan" int4 NOT NULL,
  "id_karyawan" int4,
  "id_kemampuan" int4
)
;

-- ----------------------------
-- Records of list_kemampuan
-- ----------------------------

-- ----------------------------
-- Table structure for parameter
-- ----------------------------
DROP TABLE IF EXISTS "public"."parameter";
CREATE TABLE "public"."parameter" (
  "id_param" int4 NOT NULL,
  "tb_parameter" date NOT NULL,
  "t_keluarga" numeric(16,4) DEFAULT NULL::numeric,
  "t_transport" numeric(16,4) DEFAULT NULL::numeric,
  "p_bpjs" numeric(16,4) NOT NULL,
  "lembur" numeric(16,4) NOT NULL,
  "bonus_pg" numeric(16,4) NOT NULL,
  "bonus_ts" numeric(16,4) NOT NULL,
  "bonus_tw" numeric(16,4) NOT NULL,
  "batasan_bonus_pg" int4,
  "batasan_bonus_ts" int4,
  "batasan_bonus_tw" int4,
  "max_bonus" numeric(16,4) DEFAULT NULL::numeric
)
;

-- ----------------------------
-- Records of parameter
-- ----------------------------
INSERT INTO "public"."parameter" VALUES (1, '2020-01-01', 0.0200, 2300000.0000, 0.0200, 0.0083, 20000.0000, 25000.0000, 35000.0000, 1, 100, 1, 500000.0000);

-- ----------------------------
-- Table structure for parameter_pajak
-- ----------------------------
DROP TABLE IF EXISTS "public"."parameter_pajak";
CREATE TABLE "public"."parameter_pajak" (
  "id_param_pajak" int4 NOT NULL,
  "tb_parameter_pajak" date NOT NULL,
  "batasan_pph_k1" numeric(16,2) NOT NULL,
  "batasan_pph_k2" numeric(16,2) NOT NULL,
  "batasan_pph_k3" numeric(16,2) NOT NULL,
  "batasan_pph_k4" numeric(16,2) NOT NULL,
  "presentase_pph_k1" numeric(16,2) NOT NULL,
  "presentase_pph_k2" numeric(16,2) NOT NULL,
  "presentase_pph_k3" numeric(16,2) NOT NULL,
  "presentase_pph_k4" numeric(16,2) NOT NULL,
  "ptkp_utama" numeric(16,2) NOT NULL,
  "ptkp_tambahan" numeric(16,2) NOT NULL,
  "max_ptkp_anak" int4,
  "biaya_jabatan" numeric(16,2) NOT NULL,
  "iuran_pensiun" numeric(16,2) NOT NULL
)
;

-- ----------------------------
-- Records of parameter_pajak
-- ----------------------------

-- ----------------------------
-- Table structure for pendapatan
-- ----------------------------
DROP TABLE IF EXISTS "public"."pendapatan";
CREATE TABLE "public"."pendapatan" (
  "id_pendapatan" int4 NOT NULL,
  "id_karyawan" int4,
  "tanggal_gaji" date NOT NULL,
  "gaji_pokok" numeric(16,4) NOT NULL,
  "tunjangan_keluarga" numeric(16,4) NOT NULL,
  "tunjangan_pegawai" numeric(16,4) NOT NULL,
  "tunjangan_transport" numeric(16,4) NOT NULL,
  "gaji_kotor" numeric(16,4) NOT NULL,
  "pph_perbulan" numeric(16,4) NOT NULL,
  "bpjs" numeric(16,4) NOT NULL,
  "gaji_bersih" numeric(16,4) NOT NULL,
  "lama_lembur" int4,
  "uang_lembur" numeric(16,4) NOT NULL,
  "variable_bonus" int4,
  "uang_bonus" numeric(16,4) NOT NULL,
  "take_home_pay" numeric(16,4) NOT NULL
)
;

-- ----------------------------
-- Records of pendapatan
-- ----------------------------
INSERT INTO "public"."pendapatan" VALUES (26, 14, '2020-04-29', 6401091.0000, 128021.8200, 100000.0000, 2300000.0000, 8929112.8200, 446455.6410, 128021.8200, 8354635.3590, 0, 0.0000, 0, 0.0000, 8354635.3590);
INSERT INTO "public"."pendapatan" VALUES (7, 11, '2020-03-27', 8261474.4000, 165229.4880, 300000.0000, 0.0000, 8726703.8880, 436335.1944, 165229.4880, 8125139.2056, 1, 72431.6423, 4, 0.0000, 8197570.8479);
INSERT INTO "public"."pendapatan" VALUES (8, 14, '2020-03-27', 6401091.0000, 128021.8200, 100000.0000, 2300000.0000, 8929112.8200, 446455.6410, 128021.8200, 8354635.3590, 1, 74111.6364, 1, 20000.0000, 8448746.9954);
INSERT INTO "public"."pendapatan" VALUES (9, 15, '2020-03-27', 5120872.8000, 102417.4560, 100000.0000, 2300000.0000, 7623290.2560, 381164.5128, 102417.4560, 7139708.2872, 2, 126546.6182, 1, 20000.0000, 7286254.9054);
INSERT INTO "public"."pendapatan" VALUES (10, 16, '2020-03-27', 4267394.0000, 85347.8800, 100000.0000, 2300000.0000, 6752741.8800, 337637.0940, 85347.8800, 6329756.9060, 3, 168143.2728, 1, 20000.0000, 6517900.1788);
INSERT INTO "public"."pendapatan" VALUES (11, 18, '2020-03-27', 4710911.4000, 94218.2280, 200000.0000, 0.0000, 5005129.6280, 250256.4814, 94218.2280, 4660654.9186, 4, 166170.3036, 1, 35000.0000, 4861825.2222);
INSERT INTO "public"."pendapatan" VALUES (12, 19, '2020-03-27', 5120872.8000, 102417.4560, 120000.0000, 2300000.0000, 7643290.2560, 382164.5128, 102417.4560, 7158708.2872, 5, 317196.5456, 1, 250.0000, 7476154.8328);
INSERT INTO "public"."pendapatan" VALUES (27, 15, '2020-04-29', 5120872.8000, 102417.4560, 100000.0000, 2300000.0000, 7623290.2560, 381164.5128, 102417.4560, 7139708.2872, 0, 0.0000, 0, 0.0000, 7139708.2872);
INSERT INTO "public"."pendapatan" VALUES (28, 16, '2020-04-29', 4267394.0000, 85347.8800, 100000.0000, 2300000.0000, 6752741.8800, 337637.0940, 85347.8800, 6329756.9060, 0, 0.0000, 0, 0.0000, 6329756.9060);
INSERT INTO "public"."pendapatan" VALUES (29, 18, '2020-04-29', 4710911.4000, 94218.2280, 200000.0000, 0.0000, 5005129.6280, 250256.4814, 94218.2280, 4660654.9186, 0, 0.0000, 0, 0.0000, 4660654.9186);
INSERT INTO "public"."pendapatan" VALUES (30, 19, '2020-04-29', 5120872.8000, 102417.4560, 120000.0000, 2300000.0000, 7643290.2560, 382164.5128, 102417.4560, 7158708.2872, 0, 0.0000, 0, 0.0000, 7158708.2872);
INSERT INTO "public"."pendapatan" VALUES (31, 22, '2020-04-29', 4267394.0000, 85347.8800, 100000.0000, 2300000.0000, 6752741.8800, 337637.0940, 85347.8800, 6329756.9060, 0, 0.0000, 0, 0.0000, 6329756.9060);
INSERT INTO "public"."pendapatan" VALUES (32, 23, '2020-04-29', 5435667.0000, 108713.3400, 200000.0000, 0.0000, 5744380.3400, 287219.0170, 108713.3400, 5348447.9830, 0, 0.0000, 0, 0.0000, 5348447.9830);
INSERT INTO "public"."pendapatan" VALUES (33, 25, '2020-04-29', 8720445.2000, 174408.9040, 200000.0000, 0.0000, 9094854.1040, 454742.7052, 174408.9040, 8465702.4948, 0, 0.0000, 0, 0.0000, 8465702.4948);
INSERT INTO "public"."pendapatan" VALUES (34, 26, '2020-04-29', 9059445.0000, 181188.9000, 300000.0000, 0.0000, 9540633.9000, 477031.6950, 181188.9000, 8882413.3050, 0, 0.0000, 0, 0.0000, 8882413.3050);
INSERT INTO "public"."pendapatan" VALUES (13, 22, '2020-03-27', 4267394.0000, 85347.8800, 100000.0000, 2300000.0000, 6752741.8800, 337637.0940, 85347.8800, 6329756.9060, 5, 280238.7880, 1, 20000.0000, 6629995.6940);
INSERT INTO "public"."pendapatan" VALUES (14, 23, '2020-03-27', 5435667.0000, 108713.3400, 200000.0000, 0.0000, 5744380.3400, 287219.0170, 108713.3400, 5348447.9830, 5, 238391.7841, 2, 70000.0000, 5656839.7671);
INSERT INTO "public"."pendapatan" VALUES (15, 25, '2020-03-27', 8720445.2000, 174408.9040, 200000.0000, 0.0000, 9094854.1040, 454742.7052, 174408.9040, 8465702.4948, 1, 75487.2891, 2, 500.0000, 8541689.7839);
INSERT INTO "public"."pendapatan" VALUES (16, 26, '2020-03-27', 9059445.0000, 181188.9000, 300000.0000, 0.0000, 9540633.9000, 477031.6950, 181188.9000, 8882413.3050, 2, 158374.5227, 2, 40000.0000, 9080787.8277);
INSERT INTO "public"."pendapatan" VALUES (17, 27, '2020-03-27', 8261474.4000, 165229.4880, 300000.0000, 0.0000, 8726703.8880, 436335.1944, 165229.4880, 8125139.2056, 3, 217294.9268, 2, 0.0000, 8342434.1324);
INSERT INTO "public"."pendapatan" VALUES (18, 28, '2020-03-27', 6522800.4000, 130456.0080, 300000.0000, 0.0000, 6953256.4080, 347662.8204, 130456.0080, 6475137.5796, 4, 230848.1127, 2, 0.0000, 6705985.6923);
INSERT INTO "public"."pendapatan" VALUES (19, 1, '2020-04-29', 7810350.8000, 156207.0160, 200000.0000, 0.0000, 8166557.8160, 408327.8908, 156207.0160, 7602022.9092, 0, 0.0000, 0, 0.0000, 7602022.9092);
INSERT INTO "public"."pendapatan" VALUES (20, 2, '2020-04-29', 6748441.5000, 134968.8300, 120000.0000, 0.0000, 7003410.3300, 350170.5165, 134968.8300, 6518270.9835, 0, 0.0000, 0, 0.0000, 6518270.9835);
INSERT INTO "public"."pendapatan" VALUES (21, 3, '2020-04-29', 4594324.0000, 91886.4800, 100000.0000, 0.0000, 4786210.4800, 239310.5240, 91886.4800, 4455013.4760, 0, 0.0000, 0, 0.0000, 4455013.4760);
INSERT INTO "public"."pendapatan" VALUES (22, 4, '2020-04-29', 5513188.8000, 110263.7760, 200000.0000, 0.0000, 5823452.5760, 291172.6288, 110263.7760, 5422016.1712, 0, 0.0000, 0, 0.0000, 5422016.1712);
INSERT INTO "public"."pendapatan" VALUES (23, 7, '2020-04-29', 4267394.0000, 85347.8800, 100000.0000, 2300000.0000, 6752741.8800, 337637.0940, 85347.8800, 6329756.9060, 0, 0.0000, 0, 0.0000, 6329756.9060);
INSERT INTO "public"."pendapatan" VALUES (24, 10, '2020-04-29', 9388266.8000, 187765.3360, 300000.0000, 2300000.0000, 12176032.1360, 608801.6068, 187765.3360, 11379465.1932, 0, 0.0000, 0, 0.0000, 11379465.1932);
INSERT INTO "public"."pendapatan" VALUES (35, 27, '2020-04-29', 8261474.4000, 165229.4880, 300000.0000, 0.0000, 8726703.8880, 436335.1944, 165229.4880, 8125139.2056, 0, 0.0000, 0, 0.0000, 8125139.2056);
INSERT INTO "public"."pendapatan" VALUES (36, 28, '2020-04-29', 6522800.4000, 130456.0080, 300000.0000, 0.0000, 6953256.4080, 347662.8204, 130456.0080, 6475137.5796, 0, 0.0000, 0, 0.0000, 6475137.5796);
INSERT INTO "public"."pendapatan" VALUES (1, 1, '2020-03-27', 7810350.8000, 156207.0160, 200000.0000, 0.0000, 8166557.8160, 408327.8908, 156207.0160, 7602022.9092, 6, 406694.5792, 2, 40000.0000, 8048717.4884);
INSERT INTO "public"."pendapatan" VALUES (2, 2, '2020-03-27', 6748441.5000, 134968.8300, 120000.0000, 0.0000, 7003410.3300, 350170.5165, 134968.8300, 6518270.9835, 1, 58128.3057, 3, 750.0000, 6577149.2892);
INSERT INTO "public"."pendapatan" VALUES (3, 3, '2020-03-27', 4594324.0000, 91886.4800, 100000.0000, 0.0000, 4786210.4800, 239310.5240, 91886.4800, 4455013.4760, 4, 158902.1879, 2, 70000.0000, 4683915.6639);
INSERT INTO "public"."pendapatan" VALUES (4, 4, '2020-03-27', 5513188.8000, 110263.7760, 200000.0000, 0.0000, 5823452.5760, 291172.6288, 110263.7760, 5422016.1712, 1, 48334.6564, 2, 0.0000, 5470350.8276);
INSERT INTO "public"."pendapatan" VALUES (5, 7, '2020-03-27', 4267394.0000, 85347.8800, 100000.0000, 2300000.0000, 6752741.8800, 337637.0940, 85347.8800, 6329756.9060, 6, 336286.5456, 1, 35000.0000, 6701043.4516);
INSERT INTO "public"."pendapatan" VALUES (25, 11, '2020-04-29', 8261474.4000, 165229.4880, 300000.0000, 0.0000, 8726703.8880, 436335.1944, 165229.4880, 8125139.2056, 0, 0.0000, 0, 0.0000, 8125139.2056);
INSERT INTO "public"."pendapatan" VALUES (6, 10, '2020-03-27', 9388266.8000, 187765.3360, 300000.0000, 2300000.0000, 12176032.1360, 608801.6068, 187765.3360, 11379465.1932, 1, 101061.0667, 3, 0.0000, 11480526.2599);
INSERT INTO "public"."pendapatan" VALUES (38, 1, '2020-05-12', 7810350.8000, 156207.0160, 200000.0000, 0.0000, 8166557.8160, 408327.8908, 156207.0160, 7602022.9092, 0, 0.0000, 0, 0.0000, 7602022.9092);
INSERT INTO "public"."pendapatan" VALUES (39, 2, '2020-05-12', 6748441.5000, 134968.8300, 120000.0000, 0.0000, 7003410.3300, 350170.5165, 134968.8300, 6518270.9835, 0, 0.0000, 0, 0.0000, 6518270.9835);
INSERT INTO "public"."pendapatan" VALUES (40, 3, '2020-05-12', 4594324.0000, 91886.4800, 100000.0000, 0.0000, 4786210.4800, 239310.5240, 91886.4800, 4455013.4760, 0, 0.0000, 0, 0.0000, 4455013.4760);
INSERT INTO "public"."pendapatan" VALUES (41, 4, '2020-05-12', 5513188.8000, 110263.7760, 200000.0000, 0.0000, 5823452.5760, 291172.6288, 110263.7760, 5422016.1712, 0, 0.0000, 0, 0.0000, 5422016.1712);
INSERT INTO "public"."pendapatan" VALUES (42, 7, '2020-05-12', 4267394.0000, 85347.8800, 100000.0000, 2300000.0000, 6752741.8800, 337637.0940, 85347.8800, 6329756.9060, 0, 0.0000, 0, 0.0000, 6329756.9060);
INSERT INTO "public"."pendapatan" VALUES (43, 10, '2020-05-12', 9388266.8000, 187765.3360, 300000.0000, 2300000.0000, 12176032.1360, 608801.6068, 187765.3360, 11379465.1932, 0, 0.0000, 0, 0.0000, 11379465.1932);
INSERT INTO "public"."pendapatan" VALUES (44, 11, '2020-05-12', 8261474.4000, 165229.4880, 300000.0000, 0.0000, 8726703.8880, 436335.1944, 165229.4880, 8125139.2056, 0, 0.0000, 0, 0.0000, 8125139.2056);
INSERT INTO "public"."pendapatan" VALUES (45, 14, '2020-05-12', 6401091.0000, 128021.8200, 100000.0000, 2300000.0000, 8929112.8200, 446455.6410, 128021.8200, 8354635.3590, 0, 0.0000, 0, 0.0000, 8354635.3590);
INSERT INTO "public"."pendapatan" VALUES (46, 15, '2020-05-12', 5120872.8000, 102417.4560, 100000.0000, 2300000.0000, 7623290.2560, 381164.5128, 102417.4560, 7139708.2872, 0, 0.0000, 0, 0.0000, 7139708.2872);
INSERT INTO "public"."pendapatan" VALUES (47, 16, '2020-05-12', 4267394.0000, 85347.8800, 100000.0000, 2300000.0000, 6752741.8800, 337637.0940, 85347.8800, 6329756.9060, 0, 0.0000, 0, 0.0000, 6329756.9060);
INSERT INTO "public"."pendapatan" VALUES (48, 18, '2020-05-12', 4710911.4000, 94218.2280, 200000.0000, 0.0000, 5005129.6280, 250256.4814, 94218.2280, 4660654.9186, 0, 0.0000, 0, 0.0000, 4660654.9186);
INSERT INTO "public"."pendapatan" VALUES (49, 19, '2020-05-12', 5120872.8000, 102417.4560, 120000.0000, 2300000.0000, 7643290.2560, 382164.5128, 102417.4560, 7158708.2872, 0, 0.0000, 0, 0.0000, 7158708.2872);
INSERT INTO "public"."pendapatan" VALUES (50, 22, '2020-05-12', 4267394.0000, 85347.8800, 100000.0000, 2300000.0000, 6752741.8800, 337637.0940, 85347.8800, 6329756.9060, 0, 0.0000, 0, 0.0000, 6329756.9060);
INSERT INTO "public"."pendapatan" VALUES (51, 23, '2020-05-12', 5435667.0000, 108713.3400, 200000.0000, 0.0000, 5744380.3400, 287219.0170, 108713.3400, 5348447.9830, 0, 0.0000, 0, 0.0000, 5348447.9830);
INSERT INTO "public"."pendapatan" VALUES (52, 25, '2020-05-12', 8720445.2000, 174408.9040, 200000.0000, 0.0000, 9094854.1040, 454742.7052, 174408.9040, 8465702.4948, 0, 0.0000, 0, 0.0000, 8465702.4948);
INSERT INTO "public"."pendapatan" VALUES (53, 26, '2020-05-12', 9059445.0000, 181188.9000, 300000.0000, 0.0000, 9540633.9000, 477031.6950, 181188.9000, 8882413.3050, 0, 0.0000, 0, 0.0000, 8882413.3050);
INSERT INTO "public"."pendapatan" VALUES (54, 27, '2020-05-12', 8261474.4000, 165229.4880, 300000.0000, 0.0000, 8726703.8880, 436335.1944, 165229.4880, 8125139.2056, 0, 0.0000, 0, 0.0000, 8125139.2056);
INSERT INTO "public"."pendapatan" VALUES (55, 28, '2020-05-12', 6522800.4000, 130456.0080, 300000.0000, 0.0000, 6953256.4080, 347662.8204, 130456.0080, 6475137.5796, 0, 0.0000, 0, 0.0000, 6475137.5796);

-- ----------------------------
-- Table structure for penempatan
-- ----------------------------
DROP TABLE IF EXISTS "public"."penempatan";
CREATE TABLE "public"."penempatan" (
  "id_penempatan" int4 NOT NULL,
  "kota_penempatan" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "umk_penempatan" numeric(16,4) NOT NULL
)
;

-- ----------------------------
-- Records of penempatan
-- ----------------------------
INSERT INTO "public"."penempatan" VALUES (1, 'DKI Jakarta', 4267394.0000);
INSERT INTO "public"."penempatan" VALUES (2, 'Kabupaten Karawang', 4594324.0000);
INSERT INTO "public"."penempatan" VALUES (3, 'Kota Bekasi', 4589708.0000);
INSERT INTO "public"."penempatan" VALUES (4, 'Kabupaten Bekasi', 4498961.0000);
INSERT INTO "public"."penempatan" VALUES (5, 'Kota Depok', 4202105.0000);
INSERT INTO "public"."penempatan" VALUES (6, 'Kota Bogor', 4169806.0000);
INSERT INTO "public"."penempatan" VALUES (7, 'Kabupaten Bogor', 4083670.0000);
INSERT INTO "public"."penempatan" VALUES (8, 'Kabupaten Purwakarta', 4039067.0000);
INSERT INTO "public"."penempatan" VALUES (9, 'Kota Bandung', 3623778.0000);
INSERT INTO "public"."penempatan" VALUES (10, 'Kabupaten Bandung Barat', 3145427.0000);
INSERT INTO "public"."penempatan" VALUES (11, 'Kabupaten Sumedang', 3139275.0000);
INSERT INTO "public"."penempatan" VALUES (12, 'Kabupaten Bandung', 3139275.0000);
INSERT INTO "public"."penempatan" VALUES (13, 'Kota Cimahi', 3139274.0000);
INSERT INTO "public"."penempatan" VALUES (14, 'Kabupaten Sukabumi', 3028531.0000);
INSERT INTO "public"."penempatan" VALUES (15, 'Kabupaten Subang', 2965468.0000);
INSERT INTO "public"."penempatan" VALUES (16, 'Kabupaten Cianjur', 2534798.0000);
INSERT INTO "public"."penempatan" VALUES (17, 'Kota Sukabumi', 2530182.0000);
INSERT INTO "public"."penempatan" VALUES (18, 'Kabupaten Indramayu', 2297931.0000);
INSERT INTO "public"."penempatan" VALUES (19, 'Kota Tasikmalaya', 2264093.0000);
INSERT INTO "public"."penempatan" VALUES (20, 'Kabupaten Tasikmalaya', 2251787.0000);
INSERT INTO "public"."penempatan" VALUES (21, 'Kota Cirebon', 2219487.0000);
INSERT INTO "public"."penempatan" VALUES (22, 'Kabupaten Cirebon', 2196416.0000);
INSERT INTO "public"."penempatan" VALUES (23, 'Kabupaten Garut', 1961085.0000);
INSERT INTO "public"."penempatan" VALUES (24, 'Kabupaten Majalengka', 1944166.0000);
INSERT INTO "public"."penempatan" VALUES (25, 'Kabupaten Kuningan', 1882642.0000);
INSERT INTO "public"."penempatan" VALUES (26, 'Kabupaten Ciamis', 1880654.0000);
INSERT INTO "public"."penempatan" VALUES (27, 'Kabupaten Pangandaran', 1860591.0000);
INSERT INTO "public"."penempatan" VALUES (28, 'Kota Banjar', 1831884.0000);

-- ----------------------------
-- Table structure for posisi
-- ----------------------------
DROP TABLE IF EXISTS "public"."posisi";
CREATE TABLE "public"."posisi" (
  "id_posisi" int4 NOT NULL,
  "nama_posisi" varchar(128) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of posisi
-- ----------------------------
INSERT INTO "public"."posisi" VALUES (1, 'Programmer');
INSERT INTO "public"."posisi" VALUES (2, 'Analist');
INSERT INTO "public"."posisi" VALUES (3, 'Tester');
INSERT INTO "public"."posisi" VALUES (4, 'Technical Writter');

-- ----------------------------
-- Table structure for presentase_gaji
-- ----------------------------
DROP TABLE IF EXISTS "public"."presentase_gaji";
CREATE TABLE "public"."presentase_gaji" (
  "id_presentase_gaji" int4 NOT NULL,
  "id_tingkatan" int4,
  "id_posisi" int4,
  "besaran_gaji" numeric(16,4) NOT NULL,
  "masa_kerja" int4
)
;

-- ----------------------------
-- Records of presentase_gaji
-- ----------------------------
INSERT INTO "public"."presentase_gaji" VALUES (1, 1, 1, 1.0000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (2, 1, 1, 1.0000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (3, 1, 1, 1.2000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (4, 1, 1, 1.2000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (5, 1, 1, 1.5000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (6, 2, 1, 1.7000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (7, 2, 1, 1.7000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (8, 2, 1, 1.7000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (9, 2, 1, 1.9000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (10, 2, 1, 1.9000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (11, 2, 1, 2.1000, 5);
INSERT INTO "public"."presentase_gaji" VALUES (12, 3, 1, 2.5000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (13, 3, 1, 2.5000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (14, 3, 1, 2.5000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (15, 3, 1, 2.7000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (16, 3, 1, 2.7000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (17, 3, 1, 2.8000, 5);
INSERT INTO "public"."presentase_gaji" VALUES (18, 1, 2, 1.2000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (19, 1, 2, 1.2000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (20, 1, 2, 1.3000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (21, 1, 2, 1.3000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (22, 1, 2, 1.6000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (23, 2, 2, 1.8000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (24, 2, 2, 1.8000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (25, 2, 2, 1.8000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (26, 2, 2, 1.9000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (27, 2, 2, 1.9000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (28, 2, 2, 2.2000, 5);
INSERT INTO "public"."presentase_gaji" VALUES (29, 3, 2, 2.7000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (30, 3, 2, 2.7000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (31, 3, 2, 2.7000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (32, 3, 2, 2.9000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (33, 3, 2, 2.9000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (34, 3, 2, 3.0000, 5);
INSERT INTO "public"."presentase_gaji" VALUES (35, 1, 3, 1.1000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (36, 1, 3, 1.1000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (37, 1, 3, 1.2000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (38, 1, 3, 1.2000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (39, 1, 3, 1.5000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (40, 2, 3, 1.8000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (41, 2, 3, 1.8000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (42, 2, 3, 1.8000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (43, 2, 3, 1.9000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (44, 2, 3, 1.9000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (45, 2, 3, 2.1000, 5);
INSERT INTO "public"."presentase_gaji" VALUES (46, 3, 3, 2.6000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (47, 3, 3, 2.6000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (48, 3, 3, 2.6000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (49, 3, 3, 2.7000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (50, 3, 3, 2.7000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (51, 3, 3, 2.8000, 5);
INSERT INTO "public"."presentase_gaji" VALUES (52, 1, 4, 1.0000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (53, 1, 4, 1.0000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (54, 1, 4, 1.2000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (55, 1, 4, 1.2000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (56, 1, 4, 1.3000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (57, 2, 4, 1.3000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (58, 2, 4, 1.3000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (59, 2, 4, 1.3000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (60, 2, 4, 1.5000, 3);
INSERT INTO "public"."presentase_gaji" VALUES (61, 2, 4, 1.5000, 4);
INSERT INTO "public"."presentase_gaji" VALUES (62, 2, 4, 1.7500, 5);
INSERT INTO "public"."presentase_gaji" VALUES (63, 3, 4, 2.0000, 0);
INSERT INTO "public"."presentase_gaji" VALUES (64, 3, 4, 2.0000, 1);
INSERT INTO "public"."presentase_gaji" VALUES (65, 3, 4, 2.0000, 2);
INSERT INTO "public"."presentase_gaji" VALUES (66, 3, 4, 2.3500, 3);
INSERT INTO "public"."presentase_gaji" VALUES (67, 3, 4, 2.3500, 4);

-- ----------------------------
-- Table structure for tingkatan
-- ----------------------------
DROP TABLE IF EXISTS "public"."tingkatan";
CREATE TABLE "public"."tingkatan" (
  "id_tingkatan" int4 NOT NULL,
  "nama_tingkatan" varchar(128) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of tingkatan
-- ----------------------------
INSERT INTO "public"."tingkatan" VALUES (1, 'Junior');
INSERT INTO "public"."tingkatan" VALUES (2, 'Middle');
INSERT INTO "public"."tingkatan" VALUES (3, 'Senior');

-- ----------------------------
-- Table structure for tunjangan_pegawai
-- ----------------------------
DROP TABLE IF EXISTS "public"."tunjangan_pegawai";
CREATE TABLE "public"."tunjangan_pegawai" (
  "id_tunjangan_pegawai" int4 NOT NULL,
  "id_posisi" int4,
  "id_tingkatan" int4,
  "besaran_tujnagan_pegawai" numeric(16,4) NOT NULL
)
;

-- ----------------------------
-- Records of tunjangan_pegawai
-- ----------------------------
INSERT INTO "public"."tunjangan_pegawai" VALUES (1, 1, 1, 100000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (2, 1, 2, 200000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (3, 1, 3, 300000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (4, 2, 1, 200000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (5, 2, 2, 300000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (6, 2, 3, 500000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (7, 3, 1, 120000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (8, 3, 2, 200000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (9, 3, 3, 325000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (10, 4, 1, 100000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (11, 4, 2, 200000.0000);
INSERT INTO "public"."tunjangan_pegawai" VALUES (12, 4, 3, 300000.0000);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
  "id_user" int4 NOT NULL,
  "username" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2
)
;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "public"."user" VALUES (26, 'dorar', 'renasdsada', 1);
INSERT INTO "public"."user" VALUES (27, 'asu', 'renasdsada', 1);
INSERT INTO "public"."user" VALUES (29, 'asusila', 'renasdsada', 1);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."agama_id_agama_seq"', 12, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."karyawan_id_karyawan_seq"', 4, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."kategori_kemampuan_id_kategori_seq"', 9, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."kemampuan_id_kemampuan_seq"', 80, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."lembur_bonus_seq"
OWNED BY "public"."lembur_bonus"."id_lembur_bonus";
SELECT setval('"public"."lembur_bonus_seq"', 39, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."list_kemampuan_id_list_kemampuan_seq"', 11, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."parameter_id_param_seq"', 4, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."parameter_pajak_id_param_pajak_seq"', 4, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."pendapatan_id_pendapatan_seq"', 57, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."penempatan_id_penempatan_seq"', 37, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."posisi_id_posisi_seq"', 20, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."presentase_gaji_id_presentase_gaji_seq"', 4, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."tingkatan_id_tingkatan_seq"', 4, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."tunjangan_pegawai_id_tunjangan_pegawai_seq"', 15, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."user_id_user_seq"', 32, true);

-- ----------------------------
-- Primary Key structure for table agama
-- ----------------------------
ALTER TABLE "public"."agama" ADD CONSTRAINT "pk_agama" PRIMARY KEY ("id_agama");

-- ----------------------------
-- Primary Key structure for table karyawan
-- ----------------------------
ALTER TABLE "public"."karyawan" ADD CONSTRAINT "pk_karyawan" PRIMARY KEY ("id_karyawan");

-- ----------------------------
-- Primary Key structure for table kategori_kemampuan
-- ----------------------------
ALTER TABLE "public"."kategori_kemampuan" ADD CONSTRAINT "pk_kategori_kemampuan" PRIMARY KEY ("id_kategori");

-- ----------------------------
-- Primary Key structure for table kemampuan
-- ----------------------------
ALTER TABLE "public"."kemampuan" ADD CONSTRAINT "pk_kemampuan" PRIMARY KEY ("id_kemampuan");

-- ----------------------------
-- Primary Key structure for table lembur_bonus
-- ----------------------------
ALTER TABLE "public"."lembur_bonus" ADD CONSTRAINT "pk_lembur_bonus" PRIMARY KEY ("id_lembur_bonus");

-- ----------------------------
-- Primary Key structure for table list_kemampuan
-- ----------------------------
ALTER TABLE "public"."list_kemampuan" ADD CONSTRAINT "pk_list_kemampuan" PRIMARY KEY ("id_list_kemampuan");

-- ----------------------------
-- Primary Key structure for table parameter
-- ----------------------------
ALTER TABLE "public"."parameter" ADD CONSTRAINT "pk_parameter" PRIMARY KEY ("id_param");

-- ----------------------------
-- Primary Key structure for table parameter_pajak
-- ----------------------------
ALTER TABLE "public"."parameter_pajak" ADD CONSTRAINT "pk_parameter_pajak" PRIMARY KEY ("id_param_pajak");

-- ----------------------------
-- Primary Key structure for table pendapatan
-- ----------------------------
ALTER TABLE "public"."pendapatan" ADD CONSTRAINT "pk_pendapatan" PRIMARY KEY ("id_pendapatan");

-- ----------------------------
-- Primary Key structure for table penempatan
-- ----------------------------
ALTER TABLE "public"."penempatan" ADD CONSTRAINT "pk_penempatan" PRIMARY KEY ("id_penempatan");

-- ----------------------------
-- Primary Key structure for table posisi
-- ----------------------------
ALTER TABLE "public"."posisi" ADD CONSTRAINT "pk_posisi" PRIMARY KEY ("id_posisi");

-- ----------------------------
-- Primary Key structure for table presentase_gaji
-- ----------------------------
ALTER TABLE "public"."presentase_gaji" ADD CONSTRAINT "pk_presentase_gaji" PRIMARY KEY ("id_presentase_gaji");

-- ----------------------------
-- Primary Key structure for table tingkatan
-- ----------------------------
ALTER TABLE "public"."tingkatan" ADD CONSTRAINT "pk_tingkatan" PRIMARY KEY ("id_tingkatan");

-- ----------------------------
-- Primary Key structure for table tunjangan_pegawai
-- ----------------------------
ALTER TABLE "public"."tunjangan_pegawai" ADD CONSTRAINT "pk_tunjangan_pegawai" PRIMARY KEY ("id_tunjangan_pegawai");

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "public"."user" ADD CONSTRAINT "pk_user" PRIMARY KEY ("id_user", "username");

-- ----------------------------
-- Foreign Keys structure for table karyawan
-- ----------------------------
ALTER TABLE "public"."karyawan" ADD CONSTRAINT "fk_karyawa_reference_penempa" FOREIGN KEY ("id_penempatan") REFERENCES "public"."penempatan" ("id_penempatan") ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE "public"."karyawan" ADD CONSTRAINT "fk_karyawa_reference_tingkat" FOREIGN KEY ("id_tingkatan") REFERENCES "public"."tingkatan" ("id_tingkatan") ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE "public"."karyawan" ADD CONSTRAINT "fk_relationship_11" FOREIGN KEY ("id_agama") REFERENCES "public"."agama" ("id_agama") ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE "public"."karyawan" ADD CONSTRAINT "fk_relationship_2" FOREIGN KEY ("id_posisi") REFERENCES "public"."posisi" ("id_posisi") ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Foreign Keys structure for table kemampuan
-- ----------------------------
ALTER TABLE "public"."kemampuan" ADD CONSTRAINT "fk_relationship_16" FOREIGN KEY ("id_kategori") REFERENCES "public"."kategori_kemampuan" ("id_kategori") ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Foreign Keys structure for table lembur_bonus
-- ----------------------------
ALTER TABLE "public"."lembur_bonus" ADD CONSTRAINT "lembur_bonus_id_karyawan_fkey" FOREIGN KEY ("id_karyawan") REFERENCES "public"."karyawan" ("id_karyawan") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table list_kemampuan
-- ----------------------------
ALTER TABLE "public"."list_kemampuan" ADD CONSTRAINT "fk_relationship_14" FOREIGN KEY ("id_karyawan") REFERENCES "public"."karyawan" ("id_karyawan") ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE "public"."list_kemampuan" ADD CONSTRAINT "fk_relationship_19" FOREIGN KEY ("id_kemampuan") REFERENCES "public"."kemampuan" ("id_kemampuan") ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Foreign Keys structure for table pendapatan
-- ----------------------------
ALTER TABLE "public"."pendapatan" ADD CONSTRAINT "fk_berpendapatan" FOREIGN KEY ("id_karyawan") REFERENCES "public"."karyawan" ("id_karyawan") ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Foreign Keys structure for table presentase_gaji
-- ----------------------------
ALTER TABLE "public"."presentase_gaji" ADD CONSTRAINT "fk_relationship_8" FOREIGN KEY ("id_posisi") REFERENCES "public"."posisi" ("id_posisi") ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Foreign Keys structure for table tunjangan_pegawai
-- ----------------------------
ALTER TABLE "public"."tunjangan_pegawai" ADD CONSTRAINT "fk_relationship_12" FOREIGN KEY ("id_posisi") REFERENCES "public"."posisi" ("id_posisi") ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE "public"."tunjangan_pegawai" ADD CONSTRAINT "fk_relationship_9" FOREIGN KEY ("id_tingkatan") REFERENCES "public"."tingkatan" ("id_tingkatan") ON DELETE RESTRICT ON UPDATE RESTRICT;
