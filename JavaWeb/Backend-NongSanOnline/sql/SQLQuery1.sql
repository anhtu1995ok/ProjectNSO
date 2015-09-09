USE nongsanonline;
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