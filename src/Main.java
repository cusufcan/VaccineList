import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static Scanner scn = new Scanner(System.in);
    public static boolean ciksinMi = false;

    public static void main(String[] args)
    {
        while (!ciksinMi)
        {
            MenuYaz();
        }
    }

    public static void MenuYaz()
    {
        System.out.println();
        System.out.println("              MENÜ              ");
        System.out.println("________________________________");
        System.out.println("Yeni kayıt için              1:");
        System.out.println("Randevu almak için           2:");
        System.out.println("Randevuları listelemek için  3:");
        System.out.println("Çıkış İçin                   0:\n");
        System.out.print("LÜTFEN SEÇİMİNİZİ GİRİNİZ    :");

        int n = scn.nextInt();
        switch (n)
        {
            case 1:
                YeniKayit();
                break;
            case 2:
                RandevuAl();
                break;
            case 3:
                RandevuListele();
                break;
            case 0:
                ciksinMi = true;
                break;
            default:
                System.out.println("Anlaşılmadı. Lütfen tekrar deneyin...");
                break;
        }
    }

    public static void YeniKayit()
    {
        System.out.print("Öğrenci NO: ");
        int ogrNo = scn.nextInt();
        scn.nextLine();
        System.out.print("Adı ve Soyadı: ");
        String adSoyad = scn.nextLine();

        boolean kayitVarMi = false;

        try
        {
            File dosyaAdi = new File("asiListesi.txt");
            String okunan = null;
            if (dosyaAdi.exists())
            {
                Scanner reader = new Scanner(dosyaAdi);

                while (reader.hasNextLine())
                {
                    okunan = reader.nextLine();
                    String[] bolunmusOkunan = okunan.split(",");
                    for (int i = 0; i < bolunmusOkunan.length; i++)
                    {
                        if (String.valueOf(ogrNo).equals(bolunmusOkunan[i]))
                        {
                            System.out.println("Bu öğrenci numarasına ait bir kayıt bulunmakta...");
                            kayitVarMi = true;
                            break;
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Okuma işleminde bir hata oluştu...");
            e.printStackTrace();
        }

        if (!kayitVarMi)
        {
            try
            {
                File dosyaAdi = new File("asiListesi.txt");
                dosyaAdi.createNewFile();

                Scanner readerForLineCount = new Scanner(dosyaAdi);
                int lineCounter = 0;
                while (readerForLineCount.hasNextLine())
                {
                    lineCounter++;
                    readerForLineCount.nextLine();
                }

                String[] tempArrNum = new String[lineCounter + 1];
                String[] tempArrNames = new String[lineCounter + 1];
                String[] tempArrVaccines = new String[lineCounter + 1];
                String[] tempArrDates = new String[lineCounter + 1];

                DosyayiDizilereCek(false, ogrNo, adSoyad, -1, 0, null, lineCounter, tempArrNum, tempArrNames, tempArrVaccines, tempArrDates);

                FileWriter yazici = new FileWriter(dosyaAdi, false);
                for (int i = 0; i < tempArrNum.length; i++)
                {
                    if (tempArrVaccines[i] != null)
                    {
                        yazici.write(tempArrNum[i] + ", " + tempArrNames[i] + ", " + tempArrVaccines[i] + ", " + tempArrDates[i] + ",\n");
                    }
                    else
                    {
                        yazici.write(tempArrNum[i] + ", " + tempArrNames[i] + ",\n");
                    }
                }
                yazici.close();

                System.out.println("Kaydınız başarılı bir şekilde yapılmıştır...");
            }
            catch (IOException e)
            {
                System.out.println("Yazma işleminde bir hata oluştu...");
                e.printStackTrace();
            }
        }
    }

    public static void RandevuAl()
    {
        System.out.print("Öğrenci NO: ");
        int ogrNo = scn.nextInt();

        try
        {
            File file = new File("asiListesi.txt");
            String okunan = null;
            Scanner reader = new Scanner(file);

            int posForVac = -1;
            int line = 0;
            while (reader.hasNextLine())
            {
                okunan = reader.nextLine();
                line++;
                String[] bolunmusOkunan = okunan.split(",");
                for (int i = 0; i < bolunmusOkunan.length; i++)
                {
                    if (String.valueOf(ogrNo).equals(bolunmusOkunan[i]))
                    {
                        posForVac = line;
                        System.out.println(bolunmusOkunan[i + 1].substring(1) + ":");
                        break;
                    }
                }
            }

            if (posForVac != -1)
            {
                System.out.println();
                System.out.println("              MENÜ              ");
                System.out.println("________________________________");
                System.out.println("Biontech                     1:");
                System.out.println("Sinovac                      2:");
                System.out.println("Sputnik                      3:");

                System.out.print("\nLütfen aşı seçiminizi giriniz:");
                int vaccineNum = scn.nextInt();
                scn.nextLine();
                System.out.print("Lütfen aşı tarihini giriniz  :");
                String vaccineDate = scn.nextLine();

                Scanner readerForLineCount = new Scanner(file);
                int lineCounter = 0;
                while (readerForLineCount.hasNextLine())
                {
                    lineCounter++;
                    readerForLineCount.nextLine();
                }

                String[] tempArrNum = new String[lineCounter];
                String[] tempArrNames = new String[lineCounter];
                String[] tempArrVaccines = new String[lineCounter];
                String[] tempArrDates = new String[lineCounter];

                DosyayiDizilereCek(true, -1, null, posForVac, vaccineNum, vaccineDate, lineCounter, tempArrNum, tempArrNames, tempArrVaccines, tempArrDates);

                try
                {
                    FileWriter yazici = new FileWriter(file, false);
                    for (int i = 0; i < tempArrNum.length; i++)
                    {
                        if (tempArrVaccines[i] != null)
                        {
                            yazici.write(tempArrNum[i] + ", " + tempArrNames[i] + ", " + tempArrVaccines[i] + ", " + tempArrDates[i] + ",\n");
                        }
                        else
                        {
                            yazici.write(tempArrNum[i] + ", " + tempArrNames[i] + ",\n");
                        }
                    }
                    yazici.close();

                    System.out.println("Randevunuz oluşturulmuştur...");
                }
                catch (IOException e)
                {
                    System.out.println("Yazma işleminde bir hata oluştu...");
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Bu öğrenci numarasına ait bir kayıt bulunmamakta...");
            }

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Okuma işleminde bir hata oluştu...");
            e.printStackTrace();
        }
    }

    public static void RandevuListele()
    {
        try
        {
            File fileName = new File("asiListesi.txt");
            String line = null;
            Scanner reader = new Scanner(fileName);
            int lineIdx = 1;

            System.out.println("\nSıra NO \t Öğrenci Numarası \t Adı Soyadı \t Aşı Firması \t Tarih");
            System.out.println("_________________________________________________________________________");
            while (reader.hasNextLine())
            {
                line = reader.nextLine();
                String[] bolunmusOkunan = line.split(",");
                if (bolunmusOkunan.length >= 3)
                {
                    System.out.println("   " + lineIdx + "\t\t\t\t" + bolunmusOkunan[0] + "\t\t   " + bolunmusOkunan[1] + "\t  " + bolunmusOkunan[2] + "\t  " + bolunmusOkunan[3]);
                    lineIdx++;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Okuma işleminde bir hata oluştu...");
            e.printStackTrace();
        }
    }

    public static void DosyayiDizilereCek(boolean isRegisterNewVac, int rNumber, String rName, int rPosForVac, int rVacNum, String rDate, int lineCounter, String[] tempArrNum, String[] tempArrNames, String[] tempArrVacNames, String[] tempArrDates)
    {
        try
        {
            String vaccineName = "";
            switch (rVacNum)
            {
                case 1:
                    vaccineName = "Biontech";
                    break;
                case 2:
                    vaccineName = "Sinovac";
                    break;
                case 3:
                    vaccineName = "Sputnik";
                    break;
                default:
                    break;
            }

            if (lineCounter > 0)
            {
                File fileName = new File("asiListesi.txt");
                String line = null;
                Scanner reader = new Scanner(fileName);

                String[] numbers = new String[lineCounter];
                String[] names = new String[lineCounter];
                String[] vaccines = new String[lineCounter];
                String[] dates = new String[lineCounter];

                int arrayIdx = 0;

                while (reader.hasNextLine())
                {
                    line = reader.nextLine();

                    String[] dividedLine = line.split(",");

                    numbers[arrayIdx] = dividedLine[0];
                    names[arrayIdx] = dividedLine[1].substring(1);
                    if(dividedLine.length >= 3)
                    {
                        vaccines[arrayIdx] = dividedLine[2].substring(1);
                    }
                    if (dividedLine.length == 4)
                    {
                        dates[arrayIdx] = dividedLine[3].substring(1);
                    }
                    arrayIdx++;
                }

                if (rNumber != -1)
                {
                    int pos = numbers.length;
                    for (int i = 0; i < numbers.length; i++)
                    {
                        if (rNumber <= Integer.valueOf(numbers[i]))
                        {
                            pos = i;
                            break;
                        }
                    }

                    for (int i = 0; i <= pos; i++)
                    {
                        if (i == pos)
                        {
                            tempArrNum[i] = String.valueOf(rNumber);
                            tempArrNames[i] = rName;
                            break;
                        }
                        tempArrNum[i] = numbers[i];
                        tempArrNames[i] = names[i];
                    }

                    for (int i = pos; i < numbers.length; i++)
                    {
                        tempArrNum[i + 1] = numbers[i];
                        tempArrNames[i + 1] = names[i];
                    }

                    if (numbers[numbers.length - 1] == null)
                    {
                        numbers[numbers.length - 1] = String.valueOf(rNumber);
                        names[names.length - 1] = rName;
                    }
                }
                else
                {
                    for (int i = 0; i < names.length; i++)
                    {
                        tempArrNum[i] = numbers[i];
                        tempArrNames[i] = names[i];
                    }
                }

                if (isRegisterNewVac)
                {
                    int pos = rPosForVac - 1;

                    for (int i = 0; i < vaccines.length; i++)
                    {
                        if (i == pos)
                        {
                            tempArrVacNames[i] = vaccineName;
                            tempArrDates[i] = rDate;
                        }
                        else
                        {
                            tempArrVacNames[i] = vaccines[i];
                            tempArrDates[i] = dates[i];
                        }
                    }
                }
                else
                {
                    for (int i = 0; i < vaccines.length; i++)
                    {
                        tempArrVacNames[i] = vaccines[i];
                        tempArrDates[i] = dates[i];
                    }
                }
            }
            else
            {
                if (tempArrNum != null)
                {
                    tempArrNum[0] = String.valueOf(rNumber);
                    tempArrNames[0] = rName;
                }

                if (isRegisterNewVac)
                {
                    tempArrVacNames[0] = vaccineName;
                    tempArrDates[0] = rDate;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Okuma işleminde bir hata oluştu...");
            e.printStackTrace();
        }
    }
}