package it.eg.cookbook.service;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Documento;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DocumentoService implements InitializingBean {

    private Map<Long, Documento> map;
    private Long nextId;

    @Override
    public void afterPropertiesSet() {
        map = new LinkedHashMap<>();
        nextId = 1L;

        save(new Documento(null, "Contratto", "Contratto tra le parti per sottoscrizione conto corrente", LocalDate.of(2024, 1, 1)));
        save(new Documento(null, "Recesso", "Norme per il recesso", LocalDate.of(2024, 1, 1)));
        save(new Documento(null, "Appendice", "Appendice al contratto di sottoscrizione", LocalDate.of(2024, 1, 1)));
    }

    public List<Documento> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(map.values()));
    }

    public Documento findByIdOrThrow(Long id) {
        if (map.containsKey(id)) {
            return map.get(id);
        } else {
            throw new ApiException(ResponseCode.NOT_FOUND, "Documento non trovato");
        }
    }

    public void delete(Long id) {
        map.remove(id);
    }

    public Documento save(Documento documento) {
        if (documento.getId() == null) {
            documento.setId(nextId++);
        }

        map.put(documento.getId(), documento);

        return documento;
    }

}