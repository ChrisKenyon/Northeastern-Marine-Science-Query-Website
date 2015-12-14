package com.server.dao;
import com.server.model.JavaScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

public class JavaScriptRepository {

    private static final Map<String, JavaScript> REPOSITORY = new ConcurrentSkipListMap<>();
    private static final AtomicLong IDS = new AtomicLong(0);

    private static final JavaScriptRepository INSTANCE = new JavaScriptRepository();

    static {
        INSTANCE.create(new JavaScript("whalesJS.js", "whalesJS", "First WhalesDBAccessor js file", "testing testing testing 1"),"whalesJS");
        INSTANCE.create(new JavaScript("map.js", "map", "google map js", "testing testing testing 1"),"map");
    }

    public static JavaScriptRepository getInstance() {
        return INSTANCE;
    }

    private JavaScriptRepository() {
    }

    public List<JavaScript> list() {
        return new ArrayList<>(REPOSITORY.values());
    }

    public JavaScript get(String id) {
        return REPOSITORY.get(id);
    }

    public void create(JavaScript js, String id) {
        js.setId(id);
        REPOSITORY.put(id, js);
    }
}
