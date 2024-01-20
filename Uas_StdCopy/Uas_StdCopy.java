import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Studi Kasus Bioskop
 *
 * Partisipan: Penonton
 * Film: Film
 * Kursi: Kursi
 * Transaksi: Transaksi
 * Riwayat Transaksi: RiwayatTransaksi
 */
public class Uas_StdCopy {

    // Rekam Data untuk Penonton
    public record Penonton(String nama, String noHp) {
    }

    // Rekam Data untuk Film
    public record Film(String judul, String genre, int durasi) {
    }

    // Rekam Data untuk Kursi
    public record Kursi(int nomorKursi, boolean tersedia) {
    }

    // Rekam Data untuk Transaksi
    public record Transaksi(int nomorAntrian, Penonton penonton, Film film, Kursi[] kursiDipilih) {
    }

    public static void main(String[] args) {

        // Daftar film dengan Array
        Film[] daftarFilm = {
                new Film("Siksa neraka", "Horror", 98),
                new Film("13 bom di Jakarta", "Action", 144),
                new Film("Aquaman and the lost kingdom", "Fantasi", 124),
                new Film("Layangan putus", "Romance", 92),
                new Film("Migration", "comedi", 142)
        };

        // Daftar penonton
        LinkedList<Penonton> penontonList = new LinkedList<>();

        // Stack untuk menyimpan indeks kursi yang telah dipesan
        Stack<Integer> indeksKursiDipesan = new Stack<>();

        // Antrian untuk menyimpan riwayat transaksi
        Queue<Transaksi> riwayatTransaksi = new LinkedList<>();

        // Inisialisasi kursi untuk bioskop
        Kursi[][] kursiBioskopPerFilm = new Kursi[daftarFilm.length][10];
        for (int i = 0; i < daftarFilm.length; i++) {
            for (int j = 0; j < 10; j++) {
                kursiBioskopPerFilm[i][j] = new Kursi(j + 1, true);        
            }
        }    
        // Memulai program
        Scanner scanner = new Scanner(System.in);
        boolean isProgramBerjalan = true;

        int nomorAntrian = 0; // Nomor antrian peserta (kode unik)

        while (isProgramBerjalan) {
            // Menampilkan menu
            System.out.println("Menu Bioskop:");
            System.out.println("1. Lihat Daftar Film");
            System.out.println("2. Pesan Tiket");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1-3): ");

            // Input pengguna
            int pilihan = scanner.nextInt();

            // Opsi menu 1
            if (pilihan == 1) {
                System.out.println("Daftar Film:");
                for (int i = 0; i < daftarFilm.length; i++) {
                    System.out.println((i + 1) + ". " + daftarFilm[i].judul() + " - " + daftarFilm[i].genre() + " - "
                            + daftarFilm[i].durasi() + " menit");
                }

                // Opsi menu 2
            } else if (pilihan == 2) {
                // Pemesanan tiket
                System.out.print("Masukkan nomor film yang ingin ditonton: ");
                int indeksFilmDipilih = scanner.nextInt() - 1;

                // Membersihkan karakter newLine
                scanner.nextLine();

                // Nomor Antrian Peserta
                System.out.println("Nomor Antrian:  " + nomorAntrian++);

                if (indeksFilmDipilih >= 0 && indeksFilmDipilih < daftarFilm.length) {
                    // Menampilkan kursi yang tersedia
                    System.out.println("Kursi yang Tersedia:");
                    Kursi [] kursiBioskop = kursiBioskopPerFilm[indeksFilmDipilih];
                    for (int i = 0; i < kursiBioskop.length; i++) {
                        if (kursiBioskop[i].tersedia()) {
                            System.out.print(kursiBioskop[i].nomorKursi() + " ");
                        }
                    }
                    System.out.println();

                    // Memilih kursi
                    System.out.print("Masukkan jumlah tiket yang ingin dipesan: ");
                    int jmlKursiDipilih = scanner.nextInt();

                    // Membersihkan karakter baris baru yang tersisa di buffer
                    scanner.nextLine();

                    Kursi[] kursiDipilih = new Kursi[jmlKursiDipilih];

                    for (int y = 0; y < jmlKursiDipilih; y++) {
                        System.out.print("Masukkan nomor kursi yang ingin dipesan: ");
                        int nomorKursiDipilih = scanner.nextInt() - 1;

                        // Membersihkan karakter baris baru yang tersisa di buffer
                        scanner.nextLine();

                        if (nomorKursiDipilih >= 0 && nomorKursiDipilih < kursiBioskop.length
                                && kursiBioskop[nomorKursiDipilih].tersedia()) {
                            kursiBioskop[nomorKursiDipilih] = new Kursi(kursiBioskop[nomorKursiDipilih].nomorKursi(),
                                    false);
                            kursiDipilih[y] = kursiBioskop[nomorKursiDipilih];
                            indeksKursiDipesan.push(nomorKursiDipilih);
                        } else {
                            System.out.println("Nomor kursi tidak valid atau kursi sudah dipesan. Silakan coba lagi.");
                            y--;
                        }
                    }
                    // Menampilkan kursi yang dipilih
                    System.out.print("Kursi yang Anda pilih: ");
                    for (int i = 0; i < kursiDipilih.length; i++) {
                        System.out.print(kursiDipilih[i].nomorKursi());
                        if (i < kursiDipilih.length - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();

                    // Informasi penonton
                    System.out.print("Masukkan nama Anda: ");
                    String namaPenonton = scanner.next();

                    // Membersihkan karakter newline yang tersisa di buffer
                    scanner.nextLine();

                    System.out.print("Masukkan No. Hp Anda: ");
                    String noHpPenonton = scanner.nextLine();
                    Penonton penonton = new Penonton(namaPenonton, noHpPenonton);
                    penontonList.add(penonton);

                    // Merekam transaksi
                    Transaksi transaksi = new Transaksi(nomorAntrian, penonton, daftarFilm[indeksFilmDipilih],
                            kursiDipilih);
                    riwayatTransaksi.offer(transaksi);

                    System.out.println("Pemesanan berhasil. Selamat menikmati film!");
                    System.out.println(" ");
                } else {
                    System.out.println("Pilihan film tidak valid.");
                }

                // Melihat riwayat transaksi
                System.out.println("Riwayat Transaksi:");
                for (Transaksi transaksi : riwayatTransaksi) {
                    System.out.print("Nomor Antrian: " + transaksi.nomorAntrian() +
                            "\nPenonton: " + transaksi.penonton().nama() +
                            "\nFilm: " + transaksi.film().judul() +
                            "\nKursi: ");
                    for (Kursi kursi : transaksi.kursiDipilih()) {
                        System.out.print(kursi.nomorKursi() + ", ");
                    }
                    System.out.println();
                }

                // Opsi menu 3
            } else if (pilihan == 3) {
                // Keluar dari program
                isProgramBerjalan = false;

            } else {
                System.out.println("Pilihan tidak valid. Silakan pilih opsi yang valid.");
            }
        }

        // Menutup scanner
        scanner.close();
    }
}
