package conexao;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Util {

    public static String getLinkOuPadrao(String url) {
        if (url == null || url.isEmpty()) {
            return "https://karolsalvatore.com.br/links";
        }
        return url;
    }

    public static String formatarData(LocalDate data) {
        if (data != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return data.format(formatter);
        }
        return "";
    }

    public static String formatarMoeda(Double valor) {
        Locale localeBR = new Locale("pt", "BR");
        NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(localeBR);
        return formatadorMoeda.format(valor);
    }
}