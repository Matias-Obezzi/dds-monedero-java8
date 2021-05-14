package dds.monedero.model.movimiento;

import dds.monedero.model.Cuenta;

import java.time.LocalDate;

public abstract class Movimiento {
  private LocalDate fecha;
  private double monto;

  public Movimiento(LocalDate fecha, double monto) {
    this.fecha = fecha;
    this.monto = monto;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public boolean fueDepositado(LocalDate fecha) {
    return !isExtraccion() && esDeLaFecha(fecha);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public boolean isDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public abstract boolean isExtraccion();

  public abstract double calcularValor(Cuenta cuenta);
}
