package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EbayProduct extends Model {
    private String name;
    private String price;
}
