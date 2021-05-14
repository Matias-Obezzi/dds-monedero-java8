package dds.monedero.model.movimiento;

import dds.monedero.model.Cuenta;

import java.time.LocalDate;

public class Deposito extends Movimiento{
  public Deposito(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  @Override
  public boolean isExtraccion() {
    return false;
  }

  @Override
  public double calcularValor(Cuenta cuenta) {
    // Hacemos uso del getter de monto porque es privado
    return cuenta.getSaldo() + getMonto();
  }
}
