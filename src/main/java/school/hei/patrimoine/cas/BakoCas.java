package school.hei.patrimoine.cas.PRO3;

import school.hei.patrimoine.cas.Cas;
import school.hei.patrimoine.modele.Argent;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;

public class BakoCas extends Cas {
    public BakoCas(LocalDate ajd, LocalDate finSimulation, Map<Personne, Double> possesseurs) {
        super(ajd, finSimulation, possesseurs);
    }


    @Override
    protected Devise devise() {
        return Devise.MGA;
    }

    @Override
    protected String nom() {
        return "Cas d'un etudiant de pro 3";
    }

    @Override
    protected void init() {

    }

    @Override
    protected void suivi() {

    }

    @Override
    public Set<Possession> possessions() {

        var bako = new Personne("Bako");

        var compteCourant = new Compte(
                "BNI",
                LocalDate.of(2025, Month.APRIL, 8),
                new Argent(2_000_000,Devise.MGA)
        );
        var compteEpargne = new Compte(
                "BMOI",
                LocalDate.of(2025, Month.APRIL, 8),
                new Argent(625_000, Devise.MGA)
        );
        var coffreFort = new Compte(
                "Maison",
                LocalDate.of(2025, Month.APRIL, 8),
                new Argent(1_750_000, Devise.MGA)
        );
        var debutTravail = LocalDate.of(2025, Month.APRIL, 1);
        var finTravail = LocalDate.of(2026,Month.APRIL, 1);
        var salaire = new FluxArgent(
                "CDI",
                compteCourant,
                debutTravail,
                finTravail,
                2,
                new Argent(2_115_000, Devise.MGA)
        );

        var debutEpargne = LocalDate.of(2025, Month.MARCH, 3);
        var finEpargne = LocalDate.of(2026, Month.APRIL, 3);
        var epargne = new TransfertArgent(
                "Epargne",
                compteCourant,
                compteEpargne,
                debutEpargne,
                finEpargne,
                3,
                new Argent(200_000, Devise.MGA)
        );

        var materiel = new Materiel(
                "Ordinateur portable",
                LocalDate.of(2025,Month.APRIL, 8),
                LocalDate.now(),
                new Argent(3_000_000, Devise.MGA),
                -0.12
        );

        return Patrimoine.of(
                "Bako au 31 Decembre 2025",
                LocalDate.of(2025, Month.DECEMBER,31),
                Devise.MGA,
                bako,
                Set.of(
                        compteCourant,
                        compteEpargne,
                        coffreFort,
                        materiel
                ),

                )
    }
}
