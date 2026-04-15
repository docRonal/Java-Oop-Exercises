public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("nie prawidlowa liczba argumentow");
            return;
        }
        String tekst = args[0];
        int start, koniec;
        try {
            start = Integer.parseInt(args[1]);
            koniec = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Parametry indeksów muszą być liczbami całkowitymi");
            return;
        }
        int realnyStart = Math.max(0, start);
        int realnyKoniec = Math.min(tekst.length() - 1, koniec);
        if (realnyStart >= tekst.length()) {
            System.out.println("");
            return;
        }
        if (realnyStart > realnyKoniec) {
            System.out.println("");
            return;
        }

        String wynik = tekst.substring(realnyStart, realnyKoniec + 1);
        System.out.println(wynik);
    }
}
