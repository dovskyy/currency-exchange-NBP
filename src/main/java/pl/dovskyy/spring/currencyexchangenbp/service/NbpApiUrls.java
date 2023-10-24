package pl.dovskyy.spring.currencyexchangenbp.service;

/*
Official NBP API documentation can be found over here: http://api.nbp.pl/

 */

public enum NbpApiUrls {

    ALL("http://api.nbp.pl/api/exchangerates/tables/a/"), //all rates from table A
    USD("http://api.nbp.pl/api/exchangerates/rates/c/usd/"), //USD rate with the latest effective date
    EUR("http://api.nbp.pl/api/exchangerates/rates/c/eur/"),
    GBP("http://api.nbp.pl/api/exchangerates/rates/c/gbp/"),
    CHF("http://api.nbp.pl/api/exchangerates/rates/c/chf/");


    private String url;

    NbpApiUrls(String nbpUrl) {
        this.url = nbpUrl;
    }

    public String getUrl() {
        return url;
    }
}
