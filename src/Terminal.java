import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Terminal {

    /*
     * При пополнении наличность в терминале увеличивается, а при снятии уменьшается.
     * */

    private Card card;
    //Наличные в банкомате
    private float cash = 0;
    private final Scanner scanner = new Scanner(System.in);

    Terminal(float cash) {
        this.cash = cash;
    }

    /**
     * Метод подключения карты к терминалу для произведения операций.
     */
    public boolean getCard(Card card) {
        if (card == null) {
            return false;
        }

        if (card.getBlockData() != null) {
            Duration duration = Duration.between(card.getBlockData(), LocalDateTime.now());
            long differenceInHours = duration.toHours();
            if (differenceInHours >= 24) {
                System.out.println("Карта разблокирована!");
                card.unblockCard();
            } else {
                System.out.println("Не прошло время блокировки!");
                return false;
            }
        }

        System.out.println("Введите ПИН-код: ");
        String cardPIN;
        String patternPIN = "\\d{4}";

        int counter = 3;
        while (counter != 0) {
            cardPIN = scanner.nextLine();
            while (!cardPIN.matches(patternPIN)) {
                System.out.println("Указан неверный ПИН-код карты! Введите его снова: ");
                cardPIN = scanner.nextLine();
            }
            if (!cardPIN.equals(card.getPin())) {
                System.out.println("ПИН-код не совпадает!");
            } else {
                System.out.println("Номер карты и ПИН-код совпадают!");
                this.card = card;
                return true;
            }
            counter--;
        }
        System.out.println("Карта заблокирована!");
        card.blockCard();
        return false;
    }

    /**
     * Установить доступную в терминале наличность.
     */
    public void setAvailableCash(float cash) {
        this.cash = cash;
    }

    public float getCash() {
        return cash;
    }

    /**
     * Вызов меню операций для карты в терминале.
     */
    public boolean doOperation() {
        if (card == null) {
            System.out.println("Ошибка! Карта не обнаружена!");
            return false;
        }
        while (true) {
            System.out.println("Список операций:\n 1-Проверить баланс\n 2-Пополнить баланс\n 3-Снять деньги\n 4-Завершить сессию");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    checkBalance();
                    break;
                }
                case "2": {
                    System.out.println("Введите сумму для пополнения:");
                    float deposit;
                    try {
                        deposit = Float.parseFloat(scanner.nextLine());
                        deposit = (float) (Math.round(deposit * 100.0) / 100.0);
                    } catch (NumberFormatException e) {
                        System.out.println("Введена недопустимая сумма!");
                        break;
                    }
                    if (depositMoney(deposit))
                        System.out.println("Успешно пополненно.");
                    else
                        System.out.println("Произошла ошибка при пополнении.");
                    break;
                }
                case "3": {
                    System.out.println("Введите сумму для снятия:");
                    float withdraw;
                    try {
                        withdraw = Float.parseFloat(scanner.nextLine());
                        withdraw = (float) (Math.round(withdraw * 100.0) / 100.0);
                    } catch (NumberFormatException e) {
                        System.out.println("Введена недопустимая сумма!");
                        break;
                    }
                    if (withdrawMoney(withdraw))
                        System.out.println("Успешно сняты.");
                    else
                        System.out.println("Произошла ошибка при снятии.");
                    break;
                }
                case "4": {
                    exit();
                    return true;
                }
                default: {
                    System.out.println("Выбрана несуществующая операция...");
                    break;
                }
            }
        }
    }

    private boolean depositMoney(float deposit) {
        if (deposit > 1000000)
            //Сумма пополнения должна не превышать один миллион
            return false;
        else {
            cash += deposit;
            card.addValue(deposit);
            return true;
        }
    }

    private boolean withdrawMoney(float withdraw) {
        if (withdraw > card.getValue())
            //Сумма для снятия должна не превышать баланс карты
            return false;
        if (withdraw > cash)
            //Сумма для снятия должна не превышать сумма наличности в банкомате
            return false;
        else {
            cash -= withdraw;
            card.subValue(withdraw);
            return true;
        }
    }

    private void checkBalance() {
        System.out.println(card.getValue() + " руб. составляет баланс.");
    }

    private void exit() {
        card = null;
    }
}
