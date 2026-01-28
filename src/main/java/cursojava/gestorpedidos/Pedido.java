package cursojava.gestorpedidos;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedido {
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Cliente cliente;

    private LocalDate fecha;

    public Pedido() {
    }

    public Pedido(Long id, Cliente cliente, LocalDate fecha) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Pedido [id=" + id + ", cliente=" + cliente + ", fecha=" + fecha + "]";
    }

    
}
