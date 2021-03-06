﻿USE nongsanonline;
Go

INSERT INTO [dbo].[quantrivien] VALUES (N'master',  CONVERT(VARCHAR(32), HashBytes('MD5', 'master'), 2), N'Nguyễn Thanh Tú', '',  'master', 'kichhoat');
INSERT INTO [dbo].[quantrivien] VALUES (N'admin',  CONVERT(VARCHAR(32), HashBytes('MD5', 'admin'), 2), N'Nguyễn Thanh Tú', '', 'admin', 'kichhoat');
SELECT * FROM [dbo].[quantrivien];

TRUNCATE TABLE [dbo].[nguoidung];
SELECT * FROM [dbo].[nguoidung];
INSERT INTO [dbo].[nguoidung] VALUES (N'user',  CONVERT(VARCHAR(32), HashBytes('MD5', 'user'), 2), N'Nguyễn Thanh Tú', N'Hà Nội', '0943384883', 'ntt.s2.102@gmail.com', 'default', '123456', GETDATE(), 'kichhoat');

INSERT INTO tinhthanh VALUES(N'Miền Bắc', 0)
INSERT INTO tinhthanh VALUES(N'Miền Trung', 0)
INSERT INTO tinhthanh VALUES(N'Miền Nam', 0)
INSERT INTO tinhthanh VALUES(N'Hà Nội', 1)
INSERT INTO tinhthanh VALUES(N'Đà Nẵng', 2)
INSERT INTO tinhthanh VALUES(N'TP. HCM', 3)
SELECT * FROM tinhthanh;
Truncate table tinhthanh

SELECT LOWER('Miền Bắc');

INSERT INTO sanpham VALUES(N'Sản phẩm 1', 500000, N'Mô tả sp1', GETDATE(), 0, N'Cầu Giấy, Hà Nội', 4, 'kichhoat');
INSERT INTO sanpham VALUES(N'Sản phẩm 2', 503400, N'Mô tả sp2', GETDATE(), 0, N'Cầu Giấy, Hà Nội', 4, 'kichhoat');
INSERT INTO sanpham VALUES(N'Sản phẩm 3', 513000, N'Mô tả sp3', GETDATE(), 0, N'Quận 1', 5, 'kichhoat');
INSERT INTO sanpham VALUES(N'Sản phẩm 4', 534500, N'Mô tả sp4', GETDATE(), 0, N'Cầu Giấy, Hà Nội', 4, 'kichhoat');
INSERT INTO sanpham VALUES(N'Sản phẩm 5', 1300000, N'Mô tả sp5', GETDATE(), 0, N'Quận 1', 5, 'kichhoat');
INSERT INTO sanpham VALUES(N'Sản phẩm 6', 123300, N'Mô tả sp6', GETDATE(), 0, N'Cầu Giấy, Hà Nội', 4, 'kichhoat');
TRUNCATE TABLE sanpham
SELECT * FROM sanpham

DElete from sanpham where id =17

INSERT INTO sanpham_anh VALUES ('default', 1);
INSERT INTO sanpham_anh VALUES ('default', 2);
INSERT INTO sanpham_anh VALUES ('default', 3);
INSERT INTO sanpham_anh VALUES ('default', 3);
INSERT INTO sanpham_anh VALUES ('default', 4);
INSERT INTO sanpham_anh VALUES ('default', 5);
INSERT INTO sanpham_anh VALUES ('default', 5);
INSERT INTO sanpham_anh VALUES ('default', 6);
INSERT INTO sanpham_anh VALUES ('default', 7);
TRUNCATE TABLE sanpham_anh
SELECT * FROM sanpham_anh
SELECT * FROM tinhthanh ORDER BY parent_id ASC
SELECT a.*, b.ten as 'tentinhthanh', (SELECT ten FROM tinhthanh WHERE id = b.parent_id) as 'tenvungmien' FROM sanpham a INNER JOIN tinhthanh b ON a.tinhthanh_id = b.id ORDER BY a.ten ASC;


INSERT INTO tintuc VALUES (N'Tiêu đề 1', N'Nội dung 1', N'Mô tả 1', GETDATE(), 'kichhoat');
INSERT INTO tintuc VALUES (N'Tiêu đề 2', N'Nội dung 2', N'Mô tả 2', GETDATE(), 'kichhoat');
INSERT INTO tintuc VALUES (N'Tiêu đề 3', N'Nội dung 3', N'Mô tả 3', GETDATE(), 'kichhoat');
INSERT INTO tintuc VALUES (N'Tiêu đề 4', N'Nội dung 4', N'Mô tả 4', GETDATE(), 'kichhoat');
INSERT INTO tintuc VALUES (N'Tiêu đề 5', N'Nội dung 5', N'Mô tả 5', GETDATE(), 'kichhoat');
INSERT INTO tintuc VALUES (N'Tiêu đề 6', N'Nội dung 6', N'Mô tả 6', GETDATE(), 'kichhoat');

SELECT * FROM tintuc ORDER BY ngaytao DESC
SELECT * FROM tintuc;
TRUNCATE TABLE tintuc;

INSERT INTO tintuc_anh VALUES ('default', -1);
INSERT INTO tintuc_anh VALUES ('default', -1);
INSERT INTO tintuc_anh VALUES ('default', -1);
INSERT INTO tintuc_anh VALUES ('default', -1);
INSERT INTO tintuc_anh VALUES ('14418678890881792621.jpg', -1);
SELECT * FROM tintuc_anh;

INSERT INTO muaban VALUES (N'Tiêu đề 1', 200, 'cay', N'Cầu giấy, Hà Nội', '6', '2', 'canhan', 'canban', N'Nội dung 1', GETDATE(), GETDATE(), 'dangban', 'chapnhan');
INSERT INTO muaban VALUES (N'Tiêu đề 2', 200, 'cay', N'Cầu giấy, Hà Nội', '4', '2', 'congty', 'canban', N'Nội dung 2', GETDATE(), GETDATE(), 'daban', 'chapnhan');
INSERT INTO muaban VALUES (N'Tiêu đề 3', 200, 'cay', N'Cầu giấy, Hà Nội', '6', '2', 'canhan', 'canban', N'Nội dung 3', GETDATE(), GETDATE(), 'tamdung', 'khongchapnhan');
INSERT INTO muaban VALUES (N'Tiêu đề 4', 200, 'cay', N'Cầu giấy, Hà Nội', '5', '2', 'canhan', 'canban', N'Nội dung 4', GETDATE(), GETDATE(), 'dangban', 'chapnhan');
INSERT INTO muaban VALUES (N'Tiêu đề 5', 200, 'cay', N'Cầu giấy, Hà Nội', '6', '2', 'congty', 'canban', N'Nội dung 5', GETDATE(), GETDATE(), 'dangban', 'chapnhan');
INSERT INTO muaban VALUES (N'Tiêu đề 6', 200, 'cay', N'Cầu giấy, Hà Nội', '7', '2', 'congty', 'canban', N'Nội dung 6', GETDATE(), GETDATE(), 'tamdung', 'khongchapnhan');
INSERT INTO muaban VALUES (N'Tiêu đề 7', 200, 'cay', N'Cầu giấy, Hà Nội', '6', '2', 'canhan', 'canban', N'Nội dung 7', GETDATE(), GETDATE(), 'dangban', 'chapnhan');
INSERT INTO muaban VALUES (N'Tiêu đề 8', 200, 'cay', N'Cầu giấy, Hà Nội', '8', '2', 'canhan', 'canban', N'Nội dung 8', GETDATE(), GETDATE(), 'dangban', 'chapnhan');
INSERT INTO muaban VALUES (N'Tiêu đề 9', 200, 'cay', N'Cầu giấy, Hà Nội', '8', '2', 'canhan', 'canban', N'Nội dung 9', GETDATE(), GETDATE(), 'dangban', 'chuaduyet');
TRUNCATE TABLE muaban;
SELECT * FROM muaban;

INSERT INTO muaban_anh VALUES ('aa.jpg', 1);
INSERT INTO muaban_anh VALUES ('aa.jpg', 1);
INSERT INTO muaban_anh VALUES ('aa.jpg', 1);
INSERT INTO muaban_anh VALUES ('aa.jpg', 2);
INSERT INTO muaban_anh VALUES ('aa.jpg', 2);
INSERT INTO muaban_anh VALUES ('aa.jpg', 3);
INSERT INTO muaban_anh VALUES ('aa.jpg', 4);
INSERT INTO muaban_anh VALUES ('aa.jpg', 5);
INSERT INTO muaban_anh VALUES ('aa.jpg', 5);
INSERT INTO muaban_anh VALUES ('aa.jpg', 5);

UPDATE muaban SET kiemduyet = 'chuaduyet'

SELECT a.*, b.ten as 'tentinhthanh', (SELECT ten FROM tinhthanh WHERE id = b.parent_id) as 'tenvungmien' , c.hovaten as 'taoboiten'
FROM muaban a INNER JOIN tinhthanh b 
ON a.tinhthanh_id = b.id 
INNER JOIN nguoidung c
ON a.taoboi = c.id
WHERE kiemduyet = 'chuaduyet'
ORDER BY id ASC;

INSERT INTO meovat VALUES (N'Tiêu đề 1', N'Nội dung 1', 'sanxuat', 'kichhoat');
INSERT INTO meovat VALUES (N'Tiêu đề 2', N'Nội dung 2', 'chebien', 'kichhoat');
INSERT INTO meovat VALUES (N'Tiêu đề 3', N'Nội dung 3', 'sanxuat', 'kichhoat');
INSERT INTO meovat VALUES (N'Tiêu đề 4', N'Nội dung 4', 'chebien', 'kichhoat');
INSERT INTO meovat VALUES (N'Tiêu đề 5', N'Nội dung 5', 'sanxuat', 'kichhoat');
INSERT INTO meovat VALUES (N'Tiêu đề 6', N'Nội dung 6', 'sanxuat', 'kichhoat');

SELECT * FROM meovat;

INSERT INTO meovat_anh VALUES ('aa.jpg', 1);
INSERT INTO meovat_anh VALUES ('aa.jpg', 1);
INSERT INTO meovat_anh VALUES ('aa.jpg', 1);
INSERT INTO meovat_anh VALUES ('aa.jpg', 2);
INSERT INTO meovat_anh VALUES ('aa.jpg', 2);
INSERT INTO meovat_anh VALUES ('aa.jpg', 3);
INSERT INTO meovat_anh VALUES ('aa.jpg', 3);
INSERT INTO meovat_anh VALUES ('aa.jpg', 4);
INSERT INTO meovat_anh VALUES ('aa.jpg', 5);
INSERT INTO meovat_anh VALUES ('aa.jpg', 6);
INSERT INTO meovat_anh VALUES ('aa.jpg', 6);
INSERT INTO meovat_anh VALUES ('aa.jpg', 6);

SELECT * FROM meovat_anh;