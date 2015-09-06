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