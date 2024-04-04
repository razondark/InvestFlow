package com.example.reportservice.tinkoff.accountInfo;

import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.models.FuturePosition;
import ru.tinkoff.piapi.core.models.Money;
import ru.tinkoff.piapi.core.models.Positions;
import ru.tinkoff.piapi.core.models.SecurityPosition;
import java.util.ArrayList;

public class Account {
    public static void getPositions(InvestApi api) {
        var accounts = api.getUserService().getAccountsSync();
        var mainAccount = accounts.get(0).getId();
        //Получаем и печатаем список позиций
        var positions = api.getOperationsService().getPositionsSync(mainAccount);
        getMoneyPositions(api);
        getFuturesPosition(api);
    }
    public static ArrayList<String> getStocksPositions(Positions positions) {
        //Получаем и печатаем список позиций

        ArrayList<String> positionsList = new ArrayList<>();
        var securities = positions.getSecurities();
        for (SecurityPosition security : securities) {
            positionsList.add(security.getFigi());
        }
        return positionsList;
    }

    public static ArrayList<String> getMoneyPositions(InvestApi api) {
        var accounts = api.getUserService().getAccountsSync();
        var mainAccount = accounts.get(0).getId();
        //Получаем и печатаем список позиций
        var positions = api.getOperationsService().getPositionsSync(mainAccount);

        ArrayList<String> positionsList = new ArrayList<>();

        var moneyList = positions.getMoney();
        for (Money moneyValue : moneyList) {
            positionsList.add(moneyValue.getCurrency());
        }
        return positionsList;
    }
    public static ArrayList<String> getFuturesPosition(InvestApi api) {
        int i = 0;
        var accounts = api.getUserService().getAccountsSync();
        var mainAccount = accounts.get(0).getId();
        //Получаем и печатаем список позиций
        var positions = api.getOperationsService().getPositionsSync(mainAccount);
        ArrayList<String> positionsList = new ArrayList<>();
        var futuresList = positions.getFutures();
        for (FuturePosition security : futuresList) {

            positionsList.add(security.getFigi());
        }
        return positionsList;
    }
}
