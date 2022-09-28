import com.oswin.CoinDeskApplication;
import com.oswin.model.Coin;
import com.oswin.model.ForeignCurrency;
import com.oswin.service.CoindeskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = CoinDeskApplication.class)
@WebAppConfiguration
public class _CoinTest {

    @Autowired
    private CoindeskService coindeskService;


    @Test
    public void getCoinAPI() {
        Coin coin = generateCoin("Test_Id_Get");
        try {
            coindeskService.addCoin(coin);
        } catch (Exception e) {
            assertTrue(false);
        }
        Coin actual_coin = coindeskService.getCoinById(coin.getId());
        System.out.println(actual_coin);
        assertTrue(actual_coin != null);
    }


    @Test
    public void addCoinAPI() {
        Coin coin = generateCoin("Test_Id_Add");
        System.out.println(coin);
        try {
            coindeskService.addCoin(coin);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertTrue(true);
    }

    @Test
    public void updateCoinAPI() {
        Coin coin = generateCoin("Test_Id_Update");
        Coin updateCoin = generateUpdateCoin("Test_Id_Update");
        try {
            coindeskService.addCoin(coin);
            coindeskService.updateCoin(updateCoin);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        Coin actual_coin = coindeskService.getCoinById("Test_Id_Update");
        System.out.println(actual_coin);
        assertEquals(updateCoin.getChartName(), actual_coin.getChartName());
        assertEquals(updateCoin.getChineseName(), actual_coin.getChineseName());
        assertEquals(updateCoin.getForeignCurrency().size(), actual_coin.getForeignCurrency().size());
        for (int i = 0; i < updateCoin.getForeignCurrency().size(); i++) {
            assertEquals(updateCoin.getForeignCurrency().get(i).getCode(), actual_coin.getForeignCurrency().get(i).getCode());
            assertEquals(updateCoin.getForeignCurrency().get(i).getDescription(), actual_coin.getForeignCurrency().get(i).getDescription());
            assertEquals(updateCoin.getForeignCurrency().get(i).getRate_float(), actual_coin.getForeignCurrency().get(i).getRate_float());
            assertEquals(updateCoin.getForeignCurrency().get(i).getSymbol(), actual_coin.getForeignCurrency().get(i).getSymbol());
            assertEquals(updateCoin.getForeignCurrency().get(i).getId(), actual_coin.getForeignCurrency().get(i).getId());
        }
    }

    @Test
    public void deleteCoinAPI() {
        Coin coin = generateCoin("Test_Id_Delete");
        int f = 0;
        try {
            coindeskService.addCoin(coin);
            f = coindeskService.deleteCoin("Test_Id_Delete");
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertTrue(f > 0);
    }



    @Test
    public void getCoindeskAPI() {
        String result = coindeskService.getCoindeskAPI();
        System.out.println(result);
        assertNotEquals(result, "Failed");
    }

    @Test
    public void getConvertCoindeskAPI() {
        String convertCoin = coindeskService.getConvertCoin();
        System.out.println(convertCoin);
        assertNotEquals(convertCoin, null);
    }


    private Coin generateCoin(String id) {
        String CoinId = id;
        Coin coin = new Coin();
        coin.setId(CoinId);
        coin.setChartName("Test Coin");
        coin.setChineseName("測試幣");
        List<ForeignCurrency> foreignCurrencies = new ArrayList<>();
        ForeignCurrency USD = new ForeignCurrency();
        USD.setId(CoinId);
        USD.setCode("Test_USE");
        USD.setSymbol("Test Symbol USD");
        USD.setDescription("Test Description USD");
        USD.setRate_float(20);
        ForeignCurrency JPY = new ForeignCurrency();
        JPY.setId(CoinId);
        JPY.setCode("Test_JPY");
        JPY.setSymbol("Test Symbol JPY");
        JPY.setDescription("Test Description JPY");
        JPY.setRate_float(23.6);
        foreignCurrencies.add(USD);
        foreignCurrencies.add(JPY);
        coin.setForeignCurrency(foreignCurrencies);
        return coin;
    }

    private Coin generateUpdateCoin(String id) {
        String CoinId = id;
        Coin coin = new Coin();
        coin.setId(CoinId);
        coin.setChartName("Update Coin");
        coin.setChineseName("更新測試幣");
        List<ForeignCurrency> foreignCurrencies = new ArrayList<>();
        ForeignCurrency USD = new ForeignCurrency();
        USD.setId(CoinId);
        USD.setCode("Test_USE");
        USD.setSymbol("Update Symbol USD");
        USD.setDescription("Update Description USD");
        USD.setRate_float(15.3);
        ForeignCurrency JPY = new ForeignCurrency();
        JPY.setId(CoinId);
        JPY.setCode("Test_JPY");
        JPY.setSymbol("Update Symbol JPY");
        JPY.setDescription("Update Description JPY");
        JPY.setRate_float(73.6);
        foreignCurrencies.add(USD);
        foreignCurrencies.add(JPY);
        coin.setForeignCurrency(foreignCurrencies);
        return coin;
    }

}
