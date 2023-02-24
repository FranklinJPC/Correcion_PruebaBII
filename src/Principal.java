import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Principal {
    PreparedStatement ps;
    Statement st;
    public JPanel Main;
    private JPanel MENU;
    private JTextField tf_codigo;
    private JTextField tf_producto;
    private JComboBox cb_precioVenta;
    private JTextField tf_precioCompra;
    private JComboBox cb_proveedor;
    private JTextField tf_proveedor;
    private JButton CREARButton;
    private JButton BUSCARButton;
    private JButton ACTUALIZARButton;
    private JButton BORRARButton;
    private JTextField tf_precioVenta;
    private JTextField tf_stock;
    private JButton button1;

    public Principal() {
        CREARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conexion;
                conexion = getConnection();
                try
                {
                    ps = conexion.prepareStatement("INSERT INTO farmacia.productos " +
                            "(id_prod, nombre_prod, precioVenta_prod, precioCompra_prod, stock_prod, proveedor_prod) " +
                            "VALUES (?,?,?,?,?,?)");
                    ps.setString(1,tf_codigo.getText());
                    ps.setString(2,tf_producto.getText());
                    ps.setDouble(3, Double.parseDouble(tf_precioVenta.getText()));
                    ps.setDouble(4, Double.parseDouble(tf_precioCompra.getText()));
                    ps.setInt(5, Integer.parseInt(tf_stock.getText()));
                    ps.setString(6,tf_proveedor.getText());
                    int res = ps.executeUpdate();
                    if (res > 0)
                        JOptionPane.showMessageDialog(null, "Se Guardo correctamente!!", "Bien hecho", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "ERROR!!!", "ERROR", JOptionPane.ERROR_MESSAGE);

                    conexion.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        BUSCARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conexion;
                try
                {
                    conexion = getConnection();
                    ResultSet rs;
                    st = conexion.createStatement();
                    rs = st.executeQuery("SELECT * FROM farmacia.productos " +
                            "WHERE id_prod =" + tf_codigo.getText() + ";");
                    while(rs.next())
                    {
                        tf_producto.setText(rs.getString("nombre_prod"));
                        cb_precioVenta.addItem(rs.getString("precioVenta_prod"));
                        tf_precioCompra.setText(String.valueOf(rs.getInt("precioCompra_prod")));
                        tf_stock.setText(String.valueOf(rs.getInt("stock_prod")));
                        cb_proveedor.addItem(rs.getString("proveedor_prod"));
                    }
                    conexion.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ACTUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conexion;
                try
                {
                    conexion = getConnection();
                    ps = conexion.prepareStatement("UPDATE `farmacia`.`productos` SET " +
                            "`nombre_prod` = ?" +
                            ", `precioVenta_prod` = ?" +
                            ", `precioCompra_prod`= ?" +
                            ", `stock_prod`= ?" +
                            ", `proveedor_prod` = ?" +
                            " WHERE (`id_prod` = " +tf_codigo.getText() + ");");
                    ps.setString(1,tf_producto.getText());
                    ps.setDouble(2, Double.parseDouble(tf_precioVenta.getText()));
                    ps.setDouble(3,Double.parseDouble(tf_precioCompra.getText()));
                    ps.setInt(4,Integer.parseInt(tf_stock.getText()));
                    ps.setString(5,tf_proveedor.getText());
                    System.out.println(ps);
                    int res = ps.executeUpdate();
                    if (res > 0)
                        JOptionPane.showMessageDialog(null, "Se Actualizo correctamente!!", "Bien hecho", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "ERROR!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    conexion.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        BORRARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conexion;
                try
                {
                    conexion = getConnection();
                    ps = conexion.prepareStatement("DELETE FROM `farmacia`.`productos`" +
                            " WHERE (`id_prod` = " + tf_codigo.getText() + ");");
                    System.out.println(ps);
                    limpiar();
                    int res = ps.executeUpdate();
                    if (res > 0)
                        JOptionPane.showMessageDialog(null, "Se Elimino correctamente!!", "Bien hecho", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "ERROR!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    conexion.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
    }
    public static Connection getConnection()
    {
        Connection conexion = null;
        String dbName = "farmacia";
        String url = "jdbc:mysql://localhost/";
        String user = "root";
        String password = "franklin123";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }
    public void limpiar()
    {
        tf_codigo.setText("");
        tf_precioCompra.setText("");
        tf_stock.setText("");
        tf_proveedor.setText("");
        tf_precioVenta.setText("");
        tf_producto.setText("");
        cb_proveedor.removeAllItems();
        cb_precioVenta.removeAllItems();
    }
}
