package dds.monedero.model.movimiento;

import dds.monedero.model.Cuenta;

import java.time.LocalDate;

public class Extraccion extends Movimiento{
  public Extraccion(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  @Override
  public boolean isExtraccion() {
    return true;
  }

  @Override
  public double calcularValor(Cuenta cuenta) {
    // Hacemos uso del getter de monto porque es privado
    return cuenta.getSaldo() - getMonto();
  }
}
