import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class FileManager {
    public static void writeCardData(ArrayList<Card> list) {
        try {
            FileWriter fileWriter = new FileWriter("cards.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Card element : list) {
                bufferedWriter.write(element.getNumber() + " " + element.getPin() + " " + element.getValue() + " " + element.getBlockData());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Список карт записан в файл успешно.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи данных карт.");
        }
    }

    public static ArrayList<Card> readCardData() {
        ArrayList<Card> readList = new ArrayList<>();
        try {
            File file = new File("cards.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(" ");
                //номер карты, пин-код, баланс, дата блокировки(если есть)
                if (Objects.equals(values[3], "null")) {
                    readList.add(new Card(values[0], values[1], Float.parseFloat(values[2]), null));
                }
                else{
                    readList.add(new Card(values[0], values[1], Float.parseFloat(values[2]), LocalDateTime.parse(values[3])));
                }
            }

            bufferedReader.close();
            fileReader.close();
            System.out.println("Список карт прочитан из файла успешно.");
            return readList;
        } catch (IOException e) {
            System.out.println("Ошибка при чтении данных карт.");
            return null;
        }
    }

    public static Terminal readTerminalData() {
        try {
            File file = new File("terminal.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            bufferedReader.close();
            fileReader.close();
            System.out.println("Состояние терминала прочитано успешно.");
            return new Terminal(Float.parseFloat(line));
        } catch (Exception e) {
            System.out.println("Ошибка при чтении данных терминала.");
            return null;
        }
    }

    public static void writeTerminalData(float cash) {
        try {
            FileWriter fileWriter = new FileWriter("terminal.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(Float.toString(cash));

            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Состояние терминала записано успешно.");
        } catch (Exception e) {
            System.out.println("Ошибка при записи данных терминала.");
        }
    }
}
