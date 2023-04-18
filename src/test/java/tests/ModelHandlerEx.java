package tests;

import model.EbayProduct;
import model.ModelHandler;
import model.row.EbayProductRow;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ModelHandlerEx extends BaseTest {
    @Test
    public void modelHandlerEx() {
        page.navigate("https://www.ebay.com/b/Video-Game-Manuals-Inserts-Box-Art/182174/bn_16566359");
        List<EbayProduct> list = ModelHandler
                .getModelList(page,
                        "//ul[contains(@class,'list__items')]//li",
                        "//*[contains(@class,'%s')]",
                        EbayProductRow.class);
        list.forEach(System.out::println);
    }
}
