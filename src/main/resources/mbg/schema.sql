--
-- 테이블 구조 `tb_rsa_demo`
--
CREATE TABLE `tb_rsa_demo` (
   `id` varchar(50) NOT NULL,
   `name` varchar(1024) DEFAULT NULL,
   `rsapublickey` varchar(1024) NOT NULL,
   `rsaprivatekey` varchar(1024) NOT NULL,
   `aeskey` varchar(1024) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 테이블의 인덱스 `tb_rsa_demo`
--
ALTER TABLE `tb_rsa_demo`
    ADD PRIMARY KEY (`id`);


