import java.util.Scanner;
public class MineSweeper {
    int rowNumber;
    int colNumber;
    String[][] mineField;
    String[][] mineFieldStatus;

    //Değerlendirme formu 7
    //run methodunda kullanıcıdan satır ve sütun sayılarını alıp oyunu başlatıyoruz.
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Mayın Tarlası Oyununa Hoşgeldiniz!");
            System.out.print("Satır sayısını giriniz: ");
            rowNumber = scanner.nextInt();
            System.out.print("Sütun sayısını giriniz: ");
            colNumber = scanner.nextInt();
            if (rowNumber < 2 || colNumber < 2) {
                System.out.println("Satır ve sütun sayısı en az 2 olmalıdır. Lütfen tekrar deneyin.");
            }
        } while (rowNumber < 2 || colNumber < 2);

        mineField = new String[rowNumber][colNumber];
        mineFieldStatus = new String[rowNumber][colNumber];
        playGame();
    }

    // Oyunu başlatır.
    public void playGame() {
        createMineField();
        printMineField();
        while (true) {
            printMineFieldStatus();
            userInput();
            if (isGameFinished()) {     //Değerlendirme formu 14
                System.out.println("Tebrikler! Oyunu kazandınız.");     //Değerlendirme formu 15
                printMineFieldStatus();
                break;
            }
        }
    }

    //Değerlendirme formu 8
    // Mayın tarlasını oluşturur.
    // Mayınların konumunu random olarak belirler.
    public void createMineField() {
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                mineField[i][j] = "-";     // Mayın alanı başlangıçta mayın içermeyen duruma getiriliyor.
                mineFieldStatus[i][j] = "-";       // Oyun alanı başlangıçta kullanıcıya kapalı hücre olarak gösteriliyor.
            }
        }
        int targetMineCount = (rowNumber * colNumber) / 4;
        while (targetMineCount > 0) {
            int row = (int) (Math.random() * rowNumber);
            int col = (int) (Math.random() * colNumber);
            if (!mineField[row][col].equals("*")) {
                mineField[row][col] = "*";
                targetMineCount--;
            }
        }
    }
    // Mayınların konumunu yazdırır.
    public void printMineField() {
        System.out.println("Mayınların Konumu");
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                if (mineField[i][j].equals("*")) {
                    System.out.print("* ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
    //Değerlendirme formu 11
    // Oyunun şu anki durumunu yazdırır.
    public void printMineFieldStatus() {
        System.out.println("Mayın Tarlası");
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                if (mineFieldStatus[i][j].equals("-")) {
                    System.out.print("- ");
                } else {
                    System.out.print(mineFieldStatus[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    //Değerlendirme formu 9 - Değerlendirme formu 10
    // Kullanıcıdan satır ve sütun sayılarını alıp oyun oynama işlemini gerçekleştirir.
    public void userInput() {
        Scanner scanner = new Scanner(System.in);
        int row;
        int col;
        while (true) {
            System.out.print("Satır girin: ");
            row = scanner.nextInt();
            System.out.print("Sütun girin: ");
            col = scanner.nextInt();
            System.out.println("=====================================");
            if (row < 0 || row >= rowNumber || col < 0 || col >= colNumber) {
                System.out.println("Geçersiz koordinatlar! Lütfen tekrar deneyin.");
            } else {
                break;
            }
        }
        checkMine(row, col);
    }

    //Kullanıcının seçtiği hücrenin mayın olup olmadığını kontrol eder.
    private void checkMine(int row, int col) {
        if (!mineFieldStatus[row][col].equals("-")) {     //Eğer daha önce seçilen bir hücre seçilirse kullanıcıya uyarı verir.
            System.out.println("Bu hücre daha önce seçildi! Lütfen tekrar deneyin.");
            return;
        }
        else if (mineField[row][col].equals("*")) {      // Değerlendirme formu 13.
            System.out.println("GAME OVER!");            //Değerlendirme formu 15
            System.exit(0);
        }
        else {                                          // Değerlendirme formu 12
            int count = countMines(row, col);           // Eğer kullanıcı mayına basmazsa o hücrenin etrafındaki mayın sayısını hesaplar ve o hücreye yazar.
            mineFieldStatus[row][col] = String.valueOf(count);
        }
    }

    //Değerlendirme formu 12
    // Her bir hücrenin etrafındaki mayın sayısını hesaplar.
    // Eğer hücrenin etrafında mayın yoksa 0 döndürür.
    public int countMines(int row, int col) {
        int minesCount = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rowNumber && j >= 0 && j < colNumber) {
                    if (mineField[i][j].equals("*")) {
                        minesCount++;        // Eğer hücrenin etrafında mayın varsa mayın sayısını arttırır.
                    }
                }
            }
        }
        return minesCount;
    }

    //Değerlendirme formu 14
    // Oyunun bitip bitmediğini kontrol eder.
    public boolean isGameFinished() {
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < colNumber; j++) {
                if (mineFieldStatus[i][j].equals("-") && mineField[i][j].equals("-")) {     // Eğer kullanıcı seçmediği bir hücre kaldıysa oyun bitmemiş demektir.
                    return false;
                }
            }
        }
        return true;
    }
}