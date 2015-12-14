package com.server.dao;
import com.server.model.CSS;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

public class CSSRepository {

    private static final Map<String, CSS> REPOSITORY = new ConcurrentSkipListMap<>();
    private static final AtomicLong IDS = new AtomicLong(0);

    private static final CSSRepository INSTANCE = new CSSRepository();

    static {
        INSTANCE.create(new CSS("style.css", "style", "First WhalesDBAccessor CSS file", "testing testing testing 1"), "style");
    }

    public static CSSRepository getInstance() {
        return INSTANCE;
    }

    private CSSRepository() {
    }

    public List<CSS> list() {
        return new ArrayList<>(REPOSITORY.values());
    }

    public CSS get(String id) {
        return REPOSITORY.get(id);
    }

    public void create(CSS css, String id) {
        css.setId(id);
        REPOSITORY.put(id, css);
    }
}
