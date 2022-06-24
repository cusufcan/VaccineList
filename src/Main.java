import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static Scanner scn = new Scanner(System.in);
    public static boolean isExit = false;

    public static void main(String[] args)
    {
        while (!isExit)
        {
            PrintMenu();
        }
    }

    public static void PrintMenu()
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
                RegisterNew();
                break;
            case 2:
                MakeAnAppointment();
                break;
            case 3:
                ListAppointments();
                break;
            case 0:
                isExit = true;
                break;
            default:
                System.out.println("Anlaşılmadı. Lütfen tekrar deneyin...");
                break;
        }
    }

    public static void RegisterNew()
    {
        System.out.print("Öğrenci NO: ");
        int studentNum = scn.nextInt();
        scn.nextLine();
        System.out.print("Adı ve Soyadı: ");
        String name = scn.nextLine();

        boolean isExist = false;

        try
        {
            File file = new File("asiListesi.txt");
            String line = null;
            if (file.exists())
            {
                Scanner reader = new Scanner(file);

                while (reader.hasNextLine())
                {
                    line = reader.nextLine();
                    String[] dividedLine = line.split(",");
                    for (int i = 0; i < dividedLine.length; i++)
                    {
                        if (String.valueOf(studentNum).equals(dividedLine[i]))
                        {
                            System.out.println("Bu öğrenci numarasına ait bir kayıt bulunmakta...");
                            isExist = true;
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

        if (!isExist)
        {
            try
            {
                File file = new File("asiListesi.txt");
                file.createNewFile();

                Scanner readerForLineCount = new Scanner(file);
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

                ReturnArrayWithNewRegister(studentNum, name, lineCounter, tempArrNum, tempArrNames, tempArrVaccines, tempArrDates);

                FileWriter writer = new FileWriter(file, false);
                for (int i = 0; i < tempArrNum.length; i++)
                {
                    if (tempArrVaccines[i] != null)
                    {
                        writer.write(tempArrNum[i] + ", " + tempArrNames[i] + ", " + tempArrVaccines[i] + ", " + tempArrDates[i] + ",\n");
                    }
                    else
                    {
                        writer.write(tempArrNum[i] + ", " + tempArrNames[i] + ",\n");
                    }
                }
                writer.close();

                System.out.println("Kaydınız başarılı bir şekilde yapılmıştır...");
            }
            catch (IOException e)
            {
                System.out.println("Yazma işleminde bir hata oluştu...");
                e.printStackTrace();
            }
        }
    }

    public static void MakeAnAppointment()
    {
        System.out.print("Öğrenci NO: ");
        int studentNum = scn.nextInt();

        try
        {
            File file = new File("asiListesi.txt");
            String read = null;
            Scanner reader = new Scanner(file);

            int posForVac = -1;
            int line = 0;
            while (reader.hasNextLine())
            {
                read = reader.nextLine();
                line++;
                String[] dividedLine = read.split(",");
                for (int i = 0; i < dividedLine.length; i++)
                {
                    if (String.valueOf(studentNum).equals(dividedLine[i]))
                    {
                        if (dividedLine.length <= 2)
                        {
                            posForVac = line;
                            System.out.println(dividedLine[i + 1].substring(1) + ":");
                        }
                        else posForVac = -2;

                        break;
                    }
                }
            }

            switch (posForVac)
            {
                case -1:
                    System.out.println("Bu öğrenci numarasına ait bir kayıt bulunmamakta...");
                    break;
                case -2:
                    System.out.println("Zaten randevu alınmış...");
                    break;
                default:
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

                        ReturnArrayWithNewAppointment(vaccineNum, posForVac, vaccineDate, lineCounter, tempArrNum, tempArrNames, tempArrVaccines, tempArrDates);

                        try
                        {
                            FileWriter writer = new FileWriter(file, false);
                            for (int i = 0; i < tempArrNum.length; i++)
                            {
                                if (tempArrVaccines[i] != null)
                                {
                                    writer.write(tempArrNum[i] + ", " + tempArrNames[i] + ", " + tempArrVaccines[i] + ", " + tempArrDates[i] + ",\n");
                                }
                                else
                                {
                                    writer.write(tempArrNum[i] + ", " + tempArrNames[i] + ",\n");
                                }
                            }
                            writer.close();

                            System.out.println("Randevunuz oluşturulmuştur...");
                        }
                        catch (IOException e)
                        {
                            System.out.println("Yazma işleminde bir hata oluştu...");
                            e.printStackTrace();
                        }
                    }
                    break;
            }

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Okuma işleminde bir hata oluştu...");
            e.printStackTrace();
        }
    }

    public static void ListAppointments()
    {
        try
        {
            File fileName = new File("asiListesi.txt");

            String tempLine = null;
            Scanner readerForLineCheck = new Scanner(fileName);
            boolean isAnyAppointmentExists = false;
            while(readerForLineCheck.hasNextLine())
            {
                tempLine = readerForLineCheck.nextLine();
                String[] dividedLineForCheck = tempLine.split(",");
                if (dividedLineForCheck.length >= 3)
                {
                    isAnyAppointmentExists = true;
                    break;
                }
            }

            String line = null;
            Scanner reader = new Scanner(fileName);
            int lineIdx = 1;

            if (isAnyAppointmentExists)
            {
                System.out.println("\nSıra NO \t Öğrenci Numarası \t Adı Soyadı \t Aşı Firması \t Tarih");
                System.out.println("_________________________________________________________________________");
                while (reader.hasNextLine())
                {
                    line = reader.nextLine();
                    String[] dividedLine = line.split(",");
                    if (dividedLine.length >= 3)
                    {
                        System.out.println("   " + lineIdx + "\t\t\t " + dividedLine[0] + "\t  " + dividedLine[1] + "\t  " + dividedLine[2] + "\t  " + dividedLine[3]);
                        lineIdx++;
                    }
                }
            }
            else
            {
                System.out.println("Herhangi bir randevu bulunmamaktadır...");
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Okuma işleminde bir hata oluştu...");
            e.printStackTrace();
        }
    }

    public static void ReturnArrayWithNewRegister(int rNum, String rName, int lineCounter, String[] tempArrNum, String[] tempArrNames, String[] tempArrVacNames, String[] tempArrDates)
    {
        try
        {
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

                int pos = numbers.length;
                for (int i = 0; i < numbers.length; i++)
                {
                    if (rNum <= Integer.valueOf(numbers[i]))
                    {
                        pos = i;
                        break;
                    }
                }

                for (int i = 0; i < pos; i++)
                {
                    tempArrVacNames[i] = vaccines[i];
                    tempArrDates[i] = dates[i];
                }

                for (int i = 0; i <= pos; i++)
                {
                    if (i == pos)
                    {
                        tempArrNum[i] = String.valueOf(rNum);
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
                    tempArrVacNames[i + 1] = vaccines[i];
                    tempArrDates[i + 1] = dates[i];
                }

                if (numbers[numbers.length - 1] == null)
                {
                    numbers[numbers.length - 1] = String.valueOf(rNum);
                    names[names.length - 1] = rName;
                }
            }
            else
            {
                if (tempArrNum != null)
                {
                    tempArrNum[0] = String.valueOf(rNum);
                    tempArrNames[0] = rName;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Okuma işleminde bir hata oluştu...");
            e.printStackTrace();
        }
    }

    public static void ReturnArrayWithNewAppointment(int rVacNum, int rPosForVac, String rDate, int lineCounter, String[] tempArrNum, String[] tempArrNames, String[] tempArrVacNames, String[] tempArrDates)
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

                for (int i = 0; i < names.length; i++)
                {
                    tempArrNum[i] = numbers[i];
                    tempArrNames[i] = names[i];
                }

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
                tempArrVacNames[0] = vaccineName;
                tempArrDates[0] = rDate;
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Okuma işleminde bir hata oluştu...");
            e.printStackTrace();
        }
    }
}