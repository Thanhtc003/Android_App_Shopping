package com.example.prm392_shopping_project.database;

public class DatabaseConfig {
    public static final String ACCOUNT_TABLE= "Accounts";
    public static final String PRODUCT_TABLE= "Products";
    public static final String CATEGORY_TABLE= "Categories";
    public static final String CUSTOMER_TABLE= "Customers";
    public static final String ORDER_TABLE= "Orders";
    public static final String ORDER_DETAIL_TABLE= "OrderDetails";

    public static final String Accounts = "CREATE TABLE " + ACCOUNT_TABLE + "(\n" +
            "   [email] nvarchar(50) NOT NULL COLLATE NOCASE,\n" +
            "   [password] nchar(10) NOT NULL COLLATE NOCASE,\n" +
            "   [date_created] nchar(10) NOT NULL COLLATE NOCASE,\n" +
            "   [is_admin] bit NOT NULL,\n" +
            "   PRIMARY KEY ([email])\n" +
            ")";
    public static final String Categories = "CREATE TABLE " + CATEGORY_TABLE + "(\n" +
            "   [id] integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "   [name] nvarchar(50) NOT NULL COLLATE NOCASE,\n" +
            "   [imageUrl] BLOB\n" +
            ")";
    public static final String Customers = "CREATE TABLE " + CUSTOMER_TABLE + "(\n" +
            "   [id] integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "   [email] nvarchar(50) NOT NULL COLLATE NOCASE,\n" +
            "   [full_name] nvarchar(20) NOT NULL COLLATE NOCASE,\n" +
            "   [address] int NOT NULL,\n" +
            "   [phone] nvarchar(13) NOT NULL,\n" +
            "   CONSTRAINT [FK_Customers_Accounts] FOREIGN KEY ([email])\n" +
            "      REFERENCES [Accounts]([email]) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ")";
    public static final String Orders = "CREATE TABLE " + ORDER_TABLE + "(\n" +
            "   [id] integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "   [order_date] date NOT NULL COLLATE NOCASE,\n" +
            "   [total_bill] float,\n" +
            "   [customer_id] int NOT NULL,\n" +
            "   CONSTRAINT [FK_Order_Customers] FOREIGN KEY ([customer_id])\n" +
            "      REFERENCES [Customers]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ")";
    public static final String Products = "CREATE TABLE " +  PRODUCT_TABLE + "(\n" +
            "   [id] integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "   [name] nvarchar(50) NOT NULL COLLATE NOCASE,\n" +
            "   [description] text(2147483647) COLLATE NOCASE,\n" +
            "   [price] float NOT NULL,\n" +
            "   [quantity] int NOT NULL,\n" +
            "   [unit] nvarchar(50) NOT NULL,\n" +
            "   [category_id] int NOT NULL,\n" +
            "   [discount] int,\n" +
            "   [imageUrl] BLOB NOT NULL COLLATE NOCASE,\n" +
            "   [bigImageUrl] BLOB COLLATE NOCASE,\n" +
            "   CONSTRAINT [FK_Products_Categories] FOREIGN KEY ([category_id])\n" +
            "      REFERENCES [Categories]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ")";
    public static final String OrderDetails = "CREATE TABLE " + ORDER_DETAIL_TABLE + "(\n" +
            "   [order_id] int NOT NULL,\n" +
            "   [product_id] int NOT NULL,\n" +
            "   [price] float NOT NULL,\n" +
            "   [quantity] int NOT NULL,\n" +
            "   [discount] int NOT NULL,\n" +
            "   PRIMARY KEY ([order_id], [product_id]),\n" +
            "   CONSTRAINT [FK_Order_Details_Order] FOREIGN KEY ([order_id])\n" +
            "      REFERENCES [Order]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "   CONSTRAINT [FK_Order_Details_Products] FOREIGN KEY ([product_id])\n" +
            "      REFERENCES [Products]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ")";

}
