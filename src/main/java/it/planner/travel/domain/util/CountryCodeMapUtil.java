package it.planner.travel.domain.util;

import java.util.Map;

public class CountryCodeMapUtil {
    public static final Map<String, String> COUNTRY_CODES = Map.ofEntries(
            Map.entry("ITALY", "IT"),
            Map.entry("FRANCE", "FR"),
            Map.entry("GERMANY", "DE"),
            Map.entry("SPAIN", "ES"),
            Map.entry("UNITED KINGDOM", "GB"),
            Map.entry("UNITED STATES", "US"),
            Map.entry("CANADA", "CA"),
            Map.entry("AUSTRALIA", "AU"),
            Map.entry("JAPAN", "JP"),
            Map.entry("CHINA", "CN"),
            Map.entry("INDIA", "IN"),
            Map.entry("BRAZIL", "BR"),
            Map.entry("RUSSIA", "RU"),
            Map.entry("MEXICO", "MX"),
            Map.entry("ARGENTINA", "AR"),
            Map.entry("SOUTH AFRICA", "ZA"),
            Map.entry("EGYPT", "EG"),
            Map.entry("TURKEY", "TR"),
            Map.entry("SOUTH KOREA", "KR"),
            Map.entry("INDONESIA", "ID"));

    public static String getCountryCode(String countryName) {
        for (Map.Entry<String, String> entry : COUNTRY_CODES.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(countryName)) {
                return entry.getValue();
            }
        }
        return null; // oppure puoi lanciare un'eccezione o restituire un valore di default
    }

}