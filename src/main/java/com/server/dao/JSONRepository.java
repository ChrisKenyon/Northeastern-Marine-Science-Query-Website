package com.server.dao;
import com.server.model.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

public class JSONRepository {

    private static final Map<String, JSON> REPOSITORY = new ConcurrentSkipListMap<>();

    private static final JSONRepository INSTANCE = new JSONRepository();

/*    static {
        INSTANCE.create(new JSON(1L, "Learn AngularJS", "HTML is great for declaring static documents, but it falters when we try to use it for declaring dynamic views in web-applications. AngularJS lets you extend HTML vocabulary for your application. The resulting environment is extraordinarily expressive, readable, and quick to develop. "));
    }*/

    public static JSONRepository getInstance() {
        return INSTANCE;
    }

    private JSONRepository() {
    }

    public List<JSON> list() {
        return new ArrayList<>(REPOSITORY.values());
    }

    public JSON get(String id) {
        return REPOSITORY.get(id);
    }

    public void create(JSON json) {
        REPOSITORY.put(json.getId(), json);
    }
}
