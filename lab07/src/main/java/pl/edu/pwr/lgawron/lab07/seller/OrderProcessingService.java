package pl.edu.pwr.lgawron.lab07.seller;

public class OrderProcessingService {
    private final AppFlow appFlow;
    private final SellerAppRenderer sellerAppRenderer;

    public OrderProcessingService(AppFlow appFlow, SellerAppRenderer sellerAppRenderer) {
        this.appFlow = appFlow;
        this.sellerAppRenderer = sellerAppRenderer;
    }



}
