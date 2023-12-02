package mg.opendays2023.controller;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import mg.opendays2023.OpenDays2023App;
import mg.opendays2023.model.FootballClub;
import mg.opendays2023.repository.FootballClubRepo;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class FootBallClubController implements Initializable {

    private final FootballClubRepo footballClubRepo;

    @FXML
    private Button addButton;

    @FXML
    private ListView<FootballClub> listView;

    @FXML
    private TextField textfield;

    @FXML
    private Spinner<Integer> ldcCount;

    private ObservableList<FootballClub> clubList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialize clubList as an ObservableList
        clubList = FXCollections.observableArrayList();

        addButton.setOnAction(event -> {
            String footballClubName = textfield.getText();
            Integer ldcCountValue = ldcCount.getValue();
            textfield.setText("");
            FootballClub footballClub = footballClubRepo.save(FootballClub.builder().name(footballClubName).ldcCount(ldcCountValue).build());
            clubList.add(footballClub); // Add to the ObservableList
        });

        listView.setCellFactory(param -> new FootballClubListCell());
        // Set the items in the ListView to the ObservableList
        listView.setItems(clubList);


        // Disable selection in the ListView
        listView.setOnMouseClicked(event -> listView.getSelectionModel().clearSelection());


        initializeSpinner();

    }

    private void initializeSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        ldcCount.setValueFactory(valueFactory);
    }

    private class FootballClubListCell extends ListCell<FootballClub> {
        @FXML
        private JFXButton deleteButton;
        @FXML
        private Label label;
        @FXML
        private HBox starBox;

        private List<FontAwesomeIconView> initStart(int count) {
            return IntStream.range(0, count)
                    .mapToObj(i -> {return new FontAwesomeIconView(FontAwesomeIcon.STAR, "20");})
                    .collect(Collectors.toList());
        }

        @SneakyThrows
        @Override
        protected void updateItem(FootballClub footballClub, boolean empty) {
            super.updateItem(footballClub, empty);
            if (empty || footballClub == null) {
                setText(null);
            } else {
                // You can customize the cell appearance further if needed
                FXMLLoader fxmlLoader = new FXMLLoader(OpenDays2023App.class.getResource("fcListCell.fxml"));
                fxmlLoader.setController(this);

                Node node = (Node)fxmlLoader.load();

                // INITIALIZE ITEM AFTER LOADING .fxml file
                label.setText(footballClub.getName());
                starBox.getChildren().addAll(initStart(footballClub.getLdcCount()));

                // When deleting an item:
                deleteButton.setOnAction(event -> {
                    FootballClub selectedClub = listView.getSelectionModel().getSelectedItem();
                    if (selectedClub != null) {
                        clubList.remove(selectedClub); // Remove from ObservableList
                        footballClubRepo.delete(selectedClub); // Remove from data source
                    }
                });


                setGraphic(node);
            }
        }
    }


}