package diaballik.serialization;

import com.fasterxml.jackson.databind.util.StdConverter;
import diaballik.model.Turn;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListDeconverter extends StdConverter<List<Turn>, ObservableList<Turn>> {
	@Override
	public ObservableList<Turn> convert(final List<Turn> list) {
		return FXCollections.observableArrayList(list);
	}
}
