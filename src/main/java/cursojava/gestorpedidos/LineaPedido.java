package cursojava.gestorpedidos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LineaPedido {
    
    @Id
    @ManyToOne
    private Pedido pedido;

    @Id
    @ManyToOne
    private Articulo articulo;
    
    private Integer cantidad;
    
    
    public LineaPedido() {
    }


    public LineaPedido(Pedido pedido, Articulo articulo, Integer cantidad) {
        this.pedido = pedido;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }


    public Pedido getPedido() {
        return pedido;
    }


    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }


    public Articulo getArticulo() {
        return articulo;
    }


    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }


    public Integer getCantidad() {
        return cantidad;
    }


    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "LineaPedido [pedido=" + pedido + ", articulo=" + articulo + ", cantidad=" + cantidad + "]";
    }
}
