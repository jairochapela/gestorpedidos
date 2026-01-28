package cursojava.gestorpedidos;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Articulo {

    @Id
    @GeneratedValue
    private Long id;

    private String denominacion;
    
    private BigDecimal precioUnitario;
    
    
    public Articulo() {
    }


    public Articulo(Long id, String denominacion, BigDecimal precioUnitario) {
        this.id = id;
        this.denominacion = denominacion;
        this.precioUnitario = precioUnitario;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getDenominacion() {
        return denominacion;
    }


    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }


    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }


    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }


    @Override
    public String toString() {
        return "Articulo [id=" + id + ", denominacion=" + denominacion + ", precioUnitario=" + precioUnitario + "]";
    }

    

}

