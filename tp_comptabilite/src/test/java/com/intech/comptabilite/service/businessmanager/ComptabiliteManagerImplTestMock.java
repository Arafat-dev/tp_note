package com.intech.comptabilite.service.businessmanager;

import com.intech.comptabilite.model.*;
import com.intech.comptabilite.service.entityservice.CompteComptableService;
import com.intech.comptabilite.service.entityservice.EcritureComptableService;
import com.intech.comptabilite.service.entityservice.JournalComptableService;
import com.intech.comptabilite.service.entityservice.SequenceEcritureComptableService;
import com.intech.comptabilite.service.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(ComptabiliteManagerImpl.class)
public class ComptabiliteManagerImplTestMock {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EcritureComptableService ecritureComptableService;

    @MockBean
    private JournalComptableService journalComptableService;

    @MockBean
    private CompteComptableService compteComptableService;

    @MockBean
    private SequenceEcritureComptableService sequenceEcritureComptableService;

    private List<CompteComptable> compteComptableList;
    private List<JournalComptable> journalComptableList;
    private List<EcritureComptable> ecritureComptableList;

    @BeforeEach
    void setUp() {
        // Création des données de test
        compteComptableList = new ArrayList<>();
        compteComptableList.add(new CompteComptable(1, "Compte 1"));
        compteComptableList.add(new CompteComptable(2, "Compte 2"));

        journalComptableList = new ArrayList<>();
        journalComptableList.add(new JournalComptable("J1", "Journal 1"));
        journalComptableList.add(new JournalComptable("J2", "Journal 2"));

        ecritureComptableList = new ArrayList<>();
        EcritureComptable ecritureComptable1 = new EcritureComptable();
        ecritureComptable1.setReference("E1");
        ecritureComptable1.setDate(LocalDate.now());
        ecritureComptable1.setLibelle("Ecriture 1");
        ecritureComptableList.add(ecritureComptable1);

        EcritureComptable ecritureComptable2 = new EcritureComptable();
        ecritureComptable2.setReference("E2");
        ecritureComptable2.setDate(LocalDate.now());
        ecritureComptable2.setLibelle("Ecriture 2");
        ecritureComptableList.add(ecritureComptable2);
    }

    @Test
    public void getListCompteComptableTest() throws Exception {
        when(compteComptableService.getListCompteComptable()).thenReturn(compteComptableList);

        mockMvc.perform(get("/compte-comptable"))
                .andExpect(status().isOk);
    }

    @Test
    public void getListJournalComptableTest() throws Exception {
        when(journalComptableService.getListJournalComptable()).thenReturn(journalComptableList);

        mockMvc.perform(get("/journal-comptable"))
                .andExpect(status().isOk());
    }

    @Test
    public void getListEcritureComptableTest() throws Exception {
        when(ecritureComptableService.getListEcritureComptable()).thenReturn(ecritureComptableList);

        mockMvc.perform(get("/ecriture-comptable"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEcritureComptableByReferenceTest() throws Exception {
        String reference = "E1";
        when(ecritureComptableService.getEcritureComptableByReference(reference)).thenReturn(ecritureComptableList.get(0));

        mockMvc.perform(get("/ecriture-comptable/{reference}", reference))
                .andExpect(status().isOk());
    }

    @Test
    public void insertEcritureComptableTest() throws Exception {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setReference("E3");
        ecritureComptable.setDate(LocalDate.now());
        ecritureComptable.setLibelle("Ecriture 3");
        ecritureComptable.setTotalDebit(BigDecimal.valueOf(100));
        ecritureComptable.setTotalCredit(BigDecimal.valueOf(100));

        when(ecritureComptableService.insertEcritureComptable(any(EcritureComptable.class))).thenReturn(ecritureComptable);

        mockMvc.perform(post("/ecriture-comptable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ecritureComptable)))
                .andExpect(status().isCreated());
    }

    // Méthode utilitaire pour convertir un objet en chaîne JSON
    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }
}

