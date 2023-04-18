package model.row;

import model.EbayProduct;

public class EbayProductRow extends BaseRow<EbayProduct> {
    @ModelInfo(name = "item__title")
    private String name;
    @ModelInfo(name = "item__price")
    private String price;

    @Override
    public EbayProduct asModel() {
        return new EbayProduct(name, price);
    }
}
