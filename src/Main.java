
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Читаем состояние терминала.
        Terminal terminal = FileManager.readTerminalData();
        Scanner scanner = new Scanner(System.in);

        //Читаем список всех карт.
        ArrayList<Card> cardList = FileManager.readCardData();

        if (cardList == null){
            System.out.println("Карты для обслуживания не найдены!");
            return;
        }
        if (terminal == null){
            System.out.println("Терминал не работает!");
            return;
        }

        System.out.println("Введите номер карты: ");
        String cardNumber = scanner.nextLine();
        String patternNum = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";

        while (!cardNumber.matches(patternNum)) {
            System.out.println("Указан неверный номер карты! Введите его снова в формате «ХХХХ-ХХХХ-ХХХХ-ХХХХ»: ");
            cardNumber = scanner.nextLine();
        }
        //Ищем карту по запросу клиента.
        for (Card card : cardList) {
            if (card.getNumber().matches(cardNumber)) {
                //Попытка прочесть карту банкоматом
                if (terminal.getCard(card)) {
                    //Карта прочитана, производим операции
                    System.out.println("Карта успешно прочитана!");
                    if (terminal.doOperation()) {
                        //сохраняем изменения если операции прошли корректно
                        FileManager.writeCardData(cardList);
                        FileManager.writeTerminalData(terminal.getCash());
                    }
                }
                else{
                    System.out.println("Терминал отказал в обслуживании!");
                    //Если терминал заблокировал карту, внесём эти данные
                    FileManager.writeCardData(cardList);
                }
                break;
            }
        }
        System.out.println("Карта с указанным номером не обслуживается!");
    }
}