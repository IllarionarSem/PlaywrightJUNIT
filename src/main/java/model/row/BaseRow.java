package model.row;

import model.Model;

public abstract class BaseRow<T extends Model> {

    public abstract T asModel();
}
