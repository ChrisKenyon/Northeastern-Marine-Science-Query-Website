package com.server.dao;
import com.server.model.Html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

public class HtmlRepository {

    private static final Map<Long, Html> REPOSITORY = new ConcurrentSkipListMap<>();
    private static final AtomicLong IDS = new AtomicLong(0);

    private static final HtmlRepository INSTANCE = new HtmlRepository();

    static {
        INSTANCE.create(new Html("index.html", 1L, "WhalesDBAccessor index HTML file", "testing"));
    }

    public static HtmlRepository getInstance() {
        return INSTANCE;
    }

    private HtmlRepository() {
    }

    public List<Html> list() {
        return new ArrayList<>(REPOSITORY.values());
    }

    public Html get(Long id) {
        return REPOSITORY.get(id);
    }

    public void create(Html html) {
        long id = IDS.getAndIncrement();
        html.setId(id);
        REPOSITORY.put(id, html);
    }

    public void update(Html html) {
        REPOSITORY.put(html.getId(), html);
    }

    public boolean delete(Long id) {
        return REPOSITORY.remove(id) != null;
    }
}
