public class ServidorMain {
    public static void main(String[] args) {
        ServidorModel model = new ServidorModel();
        ServidorView view = new ServidorView();
        ServidorController controller = new ServidorController(view, model);

        view.setVisible(true);
        controller.iniciarServidor(12345);
    }
}