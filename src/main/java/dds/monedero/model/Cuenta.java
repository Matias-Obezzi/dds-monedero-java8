package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import dds.monedero.model.movimiento.Deposito;
import dds.monedero.model.movimiento.Extraccion;
import dds.monedero.model.movimiento.Movimiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    this(0);
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
  }

  public void poner(double cuanto) {
    validarLimiteDepositosDiarios();
    validarMontoPositivo(cuanto);
    agregarMovimiento(new Deposito(LocalDate.now(), cuanto));
  }

  public void sacar(double cuanto) {
    validarMontoPositivo(cuanto);
    validarMontoExtraccion(cuanto);
    validarMontoLimiteDiario(cuanto);
    agregarMovimiento(new Extraccion(LocalDate.now(), cuanto));
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> movimiento.fueExtraido(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  private void validarMontoPositivo(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  private void validarLimiteDepositosDiarios() {
    // El limite de depositos diarios podria ser una propiedad y establecerse en el constructor
    if (getMovimientos().stream().filter(movimiento -> !movimiento.isExtraccion()).count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  private void validarMontoLimiteDiario(double cuanto) {
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now()),
      limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      // El limite del monto total de extracciones diarias podria ser una propiedad y
      // establecerse en el constructor
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
              + " diarios, límite: " + limite);
    }
  }

  public void validarMontoExtraccion(double cuanto) {
    if (saldo - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + saldo + " $");
    }
  }
}
