package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MonederoTest {
  private Cuenta cuenta;
  private LocalDate hoy;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
    hoy = LocalDate.now();
  }

  @Test
  void Poner() {
    double monto = 1500;
    cuenta.poner(1500);
    assertEquals(monto, cuenta.getSaldo());
  }

  @Test
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  void TresDepositos() {
    double monto1 = 1500,
      monto2 = 456,
      monto3 = 1900,
      totalMontos = monto1 + monto2 + monto3;

    cuenta.poner(monto1);
    cuenta.poner(monto2);
    cuenta.poner(monto3);

    assertEquals(totalMontos, cuenta.getSaldo());
    assertEquals(3, cuenta.getMovimientos().size());
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500);
          cuenta.poner(456);
          cuenta.poner(1900);
          cuenta.poner(245);
    });
  }

  @Test
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }

  @Test
  public void ExtraerMontoPermitido() {
    double saldo = 1000,
      monto = 799,
      saldoDespues = saldo - monto;
    cuenta.setSaldo(saldo);
    cuenta.sacar(monto);
    assertEquals(saldoDespues, cuenta.getSaldo());
  }

  @Test
  public void MontoDepositadoHoy() {
    cuenta.poner(300);
    assertTrue(cuenta.getMovimientos().get(0).fueDepositado(hoy));
  }

  @Test
  public void MontoExtraidoHoy() {
    cuenta.poner(300);
    cuenta.sacar(200);
    assertEquals(200, cuenta.getMontoExtraidoA(hoy));
  }

}