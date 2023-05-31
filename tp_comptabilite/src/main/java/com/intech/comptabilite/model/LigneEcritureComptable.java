package com.intech.comptabilite.model;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.intech.comptabilite.model.validation.MontantComptable;

@Entity
@Table(name = "ligne_ecriture_comptable")
public class LigneEcritureComptable {
	
	@EmbeddedId
	private LigneId ligneId; // Pour gérer une clé composée

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "compteComptableNumero")
    @NotNull
    private CompteComptable compteComptable;

    /** The Libelle. */
    @Size(max = 200)
    private String libelle;

    /** The Debit. */
    @MontantComptable
    private BigDecimal debit;

    /** The Credit. */
    @MontantComptable
    private BigDecimal credit;
    
    public LigneEcritureComptable() {
    }

    public LigneEcritureComptable(CompteComptable pCompteComptable, String pLibelle,
                                  BigDecimal pDebit, BigDecimal pCredit) {
        compteComptable = pCompteComptable;
        libelle = pLibelle;
        debit = pDebit;
        credit = pCredit;
    }

    public LigneId getLigneId() {
		return ligneId;
	}
    public void setLigneId(LigneId ligneId) {
		this.ligneId = ligneId;
	}    
    public CompteComptable getCompteComptable() {
        return compteComptable;
    }
    public void setCompteComptable(CompteComptable pCompteComptable) {
        compteComptable = pCompteComptable;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }
    public BigDecimal getDebit() {
        return debit;
    }
    public void setDebit(BigDecimal pDebit) {
        debit = pDebit;
    }
    public BigDecimal getCredit() {
        return credit;
    }
    public void setCredit(BigDecimal pCredit) {
        credit = pCredit;
    }
}
