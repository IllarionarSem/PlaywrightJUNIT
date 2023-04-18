package model;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.row.BaseRow;
import model.row.ModelInfo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelHandler {

    public static <T extends Model, Row extends BaseRow<T>> List<T> getModelList(Page page, String rowPath, String rowItemPath, Class<Row> rowClass) {
        return page.querySelectorAll(rowPath).stream().map(e -> getModel(e, rowItemPath, rowClass)).toList();
    }

    private static <T extends Model, Row extends BaseRow<T>> T getModel(ElementHandle element, String rowItemPath, Class<Row> rowClass) {
        Row rowModel = null;
        try {
            rowModel = rowClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error(String.format("Error while accessing class %s", rowClass.getSimpleName()));
        }
        Row finalRowModel = rowModel;
        Stream.of(rowModel.getClass().getDeclaredFields()).forEach(field -> {
            if (Objects.nonNull(field.getAnnotation(ModelInfo.class))) {
                String elementColumnName = field.getAnnotation(ModelInfo.class).name();
                ElementHandle itemElement = element.querySelector(String.format(rowItemPath, elementColumnName));
                if (Objects.isNull(itemElement)) {
                    log.warn(String.format("Can't parse element item %s", elementColumnName));
                    return;
                }
                String itemValue = itemElement.innerText().trim();
                field.setAccessible(true);
                try {
                    field.set(finalRowModel, itemValue);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    log.error(String.format("Can't parse the value '%s', to object of %s", itemValue, field.getType().getSimpleName()));
                }
            } else {
                log.warn(String.format("The field '%s' is not annotated with ModelInfo annotation", field.getName()));
            }
        });
        return finalRowModel.asModel();
    }
}
