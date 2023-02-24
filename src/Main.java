import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Principal");
        ventana.setContentPane(new Principal().Main);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setSize(700,500);
        ventana.setVisible(true);
    }
}