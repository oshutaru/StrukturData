import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Studi Kasus Bioskop 
 * Partisipan: Penonton 
 * Film: Film 
 * Kursi: Kursi 
 * Transaksi: Transaksi 
 * Riwayat Transaksi: RiwayatTransaksi
 */
public class Uas_StdCopy {
    // Rekam Data untuk Penonton
    public record Penonton(String nama, String noHp) {}

    // Rekam Data untuk Film
    public record Film(String judul, String genre, int durasi, int Theater, String[] jamTayang) {}

    // Rekam Data untuk Kursi
    public record Kursi(int nomorKursi, boolean tersedia) {}

    // Rekam Data untuk Transaksi
    public record Transaksi(int nomorAntrian, Penonton penonton, Film film, String jamTayang, Kursi[] kursiDipilih) {}

    public static void main(String[] args) {
        // Daftar film dengan Array
        Film[] daftarFilm = {
                new Film("Siksa neraka", "Horror", 98, 01, new String[]{"15:00", "17:00", "19:00", "21:00"}),
                new Film("13 bom di Jakarta", "Action", 144, 02, new String[]{"14:30", "17:00", "19:30", "22:00"}),
                new Film("Aquaman and the lost kingdom", "Fantasi", 124, 03, new String[]{"14:40", "16:50", "19:00", "21:10"}),
                new Film("Layangan putus", "Romance", 92, 04, new String[]{"15:30", "17:40", "19:50", "22:00"}),
                new Film("Migration", "comedy", 142, 05, new String[]{"14:30", "17:00", "19:30", "22:00"})
        };

        // Daftar penonton
        LinkedList<Penonton> penontonList = new LinkedList<>();

        // Stack untuk menyimpan indeks kursi yang telah dipesan
        Stack<Integer> indeksKursiDipesan = new Stack<>();

        // Antrian untuk menyimpan riwayat transaksi
        Queue<Transaksi> riwayatTransaksi = new LinkedList<>();

        // Inisialisasi kursi untuk bioskop
        Kursi[][] kursiBioskop = new Kursi[daftarFilm.length][10];
        for (int i = 0; i < daftarFilm.length; i++) {
            for (int j = 0; j < 10; j++) {
                kursiBioskop[i][j] = new Kursi(j + 1, true);
            }
        }

        // Memulai program
        Scanner scanner = new Scanner(System.in);
        boolean isProgramBerjalan = true;

        int nomorAntrian = 1; // Nomor antrian peserta (kode unik)

        // Menampilkan menu 
        while (isProgramBerjalan) {
            System.out.println("==========================================");
            System.out.println("\t\tMenu Bioskop");
            System.out.println("==========================================");
            System.out.println("\t1. Lihat Daftar Film");
            System.out.println("\t2. Pesan Tiket");
            System.out.println("\t3. Riwayat Transaksi");
            System.out.println("\t4. Keluar");
            System.out.println("==========================================");
            System.out.print("\tPilih menu (1-4): ");

            // Input pengguna
            int pilihan = scanner.nextInt();

            // Opsi menu 1
            if (pilihan == 1) {
                System.out.println("\nDaftar Film dan Jam Tayang:");
                for (int i = 0; i < daftarFilm.length; i++) {
                    System.out.println((i + 1) + ". " + daftarFilm[i].judul() + " - "
                            + daftarFilm[i].genre() + " - " + daftarFilm[i].durasi() + " menit");

                    System.out.print("   (");
                    for (String jam : daftarFilm[i].jamTayang()) {
                        System.out.print(" " + jam + " ");
                    }
                    System.out.println(")");
                }
                System.out.println(" ");

                // Opsi menu 2
            } else if (pilihan == 2) {
                // Nomor Antrian Peserta
                System.out.println("-----------------------------------------------");
                System.out.println("Nomor Antrian:  " + nomorAntrian++);
                System.out.println("-----------------------------------------------");

                // Pemesanan tiket
                System.out.print("Masukkan nomor film yang ingin ditonton: ");
                int indeksFilmDipilih = scanner.nextInt() - 1;

                // Membersihkan karakter newLine
                scanner.nextLine();

                // Menampilkan jam tayang
                if (indeksFilmDipilih >= 0 && indeksFilmDipilih < daftarFilm.length) {
                    System.out.println("Jam Tayang untuk Film " + daftarFilm[indeksFilmDipilih].judul() + ":");
                    for (int i = 0; i < daftarFilm[indeksFilmDipilih].jamTayang().length; i++) {
                        System.out.println((i + 1) + ". " + daftarFilm[indeksFilmDipilih].jamTayang()[i]);
                    }

                    // Memilih jam tayang
                    System.out.print("Masukkan nomor jam tayang yang diinginkan: ");
                    int indeksJamTayangDipilih = scanner.nextInt() - 1;


                    if (indeksJamTayangDipilih >= 0 && indeksJamTayangDipilih < daftarFilm[indeksFilmDipilih].jamTayang().length) {
                        // Memilih jumlah tiket yang ingin dibeli
                        System.out.println(" ");
                        System.out.print("Masukkan jumlah tiket yang ingin dibeli: ");
                        int jumlahTiket = scanner.nextInt();

                        Kursi[] kursiDipilih = new Kursi[jumlahTiket]; // Deklarasi di luar while

                        System.out.println(" ");
                        // Menampilkan kursi yang tersedia
                        System.out.println("===== KURSI YANG TERSEDIA =====");
                        for (int i = 0; i < kursiBioskop[indeksFilmDipilih].length; i++) {
                            if (kursiBioskop[indeksFilmDipilih][i].tersedia()) {
                                System.out.print(kursiBioskop[indeksFilmDipilih][i].nomorKursi() + " ");
                            }
                        }

                        System.out.println();

                        System.out.println(" ");
                        // Memilih kursi
                        boolean kursiValid = false;
                        while (!kursiValid) {
                            System.out.print("Masukkan nomor kursi yang ingin dipesan (dipisahkan spasi): ");
                            scanner.nextLine(); // Membersihkan buffer
                            String inputLine = scanner.nextLine();

                            // Memisahkan nomor kursi dari string input
                            String[] nomorKursiDipilih = inputLine.split(" ");

                            kursiDipilih = new Kursi[nomorKursiDipilih.length];
                            kursiValid = true;

                            for (int j = 0; j < nomorKursiDipilih.length; j++) {
                                int indeksKursi = Integer.parseInt(nomorKursiDipilih[j]) - 1;
                                if (indeksKursi >= 0 && indeksKursi < kursiBioskop[indeksFilmDipilih].length
                                        && kursiBioskop[indeksFilmDipilih][indeksKursi].tersedia()) {
                                    kursiBioskop[indeksFilmDipilih][indeksKursi]
                                            = new Kursi(kursiBioskop[indeksFilmDipilih][indeksKursi].nomorKursi(), false);
                                    kursiDipilih[j] = new Kursi(indeksKursi + 1, false);
                                    indeksKursiDipesan.push(indeksKursi);
                                } else {
                                    System.out.println("Nomor kursi tidak valid atau kursi sudah dipesan. Silakan coba lagi.");
                                    kursiValid = false;
                                    break;
                                }
                            }

                            // Informasi penonton
                            System.out.print("Masukkan nama Anda: ");
                            String namaPenonton = scanner.nextLine();

                            // Menampilkan nomor telepon penonton
                            System.out.print("Masukkan No. HP Anda: ");
                            String noHpPenonton = scanner.nextLine();
                            System.out.println("Nomor HP Penonton: " + noHpPenonton);

                            Penonton penonton = new Penonton(namaPenonton, noHpPenonton);
                            penontonList.add(penonton);

                            // Merekam transaksi
                            String jamTayangDipilih = daftarFilm[indeksFilmDipilih].jamTayang()[indeksJamTayangDipilih];
                            Transaksi transaksi = new Transaksi(nomorAntrian, penonton, daftarFilm[indeksFilmDipilih], jamTayangDipilih, kursiDipilih);
                            riwayatTransaksi.offer(transaksi);

                            System.out.println("---------------------------------------------");
                            System.out.println("Pemesanan berhasil. Selamat menikmati film!");
                            System.out.println(" ");
                            kursiValid = true;
                        }
                            // Melihat riwayat transaksi jika transaksi selesai
                            if (!riwayatTransaksi.isEmpty() && !indeksKursiDipesan.isEmpty()) {
                                System.out.println("========== INVOICE PEMESANAN TIKET ==========");
                                for (Transaksi transaksi : riwayatTransaksi) {
                                    System.out.print(
                                            "Nomor Antrian" + "\t: " + transaksi.nomorAntrian()
                                            + "\nPenonton" + "\t: " + transaksi.penonton().nama()
                                            + "\n============================================="
                                            + "\nTeater" + "\t\t: " + transaksi.film().Theater()
                                            + "\nFilm" + "\t\t: " + transaksi.film().judul()
                                            + "\nJam Tayang" + "\t: " + transaksi.jamTayang()
                                            + "\nKursi" + "\t\t: ");
                                    for (Kursi kursi : transaksi.kursiDipilih()) {
                                        System.out.print(kursi.nomorKursi() + ", ");
                                    }
                                    System.out.print("\n=============================================");
                                    System.out.println();
                                }
                            }
                            System.out.println(" ");
                            System.out.println(" ");

                    }
                }
            } else if (pilihan == 3) {
                    System.out.println(" ");
                    System.out.println("Riwayat Transaksi");
                    System.out.println("=============================================");
                    for (Transaksi transaksi : riwayatTransaksi) {
                        System.out.print(
                                "Nomor Antrian : " + transaksi.nomorAntrian()
                                + "\nPenonton : " + transaksi.penonton().nama()
                                + "\nTeater : " + transaksi.film().Theater()
                                + "\nFilm : " + transaksi.film().judul()
                                + "\nJam Tayang : " + transaksi.jamTayang()
                                + "\nKursi : " );

                        for (Kursi kursi : transaksi.kursiDipilih()) {
                            System.out.print(kursi.nomorKursi() + ", ");
                        }
                        System.out.println();
                    }

                    System.out.println(" ");

            } else if (pilihan == 4) {
                // Keluar dari program
                isProgramBerjalan = false;
            }   
        }

        // Menutup scanner
        scanner.close();
    }
}
