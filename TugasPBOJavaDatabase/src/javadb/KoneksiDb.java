package javadb;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class KoneksiDb{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/penjualan";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static void main(String[] args) {
        boolean active = true;
        String kode_brg, nama_brg, satuan;
        int option, stok, stok_min;
        Scanner scanner = new Scanner(System.in);
        while(active) {
            System.out.println("Pilih Opsi yang ingin anda pilih : ");
            System.out.println("1) Input");
            System.out.println("2) Edit");
            System.out.println("3) Delete");
            System.out.println("4) Show");
            System.out.print("Anda memilih : ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Ketikan kode barang : ");
                    kode_brg = scanner.nextLine();
                    System.out.print("Ketikan nama barang : ");
                    nama_brg = scanner.nextLine();
                    System.out.print("Ketikan satuan barang : ");
                    satuan = scanner.nextLine();
                    System.out.print("Ketikan stok barang : ");
                    stok = scanner.nextInt();
                    System.out.print("Ketikan stok minimal barang : ");
                    stok_min = scanner.nextInt();
                    insert(kode_brg, nama_brg, satuan, stok, stok_min);
                    break;
                case 2:
                    scanner.nextLine();
                    String fieldName, newValue, id;
                    System.out.print("Ketikan kode barang : ");
                    id = scanner.nextLine();
                    System.out.print("Ketikan field yang ingin diubah : ");
                    fieldName = scanner.nextLine();
                    System.out.print("Ketikan value yang baru : ");
                    newValue = scanner.nextLine();
                    edit(fieldName, newValue, id);
                    break;
                case 3:
                    scanner.nextLine();
                    String id2;
                    System.out.print("Ketikan kode barang : ");
                    id2 = scanner.nextLine();
                    delete(id2);
                    break;
                case 4:
                    show();
                    break;
                default:
                    System.out.println("Tidak ada opsi tersebut !");
            }
            System.out.print("Apakah anda ingin melakukan hal lain? (1(Ya)/0(Tidak)) : ");
            active = scanner.nextBoolean();
        }
    }

    public static void insert(String kode_brg, String nama_brg, String satuan, int stok, int stok_min)
    {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            String sql = "INSERT INTO barang (kd_brg,nm_brg,satuan,stok_brg,stok_min) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, kode_brg);
            ps.setString(2, nama_brg);
            ps.setString(3, satuan);
            ps.setInt(4, stok);
            ps.setInt(5, stok_min);

            ps.execute();

            stmt.close();
            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void show()
    {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT * FROM barang");
            int i = 1;
            while(rs.next())
            {
                System.out.println("Data ke-"+i);
                System.out.println("Kode Barang: " + rs.getString("kd_brg"));
                System.out.println("Nama Barang: "+rs.getString("nm_brg"));
                System.out.println("Satuan: "+rs.getString("satuan"));
                System.out.println("Stok: "+rs.getString("stok_brg"));
                System.out.println("Stok minimal: "+rs.getString("stok_min"));
                i++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void edit(String fieldName, String newValue, String kode_brg)
    {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            String sql = "Update barang set " + fieldName + " = ? where kd_brg = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            if(fieldName!="stok_brg" || fieldName!="stok_min")
            {
                ps.setString(1, newValue);
            }
            else {
                ps.setInt(1, Integer.valueOf(newValue));
            }
            ps.setString(2,kode_brg);

            ps.execute();

            stmt.close();
            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String kode_brg)
    {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();

            String sql = "delete from barang where kd_brg = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, kode_brg);

            ps.execute();

            stmt.close();
            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
