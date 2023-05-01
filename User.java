import java.util.Scanner;
import java.util.regex.*;

public class User {
    Scanner inputScanner = new Scanner(System.in);
    private String namaUser;
    private String passwordUser;
    private int userindex;
    private boolean admin;
    private boolean verified;
    Object[][] UserDB = { { "admin", "admin", 0, true },
            { "202210370311406", "UMM_a2022", 1, true },
            { "202110370311406", "UMM_a2021", 1, false }
    };

    public int getUserindex() {
        return userindex;
    }

    public void setUserindex(int userindex) {
        this.userindex = userindex;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Object getUserDB(int row, int col) {
        return UserDB[row][col];
    }

    public void setUserDB(Object userDB, int row, int col) {
        UserDB[row][col] = userDB;
    }

    // Setter dan Getter
    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    // Method untuk login
    public void login() {
        do {
            System.out.print("Masukkan Username : ");
            setNamaUser(inputScanner.next());
            if (getNamaUser().equals("0")) {
                break;
            }
            System.out.print("Masukkan Password : ");
            setPasswordUser(inputScanner.next());
            for (int i = 0; i < UserDB.length; i++) {

                setUserindex(i);
                if (getNamaUser().equals(getUserDB(getUserindex(), 0))
                        && getPasswordUser().equals(getUserDB(getUserindex(), 1))) {
                    setAdmin(getUserDB(getUserindex(), 2).equals(0));
                    dashboard();
                    break;
                } else if (getUserindex() >= UserDB.length - 1) {
                    System.out.println("Username Atau Password Yang Anda Masukkan Salah!");
                }
            }
            if (getNamaUser().equals("0")) {
                break;
            }
        } while (!getNamaUser().equals(getUserDB(getUserindex(), 0))
                || !getPasswordUser().equals(getUserDB(getUserindex(), 1)));
    }

    public void dashboard() {
        int pilihan;
        do {
            if (isAdmin()) {
                System.out.println("\nSistem Akademik UMM | Admin");
                System.out
                        .println("1.Update Status Mahasiswa\n2.Update Password Mahasiswa\n3.Check Database\n4.Logout");
                System.out.print("Masukkan Pilihan : ");
                pilihan = inputScanner.nextInt();
                switch (pilihan) {
                    case 1:
                        UpdateStatus();
                        break;
                    case 2:
                        UpdatePassword();
                        break;
                    case 3:
                    checkdatabase();
                        break;
                    case 0:
                        login();
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("\nSistem Akademik UMM | Mahasiswa");
                System.out.println("Username : " + getUserDB(getUserindex(), 0));
                System.out.println("Status Mahasiswa : " + getUserDB(getUserindex(), 3));
                System.out.println("1.Update Password Mahasiswa\n0.Logout");
                System.out.print("Masukkan Pilihan : ");
                pilihan = inputScanner.nextInt();
                switch (pilihan) {
                    case 1:
                        UpdatePassword();
                        break;
                    case 0:
                        login();
                    default:
                        break;
                }
            }
        } while (pilihan != 0);
    }
    public void UpdateStatus() {
        int select;
        do {
            System.out.println("Update Status Mahasiswa ");
            System.out.print("Masukkan NIM Mahasiswa : ");
            setNamaUser(inputScanner.next());
            for (int i = 1; i < UserDB.length; i++) {
                setUserindex(i);
                if (getNamaUser().equals(getUserDB(getUserindex(), 0))) {
                    break;
                } else if (getUserindex() >= UserDB.length - 1) {
                    System.out.println("Data Tidak Ditemukan!\n");
                }
            }
        } while (!getNamaUser().equals(getUserDB(getUserindex(), 0)));

        System.out.println("Ganti Status Mahasiswa : \n1.Aktif\n2.Tidak Aktif");
        System.out.print("Pilih Menu : ");
        select = inputScanner.nextInt();
        switch (select) {
            case 1:
                setUserDB(true, getUserindex(), 3);
                break;
            case 2:
                setUserDB(false, getUserindex(), 3);
                break;
            default:
                break;
        }
        System.out.println("Status Mahasiswa Berhasil Di Ubah");
    }

    public void UpdatePassword() {

        if (isAdmin()) {
            do {
                System.out.println("Update Password Mahasiswa ");
                System.out.print("Masukkan NIM Mahasiswa : ");
                setNamaUser(inputScanner.next());
                for (int i = 1; i < UserDB.length; i++) {
                    setUserindex(i);
                    if (getNamaUser().equals(getUserDB(getUserindex(), 0))) {
                        break;
                    } else if (getUserindex() >= UserDB.length - 1) {
                        System.out.println("Data Tidak Ditemukan!\n");
                    }
                }
            } while (!getNamaUser().equals(getUserDB(getUserindex(), 0)));
            do {
                System.out.println("Note : Password Harus Terdiri dari Simbol,huruf Kapital dan Kecil Serta Angka");
                System.out.print("Masukkan Password Baru : ");
                setPasswordUser(inputScanner.next());
                setVerified(getPasswordUser());
                if (isVerified()) {
                    System.out.println("Password berhasil Di Perbarui!");
                setUserDB(getPasswordUser(), getUserindex(), 1);
                }
            } while (!isVerified());
        } else {
            do {
                System.out.println("Note : Password Harus Terdiri dari Simbol,huruf Kapital dan Kecil Serta Angka");
                System.out.print("Masukkan Password Baru : ");
                setPasswordUser(inputScanner.next());
                setVerified(getPasswordUser());
                if (isVerified()) {
                    System.out.println("Password berhasil Di Perbarui!");
                    setUserDB(getPasswordUser(), getUserindex(), 1);
                }
            } while (!isVerified());
        }
    }

    boolean isPassSymbols(String param) {
        String simbolRegex = ".*[!@#$%^&*_()\\-+=\\[\\]{};':\"\\\\|,.<>\\/?].*";
        Pattern simbolPattern = Pattern.compile(simbolRegex);
        Matcher simbolMatcher = simbolPattern.matcher(param);
        boolean simbolBenar = simbolMatcher.matches();
        if (!simbolBenar) {

            return false;
        } else {
            return true;
        }

    }

    public boolean isPassDigit(String param) {
        String digitRegex = ".*[0-9].*";
        Pattern digitPattern = Pattern.compile(digitRegex);
        Matcher digitMatcher = digitPattern.matcher(param);
        boolean digitBenar = digitMatcher.matches();
        if (!digitBenar) {

            return false;
        } else {
            return true;
        }

    }

    public boolean isPassUpperCase(String Param) {
        String upperRegex = ".*[A-Z].*";
        Pattern upperPattern = Pattern.compile(upperRegex);
        Matcher upperMatcher = upperPattern.matcher(Param);
        boolean upperBenar = upperMatcher.matches();
        if (!upperBenar) {
            return false;
        } else {
            return true;
        }

    }

    public boolean isPassLowerCase(String Param) {
        String lowerRegex = ".*[a-z].*";
        Pattern lowerPattern = Pattern.compile(lowerRegex);
        Matcher lowerMatcher = lowerPattern.matcher(Param);
        boolean lowerBenar = lowerMatcher.matches();
        if (!lowerBenar) {
            return false;
        } else {
            return true;
        }
    }

    public void checkdatabase() {
        System.out.println("\nData Base Mahasiswa :");
        for (int i = 1; i < UserDB.length; i++) {
            System.out.print("Username Mahasiswa " + i + ": " + getUserDB(i, 0));
            System.out.println(" Status : " + getUserDB(i, 3));
            System.out.println("Password Mahasiswa " + i + ": " + getUserDB(i, 1));

        }

    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        if (!isPassDigit(verified)) {
            System.out.println("\nPassword Harus Memiliki Angka!");
        }
        if (!isPassLowerCase(verified)) {
            System.out.println("\nPassword Harus Memiliki Huruf Kecil!");
        }
        if (!isPassSymbols(verified)) {
            System.out.println("\nPassword Harus Memiliki Simbol!");
        }
        if (!isPassUpperCase(verified)) {
            System.out.println("\nPassword Harus Memiliki Huruf Besar!");
        }
        if (isPassDigit(verified) && isPassLowerCase(verified) && isPassUpperCase(verified)
                && isPassSymbols(verified)) {
            this.verified = true;
        } else {
            this.verified = false;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.login();
    }
}
