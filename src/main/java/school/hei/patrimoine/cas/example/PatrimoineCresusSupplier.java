package school.hei.patrimoine.cas.example;

import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static java.time.Month.SEPTEMBER;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.util.stream.Collectors.toSet;
import static school.hei.patrimoine.modele.Argent.euro;
import static school.hei.patrimoine.modele.Devise.EUR;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.AchatMaterielAuComptant;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.Creance;
import school.hei.patrimoine.modele.possession.Dette;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.GroupePossession;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;
import school.hei.patrimoine.modele.possession.TransfertArgent;

public class PatrimoineCresusSupplier implements Supplier<Patrimoine> {

  private final LocalDate ajd = LocalDate.of(2024, JULY, 8);
  private final LocalDate dans1mois = ajd.plusMonths(1);
  private final LocalDate finSimulation = LocalDate.of(2028, MARCH, 31);

  private Set<Possession> possessionsCresus(Compte bp, Compte a2, Dette dette, Creance creance) {
    new FluxArgent("Loyer", bp, dans1mois, finSimulation, euro(-1600), 25);
    new FluxArgent("Consommables", bp, dans1mois, finSimulation, euro(-1000), 1);
    // note(verif)
    new FluxArgent(
        "Contribution Cesar",
        bp,
        LocalDate.of(2024, AUGUST, 30),
        LocalDate.of(2025, JULY, 30),
        euro(2000),
        25);

    var finSalaire = LocalDate.of(2024, SEPTEMBER, 30);
    new FluxArgent("Salaire 1", bp, ajd, finSalaire, euro(2100), 7);
    new FluxArgent(
        "Salaire 2",
        bp,
        finSalaire.plusMonths(1),
        LocalDate.of(2025, DECEMBER, 31),
        euro(3_200) /*note(verif)*/,
        7);

    new FluxArgent("Prêt BP", bp, dans1mois, LocalDate.of(2027, APRIL, 30), euro(-702), 7);
    new FluxArgent("O2", bp, ajd, finSalaire, euro(-145), 20);

    var dateRembPva = LocalDate.of(2024, JULY, 14); // note(verif)
    new FluxArgent(
        "Raliz", bp, dateRembPva, dateRembPva, euro(-7_000), dateRembPva.getDayOfMonth());
    new FluxArgent("Néri", bp, dateRembPva, dateRembPva, euro(-5_000), dateRembPva.getDayOfMonth());
    new FluxArgent("Hita", bp, dateRembPva, dateRembPva, euro(-1_000), dateRembPva.getDayOfMonth());

    var debutRembMyriade = LocalDate.of(2025, JANUARY, 30);
    new FluxArgent(
        "Raly remb. Myriade", a2, debutRembMyriade, debutRembMyriade.plusYears(3), euro(500), 25);
    new FluxArgent(
        "Rom remb. Myriade 1/2",
        a2,
        debutRembMyriade,
        debutRembMyriade.plusYears(1),
        euro(1050),
        25);
    new FluxArgent(
        "Rom remb. Myriade 2/2",
        a2,
        debutRembMyriade.plusYears(1),
        debutRembMyriade.plusYears(2),
        euro(969),
        25);

    new FluxArgent("Graff & Pat", bp, ajd, LocalDate.of(2024, AUGUST, 30), euro(-1400), 27);
    new FluxArgent(
        "Graff & Pat remb.",
        a2,
        LocalDate.of(2024, SEPTEMBER, 30),
        LocalDate.of(2025, AUGUST, 30),
        euro(1680),
        7);
    new FluxArgent("Mim remb.", a2, ajd, LocalDate.of(2026, MAY, 14), euro(500), 14);
    new FluxArgent(
        "Fano créance",
        creance,
        ajd,
        ajd,
        euro(500 * (int) LocalDate.of(2023, SEPTEMBER, 1).until(ajd, MONTHS)),
        ajd.getDayOfMonth());
    new FluxArgent("Fano remb.", a2, ajd, LocalDate.of(2024, JULY, 27), euro(500), 27);

    var tauxAppreciationByzance = 0.1;
    var dateEmpruntZanzEtCie = LocalDate.of(2024, JULY, 5);
    var dateRembZanzEtCie = dateEmpruntZanzEtCie.plusYears(1);
    var dateRembPvaByzance = LocalDate.of(2025, JANUARY, 27);
    var byzance =
        new GroupePossession(
            "Byzance",
            EUR,
            LocalDate.of(2024, JANUARY, 1),
            Set.of(
                new FluxArgent(
                    "Zanz&Cie prêt",
                    bp,
                    dateEmpruntZanzEtCie,
                    dateEmpruntZanzEtCie,
                    euro(30_000),
                    dateEmpruntZanzEtCie.getDayOfMonth()),
                new FluxArgent(
                    "Zanz&Cie dette creation",
                    dette,
                    dateEmpruntZanzEtCie,
                    dateEmpruntZanzEtCie,
                    euro(-33_000),
                    dateEmpruntZanzEtCie.getDayOfMonth()),
                new FluxArgent(
                    "Zanz&Cie remb.",
                    bp,
                    dateRembZanzEtCie,
                    dateRembZanzEtCie,
                    euro(-33_000),
                    dateRembZanzEtCie.getDayOfMonth()),
                new FluxArgent(
                    "Zanz&Cie dette annulation",
                    dette,
                    dateRembZanzEtCie,
                    dateRembZanzEtCie,
                    euro(33_000),
                    dateRembZanzEtCie.getDayOfMonth()),
                new FluxArgent(
                    "Honoraires Villey Byzance",
                    dette,
                    ajd,
                    ajd,
                    euro(-15_000),
                    ajd.getDayOfMonth()),
                new FluxArgent(
                    "PVA&Cie Byzance remb.",
                    bp,
                    dateRembPvaByzance,
                    dateRembPvaByzance,
                    euro(-60_000),
                    dateRembPvaByzance.getDayOfMonth()),
                new FluxArgent(
                    "PVA&Cie Byzance Dette creation",
                    dette,
                    ajd,
                    ajd,
                    euro(-60_000),
                    ajd.getDayOfMonth()),
                new FluxArgent(
                    "PVA&Cie Byzance Dette annulation",
                    dette,
                    dateRembPvaByzance,
                    dateRembPvaByzance,
                    euro(60_000),
                    dateRembPvaByzance.getDayOfMonth()),
                new AchatMaterielAuComptant(
                    "Byzance 2/3",
                    LocalDate.of(2024, JULY, 31),
                    euro(30_000),
                    tauxAppreciationByzance,
                    bp),
                new AchatMaterielAuComptant(
                    "Byzance 3/3",
                    LocalDate.of(2025, JULY, 31),
                    euro(30_000),
                    tauxAppreciationByzance,
                    bp)));
    new FluxArgent("Achat Byzance 1/2", bp, ajd, LocalDate.of(2024, AUGUST, 30), euro(-1400), 27);

    return Set.of(
        bp,
        a2,
        dette,
        creance,
        byzance,
        new Materiel(
            "Byzance 1/3",
            LocalDate.of(2024, JANUARY, 1),
            ajd,
            euro(60_000),
            tauxAppreciationByzance));
  }

  private Set<Possession> possessionsMyriade(
      Compte myriadeFr, Creance creanceFr, Dette detteMyriadeFr, Compte myriadeMg, Compte bp) {
    // note(verif)
    var dateRembCAR = LocalDate.of(2024, JULY, 10);
    new FluxArgent(
        "CAR remb.",
        myriadeFr,
        dateRembCAR,
        dateRembCAR,
        euro(78_000),
        dateRembCAR.getDayOfMonth());
    var dateRembCCA = LocalDate.of(2024, JULY, 15);
    new TransfertArgent(
        "CCA remb.",
        myriadeFr,
        bp,
        dateRembCCA,
        dateRembCCA,
        dateRembCCA.getDayOfMonth(),
        euro(30_000));
    new FluxArgent("CAR 2024 créance", creanceFr, ajd, ajd, euro(45_000), ajd.getDayOfMonth());
    var dateRembCAR24 = LocalDate.of(2025, JULY, 10);
    new TransfertArgent(
        "CAR 2024 remb",
        creanceFr,
        myriadeFr,
        dateRembCAR24,
        dateRembCAR24,
        dateRembCAR24.getDayOfMonth(),
        euro(45_000));

    new FluxArgent(
        "Cesar dette",
        detteMyriadeFr,
        ajd,
        ajd,
        euro(-3_700 * (int) LocalDate.of(2024, MARCH, 1).until(ajd, MONTHS)),
        ajd.getDayOfMonth());
    new FluxArgent(
        "Cesar salaire", myriadeFr, ajd, LocalDate.of(2024, DECEMBER, 27), euro(-3_700), 27);
    new FluxArgent("SAFIR", myriadeFr, ajd, LocalDate.of(2024, DECEMBER, 27), euro(-2_800), 16);
    new FluxArgent("Manuhis", myriadeFr, ajd, LocalDate.of(2024, DECEMBER, 27), euro(-1_400), 26);
    new FluxArgent("Fitpal", myriadeFr, ajd, LocalDate.of(2024, DECEMBER, 27), euro(-200), 20);

    var date1AvanceStratosphery = LocalDate.of(2024, JULY, 5);
    new FluxArgent(
        "Avance Stratosphery 6 mois 1/2",
        myriadeFr,
        date1AvanceStratosphery,
        date1AvanceStratosphery,
        euro((int) (12_500 * (1 - 0.05))),
        date1AvanceStratosphery.getDayOfMonth());
    var date2AvanceStratosphery = LocalDate.of(2024, AUGUST, 5);
    new FluxArgent(
        "Avance Stratosphery 6 mois 2/2",
        myriadeFr,
        date2AvanceStratosphery,
        date2AvanceStratosphery,
        euro((int) (12_500 * (1 - 0.05))),
        date2AvanceStratosphery.getDayOfMonth());

    var datePrestationVilley = LocalDate.of(2024, JULY, 30);
    new FluxArgent(
        "Prestation Villey",
        myriadeMg,
        datePrestationVilley,
        datePrestationVilley,
        euro(-1_000),
        datePrestationVilley.getDayOfMonth());

    var dateRembITM = LocalDate.of(2025, JANUARY, 30);
    new FluxArgent(
        "Prêt ITM remb.",
        myriadeMg,
        dateRembITM,
        dateRembITM,
        euro(-11_000),
        dateRembITM.getDayOfMonth());

    var finDontBeFoe = LocalDate.of(2025, DECEMBER, 5);
    new FluxArgent("DontBeFoe", myriadeFr, ajd, finDontBeFoe, euro(7_500), 3);
    new TransfertArgent(
        "Myriade Fr --> Mg", myriadeFr, myriadeMg, ajd, finDontBeFoe, 27, euro(5_000));
    new FluxArgent("Charges Myriade Mg", myriadeMg, ajd, finDontBeFoe, euro(-5_000), 27);

    return Set.of(myriadeFr, creanceFr, detteMyriadeFr, myriadeMg);
  }

  @Override
  public Patrimoine get() {
    var cresus = new Personne("Cresus");
    var bp = new Compte("BP Cresus & Cesar", ajd, euro(4_030));
    var a2 = new Compte("A2", ajd, euro(500));
    var creanceCresus = new Creance("Creance Cresus", ajd, euro(0));
    var detteCresus = new Dette("Dette Cresus", ajd, euro(0));
    Set<Possession> possessionsCresus = possessionsCresus(bp, a2, detteCresus, creanceCresus);

    var myriadeFr = new Compte("Myriade Fr", ajd, euro(840));
    var creanceMyriadeFr = new Creance("Creance Myriade Fr", ajd, euro(0));
    var detteMyriadeFr = new Dette("Dette Myriade Fr", ajd, euro(0));
    var myriadeMg = new Compte("Myriade Mg", ajd, euro(0));

    return Patrimoine.of(
        "Crésus",
        EUR,
        ajd,
        cresus,
        Stream.concat(
                possessionsCresus.stream(),
                possessionsMyriade(myriadeFr, creanceMyriadeFr, detteMyriadeFr, myriadeMg, bp)
                    .stream())
            .collect(toSet()));
  }
}
