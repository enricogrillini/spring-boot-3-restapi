package it.eg.cookbook.service;


import it.eg.cookbook.model.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DocumentServices implements InitializingBean {

    private Map<Integer, Document> map;

    @Override
    public void afterPropertiesSet() throws Exception {
        map = new LinkedHashMap<>();

        save(Document.builder().id(1).name("Contratto").description("Contratto tra le parti per sottoscrizione conto corrente").data(LocalDate.now()).updateBy("Paolo Rossi").build());
        save(Document.builder().id(2).name("Recesso").description("Norme per il recesso").data(LocalDate.now()).updateBy("Mario Rossi").build());
        save(Document.builder().id(3).name("Appendice").description("Appendice al contratto di sottoscrizione").data(LocalDate.now()).updateBy("Franco Bianchi").build());
    }

    /**
     * Ritorna la lista documenti
     *
     * @return
     */
    public List<Document> getDocuments() {
        return Collections.unmodifiableList(new ArrayList<>(map.values()));
    }

    /**
     * Ritorna un singolo documento
     *
     * @param documentId
     * @return
     */
    public Document getDocument(Integer documentId) {
        return map.get(documentId);
    }

    /**
     * Elimina un documento
     *
     * @param documentId
     */
    public void delete(Integer documentId) {
        map.remove(documentId);
    }

    /**
     * Aggiorna o inserisce un documento
     *
     * @param document
     */
    public void save(Document document) {
        map.put(document.getId(), document);
    }

}