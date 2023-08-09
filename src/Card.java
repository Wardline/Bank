import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Card {
    private final String number;
    private final String PIN;
    //Баланс
    private float value = 0;
    private LocalDateTime blockDate = null;

    Card(String number, String PIN, float value, LocalDateTime blockDate) {
        //Валидация номера и пина
        Scanner sc = new Scanner(System.in);
        String patternNum = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
        String patternPIN = "\\d{4}";
        while (!number.matches(patternNum)) {
            System.out.println("Указан неверный номер карты! Введите его снова в формате «ХХХХ-ХХХХ-ХХХХ-ХХХХ»: ");
            number = sc.nextLine();
        }
        this.number = number;
        while (!PIN.matches(patternPIN)) {
            System.out.println("Указан неверный ПИН-код карты! Введите его снова: ");
            PIN = sc.nextLine();
        }
        this.PIN = PIN;
        this.value = value;
        this.blockDate = blockDate;
    }

    public void addValue(float value) {
        this.value += value;
    }

    public void subValue(float value) {
        this.value -= value;
    }

    public float getValue() {
        return value;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return PIN;
    }

    public void blockCard(){
        blockDate = LocalDateTime.now();
    }

    public void unblockCard(){
        blockDate = null;
    }

    public LocalDateTime getBlockData(){
        return blockDate;
    }

}
