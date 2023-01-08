package pl.edu.pwr.lgawron.lab07.shop.flow;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.SubmittedOrder;
import pl.edu.pwr.lgawron.lab07.common.IOrderRepository;
import pl.edu.pwr.lgawron.lab07.common.IRepository;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ItemTypeExtended;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ShopAppRenderer {
    private final IRepository<ClientExtended> clientRepository;
    private final IRepository<ItemTypeExtended> itemTypeRepository;
    private final IOrderRepository clientOrdersRepository;
    private final VBox itemBox;
    private final Map<Integer, Node> items;
    private final VBox clientBox;
    private final Map<Integer, Node> clients;
    private final VBox orderBox;
    private final Map<Integer, Node> orders;
    private final VBox infoBox;

    public ShopAppRenderer(IRepository<ClientExtended> clientRepository, IRepository<ItemTypeExtended> itemTypeRepository, IOrderRepository clientOrdersRepository, VBox itemBox, VBox clientBox, VBox orderBox, VBox infoBox) {
        this.clientRepository = clientRepository;
        this.itemTypeRepository = itemTypeRepository;
        this.clientOrdersRepository = clientOrdersRepository;
        this.itemBox = itemBox;
        this.clientBox = clientBox;
        this.orderBox = orderBox;
        this.infoBox = infoBox;
        this.items = new HashMap<>();
        this.clients = new HashMap<>();
        this.orders = new HashMap<>();
    }

    public void renderBasicInfo(ValuesHolder values) {
        infoBox.getChildren().add(new Label("Server Started!"));
        infoBox.getChildren().add(new Label("Registry created on port: " + values.getPort()));
    }

    public void renderAllItems() {
        Platform.runLater(() -> {
            try {
                for (Map.Entry<Integer, Node> integerNodeEntry : items.entrySet()) {
                    itemBox.getChildren().remove(integerNodeEntry.getValue());
                }

                itemTypeRepository.getRepo().forEach(itemTypeExtended -> {
                    Label itemLabel = new Label(itemTypeExtended.toString());
                    itemLabel.setAlignment(Pos.BASELINE_LEFT);
                    items.put(itemTypeExtended.getId(), itemLabel);
                    itemBox.getChildren().add(itemLabel);
                });
            } catch (RemoteException ignored) {
            }
        });
    }

    public void renderClientRegistered(int clientId) {
        Platform.runLater(() -> {
            try {
                ClientExtended byId = clientRepository.getById(clientId);
                Label clientLabel = new Label(byId.toString());
                clientLabel.setAlignment(Pos.BASELINE_LEFT);
                clients.put(clientId, clientLabel);

                clientBox.getChildren().add(clientLabel);
            } catch (RemoteException ignored) {
            }
        });
    }

    public void renderOrderAdded(int orderId) {
        Platform.runLater(() -> {
            try {
                // todo: dodac popup -> wyswietl szczegoly zamowienia -> to samo u sellera bedzie!
                SubmittedOrder byId = clientOrdersRepository.getById(orderId);

                Label orderLabel = new Label("Order" + byId.getId() + " {id=" + byId.getId() + ", clientId=" + byId.getOrder().getClientID() + ", status=" + byId.getStatus().toString() + "}");
                orderLabel.setAlignment(Pos.BASELINE_LEFT);
                HBox hBox = new HBox();
                hBox.getChildren().add(orderLabel);
                // dodac alert do buttoda!!!
                // hBox.getChildren().add();

            } catch (RemoteException ignored) {
            }
        });
    }

    // https://www.tutorialspoint.com/how-to-create-an-alert-in-javafx
    public Alert renderOrderDetails(SubmittedOrder order) {
        // todo: printowanie listy
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "lol", ButtonType.OK);
        alert.showAndWait();
        return alert;
    }

}
