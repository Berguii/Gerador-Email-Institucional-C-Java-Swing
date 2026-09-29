public class ClienteMain {
    public static void main(String[] args) {
        ClienteView view = new ClienteView();
        ClienteController controller = new ClienteController(view);

        view.setVisible(true);
    }
}